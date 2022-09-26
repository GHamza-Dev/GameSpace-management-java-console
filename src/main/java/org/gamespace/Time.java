package org.gamespace;

import javax.swing.plaf.PanelUI;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

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

    public boolean greaterThan(Time compared){
        if ((this.hours > compared.hours) || ((this.hours == compared.hours) && this.minutes > compared.minutes)) {
            return true;
        }
        return false;
    }
    public static void increment(Time time,Time amount){
        time.hours+=amount.hours;
        for (int i = 0; i < amount.getMinutes(); i++) {
            time.minutes++;
            if (time.minutes == 60) {
                time.hours++;
                time.minutes = 0;
            }
        }
    }
    public static Time add(Time t1,Time t2){
        Time res = new Time(00,00);
        increment(res,t1);
        increment(res,t2);
        return res;
    }

    public static Time now(){
        String[] now = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")).split(":");
        return new Time(Integer.valueOf(now[0]),Integer.valueOf(now[1]));
    }

    @Override
    public String toString() {
        String h = this.hours < 10 ? "0"+this.hours : this.hours+"";
        String m = this.minutes < 10 ? "0"+this.minutes : this.minutes+"";
        return h+":"+m;
    }
}

