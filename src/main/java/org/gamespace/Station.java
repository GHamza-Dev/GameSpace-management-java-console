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
}
