package com.sidden.flavored.util;

import java.util.UUID;

public interface ZombieConversionAccess {
    boolean isConverting();

    void startConversion(UUID player, int time);
}
