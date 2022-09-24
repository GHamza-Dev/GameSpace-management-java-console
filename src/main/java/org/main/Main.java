package org.main;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.dialog.Out;
import org.gamespace.*;
import org.menu.Menu;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GameSpace g = new GameSpace();

        Menu menu = new Menu();
        menu.addChoice("add new console");
        menu.addChoice("add new player");
        menu.addChoice("display all players");
        menu.addChoice("display all users");
        menu.addChoice("display all posts");

        int nbr = 0;

        do {
            nbr = menu.getChoice(scanner);
            switch (nbr){
                case 0:{
                    System.out.println("GOOD BAY!");
                }continue;
                case 1:{
                    String firstName = "";
                    String lastName = "";
                    String consoleName = "";
                    String screenName = "";
                    Out.soft("Reading player information:\n");
                    Out.soft("Player first name: ");
                    firstName = scanner.nextLine();
                    Out.soft("Player last name: ");
                    lastName = scanner.nextLine();
                    Player player = new Player(firstName,lastName,1);

                    Out.soft("Reading station info: ");
                    Out.soft("Console name? ");
                    consoleName = scanner.nextLine();
                    Out.soft("Screen name? ");
                    screenName = scanner.nextLine();
                    Console console = new Console(consoleName);
                    Screen screen = new Screen(screenName);
                    Station station = new Station(console,screen,true);

                    Out.soft("Starts at?");
                    String[] startAt = scanner.nextLine().split(":");
                    Time t1 = new Time(Integer.valueOf(startAt[0]),Integer.valueOf(startAt[1]));
                    Out.soft("Duration? ");
                    String[] duration = scanner.nextLine().split(":");
                    Time t2 = new Time(Integer.valueOf(duration[0]),Integer.valueOf(duration[1]));

                    Reservation reservation = new Reservation(player,station,t1,t2);
                    GameSpace gameSpace = new GameSpace();
                    gameSpace.addReservation(reservation);

                    Out.soft("END",20);


                }break;
                case 2:{
                    g.addPlayer();
                }break;
                case 3:{
                    g.displayPlayers();
                }break;
                case 4:{
                    g.displayReservations();
                }break;
                default:{
                    System.out.println(404);
                }break;
            }
        }while (nbr != 0);

    }
}