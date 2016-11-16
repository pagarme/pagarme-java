package me.pagarme.factory;

import me.pagar.model.BankAccount;

public class BankAccountFactory {

    public static String DEFAULT_AGENCIA = "0192";
    public static String DEFAULT_AGENCIA_DV = "0";
    public static String DEFAULT_CONTA = "03245";
    public static String DEFAULT_CONTA_DV = "0";
    public static String DEFAULT_BANK_CODE = "341";
    public static String DEFAULT_LEGAL_NAME = "Conta teste";
    public static String DEFAULT_DOCUMENT_NUMBER = "18344334799";
    
    
    public BankAccount create(){
        BankAccount bankAccount = new BankAccount();
        bankAccount.setAgencia(DEFAULT_AGENCIA);
        bankAccount.setAgenciaDv(DEFAULT_AGENCIA_DV);
        bankAccount.setBankCode(DEFAULT_BANK_CODE);
        bankAccount.setConta(DEFAULT_CONTA);
        bankAccount.setContaDv(DEFAULT_CONTA_DV);
        bankAccount.setLegalName(DEFAULT_LEGAL_NAME);
        bankAccount.setDocumentNumber(DEFAULT_DOCUMENT_NUMBER);
        
        return bankAccount;
    }
}