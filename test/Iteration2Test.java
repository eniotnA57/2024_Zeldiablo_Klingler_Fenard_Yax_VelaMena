import gameLaby.laby.Labyrinthe;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class Iteration2Test {

    private Labyrinthe labyrinthe;

    @Before
    public void setUp() throws IOException {
        // Assurez-vous d'avoir un constructeur dans Labyrinthe qui ne nécessite pas de paramètres
        // ou modifiez cette ligne pour utiliser un constructeur existant correctement.
        labyrinthe = new Labyrinthe();
    }

    /**
     * Test si le monstre se déplace bien aléatoirement quand le personnage bouge.
     * @throws IOException
     */
    @Test
    public void testMouvementRandom() throws IOException {
        // S'assurer qu'il y a des monstres dans le labyrinthe pour éviter IndexOutOfBoundsException
        if (labyrinthe.monstres.isEmpty()) {
            fail("Aucun monstre dans le labyrinthe pour tester le mouvement.");
        }

        int initialX = labyrinthe.monstres.get(0).getX();
        int initialY = labyrinthe.monstres.get(0).getY();

        // Assurez-vous que cette méthode est correctement définie pour déplacer le personnage
        // et potentiellement causer le mouvement aléatoire des monstres.
        labyrinthe.getPj().deplacerPerso(Labyrinthe.DROITE);

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
        // Assumer que "labySimple/laby0.txt" est un chemin valide pour charger un labyrinthe spécifique
        labyrinthe = new Labyrinthe(new String[]{"labySimple/laby0.txt"});

        if (labyrinthe.monstres.isEmpty()) {
            fail("Aucun monstre dans le labyrinthe pour tester la collision.");
        }

        int initialX = labyrinthe.monstres.get(0).getX();
        int initialY = labyrinthe.monstres.get(0).getY();

        // Vous devrez avoir une méthode pour déplacer spécifiquement les monstres,
        // similaire à celle utilisée pour déplacer le personnage.
        labyrinthe.monstres.get(0).deplacerMonstre(Labyrinthe.DROITE);

        int xAfterMove = labyrinthe.monstres.get(0).getX();
        int yAfterMove = labyrinthe.monstres.get(0).getY();

        assertEquals("Le monstre ne devrait pas avoir bougé à droite car il y a un mur", initialX, xAfterMove);
        assertEquals("La position Y du monstre ne devrait pas avoir changé", initialY, yAfterMove);

        labyrinthe.monstres.get(0).deplacerMonstre(Labyrinthe.GAUCHE);

        int xAfterLeftMove = labyrinthe.monstres.get(0).getX();
        int yAfterLeftMove = labyrinthe.monstres.get(0).getY();

        assertEquals("Le monstre devrait avoir bougé d'une case à gauche", initialX - 1, xAfterLeftMove);
        assertEquals("La position Y du monstre ne devrait pas avoir changé", initialY, yAfterLeftMove);

        labyrinthe.monstres.get(0).deplacerMonstre(Labyrinthe.HAUT);

        int yAfterUpMove = labyrinthe.monstres.get(0).getY();

        assertEquals("Le monstre ne devrait pas avoir bougé vers le haut car il y a un mur", yAfterLeftMove, yAfterUpMove);
    }
}
