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

    // move
    @Override
    public String takeAction(Board board, Turn turn) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Please enter one of the following commands:");
            System.out.println("Roll");
            System.out.println("Go");
            System.out.println("List");
            System.out.println("Build settlement [nodeID]");
            System.out.println("Build city [nodeID]");
            System.out.println("Build road [fromNodeID], [endID]");

            String userInput = scanner.nextLine().trim();
            if (userInput.matches("(?i)Roll")) {
                int roll = turn.doRoll(this);
                System.out.println("Rolled " + roll);

            } else if (userInput.matches("(?i)Build\\s+settlement\\s+\\d+")) {
                String[] fragments = userInput.split("\\s+");
                int nodeId = Integer.parseInt(fragments[2]);
                Intersection tileIntersection = board.getIntersection(nodeId);
                if (tileIntersection == null) {
                    System.out.println("Invalid node ID: " + nodeId);
                } else {
                    buildSettlement(board, tileIntersection);
                    System.out.println("Attempted to build a settlement at node: " + nodeId);
                }

            } else if (userInput.matches("(?i)Build\\s+city\\s+\\d+")) {
                String[] fragments = userInput.split("\\s+");
                int nodeId = Integer.parseInt(fragments[2]);
                Intersection tileIntersection = board.getIntersection(nodeId);
                if (tileIntersection == null) {
                    System.out.println("Invalid node ID: " + nodeId);
                } else {
                    buildCity(board, tileIntersection);
                    System.out.println("Attempted to build a city at node: " + nodeId);
                }

            } else if (userInput.matches("(?i)Build\\s+road\\s+\\d+\\s+\\d+")) {
                String[] fragments = userInput.split("\\s+");
                int startNum = Integer.parseInt(fragments[2]);
                int endNum = Integer.parseInt(fragments[3]);
                if (!board.isValidEdge(startNum, endNum)) {
                    System.out.println("Invalid edge: " + startNum + "-" + endNum);
                } else {
                    Edge tileEdge = new Edge(startNum, endNum);
                    buildRoad(board, tileEdge);
                    System.out.println("Attempted to build a road from node " + startNum + " to " + endNum);
                }

            } else if (userInput.matches("(?i)List")) {
                System.out.println("Current resources:");
                System.out.println(resources);

            } else if (userInput.matches("(?i)Go")) {
                break;

            } else {
                System.out.println("Invalid command try again!");
            }
        }
        return "ended turn";
    }
}