package org.gamespace;

public class Station {
    private Console console;
    private Screen screen;
    private boolean available;

    public Station(){

    }
    public Station(Console console,Screen screen,boolean available) {
        this.console = console;
        this.screen = screen;
        this.available = available;
    }

    public Console getConsole() {
        return console;
    }

    public Screen getScreen() {
        return screen;
    }

    public boolean isAvailable() {
        return available;
    }

    @Override
    public String toString() {
        String output = "Station: ";
        if (this.available) output+= "Available ;)\n";
        else output+="Not available :(\n";
        output = output + screen + "\n";
        output = output + console;

        return output;
    }
}
