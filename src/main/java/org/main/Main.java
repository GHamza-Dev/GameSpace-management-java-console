package org.main;
import org.dialog.Out;
import org.gamespace.*;
import org.menu.Menu;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GameSpace g = new GameSpace();

        Menu menu = new Menu();
        menu.addChoice("add new station");
        menu.addChoice("add new player");
        menu.addChoice("display stations");
        menu.addChoice("display reservations");
        menu.addChoice("display players");
        menu.addChoice("add reservation");
        menu.addChoice("display statistics");
        menu.addChoice(".......");


        int nbr = 0;

        do {
            nbr = menu.getChoice(scanner);
            switch (nbr){
                case 0:{
                    Out.soft("Good bye...");
                }continue;
                case 1:{
                    g.addStation();
                }break;
                case 2:{
                    g.addPlayer();
                }break;
                case 3:{
                    g.displayStations();
                }break;
                case 4:{
                    g.displayReservations();
                }break;
                case 5:{
                    g.displayPlayers();
                }break;
                case 6:{
                    g.addReservation();
                }break;
                case 7:{
                    g.displayStatistics();
                }break;
                default:{
                    System.out.println(404);
                }break;
            }
        }while (nbr != 0);

    }
}