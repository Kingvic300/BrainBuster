package com.cohort22.utils;

import java.util.Random;

public class GamePinGeneration {

    public static String gamePinGenerator() {
        Random random = new Random();
        return String.format("%04d", random.nextInt(10000));
    }
}
