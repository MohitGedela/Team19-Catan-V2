package partition;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ResourceHexTest {

    // Partition testing: verifies that a valid resource hex returns the correct resource type.
    @Test
    public void testProduceResourceReturnsCorrectType() {
        ResourceHex hex = new ResourceHex(1, new HexBoardNum(6), ResourceType.Wood);
        assertEquals(ResourceType.Wood, hex.produceResource(), "Resource hex should return the correct resource type.");
    }

    // Partition testing: verifies that production status is always true for a resource hex.
    @Test
    public void testProductionStatusReturnsTrue() {
        ResourceHex hex = new ResourceHex(1, new HexBoardNum(6), ResourceType.Brick);
        assertTrue(hex.productionStatus(), "Resource hex production status should always be true.");
    }
}