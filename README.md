# Dynamic compiler java source code

## Introduction
Java源代码动态编译并加载为Java Class对象

## Implementation

### 基于Groovy方式动态编译
- GroovyCompiler

### 基于JDK方式动态编译
- JdkCompiler
- 获取包与类名的两种方式
  - RegexJavaSource：基于复杂的正则表达式提取
  - JavacJavaSource：基于jdk tools.jar的Javac编译器功能

## Usage
To see [`JavaSourceCompilerTest`](src/test/java/cn/ponfee/commons/compile/JavaSourceCompilerTest.java)
