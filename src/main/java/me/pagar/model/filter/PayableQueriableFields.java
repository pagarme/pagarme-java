package me.pagar.model.filter;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import me.pagar.format.CommonFormats;
import me.pagar.model.Payable;

public class PayableQueriableFields extends QueriableFieldsAbstract implements QueriableFields {

    private static final String ID = "id";
    private static final String CREATED_AT = "created_at";
    private static final String BANK_ACCOUNT_ID = "bank_account_id";
    private static final String AMOUNT = "amount";
    private static final String RECIPIENT_ID = "recipient_id";
    private static final String STATUS = "status";
    private static final String INSTALLMENT = "installment";
    private static final String TRANSACTION_ID = "transaction_id";
    private static final String SPLIT_RULE_ID = "split_rule_id";
    private static final String PAYMENT_DATE = "payment_date";
    private static final String TYPE = "type";

    public void idEquals(Integer id){
        super.setEquals(ID, id.toString());
    }

    public void bankAccountIdEquals(Integer bankAccountId){
        super.setEquals(BANK_ACCOUNT_ID, bankAccountId.toString());
    }

    public void recipientIdEquals(String recipientId){
        super.setEquals(RECIPIENT_ID, recipientId);
    }

    public void transactionIdEquals(Integer transactionId){
        super.setEquals(TRANSACTION_ID, transactionId.toString());
    }

    public void splitRuleIdEquals(String splitRuleId){
        super.setEquals(SPLIT_RULE_ID, splitRuleId);
    }

    public void installmentsEquals(Integer installments){
        super.setEquals(INSTALLMENT, installments.toString());
    }

    public void statusEquals(Payable.Status status){
        super.setEquals(STATUS, status.name().toLowerCase());
    }

    public void statusNotEquals(Payable.Status status){
        super.setNotEquals(STATUS, status.name().toLowerCase());
    }

    public void typeNotEquals(Payable.Type type){
        super.setNotEquals(TYPE, type.name().toLowerCase());
    }

    public void typeEquals(Payable.Type type){
        super.setEquals(TYPE, type.name().toLowerCase());
    }

    public void amount(Integer amount){
        super.setEquals(AMOUNT, amount.toString());
    }

    public void createdBefore(DateTime date){
        DateTimeFormatter format = DateTimeFormat.forPattern(CommonFormats.DATE_TIME);
        super.setLessThan(CREATED_AT, format.print(date));
    }

    public void createdAfter(DateTime date){
        DateTimeFormatter format = DateTimeFormat.forPattern(CommonFormats.DATE_TIME);
        super.setGreatedThan(CREATED_AT, format.print(date));
    }

    public void paymentBefore(DateTime date){
        DateTimeFormatter format = DateTimeFormat.forPattern(CommonFormats.DATE_TIME);
        super.setLessThan(PAYMENT_DATE, format.print(date));
    }

    public void paymentAfter(DateTime date){
        DateTimeFormatter format = DateTimeFormat.forPattern(CommonFormats.DATE_TIME);
        super.setGreatedThan(PAYMENT_DATE, format.print(date));
    }

    public String pagarmeRelatedModel() {
        return "payables";
    }

}
