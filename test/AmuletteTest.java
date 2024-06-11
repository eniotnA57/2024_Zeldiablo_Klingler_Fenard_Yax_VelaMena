

import gameLaby.laby.Labyrinthe;
import gameLaby.laby.Perso;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class AmuletteTest {

    private Labyrinthe labyrinthe;
    private final String[] labyFiles = {
            "5\n" +
                    "5\n" +
                    ".....\n" +
                    "..P..\n" +
                    "..A..\n" +
                    ".....\n" +
                    ".....\n"
    };

    @BeforeEach
    void setUp() throws IOException {
        labyrinthe = new Labyrinthe(labyFiles);
    }

    @Test
    void testRecupererAmulette() {
        Perso pj = labyrinthe.getPj();
        assertNotNull(pj);
        assertEquals(2, pj.getX());
        assertEquals(1, pj.getY());
        assertTrue(labyrinthe.getAmulette(2, 2));

        int[] nouvellePosition = Labyrinthe.getSuivant(pj.getX(), pj.getY(), Labyrinthe.DROITE);
        pj.setX(nouvellePosition[0]);
        pj.setY(nouvellePosition[1]);

        assertFalse(labyrinthe.getAmulette(pj.getX(), pj.getY()));
        assertEquals(10,pj.getPointsDeVie());
    }
}
