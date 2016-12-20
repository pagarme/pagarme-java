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

public class AnticipationTest extends BaseTest {

    private TransactionFactory transactionFactory = new TransactionFactory();
    private Recipient defaultRecipient;

    @Before
    public void beforeEach() throws PagarMeException {
        super.setUp();
        Collection<Recipient> recipients = new Recipient().findCollection(10, 0);
        defaultRecipient = recipients.iterator().next();

        defaultRecipient = increaseAnticipationVolume(defaultRecipient);
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
        BulkAnticipation anticipation1 = createAnticipationOnDefaultRecipient(12345678);
        BulkAnticipation anticipation2 = createAnticipationOnDefaultRecipient(1234567);

        Collection<BulkAnticipation> anticipations = defaultRecipient.findAnticipations(10, 1);
        Assert.assertEquals(2, anticipations.size());
    }

    @Test
    public void testAnticipationCancelation() throws PagarMeException{
        BulkAnticipation anticipation = createAnticipationOnDefaultRecipient(12345678);
        anticipation = defaultRecipient.cancelAnticipation(anticipation);
        Assert.assertEquals(Status.CANCELED, anticipation.getStatus());
    }

    @Test
    public void testAnticipationDeletion() throws PagarMeException{
        BulkAnticipation anticipation = createBuildingAnticipationOnDefaultRecipient(12345678);

        Collection<BulkAnticipation> anticipations = defaultRecipient.findAnticipations(10, 1);
        Assert.assertEquals(1, anticipations.size());

        defaultRecipient.deleteAnticipation(anticipation);

        anticipations = defaultRecipient.findAnticipations(10, 1);
        Assert.assertEquals(0, anticipations.size());
    }

    @Test
    public void testAnticipationConfirmation() throws PagarMeException{
        BulkAnticipation anticipation = createBuildingAnticipationOnDefaultRecipient(12345678);
        Assert.assertEquals(Status.BUILDING, anticipation.getStatus());

        anticipation = defaultRecipient.confirmBulkAnticipation(anticipation);
        Assert.assertEquals(Status.PENDING, anticipation.getStatus());
    }

    private Recipient increaseAnticipationVolume(Recipient recipient) throws PagarMeException{
        recipient.setAnticipatableVolumePercentage(100);
        return recipient.save();
    }

    private BulkAnticipation createAnticipationOnDefaultRecipient(Integer requestedAmount) throws PagarMeException{
        Transaction transaction = transactionFactory.createCreditCardTransactionWithoutPinMode();
        transaction.setAmount(requestedAmount);
        transaction.save();

        BulkAnticipation anticipation = new BulkAnticipation();
        anticipation.setRequiredParametersForCreation(new DateTime().plusDays(3), Timeframe.END, requestedAmount, false);
        anticipation = defaultRecipient.anticipate(anticipation);
        return anticipation;
    }

    private BulkAnticipation createBuildingAnticipationOnDefaultRecipient(Integer requestedAmount) throws PagarMeException{
        Transaction transaction = transactionFactory.createCreditCardTransactionWithoutPinMode();
        transaction.setAmount(requestedAmount);
        transaction.save();

        BulkAnticipation anticipation = new BulkAnticipation();
        anticipation.setRequiredParametersForCreation(new DateTime().plusDays(3), Timeframe.END, requestedAmount, true);
        anticipation = defaultRecipient.anticipate(anticipation);
        return anticipation;
    }
}
