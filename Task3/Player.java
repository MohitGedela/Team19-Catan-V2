import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

abstract class Player {
    protected int playerID;
    protected int victoryPoints;
    protected List<Building> playerBuildings;
    protected List<Road> playerRoads;
    protected ResourceManager resourceManager;

    public Player(int playerNum, int playerVP, List<Building> buildings, List<Road> roads,
        Map<ResourceType, Integer> resources) {
        playerID = playerNum;
        victoryPoints = playerVP;
        playerBuildings = buildings;
        playerRoads = roads;
        this.resourceManager = new ResourceManager(resources);
    }

    public void addResource(ResourceType resource, int quantity) {
        resourceManager.add(resource, quantity);
    }

    public boolean removeResource(ResourceType resource, int quantity) {
        return resourceManager.remove(resource, quantity);
    }

    public boolean checkResource(ResourceType resource, int quantity) {
        return resourceManager.has(resource, quantity);
    }

    public int getTotalResources() {
        return resourceManager.getTotal();
    }

    public Map<ResourceType, Integer> getResourceMap() {
        return resourceManager.getResourceMap();
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }

    public void addVictoryPoint() {
        victoryPoints++;
    }

    public int getPlayerID() {
        return playerID;
    }

    public List<Building> getPlayerBuildings() {
        return playerBuildings;
    }

    public List<Building> getSettlements() {
        List<Building> settlements = new ArrayList<>();
        for (Building b : playerBuildings) {
            if (b.getBuildingType() == Building.BuildingType.SETTLEMENT) {
                settlements.add(b);
            }
        }
        return settlements;
    }

    public List<Road> getPlayerRoads() {
        return playerRoads;
    }

    public String buildSettlement(Board board, Intersection buildIntersection) {
        int nodeId = buildIntersection.getIntersectionLocation();
        if (!checkResource(ResourceType.Wood, 1) || !checkResource(ResourceType.Brick, 1)
                || !checkResource(ResourceType.Sheep, 1) || !checkResource(ResourceType.Wheat, 1)) {
            return "failed to build settlement at node " + nodeId + " (insufficient resources)";
        }
        if (board.placeSettlement(buildIntersection, this)) {
            removeResource(ResourceType.Wood, 1);
            removeResource(ResourceType.Brick, 1);
            removeResource(ResourceType.Sheep, 1);
            removeResource(ResourceType.Wheat, 1);
            victoryPoints += 1;
            return "built settlement at node " + nodeId;
        }
        return "failed to build settlement at node " + nodeId + " (invalid spot)";
    }

    public String buildCity(Board board, Intersection buildIntersection) {
        int nodeId = buildIntersection.getIntersectionLocation();
        if (!checkResource(ResourceType.Wheat, 2) || !checkResource(ResourceType.Ore, 3)) {
            return "failed to upgrade to city at node " + nodeId + " (insufficient resources)";
        }
        if (board.placeCity(buildIntersection, this)) {
            removeResource(ResourceType.Wheat, 2);
            removeResource(ResourceType.Ore, 3);
            for (int i = 0; i < playerBuildings.size(); i++) {
                if (playerBuildings.get(i).getBuildlocation() == buildIntersection) {
                    playerBuildings.set(i, playerBuildings.get(i).upgrade(this));
                    break;
                }
            }
            victoryPoints += 1;
            return "upgraded settlement to city at node " + nodeId;
        }
        return "failed to upgrade to city at node " + nodeId + " (no settlement or not yours)";
    }

    public String buildRoad(Board board, Edge buildEdge) {
        int startNum = buildEdge.getStart();
        int endNum = buildEdge.getEnd();
        if (!checkResource(ResourceType.Brick, 1) || !checkResource(ResourceType.Wood, 1)) {
            return "failed to build road from " + startNum + " to " + endNum + " (insufficient resources)";
        }
        if (board.placeRoad(buildEdge, this)) {
            removeResource(ResourceType.Brick, 1);
            removeResource(ResourceType.Wood, 1);
            return "built road from " + startNum + " to " + endNum;
        }
        return "failed to build road from " + startNum + " to " + endNum + " (not connected)";
    }

    public void discardCards(Random random) {
        int cardsNum = getTotalResources();
        if (cardsNum > 7) {
            int discardCount = cardsNum / 2;
            for (int i = 0; i < discardCount; i++) {
                ResourceType card = resourceManager.getRandomResource(random);
                removeResource(card, 1);
            }
            System.out.println("Player " + playerID + " discarded " + discardCount + " cards.");
        }
    }

    public String giveRandomResource(Player newPlayer, Random random) {
        if (getTotalResources() != 0) {
            ResourceType card = resourceManager.getRandomResource(random);
            removeResource(card, 1);
            newPlayer.addResource(card, 1);
            return ", Player " + newPlayer.getPlayerID() + " stole " + card + " from Player " + playerID;
        }
        return ", No players to steal from";
    }

    public abstract String takeAction(Board board, Turn turn);
    public abstract void initialSetup(Board board, Scanner scanner, Visualizer visualizer);
    public abstract boolean requiresGoPrompt();
}