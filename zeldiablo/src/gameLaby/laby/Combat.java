package gameLaby.laby;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Combat {
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
        Iterator<Monstre> iterator = labyrinthe.monstres.iterator();

        while (iterator.hasNext()) {
            Monstre monstre = iterator.next();
            if (isAdjacent(monstre.x, monstre.y, labyrinthe.pj.x, labyrinthe.pj.y)) {
                monstre.takeDamage(1);
                System.out.println("Le joueur attaque un monstre! Le monstre perd 1 point de vie.");

                // Déterminer la direction d'attaque en fonction de la position relative du monstre
                setAttackDirection(monstre);

                if (!monstre.isAlive()) {
                    iterator.remove();
                    System.out.println("Un monstre a été tué!");
                    if (Math.random() > 0.6666) {
                        System.out.println("Le héros récupère 2 points de vie");
                        labyrinthe.pj.takeDamage(-2);
                    }
                }
                attacked = true;
            }
        }

        labyrinthe.pj.setAttaqueEnCours(attacked);
        if (!attacked) {
            System.out.println("Le joueur attaque dans le vide.");
        }
    }

    private boolean isAdjacent(int x1, int y1, int x2, int y2) {
        return (Math.abs(x1 - x2) + Math.abs(y1 - y2)) == 1;
    }

    private void setAttackDirection(Monstre monstre) {
        if (monstre.x == labyrinthe.pj.x) {
            if (monstre.y < labyrinthe.pj.y) {
                labyrinthe.pj.setDirectionAttaque("haut");
            } else {
                labyrinthe.pj.setDirectionAttaque("bas");
            }
        } else if (monstre.y == labyrinthe.pj.y) {
            if (monstre.x < labyrinthe.pj.x) {
                labyrinthe.pj.setDirectionAttaque("gauche");
            } else {
                labyrinthe.pj.setDirectionAttaque("droite");
            }
        }
    }
}
