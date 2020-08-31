package local.ding;

import java.lang.reflect.Array;
import java.util.*;

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
    public static class Character implements Generator<java.lang.Character> {
        char[] chars= "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        @Override
        public java.lang.Character next() {
            return chars[r.nextInt(chars.length)];
        }
    }

    public static class String implements Generator<java.lang.String> {
        int length = 6;
        String() {

        }
        String(int len) {
            length = len;
        }
        @Override
        public java.lang.String next() {
            char[] buf = new char[length];
            Generator<java.lang.Character> gc = new Character();
            for (int i =0 ; i < length; i ++) {
                buf[i] = gc.next();
            }
            return new java.lang.String(buf);
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
    private HelloWorld(){}

    protected void fun()
    {
        System.out.println("asdasdas");
    }
    public static void main(String[] args) {
//       CompType[] arr = Generated.array(CompType.class, CompType.getGenerator(),10);
//        System.out.println(Arrays.toString(arr));
//
//       Arrays.sort(arr, new Comparator<CompType>() {
//           @Override
//           public int compare(CompType o1, CompType o2) {
//               return Integer.compare(o2.a, o1.a);
//           }
//       });
//        System.out.println(Arrays.toString(arr));
//        java.lang.String[] arrstr = Generated.array(java.lang.String.class, new SimpleGenerator.String(), 10);
//        System.out.println(Arrays.toString(arrstr));
//        Arrays.sort(arrstr, String.CASE_INSENSITIVE_ORDER);
//        System.out.println(Arrays.toString(arrstr));
//        System.out.println("+++" + Arrays.binarySearch(arrstr, "anTcQw", String.CASE_INSENSITIVE_ORDER));
        Generator<Pair<Integer, String>> gen = new Generator<Pair<Integer, String>>() {
            @Override
            public Pair<Integer, String> next() {
                Generator<Integer> igen = new SimpleGenerator.Integer();
                Generator<String> sgen = new SimpleGenerator.String();
                return new Pair<Integer, String>(igen.next(), sgen.next());
            }
        };
        Map<java.lang.Integer, java.lang.String> maps = new HashMap(MapData.map(gen, 10));
        Map<Integer, String> maps2 = new HashMap<>();
        maps2.putAll(MapData.map(gen, 10));
        for (Map.Entry entry: maps2.entrySet()) {
            System.out.println(entry.getKey().getClass() +  " " + entry.getValue().getClass());
        }
    }
}

class Pair<K,V> {
    public final K key;
    public final V value;
    public Pair(K k, V v) {
        key = k;
        value = v;
    }
}

class MapData<K,V> extends LinkedHashMap<K,V> {
    MapData(Generator<Pair<K,V>> gen, int quality) {
        for (int i = 0; i < quality; i++) {
            Pair<K,V> pair = gen.next();
            put(pair.key, pair.value);
        }
    }

    public static <K,V> MapData<K,V> map(Generator<Pair<K,V>> gen, int quality) {
        return new MapData(gen, quality);
    }
}




