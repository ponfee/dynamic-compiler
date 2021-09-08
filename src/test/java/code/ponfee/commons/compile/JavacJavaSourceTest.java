package code.ponfee.commons.compile;

import java.io.IOException;

import code.ponfee.commons.compile.impl.JdkCompiler;
import code.ponfee.commons.compile.model.JavaSource;
import code.ponfee.commons.compile.model.JavacJavaSource;
import code.ponfee.commons.io.Files;

public class JavacJavaSourceTest {

    public static void main(String[] args) throws IOException {
        JavaSource javaSource = new JavacJavaSource(Files.toString("d:/CompilerSource.java"));
        //JavaSource javaSource = new JavacJavaSource(Files.toString(MavenProjects.getMainJavaFile(HttpRequest.class)));
        Class<?> clazz = new JdkCompiler().compile(javaSource);
        System.out.println(clazz);
        //clazz.getMethod("say").invoke(clazz);
    }
}
