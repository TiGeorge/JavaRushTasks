package com.javarush.task.task27.task2712;

import com.javarush.task.task27.task2712.kitchen.Cook;
import com.javarush.task.task27.task2712.kitchen.Order;
import com.javarush.task.task27.task2712.kitchen.Waiter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

public class Restaurant {
    private static final int ORDER_CREATING_INTERVAL = 100;
    private static final LinkedBlockingQueue<Order> orderQueue = new LinkedBlockingQueue<>();

    public static void main(String[] args) {

        Cook cook = new Cook("Amigo");
        cook.setQueue(orderQueue);
        Cook cook1 = new Cook("Goga");
        cook1.setQueue(orderQueue);

        Waiter waiter = new Waiter();
        cook.addObserver(waiter);
        cook1.addObserver(waiter);

        List<Tablet> tablets = new ArrayList<>();
        for (int i = 0; i<5; i++){
            Tablet tablet = new Tablet(i);
            tablet.setOrderQueue(orderQueue);
            tablets.add(tablet);
        }

        Thread cookThread1 = new Thread(cook);
        Thread cookThread2 = new Thread(cook1);
        cookThread1.start();
        cookThread2.start();

        Thread th = new Thread(new RandomOrderGeneratorTask(tablets, ORDER_CREATING_INTERVAL));
        th.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {

        }

        th.interrupt();
        cookThread1.interrupt();
        cookThread2.interrupt();
        DirectorTablet directorTablet = new DirectorTablet();
        directorTablet.printAdvertisementProfit();
        directorTablet.printCookWorkloading();
        directorTablet.printActiveVideoSet();
        directorTablet.printArchivedVideoSet();


    }
}
