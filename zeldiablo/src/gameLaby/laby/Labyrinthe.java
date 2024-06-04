package gameLaby.laby;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

/**
 * classe labyrinthe. represente un labyrinthe avec
 * <ul> des murs </ul>
 * <ul> un personnage (x,y) </ul>
 * <ul> un monstre (x,y) </ul>
 */
public class
Labyrinthe {

    /**
     * Constantes char
     */
    public static final char MUR = 'X';
    public static final char PJ = 'P';
    public static final char MONSTRE = 'M';
    public static final char VIDE = '.';

    /**
     * constantes actions possibles
     */
    public static final String HAUT = "Haut";
    public static final String BAS = "Bas";
    public static final String GAUCHE = "Gauche";
    public static final String DROITE = "Droite";

    // Ajoutez les constantes pour les directions possibles
    public static final String[] ACTIONS = {HAUT, BAS, GAUCHE, DROITE};

    /**
     * attributs du personnage et du monstre
     */
    public Perso pj;
    public Monstre monstre;

    /**
     * les murs du labyrinthe
     */
    public boolean[][] murs;

    private Random random;

    /**
     * Pour les Tests
     */
    private String[] labyDefaut ={
                "XXXXXXXXXX",
                "X..X....XX",
                "X........X",
                "X.X......X",
                "X....P...X",
                "X......X.X",
                "X.M......X",
                "X.X......X",
                "X.......XX",
                "XXXXXXXXXX",

    };

    /**
     * retourne la case suivante selon une actions
     *
     * @param x      case depart
     * @param y      case depart
     * @param action action effectuee
     * @return case suivante
     */
    static int[] getSuivant(int x, int y, String action) {
        switch (action) {
            case HAUT:
                // on monte une ligne
                y--;
                break;
            case BAS:
                // on descend une ligne
                y++;
                break;
            case DROITE:
                // on augmente colonne
                x++;
                break;
            case GAUCHE:
                // on diminue colonne
                x--;
                break;
            default:
                throw new Error("action inconnue");
        }
        int[] res = {x, y};
        return res;
    }

    /**
     * Constructeur par défaut
     */
   public Labyrinthe() {

       String[] laby =labyDefaut;

       int nbLignes = laby.length;
       this.murs = new boolean[10][10];
       this.pj = null;
       this.monstre = null;
       this.random = new Random();
       String ligne = "";

       for (int i = 0; i < nbLignes; i++) {
           int numeroLigne = i;
           ligne = laby[i];

           for (int colonne = 0; colonne < 10; colonne++) {
               char c = ligne.charAt(colonne);
               switch (c) {
                   case MUR:
                       this.murs[colonne][numeroLigne] = true;
                       break;
                   case VIDE:
                       this.murs[colonne][numeroLigne] = false;
                       break;
                   case PJ:
                       // pas de mur
                       this.murs[colonne][numeroLigne] = false;
                       // ajoute PJ
                       this.pj = new Perso(colonne, numeroLigne);
                       break;
                   case MONSTRE:
                       // pas de mur
                       this.murs[colonne][numeroLigne] = false;
                       // ajoute Monstre
                       this.monstre = new Monstre(colonne, numeroLigne);
                       break;
                   default:
                       throw new Error("caractere inconnu " + c);
               }
           }
       }
   }
    /**
     * charge le labyrinthe
     *
     * @param nom nom du fichier de labyrinthe
     * @return labyrinthe cree
     * @throws IOException probleme a la lecture / ouverture
     */
    public Labyrinthe(String nom) throws IOException {
        // ouvrir fichier
        FileReader fichier = new FileReader(nom);
        BufferedReader bfRead = new BufferedReader(fichier);

        int nbLignes, nbColonnes;
        // lecture nblignes
        nbLignes = Integer.parseInt(bfRead.readLine());
        // lecture nbcolonnes
        nbColonnes = Integer.parseInt(bfRead.readLine());

        // creation labyrinthe vide
        this.murs = new boolean[nbColonnes][nbLignes];
        this.pj = null;
        this.monstre = null;
        this.random = new Random();

        // lecture des cases
        String ligne = bfRead.readLine();

        // stocke les indices courants
        int numeroLigne = 0;

        // parcours le fichier
        while (ligne != null) {

            // parcours de la ligne
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
                        // pas de mur
                        this.murs[colonne][numeroLigne] = false;
                        // ajoute PJ
                        this.pj = new Perso(colonne, numeroLigne);
                        break;
                    case MONSTRE:
                        // pas de mur
                        this.murs[colonne][numeroLigne] = false;
                        // ajoute Monstre
                        this.monstre = new Monstre(colonne, numeroLigne);
                        break;
                    default:
                        throw new Error("caractere inconnu " + c);
                }
            }
            // lecture
            ligne = bfRead.readLine();
            numeroLigne++;
        }

        // ferme fichier
        bfRead.close();

    }

    /**
     * deplace le personnage en fonction de l'action.
     * gere la collision avec les murs et le monstre
     *
     * @param action une des actions possibles
     */
    public void deplacerPerso(String action) {
        // case courante
        int[] courante = {this.pj.x, this.pj.y};

        // calcule case suivante
        int[] suivante = getSuivant(courante[0], courante[1], action);

        // si c'est pas un mur et pas le monstre, on effectue le deplacement
        if (!this.murs[suivante[0]][suivante[1]] && (this.monstre.x != suivante[0] || this.monstre.y != suivante[1])) {
            // on met a jour personnage
            this.pj.x = suivante[0];
            this.pj.y = suivante[1];
        }
        String actions = ACTIONS[random.nextInt(ACTIONS.length)];
        deplacerMonstre(actions);
    }

    /**
     * deplace le monstre en fonction de l'action.
     * gere la collision avec les murs et le personnage
     *
     * @param action une des actions possibles
     */
    public void deplacerMonstre(String action) {

        int[] courante = {this.monstre.x, this.monstre.y};

        int[] suivante = getSuivant(courante[0], courante[1], action);


        if (!this.murs[suivante[0]][suivante[1]] && (this.pj.x != suivante[0] || this.pj.y != suivante[1])) {
            this.monstre.x = suivante[0];
            this.monstre.y = suivante[1];
        }
    }

    /**
     * jamais fini
     *
     * @return fin du jeu
     */
    public boolean etreFini() {
        return false;
    }

    // ##################################
    // GETTER
    // ##################################

    /**
     * return taille selon Y
     *
     * @return
     */
    public int getLengthY() {
        return murs[0].length;
    }

    /**
     * return taille selon X
     *
     * @return
     */
    public int getLength() {
        return murs.length;
    }

    /**
     * return mur en (i,j)
     * @param x
     * @param y
     * @return
     */
    public boolean getMur(int x, int y) {
        return this.murs[x][y];
    }
}
