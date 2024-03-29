import haricots.*;
import org.concordion.integration.junit4.ConcordionRunner;
import org.junit.runner.RunWith;

import java.util.*;

@RunWith(ConcordionRunner.class)
public class PlanterFixture {

    public String premiereCarteEnMain(String main) {
        Haricot haricot = new Main(cartesFromString(main)).retirePremiereCarte();
        return "\""+varieteEnChaine(haricot.getVariete())+"\"";
    }

    public String planter(String main, String champ1, String champ2) throws PlantationInterditeException {
        List<Haricot> cartesEnMain = cartesFromString(main);

        Champ premierChamp = champFromString(champ1);
        Champ deuxiemeChamp = champFromString(champ2);

        Joueur joueur = new JoueurHumain(new Plantation(Arrays.asList(premierChamp, deuxiemeChamp)), new Main(cartesEnMain), new Scanner(System.in)) {
            @Override
            public int choisirChampARecolter(Plantation plantation, Haricot haricot) {
                return 2;
            }
        };
        joueur.planterPremierePhase();

        return formatPlantation(joueur);
    }

    public String recolte(String champ) throws PlantationInterditeException {
        Champ champ1 = champFromString(champ);
        Plantation plantation = new Plantation(Arrays.asList(champ1));
        int thunes = plantation.recolte(champ1);
        if (thunes <= 1) return thunes + " \"thune\"";
        else return thunes + " \"thunes\"";
    }

    private String formatPlantation(Joueur joueur) {
        StringBuilder builder = new StringBuilder();
        List<Champ> champs = joueur.getPlantation().getChamps();
        Iterator<Champ> iterator = champs.iterator();
        while (iterator.hasNext()) {
            Champ next = iterator.next();
            String afficheVariete = varieteEnChaine(next.getVarieteHaricot());
            builder.append(next.getNbHaricots()).append(" \"").append(afficheVariete).append("\"");
            if (iterator.hasNext()) builder.append(" et ");
        }
        return builder.toString();
    }

    private String varieteEnChaine(Variete varieteHaricot) {
        String afficheVariete = varieteHaricot.toString().toLowerCase();
        return afficheVariete.replace("_","-");
    }

    private Champ champFromString(String champ1) throws PlantationInterditeException {
        String[] split = champ1.split(" ");
        Champ champ = new Champ();
        Variete variete = varieteFromString(split[1]);
        for (int i = 0; i < Integer.valueOf(split[0]); i++) {
              champ.planter(new Haricot(variete));
        }
        return champ;
    }

    private List<Haricot> cartesFromString(String main) {
        String[] cartes = main.split(", ");
        List<Haricot> haricots = new ArrayList<Haricot>();
        for (String carte : cartes) {
            haricots.add(new Haricot(varieteFromString(carte)));
        }
        return haricots;
    }

    private Variete varieteFromString(String carte) {
        if (carte.contains("coriace"))
            return Variete.HARRY_CORIACE;
        if (carte.contains("colt"))
            return Variete.HARRY_COLT;
        if (carte.contains("cauchemar"))
            return Variete.HARRY_CAUCHEMAR;
        if (carte.contains("cochon"))
            return Variete.HARRY_COCHON;
        if (carte.contains("colique"))
            return Variete.HARRY_COLIQUE;
        if (carte.contains("copain"))
            return  Variete.HARRY_COPAIN;
        if (carte.contains("chaos"))
            return  Variete.HARRY_CHAOS;
        if (carte.contains("choco"))
            return  Variete.HARRY_CHOCO;
        if (carte.contains("cogne"))
            return  Variete.HARRY_COGNE;
        //if (carte.contains("comique"))
            return  Variete.HARRY_COMIQUE;
    }
}
