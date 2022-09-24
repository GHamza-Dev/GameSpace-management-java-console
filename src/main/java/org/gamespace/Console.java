package org.gamespace;

import java.util.ArrayList;

public class Console {
    private String name;
    private ArrayList<Game> games;

    public Console(){

    }
    public Console(String name) {
        this.games = new ArrayList<>();
        this.name = name;
    }

    public void addGame(Game game){
        this.games.add(game);
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        String output = "Console name: "+this.name+"\n";
        for(Game game: games){
            output = output + " " + game +"\n";
        }

        return output;
    }
}

