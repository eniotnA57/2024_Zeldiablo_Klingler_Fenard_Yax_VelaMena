
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
        monstre = labyrinthe.monstre;
    }

    @Test
    public void testMonstreInitialisation() {
        assertNotNull(monstre);
        System.out.println("Initial Monstre Position: (" + monstre.getX() + ", " + monstre.getY() + ")");
        assertEquals(5, monstre.getX());
        assertEquals(2, monstre.getY());
    }

    @Test
    public void testDeplacerMonstre() {
        labyrinthe.deplacerMonstre(Labyrinthe.BAS);
        System.out.println("Monstre Position after moving down: (" + monstre.getX() + ", " + monstre.getY() + ")");
        assertEquals(5, monstre.getX());
        assertEquals(3, monstre.getY());

        labyrinthe.deplacerMonstre(Labyrinthe.HAUT);
        System.out.println("Monstre Position after moving up: (" + monstre.getX() + ", " + monstre.getY() + ")");
        assertEquals(5, monstre.getX());
        assertEquals(2, monstre.getY());
    }

    }

