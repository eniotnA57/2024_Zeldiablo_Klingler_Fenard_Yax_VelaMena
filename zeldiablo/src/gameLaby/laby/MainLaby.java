package gameLaby.laby;

import moteurJeu.MoteurJeu;

public class MainLaby {
    public static final int colonne = 30;
    public static final int ligne = 15;
    public static final int etages = 20;
    private static MusicPlayer musicPlayer;

    public static void main(String[] args) {
        try {
            MoteurJeu.setFPS(15);

            musicPlayer = new MusicPlayer();
            System.out.println("Tentative de lecture de la musique...");
            musicPlayer.playMusic("gameLaby/Musique/musique.wav");

            LabyJeu jeu = new LabyJeu();
            MoteurJeu.setTaille(jeu.getLabyrinthe().getLength() * LabyDessin.TAILLE,
                    jeu.getLabyrinthe().getLengthY() * LabyDessin.TAILLE);

            LabyDessin dessin = new LabyDessin();

            MoteurJeu.launch(jeu, dessin);

            jeu.setGameOverListener(() -> musicPlayer.stopMusic());
        } catch (Exception e) {
            System.err.println("Une erreur s'est produite : " + e.getMessage());
            e.printStackTrace();
        }
    }
}
