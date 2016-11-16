package me.pagarme.factory;

import me.pagar.model.BankAccount;
import me.pagar.model.Recipient;
import me.pagar.model.Recipient.TransferInterval;

public class RecipientFactory {

    public static final Integer DEFAULT_TRANSFER_DAY = 1;
    public static final Boolean DEFAULT_TRANSFER_ENABLED = true;
    public static final TransferInterval DEFAULT_TRANSFER_INTERVAL = TransferInterval.WEEKLY;
    private BankAccountFactory bankAccountFactory = new BankAccountFactory();
    
    public Recipient create(){
        BankAccount bankAccount = bankAccountFactory.create();
        Recipient recipient = new Recipient();
        recipient.setBankAccount(bankAccount);
        recipient.setTransferDay(DEFAULT_TRANSFER_DAY);
        recipient.setTransferEnabled(DEFAULT_TRANSFER_ENABLED);
        recipient.setTransferInterval(TransferInterval.WEEKLY);
        return recipient;
    }
}
