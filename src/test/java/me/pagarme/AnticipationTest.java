package me.pagarme;

import java.util.Collection;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import me.pagar.model.Balance;
import me.pagar.model.BulkAnticipation;
import me.pagar.model.BulkAnticipation.Timeframe;
import me.pagar.model.BulkAnticipationLimits;
import me.pagar.model.PagarMe;
import me.pagar.model.PagarMeException;
import me.pagar.model.Recipient;
import me.pagar.model.Transaction;
import me.pagarme.factory.RecipientFactory;
import me.pagarme.factory.TransactionFactory;
import me.pagarme.helper.TestEndpoints;

public class AnticipationTest extends BaseTest {

    private RecipientFactory recipientFactory = new RecipientFactory();
    private TransactionFactory transactionFactory = new TransactionFactory();
    private Recipient defaultRecipient;
    private TestEndpoints testEndpoints = new TestEndpoints();

    @Before
    public void beforeEach() throws PagarMeException {
        super.setUp();
        PagarMe.init("ak_test_zXjKL8u5uxn25HNxHviPbhthNV0nL7");
        Collection<Recipient> recipients = new Recipient().findCollection(10, 0);
        defaultRecipient = recipients.iterator().next();

        defaultRecipient = increaseAnticipationVolume(defaultRecipient);
    }

    @Test
    public void testAnticipationLimit() throws PagarMeException{
        BulkAnticipation parameters = new BulkAnticipation();
        parameters.setRequiredParametersForAnticipationLimit(new DateTime().plusDays(10), Timeframe.START);
        BulkAnticipationLimits limits = defaultRecipient.getAnticipationLimits(parameters);

        Assert.assertEquals(Integer.valueOf(0), limits.getMaximum().getAmount());
        Assert.assertEquals(Integer.valueOf(0), limits.getMaximum().getAnticipationFee());
        Assert.assertEquals(Integer.valueOf(0), limits.getMaximum().getFee());
        Assert.assertEquals(Integer.valueOf(0), limits.getMinimum().getAmount());
        Assert.assertEquals(Integer.valueOf(0), limits.getMinimum().getAmount());
        Assert.assertEquals(Integer.valueOf(0), limits.getMinimum().getAmount());
        
    }

    @Test
    public void testAnticipationCreation() throws PagarMeException{
        Transaction transaction = transactionFactory.createBoletoTransaction();
        transaction.setAmount(Integer.MAX_VALUE);
        transaction.save();
        testEndpoints.payBoleto(transaction);

        Balance balance = defaultRecipient.balance();

        BulkAnticipation anticipation = new BulkAnticipation();
        anticipation.setRequiredParametersForCreation(new DateTime().plusDays(3), Timeframe.START, 12345678, false);
        defaultRecipient.anticipate(anticipation);
    }

    private Recipient increaseAnticipationVolume(Recipient recipient) throws PagarMeException{
        recipient.setAnticipatableVolumePercentage(100);
        return recipient.save();
    }
}
