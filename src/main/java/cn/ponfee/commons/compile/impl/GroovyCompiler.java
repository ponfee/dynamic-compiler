package cn.ponfee.commons.compile.impl;

import cn.ponfee.commons.compile.JavaSourceCompiler;
import cn.ponfee.commons.compile.model.JavaSource;
import groovy.lang.GroovyClassLoader;

/**
 * Compile java source code by groovy
 * 
 * @author Ponfee
 */
public class GroovyCompiler implements JavaSourceCompiler {

    // GroovyClassLoader(ClassLoader parentLoader)
    private static final GroovyClassLoader DEFAULT_CLASS_LOADER = new GroovyClassLoader();

    private final GroovyClassLoader gcl;

    public GroovyCompiler() {
        this.gcl = DEFAULT_CLASS_LOADER;
    }

    public GroovyCompiler(ClassLoader parent) {
        this.gcl = new GroovyClassLoader(parent);
    }

    @Override
    public Class<?> compile(String codeSource) {
        Class<?> clazz = gcl.parseClass(codeSource);
        if (clazz == null) {
            throw new RuntimeException("Invalid code source: " + codeSource);
        }
        return clazz;
    }

    @Override
    public Class<?> compile(JavaSource javaSource) {
        return compile(javaSource.getSourceCode());
    }

    @Override
    public Class<?> compileForce(JavaSource javaSource) {
        return compile(javaSource.getSourceCode());
    }

}
