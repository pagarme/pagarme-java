package me.pagarme.transaction;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import me.pagarme.AntifraudMetadataPojo;
import me.pagarme.BaseTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.JsonObject;

import me.pagar.model.Address;
import me.pagar.model.BankAccount;
import me.pagar.model.Customer;
import me.pagar.model.PagarMeException;
import me.pagar.model.Phone;
import me.pagar.model.Recipient;
import me.pagar.model.SplitRule;
import me.pagar.model.Transaction;
import me.pagar.model.Transaction.CaptureMethod;
import me.pagar.util.JSONUtils;
import me.pagarme.factory.RecipientFactory;
import me.pagarme.factory.CustomerFactory;
import me.pagarme.factory.TransactionFactory;
import me.pagarme.helper.TestEndpoints;
import org.joda.time.DateTime;

public class TransactionTest extends BaseTest {

    private RecipientFactory recipientFactory = new RecipientFactory();
    private CustomerFactory customerFactory = new CustomerFactory();
    private TransactionFactory transactionFactory = new TransactionFactory();
    private TestEndpoints testEndpoints = new TestEndpoints();
    private static Integer AMOUNT = 100;
    private static Integer PAID_AMOUNT_PARTIAL = 50;

    @Before
    public void setUp() {
        super.setUp();
        transaction = new Transaction();
    }

    @Test
    public void testCreatedDateExistence() throws PagarMeException{

        transaction = transactionFactory.createCreditCardTransactionWithoutPinMode();
        transaction.save();
        
        Assert.assertNotNull(transaction.getCreatedAt());
        Assert.assertNotNull(transaction.getUpdatedAt());
    }

    @Test
    public void testCreateAndCaptureTransactionWithSoftDescriptor() throws Throwable {

        transaction = transactionFactory.createCreditCardTransactionWithoutPinMode();
        transaction.setSoftDescriptor("API Test");
        transaction.save();

        Assert.assertEquals(transaction.getPaymentMethod(), Transaction.PaymentMethod.CREDIT_CARD);
        Assert.assertEquals(transaction.getStatus(), Transaction.Status.PAID);
        Assert.assertEquals(transaction.getSoftDescriptor(), "API Test");
    }

    @Test
    public void testCreateAndCaptureTransactionWithCreditCardWithoutGoingThroughFraud() throws Throwable {

        transaction = transactionFactory.createCreditCardTransactionWithoutPinMode();
        transaction.setCapture(true);
        transaction.save();

        Assert.assertEquals(transaction.getPaymentMethod(), Transaction.PaymentMethod.CREDIT_CARD);
        Assert.assertEquals(transaction.getStatus(), Transaction.Status.PAID);
    }

    @Test
    public void testCreateAndCaptureTransactionMetaData() throws Throwable {

        transaction = transactionFactory.createCreditCardTransactionWithoutPinMode();
        transaction.setCapture(true);

        Map<String, Object> metadata =  new HashMap<String, Object>();
        metadata.put("metadata1", "value1");
        metadata.put("metadata2", "value2");

        transaction.setMetadata(metadata);
        transaction.save();
        
        Transaction foundTransaction = new Transaction().find(transaction.getId());

        Assert.assertEquals(foundTransaction.getPaymentMethod(), Transaction.PaymentMethod.CREDIT_CARD);
        Assert.assertEquals(foundTransaction.getStatus(), Transaction.Status.PAID);
        Assert.assertEquals(foundTransaction.getMetadata().get("metadata1"), "value1");
        Assert.assertEquals(foundTransaction.getMetadata().get("metadata2"), "value2");

    }

    @SuppressWarnings("unchecked")
    @Test
    public void testCreateAndCaptureTransactionAntifraudMetaDataMap() throws Throwable {

        transaction = transactionFactory.createCreditCardTransactionWithoutPinMode();
        transaction.setCapture(true);

        Map<String, Object> antifraudMetadata =  new HashMap<String, Object>();
        antifraudMetadata.put("antifraudMetadata1", "value1");
        antifraudMetadata.put("antifraudMetadata2", "value2");

        transaction.setAntifraudMetadata(antifraudMetadata);
        transaction.save();

        JsonObject json = JSONUtils.treeToJson(transaction.getAntifraudMetadata());
        Map<String, Object> antifraudMetadataRes = JSONUtils.getAsObject(json, HashMap.class);

        Assert.assertEquals(antifraudMetadataRes.get("antifraudMetadata1"), "value1");
        Assert.assertEquals(antifraudMetadataRes.get("antifraudMetadata2"), "value2");
    }

