package me.pagarme;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import me.pagar.model.Payable;
import me.pagar.model.Payable.Status;
import me.pagar.model.Payable.Type;
import me.pagar.model.Recipient;
import me.pagar.model.SplitRule;
import me.pagar.model.Transaction;
import me.pagar.model.Transaction.PaymentMethod;
import me.pagarme.factory.RecipientFactory;

public class PayableTest extends BaseTest {

    private Payable payable;

    private RecipientFactory recipientFactory = new RecipientFactory();

    @Before
    public void setUp() {
        super.setUp();
        payable = new Payable();
        transaction = new Transaction();
    }

    @Test
    public void testRetrieveTransactionPayables() throws Throwable {
        transaction = this.transactionCreditCardCommon();
        transaction.save();
        Collection<Payable> payables = payable.findCollection(transaction, 10, 1);

        Assert.assertEquals(1, payables.size());
    }

    @Test
    public void testRetrieveTransactionPayableFields() throws Throwable {
        transaction = this.transactionCreditCardCommon();
        transaction.save();

        Collection<Payable> payables = payable.findCollection(transaction, 10, 1);

        Assert.assertEquals(1, payables.size());

        Payable payable = payables.iterator().next();

        Assert.assertEquals(100, (int) payable.getAmount());
        Assert.assertEquals(5, (int) payable.getFee());
        Assert.assertEquals(1, (int) payable.getInstallment());
        Assert.assertEquals(PaymentMethod.CREDIT_CARD, payable.getPaymentMethod());
        Assert.assertNotNull(payable.getTransactionId());
        Assert.assertNotNull(payable.getPayment());
        Assert.assertEquals(Status.WAITING_FUNDS, payable.getStatus());
        Assert.assertEquals(Type.CREDIT, payable.getType());
        Assert.assertNotNull(payable.getRecipientId());
        Assert.assertEquals("0", payable.getAnticipationFee());
    }

    @Test
    public void testRetrieveTransactionPayable() throws Throwable {
        transaction = this.transactionCreditCardCommon();
        transaction.save();
        Collection<Payable> payables = payable.findCollection(transaction, 10, 1);

        Assert.assertEquals(1, payables.size());

        Payable payableFromCollection = payables.iterator().next();

        Payable payableFromFind = payable.find(payableFromCollection.getId());

        Assert.assertEquals(payableFromCollection, payableFromFind);
    }

    @Test
    public void testRetrievePayableWithFilters() throws Throwable {
        transaction = this.transactionCreditCardCommon();
        transaction.setCapture(true);
        transaction.setAmount(10000);

        Collection<SplitRule> splitRules = new ArrayList<SplitRule>();

        Recipient recipient1 = recipientFactory.create();
        SplitRule splitRule1 = new SplitRule();

        recipient1.save();
        splitRule1.setRecipientId(recipient1.getId());
        splitRule1.setPercentage(50);
        splitRule1.setLiable(true);
        splitRule1.setChargeProcessingFee(true);
        splitRules.add(splitRule1);

        Recipient recipient2 = recipientFactory.create();
        SplitRule splitRule2 = new SplitRule();
        recipient2.save();
        splitRule2.setRecipientId(recipient2.getId());
        splitRule2.setPercentage(50);
        splitRule2.setLiable(true);
        splitRule2.setChargeProcessingFee(true);

        splitRules.add(splitRule2);
        transaction.setSplitRules(splitRules);
        transaction.save();

        Collection<Payable> payables = payable.findCollection(transaction, 10, 1);

        Assert.assertEquals(2, payables.size());

        String idRecipientToFilter = recipient1.getId();

        Map<String, Object> filters = new HashMap<String, Object>();
        filters.put("recipient_id", idRecipientToFilter);

        payables = payable.findCollection(10, 1, filters);

        Assert.assertEquals(1, payables.size());
    }

}