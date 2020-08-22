package local.ding;

import java.lang.ref.*;
import java.util.LinkedList;
import java.util.regex.Pattern;

class VeryBig {
    private static final int SIZE = 10000;
    private long[] la = new long[SIZE];
    private String ident;
    public VeryBig(String id) {
        ident = id;
    }
    public String toString()
    {
        return ident;
    }
    protected void finalize() {
        System.out.println("Finalizing " + ident);
    }
}

public class ReferenceDemo {
    private static ReferenceQueue<VeryBig> rq = new ReferenceQueue<>();
    public static void checkQueue()
    {
        Reference<? extends VeryBig> inq = rq.poll();
        if (inq != null) {
            System.out.println("In queue :" + inq.get());
        }
    }

    public static void main(String[] args) {
//        int size = 10;
//        LinkedList<SoftReference<VeryBig>> sa = new LinkedList<SoftReference<VeryBig>>();
//        for (int i = 0; i< size; i++) {
//            sa.add(new SoftReference<VeryBig>(new VeryBig("Soft "+ i), rq));
//            System.out.println("Just created " + sa.getLast());
//            checkQueue();
//        }
//
//        LinkedList<WeakReference<VeryBig>> wa = new LinkedList<WeakReference<VeryBig>>();
//        for (int i = 0; i< size; i++) {
//            wa.add(new WeakReference<VeryBig>(new VeryBig("Weak "+ i), rq));
//            System.out.println("Just created " + wa.getLast());
////            checkQueue();
//        }
//
//
//        LinkedList<PhantomReference<VeryBig>> pa = new LinkedList<PhantomReference<VeryBig>>();
//        for (int i = 0; i< size; i++) {
//            pa.add(new PhantomReference<VeryBig>(new VeryBig("Phantom "+ i), rq));
//            System.out.println("Just created " + pa.getLast());
////            checkQueue();
//        }
//
//        LinkedList<VeryBig> paa = new LinkedList<>();
//        for (int i = 0; i< size; i++) {
//            paa.add(new VeryBig("General "+ i));
//            System.out.println("Just created " + paa.getLast());
////            checkQueue();
//        }
//        System.gc();

        String pattern = ".*\\.java";
        System.out.println(Pattern.compile(pattern).matcher("demo.java").matches());
    }
}
