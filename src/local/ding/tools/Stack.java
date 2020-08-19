package local.ding.tools;

import java.util.LinkedList;

public class Stack<T> {
    private LinkedList<T> storage = new LinkedList();
    public void push(T v) { storage.push(v);}
    public T pop() {return storage.pop();}
    public T peek() {return storage.peek();}
    public boolean empty() {return storage.isEmpty(); }

    public String toString() {
        return storage.toString();
    }
}
