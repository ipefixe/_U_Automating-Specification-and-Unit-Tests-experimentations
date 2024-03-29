package haricots;

import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;
import java.util.Scanner;

import static java.util.Collections.EMPTY_LIST;
import static org.junit.Assert.assertFalse;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class JoueurUnitTest {

    @Test
    public void testPasserPlanter() throws AucunChampVideException {
        Joueur joueur = new JoueurHumain(new Plantation(EMPTY_LIST), new Main(EMPTY_LIST), new Scanner(System.in));
        assertFalse(joueur.planterPremierePhase());
    }

    @Test
    public void testPlanterSimple() {
        Plantation plantation = mock(Plantation.class);
        Main main = mock(Main.class);
        Haricot haricot = mock(Haricot.class);
        when(main.retirePremiereCarte()).thenReturn(haricot);
        when(plantation.planter(haricot)).thenReturn(true);
        Joueur joueur = new JoueurHumain(plantation, main, new Scanner(System.in));
        Assert.assertTrue(joueur.planterPremierePhase());
    }

    @Test
    public void testPlanterApresVente() {
        Plantation plantation = mock(Plantation.class);
        Main main = mock(Main.class);
        Haricot haricot = mock(Haricot.class);
        when(main.retirePremiereCarte()).thenReturn(haricot);
        when(plantation.planter(haricot)).thenReturn(false, true);
        when(plantation.recolte(any(Champ.class))).thenReturn(3);
        Joueur joueur = new JoueurHumain(plantation, main, new Scanner("1"));
        Assert.assertEquals(0, joueur.getThunes());
        Assert.assertTrue(joueur.planterPremierePhase());
        Assert.assertEquals(3, joueur.getThunes());
    }

    @Test (expected = RuntimeException.class)
    public void testPropositionEchange() {
        Pioche pioche = mock(Pioche.class);
        Plantation plantation = mock(Plantation.class);
        Main main = mock(Main.class);
        Scanner scanner = new Scanner("");
        Joueur joueur = new JoueurHumain(plantation, main, scanner);
        joueur.echanger(Collections.EMPTY_LIST);
    }
}
