package local.ding;

import com.sun.mirror.apt.AnnotationProcessor;
import com.sun.mirror.apt.AnnotationProcessorEnvironment;
import com.sun.mirror.declaration.MethodDeclaration;
import com.sun.mirror.declaration.Modifier;
import com.sun.mirror.declaration.ParameterDeclaration;
import com.sun.mirror.declaration.TypeDeclaration;
import net.mindview.util.ProcessFiles;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Observer;


@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
@interface ExtractInterface {
    String value();
}
@ExtractInterface("IMultiplier")
class Multiper {
    public int multiple(int x, int y) {
        int total = 0;
        for (int i = 0; i < x; i++) {
            total = add(total, y);
        }
        return total;
    }

    private int add(int a, int b) {return a + b;}

    public static void main(String[] args) {
        Multiper m = new Multiper();
        System.out.println("16 * 12 = " + m.multiple(16, 12));
    }
}

public class AnnotarionDemo {
    public static void main(String[] args) throws Exception {
        Class<HelloWorld> helloWorldClass = HelloWorld.class;
        Constructor c = helloWorldClass.getDeclaredConstructor();
        c.setAccessible(true);
        ((HelloWorld)c.newInstance()).fun();
    }
}
class InterfaceExtractProcessor implements AnnotationProcessor {
    private final AnnotationProcessorEnvironment env;

    private ArrayList<MethodDeclaration> interfaceMethods = new ArrayList();

    public InterfaceExtractProcessor(AnnotationProcessorEnvironment env) {
        this.env = env;
    }

    @Override
    public void process() {
        for (TypeDeclaration typeDecl: env.getSpecifiedTypeDeclarations()) {
            ExtractInterface annot = typeDecl.getAnnotation(ExtractInterface.class);
            if (annot == null) {
                break;
            }
            for (MethodDeclaration m : typeDecl.getMethods()) {
                if (m.getModifiers().contains(Modifier.PUBLIC) && !(m.getModifiers().contains(Modifier.STATIC)))
                    interfaceMethods.add(m);
            }
            if (interfaceMethods.size() > 0) {
                try {
                    PrintWriter writer = env.getFiler().createSourceFile(annot.value());
                    writer.println("package " + typeDecl.getPackage().getQualifiedName() + ";");
                    writer.println("public interface "+ annot.value() + " {");
                    for (MethodDeclaration m: interfaceMethods) {
                        writer.print("  public ");
                        writer.print(m.getReturnType() + " ");
                        writer.print(m.getSimpleName() + " (");
                        int i = 0;
                        for (ParameterDeclaration parm: m.getParameters()) {
                            writer.print(parm.getType() + " " + parm.getSimpleName());
                            if (++i < m.getParameters().size()) {
                                writer.print(", ");
                            }
                        }
                        writer.println(");");
                    }
                    writer.println("}");
                    writer.close();
                } catch (IOException ioe) {
                    throw new RuntimeException(ioe);
                }
            }
        }
    }
}

