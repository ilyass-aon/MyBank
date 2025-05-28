package banque.service.comparateur;

import banque.modele.Compte;
import java.util.Comparator;

public class ComparateurCompteParTitulaire implements Comparator<Compte> {
    @Override
    public int compare(Compte c1, Compte c2) {
        // Comparer les comptes en fonction du nom du titulaire (ordre alphabétique)
        return c1.getTitulaire().compareTo(c2.getTitulaire());
    }
}
