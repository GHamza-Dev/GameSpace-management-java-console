package org.gamespace;

public class Time {
    private int hours;
    private int minutes;

    public Time(){

    }
    public Time(int hours,int minutes) {
        this.hours = hours;
        this.minutes = minutes;
    }

    public int getHours() {
        return hours;
    }

    public int getMinutes() {
        return minutes;
    }

    @Override
    public String toString() {
        String h = this.hours < 10 ? "0"+this.hours : this.hours+"";
        String m = this.minutes < 10 ? "0"+this.minutes : this.minutes+"";
        return h+":"+m;
    }
}

