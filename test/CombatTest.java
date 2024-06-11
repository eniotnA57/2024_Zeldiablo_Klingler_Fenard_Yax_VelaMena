import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import gameLaby.laby.*;

public class CombatTest {
    private Labyrinthe labyrinthe;
    private Perso perso;
    private Monstre monstre1;
    private Monstre monstre2;
    private Combat combat;

    @Before
    public void setUp() {
        // Initialisation du labyrinthe et des personnages
        labyrinthe = new Labyrinthe();
        perso = new Perso(1, 1, labyrinthe);  // Inclure une référence à Labyrinthe dans le constructeur de Perso
        monstre1 = new Monstre(2, 1, labyrinthe);  // Inclure une référence à Labyrinthe dans le constructeur de Monstre
        monstre2 = new Monstre(3, 3, labyrinthe);

        combat = new Combat(labyrinthe);
        labyrinthe.pj = perso;
        labyrinthe.monstres = new ArrayList<>();
        labyrinthe.monstres.add(monstre1);
        labyrinthe.monstres.add(monstre2);
    }

    @Test
    public void testMonstreAttaque() {
        combat.monstreAttaque(monstre1);
        assertEquals("Points de vie du personnage après attaque du monstre", 9, perso.getPointsDeVie());
    }

    @Test
    public void testJoueurAttaqueMonstre() {
        combat.joueurAttaque();
        assertTrue("Monstre 1 doit être blessé après l'attaque du joueur", monstre1.getPointsDeVie() < 2);
        assertEquals("Monstre 2 ne doit pas être blessé car il n'est pas adjacent", 2, monstre2.getPointsDeVie());
    }

    @Test
    public void testJoueurTueMonstre() {
        monstre1.takeDamage(1); // Amène les PV du monstre à 1
        combat.joueurAttaque(); // Frappe encore le monstre
        assertFalse("Le monstre 1 doit être mort et retiré de la liste", labyrinthe.monstres.contains(monstre1));
        assertTrue("Le joueur doit récupérer des points de vie après avoir tué un monstre", perso.getPointsDeVie() > 10);
    }

    @Test
    public void testJoueurAttaqueSansMonstre() {
        perso.setX(5);
        perso.setY(5);
        combat.joueurAttaque();
        assertEquals("Monstre 1 ne doit pas être affecté car trop loin", 2, monstre1.getPointsDeVie());
        assertEquals("Monstre 2 ne doit pas être affecté car trop loin", 2, monstre2.getPointsDeVie());
    }
}
