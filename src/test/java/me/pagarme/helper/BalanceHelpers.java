package me.pagarme.helper;

import java.util.Arrays;

import me.pagar.model.PagarMeException;
import me.pagar.model.Recipient;
import me.pagar.model.SplitRule;
import me.pagar.model.Transaction;
import me.pagarme.factory.TransactionFactory;

public class BalanceHelpers {

    private static TestEndpoints testEndpoints = new TestEndpoints();
    private static TransactionFactory transactionFacotry = new TransactionFactory();

    public static void addAvailableBalance(Recipient recipient, Integer amount) throws PagarMeException{
        //Coloca um pouco de saldo na conta desse recebedor
        SplitRule split2 = new SplitRule();
        split2.setPercentage(100);
        split2.setRecipientId(recipient.getId());
        Transaction transaction = transactionFacotry.createBoletoTransaction();
        transaction.setAmount(amount);
        transaction.setSplitRules(Arrays.asList(
            split2
        ));
        transaction.save();
        testEndpoints.payBoleto(transaction);
    }
}
