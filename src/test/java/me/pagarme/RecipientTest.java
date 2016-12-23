package me.pagarme;


import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;

import me.pagar.model.PagarMeException;
import me.pagar.model.Recipient;
import me.pagarme.factory.BankAccountFactory;
import me.pagarme.factory.RecipientFactory;

public class RecipientTest extends BaseTest{

    public BankAccountFactory bankAccountFactory = new BankAccountFactory();
    public RecipientFactory recipientFactory = new RecipientFactory();
    
    public RecipientTest() {
        super.setUp();
    }
    
    @Test
    public void testCreateDateExistence() throws PagarMeException{
        Recipient recipient = recipientFactory.create();
        recipient.save();
        Assert.assertNotNull(recipient.getCreatedAt());
        Assert.assertNotNull(recipient.getUpdatedAt());
    }
    
    @Test
    public void testCreateRecipient() throws PagarMeException {

        int bankAccountId = bankAccountFactory.create().save().getId();
        Recipient recipient = recipientFactory.create();
        recipient.setBankAccountId(bankAccountId);

        recipient.save();
        Assert.assertEquals(recipient.getTransferInterval(), RecipientFactory.DEFAULT_TRANSFER_INTERVAL);
        Assert.assertEquals(recipient.isTransferEnabled(), RecipientFactory.DEFAULT_TRANSFER_ENABLED);
        Assert.assertEquals(recipient.getAutomaticAnticipationEnabled(), RecipientFactory.DEFAULT_AUTOMATIC_ANTICIPATION_ENABLED);

        int recipientBankAccountId = recipient.getBankAccount().getId();
        Assert.assertEquals(recipientBankAccountId, bankAccountId);
    }

    @Test
    public void testRecipientFind() throws PagarMeException {

        int bankAccountId = bankAccountFactory.create().save().getId();
        Recipient recipient = recipientFactory.create();
        recipient.setBankAccountId(bankAccountId);
        recipient.save();
        recipient.find(recipient.getId());

        Assert.assertEquals(recipient.getTransferInterval(), RecipientFactory.DEFAULT_TRANSFER_INTERVAL);
        Assert.assertEquals(recipient.isTransferEnabled(), RecipientFactory.DEFAULT_TRANSFER_ENABLED);

        int recipientBankAccountId = recipient.getBankAccount().getId();
        Assert.assertEquals(recipientBankAccountId, bankAccountId);

    }

    @Test
    public void testRecipientFindCollection() throws PagarMeException {

        int bankAccountId = bankAccountFactory.create().save().getId();
        Recipient newRecipient = recipientFactory.create();
        newRecipient.setBankAccountId(bankAccountId);
        newRecipient.save();
        Collection<Recipient> recipientCollection =  new Recipient().findCollection(1,0);

        Assert.assertEquals(1, recipientCollection.size());
        for (Recipient recipient : recipientCollection) {
            Assert.assertEquals(recipient.getTransferInterval(), RecipientFactory.DEFAULT_TRANSFER_INTERVAL);
            Assert.assertEquals(recipient.isTransferEnabled(), RecipientFactory.DEFAULT_TRANSFER_ENABLED);

            int recipientBankAccountId = recipient.getBankAccount().getId();
            Assert.assertEquals(recipientBankAccountId, bankAccountId);
        }

    }

}
