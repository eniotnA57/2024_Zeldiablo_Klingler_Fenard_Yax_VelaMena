
package gameLaby.laby;

/**
 * La classe Perso représente le personnage joueur dans le labyrinthe.
 */
public class Perso {
    public int x;
    public int y;
    private int pointsDeVie;
    private boolean attaqueEnCours; // Indicateur d'attaque
    private String directionAttaque; // Direction de l'attaque
    private Labyrinthe laby; // Ajouter cette ligne

    // Modifier le constructeur pour accepter Labyrinthe
    public Perso(int x, int y, Labyrinthe laby) {
        this.x = x;
        this.y = y;
        this.pointsDeVie = 10;
        this.attaqueEnCours = false;
        this.directionAttaque = "";
        this.laby = laby;
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

    public int getPointsDeVie() {
        return pointsDeVie;
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


    /**
     * Déplace le personnage dans la direction spécifiée.
     * @param action La direction dans laquelle déplacer le personnage (HAUT, BAS, GAUCHE, DROITE).
     */
    public void deplacerPerso(String action) {
        int[] suivante = Labyrinthe.getSuivant(this.x, this.y, action);

        // Vérifie que la nouvelle position n'est ni un mur ni occupée
        if (!laby.getMur(suivante[0], suivante[1]) && !laby.estOccupe(suivante[0], suivante[1])) {
            this.x = suivante[0];
            this.y = suivante[1];

            // Interaction avec les amulettes
            if (laby.getAmulette(suivante[0], suivante[1])) {
                Amulette.recupererAmulette(laby);
                laby.amulettes[suivante[0]][suivante[1]] = false;
            }

            // Interaction avec les escaliers
            if (laby.getEscalier(suivante[0], suivante[1])) {
                laby.escalier.prendreEscalier(suivante[0], suivante[1]);
                return;  // Arrête l'exécution si l'escalier est pris
            }
        }

        // Attaque ou déplace chaque monstre une seule fois
        moveMonsters();
    }

    private void moveMonsters() {
        for (Monstre monstre : laby.monstres) {
            if (laby.estAdjacente(monstre.x, monstre.y, this.x, this.y)) {
                laby.combat.monstreAttaque(monstre);
            } else {
                // Choisit une action aléatoire parmi les directions possibles
                String actionAleatoire = Labyrinthe.ACTIONS[laby.random.nextInt(Labyrinthe.ACTIONS.length)];
                int[] newPos = Labyrinthe.getSuivant(monstre.x, monstre.y, actionAleatoire);
                // S'assure que le mouvement est valide
                if (newPos[0] >= 0 && newPos[0] < laby.getLength() && newPos[1] >= 0 && newPos[1] < laby.getLengthY()) {
                    if (!laby.getMur(newPos[0], newPos[1]) && !laby.estOccupe(newPos[0], newPos[1])) {
                        monstre.x = newPos[0];
                        monstre.y = newPos[1];
                    }
                }
            }
        }
    }
 }

