package local.ding.tools;

import java.util.Random;

public class Enums {
    private static Random r = new Random(47);
    public static <T extends Enum<T>> T random(Class<T> enumClass) {
        return random(enumClass.getEnumConstants());
    }

    public static <T> T random(T[] values) {
        return values[r.nextInt(values.length)];
    }
}
