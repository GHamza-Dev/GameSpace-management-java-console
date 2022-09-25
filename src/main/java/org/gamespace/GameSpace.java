package org.gamespace;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.data.Storage;
import org.dialog.Out;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class GameSpace{
    private ArrayList<Reservation> reservations;
    private ArrayList<Player> players;
    private ArrayList<Station> stations;
    private ArrayList<Reservation> waitingList;

    private final Scanner scanner = new Scanner(System.in);
    public GameSpace() {
        this.reservations = loadReservations();
        this.players = loadPlayers();
        this.stations = loadStations();
        this.waitingList = new ArrayList<>();
    }
    public void addReservation(Reservation reservation){
        this.reservations.add(reservation);
        Storage.store("reservations.json",this.reservations);
    }

    public void displayReservations(){
        for (Reservation reservation: reservations){
            System.out.println(reservation);
        }
    }
    public Player addPlayer(){
        Out.soft("Player first name: ");
        String firstname = scanner.nextLine();
        Out.soft("Player last name: ");
        String lastname = scanner.nextLine();

        Player player = new Player(firstname,lastname,lastPlayerId()+1);
        this.players.add(player);

        Storage.store("players.json",this.players);

        return player;
    }

    public Game promptForGameInfo(){
        Out.soft("Game name: ");
        String name = scanner.nextLine();
        Out.soft("Game type: ");
        String type = scanner.nextLine();

        Game game = new Game(name,type);
        return game;
    }

    public Console promptForConsoleInfo(){
        Out.soft("Console name: ");
        String name = scanner.nextLine();

        Console console = new Console(name);

        Out.soft("Add games to console: ");
        System.out.println("");
        while (true){
            Out.soft("Add game? no/yes: ");
            String choice = scanner.nextLine();
            if (!choice.contains("y") && !choice.contains("Y")) {
                break;
            }
            console.addGame(this.promptForGameInfo());
        }

        return console;
    }

    public Screen promptForScreenInfo(){
        Out.soft("Screen name: ");
        String name = scanner.nextLine();
        return new Screen(name);
    }

    public void addStation(){
        Console console = promptForConsoleInfo();
        Screen screen = promptForScreenInfo();
        Station station = new Station(console,screen,true);
        this.stations.add(station);
        Storage.store("stations.json",this.stations);
    }

    public void displayStations(){
        int index = 1;
        for(Station station: stations){
            System.out.println("["+index+"] -----------------\n"+station);
            index++;
        }
    }

    public void displayPlayers(){
        for (Player player: players){
            System.out.println(player);
        }
    }

    public int lastPlayerId(){
        int size = this.players.size();
        if (size == 0) {
            return 10;
        }
        return this.players.get(size-1).getId();
    }

    public ArrayList<Reservation> loadReservations(){
        try {
            ObjectMapper mapper = new ObjectMapper();
            List<Reservation> loadedReservations = Arrays.asList(mapper.readValue(Paths.get("reservations.json").toFile(), Reservation[].class));
            return new ArrayList<>(loadedReservations);
        } catch (Exception ex) {
            System.out.println("Can not load reservations!");
            ex.printStackTrace();
        }
        return new ArrayList<>();
    }

    public ArrayList<Player> loadPlayers(){
        try {
            ObjectMapper mapper = new ObjectMapper();
            List<Player> loadedPlayers = Arrays.asList(mapper.readValue(Paths.get("players.json").toFile(), Player[].class));
            return new ArrayList<>(loadedPlayers);
        } catch (Exception ex) {
            System.out.println("Can not load players!");
            ex.printStackTrace();
        }
        return new ArrayList<>();
    }

    public ArrayList<Station> loadStations(){
        try {
            ObjectMapper mapper = new ObjectMapper();
            List<Station> loadedStations = Arrays.asList(mapper.readValue(Paths.get("stations.json").toFile(), Station[].class));
            return new ArrayList<>(loadedStations);
        } catch (Exception ex) {
            System.out.println("Can not load stations!");
            ex.printStackTrace();
        }
        return new ArrayList<>();
    }




}
