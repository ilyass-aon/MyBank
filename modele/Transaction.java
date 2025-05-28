package banque.modele;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction implements Comparable<Transaction> {
    private String type;
    private double montant;
    private LocalDateTime dateHeure;
    private String commentaire;
    
    public Transaction(String type, double montant, LocalDateTime dateHeure) {
        this.type = type;
        this.montant = montant;
        this.dateHeure = dateHeure;
        this.commentaire = "";
    }
    
    public String getType() {
        return type;
    }
    
    public double getMontant() {
        return montant;
    }
    
    public LocalDateTime getDateHeure() {
        return dateHeure;
    }
    
    public String getCommentaire() {
        return commentaire;
    }
    
    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }
    
    @Override
    public int compareTo(Transaction autre) {
        return this.dateHeure.compareTo(autre.dateHeure);
    }
    
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        String result = type + " de " + montant + " MAD le " + dateHeure.format(formatter);
        if (commentaire != null && !commentaire.isEmpty()) {
            result += " [Commentaire : " + commentaire + "]";
        }
        return result;
    }
}