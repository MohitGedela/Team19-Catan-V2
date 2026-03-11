class Go implements Command {
    @Override
    public String execute(Player player, Board board, Turn turn) {
        return "ended turn";
    }

    @Override
    public boolean isGo() {
        return true;
    }

    @Override
    public boolean isRoll() {
        return false;
    }
}
