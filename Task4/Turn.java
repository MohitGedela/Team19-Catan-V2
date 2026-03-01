import java.util.List;

// One turn = roll dice, give resources to players on that number, then get this player's one action (build something or try).
class Turn {

    private Dice dice;
    private Production production;
    private Board board;
    private Robber robber;
    private List<Player> players;

    public Turn(Dice dice, Production production, Board board, List<Player> players) {
        this.dice = dice;
        this.production = production;
        this.board = board;
        this.players = players;
        this.robber = new Robber(board, new java.util.Random());
    }

    public String execute(Player player, int roundNumber) {
        if (player instanceof HumanPlayer) {
            String actionResult = player.takeAction(board);
            return "[" + roundNumber + "] / [" + player.getPlayerID() + "]: " + actionResult;
        }
        int roll = dice.roll();

        if (roll == 7) {
            robber.runRobber(player, players);
        } else {
            production.generateResources(roll);
        }
        String actionResult = player.takeAction(board);
        return "[" + roundNumber + "] / [" + player.getPlayerID() + "]: Rolled " + roll + ", " + actionResult;
    }

    public int doRoll(Player player) {
        int roll = dice.roll();
        if (roll == 7) {
            robber.runRobber(player, players);
        } else {
            production.generateResources(roll);
        }
        return roll;
    }
}