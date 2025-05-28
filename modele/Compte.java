package banque.modele;

import banque.service.OperationBancaire;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.ListIterator;
import java.time.LocalDateTime;


public abstract class Compte implements OperationBancaire {
    private String numero;
    private String titulaire;
    private double solde;
    private ArrayList<Transaction> historiqueTransactions;
    

    public Compte(String numero, String titulaire, double solde) {
        this.numero = numero;
        this.titulaire = titulaire;
        this.solde = solde;
        this.historiqueTransactions = new ArrayList<>();
    }
    
    // Getters
    public String getNumero() {
        return numero;
    }
    
    public String getTitulaire() {
        return titulaire;
    }
    
    public double getSolde() {
        return solde;
    }
    
    @Override
    public void deposer(double montant) {
        if (montant > 0) {
            solde += montant;
            System.out.println("Dépôt de " + montant + " MAD effectué.");
            // Ajouter la transaction à l'historique
            historiqueTransactions.add(new Transaction("DEPOT", montant, LocalDateTime.now()));
        } else {
            System.out.println("Le montant du dépôt doit être positif.");
        }
    }
    
    @Override
    public abstract void retirer(double montant);
    
    @Override
    public double consulterSolde() {
        return solde;
    }
    

    public void afficherDetails() {
        System.out.println("Numéro de compte: " + numero);
        System.out.println("Titulaire: " + titulaire);
        System.out.println("Solde: " + solde + " MAD");
    }
    

    public void setSolde(double solde) {
        this.solde = solde;
    }
    
    public void afficherHistorique() {
        if (historiqueTransactions.isEmpty()) {
            System.out.println("Aucune transaction enregistrée pour ce compte.");
            return;
        }
        
        // Trier l'historique chronologiquement
        Collections.sort(this.historiqueTransactions);
        
        System.out.println("=== HISTORIQUE DES TRANSACTIONS ===");
        // Utiliser un Iterator pour parcourir les transactions
        Iterator<Transaction> it = historiqueTransactions.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }
        System.out.println("==================================");
    }
    
    public void purgerHistoriqueAncien(LocalDateTime dateLimite) {
        // Obtenir un Iterator pour l'historique des transactions
        Iterator<Transaction> it = historiqueTransactions.iterator();
        
        // Parcourir l'historique avec l'itérateur
        while (it.hasNext()) {
            Transaction transaction = it.next();
            // Si la date de la transaction est avant la date limite
            if (transaction.getDateHeure().isBefore(dateLimite)) {
                // Supprimer la transaction de la liste
                it.remove();
            }
        }
        System.out.println("Purge de l'historique effectuée.");
    }
    
    public void commenterRetraitsImportants(double seuil, String commentaire) {
        // Obtenir un ListIterator pour l'historique des transactions
        ListIterator<Transaction> it = historiqueTransactions.listIterator();
        
        // Parcourir l'historique avec le ListIterator
        while (it.hasNext()) {
            Transaction transaction = it.next();
            // Vérifier si le type est RETRAIT et si le montant est supérieur au seuil
            if ("RETRAIT".equals(transaction.getType()) && transaction.getMontant() > seuil) {
                // Définir le commentaire
                transaction.setCommentaire(commentaire);
                // Remplacer l'élément dans la liste par la version modifiée
                it.set(transaction);
            }
        }
        System.out.println("Commentaires ajoutés aux retraits importants.");
    }
    
    protected void ajouterTransaction(String type, double montant) {
        historiqueTransactions.add(new Transaction(type, montant, LocalDateTime.now()));
    }
}