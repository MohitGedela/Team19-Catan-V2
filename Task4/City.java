class City extends Building {
    
    public City(Intersection cityIntersection, Player owner) {
        super(cityIntersection, owner);
    }

    @Override
    public int getVictoryPoints() {
        return 2;
    }

}