package moteurJeu;

import javafx.scene.input.KeyEvent;

public class Clavier {


    public boolean haut, bas, gauche, droite,attaque;
    public boolean casserMur;


    public void appuyerTouche(KeyEvent event) {

        switch (event.getCode()) {

            // si touche bas
            case S:
                this.bas = true;
                break;

            // si touche haut
            case Z:
                this.haut = true;
                break;

            // si touche gauche
            case Q:
                this.gauche = true;
                break;

            // si touche droite
            case D:
                this.droite = true;
                break;

            // si touche bas
            case DOWN:
                this.bas = true;
                break;

            // si touche haut
            case UP:
                this.haut = true;
                break;

            // si touche gauche
            case LEFT:
                this.gauche = true;
                break;

            // si touche droite
            case RIGHT:
                this.droite = true;
                break;
            case SPACE:
                this.attaque = true;
                break;
        }

    }

    /**

     stocke les commandes*
     @param event evenement clavier*/
    public void relacherTouche(KeyEvent event) {

        switch (event.getCode()) {

            // si touche bas
            case S:
                this.bas = false;
                break;

            // si touche haut
            case Z:
                this.haut = false;
                break;

            // si touche gauche
            case Q:
                this.gauche = false;
                break;

            // si touche droite
            case D:
                this.droite = false;
                break;
            // si touche bas
            case DOWN:
                this.bas = false;
                break;

            // si touche haut
            case UP:
                this.haut = false;
                break;

            // si touche gauche
            case LEFT:
                this.gauche = false;
                break;

            // si touche droite
            case RIGHT:
                this.droite = false;
                break;

                case SPACE:
                this.attaque = false;
                break;


        }

    }

}