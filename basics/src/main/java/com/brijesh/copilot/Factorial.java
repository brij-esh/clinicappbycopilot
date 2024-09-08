package com.brijesh.copilot;


public class Factorial {
    public static int calculateFactorial(int number) {
        if (number < 0) {
            throw new IllegalArgumentException("Number must be non-negative");
        }
        if (number == 0 || number == 1) {
            return 1;
        } else {
            return number * calculateFactorial(number - 1);
        }
    }

    public static void main(String[] args) {
        int number = -5;
        try {
            System.out.println("Factorial of " + number + " is: " + calculateFactorial(number));
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}
