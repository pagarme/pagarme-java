package me.pagarme;

import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;

import me.pagar.model.BankAccount;
import me.pagarme.factory.BankAccountFactory;

public class BankAccountTest extends BaseTest{

    public BankAccountFactory bankAccountFactory = new BankAccountFactory();
    
    public BankAccountTest() {
        super.setUp();
    }
    
    @Test
    public void testCreateBackAccount() {

        try {
            
            BankAccount bankAccount = bankAccountFactory.create();
            BankAccount savedBankAccount = bankAccount.save();
            
            Assert.assertNotNull(savedBankAccount.getId());
            Assert.assertEquals(savedBankAccount.getAgencia(), BankAccountFactory.DEFAULT_AGENCIA);
            Assert.assertEquals(savedBankAccount.getAgenciaDv(), BankAccountFactory.DEFAULT_AGENCIA_DV);
            Assert.assertEquals(savedBankAccount.getConta(), BankAccountFactory.DEFAULT_CONTA);
            Assert.assertEquals(savedBankAccount.getContaDv(), BankAccountFactory.DEFAULT_CONTA_DV);
            Assert.assertEquals(savedBankAccount.getBankCode(), BankAccountFactory.DEFAULT_BANK_CODE);
            Assert.assertEquals(savedBankAccount.getDocumentNumber(), BankAccountFactory.DEFAULT_DOCUMENT_NUMBER);
            Assert.assertEquals(savedBankAccount.getLegalName(), BankAccountFactory.DEFAULT_LEGAL_NAME);
        } catch (Exception exception) {
            throw new UnsupportedOperationException(exception);
        }
    }

    @Test
    public void testFindBankAccountById() {

        try {

            BankAccount bankAccount = bankAccountFactory.create();
            BankAccount savedBankAccount = bankAccount.save();
            BankAccount foundBanckAccount = new BankAccount().find(bankAccount.getId());
            
            Assert.assertNotNull(foundBanckAccount.getId());
            Assert.assertEquals(savedBankAccount.getId(), foundBanckAccount.getId());
            Assert.assertEquals(savedBankAccount.getAgencia(), BankAccountFactory.DEFAULT_AGENCIA);
            Assert.assertEquals(savedBankAccount.getAgenciaDv(), BankAccountFactory.DEFAULT_AGENCIA_DV);
            Assert.assertEquals(savedBankAccount.getConta(), BankAccountFactory.DEFAULT_CONTA);
            Assert.assertEquals(savedBankAccount.getContaDv(), BankAccountFactory.DEFAULT_CONTA_DV);
            Assert.assertEquals(savedBankAccount.getBankCode(), BankAccountFactory.DEFAULT_BANK_CODE);
            Assert.assertEquals(savedBankAccount.getDocumentNumber(), BankAccountFactory.DEFAULT_DOCUMENT_NUMBER);
            Assert.assertEquals(savedBankAccount.getLegalName(), BankAccountFactory.DEFAULT_LEGAL_NAME);
            
        } catch (Exception exception) {
            throw new UnsupportedOperationException(exception);
        }
    }

    @Test
    public void testFindBankAccountCollection() {

        try {

            BankAccount bankAccount = bankAccountFactory.create();
            bankAccount.save();
            BankAccount otherBankAccount = bankAccountFactory.create();
            otherBankAccount.save();
            
            Collection<BankAccount> currentBanckAccount = bankAccount.findCollection(2,0);

            Assert.assertEquals(currentBanckAccount.size(), 2);

            for (BankAccount bankAccountVar : currentBanckAccount) {
                Assert.assertNotNull(bankAccountVar.getId());
                Assert.assertEquals(bankAccountVar.getAgencia(), BankAccountFactory.DEFAULT_AGENCIA);
                Assert.assertEquals(bankAccountVar.getAgenciaDv(), BankAccountFactory.DEFAULT_AGENCIA_DV);
                Assert.assertEquals(bankAccountVar.getConta(), BankAccountFactory.DEFAULT_CONTA);
                Assert.assertEquals(bankAccountVar.getContaDv(), BankAccountFactory.DEFAULT_CONTA_DV);
                Assert.assertEquals(bankAccountVar.getBankCode(), BankAccountFactory.DEFAULT_BANK_CODE);
                Assert.assertEquals(bankAccountVar.getDocumentNumber(), BankAccountFactory.DEFAULT_DOCUMENT_NUMBER);
                Assert.assertEquals(bankAccountVar.getLegalName(), BankAccountFactory.DEFAULT_LEGAL_NAME);
            }
        } catch (Exception exception) {
            throw new UnsupportedOperationException(exception);
        }
    }

}