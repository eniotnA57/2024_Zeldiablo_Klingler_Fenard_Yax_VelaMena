package gameLaby.laby;

/**
 * La classe Perso représente le personnage joueur dans le labyrinthe.
 */
public class Perso {
    public int x;
    public int y;
    private int pointsDeVie;
    Labyrinthe laby;

    /**
     * Constructeur de la classe Perso.
     * @param dx Position x initiale du personnage.
     * @param dy Position y initiale du personnage.
     * @param laby Le labyrinthe dans lequel le personnage évolue.
     */
    public Perso(int dx, int dy, Labyrinthe laby) {
        this.x = dx;
        this.y = dy;
        this.pointsDeVie = 10;
        this.laby = laby;
    }

    /**
     * Vérifie si le personnage est présent à la position donnée.
     * @param dx Position x à vérifier.
     * @param dy Position y à vérifier.
     * @return true si le personnage est présent à la position donnée, false sinon.
     */
    public boolean etrePresent(int dx, int dy) {
        return (this.x == dx && this.y == dy);
    }

    /**
     * Obtient la position x du personnage.
     * @return La position x du personnage.
     */
    public int getX() {
        return this.x;
    }

    /**
     * Obtient la position y du personnage.
     * @return La position y du personnage.
     */
    public int getY() {
        return this.y;
    }

    /**
     * Obtient les points de vie du personnage.
     * @return Les points de vie du personnage.
     */
    public int getPointsDeVie() {
        return this.pointsDeVie;
    }

    /**
     * Définit les points de vie du personnage.
     * @param pointsDeVie Les nouveaux points de vie du personnage.
     */
    public void setPointsDeVie(int pointsDeVie) {
        this.pointsDeVie = pointsDeVie;
    }

    /**
     * Inflige des dégâts au personnage.
     * @param damage Le nombre de points de dégâts à infliger.
     */
    public void takeDamage(int damage) {
        this.pointsDeVie -= damage;
        System.out.println("Le héros a maintenant " + this.pointsDeVie + " points de vie.");
        if (pointsDeVie <= 0) {
            System.exit(1);
        }
    }

    /**
     * Déplace le personnage dans la direction spécifiée.
     * @param action La direction dans laquelle déplacer le personnage (HAUT, BAS, GAUCHE, DROITE).
     */
    public void deplacerPerso(String action) {
        int[] suivante = Labyrinthe.getSuivant(this.x, this.y, action);

        if (!laby.murs[suivante[0]][suivante[1]] && !laby.estOccupe(suivante[0], suivante[1])) {
            this.x = suivante[0];
            this.y = suivante[1];

            if (laby.amulettes[suivante[0]][suivante[1]]) {
                Amulette.recupererAmulette(laby);
                laby.amulettes[suivante[0]][suivante[1]] = false;
            }

            if (laby.escaliers[suivante[0]][suivante[1]]) {
                laby.escalier.prendreEscalier(suivante[0], suivante[1]);
                return;
            }
        }

        for (Monstre monstre : laby.monstres) {
            if (laby.estAdjacente(monstre.x, monstre.y, this.x, this.y)) {
                laby.combat.monstreAttaque(monstre);
            } else {
                String actionAleatoire = Labyrinthe.ACTIONS[laby.random.nextInt(Labyrinthe.ACTIONS.length)];
                monstre.deplacerMonstre(actionAleatoire);
            }
        }
    }
}
