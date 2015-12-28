package com.example.concurrency;

/**
 * Created by alexander on 12/28/15.
 */
public class StateHolder {

    private volatile boolean state;

    public boolean getState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }
}
