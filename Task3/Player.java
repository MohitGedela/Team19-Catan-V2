import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

// One player: their cards (resources), buildings (settlements/cities/roads), and VP. Can build if they have the right cards.
abstract class Player {
    protected int playerID;
    protected int victoryPoints;
    protected List<City> playerCities;
    protected List<Settlement> playerSettlements;
    protected List<Road> playerRoads;
    protected Map<ResourceType, Integer> playerResources;
    protected Visualizer visualizer;

    public Player(int playerNum, int playerVP, List<City> cities, List<Settlement> settlements, List<Road> roads,
            Map<ResourceType, Integer> resources) {
        playerID = playerNum;
        victoryPoints = playerVP;
        playerCities = cities;
        playerSettlements = settlements;
        playerResources = resources;
        playerRoads = roads;
    }

    public void addResource(ResourceType resource, int quantity) {
        playerResources.put(resource, playerResources.getOrDefault(resource, 0) + quantity);
    }

    public void setVisualizer(Visualizer visualizer) {
        this.visualizer = visualizer;
    }

    public abstract void initialSetup(Board board, Scanner scanner, Visualizer visualizer);

    // Take cards from hand. Returns false if not enough.
    public boolean removeResource(ResourceType resource, int quantity) {
        int have = playerResources.getOrDefault(resource, 0);
        if (have < quantity) {
            System.out.println("Unsuccesful not enough resources");
            return false;
        }
        playerResources.put(resource, have - quantity);
        return true;
    }

    public boolean checkResource(ResourceType resource, int quantity) {
        if (playerResources.containsKey(resource) && playerResources.get(resource) >= quantity) {
            return true;
        } else {
            return false;
        }
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }

    public void addVictoryPoint() {
        victoryPoints++;
    }

    public int getTotalResources() {
        int total = 0;
        for (int amount : playerResources.values()) {
            total += amount;
        }
        return total;
    }

    // Costs 1 wood, 1 brick, 1 sheep, 1 wheat. Only builds if board says the spot
    // is ok.
    public void buildSettlement(Board board, Intersection buildIntersection) {
        if (!checkResource(ResourceType.Wood, 1) || !checkResource(ResourceType.Brick, 1)
                || !checkResource(ResourceType.Sheep, 1) || !checkResource(ResourceType.Wheat, 1)) {
            // System.out.println("Not enough resources to build settlement");
            return;
        }
        if (board.placeSettlement(buildIntersection, this)) {
            removeResource(ResourceType.Wood, 1);
            removeResource(ResourceType.Brick, 1);
            removeResource(ResourceType.Sheep, 1);
            removeResource(ResourceType.Wheat, 1);
            victoryPoints += 1;
            visualizer.refresh();
            System.out.println("Succesfully built a settlement at node: " + buildIntersection.getIntersectionLocation());
        }
        else {
            System.out.println("Cannot build settlement at node " + buildIntersection.getIntersectionLocation() + " - spot is invalid or too close to another building.");
        }
        
    }

    // Costs 2 wheat, 3 ore. Replaces one of your settlements with a city (same spot).
    public void buildCity(Board board, Intersection buildIntersection) {
        if (!checkResource(ResourceType.Wheat, 2) || !checkResource(ResourceType.Ore, 3)) {
            // System.out.println("Not enough resources to build city");
            return;
        }
        if (board.placeCity(buildIntersection, this)) {
            removeResource(ResourceType.Wheat, 2);
            removeResource(ResourceType.Ore, 3);

            for (int i = 0; i < playerSettlements.size(); i++) {
                if (playerSettlements.get(i).getBuildlocation() == buildIntersection) {
                    playerSettlements.remove(i);
                    break;
                }
            }

            playerCities.add(new City(buildIntersection, this));
            victoryPoints += 1;
            visualizer.refresh();
        } else {
            System.out.println("Cannot build city at node " + buildIntersection.getIntersectionLocation() + " - no settlement there or not yours.");
        }
    }

    public void buildRoad(Board board, Edge buildEdge) {
        int startNum = buildEdge.getStart();
        int endNum = buildEdge.getEnd();

        if (!checkResource(ResourceType.Brick, 1) || !checkResource(ResourceType.Wood, 1)) {
            // System.out.println("Not enough resources to build road");
            return;
        }
        if (board.placeRoad(buildEdge, this)) {
            removeResource(ResourceType.Brick, 1);
            removeResource(ResourceType.Wood, 1);
            visualizer.refresh();
            System.out.println("Succesfully built a road from " + startNum + " to " + endNum);
        }
        else {
            System.out.println("Cannot build road from " + startNum + " to " + endNum + " - not connected to your network.");
        }
    }

    // If over 7 cards we must try to spend (settlement then city then road). Else pick one build at random and try it.
    public abstract String takeAction(Board board, Turn turn);

    public List<Settlement> getPlayerSettlements() {
        return playerSettlements;
    }

    public List<Road> getPlayerRoads() {
        return playerRoads;
    }

    public int getPlayerID() {
        return playerID;
    }

    /**
     * Returns a copy of the player's resource counts (for display, e.g. List
     * command).
     */
    public Map<ResourceType, Integer> getResourceMap() {
        return new HashMap<>(playerResources);
    }

    private ResourceType getRandomResource(Random random) {
        Map<ResourceType, Integer> resourceMap = getResourceMap();
        List<ResourceType> resources = new ArrayList<>();
        for (ResourceType resource : resourceMap.keySet()) {
            int count = resourceMap.get(resource);
            for (int i = 0; i < count; i++) {
                resources.add(resource);
            }
        }
        return resources.get(random.nextInt(resources.size()));
    }

    public void discardCards(Random random) {
        int cardsNum = getTotalResources();
        if (cardsNum > 7) {
            int discardCount = cardsNum / 2;
            for (int i = 0; i < discardCount; i++) {
                ResourceType card = getRandomResource(random);
                removeResource(card, 1);
            }
            System.out.println("Player " + playerID + " discarded " + discardCount + " cards.");
        }
    }

    public String giveRandomResource(Player newPlayer, Random random) {
        if (getTotalResources() != 0) {
            ResourceType card = getRandomResource(random);
            removeResource(card, 1);
            newPlayer.addResource(card, 1);
            return(", Player " + newPlayer.getPlayerID() + " stole " + card + " from Player " + playerID);
        }
        return(", No players to steal from");
    }
}