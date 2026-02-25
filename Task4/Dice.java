import java.util.Random;

class Dice {
    private int dice1;
    private int dice2;
    private Random random;

    public Dice() {
        this.random = new Random();
    }

    public int roll() {
        do {
            dice1 = random.nextInt(6) + 1;
            dice2 = random.nextInt(6) + 1;
        } while (dice1 + dice2 == 7); 

        return dice1 + dice2;
    }
}
