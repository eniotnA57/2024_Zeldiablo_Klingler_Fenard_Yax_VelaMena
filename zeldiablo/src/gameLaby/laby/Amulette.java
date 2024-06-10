package gameLaby.laby;

public class Amulette {
    private int x;
    private int y;

    public Amulette(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static void recupererAmulette(Labyrinthe laby) {
        laby.getPj().takeDamage(-4);
    }
}
