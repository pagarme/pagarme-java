package me.pagarme;

import java.util.Collection;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import me.pagar.model.BulkAnticipation;
import me.pagar.model.BulkAnticipation.Status;
import me.pagar.model.BulkAnticipation.Timeframe;
import me.pagar.model.Limit;
import me.pagar.model.PagarMeException;
import me.pagar.model.Recipient;
import me.pagar.model.Transaction;
import me.pagarme.factory.TransactionFactory;
import me.pagarme.helper.TestCases;

public class AnticipationTest extends BaseTest {

    private TransactionFactory transactionFactory = new TransactionFactory();
    private Recipient defaultRecipient;

    @Before
    public void beforeEach() throws PagarMeException {
        super.setUp();
        Collection<Recipient> recipients = new Recipient().findCollection(10, 0);
        defaultRecipient = recipients.iterator().next();

        defaultRecipient = TestCases.increaseAnticipationVolume(defaultRecipient);
    }

    @Test
    public void testMaxAnticipationLimit() throws PagarMeException{
        Limit limit = defaultRecipient.getMaxAnticipationLimit(new DateTime().plusDays(10), Timeframe.START);

        Assert.assertEquals(Integer.valueOf(0), limit.getAmount());
        Assert.assertEquals(Integer.valueOf(0), limit.getAnticipationFee());
        Assert.assertEquals(Integer.valueOf(0), limit.getFee());
    }

    @Test
    public void testMinAnticipationLimit() throws PagarMeException{
        Limit limit = defaultRecipient.getMinAnticipationLimit(new DateTime().plusDays(10), Timeframe.START);

        Assert.assertEquals(Integer.valueOf(0), limit.getAmount());
        Assert.assertEquals(Integer.valueOf(0), limit.getAnticipationFee());
        Assert.assertEquals(Integer.valueOf(0), limit.getFee());
    }

    @Test
    public void testAnticipationCreation() throws PagarMeException{
        Transaction transaction = transactionFactory.createCreditCardTransactionWithoutPinMode();
        transaction.setAmount(1000000);
        transaction.save();

        BulkAnticipation anticipation = new BulkAnticipation();
        anticipation.setRequiredParametersForCreation(new DateTime().plusDays(3), Timeframe.START, 1234567, false);
        anticipation = defaultRecipient.anticipate(anticipation);
        Assert.assertNotNull(anticipation.getId());
        Assert.assertNotNull(anticipation.getAnticipationFee());
        Assert.assertNotNull(anticipation.getCreatedAt());
        Assert.assertNotNull(anticipation.getFee());
        Assert.assertEquals(Status.PENDING, anticipation.getStatus());
    }

    @Test
    public void testAnticipationList() throws PagarMeException{
        BulkAnticipation anticipation1 = TestCases.createAnticipationOnRecipient(12345678, defaultRecipient);
        BulkAnticipation anticipation2 = TestCases.createAnticipationOnRecipient(1234567, defaultRecipient);

        Collection<BulkAnticipation> anticipations = defaultRecipient.findAnticipations(10, 1);
        Assert.assertEquals(2, anticipations.size());
    }

    @Test
    public void testAnticipationCancelation() throws PagarMeException{
        BulkAnticipation anticipation = TestCases.createAnticipationOnRecipient(12345678, defaultRecipient);
        anticipation = defaultRecipient.cancelAnticipation(anticipation);
        Assert.assertEquals(Status.CANCELED, anticipation.getStatus());
    }

    @Test
    public void testAnticipationDeletion() throws PagarMeException{
        BulkAnticipation anticipation = TestCases.createBuildingAnticipationOnRecipient(12345678, defaultRecipient);

        Collection<BulkAnticipation> anticipations = defaultRecipient.findAnticipations(10, 1);
        Assert.assertEquals(1, anticipations.size());

        defaultRecipient.deleteAnticipation(anticipation);

        anticipations = defaultRecipient.findAnticipations(10, 1);
        Assert.assertEquals(0, anticipations.size());
    }

    @Test
    public void testAnticipationConfirmation() throws PagarMeException{
        BulkAnticipation anticipation = TestCases.createBuildingAnticipationOnRecipient(12345678, defaultRecipient);
        Assert.assertEquals(Status.BUILDING, anticipation.getStatus());

        anticipation = defaultRecipient.confirmBulkAnticipation(anticipation);
        Assert.assertEquals(Status.PENDING, anticipation.getStatus());
    }
}
