package fr.dawan.jeankasskouille.tools;

import java.util.List;
import java.util.Random;

public class RandomUtils {
    private static final Random RANDOM = new Random();

    public static boolean getRandomByPercentage(float percentage) {
        return RANDOM.nextFloat(0f, 1f) <= percentage;
    }

    public static <T> T getRandomList(List<T> list) throws
            IllegalArgumentException {
        return list.get(RANDOM.nextInt(0, list.size()));
    }

    public static <T> T getRandomList(T[] list) {
        if (list.length == 0) return null;
        return list[(RANDOM.nextInt(0, list.length))];
    }
}
