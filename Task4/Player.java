import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

// One player: their cards (resources), buildings (settlements/cities/roads), and VP. Can build if they have the right cards.
abstract class Player {
    private int playerID;
    private int victoryPoints;
    private List<City> playerCities;
    private List<Settlement> playerSettlements;
    private List<Road> playerRoads;
    private Map<ResourceType, Integer> playerResources;

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
            return;
        }
        if (board.placeSettlement(buildIntersection, this)) {
            removeResource(ResourceType.Wood, 1);
            removeResource(ResourceType.Brick, 1);
            removeResource(ResourceType.Sheep, 1);
            removeResource(ResourceType.Wheat, 1);
            victoryPoints += 1;
        }
    }

    // Costs 2 wheat, 3 ore. Replaces one of your settlements with a city (same
    // spot).
    public void buildCity(Board board, Intersection buildIntersection) {
        if (!checkResource(ResourceType.Wheat, 2) || !checkResource(ResourceType.Ore, 3)) {
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
        }
    }

    public void buildRoad(Board board, Edge buildEdge) {
        if (!checkResource(ResourceType.Brick, 1) || !checkResource(ResourceType.Wood, 1)) {
            return;
        }
        if (board.placeRoad(buildEdge, this)) {
            removeResource(ResourceType.Brick, 1);
            removeResource(ResourceType.Wood, 1);
        }
    }

    // If over 7 cards we must try to spend (settlement then city then road). Else
    // pick one build at random and try it.
    public abstract String takeAction(Board board);

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
                ResourceType card = getRandomResource(null);
                removeResource(card, 1);
            }
            System.out.println("Player " + playerID + " discarded" + discardCount + " cards.");
        }
    }

    public void giveRandomResource(Player newPlayer, Random random) {
        if (getTotalResources() != 0) {
            ResourceType card = getRandomResource(random);
            removeResource(card, 1);
            newPlayer.addResource(card, 1);
            System.out.println("Player " + newPlayer.getPlayerID() + " stole " + card + " to Player " + playerID);
        }
    }
}
