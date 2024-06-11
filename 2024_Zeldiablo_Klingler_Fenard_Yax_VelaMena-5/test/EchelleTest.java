import gameLaby.laby.Labyrinthe;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class EchelleTest {

    private Labyrinthe labyrinthe;

    @Before
    public void setUp() {
        labyrinthe = new Labyrinthe();
    }

    @Test
    public void testEchellePosition() {
        boolean echelleFound = false;
        for (int x = 0; x < labyrinthe.getLength(); x++) {
            for (int y = 0; y < labyrinthe.getLengthY(); y++) {
                if (labyrinthe.escaliers[x][y]) {
                    echelleFound = true;
                    break;
                }
            }
        }
        assertTrue("L'échelle devrait être présente dans le labyrinthe", echelleFound);
    }

    @Test
    public void testMonteeEtage() {
        boolean echellePlaced = false;
        for (int x = 0; x < labyrinthe.getLength(); x++) {
            for (int y = 0; y < labyrinthe.getLengthY(); y++) {
                if (labyrinthe.escaliers[x][y]) {
                    labyrinthe.pj.x = x;
                    labyrinthe.pj.y = y;
                    echellePlaced = true;
                    break;
                }
            }
            if (echellePlaced) break;
        }


        assertTrue("Le personnage devrait être placé sur une échelle pour le test", echellePlaced);

        labyrinthe.deplacerPerso(Labyrinthe.DROITE);
        labyrinthe.deplacerPerso(Labyrinthe.GAUCHE);

        assertEquals("Le personnage devrait être monté à l'étage supérieur",
                1,labyrinthe.getCurrentLevel());
    }

    @Test
    public void testChargementEtageSuivant() {
        int initialLevel = labyrinthe.getCurrentLevel();

        boolean echellePlaced = false;
        for (int x = 0; x < labyrinthe.getLength(); x++) {
            for (int y = 0; y < labyrinthe.getLengthY(); y++) {
                if (labyrinthe.escaliers[x][y]) {
                    labyrinthe.pj.x = x;
                    labyrinthe.pj.y = y;
                    echellePlaced = true;
                    break;
                }
            }
            if (echellePlaced) break;
        }

        assertTrue("Le personnage devrait être placé sur une échelle pour le test", echellePlaced);

        labyrinthe.deplacerPerso(Labyrinthe.DROITE);
        labyrinthe.deplacerPerso(Labyrinthe.GAUCHE);
        assertEquals("Le niveau suivant devrait être chargé", initialLevel + 1, labyrinthe.getCurrentLevel());
    }
}
