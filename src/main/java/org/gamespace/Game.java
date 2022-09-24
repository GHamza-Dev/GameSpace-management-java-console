package org.gamespace;

public class Game {
    private String name;
    private String type;

    public Game(){

    }
    public Game(String name,String type){
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Game name: "+this.name;
    }
}
