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
        labyrinthe = new Labyrinthe("labySimple/laby1.txt");
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
        // Sauvegarde de la position initiale
        int initialX = monstre.getX();
        int initialY = monstre.getY();

        // Déplacer le personnage pour déclencher le mouvement du monstre
        labyrinthe.deplacerPerso(Labyrinthe.DROITE);

        // Récupérer la nouvelle position du monstre
        int newX = monstre.getX();
        int newY = monstre.getY();

        // Vérifier si le monstre s'est déplacé
        boolean monstreDeplace = (initialX != newX || initialY != newY);
        assertTrue("Le monstre aurait dû se déplacer après le mouvement du personnage", monstreDeplace);
    }

    @Test
    public void testCollisionMonstre() {
        // Initialiser la position de départ
        int initialX = monstre.getX();
        int initialY = monstre.getY();
        labyrinthe.deplacerPerso(Labyrinthe.DROITE);

        // Déplacer le personnage pour essayer de provoquer un mouvement du monstre dans un mur
        labyrinthe.deplacerPerso(Labyrinthe.DROITE);

        // Vérifier que le monstre n'a pas traversé un mur
        int postMoveX = monstre.getX();
        int postMoveY = monstre.getY();

        boolean collisionDetected = labyrinthe.getMur(postMoveX, postMoveY);

        assertFalse("Le monstre ne doit pas traverser un mur", collisionDetected);
    }
}