    @Test
    public void testCreateAndCaptureTransactionAntifraudMetaDataPojo() throws Throwable {

        transaction = transactionFactory.createCreditCardTransactionWithoutPinMode();
        transaction.setCapture(true);

        AntifraudMetadataPojo antifraudMetadata = new AntifraudMetadataPojo();
        antifraudMetadata.setName("Philip J. Fry");

        transaction.setAntifraudMetadata(antifraudMetadata);
        transaction.save();

        JsonObject json = JSONUtils.treeToJson(transaction.getAntifraudMetadata());
        AntifraudMetadataPojo antifraudMetadataRes = JSONUtils.getAsObject(json, AntifraudMetadataPojo.class);

        Assert.assertEquals(antifraudMetadataRes.getName(), "Philip J. Fry");
    }

    @Test
    public void testCreateAndCaptureTransactionMetaDataInCapture() throws Throwable {

        transaction = transactionFactory.createCreditCardTransactionWithoutPinMode();
        transaction.setCapture(false);
        transaction.save();

        Map<String, Object> metadata =  new HashMap<String, Object>();
        metadata.put("metadata1", "value1");
        metadata.put("metadata2", "value2");
        transaction.setMetadata(metadata);

        transaction.capture(AMOUNT);

        Assert.assertEquals(transaction.getPaymentMethod(), Transaction.PaymentMethod.CREDIT_CARD);
        Assert.assertEquals(transaction.getStatus(), Transaction.Status.PAID);
    }

    @Test
    public void testCreateAndAuthorizedTransactionWithCreditCardWithoutGoingThroughFraud() throws Throwable {

        transaction = transactionFactory.createCreditCardTransactionWithoutPinMode();
        transaction.setCapture(false);
        transaction.save();

        Assert.assertEquals(transaction.getPaymentMethod(), Transaction.PaymentMethod.CREDIT_CARD);
        Assert.assertEquals(transaction.getStatus(), Transaction.Status.AUTHORIZED);
    }

    @Test
    public void testTransactionCreatePostbackUrl() throws Throwable {

        transaction = transactionFactory.createCreditCardTransactionWithoutPinMode();
        transaction.setPostbackUrl("http://pagar.me");
        transaction.save();

        Assert.assertEquals(transaction.getStatus(), Transaction.Status.PROCESSING);
    }

    @Test
    public void testTransactionAuthAndCaptureCaptureTotalValue() throws Throwable {

        transaction = transactionFactory.createCreditCardTransactionWithoutPinMode();
        transaction.setCapture(false);
        transaction.save();

        Assert.assertEquals(transaction.getStatus(), Transaction.Status.AUTHORIZED);

        transaction.capture(AMOUNT);

        Assert.assertEquals(transaction.getStatus(), Transaction.Status.PAID);
    }

    @Test
    public void testTransactionCanBeMadeString() throws Throwable {

        transaction = transactionFactory.createCreditCardTransactionWithoutPinMode();

        transaction.toString();
    }

    @Test
    public void testTransactionCanBeMadeJSON() throws Throwable {

        transaction = transactionFactory.createCreditCardTransactionWithoutPinMode();
        transaction.setCapture(false);
        transaction.save();

        Assert.assertEquals(transaction.getStatus(), Transaction.Status.AUTHORIZED);

        transaction.capture(50);
        transaction.toJson();
    }

    @Test
    public void testTransactionAuthAndCaptureCapturePartialValue() throws Throwable {

        transaction = transactionFactory.createCreditCardTransactionWithoutPinMode();
        transaction.setCapture(false);
        transaction.save();

        Assert.assertEquals(transaction.getStatus(), Transaction.Status.AUTHORIZED);

        transaction.capture(50);

        Assert.assertEquals(transaction.getStatus(), Transaction.Status.PAID);
        Assert.assertEquals(transaction.getPaidAmount(), PAID_AMOUNT_PARTIAL);
        Assert.assertEquals(transaction.getAuthorizedAmount(), AMOUNT);
    }

    @Test
    public void testTransactionAuthAndCaptureRefoundPartialValue() throws Throwable {

        transaction = transactionFactory.createCreditCardTransactionWithoutPinMode();
        transaction.setCapture(true);
        transaction.save();

        transaction.refund(50);

        Assert.assertEquals(transaction.getStatus(), Transaction.Status.PAID);
        Assert.assertEquals(transaction.getPaidAmount(), AMOUNT);
        Assert.assertEquals(transaction.getRefundedAmount(), PAID_AMOUNT_PARTIAL);
        Assert.assertEquals(transaction.getAuthorizedAmount(), AMOUNT);
    }

