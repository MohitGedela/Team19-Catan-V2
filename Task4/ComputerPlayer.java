import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Random;

// One player: their cards (resources), buildings (settlements/cities/roads), and VP. Can build if they have the right cards.
class ComputerPlayer extends Player {

    public ComputerPlayer(int playerNum, int playerVP, List<City> cities, List<Settlement> settlements, List<Road> roads, Map<ResourceType, Integer> resources) {
        super(playerNum, playerVP, cities, settlements, roads, resources);
    }

    // If over 7 cards we must try to spend (settlement then city then road). Else
    // pick one build at random and try it.
    @Override
    public String takeAction(Board board, Turn turn) {
        Random random = new Random();
        int roll = turn.doRoll(this);

        if (getTotalResources() > 7) {
            for (int i = 0; i <= 53; i++) {
                Intersection spot = board.getIntersection(i);
                if (spot != null && spot.getBuilding() == null) {
                    int vpBefore = victoryPoints;
                    buildSettlement(board, spot);
                    if (victoryPoints > vpBefore) {
                        return "Rolled " + roll + ", forced spend: built settlement at " + i;
                    }
                }
            }
            for (int i = 0; i < playerSettlements.size(); i++) {
                Intersection spot = playerSettlements.get(i).getBuildlocation();
                int vpBefore = victoryPoints;
                buildCity(board, spot);
                if (victoryPoints > vpBefore) {
                    return "Rolled " + roll + ", forced spend: upgraded to city at " + spot.getIntersectionLocation();
                }
            }
            for (int i = 0; i <= 53; i++) {
                for (int j = i + 1; j <= 53; j++) {
                    if (board.isValidEdge(i, j)) {
                        int roadsBefore = playerRoads.size();
                        Edge edge = new Edge(i, j);
                        buildRoad(board, edge);
                        if (playerRoads.size() > roadsBefore) {
                            return "Rolled " + roll + ", forced spend: built road at " + i + "-" + j;
                        }
                    }
                }
            }
            return "Rolled " + roll + ", forced spend: could not build anything";
        }

        int action = random.nextInt(3); // 0 = settlement, 1 = city, 2 = road.

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
                Intersection target = board.getIntersection(validSpots.get(randomIndex));
                buildSettlement(board, target);
                return "Rolled " + roll + ", attempted settlement at " + validSpots.get(randomIndex);
            }

        } else if (action == 1) {
            if (!playerSettlements.isEmpty()) {
                int randomIndex = random.nextInt(playerSettlements.size());
                Intersection target = playerSettlements.get(randomIndex).getBuildlocation();
                buildCity(board, target);
                return "Rolled " + roll + ", attempted city at " + target.getIntersectionLocation();
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
                buildRoad(board, edge);
                return "Rolled " + roll + ", attempted road at edge " + picked[0] + "-" + picked[1];
            }
        }

        return "Rolled " + roll + ", no action taken";
    }
}