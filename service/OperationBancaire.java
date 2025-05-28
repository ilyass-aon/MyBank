package banque.service;


public interface OperationBancaire {
    
    void deposer(double montant);
    
    void retirer(double montant);
    
    double consulterSolde();
}