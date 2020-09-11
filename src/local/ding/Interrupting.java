package local.ding;//: concurrency/Interrupting.java
// Interrupting a blocked thread.
import java.util.concurrent.*;
import java.io.*;
import static net.mindview.util.Print.*;

class SleepBlocked implements Runnable {
  public void run() {
    try {
      TimeUnit.SECONDS.sleep(100);
    } catch(InterruptedException e) {
      print("InterruptedException");
    }
    print("Exiting SleepBlocked.run()");
  }
}

class IOBlocked implements Runnable {
  private InputStream in;
  public IOBlocked(InputStream is) { in = is; }
  public void run() {
    try {
      print("Waiting for read():");
      System.out.println("----------");
      int a = in.read();
      System.out.println("read :" + a);
    } catch(IOException e) {
      System.out.println("----------");
      if(Thread.currentThread().isInterrupted()) {
        print("Interrupted from blocked I/O");
      } else {
        System.out.println("+++++");
        throw new RuntimeException(e);
      }
    }
    print("Exiting IOBlocked.run()");
  }
}

class SynchronizedBlocked implements Runnable {
  public synchronized void f() {
    while(true) // Never releases lock
      Thread.yield();
  }
  public SynchronizedBlocked() {
    new Thread() {
      public void run() {
        f(); // Lock acquired by this thread
      }
    }.start();
  }
  public void run() {
    print("Trying to call f()");
    f();
    print("Exiting SynchronizedBlocked.run()");
  }
}

public class Interrupting {
  private static ExecutorService exec =
    Executors.newCachedThreadPool();

  static void test(Runnable r) throws InterruptedException{
    exec.shutdown();
    Future<?> f = exec.submit(r);
    TimeUnit.MILLISECONDS.sleep(100);
    print("Interrupting " + r.getClass().getName());
    f.cancel(true); // Interrupts if running
    print("Interrupt sent to " + r.getClass().getName());
  }
  public static void main(String[] args) throws Exception {
    test(new SleepBlocked());
    test(new IOBlocked(System.in));
    test(new SynchronizedBlocked());
    TimeUnit.SECONDS.sleep(3);
    print("Aborting with System.exit(0)");
    System.exit(0); // ... since last 2 interrupts failed
  }
} /* Output: (95% match)
Interrupting SleepBlocked
InterruptedException
Exiting SleepBlocked.run()
Interrupt sent to SleepBlocked
Waiting for read():
Interrupting IOBlocked
Interrupt sent to IOBlocked
Trying to call f()
Interrupting SynchronizedBlocked
Interrupt sent to SynchronizedBlocked
Aborting with System.exit(0)
*///:~


// Java program to illustrate the
// behaviour of notify() method
class Geek1 extends Thread {
  public void run()
  {
    synchronized(this)
    {
      System.out.println
              (Thread.currentThread().getName() + "...starts");
      try {
        wait();
      }
      catch (InterruptedException e) {
        e.printStackTrace();
      }
      System.out.println
              (Thread.currentThread().getName() + "...notified");
    }
  }
}

class Geek2 extends Thread {
  Geek1 geeks1;
  Geek2(Geek1 geeks1)
  {
    this.geeks1 = geeks1;
  }
  public void run()
  {
    synchronized(geeks1)
    {
      System.out.println
              (Thread.currentThread().getName() + "...starts");

      try {
        geeks1.wait();
      }
      catch (InterruptedException e) {
        e.printStackTrace();
      }
      System.out.println
              (Thread.currentThread().getName() + "...notified");
    }
  }
}
class Geek3 extends Thread {
  Geek1 geeks1;
  Geek3(Geek1 geeks1)
  {
    this.geeks1 = geeks1;
  }
  public void run()
  {
    synchronized(geeks1)
    {
      System.out.println
              (Thread.currentThread().getName() + "...starts");
      geeks1.notify();
      System.out.println
              (Thread.currentThread().getName() + "...notified");
    }
  }
} class MainClass {
  public static void main(String[] args) throws InterruptedException
  {

    Geek1 geeks1 = new Geek1();
    Geek2 geeks2 = new Geek2(geeks1);
    Geek3 geeks3 = new Geek3(geeks1);
    Thread t1 = new Thread(geeks1, "Thread-1");
    Thread t2 = new Thread(geeks2, "Thread-2");
    Thread t3 = new Thread(geeks3, "Thread-3");
    t1.start();
    t2.start();
    Thread.sleep(100);
    t3.start();
  }
}
