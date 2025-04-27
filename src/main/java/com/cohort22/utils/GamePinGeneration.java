package com.cohort22.utils;

import java.util.UUID;


public class GamePinGeneration {

    public static String gamePinGenerator() {
        return String.format("%04d", UUID.randomUUID().hashCode());
    }

}
