package com.javarush.task.task27.task2712;

import com.javarush.task.task27.task2712.kitchen.Cook;
import com.javarush.task.task27.task2712.kitchen.Waiter;

public class Restaurant {
    public static void main(String[] args) {

        Tablet tablet = new Tablet(5);
        Cook cook = new Cook("George");
        Waiter waiter = new Waiter();
        cook.addObserver(waiter);
        tablet.addObserver(cook);
        tablet.createOrder();

    }
}
