package gameLaby.laby;

import java.io.IOException;

/**
 * La classe Escalier gère la navigation entre les différents niveaux du labyrinthe.
 */
public class Escalier {

    private Labyrinthe labyrinthe;
    private String[] labyFiles;
    private int currentLevel;

    /**
     * Constructeur de la classe Escalier.
     * @param labyrinthe Le labyrinthe associé.
     * @param labyFiles Les fichiers de labyrinthe pour chaque niveau.
     */
    public Escalier(Labyrinthe labyrinthe, String[] labyFiles) {
        this.labyrinthe = labyrinthe;
        this.labyFiles = labyFiles;
        this.currentLevel = 0;
    }

    /**
     * Prend l'escalier pour monter à l'étage supérieur si la position contient un escalier.
     * @param x Coordonnée x de la position.
     * @param y Coordonnée y de la position.
     * @return true si l'escalier est pris, false sinon.
     */
    public boolean prendreEscalier(int x, int y) {
        if (labyrinthe.getEscalier(x, y)) {
            System.out.println("Montée à l'étage supérieur");
            monterEtage();
            return true;
        }
        return false;
    }

    /**
     * Monte à l'étage supérieur du labyrinthe.
     * Charge le niveau suivant ou affiche un message de victoire si le dernier niveau est atteint.
     */
    private void monterEtage() {
        currentLevel++;
        if (currentLevel < labyFiles.length) {
            try {
                labyrinthe.chargerLaby(labyFiles[currentLevel]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Vous avez gagné");
            System.exit(1);
        }
    }

    /**
     * Obtient le niveau actuel du labyrinthe.
     * @return Le niveau actuel.
     */
    public int getCurrentLevel() {
        return currentLevel;
    }
}