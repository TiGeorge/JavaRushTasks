package com.javarush.task.task25.task2508;

public interface CustomThreadManipulator {
    public void start(String threadName) throws InterruptedException;
    public void stop();
}
