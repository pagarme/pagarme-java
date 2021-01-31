package me.pagarme;

import java.util.Collection;
import java.util.Iterator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import me.pagar.model.BalanceOperation;
import me.pagar.model.BalanceOperation.Type;
import me.pagar.model.BulkAnticipation;
import me.pagar.model.PagarMeException;
import me.pagar.model.Recipient;
import me.pagar.model.Transaction;
import me.pagar.model.Transfer;
import me.pagarme.factory.RecipientFactory;
import me.pagarme.factory.TransactionFactory;
import me.pagarme.helper.BalanceHelpers;
import me.pagarme.helper.BulkAnticipationHelpers;
import me.pagarme.helper.TestEndpoints;

public class BalanceOperationsTest extends BaseTest {

    private TransactionFactory transactionFactory = new TransactionFactory();
    private TestEndpoints testEndpoints = new TestEndpoints();
    private RecipientFactory recipientFactory = new RecipientFactory();

    @Before
    public void setUp() {
        super.setUp();
    }

    @Test
    public void testRetrieveBalanceOperationHistory() throws PagarMeException{
        Transaction transaction = transactionFactory.createBoletoTransaction();
        transaction.save();
        testEndpoints.payBoleto(transaction);

        Collection<BalanceOperation> operations = new BalanceOperation().findCollection(10, 0);
        Assert.assertEquals(1, operations.size());
        BalanceOperation operation = operations.iterator().next();
        Assert.assertEquals(BalanceOperation.Status.AVAILABLE, operation.getStatus());
        Assert.assertEquals(transaction.getAmount(), operation.getAmount());
        Assert.assertNull(operation.getBalanceOldAmount());
        Assert.assertEquals(BalanceOperation.Type.PAYABLE, operation.getType());
        Assert.assertEquals(Integer.valueOf(0), operation.getBalanceAmount());
    }

    @Test
    public void testRetrieveOneTransactionBalanceOperation() throws PagarMeException{
        Transaction transaction = transactionFactory.createBoletoTransaction();
        transaction.save();
        testEndpoints.payBoleto(transaction);

        Collection<BalanceOperation> operations = new BalanceOperation().findCollection(10, 0);
        Assert.assertEquals(1, operations.size());

        BalanceOperation balanceOperation = operations.iterator().next();
        String balanceId = balanceOperation.getId();

        BalanceOperation foundBalanceOperation = new BalanceOperation().find(balanceId);
        Assert.assertEquals(foundBalanceOperation.getId(), balanceOperation.getId());
        Assert.assertEquals(foundBalanceOperation.getAmount(), balanceOperation.getAmount());
        Assert.assertEquals(foundBalanceOperation.getBalanceAmount(), balanceOperation.getBalanceAmount());
        Assert.assertEquals(foundBalanceOperation.getCreatedAt(), balanceOperation.getCreatedAt());
        Assert.assertEquals(foundBalanceOperation.getFee(), balanceOperation.getFee());
        Assert.assertEquals(foundBalanceOperation.getStatus(), balanceOperation.getStatus());
        Assert.assertEquals(foundBalanceOperation.getType(), balanceOperation.getType());
        Assert.assertEquals(foundBalanceOperation.getMovementPayable().getId(), balanceOperation.getMovementPayable().getId());
    }

    @Test
    public void testTransferBalanceOperation() throws PagarMeException{
        Recipient newRecipient = recipientFactory.create();
        newRecipient.save();

        BalanceHelpers.addAvailableBalance(newRecipient, Integer.MAX_VALUE);
        Transfer transfer = new Transfer(12345, newRecipient.getId());
        transfer.save();

        Collection<BalanceOperation> foundBalanceOperations = new BalanceOperation().findCollection(10, 1);
        Assert.assertEquals(2, foundBalanceOperations.size());

        Iterator<BalanceOperation> balanceOperationIterator = foundBalanceOperations.iterator();
        BalanceOperation balanceOperation = balanceOperationIterator.next();
        while(!balanceOperation.getType().equals(Type.TRANSFER)){
            balanceOperation = balanceOperationIterator.next();
        }
        Assert.assertNotNull(balanceOperation.getId());
        Assert.assertEquals(Type.TRANSFER, balanceOperation.getType());
        Assert.assertEquals((Integer)(-12345), balanceOperation.getAmount());
        Assert.assertNotNull(balanceOperation.getFee());
        Assert.assertNotNull(balanceOperation.getBalanceAmount());
        Assert.assertNotNull(balanceOperation.getStatus());
        Assert.assertNotNull(balanceOperation.getMovementTranfer());
    }
}
