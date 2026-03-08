class Settlement extends Building {
    
    public Settlement(Intersection settlementIntersection, Player owner) {
        super(settlementIntersection, owner);
    }

    @Override
    public int getVictoryPoints() {
        return 1;
    }

}