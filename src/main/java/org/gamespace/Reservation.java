package org.gamespace;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public class Reservation{
    private Player player;
    private Station station;
    private Time startAt;
    private Time duration;
    private LocalDate reservationDate;

    public Reservation(){

    }
    public Reservation(Player player,Station station,Time startAt,Time duration) {
        this.player = player;
        this.station = station;
        this.startAt = startAt;
        this.duration = duration;
        this.reservationDate = LocalDate.now();
    }

    public Player getPlayer() {
        return player;
    }

    public Station getStation() {
        return station;
    }

    public Time getStartAt() {
        return startAt;
    }

    public Time getDuration() {
        return duration;
    }

    @Override
    public String toString() {
        return "Reservation[player:" + player + ", station:" + station + ", startAt:" + startAt + ", duration:" + duration+"]";
    }
}
