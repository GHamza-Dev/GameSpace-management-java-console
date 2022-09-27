package org.gamespace;
public class Station {
    private Console console;
    private Screen screen;
    private int stationId;
    private boolean available;

    public Station(){

    }
    public Station(Console console,Screen screen,int stationId,boolean available) {
        this.console = console;
        this.screen = screen;
        this.stationId = stationId;
        this.available = available;
    }

    public Console getConsole() {
        return console;
    }

    public Screen getScreen() {
        return screen;
    }

    public int getStationId(){
        return this.stationId;
    }

    public boolean isAvailable() {
        return available;
    }

    @Override
    public String toString() {
        String output = "";
        output = output + screen + "\n";
        output = output + console;
        return output;
    }
}
