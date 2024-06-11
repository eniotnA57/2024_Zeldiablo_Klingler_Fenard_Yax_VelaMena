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
    private  Combat combat;

    @Before
    public void setUp() {
        labyrinthe = new Labyrinthe();
        perso = new Perso(1, 1,labyrinthe);
        monstre1 = new Monstre(2, 1);
        monstre2 = new Monstre(3, 3);
        combat = new Combat(labyrinthe);
        labyrinthe.pj = perso;
        labyrinthe.monstres = new ArrayList<>();
        labyrinthe.monstres.add(monstre1);
        labyrinthe.monstres.add(monstre2);
    }

    @Test
    public void testMonstreAttaque() {
        combat.monstreAttaque(monstre1);
        assertEquals(9, perso.getPointsDeVie());
    }

    @Test
    public void testJoueurAttaqueMonstre() {
        combat.joueurAttaque();
        assertEquals(1, monstre1.getPointsDeVie());
        assertEquals(10, perso.getPointsDeVie());
    }

    @Test
    public void testJoueurAttaqueSansMonstre() {
        labyrinthe.pj.x = 5;
        labyrinthe.pj.y = 5;
        combat.joueurAttaque();
        assertEquals(2, monstre1.getPointsDeVie());
        assertEquals(2, monstre2.getPointsDeVie());
    }
}