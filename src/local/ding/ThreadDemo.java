package local.ding;

import java.io.IOException;
import java.nio.channels.Channel;
import java.nio.channels.Channels;
import java.nio.channels.SocketChannel;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;

public class ThreadDemo {
    static class Liftup implements Runnable {
        protected int countDown = 10;
        static int num = 0;
        private final int id = num++;

        public Liftup() {
        }

        public Liftup(int countDown) {
            this.countDown = countDown;
        }

        public String status() {
            return "#" + id + "(" + (countDown > 0 ? countDown : "liftoff!") + "), ";
        }

        @Override
        public void run() {
            while (countDown-- > 0) {
                System.out.println(status());
                Thread.yield();
            }
        }
    }

    public static void main(String[] args) throws Exception {
//        for (int i = 0; i < 5; i++) {
//            new Thread(new Liftup(5)).start();
//        }
//        int i = 1;
//        ExecutorService exec = Executors.newSingleThreadExecutor();
//        Future f = exec.submit(new Callable<String>() {
//            @Override
//            public String call() throws Exception {
//
//                return "null";
//            }
//        });
//        System.out.println(f.get());
////        for (int i = 0; i < 5; i++) {
//
////        }
//        exec.shutdown();


        new Thread();
       Thread t =  new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    System.out.println("start run");
                    TimeUnit.MILLISECONDS.sleep(1000);
                } catch (Exception e) {

                } finally {
                    System.out.println("This should always call");
                }
            }
        });
       t.setDaemon(true);
       t.start();

    }


}

interface InterfaceTest {
    void aaa();
}

class CommonClass implements InterfaceTest {
    public CommonClass() {
    }

    //想要用lambda通过new普通类实现接口方法的实现需要写此含接口的构造器
    public CommonClass(InterfaceTest interfaceTest) {
    }

    public void ccc() {
        System.out.println("cccccc");
    }

    @Override
    public void aaa() {
        // TODO Auto-generated method stub
        System.out.println("普通类从接口继承");
    }

    public void bbb() {

    }
}

class TestDemoA {
//    public void method() {
//        new CommonClass(new CommonClass() {
//            public void aaa() {
//                // TODO Auto-generated method stub
//                System.out.println("absasasa");
//            }
//        });
//    }
//
//    int m = 12;
//    public void method2() {
//        int mm = 12;
//        InterfaceTest test =  () -> { m = 11 ; System.out.println(mm);};
//        InterfaceTest test1 = new InterfaceTest() {
//            @Override
//            public void aaa() {
//                System.out.println(mm);
//            }
//        };
//        test.aaa();
//        System.out.println(m);
//    }

    public static void main(String[] args) throws InterruptedException {
        //测试lambda的使用 拉姆达
        String str="haha";
        Thread.sleep(1000);
        (new Thread(() -> {System.out.println(str);})).start();

//       new TestDemoA().method2();
//        List<Integer> list = new ArrayList<>();
//        list.addAll(Arrays.asList(12, 31, 2, 1, 32));
//        list.forEach(new Consumer<Integer>() {
//            @Override
//            public void accept(Integer d1) {
//                if (d1 > 10) System.out.println(d1);
//                System.out.println("-------");
//            }
//        });
    }

}

class SimpleThread extends Thread {
    private int countDown = 10;
    private static int threadCount = 0;
    public SimpleThread() {
        super(Integer.toString(threadCount++));
        start();
    }

    @Override
    public String toString() {
        return "#" + getName() + "("+ countDown +")";
    }

    @Override
    public void run() {
        while (true) {
            System.out.println(this);
            if (--countDown == 0) {
                return;
            }
        }
    }

    public static void main(String[] args) {
        for (int i =0; i < 5; i++)
            new SimpleThread();
    }
}


class UncaughtException implements Thread.UncaughtExceptionHandler {
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.out.println("caught default "+ e);
    }
}

class UncaughtExceptionOrig implements Thread.UncaughtExceptionHandler {
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.out.println("caught origin "+ e);
    }
}

class ExceptionThread extends Thread
{
    public ExceptionThread (){
//       super.setUncaughtExceptionHandler(new UncaughtExceptionOrig());
        System.out.println("### ehandler : " + this.getUncaughtExceptionHandler());
    }
    @Override
    public void run() {
        throw new RuntimeException("message ------");
    }
}

class SettingDefaultHandler implements Runnable{
    private AtomicInteger ii = new AtomicInteger(0);
    public volatile static int i = 0;
    public  int getValue() {return i;}
    private synchronized void evenIncrement() {
        ii.get();
        i++;
        Thread.yield();
        try {
            TimeUnit.MILLISECONDS.sleep(100);
        } catch (Exception e ) {
            ExecutorService exec =  Executors.newCachedThreadPool();
            exec.shutdown();
        }
        i++;
    }
    public static void main(String[] args) throws InterruptedException {
        //interrupt
//        Thread.interrupted();
        new SettingDefaultHandler().wait();


        // handler
//        ExecutorService exec = Executors.newCachedThreadPool();
//        SettingDefaultHandler h = new SettingDefaultHandler();
//        exec.execute(h);
//        while (true) {
//            int val = h.getValue();
//            if (val %2 != 0) {
//                System.out.println("val:" + val);
//                System.exit(0);
//            }
//        }



//        try {
//            ExecutorService exec = Executors.newCachedThreadPool();
//            Thread t = new ExceptionThread();
//            t.setUncaughtExceptionHandler(new UncaughtExceptionOrig());
////        Thread.setDefaultUncaughtExceptionHandler(new UncaughtException());
////            exec.execute(t);
//            System.out.println("#" + t.getUncaughtExceptionHandler());
//            System.out.println("#" + t.getDefaultUncaughtExceptionHandler());
//            t.start();
////            exec.shutdown();
//        }catch (Exception e) {
//            System.out.println("+++++" + e);
//        }

//        ReentrantLock lock = new ReentrantLock();
//        lock.lock();

    }

    @Override
    public void run() {
        while (true)
            evenIncrement();
    }
}

class ThreadId {
    // Atomic integer containing the next thread ID to be assigned
    private static final AtomicInteger nextId = new AtomicInteger(0);

    // Thread local variable containing each thread's ID
    private static final ThreadLocal<Integer> threadId =
            new ThreadLocal<Integer>() {
                @Override protected Integer initialValue() {
                    return nextId.getAndIncrement();
                }
            };

    private static final int threadIdN = nextId.getAndIncrement();

    // Returns the current thread's unique ID, assigning it if necessary
    public static int get() {
//        return threadId.get();
        return threadIdN;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            new Thread(new Runnable() {
                public void run() {
                    System.out.println("thread" + Thread.currentThread() + " ; Thread id:" + threadIdN +" another call" + threadIdN);
                }
            }).start();
        }
    }
}



class ThreadStateTest {
    static class Run implements Runnable {
        volatile boolean aBoolean = false;
        @Override
        public void run() {
            try {
                System.in.read();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args) {
        Thread t = new Thread(new Run());
        t.start();
        int i = 0;
//        while (++i < 100000 ) {
//            System.out.println(t.getState());
//        }
    }
}