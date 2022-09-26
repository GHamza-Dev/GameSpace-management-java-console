package org.gamespace;

public class Plan {
    private int id;
    private Time duration;
    private double price;

    public Plan(int id,Time duration,double price){
        this.id = id;
        this.duration = duration;
        this.price = price;
    }

    public boolean outOfInterval(){

        Time until = Time.add(Time.now(),this.getDuration());

        if ((until.greaterThan(new Time(12,00)) && !until.greaterThan(new Time(14,00))) || until.greaterThan(new Time(22,00))) {
            return true;
        }

        return false;
    }

    public int getId() {
        return id;
    }

    public Time getDuration() {
        return duration;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "["+id+"]: Price: "+price+", Duration: "+duration;
    }
}