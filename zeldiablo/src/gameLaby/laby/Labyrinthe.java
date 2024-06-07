package gameLaby.laby;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Classe Labyrinthe
 */

public class Labyrinthe {

    public static final char MUR = 'X';
    public static final char PJ = 'P';
    public static final char MONSTRE = 'M';
    public static final char VIDE = '.';
    public static final char ESCALIER = 'E';

    public static final String HAUT = "Haut";
    public static final String BAS = "Bas";
    public static final String GAUCHE = "Gauche";
    public static final String DROITE = "Droite";

    public static final String[] ACTIONS = {HAUT, BAS, GAUCHE, DROITE};

    public Perso pj;
    public List<Monstre> monstres;

    public boolean[][] murs;
    public boolean[][] escaliers;

    private Random random;

    private String[] labyFiles = {
            "labySimple/laby3.txt",
            "labySimple/laby4.txt",
            "labySimple/laby5.txt"
    };

    private Escalier escalier;
    private Combat combat;

    /**
     *
     * @param x
     * @param y
     * @param action
     * @return
     */
    static int[] getSuivant(int x, int y, String action) {
        switch (action) {
            case HAUT:
                y--;
                break;
            case BAS:
                y++;
                break;
            case DROITE:
                x++;
                break;
            case GAUCHE:
                x--;
                break;
            default:
                throw new Error("action inconnue");
        }
        return new int[]{x, y};
    }

    /**
     * constructeur par défaut
     */

    public Labyrinthe() {
        try {
            chargerLaby(labyFiles[0]);
            this.escalier = new Escalier(this, labyFiles);
            this.combat = new Combat(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Constructeur avec un fichier
     * @param nom
     * @throws IOException
     */
    public Labyrinthe(String nom) throws IOException {
        chargerLaby(nom);
        this.escalier = new Escalier(this, labyFiles);
        this.combat = new Combat(this);
    }

    /**
     * deplace le perso
     * @param action
     */
    public void deplacerPerso(String action) {
        int[] courante = {this.pj.x, this.pj.y};
        int[] suivante = getSuivant(courante[0], courante[1], action);

        if (!this.murs[suivante[0]][suivante[1]] && !estOccupe(suivante[0], suivante[1])) {
            this.pj.x = suivante[0];
            this.pj.y = suivante[1];

            if (this.escalier.checkAndHandleStairs(suivante[0], suivante[1])) {
                return;
            }
        }
        for (Monstre monstre : monstres) {
            String actions = ACTIONS[random.nextInt(ACTIONS.length)];
            deplacerMonstre(monstre, actions);
        }
    }

    /**
     *
     * @param x
     * @param y
     * @return
     */
    private boolean estOccupe(int x, int y) {
        for (Monstre monstre : monstres) {
            if (monstre.x == x && monstre.y == y) {
                return true;
            }
        }
        return false;
    }

    /**
     * Charge le labyrinthe
     * @param nom
     * @throws IOException
     */
    public void chargerLaby(String nom) throws IOException {
        FileReader fichier = new FileReader(nom);
        BufferedReader bfRead = new BufferedReader(fichier);

        int nbLignes = Integer.parseInt(bfRead.readLine());
        int nbColonnes = Integer.parseInt(bfRead.readLine());

        this.murs = new boolean[nbColonnes][nbLignes];
        this.escaliers = new boolean[nbColonnes][nbLignes];
        this.pj = null;
        this.monstres = new ArrayList<>();
        this.random = new Random();

        String ligne = bfRead.readLine();
        int numeroLigne = 0;

        while (ligne != null) {
            for (int colonne = 0; colonne < ligne.length(); colonne++) {
                char c = ligne.charAt(colonne);
                switch (c) {
                    case MUR:
                        this.murs[colonne][numeroLigne] = true;
                        break;
                    case VIDE:
                        this.murs[colonne][numeroLigne] = false;
                        break;
                    case PJ:
                        this.murs[colonne][numeroLigne] = false;
                        this.pj = new Perso(colonne, numeroLigne);
                        break;
                    case MONSTRE:
                        this.murs[colonne][numeroLigne] = false;
                        this.monstres.add(new Monstre(colonne, numeroLigne));
                        break;
                    case ESCALIER:
                        this.murs[colonne][numeroLigne] = false;
                        this.escaliers[colonne][numeroLigne] = true;
                        break;
                    default:
                        throw new Error("caractere inconnu " + c);
                }
            }
            ligne = bfRead.readLine();
            numeroLigne++;
        }

        bfRead.close();
    }

    public void deplacerMonstre(Monstre monstre, String action) {
        int[] courante = {monstre.x, monstre.y};
        int[] suivante = getSuivant(courante[0], courante[1], action);

        if (!this.murs[suivante[0]][suivante[1]] && (this.pj.x != suivante[0] || this.pj.y != suivante[1]) && !estOccupe(suivante[0], suivante[1])) {
            monstre.x = suivante[0];
            monstre.y = suivante[1];
        }
    }

    /**
     *
     * @return
     */
    public boolean etreFini() {
        return false;
    }

    /**
     *
     * @return
     */

    public int getLengthY() {
        return murs[0].length;
    }

    /**
     * @return
     */

    public int getLength() {
        return murs.length;
    }

    public boolean getMur(int x, int y) {
        return this.murs[x][y];
    }

    public boolean getEscalier(int x, int y) {
        return this.escaliers[x][y];
    }

    public int getCurrentLevel() {
        return escalier.getCurrentLevel();
    }

    public Perso getPj() {
        return pj;
    }
}
