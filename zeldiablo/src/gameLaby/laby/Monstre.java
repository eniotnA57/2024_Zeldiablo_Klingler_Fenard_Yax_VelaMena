package gameLaby.laby;

/**
 * La classe Monstre représente un monstre dans le labyrinthe.
 */
public class Monstre extends Entite {
    public Labyrinthe laby;
    private int pointsDeVie;

    /**
     * Constructeur de la classe Monstre.
     *
     * @param dx         Position x initiale du monstre.
     * @param dy         Position y initiale du monstre.
     * @param labyrinthe
     */
    public Monstre(int dx, int dy, Labyrinthe labyrinthe) {
        super(dx, dy);
        this.pointsDeVie = 2;
    }

    /**
     * Obtient les points de vie du monstre.
     * @return Les points de vie du monstre.
     */
    public int getPointsDeVie() {
        return this.pointsDeVie;
    }

    /**
     * Inflige des dégâts au monstre.
     * @param damage Le nombre de points de dégâts à infliger.
     */
    public void takeDamage(int damage) {
        this.pointsDeVie -= damage;
        System.out.println("Monstre blessé, points de vie restants : " + this.pointsDeVie);
    }

    /**
     * Vérifie si le monstre est encore en vie.
     * @return true si le monstre est vivant, false sinon.
     */
    public boolean isAlive() {
        return this.pointsDeVie > 0;
    }

    /**
     * Déplace le monstre dans la direction spécifiée.
     */
    public void deplacerMonstre(String direction) {
        int[] suivante = Labyrinthe.getSuivant(this.x, this.y, direction);
        if (suivante[0] >= 0 && suivante[0] < laby.getLength() && suivante[1] >= 0 && suivante[1] < laby.getLengthY()) {
            if (!laby.getMur(suivante[0], suivante[1]) && !laby.estOccupe(suivante[0], suivante[1])) {
                this.x = suivante[0];
                this.y = suivante[1];
            }
        }
    }
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }


}
