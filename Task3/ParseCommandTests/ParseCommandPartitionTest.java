import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.*;

class ParseCommandPartitionTest {

    // Helper to make a ParseCommand parser
    private ParseCommand makeParser() {
        return new ParseCommand();
    }

    // Partition Testing: "Roll" is a valid canonical command and should return a Roll instance
    @Test
    void testRollCanonical() {
        ParseCommand parser = makeParser();
        Command cmd = parser.parse("Roll");
        assertNotNull(cmd, "\"Roll\" is a valid canonical command and should return a Roll instance.");
        assertInstanceOf(Roll.class, cmd);
    }

    // Partition Testing: "go" tests lowercase Go input and should return a Go instance that ends the turn
    @Test
    void testGoLowercase() {
        ParseCommand parser = makeParser();
        Command cmd = parser.parse("go");
        assertNotNull(cmd, "\"go\" tests lowercase input and should return a Go instance.");
        assertInstanceOf(Go.class, cmd);
        assertTrue(cmd.endsTurn(), "Go must end the turn.");
    }

    // Partition Testing: "Build settlement 5" is a valid settlement command and should return a Build instance
    @Test
    void testBuildSettlementValid() {
        ParseCommand parser = makeParser();
        Command cmd = parser.parse("Build settlement 5");
        assertNotNull(cmd, "\"Build settlement 5\" is a valid settlement command and should return a Build instance.");
        assertInstanceOf(Build.class, cmd);
    }

    // Partition Testing: "BUILD CITY 45" tests uppercase Build city input and should return a Build instance
    @Test
    void testBuildCityUppercase() {
        ParseCommand parser = makeParser();
        Command cmd = parser.parse("BUILD CITY 45");
        assertNotNull(cmd, "\"BUILD CITY 45\" tests uppercase input and should return a Build instance.");
        assertInstanceOf(Build.class, cmd);
    }

    // Partition Testing: "build road 9 10" tests lowercase Build road input and should return a Build instance
    @Test
    void testBuildRoadValid() {
        ParseCommand parser = makeParser();
        Command cmd = parser.parse("build road 9 10");
        assertNotNull(cmd, "\"build road 9 10\" tests lowercase input and should return a Build instance.");
        assertInstanceOf(Build.class, cmd);
    }

    // Partition Testing: "Build village 7" uses an unrecognised subcommand and should return null
    @Test
    void testBuildUnknownSubcommandReturnsNull() {
        ParseCommand parser = makeParser();
        Command cmd = parser.parse("Build village 7");
        assertNull(cmd, "\"Build village 7\" uses an unrecognised subcommand and should return null.");
    }

    // Partition Testing: An empty string does not match any command pattern and should return null
    @Test
    void testEmptyStringReturnsNull() {
        ParseCommand parser = makeParser();
        Command cmd = parser.parse("");
        assertNull(cmd, "An empty string does not match any command pattern and should return null.");
    }
}
