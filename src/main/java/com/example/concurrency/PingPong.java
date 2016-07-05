package com.example.concurrency;

/**
 * Created by alexander on 12/28/15.
 */
public class PingPong implements Runnable {

    private final boolean targetState;
    private final StateHolder stateHolder;

    public PingPong(boolean targetState, StateHolder stateHolder) {
        this.targetState = targetState;
        this.stateHolder = stateHolder;
    }

    @Override
    public void run() {

        while (!Thread.interrupted()) {
            synchronized (stateHolder) {
                try {
                    while (stateHolder.getState() != targetState) {
                        stateHolder.wait();
                    }
                    stateHolder.setState(!stateHolder.getState());
                    System.out.println(stateHolder.getState() ? "PING" : "PONG");
                    stateHolder.notify();

                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                    break;
                }

            }
        }

    }


}
