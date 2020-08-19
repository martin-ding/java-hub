package local.ding;

import java.lang.reflect.Array;
import java.util.Arrays;
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

public class HelloWorld {
    public static void main(String[] args) {
        java.lang.Integer[] a = Generated.array(java.lang.Integer.class, new SimpleGenerator.Integer(), 10);
        System.out.println(Arrays.toString(a));
    }
}


