package me.pagarme;

import java.util.ArrayList;
import java.util.Collection;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import me.pagar.model.PagarMeException;
import me.pagar.model.Payable;
import me.pagar.model.Payable.Status;
import me.pagar.model.Payable.Type;
import me.pagar.model.Recipient;
import me.pagar.model.SplitRule;
import me.pagar.model.Transaction;
import me.pagar.model.Transaction.PaymentMethod;
import me.pagar.model.filter.PayableQueriableFields;
import me.pagarme.factory.RecipientFactory;
import me.pagarme.factory.TransactionFactory;
import me.pagarme.helper.TestEndpoints;

public class PayableTest extends BaseTest {

    private RecipientFactory recipientFactory = new RecipientFactory();
    private TransactionFactory transactionFactory = new TransactionFactory();
    private TestEndpoints testEndpoints = new TestEndpoints();

    @Before
    public void setUp() {
        super.setUp();
    }

    @Test
    public void testRetrieveTransactionPayables() throws Throwable {
        Transaction transaction = transactionFactory.createBoletoTransaction();
        transaction.save();
        testEndpoints.payBoleto(transaction);
        Collection<Payable> payables = transaction.findPayableCollection(10, 0);
        Assert.assertEquals(1, payables.size());
    }

    @Test
    public void testRetrieveTransactionPayableFields() throws Throwable {
        Transaction transaction = transactionFactory.createBoletoTransaction();
        transaction.save();
        testEndpoints.payBoleto(transaction);
        Collection<Payable> payables = transaction.findPayableCollection(10, 0);
        Assert.assertEquals(1, payables.size());

        Payable payableFromCollection = payables.iterator().next();
        Payable payableFromFind = new Payable().find(payableFromCollection.getId());
        Assert.assertEquals(transaction.getAmount(), payableFromFind.getAmount());
        Assert.assertNotNull(payableFromFind.getFee());
        Assert.assertEquals(PaymentMethod.BOLETO, payableFromFind.getPaymentMethod());
        Assert.assertNotNull(payableFromFind.getTransactionId());
        Assert.assertNotNull(payableFromFind.getPaymentDate());
        Assert.assertEquals(Status.PAID, payableFromFind.getStatus());
        Assert.assertEquals(Type.CREDIT, payableFromFind.getType());
        Assert.assertNotNull(payableFromFind.getRecipientId());
        Assert.assertNotNull(payableFromFind.getAnticipationFee());
    }

    @Test
    public void testRetrievePayableWithFilters() throws Throwable {
        Transaction transaction = transactionFactory.createBoletoTransaction();
        transaction.setCapture(true);
        transaction.setAmount(10000);

        Collection<SplitRule> splitRules = new ArrayList<SplitRule>();
        
        Recipient recipient1 = recipientFactory.create();
        recipient1.save();
        SplitRule splitRule1 = new SplitRule();
        recipient1.save();
        splitRule1.setRecipientId(recipient1.getId());
        splitRule1.setPercentage(50);
        splitRule1.setLiable(true);
        splitRule1.setChargeProcessingFee(true);
        splitRules.add(splitRule1);

        Recipient recipient2 = recipientFactory.create();
        recipient2.save();
        SplitRule splitRule2 = new SplitRule();
        recipient2.save();
        splitRule2.setRecipientId(recipient2.getId());
        splitRule2.setPercentage(50);
        splitRule2.setLiable(true);
        splitRule2.setChargeProcessingFee(true);

        splitRules.add(splitRule2);
        transaction.setSplitRules(splitRules);
        transaction.save();
        testEndpoints.payBoleto(transaction);
        Collection<Payable> payables = transaction.findPayableCollection(10, 0);
        Assert.assertEquals(2, payables.size());

        String idRecipientToFilter = recipient1.getId();
        PayableQueriableFields filter = new PayableQueriableFields();
        filter.recipientIdEquals(idRecipientToFilter);
        Collection<Payable> filteredPayables = new Payable().findCollection(10, 1, filter);
        Assert.assertEquals(1, filteredPayables.size());
    }

    @Test
    public void testCreatedAtDateFilters() throws PagarMeException{
        Transaction transaction = transactionFactory.createBoletoTransaction();
        transaction.save();
        testEndpoints.payBoleto(transaction);

        DateTime yesterday = new DateTime().minusDays(1);

        PayableQueriableFields beforeDateFilter = new PayableQueriableFields();
        beforeDateFilter.createdBefore(yesterday);
        Collection<Payable> foundPayables = new Payable().findCollection(10, 0, beforeDateFilter);
        Assert.assertEquals(0, foundPayables.size());

        PayableQueriableFields afterDateFilter = new PayableQueriableFields();
        afterDateFilter.createdAfter(yesterday);
        foundPayables = new Payable().findCollection(10, 0, afterDateFilter);
        Assert.assertEquals(1, foundPayables.size());
    }

    @Test
    public void testPaymentDateFilters() throws PagarMeException{
        Transaction transaction = transactionFactory.createBoletoTransaction();
        transaction.save();
        testEndpoints.payBoleto(transaction);

        DateTime yesterday = new DateTime().minusDays(1);

        PayableQueriableFields beforeDateFilter = new PayableQueriableFields();
        beforeDateFilter.paymentBefore(yesterday);
        Collection<Payable> foundPayables = new Payable().findCollection(10, 0, beforeDateFilter);
        Assert.assertEquals(0, foundPayables.size());

        PayableQueriableFields afterDateFilter = new PayableQueriableFields();
        afterDateFilter.paymentAfter(yesterday);
        foundPayables = new Payable().findCollection(10, 0, afterDateFilter);
        Assert.assertEquals(1, foundPayables.size());
    }

    @Test
    public void testPaymentStatusFilter() throws PagarMeException{
        Transaction transaction = transactionFactory.createBoletoTransaction();
        transaction.save();
        testEndpoints.payBoleto(transaction);

        PayableQueriableFields paid = new PayableQueriableFields();
        paid.statusEquals(Payable.Status.PAID);
        Collection<Payable> foundPayables = new Payable().findCollection(10, 0, paid);
        Assert.assertEquals(1, foundPayables.size());

        PayableQueriableFields waitingFunds = new PayableQueriableFields();
        waitingFunds.statusNotEquals(Payable.Status.PAID);
        foundPayables = new Payable().findCollection(10, 0, waitingFunds);
        Assert.assertEquals(0, foundPayables.size());
    }

}