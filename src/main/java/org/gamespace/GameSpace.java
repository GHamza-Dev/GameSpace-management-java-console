package org.gamespace;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.data.Storage;
import org.dialog.Out;

import java.nio.file.Paths;
import java.time.LocalDate;
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

        displayStations();

        System.out.println("Chose a station: ");
        int chosenStationId = scanner.nextInt();
        Station chosenStation = stations.get(chosenStationId-1);

        if (isAvailable(chosenStation)) {
            Plan plan = promptForPlayerPlan();

            if (plan.outOfInterval()){
                System.out.println("This plan is out of interval please go back and chose a valid plan!");
                return;
            }

            this.reservations.add(new Reservation(playerId,chosenStation.getStationId(),Time.now(),plan.getDuration(),plan.getPrice()));
            System.out.println("Enjoy!!!!!!!!");
        }else {
            System.out.println("This station is not available at this moment.");
            System.out.println("Add you to the waiting list: ");

            String answer = scanner.nextLine();
            if (answer.equals("no")) {
                System.out.println("See you next time!");
                return;
            }

            Plan plan = promptForPlayerPlan();
            Time avTime = stationAvailabilityTime(chosenStation);

            if (plan.outOfInterval(avTime)){
                System.out.println("This plan is out of interval please go back and chose a valid plan!");
                return;
            }

            this.reservations.add(new Reservation(playerId,chosenStation.getStationId(),avTime,plan.getDuration(),plan.getPrice()));
        }

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

    public Plan promptForPlayerPlan(){
        ArrayList<Plan> plans = new ArrayList<>();

        plans.add(new Plan(1,new Time(0,30),05));
        plans.add(new Plan(2,new Time(1,0),10));
        plans.add(new Plan(3,new Time(2,0),18));
        plans.add(new Plan(4,new Time(5,0),40));
        plans.add(new Plan(5,new Time(9,0),65));

        for (Plan plan: plans){
            System.out.println(plan);
        }

        Out.soft("Chose a plan: ");
        int planId = scanner.nextInt();

        return plans.get(planId-1);
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

    public Time stationAvailabilityTime(Station station){
        Reservation lastOccurrence = lastStationReservation(station);

        if (lastOccurrence == null) {
            return Time.now();
        }

        return Time.add(lastOccurrence.getStartAt(),lastOccurrence.getDuration());
    }
    public boolean isAvailable(Station station){
        Reservation lastOccurrence = lastStationReservation(station);

        if (lastOccurrence == null) {
            return true;
        }

        Time sum = Time.add(lastOccurrence.getStartAt(),lastOccurrence.getDuration());

        Time now = Time.now();

        return now.greaterThan(sum);
    }

    public String stationAvailability(Station station){
        Reservation lastOccurrence = lastStationReservation(station);

        if (lastOccurrence == null) {
            return "Available";
        }

        Time sum = Time.add(lastOccurrence.getStartAt(),lastOccurrence.getDuration());

        Time now = Time.now();

        return now.greaterThan(sum) ? "Available" : "Occupied until: "+sum;
    }

    public void displayStatistics(){
        int size = reservations.size();
        if (size == 0) {
            System.out.println("Data is not available at this moment!");
            return;
        }

        String  prevDate = this.reservations.get(0).getDate();
        Double prevPrice = this.reservations.get(0).getPrice();
        HashMap<String,Double> stats = new HashMap<String, Double>();

        for (int i = 1; i < size; i++) {
            if (reservations.get(i).getDate().equals(prevDate)) {
                prevPrice+=reservations.get(i).getPrice();
            }else {
                stats.put(prevDate,prevPrice);
                prevPrice = reservations.get(i).getPrice();
                prevDate = reservations.get(i).getDate();
            }
        }

        stats.put(prevDate,prevPrice);

        for (var entry : stats.entrySet()) {
            System.out.println("Day: "+entry.getKey()+" | Revenue: "+entry.getValue()+"DH.");
        }
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
