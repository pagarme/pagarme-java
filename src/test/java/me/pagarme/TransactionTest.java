package me.pagarme;

import me.pagar.model.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class TransactionTest extends BaseTest {

    private Recipient recipient = new Recipient();
    private SplitRule splitRule = new SplitRule();
    private BankAccount bankAccount;

    private static Integer AMOUNT = 100;
    private static Integer PAID_AMOUNT_PARTIAL = 50;

    @Before
    public void setUp() {
        super.setUp();
        transaction = new Transaction();
    }

    @Test
    public void testCreateAndCaptureTransactionWithOfflineDebitCard() throws Throwable {

        transaction = this.transactionDebitCardCommon("offline");
        transaction.setCapture(true);
        transaction.save();

        Assert.assertEquals(transaction.getPaymentMethod(), Transaction.PaymentMethod.DEBIT_CARD);
        Assert.assertEquals(transaction.getStatus(), Transaction.Status.PAID);
    }

    @Test
    public void testCreateAndAuthorizedTransactionWithOfflineDebitCard() throws Throwable {

        transaction = this.transactionDebitCardCommon("offline");
        transaction.setCapture(false);
        transaction.save();

        Assert.assertEquals(transaction.getPaymentMethod(), Transaction.PaymentMethod.DEBIT_CARD);
        Assert.assertEquals(transaction.getStatus(), Transaction.Status.AUTHORIZED);
    }

    @Test
    public void testCreateAndCaptureTransactionWithOnlineDebitCard() throws Throwable {

        transaction = this.transactionDebitCardCommon("online");
        transaction.setCapture(true);
        transaction.save();

        Assert.assertEquals(transaction.getPaymentMethod(), Transaction.PaymentMethod.DEBIT_CARD);
        Assert.assertEquals(transaction.getStatus(), Transaction.Status.PAID);
    }

    @Test
    public void testCreateAndAuthorizedTransactionWithOnlineDebitCard() throws Throwable {

        transaction = this.transactionDebitCardCommon("online");
        transaction.setCapture(false);
        transaction.save();

        Assert.assertEquals(transaction.getPaymentMethod(), Transaction.PaymentMethod.DEBIT_CARD);
        Assert.assertEquals(transaction.getStatus(), Transaction.Status.AUTHORIZED);
    }

    @Test
    public void testCreateAndCaptureTransactionWithSoftDescriptor() throws Throwable {

        transaction = this.transactionCreditCardCommon();
        transaction.setSoftDescriptor("API Test");
        transaction.save();

        Assert.assertEquals(transaction.getPaymentMethod(), Transaction.PaymentMethod.CREDIT_CARD);
        Assert.assertEquals(transaction.getStatus(), Transaction.Status.PAID);
        Assert.assertEquals(transaction.getSoftDescriptor(), "API Test");
    }

    @Test
    public void testCreateAndCaptureTransactionWithCreditCardWithoutGoingThroughFraud() throws Throwable {

        transaction = this.transactionCreditCardCommon();
        transaction.setCapture(true);
        transaction.save();

        Assert.assertEquals(transaction.getPaymentMethod(), Transaction.PaymentMethod.CREDIT_CARD);
        Assert.assertEquals(transaction.getStatus(), Transaction.Status.PAID);
    }

    @Test
    public void testCreateAndCaptureTransactionMetaData() throws Throwable {

        transaction = this.transactionCreditCardCommon();
        transaction.setCapture(true);

        Map<String, Object> metadata =  new HashMap<String, Object>();
        metadata.put("metadata1", "value1");
        metadata.put("metadata2", "value2");

        transaction.setMetadata(metadata);
        transaction.save();

        Assert.assertEquals(transaction.getPaymentMethod(), Transaction.PaymentMethod.CREDIT_CARD);
        Assert.assertEquals(transaction.getStatus(), Transaction.Status.PAID);
    }
    
    @Test
    public void testCreateAndCaptureTransactionAntifraudMetaData() throws Throwable {

        transaction = this.transactionCreditCardCommon();
        transaction.setCapture(true);

        Map<String, Object> antifraudMetadata =  new HashMap<String, Object>();
        antifraudMetadata.put("metadata1", "value1");
        antifraudMetadata.put("metadata2", "value2");

        transaction.setAntifraudMetadata(antifraudMetadata);
        transaction.save();

        Assert.assertEquals(transaction.getPaymentMethod(), Transaction.PaymentMethod.CREDIT_CARD);
        Assert.assertEquals(transaction.getStatus(), Transaction.Status.PAID);
    }

    @Test
    public void testCreateAndCaptureTransactionMetaDataInCapture() throws Throwable {

        transaction = this.transactionCreditCardCommon();
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

        transaction = this.transactionCreditCardCommon();
        transaction.setCapture(false);
        transaction.save();

        Assert.assertEquals(transaction.getPaymentMethod(), Transaction.PaymentMethod.CREDIT_CARD);
        Assert.assertEquals(transaction.getStatus(), Transaction.Status.AUTHORIZED);
    }

    @Test
    public void testTransactionCreatePostbackUrl() throws Throwable {

        transaction = this.transactionCreditCardCommon();
        transaction.setPostbackUrl("http://pagar.me");
        transaction.save();

        Assert.assertEquals(transaction.getStatus(), Transaction.Status.PROCESSING);
    }

    @Test
    public void testTransactionAuthAndCaptureCaptureTotalValue() throws Throwable {

        transaction = this.transactionCreditCardCommon();
        transaction.setCapture(false);
        transaction.save();

        Assert.assertEquals(transaction.getStatus(), Transaction.Status.AUTHORIZED);

        transaction.capture(AMOUNT);

        Assert.assertEquals(transaction.getStatus(), Transaction.Status.PAID);
    }

    @Test
    public void testTransactionCanBeMadeString() throws Throwable {

        transaction = this.transactionCreditCardCommon();

        transaction.toString();
    }

    @Test
    public void testTransactionCanBeMadeJSON() throws Throwable {

        transaction = this.transactionCreditCardCommon();
        transaction.setCapture(false);
        transaction.save();

        Assert.assertEquals(transaction.getStatus(), Transaction.Status.AUTHORIZED);

        transaction.capture(50);
        transaction.toJson();
    }

    @Test
    public void testTransactionAuthAndCaptureCapturePartialValue() throws Throwable {

        transaction = this.transactionCreditCardCommon();
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

        transaction = this.transactionCreditCardCommon();
        transaction.setCapture(true);
        transaction.save();

        transaction.refund(50);

        Assert.assertEquals(transaction.getStatus(), Transaction.Status.PAID);
        Assert.assertEquals(transaction.getPaidAmount(), AMOUNT);
        Assert.assertEquals(transaction.getRefundedAmount(), PAID_AMOUNT_PARTIAL);
        Assert.assertEquals(transaction.getAuthorizedAmount(), AMOUNT);
    }

    @Test
    public void testTransactionAuthAndCaptureRefoundTotalValue() throws Throwable {

        transaction = this.transactionCreditCardCommon();
        transaction.setCapture(true);
        transaction.save();

        transaction.refund(AMOUNT);

        Assert.assertEquals(transaction.getStatus(), Transaction.Status.REFUNDED);
        Assert.assertEquals(transaction.getRefundedAmount(), AMOUNT);
    }

    @Test
    public void testCreateAndAuthorizedTransactionWithBoleto() throws Throwable {

        transaction = this.transactionBoletoCommon();
        transaction.save();

        Assert.assertEquals(transaction.getStatus(), Transaction.Status.WAITING_PAYMENT);
        Assert.assertEquals(transaction.getPaymentMethod(), Transaction.PaymentMethod.BOLETO);
    }

    @Test
    public void testCreateTransactionWithBoleto() throws Throwable {

        transaction = this.transactionBoletoCommon();
        transaction.save();

        Assert.assertEquals(transaction.getStatus(), Transaction.Status.WAITING_PAYMENT);
        Assert.assertEquals(transaction.getPaymentMethod(), Transaction.PaymentMethod.BOLETO);
        Assert.assertEquals(transaction.getBoletoUrl(), "https://pagar.me");
        Assert.assertNotNull(transaction.getBoletoBarcode());
    }

    @Test
    public void testFindTransactionById() throws Throwable {

        transaction = this.transactionCreditCardCommon();
        transaction.save();

        Integer transactionId = transaction.getId();

        transaction = transaction.find(transactionId);
        Assert.assertEquals(transaction.getId(), transactionId);
    }

    @Test
    public void testCreatingCustomerTransactionThroughTheTransaction() throws Throwable {

        transaction = this.transactionCreditCardCommon();
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

//    @Test
//    public void testSplitTransaction() throws Throwable {
//
//        transaction = this.transactionCreditCardCommon();
//        transaction.setCapture(true);
//
//        Collection<SplitRule> splitRules = new ArrayList<SplitRule>();
//
//        splitRule.setRecipientId(this.getRecipientId(false));
//        splitRule.setPercentage(50);
//        splitRule.setLiable(true);
//        splitRule.setChargeProcessingFee(true);
//
//        splitRules.add(splitRule);
//
//        splitRule.setRecipientId(this.getRecipientId(true));
//        splitRule.setPercentage(50);
//        splitRule.setLiable(true);
//        splitRule.setChargeProcessingFee(true);
//
//        splitRules.add(splitRule);
//        transaction.setSplitRules(splitRules);
//        transaction.save();
//    }
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