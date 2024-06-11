import gameLaby.laby.Labyrinthe;
import gameLaby.laby.Monstre;
import gameLaby.laby.Perso;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class Iteration2Test {

    private Labyrinthe labyrinthe;
    private Perso pj;

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
        pj = labyrinthe.getPj();
    }

    @Test
    public void testMouvementRandom() {
        assertFalse("Il n'y a pas de monstres dans le labyrinthe", labyrinthe.monstres.isEmpty());

        int initialX = labyrinthe.monstres.get(0).getX();
        int initialY = labyrinthe.monstres.get(0).getY();

        pj.deplacerPerso(Labyrinthe.DROITE);

        int newX = labyrinthe.monstres.get(0).getX();
        int newY = labyrinthe.monstres.get(0).getY();

        boolean monstreDeplace = (initialX != newX || initialY != newY);
        assertTrue("Le monstre ne s'est pas déplacé aléatoirement", monstreDeplace);
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
        Monstre monstre = labyrinthe.monstres.get(0);

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
