package cn.ponfee.commons.compile;

import cn.ponfee.commons.compile.impl.JdkCompiler;
import cn.ponfee.commons.compile.model.JavaSource;
import cn.ponfee.commons.compile.model.JavacJavaSource;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class JavacJavaSourceTest {

    @Test
    public void test() throws IOException {
        String sourceCode = IOUtils.resourceToString("Cat.txt", StandardCharsets.UTF_8, JavacJavaSourceTest.class.getClassLoader());
        JavaSource javaSource = new JavacJavaSource(sourceCode);
        //JavaSource javaSource = new JavacJavaSource(Files.toString(MavenProjects.getMainJavaFile(HttpRequest.class)));
        Class<?> clazz = new JdkCompiler().compile(javaSource);
        System.out.println(clazz);
        //clazz.getMethod("say").invoke(clazz);
    }
}
