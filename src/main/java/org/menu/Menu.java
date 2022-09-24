package org.menu;

import org.dialog.Out;

import java.util.ArrayList;
import java.util.Scanner;

public class Menu {
    private ArrayList<String> choices;
    private int nbrOfChoices;

    public Menu() {
        this.choices = new ArrayList<>();
        this.nbrOfChoices = 1;
    }

    public void addChoice(String choice){
        String msg = "Click "+nbrOfChoices+": to ";
        this.choices.add(msg+choice+".");
        nbrOfChoices++;
    }

    public void printMenu(){
        System.out.print("*****");
        for (int i = 0; i < choices.size(); i++) {
            Out.soft(choices.get(i));
        }
        System.out.println("*****");
    }

    public int getChoice(Scanner scanner) {
        printMenu();
        System.out.print("\nYour choice: ");
        return scanner.nextInt();
    }
}
