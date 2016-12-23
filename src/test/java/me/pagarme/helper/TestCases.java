package me.pagarme.helper;

import org.joda.time.DateTime;

import me.pagar.model.BulkAnticipation;
import me.pagar.model.BulkAnticipation.Timeframe;
import me.pagarme.factory.TransactionFactory;
import me.pagar.model.PagarMeException;
import me.pagar.model.Recipient;
import me.pagar.model.Transaction;

public class TestCases {

    private static TransactionFactory transactionFactory = new TransactionFactory();

    public static Recipient increaseAnticipationVolume(Recipient recipient) throws PagarMeException{
        recipient.setAnticipatableVolumePercentage(100);
        return recipient.save();
    }

    public static BulkAnticipation createAnticipationOnRecipient(Integer requestedAmount, Recipient defaultRecipient) throws PagarMeException{
        Transaction transaction = transactionFactory.createCreditCardTransactionWithoutPinMode();
        transaction.setAmount(requestedAmount);
        transaction.save();

        BulkAnticipation anticipation = new BulkAnticipation();
        anticipation.setRequiredParametersForCreation(new DateTime().plusDays(3), Timeframe.END, requestedAmount, false);
        anticipation = defaultRecipient.anticipate(anticipation);
        return anticipation;
    }

    public static BulkAnticipation createBuildingAnticipationOnRecipient(Integer requestedAmount, Recipient defaultRecipient) throws PagarMeException{
        Transaction transaction = transactionFactory.createCreditCardTransactionWithoutPinMode();
        transaction.setAmount(requestedAmount);
        transaction.save();

        BulkAnticipation anticipation = new BulkAnticipation();
        anticipation.setRequiredParametersForCreation(new DateTime().plusDays(3), Timeframe.END, requestedAmount, true);
        anticipation = defaultRecipient.anticipate(anticipation);
        return anticipation;
    }
}
