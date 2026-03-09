import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

// One player: their cards (resources), buildings (settlements/cities/roads), and VP. Can build if they have the right cards.
class ComputerPlayer extends Player {

    private Random random = new Random(); // FIX: single Random instance instead of creating new one every call

    public ComputerPlayer(int playerNum, int playerVP, List<City> cities, List<Settlement> settlements, List<Road> roads, Map<ResourceType, Integer> resources) {
        super(playerNum, playerVP, cities, settlements, roads, resources);
    }

    // If over 7 cards we must try to spend (settlement then city then road). Else
    // pick one build at random and try it.
    @Override
    public String takeAction(Board board, Turn turn) {
        int roll = turn.doRoll(this);

        if (getTotalResources() > 7) {
            // Try settlement
            for (int i = 0; i <= 53; i++) {
                Intersection spot = board.getIntersection(i);
                if (spot != null && spot.getBuilding() == null) {
                    int vpBefore = getVictoryPoints();
                    buildSettlement(board, spot);
                    if (getVictoryPoints() > vpBefore) {
                        return "Rolled " + roll + ", forced spend: built settlement at node " + i;
                    }
                }
            }
            // Try city
            for (int i = 0; i < getPlayerSettlements().size(); i++) {
                Intersection spot = getPlayerSettlements().get(i).getBuildlocation();
                int vpBefore = getVictoryPoints();
                buildCity(board, spot);
                if (getVictoryPoints() > vpBefore) {
                    return "Rolled " + roll + ", forced spend: upgraded settlement to city at node " + spot.getIntersectionLocation();
                }
            }
            // Try road
            for (int i = 0; i <= 53; i++) {
                for (int j = i + 1; j <= 53; j++) {
                    if (board.isValidEdge(i, j)) {
                        int roadsBefore = getPlayerRoads().size();
                        Edge edge = new Edge(i, j);
                        buildRoad(board, edge);
                        if (getPlayerRoads().size() > roadsBefore) {
                            return "Rolled " + roll + ", forced spend: built road at " + i + "-" + j;
                        }
                    }
                }
            }
            return "Rolled " + roll + ", forced spend: not enough resources to build anything";
        }

        int action = random.nextInt(3); // 0 = settlement, 1 = city, 2 = road

        if (action == 0) {
            List<Integer> validSpots = new ArrayList<>();
            for (int i = 0; i <= 53; i++) {
                Intersection intersection = board.getIntersection(i);
                if (intersection != null) {
                    validSpots.add(i);
                }
            }
            if (!validSpots.isEmpty()) {
                int randomIndex = random.nextInt(validSpots.size());
                int nodeId = validSpots.get(randomIndex);
                Intersection target = board.getIntersection(nodeId);
                int vpBefore = getVictoryPoints();
                buildSettlement(board, target);
                if (getVictoryPoints() > vpBefore) {
                    return "Rolled " + roll + ", built settlement at node " + nodeId;
                }
                return "Rolled " + roll + ", failed to build settlement at node " + nodeId + " (invalid spot or insufficient resources)";
            }

        } else if (action == 1) {
            if (!getPlayerSettlements().isEmpty()) {
                int randomIndex = random.nextInt(getPlayerSettlements().size());
                Intersection target = getPlayerSettlements().get(randomIndex).getBuildlocation();
                int vpBefore = getVictoryPoints();
                buildCity(board, target);
                if (getVictoryPoints() > vpBefore) {
                    return "Rolled " + roll + ", upgraded settlement to city at node " + target.getIntersectionLocation();
                }
                return "Rolled " + roll + ", failed to upgrade to city at node " + target.getIntersectionLocation() + " (insufficient resources)";
            }

        } else {
            List<int[]> validEdges = new ArrayList<>();
            for (int i = 0; i <= 53; i++) {
                for (int j = i + 1; j <= 53; j++) {
                    if (board.isValidEdge(i, j)) {
                        validEdges.add(new int[] { i, j });
                    }
                }
            }
            if (!validEdges.isEmpty()) {
                int randomIndex = random.nextInt(validEdges.size());
                int[] picked = validEdges.get(randomIndex);
                Edge edge = new Edge(picked[0], picked[1]);
                int roadsBefore = getPlayerRoads().size();
                buildRoad(board, edge);
                if (getPlayerRoads().size() > roadsBefore) {
                    return "Rolled " + roll + ", built road from node " + picked[0] + " to " + picked[1];
                }
                return "Rolled " + roll + ", failed to build road from node " + picked[0] + " to " + picked[1] + " (not connected or insufficient resources)";
            }
        }

        return "Rolled " + roll + ", no action taken";
    }

    @Override
    public void initialSetup(Board board, Scanner scanner, Visualizer visualizer) {
        // Place settlement
        List<Integer> validSpots = new ArrayList<>();
        for (int i = 0; i <= 53; i++) {
            Intersection spot = board.getIntersection(i);
            if (spot == null) continue; // FIX: null check to prevent NullPointerException
            boolean valid = spot.getBuilding() == null;
            for (int neighbour : board.getNeighbouringIntersections(i)) {
                Intersection n = board.getIntersection(neighbour);
                if (n == null) continue; // FIX: null check for neighbours
                if (n.getBuilding() != null) {
                    valid = false;
                    break;
                }
            }
            if (valid) {
                validSpots.add(i);
            }
        }

        int picked = validSpots.get(random.nextInt(validSpots.size()));
        board.placeSettlement(board.getIntersection(picked), this);
        addVictoryPoint();
        System.out.println("\nPlayer " + playerID + " placed settlement at node " + picked);
        visualizer.refresh();

        // Place adjacent road
        List<Integer> neighbours = board.getNeighbouringIntersections(picked);
        int roadEnd = neighbours.get(random.nextInt(neighbours.size()));

        Edge edge = new Edge(picked, roadEnd);
        board.placeRoad(edge, this);
        System.out.println("Player " + playerID + " placed road at " + picked + "-" + roadEnd);
        visualizer.refresh();

        try {
            Thread.sleep(600);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("Enter Go to continue.");
        while (!scanner.nextLine().trim().matches("(?i)Go")) {
            System.out.println("Enter Go to continue.");
        }
    }
}