package local.ding;

import java.util.concurrent.TimeUnit;

public class Peterson implements Runnable {

    private static boolean[] in = { false, false };
    private static volatile int turn = 0;

    public static void main(String[] args) {
        new Thread(new Peterson(0), "Thread - 0").start();
        new Thread(new Peterson(1), "Thread - 1").start();
    }

    private final int id;

    public Peterson(int i) {
        id = i;
    }

    private int other() {
        return id == 0 ? 1 : 0;
    }

    @Override
    public void run() {
        while (true) {
            in[id] = true;//意愿
            turn = other();//谦让
            while (in[other()] && turn == other()) {
                System.out.println("[" + id + "] - Waiting...");
                try { TimeUnit.MILLISECONDS.sleep(50);} catch (Exception e) {}
            }
            System.out.println(turn + " " + id);
            try { TimeUnit.MILLISECONDS.sleep(100);} catch (Exception e) {}
            System.out.println("[" + id + "] - Working ("
                    + (!(id == 1) ? "my turn" : "other done" ) + ")");//这里不能使用turn == id
            in[id] = false;
            //表示等待一段时间再
            try { TimeUnit.MILLISECONDS.sleep(1000);} catch (Exception e) {}

        }
    }
}