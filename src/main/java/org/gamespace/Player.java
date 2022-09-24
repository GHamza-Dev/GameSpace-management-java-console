package org.gamespace;

public class Player {
    private String firstName;
    private String lastName;

    private int id;

    public Player(){

    }
    public Player(String firstName,String lastName,int id) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "ID: "+this.id+" | Full name: "+this.lastName+", "+this.firstName;
    }
}
