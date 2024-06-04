import gameLaby.laby.Labyrinthe;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class Iteration2Test {
    /**
     * Test si le monstre se déplace bien aléatoirement quand le personnage bouge
     * @throws IOException
     */
    @Test
    public void testMouvementRandom() throws IOException {
            Labyrinthe laby = new Labyrinthe();
            int x = laby.monstre.getX();
            int y = laby.monstre.getY();
            laby.deplacerPerso(Labyrinthe.DROITE);
            int x2 = laby.monstre.getX();
            int y2 = laby.monstre.getY();
            boolean b = (x != x2 || y != y2);
            assertNotEquals(false, b);
    }

    /**
     * Test si les collisions fonctionne quand le monstre se déplace
     * @throws IOException
     */
    @Test
    public void testCollisionMonstre() throws IOException {
        Labyrinthe laby = new Labyrinthe("labySimple/laby0.txt");
        laby.deplacerMonstre(Labyrinthe.DROITE);
        int x1 = laby.monstre.getX();
        assertEquals(5,x1);

        laby.deplacerMonstre(Labyrinthe.DROITE);
        int x2 = laby.monstre.getX();
        assertEquals(5,x2);

        laby.deplacerMonstre(Labyrinthe.GAUCHE);
        int x3 = laby.monstre.getX();
        assertEquals(4,x3);

        laby.deplacerMonstre(Labyrinthe.GAUCHE);
        int y1 = laby.monstre.getY();
        int x4 = laby.monstre.getX();
        assertEquals(3,x4);
        assertEquals(3,y1);


        laby.deplacerMonstre(Labyrinthe.HAUT);
        int y2 = laby.monstre.getY();
        assertEquals(3,y2);

        laby.deplacerMonstre(Labyrinthe.HAUT);
        int y3 = laby.monstre.getY();
        assertEquals(3,y3);
    }
}
