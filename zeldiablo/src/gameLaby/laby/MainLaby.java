package gameLaby.laby;

import moteurJeu.MoteurJeu;

public class MainLaby {

    public static void main(String[] args) {

        MoteurJeu.setTaille(600, 400);
        MoteurJeu.setFPS(60);

        try {

            LabyJeu jeu = new LabyJeu("labySimple/laby2.txt");
            LabyDessin dessin = new LabyDessin();
            MoteurJeu.launch(jeu, dessin);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
