package cn.ponfee.compile;

import $._.a.b.n323c23.$._.CompilerSource;
import cn.ponfee.compile.model.JavaSource;
import cn.ponfee.compile.model.RegexJavaSource;
import cn.ponfee.commons.reflect.ClassUtils;
import cn.ponfee.commons.util.MavenProjects;
import com.google.common.io.Files;
import org.junit.Test;

import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import javax.tools.ToolProvider;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

/**
 * 源码编译
 *
 * @author fupf
 */
public class JavaSourceCompilerDemo {

    @Test
    public void test() throws Exception {
        compile(Files.asCharSource(MavenProjects.getTestJavaFile(CompilerSource.class), StandardCharsets.UTF_8).read());
    }

    @SuppressWarnings({"unchecked", "resource", "rawtypes"})
    public static void compile(String sourceCode) throws Exception {
        // 1.解析源码
        //JavaSource code = new JavacJavaSource(sourceCode);
        JavaSource code = new RegexJavaSource(sourceCode);

        // 2.开始编译  
        List<JavaFileObject> srcs = Arrays.asList(new JavaStringObject(code.getPublicClass(), sourceCode));
        List<String> options = Arrays.asList("-d", ClassUtils.getClasspath());

        CompilationTask task = ToolProvider.getSystemJavaCompiler().getTask(null, null, null, options, null, srcs);
        if (!task.call()) {
            System.out.println("编译失败");
            System.exit(-1);
        }
        System.out.println("编译成功");

        // 3.加载类
        URL[] urls = new URL[]{new URL("file:/" + ClassUtils.getClasspath())};
        URLClassLoader classLoader = new URLClassLoader(urls);
        Class classl = classLoader.loadClass(code.getFullyQualifiedName());
        System.out.println(classl == CompilerSource.class); // true
        Method method = classl.getDeclaredMethod("say");
        method.invoke(classl.newInstance());
    }

    private static class JavaStringObject extends SimpleJavaFileObject {
        private final String code;

        public JavaStringObject(String name, String code) {
            //super(URI.create("string:///" + name.replace('.', '/') + Kind.SOURCE.extension), Kind.SOURCE); 
            super(URI.create(name + Kind.SOURCE.extension), Kind.SOURCE);
            this.code = code;
        }

        @Override
        public CharSequence getCharContent(boolean ignoreEncodingErrors) {
            return code;
        }
    }

}
