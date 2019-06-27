package com.tasdiqhasan.junit.first;

public class Math {
    public int multiply(int x, int y) {
    // the following is just an example
        if (x > 999) {
            throw new IllegalArgumentException("X should be less than 1000");
        }
        // return x / y;
        return x * y;
    }
}