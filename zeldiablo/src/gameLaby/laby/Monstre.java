package gameLaby.laby;

public class Monstre extends Entite {
    private int pointsDeVie;

    public Monstre(int dx, int dy) {
        super(dx, dy);
        this.pointsDeVie = 2;
    }

    public int getPointsDeVie() {
        return this.pointsDeVie;
    }

    public void takeDamage(int damage) {
        this.pointsDeVie -= damage;
        System.out.println("Le monstre a maintenant " + this.pointsDeVie + " points de vie.");
    }

    public boolean isAlive() {
        return this.pointsDeVie > 0;
    }
}
