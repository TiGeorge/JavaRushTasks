package com.javarush.task.task29.task2909.human;

public class Teacher extends UniversityPerson {
    private int numberOfStudents;
//    private String university;
    private boolean isProfessor;

    public Teacher(String name, int age, int numberOfStudents) {
        super(name, age);
//        this.name = name;
//        this.age = age;
        this.numberOfStudents = numberOfStudents;
    }

    public void live() {
        teach();
    }

    public void teach() {
    }

//    public String getUniversity() {
//        return university;

    @Override
    public String getPosition() {
        return "Преподаватель";
    }
//    }
//
//    public void setUniversity(String university) {
//        this.university = university;
//    }

//    public void printData() {
//        System.out.println("Преподаватель: " + name);
//    }
}