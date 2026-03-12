import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.*;

class ParseCommandBoundaryTest {
    private ParseCommand makeParser() {
        return new ParseCommand();
    }

    // Boundary Testing: Node 0 is the lowest valid intersection ID and should be accepted
    @Test
    void testBuildSettlementNodeZero() {
        ParseCommand parser = makeParser();
        Command cmd = parser.parse("Build settlement 0");
        assertNotNull(cmd, "Node 0 is the lowest valid intersection ID and should be accepted.");
    }

    // Boundary Testing: Node 53 is the highest valid intersection ID and should be accepted
    @Test
    void testBuildSettlementNodeFiftyThree() {
        ParseCommand parser = makeParser();
        Command cmd = parser.parse("Build settlement 53");
        assertNotNull(cmd, "Node 53 is the highest valid intersection ID and should be accepted.");
    }

    // Boundary Testing: A negative node ID does not match \\d+ and should return null
    @Test
    void testBuildSettlementNegativeNodeReturnsNull() {
        ParseCommand parser = makeParser();
        Command cmd = parser.parse("Build settlement -1");
        assertNull(cmd, "A negative node ID does not match \\d+ and should return null.");
    }

    // Boundary Testing: A leading space breaks the full-string match required by matches() and should return null
    @Test
    void testRollLeadingSpaceReturnsNull() {
        ParseCommand parser = makeParser();
        Command cmd = parser.parse(" Roll");
        assertNull(cmd, "A leading space breaks the full-string match required by matches() and should return null.");
    }

    // Boundary Testing: A trailing space breaks the full-string match required by matches() and should return null
    @Test
    void testGoTrailingSpaceReturnsNull() {
        ParseCommand parser = makeParser();
        Command cmd = parser.parse("Go ");
        assertNull(cmd, "A trailing space breaks the full-string match required by matches() and should return null.");
    }

    // Boundary Testing: "Build road 5" with only one node does not satisfy the two-node pattern and should return null
    @Test
    void testBuildRoadOneNodeReturnsNull() {
        ParseCommand parser = makeParser();
        Command cmd = parser.parse("Build road 5");
        assertNull(cmd, "\"Build road 5\" with only one node does not satisfy the two-node pattern and should return null.");
    }
}