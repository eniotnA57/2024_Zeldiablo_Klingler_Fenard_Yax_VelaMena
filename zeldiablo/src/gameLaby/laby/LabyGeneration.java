package gameLaby.laby;

import java.util.Random;

/**
 * Generation automatique de labyrinthe.
 */
public class LabyGeneration {

    static int colonne = MainLaby.colonne;
    static int ligne = MainLaby.ligne;

    static int monstreMax = (int) Math.sqrt((double) (MainLaby.colonne * MainLaby.ligne) / 2);
    static int maxMurs = (int) Math.sqrt((double) (MainLaby.colonne * MainLaby.ligne) * MainLaby.colonne / 10);

    /**
     * Génère un tableau de labyrinthes pour un nombre donné d'étages.
     * @param rows Le nombre de lignes du labyrinthe.
     * @param cols Le nombre de colonnes du labyrinthe.
     * @param etages Le nombre d'étages du labyrinthe.
     * @return Un tableau de chaînes représentant les différents niveaux du labyrinthe.
     */
    public static String[] genererLabyrinthe(int rows, int cols, int etages) {
        String[] labys = new String[etages];
        Random rand = new Random();
        if (colonne < 15) {
            monstreMax = (int) colonne * ligne / 10;
        }
        if (ligne < 10) {
            monstreMax = (int) colonne * ligne / 10;
            maxMurs = (int) colonne * ligne / 2;
        }

        // Generer des coordonnées pour le perso et pour l'échelle
        int lignePerso = rand.nextInt(rows - 2) + 1;
        int colonnePerso = rand.nextInt(cols - 2) + 1;
        int ligneEchelle = rand.nextInt(rows - 2) + 1;
        int colonneEchelle = rand.nextInt(cols - 2) + 1;

        // Assure que P et E n'ont pas les mêmes coordonnées
        while (lignePerso == ligneEchelle && colonnePerso == colonneEchelle) {
            ligneEchelle = rand.nextInt(rows - 2) + 1;
            colonneEchelle = rand.nextInt(cols - 2) + 1;
        }

        // Generation du labyrinthe
        for (int etage = 0; etage < etages; etage++) {
            StringBuilder laby = new StringBuilder();
            laby.append(rows).append("\n");
            laby.append(cols).append("\n");

            // Compteur de monstre et de murs
            int nbrMonstre = 0;
            int nbrMur = 0;

            // Création du labyrinthe vide
            char[][] grid = new char[rows][cols];

            // Placement des murs sur les bords
            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < cols; c++) {
                    if (r == 0 || r == rows - 1 || c == 0 || c == cols - 1) {
                        grid[r][c] = 'X';
                    } else {
                        grid[r][c] = '.';
                    }
                }
            }

            // Placement du personnage et de l'échelle
            grid[lignePerso][colonnePerso] = 'P';
            grid[ligneEchelle][colonneEchelle] = 'E';

            // Placement des murs internes
            while (nbrMur < maxMurs) {
                int r = rand.nextInt(rows - 2) + 1;
                int c = rand.nextInt(cols - 2) + 1;
                if (grid[r][c] == '.') {
                    grid[r][c] = 'X';
                    nbrMur++;
                }
            }

            // Placement des monstres
            while (nbrMonstre < monstreMax) {
                int r = rand.nextInt(rows - 2) + 1;
                int c = rand.nextInt(cols - 2) + 1;
                if (grid[r][c] == '.') {
                    grid[r][c] = 'M';
                    nbrMonstre++;
                }
            }

            // Placement des amulettes
            placeAmulette(grid, rand, rows, cols);
            placeAmulette(grid, rand, rows, cols);

            // Construction de la chaîne de caractères du labyrinthe
            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < cols; c++) {
                    laby.append(grid[r][c]);
                }
                laby.append('\n');
            }
            laby.append('\n');
            labys[etage] = laby.toString();

            // Mise à jour des coordonnées précédentes de l'échelle en nouvelles coordonnées du perso
            lignePerso = ligneEchelle;
            colonnePerso = colonneEchelle;

            // Génération de nouvelles coordonnées
            ligneEchelle = rand.nextInt(rows - 2) + 1;
            colonneEchelle = rand.nextInt(cols - 2) + 1;

            // Différentes de celles du perso
            while (lignePerso == ligneEchelle && colonnePerso == colonneEchelle) {
                ligneEchelle = rand.nextInt(rows - 2) + 1;
                colonneEchelle = rand.nextInt(cols - 2) + 1;
            }
        }
        return labys;
    }

    /**
     * Place une amulette à une position aléatoire dans la grille.
     * @param grid La grille du labyrinthe.
     * @param rand L'objet Random pour générer des positions aléatoires.
     * @param rows Le nombre de lignes de la grille.
     * @param cols Le nombre de colonnes de la grille.
     */
    private static void placeAmulette(char[][] grid, Random rand, int rows, int cols) {
        int r, c;
        do {
            r = rand.nextInt(rows - 2) + 1;
            c = rand.nextInt(cols - 2) + 1;
        } while (grid[r][c] != '.');
        grid[r][c] = 'A';
    }

    /**
     * Méthode principale pour tester la génération de labyrinthes.
     * @param args Arguments de la ligne de commande.
     */
    public static void main(String[] args) {
        int rows = 10;
        int cols = 10;
        int etages = 3;

        String[] labys = genererLabyrinthe(rows, cols, etages);
        for (String laby : labys) {
            System.out.println(laby);
        }
    }
}