    @Test
    public void testBoletoTransactionAuthAndCaptureRefund() throws Throwable {

        Transaction transaction = transactionFactory.createBoletoTransaction();
        transaction.setCapture(true);
        transaction.setAmount(10000);
        transaction.save();
        transaction = testEndpoints.payBoleto(transaction);

        Transaction transaction2 = transactionFactory.createBoletoTransaction();
        transaction2.setCapture(true);
        transaction2.setAmount(10000);
        transaction2.save();
        transaction2 = testEndpoints.payBoleto(transaction2);

        BankAccount bankAccount = (BankAccount)new BankAccount().findCollection(1, 0).toArray()[0];
        transaction.refund(bankAccount);

        Assert.assertEquals(Transaction.Status.PENDING_REFUND, transaction.getStatus());
    }

    @Test
    public void testTransactionAuthAndCaptureRefoundTotalValue() throws Throwable {

        transaction = transactionFactory.createCreditCardTransactionWithoutPinMode();
        transaction.setCapture(true);
        transaction.save();

        transaction.refund(AMOUNT);

        Assert.assertEquals(transaction.getStatus(), Transaction.Status.REFUNDED);
        Assert.assertEquals(transaction.getRefundedAmount(), AMOUNT);
    }

    @Test
    public void testCreateAndAuthorizedTransactionWithBoleto() throws Throwable {

        transaction = transactionFactory.createBoletoTransaction();
        transaction.save();

        Assert.assertEquals(transaction.getStatus(), Transaction.Status.WAITING_PAYMENT);
        Assert.assertEquals(transaction.getPaymentMethod(), Transaction.PaymentMethod.BOLETO);
    }

    @Test
    public void testCreateTransactionWithBoleto() throws Throwable {

        transaction = transactionFactory.createBoletoTransaction();
        transaction.save();

        Assert.assertEquals(transaction.getStatus(), Transaction.Status.WAITING_PAYMENT);
        Assert.assertEquals(transaction.getPaymentMethod(), Transaction.PaymentMethod.BOLETO);
        Assert.assertEquals(transaction.getBoletoUrl(), "https://pagar.me");
        Assert.assertNotNull(transaction.getBoletoBarcode());
    }

    @Test
    public void testBoletoExpirationDate() throws Throwable{
        transaction = transactionFactory.createBoletoTransaction();
        transaction.setBoletoExpirationDate(DateTime.now().plusDays(4));
        transaction.save();

        Transaction foundTransaction = new Transaction().find(transaction.getId());

        Assert.assertEquals(DateTime.now().plusDays(4).withTime(2, 0, 0, 0), foundTransaction.getBoletoExpirationDate());
    }

    @Test
    public void testFindTransactionById() throws Throwable {

        transaction = transactionFactory.createCreditCardTransactionWithoutPinMode();
        transaction.save();

        Integer transactionId = transaction.getId();

        transaction = transaction.find(transactionId);
        Assert.assertEquals(transaction.getId(), transactionId);
    }

    @Test
    public void testCreatingCustomerTransactionThroughTheTransaction() throws Throwable {

        transaction = transactionFactory.createCreditCardTransactionWithoutPinMode();
        transaction.setCapture(true);

        Customer customer = this.customerCommon();
        Address address = this.addressCommon();
        Phone phone = this.phoneCommon();

        customer.setAddress(address);
        customer.setPhone(phone);

        transaction.setCustomer(customer);
        transaction.save();

        Customer transactionCustomer = transaction.getCustomer();
        Assert.assertEquals(transactionCustomer.getName(), "lucas santos");
        Assert.assertEquals(transactionCustomer.getDocumentNumber(), "15317529506");
        Assert.assertEquals(transactionCustomer.getEmail(), "testelibjava@pagar.me");

        Address transactionAddress = transactionCustomer.getAddress();
        Assert.assertEquals(transactionAddress.getStreet(), "Rua Piraju");
        Assert.assertEquals(transactionAddress.getStreetNumber(), "218");
        Assert.assertEquals(transactionAddress.getComplementary(), "ao lado da consigáz");
        Assert.assertEquals(transactionAddress.getNeighborhood(), "Interlagos");

        Phone transactionPhone = transactionCustomer.getPhone();
        Assert.assertEquals(transactionPhone.getDdd(), "11");
        Assert.assertEquals(transactionPhone.getNumber(), "55284132");
    }
    
