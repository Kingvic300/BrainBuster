package com.cohort22.utils;

import java.util.Random;


public class GamePinGeneration {

    public static String gamePinGenerator() {
        return String.format("%04d", new Random().nextInt(10000));
    }

}
