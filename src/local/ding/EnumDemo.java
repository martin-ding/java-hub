package local.ding;

import local.ding.tools.Enums;
import local.ding.tools.TextFile;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.*;

enum Sh {NU, HJA, AHDA}

public class EnumDemo {
    public static Set<String> analyze(Class<?> enumClass) {
        System.out.println(enumClass.getSimpleName() + " interfaces");
        for (Type t : enumClass.getInterfaces()) {
            System.out.println(t);
        }

        System.out.println("Base: " + enumClass.getSuperclass());
        System.out.println("Methods: ");
        Set<String> sets = new HashSet<>();
        for (Method m : enumClass.getMethods()) {
            sets.add(m.getName());
        }
        System.out.println(sets);
        return sets;
    }

    public static void main(String[] args) {
//        Set<String> expMethods = analyze(Sh.class);
//        Set<String> original = analyze(Enum.class);
//        System.out.println(expMethods.containsAll(original));
//        expMethods.removeAll(original);
//        System.out.println(expMethods);
//        System.out.println(SpaceShip.GASE.next());
//        for (int i=0; i < 10; i++) {
//            System.out.println(Enums.random(SpaceShip.class));
//        }
//        enumSetDemo();
        new EnumDemo().demo();
    }

    public static void enumSetDemo() {
//        long [] elements = new long[3];
//        int eOrdinal = 100;
//        int eWordNum = eOrdinal >>> 6;
//        long num = -1;
//        System.out.printf("%64s\n", Long.toBinaryString(num));
//        num = num >>> 10000;
//        System.out.println(10000 % 64);
//        System.out.printf("%64s\n", Long.toBinaryString(num));
//        long oldElements = elements[eWordNum];
//        elements[eWordNum] |= (1L << eOrdinal);
//        System.out.println(Long.toBinaryString(1L << eOrdinal));
//        System.out.println(1L << 62);
////        boolean result = (elements[eWordNum] != oldElements);
//        for (long e : elements) {
//            System.out.printf("%64s\n", Long.toBinaryString(e));
//        }

        EnumSet<Sh> em = EnumSet.allOf(Sh.class);

        System.out.println(em);
    }

    abstract class Item {
        int n;

        Item(int m) {
            this.n = m;
        }

        String compare(Item i) {
            return i.desc() + " " + desc();
        }

        abstract public String desc();
    }

    class A extends Item {
        A(int m) {
            super(m);
        }

        @Override
        public String desc() {
            return "a";
        }
    }

    class B extends Item {
        B(int m) {
            super(m);
        }

        @Override
        public String desc() {
            return "B";
        }
    }

    public void demo() {
        System.out.println(new Paper().compete(new Scissors()));
    }

    public enum Outcome { WIN, LOSE, DRAW }
    interface Itema {
        Outcome compete(Itema it);
    }

    class Paper implements Itema {

        @Override
        public Outcome compete(Itema it) {
            if (it instanceof Scissors) return Outcome.LOSE;
            if (it instanceof Rock) return Outcome.WIN;
            return Outcome.DRAW;
        }
    }

    class Scissors implements Itema {

        @Override
        public Outcome compete(Itema it) {
            if (it instanceof Rock) return Outcome.LOSE;
            if (it instanceof Paper) return Outcome.WIN;
            return Outcome.DRAW;
        }
    }
    class Rock implements Itema {

        @Override
        public Outcome compete(Itema it) {
            if (it instanceof Scissors) return Outcome.WIN;
            if (it instanceof Paper) return Outcome.LOSE;
            return Outcome.DRAW;
        }
    }
    }

enum SpaceShip {
    SCOUT("zhansg"), ZHA, SHAS, GASE, KDA, HAJSHA;
    private String description;

    private SpaceShip(String dec) {
        this.description = dec;
    }

    SpaceShip() {
        this.description = name();
    }

    @Override
    public String toString() {
        String ori = name();
        return ori.charAt(0) + ori.substring(1).toLowerCase() + " desc: " + description;
    }

    public SpaceShip next() {
        Random r = new Random();
        return values()[r.nextInt(values().length)];
    }

    public static void main(String[] args) {
        for (SpaceShip a : SpaceShip.values()) {
            System.out.println(a);
            System.out.println(a.ordinal());
            System.out.println(a.getClass());
            System.out.println(a.getDeclaringClass());
        }

        SpaceShip num = SpaceShip.GASE;
        switch (num) {
            case ZHA:
                System.out.println("1111");
                return;
        }
    }
}

interface  Competior <T extends Competior<T>> {
    RESULT compete(T competitor);
}
enum RESULT{ WIN, LOST, DRAW}
class RoShamBo2 implements Competior<RoShamBo2>{
    PAPER(RESULT.DRAW,RESULT.LOST,RESULT.WIN),
    SCISSORS(RESULT.DRAW,RESULT.LOST,RESULT.WIN),
    ROCK(RESULT.DRAW,RESULT.LOST,RESULT.WIN);
    private RESULT vpaper, vscissor, vrock;
    RoShamBo2(RESULT paper, RESULT scissor, RESULT rock) {
        vpaper = paper;
        vscissor = scissor;
        rock = vrock;
    }
    @Override
    public RESULT compete(RoShamBo2 competitor) {
        switch (competitor) {
            default:
            case PAPER: return vpaper;
            case
    }
}
}
