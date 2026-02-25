// One turn = roll dice, give resources to players on that number, then get this player's one action (build something or try).
class Turn {

    private Dice dice;
    private Production production;
    private Board board;

    public Turn(Dice dice, Production production, Board board) {
        this.dice = dice;
        this.production = production;
        this.board = board;
    }

    public String execute(Player player, int roundNumber) {
        int roll = dice.roll();
        production.generateResources(roll); // Give out resources to all players on hexes that match the roll.
        String actionResult = player.takeAction(board);
        return "[" + roundNumber + "] / [" + player.getPlayerID() + "]: Rolled " + roll + ", " + actionResult;
    }
}