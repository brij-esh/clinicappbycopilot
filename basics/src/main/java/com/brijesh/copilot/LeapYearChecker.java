package com.brijesh.copilot;

import java.util.logging.Logger;

public class LeapYearChecker {
    private static final Logger logger = Logger.getLogger(LeapYearChecker.class.getName());

    public static void main(String[] args) {
        int year = 2024; // Example year, you can change this to test other years.
        logger.info(year + " is a leap year? " + isLeapYear(year));
    }

    public static boolean isLeapYear(int year) {
        // Corrected leap year calculation
        // A year is a leap year if it is divisible by 4.
        // However, years divisible by 100 are not leap years,
        // except years that are divisible by 400.
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }
}
