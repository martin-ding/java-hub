package local.ding;

import java.util.*;

public class Countries {
    public static final String[][] DATA = {
            {"ABD","lakd"},{"AHSA","akjs"}, {"HAS", "gtgr"},
            {"HWK","da"},{"KHW","sf"}, {"BJW", "gegr"},
            {"LWQ","asdas"},{"ONQ","sdfs"}, {"PWD", "tyrgt"},
    };

    private static class FlyWeightMap extends AbstractMap<String, String> {
        private static class Entry implements Map.Entry<String,String> {
            int index;
            Entry(int index) {this.index = index;}
            public boolean equals(Object o) {
                return DATA[index][0].equals(o);
            }

            @Override
            public String getKey() {
                return DATA[index][0];
            }

            @Override
            public String getValue() {
                return DATA[index][1];
            }

            @Override
            public String setValue(String value) {
                throw new UnsupportedOperationException();
            }

            public int hashCode() {
                return DATA[index][0].hashCode();
            }
        }

        static class EntrySet extends AbstractSet<Map.Entry<String,String>> {
            private int size;
            EntrySet(int size) {
                if (size < 0) {
                    this.size = 0;
                } else if (size > DATA.length) {
                    this.size = DATA.length;
                } else {
                    this.size = size;
                }
            }

            public int getSize() {
                return size;
            }

            private class Iter implements Iterator<Map.Entry<String, String>> {
                private Entry entry = new Entry(-1);
                @Override
                public boolean hasNext() {
                    return entry.index < size - 1;
                }

                @Override
                public Map.Entry<String, String> next() {
                    entry.index++;
                    return entry;
                }
            }


            @Override
            public Iterator<Map.Entry<String, String>> iterator() {
                return new Iter();
            }

            @Override
            public int size() {
                return 0;
            }
        }

        private static Set<Map.Entry<String,String>> entries = new EntrySet(DATA.length);

        @Override
        public Set<Map.Entry<String, String>> entrySet() {
            return entries;
        }
    }

    static Map<String, String> select(int size) {
        return new FlyWeightMap() {
            public Set<Map.Entry<String,String>> entrySet() {
                return new EntrySet(size);
            }
        };
    }

    static Map<String, String> map = new FlyWeightMap();
    public static Map<String, String> capitals() {
        return map;
    }

    public static Map<String, String> capitals(int size) {
        return select(size);
    }

    static List<String> names = new ArrayList(map.keySet());
    public static List<String> names() {
        return names;
    }

    public static List<String> names(int size) {
        return new ArrayList(select(size).keySet());
    }

    public static void main(String[] args) {
        Map<String, String> maps = capitals(10);
        System.out.println(capitals().get("BJW"));
    }
}
