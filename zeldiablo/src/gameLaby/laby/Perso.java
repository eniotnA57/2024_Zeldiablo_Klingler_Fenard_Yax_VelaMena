package gameLaby.laby;

public class Perso {
    public int x;
    public int y;
    private int pointsDeVie;

    public Perso(int dx, int dy) {
        this.x = dx;
        this.y = dy;
        this.pointsDeVie = 10;
    }

    public boolean etrePresent(int dx, int dy) {
        return (this.x == dx && this.y == dy);
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getPointsDeVie() {
        return this.pointsDeVie;
    }

    public void takeDamage(int damage) {
        this.pointsDeVie -= damage;
        System.out.println("Le héros a maintenant " + this.pointsDeVie + " points de vie.");
    }
}
