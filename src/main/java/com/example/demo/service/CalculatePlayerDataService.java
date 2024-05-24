package com.example.demo.service;

public abstract class CalculatePlayerDataService {

    public static int calculateLvl(int experience) {
        return (int) ((Math.sqrt((double) 2500 + 200 * experience) - 50) / 100);
    }

    public static int calculateUntilNextLvl(int level, int experience) {
        return 50 * (level + 1) * (level + 2) - experience;
    }
}
