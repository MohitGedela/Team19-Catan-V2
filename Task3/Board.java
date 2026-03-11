import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.List;

// The map: 54 corners (intersections), 19 hexes with resources and numbers. Tracks where buildings and roads are.
class Board {
    private int[][] tilesNodes = { 
        { 42, 40, 41, 16, 18, 38 }, { 41, 43, 44, 19, 17, 16 }, { 44, 45, 46, 47, 20, 19 },
        { 39, 38, 18, 13, 15, 35 }, { 18, 16, 17, 0, 5, 13 }, { 17, 19, 20, 21, 1, 0 },
        { 20, 47, 48, 49, 22, 21 }, { 37, 35, 15, 14, 34, 36 }, { 15, 13, 5, 4, 12, 14 },
        { 5, 0, 1, 2, 3, 4 }, { 1, 21, 22, 23, 6, 2 }, { 22, 49, 50, 51, 52, 23 },
        { 34, 14, 12, 11, 32, 33 }, { 12, 4, 3, 9, 10, 11 }, { 3, 2, 6, 7, 8, 9 },
        { 6, 23, 52, 53, 24, 7 }, { 32, 11, 10, 29, 30, 31 }, { 10, 9, 8, 27, 28, 29 },
        { 8, 7, 24, 25, 26, 27 } 
    };

    private Map<Integer, Intersection> intersections;
    private Map<Integer, HexTerrain> tiles;
    private List<int[]> builtEdges = new ArrayList<>();
    private GameRules rules = new GameRules();
    private int robberHexID;

    public Board() {
        intersections = new HashMap<>();
        robberHexID = 9;
        for (int i = 0; i <= 53; i++) {
            intersections.put(i, new Intersection(i));
        }

        tiles = new HashMap<>();

        ResourceType[] resources = {
            ResourceType.Wood, ResourceType.Wood, ResourceType.Wood, ResourceType.Wood,
            ResourceType.Brick, ResourceType.Brick, ResourceType.Brick,
            ResourceType.Wheat, ResourceType.Wheat, ResourceType.Wheat, ResourceType.Wheat,
            ResourceType.Sheep, ResourceType.Sheep, ResourceType.Sheep, ResourceType.Sheep,
            ResourceType.Ore, ResourceType.Ore, ResourceType.Ore
        };

        int[] numbers = { 2, 3, 3, 4, 4, 5, 5, 6, 6, 8, 8, 9, 9, 10, 10, 11, 11, 12 };

        tiles.put(9, new DesertHex(9));

        int resourceIndex = 0;
        for (int i = 0; i < 19; i++) {
            if (i == 9) {
                continue;
            }
            tiles.put(i, new ResourceHex(i, new HexBoardNum(numbers[resourceIndex]), resources[resourceIndex]));
            resourceIndex++;
        }
    }

    // True if the two corners are next to each other on the hex map (share an
    // edge).
    public boolean isValidEdge(int start, int end) {
        for (int i = 0; i < 19; i++) {
            for (int j = 0; j < 6; j++) {
                if (j < 5) {
                    if ((tilesNodes[i][j] == start && tilesNodes[i][j + 1] == end) || (tilesNodes[i][j] == end && tilesNodes[i][j + 1] == start)) {
                        return true;
                    }
                }
                
                if ((tilesNodes[i][0] == start && tilesNodes[i][5] == end) || (tilesNodes[i][0] == end && tilesNodes[i][5] == start)) {
                    return true;
                }
            }
        }
        return false;
    }

    // All corners that are one edge away from this corner.
    public List<Integer> getNeighbouringIntersections(int intersectionID) {
        List<Integer> neighbours = new ArrayList<>();

        for (int i = 0; i <= 53; i++) {
            if (i == intersectionID) {
                continue;
            }

            if (isValidEdge(intersectionID, i)) {
                neighbours.add(i);
            }
        }

        return neighbours;
    }

    public Intersection getIntersection(int intersectionID) {
        return intersections.get(intersectionID);
    }

    // Ok if spot is empty and no neighbour has a building (and player has < 2), or
    // if spot is connected by player's road.
    public boolean placeSettlement(Intersection placeIntersection, Player player) {
        if (rules.checkEmptyIntersections(placeIntersection.getIntersectionLocation(), this)) {
            if (player.getPlayerSettlements().size() < 2) {
                Settlement settlement = new Settlement(placeIntersection, player);
                placeIntersection.setBuilding(settlement);
                placeIntersection.setOwner(player);
                player.getPlayerSettlements().add(settlement);
                return true;
            }
        } else {
            if (rules.isConnected(placeIntersection.getIntersectionLocation(), player, this)) {
                Settlement settlement = new Settlement(placeIntersection, player);
                placeIntersection.setBuilding(settlement);
                placeIntersection.setOwner(player);
                player.getPlayerSettlements().add(settlement);
                return true;
            }
        }
        return false;
    }

    // Only works if there is your settlement there; replaces it with a city.
    public boolean placeCity(Intersection placeIntersection, Player player) {
        Building existing = placeIntersection.getBuilding();
        if (existing instanceof Settlement && existing.getOwner() == player) {
            City city = new City(placeIntersection, player);
            placeIntersection.setBuilding(city);
            return true;
        }
        return false;
    }

    public boolean isEdgeOccupied(int start, int end) {
        for (int[] edge : builtEdges) {
            if ((edge[0] == start && edge[1] == end) || (edge[0] == end && edge[1] == start)) {
                return true;
            }
        }
        return false;
    }

    // Road must be on a valid edge, not taken, and next to your building or your
    // road.
    public boolean placeRoad(Edge placeEdge, Player player) {
        if (rules.checkRoadPlacement(placeEdge, player, this)) {
            Road road = new Road(player, placeEdge);
            player.getPlayerRoads().add(road);
            builtEdges.add(new int[] { placeEdge.getStart(), placeEdge.getEnd() });
            return true;
        }
        return false;
    }

    public List<HexTerrain> getHexes() {
        return new ArrayList<>(tiles.values());
    }

    public HexTerrain getHex(int hexID) {
        return tiles.get(hexID);
    }

    public List<Integer> getHexIntersections(int hexID) {
        List<Integer> hexes = new ArrayList<>();

        for (int hex : tilesNodes[hexID]) {
            hexes.add(hex);
        }

        return hexes;
    }

    public int getRobberHexID() {
        return robberHexID;
    }

    public void moveRobber(Random random) {
        int moveTile = 0;
        while (moveTile == robberHexID) {
            moveTile = random.nextInt(19);
        }
        robberHexID = moveTile;
    }

    public List<Player> getNearPlayers(List<Player> players) {
        List<Integer> nearbyIntersections = getHexIntersections(robberHexID);
        List<Player> playersList = new ArrayList<>();

        for (Player player: players) {
            for (int intersectionID: nearbyIntersections) {
                Intersection intersection = getIntersection(intersectionID);
                if (intersection != null && intersection.getPlayer() == player) {
                    if (!playersList.contains(player)) {
                        playersList.add(player);
                    }
                }
            }
        }
        return playersList;
    }
}