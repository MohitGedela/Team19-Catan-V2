package boundary;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.*;

class PlayerTest {
    private Player makePlayer() {
        Map<ResourceType, Integer> resources = new HashMap<>();
        resources.put(ResourceType.Wood, 0);
        resources.put(ResourceType.Brick, 0);
        resources.put(ResourceType.Sheep, 0);
        resources.put(ResourceType.Wheat, 0);
        resources.put(ResourceType.Ore, 0);
        List<Settlement> settlements = new ArrayList<>();
        List<City> cities = new ArrayList<>();
        List<Road> roads = new ArrayList<>();
        return new Player(1, 0, cities, settlements, roads, resources);
    }

    // Boundary Testing: Removing more resources than available should return false.
    @Test
    void testRemoveMoreThanAvailable() {
        Player player = makePlayer();
        player.addResource(ResourceType.Wood, 1);
        boolean result = player.removeResource(ResourceType.Wood, 2);
        assertFalse(result, "Removing more resources than available should return false.");
    }

    // Boundary Testing: Removing exactly all resources should leave zero.
    @Test
    void testRemoveAllResources() {
        Player player = makePlayer();
        player.addResource(ResourceType.Wood, 3);
        player.removeResource(ResourceType.Wood, 3);
        assertEquals(0, player.getTotalResources(), "Removing all resources should leave 0 total resources.");
    }

    // Boundary Testing: buildSettlement should build correctly at boundary intersection 0.
    @Test
    void testBuildSettlementAtBoundaryIntersection() {
        Player player = makePlayer();
        Board board = new Board();
        player.addResource(ResourceType.Wood, 1);
        player.addResource(ResourceType.Brick, 1);
        player.addResource(ResourceType.Sheep, 1);
        player.addResource(ResourceType.Wheat, 1);
        player.buildSettlement(board, board.getIntersection(0));
        assertEquals(1, player.getVictoryPoints(), "Player should have 1 VP after building at intersection 0.");
    }

    // Boundary Testing: takeRandomAction should return no action taken when no valid actions exist.
    @Test
    void testTakeRandomActionNoActionTaken() {
        Player player = makePlayer();
        Board board = new Board();

        // Run many times with no resources to try to hit no action taken
        boolean noActionTaken = false;
        for (int i = 0; i < 100; i++) {
            String result = player.takeRandomAction(board);
            if (result.equals("no action taken")) {
                noActionTaken = true;
                break;
            }
        }

        assertTrue(noActionTaken, "takeRandomAction should return no action taken when no valid actions exist.");
    }
}