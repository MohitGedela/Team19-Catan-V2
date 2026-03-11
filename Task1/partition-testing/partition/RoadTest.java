package partition;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

public class RoadTest {

    private Player makePlayer() {
        Map<ResourceType, Integer> resources = new HashMap<>();
        List<Settlement> settlements = new ArrayList<>();
        List<City> cities = new ArrayList<>();
        List<Road> roads = new ArrayList<>();
        return new Player(1, 0, cities, settlements, roads, resources);
    }

    // Partition testing: verifies that the correct owner and edge location are stored when a road is created with valid inputs.
    @Test
    public void testLocationAndOwnerAreStored() {
        Edge edge = new Edge(1, 5);
        Player player = makePlayer();
        Road road = new Road(player, edge);
        assertSame(edge, road.getLocation(), "stores the correct edge");
        assertSame(player, road.getOwner(), "stores the correct owner");
    }

    // Partition testing: null owner inputs are handled and edge location is still stored correctly.
    @Test
    public void testNullOwnerIsHandled() {
        Edge edge = new Edge(1, 5);
        Road road = new Road(null, edge);
        assertNull(road.getOwner(), "owner can be null");
        assertSame(edge, road.getLocation(), "edge should still be stored");
    }
}