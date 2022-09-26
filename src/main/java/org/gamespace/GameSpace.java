package org.gamespace;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.data.Storage;
import org.dialog.Out;

import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

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
    public void addReservation(){
        int playerId = 0;
        int stationId = 0;

        Out.soft("[1] - Add new player.");
        Out.soft("[2] - Choose from players list.");
        Out.soft(">: ");

        int choice = scanner.nextInt();

        if (choice == 1) {
            playerId = addPlayer().getId();
        }else {
            Out.soft("Enter player id: ");
            displayPlayers();
            Out.soft("Enter player id: ");
            int id = scanner.nextInt();
            playerId = getPlayerById(this.players,id) != null ? id : 0;
        }

        Time t1 = new Time(15,00);
        Time t2 = new Time(00,30);
        stationId = this.stations.get(2).getStationId();

        this.reservations.add(new Reservation(playerId,stationId,t1,t2));
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
        Station station = new Station(console,screen,lastStationId()+1,true);
        this.stations.add(station);
        Storage.store("stations.json",this.stations);
    }

    public void displayStations(){
        int index = 1;
        for(Station station: stations){
            String av = stationAvailability(station);
            System.out.println("["+index+"]["+av+"] -----------------\n"+station);
            index++;
        }
    }

    public Reservation lastStationReservation(Station station){
        Reservation lastOccurrence = null;
        String today = LocalDate.now().toString();

        for(Reservation reservation: reservations){
            if (reservation.getDate().equals(today)) {
                if (reservation.getStationId() == station.getStationId()) {
                    lastOccurrence = reservation;
                }
            }
        }

        return lastOccurrence;
    }
    public boolean isAvailable(Station station){
        Reservation lastOccurrence = lastStationReservation(station);

        if (lastOccurrence == null) {
            return true;
        }

        Time sum = Time.add(lastOccurrence.getStartAt(),lastOccurrence.getDuration());

        String[] now = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")).split(":");

        Time nowTime = new Time(Integer.valueOf(now[0]),Integer.valueOf(now[1]));

        return nowTime.greaterThan(sum);
    }

    public String stationAvailability(Station station){
        Reservation lastOccurrence = lastStationReservation(station);

        if (lastOccurrence == null) {
            return "Available";
        }

        Time sum = Time.add(lastOccurrence.getStartAt(),lastOccurrence.getDuration());

        String[] now = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")).split(":");

        Time nowTime = new Time(Integer.valueOf(now[0]),Integer.valueOf(now[1]));

        return nowTime.greaterThan(sum) ? "Available" : "Occupied until: "+sum;
    }

    public ArrayList<Reservation> getTodayReservations(){

        if (this.reservations == null || this.reservations.size() == 0) {
            return new ArrayList<>();
        }

        ArrayList<Reservation> todayReservations = new ArrayList<>();
        String today = LocalDate.now().toString();

        for(Reservation reservation: reservations){
            if (reservation.getDate().equals(today)) {
                todayReservations.add(reservation);
            }
        }

        return todayReservations;
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

    public int lastStationId(){
        int size = this.stations.size();
        if (size == 0) {
            return 10;
        }
        return this.stations.get(size-1).getStationId();
    }

    public Player getPlayerById(ArrayList<Player> list,int id){
        if (list.size() == 0) {
            return null;
        }
        for (Player player: list){
            if (player.getId() == id) {
                return player;
            }
        }
        return null;
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
