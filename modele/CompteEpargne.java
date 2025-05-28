package banque.modele;


public class CompteEpargne extends Compte {
    private double tauxInteret;
    
    public CompteEpargne(String numero, String titulaire, double solde, double tauxInteret) {
        super(numero, titulaire, solde);
        this.tauxInteret = tauxInteret;
    }
    

    public double getTauxInteret() {
        return tauxInteret;
    }
    

    public void setTauxInteret(double tauxInteret) {
        this.tauxInteret = tauxInteret;
    }

    @Override
    public void retirer(double montant) {
        if (montant <= 0) {
            System.out.println("Le montant du retrait doit être positif.");
            return;
        }
        
        if (getSolde() >= montant) {
            setSolde(getSolde() - montant);
            System.out.println("Retrait de " + montant + " MAD effectué.");
            // Ajouter la transaction à l'historique
            ajouterTransaction("RETRAIT", montant);
        } else {
            System.out.println("Solde insuffisant pour effectuer ce retrait.");
        }
    }

    public double calculerInterets() {
        double interets = getSolde() * (tauxInteret / 100);
        setSolde(getSolde() + interets);
        System.out.println("Intérêts de " + interets + " MAD ajoutés au compte.");
        // Ajouter la transaction à l'historique
        ajouterTransaction("INTERET", interets);
        return interets;
    }
    
    @Override
    public void afficherDetails() {
        super.afficherDetails();
        System.out.println("Type de compte: Compte Épargne");
        System.out.println("Taux d'intérêt: " + (tauxInteret) + "%");
    }
}