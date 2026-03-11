package partition;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

public class CityTest {

    private Player makePlayer() {
        Map<ResourceType, Integer> resources = new HashMap<>();
        List<Settlement> settlements = new ArrayList<>();
        List<City> cities = new ArrayList<>();
        List<Road> roads = new ArrayList<>();
        return new Player(1, 0, cities, settlements, roads, resources);
    }

    // Partition testing: verifies if the same intersection and owner objects are tracked.
    @Test
    public void testBuildLocationAndOwnerAreStored() {
        Intersection intersection = new Intersection(1);
        Player player = makePlayer();
        City city = new City(intersection, player);
        assertSame(intersection, city.getBuildlocation(), "stores the correct intersection");
        assertSame(player, city.getOwner(), "stores the correct owner");
    }

    // Partition testing: testVPReturnsTwo ensures 2 victory points are returned for city.
    @Test
    public void testVPReturnsTwo() {
        Player player = makePlayer();
        City city = new City(new Intersection(1), player);
        assertEquals(2, city.getVictoryPoints(), "should return 2 victory points");
    }
}