package gameLaby.laby;

import moteurJeu.Clavier;
import moteurJeu.Jeu;

import java.io.IOException;

/**
 * Classe qui permet de faire fonctionner le jeu dans le labyrinthe
 */
public class LabyJeu implements Jeu {
    private Runnable gameOverListener;
    private final Labyrinthe labyrinthe;
    /**
     * Constructeur de la classe LabyJeu.
     *
     * @param nom Le nom du fichier contenant la configuration du labyrinthe.
     * @throws IOException Si une erreur de lecture du fichier se produit.
     */
    public LabyJeu(String[] nom) throws IOException {
        this.labyrinthe = new Labyrinthe(nom);
    }

    /**
     * Constructeur par défaut
     */
    public LabyJeu(){
        this.labyrinthe = new Labyrinthe();
    }

    @Override
    public void update(double deltaTime, Clavier clavier) {
        if (clavier.haut) {
            this.labyrinthe.deplacerPerso(Labyrinthe.HAUT);
        }
        if (clavier.bas) {
            this.labyrinthe.deplacerPerso(Labyrinthe.BAS);
        }
        if (clavier.gauche) {
            this.labyrinthe.deplacerPerso(Labyrinthe.GAUCHE);
        }
        if (clavier.droite) {
            this.labyrinthe.deplacerPerso(Labyrinthe.DROITE);
        }
        if(clavier.attaque){
            this.labyrinthe.combat.joueurAttaque();
        }
    }

    @Override
    public void init() {
        // Pas besoin
    }

    @Override
    public boolean etreFini() {
        return this.labyrinthe.etreFini();
    }

    /**
     * @return Retourne le labyrinthe
     */
    public Labyrinthe getLabyrinthe() {
        return this.labyrinthe;
    }
    public void setGameOverListener(Runnable listener) {
        this.gameOverListener = listener;
    }

    private void gameOver() {
        if (gameOverListener != null) {
            gameOverListener.run();
        }
    }
}
