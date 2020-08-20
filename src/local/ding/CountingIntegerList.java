package local.ding;

import java.util.AbstractList;

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
        System.out.println(new CountingIntegerList(10));
    }
}
