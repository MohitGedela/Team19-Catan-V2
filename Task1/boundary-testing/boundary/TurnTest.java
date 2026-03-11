package boundary;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

class TurnTest {
    private Board board;
    private Dice dice;
    private Production production;
    private Player player;
    private Turn turn;

    @BeforeEach
    void setUp() {
        board = new Board();
        dice = new Dice();
        production = new Production(board);
        Map<ResourceType, Integer> resources = new HashMap<>();
        player = new Player(1, 0, new ArrayList<City>(), new
        ArrayList<Settlement>(), new ArrayList<Road>(), resources);
        turn = new Turn(dice, production, board);
    }

    // Boundary Testing: Round number one is the first valid round.
    @Test
    void testExecuteRoundNumberOne() {
        // Boundary: first valid round
        String result = turn.execute(player, 1);
        assertTrue(result.contains("[1]"), "The execute method should handle round number one correctly.");
    }

    // Boundary Testing: Exactly 7 resources should NOT trigger forced spend.
    @Test
    void testPlayerExactlySevenResources() {
        // Boundary: exactly 7 resources, should NOT trigger forced spend
        Map<ResourceType, Integer> resources = new HashMap<>();
        resources.put(ResourceType.Wood, 3);
        resources.put(ResourceType.Brick, 2);
        resources.put(ResourceType.Sheep, 2);
        Player player7 = new Player(2, 0, new ArrayList<City>(), new
        ArrayList<Settlement>(), new ArrayList<Road>(), resources);
        String result = turn.execute(player7, 1);
        assertFalse(result.contains("forced spend"), "Exactly 7 resources should not trigger a forced spend.");
    }

    // Boundary Testing: 8 resources is just over 7 and SHOULD trigger forced spend.
    @Test
    void testPlayerEightResources() {
        // Boundary: 8 resources, just over 7, should trigger forced spend
        Map<ResourceType, Integer> resources = new HashMap<>();
        resources.put(ResourceType.Wood, 3);
        resources.put(ResourceType.Brick, 3);
        resources.put(ResourceType.Sheep, 2);
        Player player8 = new Player(3, 0, new ArrayList<City>(), new ArrayList<Settlement>(), new ArrayList<Road>(), resources);
        String result = turn.execute(player8, 1);
        assertTrue(result.contains("forced spend"), "8 resources is just over 7 and should trigger a forced spend.");
    }
}
