package local.ding;

import com.sun.xml.internal.xsom.impl.scd.Iterators;

import java.lang.annotation.*;
import java.lang.reflect.*;
import java.util.*;

import static junit.framework.TestCase.assertTrue;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@interface Test{}

public class AnnotarionDemo {
    class Testable {
        void execute() {
            System.out.println("Executing....");
        }
        @Test void testExecute() {execute();}

    }

    public void demo()
    {}

    @Upcase(id = 12, desc = "zhasn")
    public void  demoUpcase()
    {

    }

    @Upcase(id = 1211)
    public void  demoUpcase1()
    {

    }

}

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@interface Upcase{
    int id();
    String desc() default "desc";
}

//class UpCaseTracker {
//    public static void trackUseCases(List<Integer> useCases, Class<?> cl)
//    {
//        for (Method m:cl.getDeclaredMethods()) {
//            Upcase uc = m.getAnnotation(Upcase.class);
//            if (uc != null) {
//                System.out.println("Found Use Case:" + uc.id() + " " + uc.desc());
//                useCases.remove(new Integer(uc.id()));
//            }
//        }
//        for (int i: useCases) {
//            System.out.println("Warning :Missing use case-" + i);
//        }
//    }
//
//    public static void main(String[] args) {
//        List<Integer> useCases = new ArrayList<>();
//        Collections.addAll(useCases, 12, 1211, 1);
//        trackUseCases(useCases, AnnotarionDemo.class);
//        MethodDeclaration
//    }
//
//
//}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
@interface IsInheritedAnnotation {
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@interface NoInherritedAnnotation {
}

@NoInherritedAnnotation
@IsInheritedAnnotation
class InheritedBase {
}

class MyInheritedClass extends InheritedBase  {
}

//接口
@NoInherritedAnnotation
@IsInheritedAnnotation
interface IInheritedInterface {
}

interface IInheritedInterfaceChild extends IInheritedInterface {
}

class MyInheritedClassUseInterface implements IInheritedInterface {}

class MyInheritedClassTest {

    @Test
    public static void testInherited() {
        {
            Annotation[] annotations = MyInheritedClass.class.getAnnotations();
            assertTrue("", Arrays.stream(annotations).anyMatch(l -> l.annotationType().equals(IsInheritedAnnotation.class)));
            assertTrue("", Arrays.stream(annotations).noneMatch(l -> l.annotationType().equals(NoInherritedAnnotation.class)));
        }
        {
            Annotation[] annotations = MyInheritedClassUseInterface.class.getAnnotations();
            assertTrue("", Arrays.stream(annotations).noneMatch(l -> l.annotationType().equals(IsInheritedAnnotation.class)));
            assertTrue("", Arrays.stream(annotations).noneMatch(l -> l.annotationType().equals(NoInherritedAnnotation.class)));
        }
        {
            Annotation[] annotations = IInheritedInterface.class.getAnnotations();
            assertTrue("", Arrays.stream(annotations).anyMatch(l -> l.annotationType().equals(IsInheritedAnnotation.class)));
            assertTrue("", Arrays.stream(annotations).anyMatch(l -> l.annotationType().equals(NoInherritedAnnotation.class)));
        }
        {
            Annotation[] annotations = IInheritedInterfaceChild.class.getAnnotations();
            assertTrue("", Arrays.stream(annotations).noneMatch(l -> l.annotationType().equals(IsInheritedAnnotation.class)));
            assertTrue("", Arrays.stream(annotations).noneMatch(l -> l.annotationType().equals(NoInherritedAnnotation.class)));
        }
    }

    private int doaas() {
        System.out.println("------");
        return 1;
    }
    public void setName(String na) {
        this.name = na;
    }
    public void getName()
    {
//        System.out.println(name);
    }

    private String name;

    public <E> void  mmp(Map<String, Integer>[] map, List<E> list, Set<? extends MyInheritedClass> sets, String[] a) {
        System.out.println("genetic parameter");
    }

    public Map<String, Integer> sample() {
        System.out.println("genetic return");
        return null;
    }

    public static void main(String[] args) throws Exception {
//        long start = System.currentTimeMillis();
//        Class c = Class.forName("local.ding.MyInheritedClassTest");
//        MyInheritedClassTest test = (MyInheritedClassTest)c.newInstance();
//        Method m = c.getDeclaredMethod("setName", String.class);
//        Method m1 = c.getDeclaredMethod("getName");
//        for (int i = 0; i< 1000000; i++) {
//            m.invoke(test, "hello world");
//            m1.invoke(test);
//        }
//        long end = System.currentTimeMillis();
//        System.out.println("reflect spend time" + (end - start));
//
//        start = System.currentTimeMillis();
//        MyInheritedClassTest test1 = new MyInheritedClassTest();
//        for (int i = 0; i< 1000000; i++) {
//            test1.setName("hello world");
//            test1.getName();
//        }
//        end = System.currentTimeMillis();
//        System.out.println("normal spend time" + (end - start));
        List<String[]> list = new ArrayList();
        Class c  = Class.forName("local.ding.MyInheritedClassTest");
        Method m = c.getDeclaredMethod("mmp", Map[].class, List.class, Set.class, String[].class);
        for (Type t :m.getGenericParameterTypes()) {
//            if (t instanceof Class) {//原始类型输出 class java.lang.String
            if (t instanceof GenericArrayType) {
                System.out.println("######" + t);
                 t.getTypeName();

                continue;
//                Type[] as = ((ParameterizedType) t). getActualTypeArguments();
////                System.out.println(Arrays.toString(as));
//                for (Type a : as) {
//                    if (a instanceof Class) {
//                        System.out.println(a);
//                    }
//                }
////                    if (a instanceof WildcardType){
////                        Type[] maa = ((WildcardType) a).getUpperBounds();
////                        System.out.println(Arrays.toString(maa));
////
////                        Type[] man = ((WildcardType) a).getLowerBounds();
////                        System.out.println(Arrays.toString(man));
////                    }
////                }
//                for (Type a: as) {
//                    if (a instanceof GenericArrayType) {
//                        System.out.println(Arrays.toString(((TypeVariable)a).getBounds()));
//                    }
                }
            }
//
////            if (t instanceof GenericArrayType) {
////                Type ma = ((GenericArrayType) t).getGenericComponentType();
////                System.out.println(ma);
////            }
//
//
        }

//        class Mapp<K extends String, V extends MyInheritedClass & I> extends AbstractMap<K,V> {
//
//            @Override
//            public Set<Entry<K, V>> entrySet() {
//                return null;
//            }
//        }

//    }
}

class AB {
    private int age;
    private String name;

    public AB(int age, String name) {
        this.age = age;
        this.name = name;
    }
}




