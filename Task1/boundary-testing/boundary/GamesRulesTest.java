package boundary;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.*;

class GameRulesTest {
    private Player makePlayer() {
        Map<ResourceType, Integer> resources = new HashMap<>();
        resources.put(ResourceType.Wood, 0);
        resources.put(ResourceType.Brick, 0);
        resources.put(ResourceType.Sheep, 0);
        resources.put(ResourceType.Wheat, 0);
        resources.put(ResourceType.Ore, 0);
        return new Player(1, 0, new ArrayList<>(), new ArrayList<>(), new
        ArrayList<>(), resources);
    }

    // Boundary Testing: Intersection 0 is the lowest valid ID and should be empty at the start
    @Test
    void testCheckEmptyIntersectionLowerBoundary() {
        Board board = new Board();
        GameRules rules = new GameRules();
        assertTrue(rules.checkEmptyIntersections(0, board), "Intersection 0 is the lowest valid ID and should be empty at the start.");
    }

    // Boundary Testing: A road with no settlement or road nearby should not be allowed
    @Test
    void testCheckRoadPlacementNoConnection() {
        Board board = new Board();
        GameRules rules = new GameRules();
        Player player = makePlayer();
        assertFalse(rules.checkRoadPlacement(new Edge(10, 2), player, board), "A road with no nearby settlement or road should not be allowed.");
    }
}