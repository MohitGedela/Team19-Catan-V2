package partition;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

public class SettlementTest {

    private Player makePlayer() {
        Map<ResourceType, Integer> resources = new HashMap<>();
        List<Settlement> settlements = new ArrayList<>();
        List<City> cities = new ArrayList<>();
        List<Road> roads = new ArrayList<>();
        return new Player(1, 0, cities, settlements, roads, resources);
    }

    // Partition testing: verifies the victory-point partition for settlements to be 1.
    @Test
    public void testVictoryPointsReturnsOne() {
        Player player = makePlayer();
        Settlement settlement = new Settlement(new Intersection(1), player);
        assertEquals(1, settlement.getVictoryPoints(), "should return 1 victory point");
    }

    // Partition testing: allows null owners without failure.
    @Test
    public void testNullOwnerIsHandled() {
        Intersection intersection = new Intersection(1);
        Settlement settlement = new Settlement(intersection, null);
        assertSame(intersection, settlement.getBuildlocation(), "intersection should still be stored");
        assertNull(settlement.getOwner(), "owner can be null");
    }
}