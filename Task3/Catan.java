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

        Player p1 = new ComputerPlayer(1, 0, new ArrayList<>(), new ArrayList<>(), new HashMap<>());
        Player p2 = new ComputerPlayer(2, 0, new ArrayList<>(), new ArrayList<>(), new HashMap<>());
        Player p3 = new ComputerPlayer(3, 0, new ArrayList<>(), new ArrayList<>(), new HashMap<>());

        players.add(p1);
        players.add(p2);
        players.add(p3);

        Turn turn = new Turn(dice, production, board, players);
        Player p4 = new HumanPlayer(4, 0, new ArrayList<>(), new ArrayList<>(), new HashMap<>());
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
                player.initialSetup(board, inputScanner, visualizer);
            }
        }

        Simulator simulator = new Simulator(players, turn, maxRounds);
        simulator.runGame();
    }
}