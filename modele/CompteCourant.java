package banque.modele;

public class CompteCourant extends Compte {
    private double decouvertAutorise;
    
    
    public CompteCourant(String numero, String titulaire, double solde, double decouvertAutorise) {
        super(numero, titulaire, solde);
        this.decouvertAutorise = decouvertAutorise;
    }
    
    
    public double getDecouvertAutorise() {
        return decouvertAutorise;
    }
    
    
    public void setDecouvertAutorise(double decouvertAutorise) {
        this.decouvertAutorise = decouvertAutorise;
    }
    
    
    @Override
    public void retirer(double montant) {
        if (montant <= 0) {
            System.out.println("Le montant du retrait doit être positif.");
            return;
        }
        
        if (getSolde() - montant >= -decouvertAutorise) {
            setSolde(getSolde() - montant);
            System.out.println("Retrait de " + montant + " MAD effectué.");
            // Ajouter la transaction à l'historique
            ajouterTransaction("RETRAIT", montant);
        } else {
            System.out.println("Opération impossible : dépassement du découvert autorisé.");
        }
    }
    

    public void appliquerFrais(double frais) {
        if (frais > 0) {
            setSolde(getSolde() - frais);
            System.out.println("Frais de " + frais + " MAD appliqués.");
            // Ajouter la transaction à l'historique
            ajouterTransaction("FRAIS", frais);
        } else {
            System.out.println("Les frais doivent être positifs.");
        }
    }
    
    @Override
    public void afficherDetails() {
        super.afficherDetails();
        System.out.println("Type de compte: Compte Courant");
        System.out.println("Découvert autorisé: " + decouvertAutorise + " MAD");
    }
}