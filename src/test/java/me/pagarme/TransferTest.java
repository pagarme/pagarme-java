package me.pagarme;

import java.util.Collection;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import me.pagar.model.PagarMeException;
import me.pagar.model.Recipient;
import me.pagar.model.Transfer;
import me.pagarme.factory.RecipientFactory;
import me.pagarme.helper.BalanceHelpers;

public class TransferTest extends BaseTest {

    private Recipient newRecipient;
    private RecipientFactory recipientFactory = new RecipientFactory();

    @Before
    public void setUpEnvironment() throws PagarMeException{
        super.setUp();

        newRecipient = recipientFactory.create();
        newRecipient.save();
        BalanceHelpers.addAvailableBalance(newRecipient, Integer.MAX_VALUE);
    }

    @Test
    public void testTransferTransferToRecipient() throws PagarMeException{
        Transfer transfer = new Transfer(10000, newRecipient.getId());
        transfer.save();

        Assert.assertEquals(Transfer.Status.PENDING_TRANSFER, transfer.getStatus());
        Assert.assertEquals(Transfer.Type.TED, transfer.getType());
        Assert.assertEquals(newRecipient.getBankAccount().getId(), transfer.getBankAccount().getId());
        Assert.assertEquals(Integer.valueOf(10000), transfer.getAmount());
        Assert.assertNotNull(transfer.getFee());
        Assert.assertNotNull(transfer.getCreatedAt());
        Assert.assertNotNull(transfer.getFundingEstimatedDate());
    }

    @Test
    public void testTransferTransferToBankAccount() throws PagarMeException{
        Transfer transfer = new Transfer(10000, newRecipient.getId(), newRecipient.getBankAccount().getId());
        transfer.save();

        Assert.assertEquals(Transfer.Status.PENDING_TRANSFER, transfer.getStatus());
        Assert.assertEquals(Transfer.Type.TED, transfer.getType());
        Assert.assertEquals(newRecipient.getBankAccount().getId(), transfer.getBankAccount().getId());
        Assert.assertEquals(Integer.valueOf(10000), transfer.getAmount());
        Assert.assertNotNull(transfer.getFee());
        Assert.assertNotNull(transfer.getCreatedAt());
        Assert.assertNotNull(transfer.getFundingEstimatedDate());
    }

    @Test(expected=PagarMeException.class)
    public void testTransferTransferToBankAccountWithException() throws PagarMeException{
        Transfer transfer = new Transfer(10000, null, newRecipient.getBankAccount().getId());
        transfer.save();
    }

    @Test
    public void testTransferCancellation() throws PagarMeException{
        Transfer transfer = new Transfer(10000, newRecipient.getId());
        transfer.save();

        transfer.cancel();
        Assert.assertEquals(Transfer.Status.CANCELED, transfer.getStatus());
    }

    @Test
    public void testTransferFind() throws PagarMeException{
        Transfer transfer = new Transfer(10000, newRecipient.getId());
        transfer.save();

        Transfer foundTransfer = new Transfer().find(transfer.getId());
        Assert.assertEquals(transfer.getId(), foundTransfer.getId());
        Assert.assertEquals(transfer.getAmount(), foundTransfer.getAmount());
        Assert.assertEquals(transfer.getBankAccount().getId(), foundTransfer.getBankAccount().getId());
        Assert.assertEquals(transfer.getCreatedAt(), foundTransfer.getCreatedAt());
        Assert.assertEquals(transfer.getFee(), foundTransfer.getFee());
        Assert.assertEquals(transfer.getFundingEstimatedDate(), foundTransfer.getFundingEstimatedDate());
        Assert.assertEquals(transfer.getStatus(), foundTransfer.getStatus());
        Assert.assertEquals(transfer.getType(), foundTransfer.getType());
    }

    @Test
    public void testTransferFindCollection() throws PagarMeException{
        Transfer transfer1 = new Transfer(10000, newRecipient.getId());
        transfer1.save();
        Transfer transfer2 = new Transfer(10000, newRecipient.getId());
        transfer2.save();

        Collection<Transfer> foundTransfers = new Transfer().findCollection(10, 0);
        Assert.assertEquals(2, foundTransfers.size());
    }

    @Test
    public void testTransferWithRecipientId() throws PagarMeException{
        Transfer transfer = new Transfer();
        transfer.setAmount(1000);
        transfer.setRecipientId(newRecipient.getId());
        transfer.save();

        Assert.assertEquals(Transfer.Status.PENDING_TRANSFER, transfer.getStatus());
        Assert.assertEquals(Transfer.Type.TED, transfer.getType());
        Assert.assertEquals(newRecipient.getId(), transfer.getRecipientId());
        Assert.assertEquals(Integer.valueOf(1000), transfer.getAmount());
        Assert.assertNotNull(transfer.getFee());
        Assert.assertNotNull(transfer.getCreatedAt());
        Assert.assertNotNull(transfer.getFundingEstimatedDate());

    }
}
