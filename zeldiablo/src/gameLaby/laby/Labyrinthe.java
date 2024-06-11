package gameLaby.laby;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * La classe Labyrinthe représente un labyrinthe dans le jeu, avec des murs, des escaliers,
 * des amulettes, des monstres et un personnage joueur.
 */
public class Labyrinthe {

    public static final char MUR = 'X';
    public static final char AMULETTE = 'A';
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
    public boolean[][] amulettes;

    public Random random;
    private String[] labyFiles;
    public Escalier escalier;
    public Combat combat;

    private int savedHealth = 10;

    /**
     * Constructeur par défaut de la classe Labyrinthe.
     * Génère un labyrinthe et charge le premier niveau.
     */
    public Labyrinthe() {
        try {
            labyFiles = LabyGeneration.genererLabyrinthe(MainLaby.ligne, MainLaby.colonne, MainLaby.etages);
            chargerLaby(labyFiles[0]);
            this.escalier = new Escalier(this, labyFiles);
            this.combat = new Combat(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Labyrinthe(String labyString) throws IOException {
        chargerLaby(labyString);
    }

    /**
     * Constructeur avec des fichiers de labyrinthe spécifiques.
     * @param labyFiles Tableau de chaînes représentant les niveaux du labyrinthe.
     * @throws IOException Si une erreur d'entrée/sortie se produit lors du chargement du labyrinthe.
     */
    public Labyrinthe(String[] labyFiles) throws IOException {
        this.labyFiles = labyFiles;
        chargerLaby(labyFiles[0]);
        this.escalier = new Escalier(this, labyFiles);
        this.combat = new Combat(this);
    }

    /**
     * Sauvegarde les points de vie du personnage joueur.
     */
    public void savePlayerHealth() {
        if (pj != null) {
            savedHealth = pj.getPointsDeVie();
        }
    }

    /**
     * Restaure les points de vie sauvegardés du personnage joueur.
     */
    public void restorePlayerHealth() {
        if (pj != null) {
            pj.setPointsDeVie(savedHealth);
        }
    }

    /**
     * Vérifie si une position est occupée par un monstre.
     * @param x Coordonnée x.
     * @param y Coordonnée y.
     * @return true si la position est occupée, false sinon.
     */
    public boolean estOccupe(int x, int y) {
        for (Monstre monstre : monstres) {
            if (monstre.x == x && monstre.y == y) {
                return true;
            }
        }
        return false;
    }

    /**
     * Vérifie si deux positions sont adjacentes.
     * @param x1 Coordonnée x de la première position.
     * @param y1 Coordonnée y de la première position.
     * @param x2 Coordonnée x de la deuxième position.
     * @param y2 Coordonnée y de la deuxième position.
     * @return true si les positions sont adjacentes, false sinon.
     */
    public boolean estAdjacente(int x1, int y1, int x2, int y2) {
        return (Math.abs(x1 - x2) == 1 && y1 == y2) || (Math.abs(y1 - y2) == 1 && x1 == x2);
    }

    /**
     * Charge un niveau de labyrinthe à partir d'une chaîne.
     * @param laby La chaîne représentant le labyrinthe.
     * @throws IOException Si une erreur d'entrée/sortie se produit lors du chargement du labyrinthe.
     */
    public void chargerLaby(String laby) throws IOException {
        savePlayerHealth(); // Sauvegarder les points de vie avant de charger le nouveau niveau
        BufferedReader bfRead = new BufferedReader(new StringReader(laby));

        int nbLignes = Integer.parseInt(bfRead.readLine());
        int nbColonnes = Integer.parseInt(bfRead.readLine());

        this.murs = new boolean[nbColonnes][nbLignes];
        this.escaliers = new boolean[nbColonnes][nbLignes];
        this.amulettes = new boolean[nbColonnes][nbLignes];
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
                        this.pj = new Perso(colonne, numeroLigne, this);
                        break;
                    case MONSTRE:
                        this.murs[colonne][numeroLigne] = false;
                        Monstre monstre = new Monstre(colonne, numeroLigne);
                        monstre.laby = this;
                        this.monstres.add(monstre);
                        break;
                    case ESCALIER:
                        this.murs[colonne][numeroLigne] = false;
                        this.escaliers[colonne][numeroLigne] = true;
                        break;
                    case AMULETTE:
                        this.murs[colonne][numeroLigne] = false;
                        this.amulettes[colonne][numeroLigne] = true;
                        break;
                    default:
                        throw new Error("caractere inconnu " + c);
                }
            }
            ligne = bfRead.readLine();
            numeroLigne++;
        }

        bfRead.close();
        restorePlayerHealth();
    }

    /**
     * Vérifie si le labyrinthe est terminé.
     * @return false (à implémenter pour vérifier si le jeu est terminé).
     */
    public boolean etreFini() {
        return false;
    }

    /**
     * Obtient la longueur verticale du labyrinthe.
     * @return La longueur verticale.
     */
    public int getLengthY() {
        return murs[0].length;
    }

    /**
     * Obtient la longueur horizontale du labyrinthe.
     * @return La longueur horizontale.
     */
    public int getLength() {
        return murs.length;
    }

    /**
     * Vérifie si une position est un mur.
     * @param x Coordonnée x.
     * @param y Coordonnée y.
     * @return true si la position est un mur, false sinon.
     */
    public boolean getMur(int x, int y) {
        return this.murs[x][y];
    }

    /**
     * Vérifie si une position est un escalier.
     * @param x Coordonnée x.
     * @param y Coordonnée y.
     * @return true si la position est un escalier, false sinon.
     */
    public boolean getEscalier(int x, int y) {
        return this.escaliers[x][y];
    }

    /**
     * Vérifie si une position est une amulette.
     * @param x Coordonnée x.
     * @param y Coordonnée y.
     * @return true si la position est une amulette, false sinon.
     */
    public boolean getAmulette(int x, int y) {
        return this.amulettes[x][y];
    }

    /**
     * Obtient le niveau actuel.
     * @return Le niveau actuel.
     */
    public int getCurrentLevel() {
        return escalier.getCurrentLevel();
    }

    /**
     * Calcule la position suivante en fonction de l'action donnée.
     * @param x Coordonnée x actuelle.
     * @param y Coordonnée y actuelle.
     * @param action Action à réaliser.
     * @return Un tableau contenant les nouvelles coordonnées [x, y].
     */
    public static int[] getSuivant(int x, int y, String action) {
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
     * Obtient le personnage joueur.
     * @return Le personnage joueur.
     */
    public Perso getPj() {
        return pj;
    }

    /**
     * Test si les monstres sont morts
     * @return true ou false
     */
    public boolean tousLesMonstresSontMorts() {
        for (Monstre monstre : monstres) {
            if (monstre.isAlive()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Méthode supprimer les murs internes
     */
    public void supprimerMursInternes() {
        for (int x = 1; x < murs.length - 1; x++) {
            for (int y = 1; y < murs[x].length - 1; y++) {
                murs[x][y] = false;
            }
        }
    }
}
