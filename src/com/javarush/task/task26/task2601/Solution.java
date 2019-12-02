package com.javarush.task.task26.task2601;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/*
Почитать в инете про медиану выборки
*/
public class Solution {

    public static void main(String[] args) {
//        Integer[] integers = {13, 8, 15, 5, 18, 11};
//        Integer[] sortIntegers = sort(integers);
//        System.out.println(Arrays.toString(sortIntegers));
    }

    public static Integer[] sort(Integer[] array) {
        //implement logic here
        List<Integer> list = Arrays.asList(array);
        Collections.sort(list);
        System.out.println(list.toString());

        int ind = list.size() / 2;
        double mediana = (array.length % 2 != 0) ? list.get(ind) : (double) (list.get(ind) + list.get(ind - 1)) / 2;
        System.out.println(mediana);
        Collections.sort(list, Comparator.comparing(obj -> Math.abs(mediana - obj)));
        array = (Integer[]) list.toArray();


        return array;
    }
}
