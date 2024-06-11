package gameLaby.laby;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

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
    private int currentLevel; // Niveau courant du labyrinthe

    private int savedHealth = 10;

    public Labyrinthe() {
        try {
            labyFiles = LabyGeneration.genererLabyrinthe(MainLaby.ligne, MainLaby.colonne, MainLaby.etages);
            currentLevel = 0; // Initialiser le niveau courant
            chargerLaby(labyFiles[currentLevel]);
            this.escalier = new Escalier(this, labyFiles);
            this.combat = new Combat(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Labyrinthe(String[] labyFiles) throws IOException {
        this.labyFiles = labyFiles;
        currentLevel = 0; // Initialiser le niveau courant
        chargerLaby(labyFiles[currentLevel]);
        this.escalier = new Escalier(this, labyFiles);
        this.combat = new Combat(this);
    }

    public void savePlayerHealth() {
        if (pj != null) {
            savedHealth = pj.getPointsDeVie();
        }
    }

    public void restorePlayerHealth() {
        if (pj != null) {
            pj.setPointsDeVie(savedHealth);
        }
    }

    public boolean estOccupe(int x, int y) {
        for (Monstre monstre : monstres) {
            if (monstre.x == x && monstre.y == y) {
                return true;
            }
        }
        return false;
    }

    public boolean estAdjacente(int x1, int y1, int x2, int y2) {
        return (Math.abs(x1 - x2) == 1 && y1 == y2) || (Math.abs(y1 - y2) == 1 && x1 == x2);
    }

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
                        Monstre monstre = new Monstre(colonne, numeroLigne, this);
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

    public boolean etreFini() {
        return false;
    }

    public int getLengthY() {
        return murs[0].length;
    }

    public int getLength() {
        return murs.length;
    }

    public boolean getMur(int x, int y) {
        return this.murs[x][y];
    }

    public boolean getEscalier(int x, int y) {
        return this.escaliers[x][y];
    }

    public boolean getAmulette(int x, int y) {
        return this.amulettes[x][y];
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

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

    public Perso getPj() {
        return pj;
    }

    public void deplacerPerso(String direction) {
        int[] newPosition = getSuivant(pj.getX(), pj.getY(), direction);

        // Vérifier les limites du labyrinthe et les obstacles
        if (newPosition[0] >= 0 && newPosition[0] < getLength() && newPosition[1] >= 0 && newPosition[1] < getLengthY()) {
            if (escaliers[newPosition[0]][newPosition[1]]) {
                monterEtage();
            } else if (!murs[newPosition[0]][newPosition[1]] && !estOccupe(newPosition[0], newPosition[1])) {
                pj.setX(newPosition[0]);
                pj.setY(newPosition[1]);
            }
        }

        // Mettre à jour le combat après le déplacement
        updateCombat();
    }

    private void monterEtage() {
        if (currentLevel < labyFiles.length - 1) {
            currentLevel++;
            try {
                chargerLaby(labyFiles[currentLevel]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Vous avez atteint le dernier niveau !");
        }
    }

    public void updateCombat() {
        Iterator<Monstre> iterator = monstres.iterator();
        while (iterator.hasNext()) {
            Monstre monstre = iterator.next();
            if (!monstre.isAlive()) {
                iterator.remove();
                System.out.println("Un monstre a été tué!");
            }
        }

        // Exécute d'autres logiques de combat, par exemple, vérifier les attaques des monstres
        for (Monstre monstre : monstres) {
            if (estAdjacente(monstre.x, monstre.y, pj.getX(), pj.getY())) {
                combat.monstreAttaque(monstre);
            } else {
                String actionAleatoire = ACTIONS[random.nextInt(ACTIONS.length)];
                monstre.deplacerMonstre(actionAleatoire);
            }
        }
    }


    // Dans la classe Labyrinthe

    public void deplacerMonstre(Monstre monstre, int direction) {
        int newX = monstre.getX();
        int newY = monstre.getY();

        switch (direction) {
            case 0:
                newX--;
                break;
            case 1:
                newX++;
                break;
            case 2:
                newY--;
                break;
            case 3:
                newY++;
                break;
            default:
                break;
        }
    }
}
