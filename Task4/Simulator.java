import java.util.List;

// Runs round by round: each player rolls and takes one action, then we print VPs. Stops at 10 VP or max rounds.
class Simulator {

    private List<Player> players;
    private Turn turn;
    private int maxRounds;

    public Simulator(List<Player> players, Turn turn, int maxRounds) {
        this.players = players;
        this.turn = turn;
        this.maxRounds = maxRounds;
    }

    public void runGame() {
        int round = 1;
        boolean gameOver = false;

        while (!gameOver && round <= maxRounds) {

            System.out.println("******Round " + round + "******");

            for (Player player : players) {
                String result = turn.execute(player, round);
                System.out.println(result);

                if (player.getVictoryPoints() >= 10) {
                    System.out.println("Player " + player.getPlayerID() + " wins with " + player.getVictoryPoints() + " victory points!");

                    gameOver = true;
                    break;
                }
            }

            for (Player player : players) {
                System.out.println("Player " + player.getPlayerID() + " VP: " + player.getVictoryPoints());
            }
            System.out.println();

            round++;
        }

        if (!gameOver) {
            // Nobody reached 10 VP before round limit.
            System.out.println("Game ended after " + maxRounds + " rounds. No winner.");
        }
    }
}