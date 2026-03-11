class Roll implements Command {
    @Override
    public String execute(Player player, Board board, Turn turn) {
        return turn.doRoll(player);
    }

    @Override
    public boolean isGo() {
        return false;
    }

    @Override
    public boolean isRoll() {
        return true;
    }
}