    @Test
    public void testCaptureFalseWithSplitTransaction() throws Throwable {

        transaction = transactionFactory.createCreditCardTransactionWithoutPinMode();
        transaction.setCapture(false);
        transaction.setAmount(10000);
        Customer customer = customerFactory.create();
        transaction.setCustomer(customer);
        
        transaction.save();
        
        Collection<SplitRule> splitRules = new ArrayList<SplitRule>();
        
        Recipient recipient1 = recipientFactory.create();
        recipient1.save();
        SplitRule splitRule = new SplitRule();
        splitRule.setRecipientId(recipient1.getId());
        splitRule.setPercentage(50);
        splitRule.setLiable(true);
        splitRule.setChargeProcessingFee(true);
        splitRules.add(splitRule);

        Recipient recipient2  = recipientFactory.create();
        SplitRule splitRule2 = new SplitRule();
        recipient2.save();
        splitRule2.setRecipientId(recipient2.getId());
        splitRule2.setPercentage(50);
        splitRule2.setLiable(true);
        splitRule2.setChargeProcessingFee(true);

        splitRules.add(splitRule2);
        
        Assert.assertEquals(transaction.getStatus(), Transaction.Status.AUTHORIZED);
        transaction.setSplitRules(splitRules);
        transaction.capture(transaction.getAmount());
        Transaction foundTransaction = new Transaction().find(transaction.getId());
        Collection<SplitRule> foundSplitRules = foundTransaction.getSplitRules();
        Assert.assertEquals(splitRules.size(), foundSplitRules.size());
        
    }

    @Test
    public void testSplitTransaction() throws Throwable {

        transaction = transactionFactory.createCreditCardTransactionWithoutPinMode();
        transaction.setCapture(true);
        transaction.setAmount(10000);

        Collection<SplitRule> splitRules = new ArrayList<SplitRule>();
        
        Recipient recipient1 = recipientFactory.create();
        recipient1.save();
        SplitRule splitRule = new SplitRule();
        splitRule.setRecipientId(recipient1.getId());
        splitRule.setPercentage(50);
        splitRule.setLiable(true);
        splitRule.setChargeProcessingFee(true);
        splitRules.add(splitRule);

        Recipient recipient2  = recipientFactory.create();
        SplitRule splitRule2 = new SplitRule();
        recipient2.save();
        splitRule2.setRecipientId(recipient2.getId());
        splitRule2.setPercentage(50);
        splitRule2.setLiable(true);
        splitRule2.setChargeProcessingFee(true);

        splitRules.add(splitRule2);
        transaction.setSplitRules(splitRules);
        transaction.save();
        
        Transaction foundTransaction = new Transaction().find(transaction.getId());
        Collection<SplitRule> foundSplitRules = foundTransaction.getSplitRules();
        Assert.assertEquals(splitRules.size(), foundSplitRules.size());
        
    }
//
//    private String getRecipientId(Boolean documentNumber) {
//
//        int bankAccountId = this.getBankAccountId(documentNumber);
//
//        recipient.setTransferInterval(Recipient.TransferInterval.WEEKLY);
//        recipient.setTransferDay(TRANSFER_DAY);
//        recipient.setTransferEnabled(TRANSFER_ENABLE);
//        recipient.setBankAccountId(bankAccountId);
//
//        try {
//            recipient.save();
//            return recipient.getId();
//
//        } catch (PagarMeException exception) {
//            throw new UnsupportedOperationException(exception);
//        }
//    }
//
//    /**
//     *
//     * @return
//     */
//    private Integer getBankAccountId(Boolean documentNumber) {
//
//        System.out.print("Hello " + documentNumber);
//
//        if (documentNumber == true) {
//            bankAccount = new BankAccount();
//            bankAccount.setAgencia(AGENCIA);
//            bankAccount.setAgenciaDv(AGENCIA_DV);
//            bankAccount.setConta(CONTA);
//            bankAccount.setContaDv(CONTA_DV);
//            bankAccount.setBankCode(BANK_CODE);
//            bankAccount.setLegalName(LEGAL_NAME);
//            bankAccount.setDocumentNumber(DOCUMENT_NUMBER);
//        } else {
//            bankAccount = new BankAccount();
//            System.out.print("Hello " + bankAccount.getConta());
//            bankAccount.setAgencia("0173");
//            bankAccount.setAgenciaDv("1");
//            bankAccount.setConta("506725");
//            bankAccount.setContaDv(CONTA_DV);
//            bankAccount.setBankCode(BANK_CODE);
//            bankAccount.setLegalName("Java Lib Bank Account Number 2");
//            bankAccount.setDocumentNumber("55278368128");
//        }
//
//        try {
//
//            bankAccount.save();
//            return bankAccount.getId();
//
//        } catch (PagarMeException exception) {
//            throw new UnsupportedOperationException(exception);
//        }
//    }
}
