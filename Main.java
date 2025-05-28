package banque;

import banque.modele.*;
import banque.service.Banque;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // Instancier un objet Banque
        Banque banque = new Banque();
        
        // Ajouter quelques adresses d'agences
        banque.ajouterAdresseAgence("123 Avenue Mohammed V, Casablanca");
        banque.ajouterAdresseAgence("45 Rue Allal Ben Abdellah, Rabat");
        banque.ajouterAdresseAgence("78 Boulevard Hassan II, Marrakech");
        
        System.out.println("=== APPLICATION BANCAIRE ===");
        
        // Menu principal
        boolean quitter = false;
        while (!quitter) {
            System.out.println("\n=== MENU PRINCIPAL ===");
            System.out.println("1. Ajouter un compte");
            System.out.println("2. Sélectionner un compte");
            System.out.println("3. Lister tous les comptes");
            System.out.println("4. Lister les comptes par solde");
            System.out.println("5. Lister les comptes par titulaire");
            System.out.println("6. Afficher les adresses des agences");
            System.out.println("0. Quitter");
            
            int choix = getIntInput(scanner, "Votre choix: ");
            
            switch (choix) {
                case 1:
                    ajouterCompte(scanner, banque);
                    break;
                case 2:
                    selectionnerCompte(scanner, banque);
                    break;
                case 3:
                    banque.listerTousLesComptes();
                    break;
                case 4:
                    banque.listerComptesParSolde();
                    break;
                case 5:
                    banque.listerComptesParTitulaire();
                    break;
                case 6:
                    banque.afficherAdressesAgences();
                    break;
                case 0:
                    quitter = true;
                    System.out.println("Merci d'avoir utilisé notre application bancaire. Au revoir !");
                    break;
                default:
                    System.out.println("Choix invalide, veuillez réessayer.");
            }
        }
        
        scanner.close();
    }
    
    private static void ajouterCompte(Scanner scanner, Banque banque) {
        System.out.println("\n=== CREATION DE COMPTE ===");
        System.out.println("Quel type de compte souhaitez-vous créer ?");
        System.out.println("1. Compte Courant");
        System.out.println("2. Compte Épargne");
        
        int choixCompte = getIntInput(scanner, "Votre choix (1/2): ");
        
        System.out.print("Numéro de compte: ");
        String numero = scanner.nextLine();
        
        System.out.print("Nom du titulaire: ");
        String titulaire = scanner.nextLine();
        
        double soldeInitial = getDoubleInput(scanner, "Solde initial: ");
        
        if (choixCompte == 1) {
            double decouvert = getDoubleInput(scanner, "Découvert autorisé: ");
            CompteCourant compte = new CompteCourant(numero, titulaire, soldeInitial, decouvert);
            banque.ajouterCompte(compte);
        } else if (choixCompte == 2) {
            double tauxInteret = getDoubleInput(scanner, "Taux d'intérêt (en %): ");
            CompteEpargne compte = new CompteEpargne(numero, titulaire, soldeInitial, tauxInteret);
            banque.ajouterCompte(compte);
        } else {
            System.out.println("Choix invalide, opération annulée.");
        }
    }
    
    private static void selectionnerCompte(Scanner scanner, Banque banque) {
        System.out.print("\nEntrez le numéro du compte: ");
        String numero = scanner.nextLine();
        
        Compte compteTrouve = banque.rechercherCompteParNumero(numero);
        
        if (compteTrouve == null) {
            System.out.println("Aucun compte trouvé avec le numéro " + numero);
            return;
        }
        
        System.out.println("Compte trouvé:");
        compteTrouve.afficherDetails();
        
        // Sous-menu pour le compte sélectionné
        boolean retourMenu = false;
        while (!retourMenu) {
            System.out.println("\n=== OPERATIONS SUR LE COMPTE " + compteTrouve.getNumero() + " ===");
            String[] operations = banque.getOperationsDisponibles();
            
            for (int i = 0; i < operations.length; i++) {
                System.out.println((i + 1) + ". " + operations[i]);
            }
            
            // Options spécifiques au type de compte
            if (compteTrouve instanceof CompteCourant) {
                System.out.println("5. Appliquer des frais bancaires");
            } else if (compteTrouve instanceof CompteEpargne) {
                System.out.println("5. Calculer et ajouter les intérêts");
            }
            
            System.out.println("0. Retour au menu principal");
            
            int choixOperation = getIntInput(scanner, "Votre choix: ");
            
            switch (choixOperation) {
                case 1: // Déposer
                    double montantDepot = getDoubleInput(scanner, "Montant à déposer: ");
                    compteTrouve.deposer(montantDepot);
                    break;
                case 2: // Retirer
                    double montantRetrait = getDoubleInput(scanner, "Montant à retirer: ");
                    compteTrouve.retirer(montantRetrait);
                    break;
                case 3: // Consulter Solde
                    System.out.println("Solde actuel: " + compteTrouve.consulterSolde() + " MAD");
                    break;
                case 4: // Afficher Historique
                    compteTrouve.afficherHistorique();
                    break;
                case 5: // Opérations spécifiques
                    if (compteTrouve instanceof CompteCourant) {
                        double frais = getDoubleInput(scanner, "Montant des frais à appliquer: ");
                        ((CompteCourant) compteTrouve).appliquerFrais(frais);
                    } else if (compteTrouve instanceof CompteEpargne) {
                        ((CompteEpargne) compteTrouve).calculerInterets();
                    }
                    break;
                case 0:
                    retourMenu = true;
                    break;
                default:
                    System.out.println("Choix invalide, veuillez réessayer.");
            }
        }
    }
    
    private static int getIntInput(Scanner scanner, String prompt) {
        int input = 0;
        boolean validInput = false;
        
        while (!validInput) {
            System.out.print(prompt);
            try {
                input = Integer.parseInt(scanner.nextLine());
                validInput = true;
            } catch (NumberFormatException e) {
                System.out.println("Veuillez entrer un nombre entier valide.");
            }
        }
        
        return input;
    }
    
    private static double getDoubleInput(Scanner scanner, String prompt) {
        double input = 0;
        boolean validInput = false;
        
        while (!validInput) {
            System.out.print(prompt);
            try {
                input = Double.parseDouble(scanner.nextLine());
                validInput = true;
            } catch (NumberFormatException e) {
                System.out.println("Veuillez entrer un nombre décimal valide.");
            }
        }
        
        return input;
    }
}