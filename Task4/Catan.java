import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.io.File;

// Starts the game: makes the board, players, then runs rounds until someone hits 10 VP or we hit max rounds.
class Catan {

    public static void main(String[] args) throws Exception {

        Board board = new Board();
        Dice dice = new Dice();
        Production production = new Production(board);

        // Read how many rounds from config file (e.g. turns: 100).
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

        // Make 4 players with no stuff yet.
        List<Player> players = new ArrayList<Player>();

        Player p1 = new Player(1, 0, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new HashMap<>());
        Player p2 = new Player(2, 0, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new HashMap<>());
        Player p3 = new Player(3, 0, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new HashMap<>());
        Player p4 = new Player(4, 0, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new HashMap<>());

        players.add(p1);
        players.add(p2);
        players.add(p3);
        players.add(p4);

        // Put 2 settlements per player on the board (each gives 1 VP). First one each.
        board.placeSettlement(board.getIntersection(0), p1);
        p1.addVictoryPoint();
        board.placeSettlement(board.getIntersection(10), p2);
        p2.addVictoryPoint();
        board.placeSettlement(board.getIntersection(20), p3);
        p3.addVictoryPoint();
        board.placeSettlement(board.getIntersection(30), p4);
        p4.addVictoryPoint();

        // Second settlement each (different spots so they are not next to the first
        // ones).
        board.placeSettlement(board.getIntersection(7), p1);
        p1.addVictoryPoint();
        board.placeSettlement(board.getIntersection(13), p2);
        p2.addVictoryPoint();
        board.placeSettlement(board.getIntersection(16), p3);
        p3.addVictoryPoint();
        board.placeSettlement(board.getIntersection(22), p4);
        p4.addVictoryPoint();

        // One turn = roll dice, give out resources, player does one build. Simulator
        // runs round by round.
        Turn turn = new Turn(dice, production, board);
        Simulator simulator = new Simulator(players, turn, maxRounds);

        simulator.runGame();
    }
}