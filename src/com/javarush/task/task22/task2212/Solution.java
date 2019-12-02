package com.javarush.task.task22.task2212;

/*
Проверка номера телефона
*/
public class Solution {
    public static boolean checkTelNumber(String telNumber) {

        if (telNumber==null || telNumber.isEmpty()){
            return false;
        }

        // 1) если номер начинается с '+', то он содержит 12 цифр
        int countDigit = telNumber.replaceAll("\\D+", "").length();
        if(telNumber.startsWith("+") && countDigit != 12) return false;

        // 2) если номер начинается с цифры или открывающей скобки, то он содержит 10 цифр
        if ((telNumber.startsWith("(") || Character.isDigit(telNumber.charAt(0))) && countDigit != 10) return false;

        // 6) номер не содержит букв
        if(telNumber.contains("[a-zA-Z]")) return false;

        // 7) номер заканчивается на цифру
        if (!telNumber.substring(telNumber.length()-1).matches("\\d")) return false;

        // 3) может содержать 0-2 знаков '-', которые не могут идти подряд
        int len = telNumber.length();
        int countDashes = len - telNumber.replaceAll("-", "").length();
        if (countDashes > 2) return false;
        if ((telNumber.indexOf('-')+1 == telNumber.lastIndexOf('-'))) return false;

        // 4) может содержать 1 пару скобок '(' и ')' , причем если она есть, то она расположена левее знаков '-'
        int countOpenParenthesis = len - telNumber.replaceAll("\\(", "").length();
        int countCloseParenthesis = len - telNumber.replaceAll("\\)", "").length();
        if (countOpenParenthesis > 1 || countCloseParenthesis >1 || countOpenParenthesis != countCloseParenthesis) return false;
        if (countOpenParenthesis > 0) {
            // 5) скобки внутри содержат четко 3 цифры
            if(!(telNumber.indexOf('(') + 4 == telNumber.indexOf(')'))) return false;
            // причем если она есть, то она расположена левее знаков '-'
            if (telNumber.contains("-") && telNumber.indexOf('-') < telNumber.indexOf(')')) return false;
        }

        return true;
    }

    public static void main(String[] args) {

//        +380501234567 - true
//        +38(050)1234567 - true
//        +38050123-45-67 - true
//        050123-4567 - true
//        +38)050(1234567 - false
//        +38(050)1-23-45-6-7 - false
//        050ххх4567 - false
//        050123456 - false
//        (0)501234567 - false
        System.out.println(checkTelNumber("+380501234567"));
        System.out.println(checkTelNumber("+38(050)1234567"));
        System.out.println(checkTelNumber("+38050123-45-67"));
        System.out.println(checkTelNumber("050123-4567"));
        System.out.println(checkTelNumber("+38)050(1234567"));
        System.out.println(checkTelNumber("+38(050)1-23-45-6-7"));
        System.out.println(checkTelNumber("050ххх4567"));
        System.out.println(checkTelNumber("050123456"));
        System.out.println(checkTelNumber("(0)501234567"));

    }
}
