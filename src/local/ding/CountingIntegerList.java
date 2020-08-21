package local.ding;

import java.util.*;

public class CountingIntegerList extends AbstractList<Integer> {
    private int size;
    CountingIntegerList(int size) {
        this.size = size;
    }

    public int size() {
        return size;
    }
    public Integer get(int size) {
        return Integer.valueOf(size);
    }

    public static void main(String[] args) {
//        List<String> list = new ArrayList<String>();
//        ListIterator<String> it = list.listIterator();
//        it.add("abc");
////        it.next();
//        it.add("NAA");
////        it.add("HAS");
//        System.out.println(list);
//        associativeArrayTest();
        slowMapDemo();
    }

    static void testLinkHash() {
        SortedSet<String> set =  new TreeSet();
        Collections.addAll(set, "one two three four five six seven eight". split(" "));
        System.out.println(set);
        String low = set.first();
        String high = set.last();
        System.out.println(low +  " "  + high);
        Iterator<String> it = set.iterator();
        for (int i = 0; i<= 6; i++) {
            if(i == 3) {low = it.next();}
            if(i == 6) {
                System.out.println("-----");
                high = it.next();}
            else System.out.println( i + "===" + it.next());
        }
        System.out.println(low +  " "  + high);
    }

    static void associativeArrayTest() {
        AssociativeArray<String, String> map = new AssociativeArray(6);
        map.put("zhangsan", "100");
        map.put("liassdas", "900");
        map.put("asdas", "243");
        map.put("erfew", "23");
        map.put("afwe", "1002");
        System.out.println(map);
    }

    static void slowMapDemo()
    {
        SlowMap<String,String> map = new SlowMap<>();
        map.put("zhangsan", "100");
        map.put("lisi", "100");
        map.put("wangwu", "100");
        System.out.println(map.entrySet());
    }
}


class AssociativeArray <K, V>{
    public Object[][] pairs;
    private int index;
    AssociativeArray(int length) {
        pairs = new Object[length][2];
    }

    public void put(K key, V value) {
        if (index >= pairs.length) {
            throw new ArrayIndexOutOfBoundsException();
        }
        pairs[index++] = new Object[] {key, value};
    }

    public V get(K key) {
        for (int i = 0; i< index; i++) {
            if (pairs[i][0].equals(key)) {
                return (V)pairs[i][1];
            }
        }
        return null;
    }

    public String toString() {
        StringBuilder result =  new StringBuilder();
        for (int i = 0; i< index; i++) {
            result.append(pairs[i][0].toString());
            result.append(":");
            result.append(pairs[i][1].toString());
            if (i < index-1) {
                result.append("\n");
            }
        }
        return result.toString();
    }
}

class MapEntry<K,V> implements Map.Entry<K,V> {
    K key;
    V value;
    MapEntry(K key, V value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public K getKey() {
        return key;
    }

    @Override
    public V getValue() {
        return value;
    }

    @Override
    public V setValue(V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int hashCode() {
        System.out.println("----");
//        return super.hashCode();
        return key == null ? 0: key.hashCode() ^ (value==null ? 0 : value.hashCode());
    }
    public boolean equals(Object o) {
        System.out.println("++++");
        if (!(o instanceof MapEntry)) return false;
        MapEntry me = (MapEntry) o;
        return (key == null? me.getKey() == null : key.equals(me.getKey())) &&
                (value == null? me.getValue() == null : value.equals(me.getValue()));
    }

    public String toString(){
        return key + " = " + value;
    }
}

class SlowMap<K,V> extends AbstractMap<K,V> {
    private List<K> keys =  new ArrayList();
    private List<V> values =  new ArrayList();

    public V put(K key, V value) {
        V oldvalue = get(key);
        if (!keys.contains(key)) {
            keys.add(key);
            values.add(value);
        } else {
            values.set(keys.indexOf(key), value);
        }
        return oldvalue;
    }

    @Override
    public V get(Object key) {
        if (!keys.contains(key))
            return null;
        return values.get(keys.indexOf(key));
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        Set<Map.Entry<K,V>> set = new HashSet<Map.Entry<K, V>>();
        Iterator<K> ki = keys.iterator();
        Iterator<V> vi = values.iterator();
        while (ki.hasNext()) {
           set.add(new MapEntry<K,V>(ki.next(), vi.next()));
        }
        return set;
    }
}
