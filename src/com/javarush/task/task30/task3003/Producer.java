package com.javarush.task.task30.task3003;

import java.util.concurrent.TransferQueue;

public class Producer implements Runnable {
    private TransferQueue<ShareItem> queue;

    public Producer(TransferQueue<ShareItem> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            for (int n = 1; n < 10; n++) {
                if (Thread.currentThread().isInterrupted()) break;

                System.out.format("Элемент 'ShareItem-%s' добавлен%n", n);
                queue.offer(new ShareItem("ShareItem-" + n, n));
                Thread.sleep(100);

                if (queue.hasWaitingConsumer()) {
                    System.out.format("Consumer в ожидании!%n");
                }
            }
        } catch (InterruptedException e) {
        }
    }
}
