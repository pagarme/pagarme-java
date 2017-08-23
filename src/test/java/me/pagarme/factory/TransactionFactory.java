package me.pagarme.factory;

import org.joda.time.DateTime;

import me.pagar.model.Card;

import me.pagar.model.Transaction;

public class TransactionFactory {

    /**
     * @param pinMode can be online or offline. With online option, the bank will check the card, and with offline the pin is used to check the card. 
     * @return the transaction
     */
	private CardFactory cardFactory = new CardFactory();

    public Transaction createCreditCardOfflineTransaction() {

        Card card = cardFactory.create();

        Transaction transaction = new Transaction();

        transaction.setAmount(100);
        transaction.setPaymentMethod(Transaction.PaymentMethod.DEBIT_CARD);
        transaction.setCaptureMethod(Transaction.CaptureMethod.EMV);
        transaction.setCardHolderName(card.getHolderName());
        transaction.setCardExpirationDate(card.getExpiresAt());
        transaction.setCardNumber(card.getNumber());
        transaction.setInstallments(1);
        transaction.setCardEmvData("0");
        transaction.setCardTrack1("0");
        transaction.setCardTrack2("0");
        transaction.setCardTrack3("0");
        transaction.setCardPinMode("offline");

        return transaction;
    }

    public Transaction createCreditCardOnlineTransaction(){

        Card card = cardFactory.create();

        Transaction transaction = new Transaction();

        transaction.setAmount(100);
        transaction.setPaymentMethod(Transaction.PaymentMethod.DEBIT_CARD);
        transaction.setCaptureMethod(Transaction.CaptureMethod.EMV);
        transaction.setCardHolderName(card.getHolderName());
        transaction.setCardExpirationDate(card.getExpiresAt());
        transaction.setCardNumber(card.getNumber());
        transaction.setInstallments(1);
        transaction.setCardEmvData("0");
        transaction.setCardTrack1("0");
        transaction.setCardTrack2("0");
        transaction.setCardTrack3("0");
        transaction.setCardPinMode("online");

        transaction.setCardPin("0");
        transaction.setCardPinKek("0");

        return transaction;
    }

    public Transaction createCreditCardTransactionWithoutPinMode() {

        Card card = cardFactory.create();

        Transaction transaction = new Transaction();

        transaction.setAmount(100);
        transaction.setPaymentMethod(Transaction.PaymentMethod.CREDIT_CARD);
        transaction.setCardHolderName(card.getHolderName());
        transaction.setCardExpirationDate(card.getExpiresAt());
        transaction.setCardCvv(Integer.toString(card.getCvv()));
        transaction.setCardNumber(card.getNumber());
        transaction.setInstallments(1);

        return transaction;
    }

    public Transaction createBoletoTransaction() {

        Transaction transaction = new Transaction();

        transaction.setBoletoExpirationDate(DateTime.now().plusDays(4));
        transaction.setAmount(100);
        transaction.setPaymentMethod(Transaction.PaymentMethod.BOLETO);
        return transaction;
    }
}
