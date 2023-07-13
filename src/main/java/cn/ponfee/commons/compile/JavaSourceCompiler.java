package cn.ponfee.commons.compile;

import cn.ponfee.commons.compile.model.JavaSource;

/**
 * java源代码动态编译
 */
public interface JavaSourceCompiler {

    Class<?> compile(String sourceString);

    Class<?> compile(JavaSource javaSource);

    Class<?> compileForce(JavaSource javaSource);
}
