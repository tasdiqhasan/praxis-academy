package com.javageeks.concurrency.performance;

import java.util.concurrent.*;

public class LockSplitting implements Runnable {
    private static final int NUMBER_OF_THREADS = 5;
    private Counter counter;
 
    public interface Counter {
        void incrementCustomer();
 
        void incrementShipping();
 
        long getCustomerCount();
 
        long getShippingCount();
    }
 
    public static class CounterOneLock implements Counter {
        private long customerCount = 0;
        private long shippingCount = 0;
     
        public synchronized void incrementCustomer() {
            customerCount++;
        }
     
        public synchronized void incrementShipping() {
            shippingCount++;
        }
     
        public synchronized long getCustomerCount() {
            return customerCount;
        }
     
        public synchronized long getShippingCount() {
            return shippingCount;
        }
    }
    
    public static class CounterSeparateLock implements Counter {
        private static final Object customerLock = new Object();
        private static final Object shippingLock = new Object();
        private long customerCount = 0;
        private long shippingCount = 0;
     
        public void incrementCustomer() {
            synchronized (customerLock) {
                customerCount++;
            }
        }
     
        public void incrementShipping() {
            synchronized (shippingLock) {
                shippingCount++;
            }
        }
     
        public long getCustomerCount() {
            synchronized (customerLock) {
                return customerCount;
            }
        }
     
        public long getShippingCount() {
            synchronized (shippingLock) {
                return shippingCount;
            }
        }
    }

    public LockSplitting(Counter counter) {
        this.counter = counter;
    }
 
    public void run() {
        for (int i = 0; i < 100000; i++) {
            if (ThreadLocalRandom.current().nextBoolean()) {
                counter.incrementCustomer();
            } else {
                counter.incrementShipping();
            }
        }
    }
 
    public static void main(String[] args) throws InterruptedException {
        Thread[] threads = new Thread[NUMBER_OF_THREADS];
        Counter counter = new CounterOneLock();
        for (int i = 0; i < NUMBER_OF_THREADS; i++) {
            threads[i] = new Thread(new LockSplitting(counter));
        }
        long startMillis = System.currentTimeMillis();
        for (int i = 0; i < NUMBER_OF_THREADS; i++) {
            threads[i].start();
        }
        for (int i = 0; i < NUMBER_OF_THREADS; i++) {
            threads[i].join();
        }
        System.out.println((System.currentTimeMillis() - startMillis) + "ms");
    }
}