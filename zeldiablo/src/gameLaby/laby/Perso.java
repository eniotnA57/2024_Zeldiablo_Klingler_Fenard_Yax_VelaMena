package gameLaby.laby;

public class Perso {
    public int x;
    public int y;
    private int pointsDeVie;
    private boolean attaqueEnCours;
    private String directionAttaque;
    private Labyrinthe laby;

    public Perso(int dx, int dy, Labyrinthe laby) {
        this.x = dx;
        this.y = dy;
        this.pointsDeVie = 10;
        this.attaqueEnCours = false;
        this.directionAttaque = "";
        this.laby = laby;
    }

    public boolean etrePresent(int dx, int dy) {
        return (this.x == dx && this.y == dy);
    }

    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getPointsDeVie() {
        return this.pointsDeVie;
    }

    public void setPointsDeVie(int pointsDeVie) {
        this.pointsDeVie = pointsDeVie;
    }

    public void takeDamage(int damage) {
        this.pointsDeVie -= damage;
        System.out.println("Le héros a maintenant " + this.pointsDeVie + " points de vie.");
        if (pointsDeVie <= 0) {
            System.exit(1);
        }
    }

    public boolean isAttaqueEnCours() {
        return attaqueEnCours;
    }

    public void setAttaqueEnCours(boolean attaqueEnCours) {
        this.attaqueEnCours = attaqueEnCours;
    }

    public String getDirectionAttaque() {
        return directionAttaque;
    }

    public void setDirectionAttaque(String directionAttaque) {
        this.directionAttaque = directionAttaque;
    }

    public void deplacerPerso(String action) {
        int[] suivante = Labyrinthe.getSuivant(this.x, this.y, action);

        if (!laby.getMur(suivante[0], suivante[1]) && !laby.estOccupe(suivante[0], suivante[1])) {
            this.x = suivante[0];
            this.y = suivante[1];

            if (laby.getAmulette(suivante[0], suivante[1])) {
                Amulette.recupererAmulette(laby);
                laby.enleverAmulette(suivante[0], suivante[1]);
                System.out.println("Amulette ramassée à (" + suivante[0] + ", " + suivante[1] + ")");
            }

            if (laby.getEscalier(suivante[0], suivante[1])) {
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
