package local.ding;

import com.sun.tools.javah.Gen;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

interface Generator <T> {
    T next();
}
class SimpleGenerator  {
    public static Random r = new Random(47);
    static class Integer implements  Generator<java.lang.Integer> {
        @Override
        public java.lang.Integer next() {
            return r.nextInt(1000);
        }
    }
}

class Generated {
    //生成类型的数据
    public static <T> T[] array(T[] a, Generator<T> gen) {
        for (int i = 0; i< a.length; i++) {
            a[i] = gen.next();
        }
        return a;
    }

    public static <T> T[] array(Class<T> type,Generator<T> gen, int size) {
        T[] a = (T[])Array.newInstance(type, size);
        for (int i = 0; i< a.length; i++) {
            a[i] = gen.next();
        }
        return a;
    }
}

class CompType implements Comparable<CompType> {
    int a;
    int b;
    CompType(int inta, int intb) {
        a = inta;
        b = intb;
    }

    @Override
    public String toString() {
        return "[b = "+a+", b = "+b+"]";
    }

    @Override
    public int compareTo(CompType o) {
        return a > o.a ? 1 : a == o.a ? 0 : -1;
    }
    private static Random r = new Random(47);
    public static Generator<CompType> getGenerator() {
        return new Generator<CompType>() {
            @Override
            public CompType next() {
                return new CompType(r.nextInt(100), r.nextInt(100));
            }
        };
    }
}

public class HelloWorld {
    public static void main(String[] args) {
       CompType[] arr = Generated.array(CompType.class, CompType.getGenerator(),10);
        System.out.println(Arrays.toString(arr));

       Arrays.sort(arr, new Comparator<CompType>() {
           @Override
           public int compare(CompType o1, CompType o2) {
               return Integer.compare(o2.a, o1.a);
           }
       });
        System.out.println(Arrays.toString(arr));
    }
}


