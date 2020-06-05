package com.yoni.a2020_02_07mycarwashclient.DataClasses;

public class Slots {
    String time;
    boolean isTaken;

    public Slots(String time, boolean isTaken) {
        this.time = time;
        this.isTaken = isTaken;
    }

    public String getTime() {
        return time;
    }

    public boolean isTaken() {
        return isTaken;
    }

    @Override
    public String toString() {
        return "Slots{" +
                "time='" + time + '\'' +
                ", isTaken=" + isTaken +
                '}';
    }
}
