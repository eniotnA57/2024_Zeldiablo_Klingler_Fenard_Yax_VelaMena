import gameLaby.laby.Labyrinthe;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class Iteration2Test {

    private Labyrinthe labyrinthe;

    @Before
    public void setUp() throws IOException {
        labyrinthe = new Labyrinthe();
    }

    /**
     * Test si le monstre se déplace bien aléatoirement quand le personnage bouge.
     * @throws IOException
     */
    @Test
    public void testMouvementRandom() throws IOException {
        int initialX = labyrinthe.monstres.get(0).getX();
        int initialY = labyrinthe.monstres.get(0).getY();

        labyrinthe.deplacerPerso(Labyrinthe.DROITE);

        int newX = labyrinthe.monstres.get(0).getX();
        int newY = labyrinthe.monstres.get(0).getY();

        boolean monstreDeplace = (initialX != newX || initialY != newY);
        assertTrue("Le monstre ne s'est pas déplacé aléatoirement", monstreDeplace);
    }

    /**
     * Test si les collisions fonctionnent quand le monstre se déplace.
     * @throws IOException
     */
    @Test
    public void testCollisionMonstre() throws IOException {
        labyrinthe = new Labyrinthe("labySimple/laby0.txt");

        // Position initiale du monstre
        int initialX = labyrinthe.monstres.get(0).getX();
        int initialY = labyrinthe.monstres.get(0).getY();

        // Déplacement à droite dans un mur
        labyrinthe.deplacerMonstre(labyrinthe.monstres.get(0), Labyrinthe.DROITE);
        int xAfterMove = labyrinthe.monstres.get(0).getX();
        int yAfterMove = labyrinthe.monstres.get(0).getY();
        assertEquals("Le monstre ne devrait pas avoir bougé à droite car il y a un mur", initialX, xAfterMove);
        assertEquals("La position Y du monstre ne devrait pas avoir changé", initialY, yAfterMove);

        // Déplacement valide à gauche
        labyrinthe.deplacerMonstre(labyrinthe.monstres.get(0), Labyrinthe.GAUCHE);
        int xAfterLeftMove = labyrinthe.monstres.get(0).getX();
        int yAfterLeftMove = labyrinthe.monstres.get(0).getY();
        assertEquals("Le monstre devrait avoir bougé d'une case à gauche", initialX - 1, xAfterLeftMove);
        assertEquals("La position Y du monstre ne devrait pas avoir changé", initialY, yAfterLeftMove);

        // Déplacement vers le haut dans un mur
        labyrinthe.deplacerMonstre(labyrinthe.monstres.get(0), Labyrinthe.HAUT);
        int yAfterUpMove = labyrinthe.monstres.get(0).getY();
        assertEquals("Le monstre ne devrait pas avoir bougé vers le haut car il y a un mur", yAfterLeftMove, yAfterUpMove);
    }
}