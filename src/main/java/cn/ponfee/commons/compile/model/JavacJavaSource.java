package cn.ponfee.commons.compile.model;

import com.sun.source.tree.ClassTree;
import com.sun.source.util.TreeScanner;
import com.sun.tools.javac.file.JavacFileManager;
import com.sun.tools.javac.parser.Parser;
import com.sun.tools.javac.parser.ParserFactory;
import com.sun.tools.javac.tree.JCTree.JCCompilationUnit;
import com.sun.tools.javac.util.Context;

import javax.lang.model.element.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static javax.lang.model.element.Modifier.*;

/**
 * <pre>
 *   <dependency>
 *     <groupId>cn.pivoto.jdk</groupId>
 *     <artifactId>tools</artifactId>
 *     <version>1.8</version>
 *     <scope>provided</scope>
 *   </dependency>
 * </pre>
 * 
 * 基于jdk tools jar
 * 
 * @author Ponfee
 */
public class JavacJavaSource extends JavaSource {
    private static final long serialVersionUID = 8020419352084840057L;

    public JavacJavaSource(String sourceCode) {
        super(sourceCode);

        Context context = new Context();
        JavacFileManager.preRegister(context);
        Parser parser = ParserFactory.instance(context).newParser(sourceCode, true, false, true);
        JCCompilationUnit unit = parser.parseCompilationUnit();

        super.packageName = unit.getPackageName().toString();
        new SourceVisitor().visitCompilationUnit(unit, this);
        if (super.publicClass == null || super.publicClass.isEmpty()) {
            throw new IllegalArgumentException("illegal source code, public class not found.");
        }
    }

    /**
     * 最外围类（class TD）总在最后
     * 
     * <pre>
     * public final class TD {
     *     public void say() {}
     * 
     *     public class TA {
     *         public String hello() {
     *             return "hello";
     *         }
     *     }
     * 
     *     public class TB {
     *         public String hello() {
     *             return "hello";
     *         }
     *     }
     * 
     *     public class TV {
     *     }
     * 
     *     public class TF {
     *         public String hello() {
     *             return "hello";
     *         }
     *     }
     * 
     *     public class TC {
     *     }
     * }
     * 
     * class D {
     * }
     * </pre>
     * 源码访问类
     */
    private static class SourceVisitor extends TreeScanner<Void, JavaSource> {
        private static final List<Modifier> MODIFIERS = Arrays.asList(PUBLIC, FINAL, ABSTRACT, STRICTFP);

        @Override
        public Void visitClass(ClassTree classtree, JavaSource source) {
            super.visitClass(classtree, source);
            Set<Modifier> modifiers = classtree.getModifiers().getFlags();
            if (modifiers.contains(PUBLIC) && modifiers.size() <= 3
                && MODIFIERS.containsAll(modifiers)) {
                source.publicClass = classtree.getSimpleName().toString();
            }
            return null;
        }
    }

}
