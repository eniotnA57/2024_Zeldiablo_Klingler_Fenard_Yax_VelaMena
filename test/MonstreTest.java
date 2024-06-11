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
        // Initialisation du labyrinthe pour le test
        String labyString =
                "5\n" +
                        "5\n" +
                        ".....\n" +
                        ".....\n" +
                        "..P..\n" +
                        ".....\n" +
                        "..M..\n";
        labyrinthe = new Labyrinthe(new String[]{labyString});
        if (labyrinthe.monstres.isEmpty()) {
            fail("Aucun monstre chargé dans le labyrinthe");
        }
        monstre = labyrinthe.monstres.get(0);
    }

    @Test
    public void testMonstreInitialisation() {
        assertNotNull("Le monstre ne doit pas être null après l'initialisation", monstre);
        assertEquals("La position initiale en X du monstre doit être 2", 2, monstre.getX());
        assertEquals("La position initiale en Y du monstre doit être 4", 4, monstre.getY());
    }

    @Test
    public void testDeplacerMonstre() {
        int initialX = monstre.getX();
        int initialY = monstre.getY();

        labyrinthe.pj.deplacerPerso(Labyrinthe.DROITE);

        int newX = monstre.getX();
        int newY = monstre.getY();

        boolean monstreDeplace = (initialX != newX || initialY != newY);
        assertTrue("Le monstre aurait dû se déplacer", monstreDeplace);
    }

    @Test
    public void testCollisionMonstre() throws IOException {
        String labyString =
                "5\n" +
                        "5\n" +
                        ".....\n" +
                        ".X...\n" +
                        ".....\n" +
                        ".....\n" +
                        "..M..\n";
        labyrinthe = new Labyrinthe(new String[]{labyString});
        monstre = labyrinthe.monstres.get(0);

        int initialX = monstre.getX();
        int initialY = monstre.getY();

        monstre.deplacerMonstre(Labyrinthe.DROITE);
        int xAfterMove = monstre.getX();
        int yAfterMove = monstre.getY();
        assertEquals("Le monstre ne devrait pas avoir bougé à droite car il y a un mur", initialX, xAfterMove);
        assertEquals("La position Y du monstre ne devrait pas avoir changé", initialY, yAfterMove);

        monstre.deplacerMonstre(Labyrinthe.GAUCHE);
        int xAfterLeftMove = monstre.getX();
        int yAfterLeftMove = monstre.getY();
        assertEquals("Le monstre devrait avoir bougé d'une case à gauche", initialX - 1, xAfterLeftMove);
        assertEquals("La position Y du monstre ne devrait pas avoir changé", initialY, yAfterLeftMove);

        monstre.deplacerMonstre(Labyrinthe.HAUT);
        int yAfterUpMove = monstre.getY();
        assertEquals("Le monstre ne devrait pas avoir bougé vers le haut car il y a un mur", yAfterLeftMove, yAfterUpMove);
    }
}
