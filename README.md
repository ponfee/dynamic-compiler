# commons-compile

## 简介
**动态编译Java源代码并加载其字节码**

## Groovy方式动态编译加载
**基于Groovy实现：GroovyCompiler**

## JDK方式动态编译加载
- **基于JDK实现：JdkCompiler**
- **获取包与类名的两种方式**
  - RegexJavaSource：基于复杂的正则表达式
  - JavacJavaSource：基于Javac编译器的词法/语法分析功能

## 使用说明
To see [**`JavaSourceCompilerTest`**](https://github.com/ponfee/commons-compile/blob/master/src/test/java/code/ponfee/commons/compile/JavaSourceCompilerTest.java)
