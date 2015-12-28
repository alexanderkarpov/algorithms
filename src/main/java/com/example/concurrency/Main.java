package com.example.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by alexander on 12/28/15.
 */
public class Main {

    public static void main(String... args) {

        System.out.println("-----------");
        StateHolder stateHolder = new StateHolder();
        stateHolder.setState(false);

        ExecutorService executorService = Executors.newFixedThreadPool(2);

        executorService.execute(new PingPong(true, stateHolder));
        executorService.execute(new PingPong(false, stateHolder));

        try {
            Thread.sleep(50L);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

        executorService.shutdownNow();
        System.out.println("-----------");
    }

}
