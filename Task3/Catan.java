import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

class Catan {

    public static void main(String[] args) throws Exception {

        Board board = new Board();
        Dice dice = new Dice();
        Production production = new Production(board);

        Scanner scanner = null;
        java.io.File cfgFile = new java.io.File("config.txt");
        if (!cfgFile.exists()) {
            cfgFile = new java.io.File("Task4/config.txt");
        }
        if (cfgFile.exists()) {
            scanner = new Scanner(cfgFile);
        } else {
            java.io.InputStream in = Catan.class.getResourceAsStream("/Task4/config.txt");
            if (in == null) {
                throw new java.io.FileNotFoundException("config.txt not found in working dir, Task4/, or classpath");
            }
            scanner = new Scanner(in);
        }
        String line = scanner.nextLine();
        int maxRounds = Integer.parseInt(line.split(":")[1].trim());
        scanner.close();

        List<Player> players = new ArrayList<Player>();

        Player p1 = new ComputerPlayer(1, 0, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new HashMap<>());
        Player p2 = new ComputerPlayer(2, 0, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new HashMap<>());
        Player p3 = new ComputerPlayer(3, 0, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new HashMap<>());

        players.add(p1);
        players.add(p2);
        players.add(p3);

        Turn turn = new Turn(dice, production, board, players);
        Player p4 = new HumanPlayer(4, 0, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new HashMap<>());
        players.add(p4);

        Visualizer visualizer = new Visualizer("visualize/state.json", players, board);
        p1.setVisualizer(visualizer);
        p2.setVisualizer(visualizer);
        p3.setVisualizer(visualizer);
        p4.setVisualizer(visualizer);

        visualizer.clear();
        Thread.sleep(600);

        Scanner inputScanner = new Scanner(System.in);
        Random random = new Random();

        // Each player places 2 initial settlements
        for (int round = 0; round < 2; round++) {
            for (Player player : players) {
                if (player instanceof HumanPlayer) {
                    while (true) {
                        System.out.print("Player " + player.getPlayerID() + ", place your settlement (enter node ID 0-53): ");
                        String input = inputScanner.nextLine().trim();
                        try {
                            int nodeID = Integer.parseInt(input);
                            if (nodeID < 0 || nodeID > 53) {
                                System.out.println("Invalid node. Must be between 0 and 53.");
                                continue;
                            }
                            Intersection spot = board.getIntersection(nodeID);
                            if (board.placeSettlement(spot, player)) {
                                player.addVictoryPoint();
                                visualizer.refresh();
                                Thread.sleep(600);
                                break;
                            } else {
                                System.out.println("Invalid spot, try another node.");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Please enter a valid number.");
                        }
                    }
                } else {
                    // Computer picks a random valid spot
                    List<Integer> validSpots = new ArrayList<>();
                    for (int i = 0; i <= 53; i++) {
                        Intersection spot = board.getIntersection(i);
                        if (spot.getBuilding() == null) {
                            boolean valid = true;
                            for (int neighbour : board.getNeighbouringIntersections(i)) {
                                if (board.getIntersection(neighbour).getBuilding() != null) {
                                    valid = false;
                                    break;
                                }
                            }
                            if (valid) validSpots.add(i);
                        }
                    }
                    int picked = validSpots.get(random.nextInt(validSpots.size()));
                    board.placeSettlement(board.getIntersection(picked), player);
                    player.addVictoryPoint();
                    System.out.println("Player " + player.getPlayerID() + " placed settlement at node " + picked);
                    visualizer.refresh();
                    Thread.sleep(600);
                    System.out.println("Enter Go to continue.");
                    while (!inputScanner.nextLine().trim().matches("(?i)Go")) {
                        System.out.println("Enter Go to continue.");
                    }
                }
            }
        }

        Simulator simulator = new Simulator(players, turn, maxRounds);
        simulator.runGame();
    }
}