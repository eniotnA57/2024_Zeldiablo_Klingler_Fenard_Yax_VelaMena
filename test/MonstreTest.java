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
        if (labyrinthe.monstres.isEmpty()) {
            fail("Aucun monstre chargé dans le labyrinthe");
        }
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

        labyrinthe.deplacerMonstre(monstre, Integer.parseInt(Labyrinthe.DROITE));

        int newX = monstre.getX();
        int newY = monstre.getY();

        boolean monstreDeplace = (initialX != newX || initialY != newY);
        assertTrue("Le monstre aurait dû se déplacer", monstreDeplace);
    }


    @Test
    public void testCollisionMonstre() {
        // Déplacer le monstre directement pour tester la collision
        int initialX = monstre.getX();
        int initialY = monstre.getY();

        // Tentative de déplacer le monstre vers un mur
        labyrinthe.deplacerMonstre(monstre, Integer.parseInt(Labyrinthe.DROITE)); // Assurez-vous que cette méthode est implémentée

        // Vérifier que le monstre n'a pas traversé un mur
        assertEquals("Le monstre ne doit pas traverser un mur", initialX, monstre.getX());
        assertEquals("La position Y du monstre ne doit pas avoir changé", initialY, monstre.getY());
    }
}
