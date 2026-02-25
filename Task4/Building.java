abstract class Building {
    private Intersection buildLocation;
    private Player buildOwner; 

    public Building(Intersection buildingIntersection, Player owner) {
        buildLocation = buildingIntersection;
        buildOwner = owner;
    }

    public Intersection getBuildlocation() {
        return buildLocation;
    }

    public abstract int getVictoryPoints();

    public Player getOwner() {
        return buildOwner;
    }
}