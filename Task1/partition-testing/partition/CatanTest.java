package partition;
import org.junit.jupiter.api.Test;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CatanTest {
    private static final Path CONFIG_PATH = Paths.get("config.txt");

    // Partition testing: Ensures the game completes one full round without throwing exception.
    @Test
    void testMainRunsWithOneRound() throws Exception {
        Files.write(CONFIG_PATH, "turns: 1".getBytes());
        Catan.main(new String[]{});
    }

    // Partition testing: Ensures a negative round count does not affect the program.
    @Test
    void testMainRunsWithNegativeRounds() throws Exception {
        Files.write(CONFIG_PATH, "turns: -1".getBytes());
        Catan.main(new String[]{});
    }

    // Partition testing: Ensures the game exits when configured for zero rounds.
    @Test
    void testMainRunsWithZeroRounds() throws Exception {
        Files.write(CONFIG_PATH, "turns: 0".getBytes());
        Catan.main(new String[]{});
    }
}