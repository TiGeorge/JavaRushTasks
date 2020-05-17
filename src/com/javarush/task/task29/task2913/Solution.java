package com.javarush.task.task29.task2913;

import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/* 
Замена рекурсии
*/

public class Solution {
    private static int numberA;
    private static int numberB;

    public static String getAllNumbersBetween(int a, int b) {
        String result = "";
        if (a < b) {
            result = IntStream.rangeClosed(a, b).boxed().map(e -> e.toString()).collect(Collectors.joining(" "));
        } else {
            result = IntStream.rangeClosed(b, a).boxed().map(i -> a - i + b).map(e -> e.toString()).collect(Collectors.joining(" "));
        }
        return result;
    }

    public static void main(String[] args) {
        Random random = new Random();
        numberA = random.nextInt(1000);
        numberB = random.nextInt(1000);
        System.out.println(getAllNumbersBetween(numberA, numberB));
        System.out.println(getAllNumbersBetween(numberB, numberA));
    }
}