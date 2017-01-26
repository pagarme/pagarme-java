package me.pagar.model;

import java.util.Collection;

import javax.ws.rs.HttpMethod;

import org.joda.time.DateTime;

import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import me.pagar.util.JSONUtils;

public class Transfer extends PagarMeModel<String> {

    @Expose(serialize=false)
    private Type type;
    @Expose(serialize=false)
    private Status status;
    @Expose(serialize=false)
    private Integer fee;
    @Expose(serialize=false)
    private DateTime fundingEstimatedDate;
    @Expose(serialize=false)
    private BankAccount bankAccount;

    @Expose
    private Integer amount;

    @Expose(deserialize=false)
    private Integer bankAccountId;
    @Expose(deserialize=false)
    private String recipientId;

    public Transfer(){
        super();
    }

    public Transfer(Integer amount, String recipientId){
        super();
        this.recipientId = recipientId;
        this.amount = amount;
    }

    public Transfer(Integer amount, String recipientId, Integer bankAccountId){
        super();
        this.amount = amount;
        this.recipientId = recipientId;
        this.bankAccountId = bankAccountId;
    }

    public Type getType() {
        return type;
    }

    public Status getStatus() {
        return status;
    }

    public Integer getFee() {
        return fee;
    }

    public DateTime getFundingEstimatedDate() {
        return fundingEstimatedDate;
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public Integer getAmount() {
        return amount;
    }

    public Integer getBankAccountId() {
        return bankAccountId;
    }

    public String getRecipientId() {
        return recipientId;
    }

    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public void setBankAccountId(Integer bankAccountId) {
        this.bankAccountId = bankAccountId;
    }

    public Transfer save() throws PagarMeException{
        final Transfer saved = super.save(getClass());
        copy(saved);

        return saved;
    }

    public Transfer cancel() throws PagarMeException{
        validateId();

        final PagarMeRequest request = new PagarMeRequest(HttpMethod.POST,
                String.format("/%s/%s/cancel", getClassName(), getId()));
        request.getParameters().put("amount", amount);
        request.getParameters().put("recipient_id", getRecipientId());
        request.getParameters().put("bank_account_id", getBankAccountId());

        final Transfer other = new JSONUtils().getAsObject((JsonObject) request.execute(), Transfer.class);
        copy(other);
        flush();

        return other;
    }

    public Transfer find(String id) throws PagarMeException{
        final PagarMeRequest request = new PagarMeRequest(HttpMethod.GET, String.format("/%s/%s", getClassName(), id));

        final Transfer other = new JSONUtils().getAsObject((JsonObject) request.execute(), Transfer.class);
        copy(other);
        flush();

        return other;
    }

    public Collection<Transfer> findCollection(int totalPerPage, int page) throws PagarMeException {
        return new JSONUtils().getAsList(super.paginate(totalPerPage, page), new TypeToken<Collection<Transfer>>() {
        }.getType());
    }

    private void copy(Transfer other){
        super.copy(other);
        this.amount = other.amount;
        this.bankAccount = other.bankAccount;
        this.fee = other.fee;
        this.fundingEstimatedDate = other.fundingEstimatedDate;
        this.status = other.status;
        this.type = other.type;
    }

    public enum Status{
        @SerializedName("pending_transfer")
        PENDING_TRANSFER,
        @SerializedName("transferred")
        TRANSFERRED,
        @SerializedName("failed")
        FAILED,
        @SerializedName("processing")
        PROCESSING,
        @SerializedName("canceled")
        CANCELED
    }

    public enum Type{
        @SerializedName("ted")
        TED,
        @SerializedName("doc")
        DOC,
        @SerializedName("credito_em_conta")
        CREDITO_EM_CONTA
    }
}
