package com.javageeks.concurrency.thepackage;

import java.util.concurrent.*;

public class MyExecutor implements Executor {
 
    public void execute(Runnable r) {
        (new Thread(r)).start();
    }
}