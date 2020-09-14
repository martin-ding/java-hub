package local.ding;//: concurrency/Restaurant.java
// The producer-consumer approach to task cooperation.
import java.sql.Time;
import java.util.Queue;
import java.util.concurrent.*;
import static net.mindview.util.Print.*;

class Meal {
  private final int orderNum;
  public Meal(int orderNum) { this.orderNum = orderNum; }
  public String toString() { return "Meal " + orderNum; }
}

class WaitPerson implements Runnable {
  private Restaurant restaurant;
  public WaitPerson(Restaurant r) { restaurant = r; }
  public void run() {
    try {
      while(!Thread.interrupted()) {

        Meal meal = null;
        synchronized(restaurant) {
          System.out.println(Thread.currentThread() + " enter in");
          while(restaurant.getFull() <= 0) {
            System.out.println("waiter " + Thread.currentThread() + " waiting ...");
            restaurant.wait(); // ... for the chef to produce a meal
          }

          meal = restaurant.meals.poll();
          //设置数据
          int num = restaurant.getEmpty();
          restaurant.setEmpty(num + 1);
          num =  restaurant.getFull();
          restaurant.setFull(num - 1);

          System.out.println("consume -- full :" + restaurant.getFull() +" empty :" + restaurant.getEmpty());

          restaurant.notifyAll(); // Ready for another
          print(Thread.currentThread() + " Waitperson got " + meal);
        }
      }
    } catch(InterruptedException e) {
      print("WaitPerson interrupted");
    }
  }
}

class Chef implements Runnable {
  private Restaurant restaurant;
  public Chef(Restaurant r) { restaurant = r; }
  public void run() {
    try {
      while(!Thread.interrupted()) {
        synchronized(restaurant) {
          print(Thread.currentThread() + "Order up! But not in res...");
          TimeUnit.MILLISECONDS.sleep(100);
          while(restaurant.getEmpty() <= 0) {
            restaurant.wait(); // ... for the meal to be taken
          }

          if ((restaurant.count)++ == 10) {
            while (restaurant.getFull() > 0) {
              print("waiting comsume food " + restaurant.getFull());
              restaurant.wait();

            }
            print("Out of food, closing " + restaurant.getFull());
            restaurant.exec.shutdownNow();
            return;//主动结束
          }

          restaurant.meals.add(new Meal(restaurant.count));
          System.out.println("add food....");
//          设置数据
          int num = restaurant.getFull();
          restaurant.setFull(num + 1);
          num =  restaurant.getEmpty();
          restaurant.setEmpty(num - 1);

          System.out.println("full :" + restaurant.getFull() +" empty :" + restaurant.getEmpty());

          restaurant.notifyAll();
        }
        System.out.println("Chief is doing other things....");
        TimeUnit.MILLISECONDS.sleep(100);
      }
    } catch(InterruptedException e) {
      print("Chef interrupted");
    }
  }
}

public class Restaurant {
  ExecutorService exec = Executors.newCachedThreadPool();

  Queue<Meal> meals = new ConcurrentLinkedQueue<>();
  private int empty = 5;
  private int full = 0;//初始的状态
  int count = 0;//初始的状态
  public synchronized int getEmpty() {
    return empty;
  }

  public synchronized int getFull() {
    return full;
  }

  public synchronized void setEmpty(int empty) {
    this.empty = empty;
  }

  public synchronized void setFull(int full) {
    this.full = full;
  }

  Chef chef = new Chef(this);
  Chef chef2 = new Chef(this);
  WaitPerson waitPerson = new WaitPerson(this);
  WaitPerson waitPerson2 = new WaitPerson(this);
  WaitPerson waitPerson3 = new WaitPerson(this);
  public Restaurant() {
    exec.execute(chef);
    exec.execute(chef2);
    exec.execute(waitPerson);
    exec.execute(waitPerson3);
    exec.execute(waitPerson2);
  }
  public static void main(String[] args) {
    new Restaurant();
  }
} /* Output:
Order up! Waitperson got Meal 1
Order up! Waitperson got Meal 2
Order up! Waitperson got Meal 3
Order up! Waitperson got Meal 4
Order up! Waitperson got Meal 5
Order up! Waitperson got Meal 6
Order up! Waitperson got Meal 7
Order up! Waitperson got Meal 8
Order up! Waitperson got Meal 9
Out of food, closing
WaitPerson interrupted
Order up! Chef interrupted
*///:~
