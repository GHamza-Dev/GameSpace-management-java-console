package org.gamespace;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class Console {
    private String name;
    @JsonProperty(value = "games")
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
        output = output + "-- List of games-- \n";

        if (this.games == null){
            return output+"NO GAMES\n";
        }

        for(Game game: games){
            output = output + " " + game +"\n";
        }

        return output;
    }
}

