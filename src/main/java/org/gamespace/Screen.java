package org.gamespace;
public class Screen {
    private String name;

    public Screen(){

    }
    public Screen(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString(){
        return "Screen name: "+this.name;
    }
}
