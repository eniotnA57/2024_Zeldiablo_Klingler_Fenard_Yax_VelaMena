package gameLaby.laby;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import moteurJeu.DessinJeu;
import moteurJeu.Jeu;

public class LabyDessin implements DessinJeu {

    public static final int TAILLE = 50;
    private Image imagePrsng;
    private Image imageMstr;
    private Image imageBdf;
    private Image echelle;

    public void dessinerJeu(Jeu jeu, Canvas canvas) {
        LabyJeu labyrinthe = (LabyJeu) jeu;

        final GraphicsContext gc = canvas.getGraphicsContext2D();

        // fond
        gc.setFill(Color.SKYBLUE);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // dessin Labyrinthe
        Image mur = new Image("gameLaby/image/nuage.png");
        Labyrinthe laby = labyrinthe.getLabyrinthe();
        for (int j = 0; j < laby.getLength(); j++) {
            for (int i = 0; i < laby.getLengthY(); i++) {
                if (laby.getMur(j, i)) {
                    gc.drawImage(mur, j * TAILLE, i * TAILLE, TAILLE, TAILLE);
                }
            }
        }

        // escalier
        echelle = new Image("gameLaby/image/echelle.png");
        for (int j = 0; j < laby.getLength(); j++) {
            for (int i = 0; i < laby.getLengthY(); i++) {
                if (laby.escaliers[j][i]) {
                    gc.drawImage(echelle, j * TAILLE, i * TAILLE, TAILLE, TAILLE);
                }
            }
        }

        // perso
        double persox = labyrinthe.getLabyrinthe().pj.getX();
        double persoy = labyrinthe.getLabyrinthe().pj.getY();
        imagePrsng = new Image("gameLaby/image/personnage.png");
        gc.drawImage(imagePrsng, persox * TAILLE, persoy * TAILLE, TAILLE, TAILLE);

        // monstre
        for (Monstre monstre : laby.monstres) {
            double monstrex = monstre.getX();
            double monstrey = monstre.getY();
            imageMstr = new Image("gameLaby/image/dragon2.png");
            gc.drawImage(imageMstr, monstrex * TAILLE, monstrey * TAILLE, TAILLE, TAILLE);
        }
    }
}
