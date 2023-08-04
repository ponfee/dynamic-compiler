package cn.ponfee.compile;

import cn.ponfee.compile.impl.GroovyCompiler;
import cn.ponfee.compile.impl.JdkCompiler;
import cn.ponfee.compile.model.JavaSource;
import cn.ponfee.compile.model.JavacJavaSource;
import cn.ponfee.compile.model.RegexJavaSource;
import cn.ponfee.commons.exception.Throwables;
import cn.ponfee.commons.util.MavenProjects;
import com.google.common.io.Files;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.nio.charset.StandardCharsets;

public class JavaSourceCompilerTest {
    //final Class<?> _clazz = CompilerSource.class; // error
    final Class<?> _clazz = JavaSourceCompilerTest.class;
    final String sourceCode = Throwables.ThrowingSupplier.get(() -> Files.asCharSource(MavenProjects.getTestJavaFile(_clazz), StandardCharsets.UTF_8).read());

    @Test
    public void testJdk() throws Exception {
        //JavaSource javaSource = new JavacJavaSource(sourceCode);
        JavaSource javaSource = new RegexJavaSource(sourceCode);

        System.out.println("packageName:" + javaSource.getPackageName());
        System.out.println("className:" + javaSource.getPublicClass());
        System.out.println(RegexJavaSource.QUALIFIER_PATTERN.matcher(javaSource.getFullyQualifiedName()).matches());

        Class<?> clazz = new JdkCompiler().compile(javaSource);

        String s = "_clazz==clazz --> " + (_clazz == clazz) + "  ";
        System.out.println(s + _clazz.getClassLoader().getClass());
        System.out.println(StringUtils.leftPad(" ", s.length()) + clazz.getClassLoader().getClass());

        clazz.getMethod("say").invoke(clazz);
    }

    @Test
    public void testGroovy() throws Exception {
        //JavaSource javaSource = new JavacJavaSource(sourceCode);
        JavaSource javaSource = new RegexJavaSource(sourceCode);

        System.out.println("packageName:" + javaSource.getPackageName());
        System.out.println("className:" + javaSource.getPublicClass());

        Class<?> clazz = new GroovyCompiler().compile(javaSource);

        String s = "_clazz==clazz --> " + (_clazz == clazz) + "  ";
        System.out.println(s + _clazz.getClassLoader().getClass());
        System.out.println(StringUtils.leftPad(" ", s.length()) + clazz.getClassLoader().getClass());

        clazz.getMethod("say").invoke(clazz);
    }

    @Test
    public void testCompileForce() throws Exception {
        JavaSource javaSource = new JavacJavaSource(sourceCode);
        System.out.println(javaSource.getFullyQualifiedName());
        Class<?> clazz = new JdkCompiler().compile(javaSource);
        //clazz.getMethod("sayHello").invoke(clazz);
        Class<?> clazz2 = new JdkCompiler().compile(javaSource);
        System.out.println(clazz == clazz2);
        Class<?> clazz3 = new JdkCompiler().compileForce(javaSource);
        System.out.println(clazz == clazz3);
        Class<?> clazz4 = new JdkCompiler().compileForce(javaSource);
        System.out.println(clazz3 == clazz4);
    }

    public static void say() {
        System.out.println("hello world!");
    }
}
