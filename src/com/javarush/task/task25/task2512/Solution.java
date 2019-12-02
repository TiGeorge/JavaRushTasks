package com.javarush.task.task25.task2512;

/* 
Живем своим умом
*/
public class Solution implements Thread.UncaughtExceptionHandler {

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        t.interrupt();
        printStack(e);
    }

    private void printStack(Throwable e) {
        Throwable cause = e.getCause();
        if(cause != null) printStack(cause);
        System.out.println(e.getClass().getName() + ": " + e.getMessage());
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        solution.uncaughtException(new Thread(), new Exception("ABC", new RuntimeException("DEF", new IllegalAccessException("GHI"))));    }
}
