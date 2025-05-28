package banque.service.comparateur;

import banque.modele.Compte;
import java.util.Comparator;

public class ComparateurCompteParSolde implements Comparator<Compte> {
    @Override
    public int compare(Compte c1, Compte c2) {
        // Comparer les comptes en fonction de leur solde (ordre croissant)
        return Double.compare(c1.getSolde(), c2.getSolde());
    }
}
