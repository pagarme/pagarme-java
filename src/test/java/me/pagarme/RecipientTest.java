package me.pagarme;


import java.util.Collection;
import me.pagar.RecipientStatus;
import me.pagar.AutoAnticipationType;

import me.pagar.model.KYCLink;
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
        Assert.assertEquals(recipient.getStatus(), RecipientFactory.DEFAULT_STATUS);
        Assert.assertEquals(recipient.getAutomaticAnticipationType(), RecipientFactory.DEFAULT_AUTOANTICIPATIONTYPE);
        Assert.assertEquals(recipient.getAutomaticAnticipationDays(), RecipientFactory.DEFAULT_AUTOANTICIPATIONDAYS);
        Assert.assertEquals(recipient.getAutomaticAnticipationDelay(), RecipientFactory.DEFAULT_AUTOANTICIPATIONDELAY);

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
        Assert.assertEquals(recipient.getStatus(), RecipientFactory.DEFAULT_STATUS);

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
            Assert.assertEquals(recipient.getStatus(), RecipientFactory.DEFAULT_STATUS);

            int recipientBankAccountId = recipient.getBankAccount().getId();
            Assert.assertEquals(recipientBankAccountId, bankAccountId);
        }

    }
    
    @Test
    public void testChangeRecipientStatus() throws PagarMeException {

        int bankAccountId = bankAccountFactory.create().save().getId();
        Recipient recipient = recipientFactory.create();
        recipient.setBankAccountId(bankAccountId);

        recipient.save();
        recipient.setStatus(RecipientStatus.PROCESSING);
        recipient = recipient.save(); 
        Assert.assertEquals(recipient.getStatus(), RecipientStatus.PROCESSING);
        recipient.setStatus(RecipientStatus.REGISTRATION);
        recipient = recipient.save(); 
        Assert.assertEquals(recipient.getStatus(), RecipientStatus.REGISTRATION);
        recipient.setStatus(RecipientStatus.AFFILIATION);
        recipient = recipient.save(); 
        Assert.assertEquals(recipient.getStatus(), RecipientStatus.AFFILIATION);
        recipient.setStatus(RecipientStatus.ACTIVE);
        recipient = recipient.save(); 
        Assert.assertEquals(recipient.getStatus(), RecipientStatus.ACTIVE);
        recipient.setStatus(RecipientStatus.REFUSED);
        recipient = recipient.save(); 
        Assert.assertEquals(recipient.getStatus(), RecipientStatus.REFUSED);
        recipient.setStatus(RecipientStatus.SUSPENDED);
        recipient = recipient.save(); 
        Assert.assertEquals(recipient.getStatus(), RecipientStatus.SUSPENDED);
        recipient.setStatus(RecipientStatus.BLOCKED);
        recipient = recipient.save(); 
        Assert.assertEquals(recipient.getStatus(), RecipientStatus.BLOCKED);
        recipient.setStatus(RecipientStatus.INACTIVE);
        recipient = recipient.save(); 
        Assert.assertEquals(recipient.getStatus(), RecipientStatus.INACTIVE);

    }
        
    @Test
    public void testChangeRecipientAutomaticAnticipationType() throws PagarMeException {

        int bankAccountId = bankAccountFactory.create().save().getId();
        Recipient recipient = recipientFactory.create();
        recipient.setBankAccountId(bankAccountId);

        recipient.save();
        recipient.setAutomaticAnticipationType(AutoAnticipationType.TENTWENTYFIVE);
        recipient = recipient.save(); 
        Assert.assertEquals(recipient.getAutomaticAnticipationType(), AutoAnticipationType.TENTWENTYFIVE);
        recipient.setAutomaticAnticipationType(AutoAnticipationType.FULL);
        recipient = recipient.save(); 
        Assert.assertEquals(recipient.getAutomaticAnticipationType(), AutoAnticipationType.FULL);
    }

    @Test
    public void testGenerateKYCLink() throws PagarMeException {
        Recipient recipient = recipientFactory.create();
        recipient.save();
        KYCLink kycLink = recipient.generateKYCLink();

        Assert.assertNotNull(kycLink.getBase64());
        Assert.assertNotNull(kycLink.getUrl());
        Assert.assertNotNull(kycLink.getExpirationDate());
    }
}
