package me.pagarme.helper;

import java.util.Collection;

import me.pagar.model.BulkAnticipation;
import me.pagar.model.BulkAnticipation.Timeframe;
import me.pagar.model.PagarMeException;
import me.pagar.model.Recipient;
import me.pagar.model.Transaction;
import me.pagarme.factory.TransactionFactory;
import me.pagarme.util.PagarmeCalendar;

public class BulkAnticipationHelpers {

    private static TransactionFactory transactionFactory = new TransactionFactory();

    public static Recipient increaseAnticipationVolume(Recipient recipient) throws PagarMeException{
        recipient.setAnticipatableVolumePercentage(100);
        return recipient.save();
    }

    public static BulkAnticipation createAnticipation(Integer requestedAmount, Timeframe timeFrame, Recipient recipient)
            throws PagarMeException, Exception {
        Transaction transaction = transactionFactory.createCreditCardTransactionWithoutPinMode();
        transaction.setAmount(requestedAmount);
        transaction.save();

        BulkAnticipation anticipation = new BulkAnticipation();
        anticipation.setRequiredParametersForCreation(PagarmeCalendar.getValidWeekday(), timeFrame, requestedAmount);
        anticipation = recipient.anticipate(anticipation);
        return anticipation;
    }

    public static Recipient getDefaultRecipient() throws PagarMeException{
        Collection<Recipient> recipients = new Recipient().findCollection(10, 0);
        return recipients.iterator().next();
    }
}
