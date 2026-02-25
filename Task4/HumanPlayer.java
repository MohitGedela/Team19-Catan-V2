import java.util.List;
import java.util.Map;
import java.util.Scanner;

class HumanPlayer extends Player {
    private Map<ResourceType, Integer> resources;

    public HumanPlayer(int playerNum, int playerVP, List<City> cities, List<Settlement> settlements, List<Road> roads,
            Map<ResourceType, Integer> resources) {
        super(playerNum, playerVP, cities, settlements, roads, resources);
        this.resources = resources;
    }

    // Fix this method's string returns to actually print the attempted human player move
    @Override
    public String takeAction(Board board) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Please enter one of the following commands:");
            System.out.println("List"); // Lists the current hand of the player
            System.out.println("Build settlement [tileNum]");
            System.out.println("Build city [tileNum]");
            System.out.println("Build road [startID] [endID]");
            System.out.println("Go");

            String userInput = scanner.nextLine().trim();

            if (userInput.matches("(?i)Build\\s+settlement\\s+\\d+")) {
                String[] fragments = userInput.split("\\s+");
                int tileNum = Integer.parseInt(fragments[2]);
                Intersection tileIntersection = new Intersection(tileNum);
                buildSettlement(board, tileIntersection);

                return("hi"); // Fix return the attempted move
            } 
            else if (userInput.matches("(?i)Build\\s+city\\s+\\d+")) {
                String[] fragments = userInput.split("\\s+");
                int tileNum = Integer.parseInt(fragments[2]);
                Intersection tileIntersection = new Intersection(tileNum);
                buildCity(board, tileIntersection);

                return("hi"); // Fix return the attempted move
            } 
            else if (userInput.matches("(?i)Build\\s+road\\s+\\d+\\s+\\d+")) {
                String[] fragments = userInput.split("\\s+");
                int startNum = Integer.parseInt(fragments[2]);
                int endNum = Integer.parseInt(fragments[3]);
                Edge tileEdge = new Edge(startNum, endNum);
                buildRoad(board, tileEdge);

                return("hi"); // Fix return the attempted move
            } 
            else if (userInput.matches("(?i)List")) {
                System.out.println("Current resources:");
                System.out.println(resources);
                return "hi";
            } 
            else if (userInput.matches("(?i)Go")) {
                break;
            } 
            else {
                System.out.println("Invalid command try again!");
            }
        }
        return "hi"; // Fix
    }
}