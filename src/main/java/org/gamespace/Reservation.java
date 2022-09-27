package org.gamespace;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public class Reservation{
    private int playerId;
    private int stationId;
    private Time startAt;
    private Time duration;
    @JsonProperty(value = "date")
    private String date;
    @JsonProperty(value = "price")
    private double price;

    public Reservation(){

    }
    public Reservation(int playerId,int stationId,Time startAt,Time duration,double price) {
        this.playerId = playerId;
        this.stationId = stationId;
        this.startAt = startAt;
        this.duration = duration;
        this.date = LocalDate.now().toString();
        this.price = price;
    }

    public int getPlayerId() {
        return playerId;
    }

    public int getStationId() {
        return this.stationId;
    }

    public Time getStartAt() {
        return startAt;
    }

    public Time getDuration() {
        return duration;
    }
    public String getDate(){
        return this.date;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Reservation[player id:" + playerId + ", station id:" + stationId + ", start at:" + startAt + ", duration:" + duration+"]";
    }
}
