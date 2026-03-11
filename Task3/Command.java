interface Command {
    String execute(Player player, Board board, Turn turn);
    boolean endsTurn();
    boolean requiresRoll();
}