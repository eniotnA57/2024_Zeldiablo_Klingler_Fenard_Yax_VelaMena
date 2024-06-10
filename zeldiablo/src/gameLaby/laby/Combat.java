package gameLaby.laby;

import javafx.scene.canvas.Canvas;
import moteurJeu.DessinJeu;
import moteurJeu.Jeu;

import java.util.ArrayList;
import java.util.List;

public class Combat{

    private Labyrinthe labyrinthe;

    public Combat(Labyrinthe labyrinthe) {
        this.labyrinthe = labyrinthe;
    }

    public void monstreAttaque(Monstre monstre) {
        labyrinthe.pj.takeDamage(1);
        System.out.println("Le monstre attaque! Le héros perd 1 point de vie.");
    }

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
                    System.out.println("Le héros récupère 2 points de vie");
                    labyrinthe.pj.takeDamage(-2);
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

    private boolean isAdjacent(int x1, int y1, int x2, int y2) {
        return (Math.abs(x1 - x2) + Math.abs(y1 - y2)) == 1;
    }
}
