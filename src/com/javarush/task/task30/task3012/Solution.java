package com.javarush.task.task30.task3012;

/* 
Получи заданное число
*/

import java.util.ArrayList;
import java.util.List;

public class Solution {
    public static void main(String[] args) {
        Solution solution = new Solution();
        solution.createExpression(74);
    }

    public void createExpression(int number) {
        //напишите тут ваш код 1, 3, 9, 27, 81, 243, 729, 2187
        List<Integer> list = new ArrayList<>();
        int rem = 0;
        int n = number;
        while (n>0) {
            rem = n%3;
            n = n/3;
            if (rem == 2) {
                rem = -1;
                n++;
            }
            list.add((rem == 0 ? 0 : (rem == 1) ? 1 : -1));
        }

        String output = "";
        for (int i = 0; i < list.size(); i++) {
            int current = list.get(i);
            if (current != 0) {
                output += (current>0 ? " + " : " - ") + (Math.abs(current) * (int) Math.pow(3, i));
            }
        }

        System.out.println(number + " = " + output);
    }
}

