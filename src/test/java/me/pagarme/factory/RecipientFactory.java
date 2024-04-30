package me.pagarme.factory;

import me.pagar.model.BankAccount;
import me.pagar.model.Recipient;
import me.pagar.RecipientStatus;
import me.pagar.model.Recipient.TransferInterval;
import me.pagar.AutoAnticipationType;
import me.pagar.model.RegisterInformation;

public class RecipientFactory {

    public static final Integer DEFAULT_TRANSFER_DAY = 1;
    public static final Boolean DEFAULT_TRANSFER_ENABLED = true;
    public static final Boolean DEFAULT_AUTOMATIC_ANTICIPATION_ENABLED = true;
    public static final TransferInterval DEFAULT_TRANSFER_INTERVAL = TransferInterval.WEEKLY;
    public static final RecipientStatus DEFAULT_STATUS = RecipientStatus.ACTIVE;
    public static final AutoAnticipationType DEFAULT_AUTOANTICIPATIONTYPE = AutoAnticipationType.FULL;
    public static final String DEFAULT_AUTOANTICIPATIONDAYS = "[1]";
    public static final Integer DEFAULT_AUTOANTICIPATIONDELAY = 30;
    private BankAccountFactory bankAccountFactory = new BankAccountFactory();
    private RegisterInformationFactory registerInformationFactory =  new RegisterInformationFactory();
    
    public Recipient create(){
        BankAccount bankAccount = bankAccountFactory.create();
        RegisterInformation registerInformation = registerInformationFactory.create();
        Recipient recipient = new Recipient();
        recipient.setBankAccount(bankAccount);
        recipient.setRegisterInformation(registerInformation);
        recipient.setTransferDay(DEFAULT_TRANSFER_DAY);
        recipient.setTransferEnabled(DEFAULT_TRANSFER_ENABLED);
        recipient.setTransferInterval(TransferInterval.WEEKLY);
        recipient.setAutomaticAnticipationEnabled(DEFAULT_AUTOMATIC_ANTICIPATION_ENABLED);
        recipient.setStatus(DEFAULT_STATUS);
        recipient.setAutomaticAnticipationType(DEFAULT_AUTOANTICIPATIONTYPE);
        recipient.setAutomaticAnticipationDays(DEFAULT_AUTOANTICIPATIONDAYS);
        recipient.setAutomaticAnticipationDelay(DEFAULT_AUTOANTICIPATIONDELAY);
        return recipient;
    }
}
