package me.pagar;

public enum PaymentMethod {
    
    credit_card("credit_card"),
    boleto("boleto"),
    debit_card("debit_card"),
    pix("pix");
    
    private String method;

    private PaymentMethod(String method) {
            this.method=method;
    }

    @Override
    public String toString() {
            return method;
    }
}
