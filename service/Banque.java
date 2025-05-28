package banque.service;

import banque.modele.Compte;
import banque.service.comparateur.ComparateurCompteParSolde;
import banque.service.comparateur.ComparateurCompteParTitulaire;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public class Banque {
    private HashMap<String, Compte> comptes;
    private HashSet<String> adressesAgences;
    private static final String[] OPERATIONS_DISPONIBLES = {"Déposer", "Retirer", "Consulter Solde", "Afficher Historique"};
    
    public Banque() {
        this.comptes = new HashMap<>();
        this.adressesAgences = new HashSet<>();
    }
    
    public void ajouterCompte(Compte compte) {
        if (comptes.containsKey(compte.getNumero())) {
            System.out.println("Erreur : Un compte avec le numéro " + compte.getNumero() + " existe déjà.");
            return;
        }
        comptes.put(compte.getNumero(), compte);
        System.out.println("Compte " + compte.getNumero() + " ajouté avec succès.");
    }
    
    public Compte rechercherCompteParNumero(String numero) {
        // faut verifier le retour de get()
        return comptes.get(numero);
    }
    
    public void listerTousLesComptes() {
        if (comptes.isEmpty()) {
            System.out.println("Aucun compte n'est enregistré dans la banque.");
            return;
        }
        
        System.out.println("=== LISTE DES COMPTES ===");
        for (Compte compte : comptes.values()) {
            compte.afficherDetails();
            System.out.println("-------------------------");
        }
    }
    
    public void ajouterAdresseAgence(String adresse) {
        adressesAgences.add(adresse);
        System.out.println("Adresse d'agence ajoutée : " + adresse);
    }
    
    public void afficherAdressesAgences() {
        if (adressesAgences.isEmpty()) {
            System.out.println("Aucune adresse d'agence n'est enregistrée.");
            return;
        }
        
        System.out.println("=== ADRESSES DES AGENCES ===");
        for (String adresse : adressesAgences) {
            System.out.println("- " + adresse);
        }
        System.out.println("===========================");
    }
    
    public String[] getOperationsDisponibles() {
        return OPERATIONS_DISPONIBLES;
    }
    
    public void listerComptesParSolde() {
        if (comptes.isEmpty()) {
            System.out.println("Aucun compte n'est enregistré dans la banque.");
            return;
        }
        
        // Récupérer tous les comptes
        ArrayList<Compte> listeComptes = new ArrayList<>(comptes.values());
        
        // Trier cette ArrayList en utilisant le ComparateurCompteParSolde
        Collections.sort(listeComptes, new ComparateurCompteParSolde());
        
        // Afficher les comptes triés
        System.out.println("=== LISTE DES COMPTES PAR SOLDE (CROISSANT) ===");
        Iterator<Compte> it = listeComptes.iterator();
        while (it.hasNext()) {
            Compte compte = it.next();
            compte.afficherDetails();
            System.out.println("-------------------------");
        }
    }
    
    public void listerComptesParTitulaire() {
        if (comptes.isEmpty()) {
            System.out.println("Aucun compte n'est enregistré dans la banque.");
            return;
        }
        
        // Récupérer tous les comptes
        ArrayList<Compte> listeComptes = new ArrayList<>(comptes.values());
        
        // Trier cette ArrayList en utilisant le ComparateurCompteParTitulaire
        Collections.sort(listeComptes, new ComparateurCompteParTitulaire());
        
        // Afficher les comptes triés
        System.out.println("=== LISTE DES COMPTES PAR TITULAIRE (ALPHABÉTIQUE) ===");
        Iterator<Compte> it = listeComptes.iterator();
        while (it.hasNext()) {
            Compte compte = it.next();
            compte.afficherDetails();
            System.out.println("-------------------------");
        }
    }
}