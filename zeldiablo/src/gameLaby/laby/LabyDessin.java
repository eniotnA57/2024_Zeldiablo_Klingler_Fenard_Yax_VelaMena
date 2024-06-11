package gameLaby.laby;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import moteurJeu.DessinJeu;
import moteurJeu.Jeu;

/**
 * Classe pour dessiner le jeu du labyrinthe.
 */
public class LabyDessin implements DessinJeu {

    public static int TAILLE = 50;

    private Image imagePrsng;
    private Image imageMstr;
    private Image imageBdf;
    private Image echelle;
    private Image amulette;

    /**
     * Dessine l'état actuel du jeu sur le canvas.
     * @param jeu L'état actuel du jeu.
     * @param canvas Le canvas sur lequel dessiner.
     */
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
                if (laby.escaliers[j][i]) {
                    gc.drawImage(echelle, j * TAILLE, i * TAILLE, TAILLE, TAILLE);
                }
            }
        }


        // escalier
        echelle = new Image("gameLaby/image/echelle.png");
        for (int j = 0; j < laby.getLength(); j++) {
            for (int i = 0; i < laby.getLengthY(); i++) {
                if (laby.getEscalier(j, i)) {
                    gc.drawImage(echelle, j * TAILLE, i * TAILLE, TAILLE, TAILLE);
                }
            }
        }

        // amulette
        amulette = new Image("gameLaby/image/receptacle.png");
        for (int j = 0; j < laby.getLength(); j++) {
            for (int i = 0; i < laby.getLengthY(); i++) {
                if (laby.getAmulette(j, i)) {
                    gc.drawImage(amulette, j * TAILLE, i * TAILLE, TAILLE, TAILLE);
                }
            }
        }

        // perso
        double persoX = laby.getPj().getX();
        double persoY = laby.getPj().getY();
        Image imagePrsng = new Image("gameLaby/image/personnage.png");
        for (Monstre monstre : laby.monstres) {
            double monstrex = monstre.getX();
            double monstrey = monstre.getY();
            // Vérifiez si un monstre est immédiatement à gauche du personnage
            if (persoX - monstrex == 1 && monstrey == persoY) {
                imagePrsng = new Image("gameLaby/image/personnage2.png");
                break; // Quitte la boucle après avoir trouvé un monstre à gauche
            }
        }
        gc.drawImage(imagePrsng, persoX * TAILLE, persoY * TAILLE, TAILLE, TAILLE);
        // monstre
        for (Monstre monstre : laby.monstres) {
            double monstrex = monstre.getX();
            double monstrey = monstre.getY();
            imageMstr = new Image("gameLaby/image/dragon2.png");
            gc.drawImage(imageMstr, monstrex * TAILLE, monstrey * TAILLE, TAILLE, TAILLE);
        }

        if (laby.getPj().isAttaqueEnCours()) {
            Image imageBdf = null;
            int offsetX = 0, offsetY = 0;
            String direction = laby.getPj().getDirectionAttaque();

            switch (direction) {
                case "haut":
                    offsetY = -1; // Déplacement vers le haut
                    imageBdf = new Image("gameLaby/image/bdf3.png");
                    break;
                case "bas":
                    offsetY = 1; // Déplacement vers le bas
                    imageBdf = new Image("gameLaby/image/bdf4.png");
                    break;
                case "gauche":
                    offsetX = -1; // Déplacement vers la gauche
                    imageBdf = new Image("gameLaby/image/bdf.png");
                    break;
                case "droite":
                    offsetX = 1; // Déplacement vers la droite
                    imageBdf = new Image("gameLaby/image/bdf2.png");
                    break;
            }
            // Ajout de messages de débogage
            System.out.println("Attaque en cours dans la direction: " + direction);
            System.out.println("OffsetX: " + offsetX + ", OffsetY: " + offsetY);
            System.out.println("Image: " + imageBdf);

            // Assurez-vous que l'image est bien chargée
            if (imageBdf != null) {
                gc.drawImage(imageBdf, (persoX + offsetX) * TAILLE, (persoY + offsetY) * TAILLE, TAILLE, TAILLE);
            } else {
                System.out.println("Erreur: L'image de l'attaque n'a pas été chargée.");
            }

            laby.getPj().setAttaqueEnCours(false);
        }
    }
}
