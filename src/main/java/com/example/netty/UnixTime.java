package com.example.netty;

import java.util.Date;

/**
 * Created by alexander on 12/28/15.
 */
public class UnixTime {

    private static final long TIME_OFFSET = 2208988800L;
    private static final long TIME_MULTIPLIER = 1000L;

    private final long value;

    public UnixTime() {
        this(System.currentTimeMillis() / TIME_MULTIPLIER + TIME_OFFSET);
    }

    public UnixTime(long value) {
        this.value = value;
    }

    public long value() {
        return value;
    }

    @Override
    public String toString() {
        return new Date((value() - TIME_OFFSET) * TIME_MULTIPLIER).toString();
    }

}
