package me.pagar.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ws.rs.HttpMethod;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import me.pagar.util.JSONUtils;

public class BalanceOperation extends PagarMeModel<String> {

    @Expose(serialize = false)
    private Status status;
    @Expose(serialize = false)
    private Type type;
    @Expose(serialize = false)
    private Integer balanceAmount;
    @Expose(serialize = false)
    private Integer balanceOldAmount;
    @Expose(serialize = false)
    private Integer amount;
    @Expose(serialize = false)
    private Integer fee;

    private Payable movementPayable;
    private Transfer movementTransfer;
    private BulkAnticipation movementBulkAnticipation;

    public BalanceOperation() {
        setClassName(getClassName());
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Integer getBalanceAmount() {
        return balanceAmount;
    }

    public void setBalanceAmount(Integer balanceAmount) {
        this.balanceAmount = balanceAmount;
    }

    public Integer getBalanceOldAmount() {
        return balanceOldAmount;
    }

    public void setBalanceOldAmount(Integer balanceOldAmount) {
        this.balanceOldAmount = balanceOldAmount;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getFee() {
        return fee;
    }

    public void setFee(Integer fee) {
        this.fee = fee;
    }

    public Payable getMovementPayable() {
        return movementPayable;
    }

    private void setMovementPayable(Payable payable){
        this.movementPayable = payable;
    }

    public Transfer getMovementTranfer(){
        return movementTransfer;
    }

    private void setMovementTransfer(Transfer transfer){
        this.movementTransfer = transfer;
    }

    public BulkAnticipation getMovementBulkAnticipation(){
        return movementBulkAnticipation;
    }

    private void setMovementBulkAnticipation(BulkAnticipation bulkAnticipation){
        this.movementBulkAnticipation = bulkAnticipation;
    }

    public BalanceOperation find(String id) throws PagarMeException {
        final PagarMeRequest request = new PagarMeRequest(HttpMethod.GET, String.format("/%s/%s", getClassName(), id));
        JsonObject response = (JsonObject) request.execute();

        final BalanceOperation other = convertJsonObject(response);
        copy(other);
        flush();

        return this;
    }

    public Collection<BalanceOperation> findCollection(int totalPerPage, int page) throws PagarMeException {
        List<BalanceOperation> balances = new ArrayList<BalanceOperation>();
        JsonArray response = super.paginate(totalPerPage, page);
        for (JsonElement jsonElement : response) {
            BalanceOperation balanceOperation = convertJsonObject(jsonElement.getAsJsonObject());
            balances.add(balanceOperation);
        }
        return balances;
    }

    private void copy(BalanceOperation other){
        super.copy(other);
        this.amount = other.amount;
        this.balanceAmount = other.balanceAmount;
        this.balanceOldAmount = other.balanceOldAmount;
        this.fee = other.fee;
        this.movementPayable = other.movementPayable;
        this.movementTransfer = other.movementTransfer;
        this.status = other.status;
        this.type = other.type;
    }
    public enum Status{

        @SerializedName("waiting_funds")
        WAITING_FUNDS, 

        @SerializedName("available")
        AVAILABLE, 

        @SerializedName("transferred")
        TRANSFERRED
    }

    public enum Type{

        @SerializedName("payable")
        PAYABLE, 

        @SerializedName("transfer")
        TRANSFER, 

        @SerializedName("anticipation")
        ANTICIPATION;
    }

    @Override
    public String getClassName() {
        return "balance/operations";
    }

    private BalanceOperation convertJsonObject(JsonObject json){
        final BalanceOperation other = JSONUtils.getAsObject(json, BalanceOperation.class);

        Type responseType = Type.valueOf(json.get("type").getAsString().toUpperCase());
        JsonObject responseMovementObject = json.get("movement_object").getAsJsonObject();
        if(responseType.equals(Type.PAYABLE)){
            Payable payable = JSONUtils.getAsObject(responseMovementObject, Payable.class);
            other.setMovementPayable(payable);
        }else if(responseType.equals(Type.TRANSFER)){
            Transfer transfer = JSONUtils.getAsObject(responseMovementObject, Transfer.class);
            other.setMovementTransfer(transfer);
        }else if(responseType.equals(Type.ANTICIPATION)){
            BulkAnticipation bulkAnticipation = JSONUtils.getAsObject(responseMovementObject, BulkAnticipation.class);
            other.setMovementBulkAnticipation(bulkAnticipation);
        }

        return other;
    }
}
