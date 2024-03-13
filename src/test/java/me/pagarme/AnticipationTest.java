package me.pagarme;

import java.util.Collection;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import me.pagar.model.BulkAnticipation;
import me.pagar.model.BulkAnticipation.Status;
import me.pagar.model.BulkAnticipation.Timeframe;
import me.pagar.model.Limit;
import me.pagar.model.PagarMeException;
import me.pagar.model.Recipient;
import me.pagarme.factory.TransactionFactory;
import me.pagarme.helper.BulkAnticipationHelpers;
import me.pagarme.util.PagarmeCalendar;

public class AnticipationTest extends BaseTest {

    private TransactionFactory transactionFactory = new TransactionFactory();
    private Recipient defaultRecipient;

    @Before
    public void beforeEach() throws PagarMeException {
        super.setUp();
        defaultRecipient = BulkAnticipationHelpers.getDefaultRecipient();

        defaultRecipient = BulkAnticipationHelpers.increaseAnticipationVolume(defaultRecipient);
    }

    @Test
    public void testMaxAnticipationLimit() throws PagarMeException, Exception {
        Limit limit = defaultRecipient.getMaxAnticipationLimit(PagarmeCalendar.getValidWeekday() , Timeframe.START);

        Assert.assertEquals((Integer)0, limit.getAmount());
        Assert.assertEquals((Integer)0, limit.getAnticipationFee());
        Assert.assertEquals((Integer)0, limit.getFee());
    }

    @Test
    public void testMinAnticipationLimit() throws PagarMeException, Exception {
        Limit limit = defaultRecipient.getMinAnticipationLimit(PagarmeCalendar.getValidWeekday(), Timeframe.START);

        Assert.assertEquals((Integer)0, limit.getAmount());
        Assert.assertEquals((Integer)0, limit.getAnticipationFee());
        Assert.assertEquals((Integer)0, limit.getFee());
    }

    @Test
    public void testAnticipationCreation() throws PagarMeException, Exception {
        transaction = transactionFactory.createCreditCardTransactionWithoutPinMode();
        transaction.setAmount(1000000);
        transaction.save();

        BulkAnticipation anticipation = new BulkAnticipation();
        anticipation.setRequiredParametersForCreation(PagarmeCalendar.getValidWeekday(), Timeframe.START, 1234567);
        anticipation = defaultRecipient.anticipate(anticipation);
        Assert.assertNotNull(anticipation.getId());
        Assert.assertNotNull(anticipation.getAnticipationFee());
        Assert.assertNotNull(anticipation.getCreatedAt());
        Assert.assertNotNull(anticipation.getFee());
        Assert.assertEquals(Status.PENDING, anticipation.getStatus());
    }

    @Test
    public void testAnticipationList() throws PagarMeException, Exception {
        BulkAnticipationHelpers.createAnticipation(12345678, Timeframe.END, defaultRecipient);
        BulkAnticipationHelpers.createAnticipation(1234567, Timeframe.END, defaultRecipient);

        Collection<BulkAnticipation> anticipations = defaultRecipient.findAnticipations(10, 1);
        Assert.assertEquals(2, anticipations.size());
    }

    @Test
    public void testAnticipationCancelation() throws PagarMeException, Exception {
        BulkAnticipation anticipation = BulkAnticipationHelpers.createAnticipation(12345678, Timeframe.END, defaultRecipient);
        anticipation = defaultRecipient.cancelAnticipation(anticipation);
        Assert.assertEquals(Status.CANCELED, anticipation.getStatus());
    }
}
