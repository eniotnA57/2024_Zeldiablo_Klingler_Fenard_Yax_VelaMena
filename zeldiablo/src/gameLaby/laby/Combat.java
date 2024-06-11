package gameLaby.laby;

import java.util.ArrayList;
import java.util.List;

/**
 * Gère les combats entre le joueur et les monstres dans le labyrinthe.
 */
public class Combat {

    private Labyrinthe labyrinthe;

    /**
     * Constructeur de la classe Combat.
     * @param labyrinthe Le labyrinthe où les combats ont lieu.
     */
    public Combat(Labyrinthe labyrinthe) {
        this.labyrinthe = labyrinthe;
    }

    /**
     * Fait attaquer un monstre au joueur.
     * @param monstre Le monstre qui attaque.
     */
    public void monstreAttaque(Monstre monstre) {
        labyrinthe.pj.takeDamage(1);
        System.out.println("Le monstre attaque! Le héros perd 1 point de vie.");
    }

    /**
     * Fait attaquer le joueur aux monstres adjacents.
     */
    public void joueurAttaque() {
        boolean attacked = false;
        List<Monstre> monstresToRemove = new ArrayList<>();

        for (Monstre monstre : labyrinthe.monstres) {
            if (isAdjacent(monstre.x, monstre.y, labyrinthe.pj.x, labyrinthe.pj.y)) {
                monstre.takeDamage(1);
                attacked = true;
                System.out.println("Le joueur attaque un monstre! Le monstre perd 1 point de vie.");
                if (!monstre.isAlive()) {
                    monstresToRemove.add(monstre);
                    System.out.println("Un monstre a été tué!");
                    if (Math.random() > 0.5) {
                        System.out.println("Le héros récupère 2 points de vie");
                        labyrinthe.pj.takeDamage(-2);
                    }
                }
            }
        }

        for (Monstre monstre : monstresToRemove) {
            labyrinthe.monstres.remove(monstre);
        }

        if (!attacked) {
            System.out.println("Le joueur attaque dans le vide.");
        }
    }

    /**
     * Vérifie si deux positions sont adjacentes.
     * @param x1 Coordonnée x de la première position.
     * @param y1 Coordonnée y de la première position.
     * @param x2 Coordonnée x de la deuxième position.
     * @param y2 Coordonnée y de la deuxième position.
     * @return true si les positions sont adjacentes, false sinon.
     */
    private boolean isAdjacent(int x1, int y1, int x2, int y2) {
        return (Math.abs(x1 - x2) + Math.abs(y1 - y2)) == 1;
    }
}
