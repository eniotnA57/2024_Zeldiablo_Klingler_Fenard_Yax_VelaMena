package gameLaby.laby;

import java.io.IOException;

public class Escalier {

    private Labyrinthe labyrinthe;
    private String[] labyFiles;
    private int currentLevel;

    public Escalier(Labyrinthe labyrinthe, String[] labyFiles) {
        this.labyrinthe = labyrinthe;
        this.labyFiles = labyFiles;
        this.currentLevel = 0;
    }

    public boolean checkAndHandleStairs(int x, int y) {
        if (labyrinthe.getEscalier(x, y)) {
            System.out.println("Montée à l'étage supérieur");
            monterEtage();
            return true;
        }
        return false;
    }

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

    public int getCurrentLevel() {
        return currentLevel;
    }
}
