
package me.pagar.model;

/**
 *
 * @author jonatasmaxi
 */
public class Installment  extends PagarMeModel<String> {
    int installment;
    int amount; 
    int installment_amount; 

    public int getInstallment() {
        return installment;
    }

    public void setInstallment(int installment) {
        this.installment = installment;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getInstallmentAmount() {
        return installment_amount;
    }

    public void setInstallmentAmount(int installment_amount) {
        this.installment_amount = installment_amount;
    }
    
    

    
}
