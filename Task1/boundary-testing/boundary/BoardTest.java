package boundary;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.*;

class BoardTest {
    private Player makePlayer() {
        Map<ResourceType, Integer> resources = new HashMap<>();
        resources.put(ResourceType.Wood, 0);
        resources.put(ResourceType.Brick, 0);
        resources.put(ResourceType.Sheep, 0);
        resources.put(ResourceType.Wheat, 0);
        resources.put(ResourceType.Ore, 0);
        return new Player(1, 0, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), resources);
    }

    // Boundary Testing: Intersection 0 is the lowest valid ID and should exist
    @Test
    void testIntersectionLowerBoundary() {
        Board board = new Board();
        assertNotNull(board.getIntersection(0), "Intersection 0 is the lowest valid ID and should exist.");
    }

    // Boundary Testing: Intersection 53 is the highest valid ID and should exist
    @Test
    void testIntersectionUpperBoundary() {
        Board board = new Board();
        assertNotNull(board.getIntersection(53), "Intersection 53 is the highest valid ID and should exist.");
    }

    // Boundary Testing: Anything above 53 is out of range and should return null
    @Test
    void testIntersectionAboveBoundary() {
        Board board = new Board();
        assertNull(board.getIntersection(54), "Intersection 54 is out of range and should return null.");
    }

    // Boundary Testing: A player should not be able to place a third settlement freely
    @Test
    void testSettlementLimit() {
        Board board = new Board();
        Player player = makePlayer();
        board.placeSettlement(board.getIntersection(10), player);
        board.placeSettlement(board.getIntersection(20), player);
        boolean result = board.placeSettlement(board.getIntersection(30), player);
        assertFalse(result, "A player should not freely place a third settlement without a connected road");
    }
}