package local.ding;

import local.ding.tools.Enums;

import java.util.EnumSet;

;
enum Outcome{ WIN, LOST, DRAW}
public class EnumDemo {
    public static void main(String[] args) {
    }
}

interface  Competior <T extends Competior<T>> {
    Outcome compete(T competitor);
}

enum RoShamBo2 implements Competior<RoShamBo2> {
    PAPER(Outcome.DRAW,Outcome.LOST,Outcome.WIN),
    SCISSORS(Outcome.WIN,Outcome.DRAW,Outcome.LOST),
    ROCK(Outcome.LOST,Outcome.WIN,Outcome.DRAW);
    private Outcome vpaper, vscissor, vrock;
    RoShamBo2(Outcome paper, Outcome scissor, Outcome rock) {
        vpaper = paper;
        vscissor = scissor;
        vrock = rock;
    }
    @Override
    public Outcome compete(RoShamBo2 competitor) {
        switch (competitor) {
            default:
            case PAPER: return vpaper;
            case SCISSORS:return vscissor;
            case ROCK: return vrock;
    }
 }
}

class RoShamBo {
    public static <T extends Competior<T>> void match(T a, T b) {
        System.out.println(a+ "vs." + b + ":" + a.compete(b));
    }

    public static <T extends Enum<T> & Competior<T>> void play(Class<T> rsbClass, int size) {
        for (int i = 0; i < size; i++) {
            match(Enums.random(rsbClass), Enums.random(rsbClass));
        }
    }

    public static <T extends I>void merge(T a, T b ) {
        System.out.println(a + " :" + b);
    }
    public static void main(String[] args) {
//        play(RoShamBo2.class, 10);
        merge(new AA(), new BB());
    }
}

interface I {
}

class AA implements I {
    public String toString() {
        return "AAA";
    }
}

class BB implements I {
    public String toString() {
        return "BBBB";
    }
}
