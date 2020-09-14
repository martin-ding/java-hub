package local.ding;

import net.mindview.util.CountingIntegerList;

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
        ExecutorService exec = Executors.newCachedThreadPool();
        exec.execute(new Runnable() {
            @Override
            public void run() {
                Thread t = Thread.currentThread();
                System.out.println(t.getName());
                System.out.println(t.getPriority());
                System.out.println(Thread.currentThread().getThreadGroup().getName());
                System.out.println(Thread.currentThread());
//                Thread.currentThread().toString();
            }
        });
        exec.shutdown();
        System.out.println(Thread.currentThread());

//        //测试lambda的使用 拉姆达
//        String str="haha";
//        Thread.sleep(1000);
//        (new Thread(() -> {System.out.println(str);})).start();

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

class DelayTask implements Runnable , Delayed {
    private static int counter = 0;
    private final int id = counter++;
    private final int delta;//延迟的时间
    private final long trigger;//什么时候触发
    protected static List<DelayTask> sequence = new ArrayList<>();

    public DelayTask(int delayInMilliseconds)
    {
        delta = delayInMilliseconds;
        trigger = System.nanoTime() + TimeUnit.NANOSECONDS.convert(delayInMilliseconds, TimeUnit.MILLISECONDS);
        sequence.add(this);
    }

    @Override
    public int compareTo(Delayed o) {
        DelayTask that = (DelayTask) o;
        if (trigger > that.trigger) { return 1;}
        if (trigger < that.trigger) { return -1;}
        return 0;
    }

    @Override
    public void run() {
        System.out.println(this + " ");
    }

    @Override
    public String toString() {
        return String.format("[%1$-4d]", delta) + " Task " + id;
    }

    public String summary()
    {
        return "(" + id + ":" + delta + ")";
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(trigger - System.nanoTime(), TimeUnit.NANOSECONDS);
    }

    public static class EndSentiel extends DelayTask {
        private ExecutorService exec;

        public EndSentiel(int delayInMilliseconds, ExecutorService e) {
            super(delayInMilliseconds);
            exec = e;
        }

        @Override
        public void run() {
            for (DelayTask pt: sequence) {
                System.out.println(pt.summary() + " ");
            }
            System.out.println();
            System.out.println(this + " Calling shutdownNow()");
            exec.shutdownNow();
        }
    }
}

class DelayedTaskConsumer implements Runnable {
    private DelayQueue<DelayTask> q;

    DelayedTaskConsumer(DelayQueue<DelayTask> q) {
        this.q = q;
    }

    @Override
    public void run() {
        try{
            while (!Thread.interrupted()) {
                q.poll().run();
            }
        } catch (Exception e) {
            System.out.println("-----");
        }
        System.out.println("Finished DelayedTaskConsumer");
    }

}

class DelayQueueDemo{
    ExecutorService exec = Executors.newCachedThreadPool();
    public static void main(String[] args) {
//        Random random = new Random(47);

//        DelayQueue<DelayTask> queue = new DelayQueue<>();
//        for (int i = 0; i < 20; i++) {
//            queue.put(new DelayTask(random.nextInt(5000)));
//        }
//        queue.add(new DelayTask.EndSentiel(5000, executorService));
//        executorService.execute(new DelayedTaskConsumer(queue));
//        executorService.shutdown();

//        new ScheduledThreadPoolExecutor()
//        Calendar lastTime = Calendar.getInstance();
//        System.out.println(lastTime.getTime());
//        lastTime.set(Calendar.MINUTE, 30);
//        System.out.println(lastTime.getTime());
//        new Exchanger().exchange();
//        CopyOnWriteArrayList

//        try {
//            return;
//        }finally {
//            System.out.println("===");
//        }

//        PriorityQueue p = new PriorityQueue();
//        AtomicInteger ai = new AtomicInteger(10);
//        ai.incrementAndGet();
//        System.out.println(ai);
        Random rand = new Random(47);


    }

    public Future<Integer> calculate(final int x, final int b) {
        return exec.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return x + b;
            }
        });
    }
}

class Exercise42{
    private static int carCount=0;
    private static int robotCount=0;
    private static List<ActiveCarRobot> robots=new ArrayList<ActiveCarRobot>();

    public class Car{
        private final int id=++carCount;
        private boolean waxOn=false;
        public void waxOn(){
            if(waxOn){System.out.println("Error, the wax already on!");return;}
            waxOn=true;
        }
        public void waxOff(){
            if(!waxOn){System.out.println("Error, should waxOn before waxOff!");return;}
            waxOn=false;
        }
        public String toString(){return "Car#"+id;}
    }

    public class ActiveCarRobot implements Runnable{
        private final int id=++robotCount;
        private final ExecutorService exec=Executors.newSingleThreadExecutor();	//必须是单线程执行器
        private List<Future<String>> results=new CopyOnWriteArrayList<Future<String>>();
        private Car car;
        public ActiveCarRobot(Car c){car=c;robots.add(this);}
        public String toString(){return "Robot#"+id;}

        public void run(){
            for(int i=0;i<10;i++){
                results.add(waxOn());
                sleep(10);
                results.add(waxOff());
            }
            showResults();
            shutdown();
        }
        public Future<String> waxOn(){
            return exec.submit(new Callable<String>(){	//把waxOn的动作封装成一个Callable对象，被提交给消息队列
                public String call(){
//                    sleep(10);
                    car.waxOn();
                    return "    "+car+" wax on by "+ActiveCarRobot.this;
                }
            });
        }
        public Future<String> waxOff(){
            return exec.submit(new Callable<String>(){	//把waxOff的动作封装成一个Callable对象，被提交给消息队列
                public String call(){
//                    sleep(10);
                    car.waxOff();
                    return "    "+car+" wax off by "+ActiveCarRobot.this;
                }
            });
        }
        public void sleep(int time){
            try{
                TimeUnit.MILLISECONDS.sleep(time);
            }catch(InterruptedException ie){
                System.out.println(this+" interrupted!");
            }
        }
        public void shutdown(){exec.shutdownNow();}
        public void showResults(){
            long endAt=System.currentTimeMillis()+5000;
            while(true){
                for(Future<String> f:results){
                    if(f.isDone()){
                        try{
                            System.out.println(f.get());
                        }catch(Exception e){
                            System.out.println("Error when reading the results!");
                        }
                    }
                    results.remove(f);
                }
                if(System.currentTimeMillis()>=endAt){break;}
            }
        }
    }

    public static void main(String[] args){
        Exercise42 test=new Exercise42();
        ExecutorService exec=Executors.newCachedThreadPool();
        for(int i=0;i<10;i++){
            exec.execute(test.new ActiveCarRobot(test.new Car()));
        }
        try{
            TimeUnit.SECONDS.sleep(5);
        }catch(InterruptedException ie){
            System.out.println("Test interrupted!");
        }
        exec.shutdownNow();
    }
}

