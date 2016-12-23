package me.pagar.model;

import java.util.Collection;
import java.util.Date;

import javax.ws.rs.HttpMethod;

import org.joda.time.DateTime;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import me.pagar.model.Transaction.PaymentMethod;
import me.pagar.model.filter.QueriableFields;
import me.pagar.util.JSONUtils;

public class Payable extends PagarMeModel<Integer> {

    @Expose(serialize = false)
    private Integer amount;

    @Expose(serialize = false)
    private Integer fee;

    @Expose(serialize = false)
    private Integer installment;

    @Expose(serialize = false)
    private Integer transactionId;

    @Expose(serialize = false)
    private String splitRuleId;

    @Expose(serialize = false)
    private DateTime paymentDate;

    @Expose(serialize = false)
    private Status status;

    @Expose(serialize = false)
    private Type type;

    @Expose
    private String recipientId;

    @Expose
    private String anticipationFee;

    @Expose
    private String bulkAnticipationId;

    @Expose
    private Date originalPaymentDate;

    @Expose
    private PaymentMethod paymentMethod;

    public Payable() {
        super();
    }

    public Payable find(Integer id) throws PagarMeException {

        final PagarMeRequest request = new PagarMeRequest(HttpMethod.GET, String.format("/%s/%s", getClassName(), id));

        final Payable other = JSONUtils.getAsObject((JsonObject) request.execute(), Payable.class);
        copy(other);
        flush();

        return other;
    }

    public Collection<Payable> findCollection(final Integer totalPerPage, Integer page) throws PagarMeException {
        JsonArray response = super.paginate(totalPerPage, page);
        return JSONUtils.getAsList(response, new TypeToken<Collection<Payable>>() {
        }.getType());
    }

    public Collection<Payable> findCollection(final Integer totalPerPage, Integer page, QueriableFields payableFilter) throws PagarMeException {
        JsonArray response = super.paginate(totalPerPage, page, payableFilter);
        return JSONUtils.getAsList(response, new TypeToken<Collection<Payable>>() {
        }.getType());
    }

    private void copy(Payable other) {
        super.copy(other);
        this.amount = other.amount;
        this.anticipationFee = other.anticipationFee;
        this.bulkAnticipationId = other.bulkAnticipationId;
        this.fee = other.fee;
        this.installment = other.installment;
        this.originalPaymentDate = other.originalPaymentDate;
        this.paymentDate = other.paymentDate;
        this.paymentMethod = other.paymentMethod;
        this.recipientId = other.recipientId;
        this.splitRuleId = other.splitRuleId;
        this.status = other.status;
        this.transactionId = other.transactionId;
        this.type = other.type;
    }

    @Override
    public void setId(Integer id) {
        throw new UnsupportedOperationException("Not allowed.");
    }

    public Integer getAmount() {
        return amount;
    }

    public Integer getFee() {
        return fee;
    }

    public Integer getInstallment() {
        return installment;
    }

    public Integer getTransactionId() {
        return transactionId;
    }

    public String getSplitRuleId() {
        return splitRuleId;
    }
    
    @Deprecated
    public DateTime getPayment(){
        return paymentDate;
    }

    public DateTime getPaymentDate() {
        return paymentDate;
    }

    public Status getStatus() {
        return status;
    }

    public Type getType() {
        return type;
    }

    public String getRecipientId() {
        return recipientId;
    }

    public String getAnticipationFee() {
        return anticipationFee;
    }

    public String getBulkAnticipationId() {
        return bulkAnticipationId;
    }

    public Date getOriginalPaymentDate() {
        return originalPaymentDate;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public void setFee(Integer fee) {
        this.fee = fee;
    }

    public void setInstallment(Integer installment) {
        this.installment = installment;
    }

    public void setTransactionId(Integer transactionId) {
        this.transactionId = transactionId;
    }

    public void setSplitRuleId(String splitRuleId) {
        this.splitRuleId = splitRuleId;
    }

    public void setPaymentDate(DateTime paymentDate) {
        this.paymentDate = paymentDate;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setRecipientId(String recipientId) {
        this.recipientId = recipientId;
    }

    public void setAnticipationFee(String anticipationFee) {
        this.anticipationFee = anticipationFee;
    }

    public void setBulkAnticipationId(String bulkAnticipationId) {
        this.bulkAnticipationId = bulkAnticipationId;
    }

    public void setOriginalPaymentDate(Date originalPaymentDate) {
        this.originalPaymentDate = originalPaymentDate;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    @Override
    public void setClassName(String className) {
        throw new UnsupportedOperationException("Not allowed.");
    }

    public enum Status {

        @SerializedName("paid")
        PAID,

        @SerializedName("waiting_funds")
        WAITING_FUNDS

    }

    public enum Type {

        @SerializedName("chargeback")
        CHARGEBACK,

        @SerializedName("credit")
        CREDIT,

        @SerializedName("refund")
        REFUND

    }
}
