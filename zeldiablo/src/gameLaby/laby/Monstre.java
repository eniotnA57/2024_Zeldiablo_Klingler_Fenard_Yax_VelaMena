package gameLaby.laby;

/**
 * La classe Monstre représente un monstre dans le labyrinthe.
 */
public class Monstre extends Entite {
    public Labyrinthe laby;
    private int pointsDeVie;

    /**
     * Constructeur de la classe Monstre.
     * @param dx Position x initiale du monstre.
     * @param dy Position y initiale du monstre.
     */
    public Monstre(int dx, int dy) {
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
        System.out.println("Le monstre a maintenant " + this.pointsDeVie + " points de vie.");
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
     * @param action La direction dans laquelle déplacer le monstre (HAUT, BAS, GAUCHE, DROITE).
     */
    public void deplacerMonstre(String action) {
        int[] courante = {this.x, this.y};
        int[] suivante = laby.getSuivant(courante[0], courante[1], action);

        if (!laby.murs[suivante[0]][suivante[1]] && (laby.pj.x != suivante[0] || laby.pj.y != suivante[1]) && !laby.estOccupe(suivante[0], suivante[1])) {
            this.x = suivante[0];
            this.y = suivante[1];
        }
    }
}
