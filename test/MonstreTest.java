import gameLaby.laby.Labyrinthe;
import gameLaby.laby.Monstre;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class MonstreTest {

    private Labyrinthe labyrinthe;
    private Monstre monstre;

    @Before
    public void setUp() throws IOException {
        labyrinthe = new Labyrinthe();
        monstre = labyrinthe.monstres.get(0);
    }

    @Test
    public void testMonstreInitialisation() {
        assertNotNull("Le monstre ne doit pas être null après l'initialisation", monstre);
        assertEquals("La position initiale en X du monstre doit être 5", 5, monstre.getX());
        assertEquals("La position initiale en Y du monstre doit être 2", 2, monstre.getY());
    }

    @Test
    public void testDeplacerMonstre() {
        int initialX = monstre.getX();
        int initialY = monstre.getY();

        labyrinthe.pj.deplacerPerso(Labyrinthe.DROITE);

        int newX = monstre.getX();
        int newY = monstre.getY();

        boolean monstreDeplace = (initialX != newX || initialY != newY);
        assertTrue("Le monstre aurait dû se déplacer après le mouvement du personnage", monstreDeplace);
    }

    @Test
    public void testCollisionMonstre() {
        int initialX = monstre.getX();
        int initialY = monstre.getY();
        labyrinthe.pj.deplacerPerso(Labyrinthe.DROITE);

        labyrinthe.pj.deplacerPerso(Labyrinthe.DROITE);
        int postMoveX = monstre.getX();
        int postMoveY = monstre.getY();

        boolean collisionDetected = labyrinthe.getMur(postMoveX, postMoveY);

        assertFalse("Le monstre ne doit pas traverser un mur", collisionDetected);
    }
}