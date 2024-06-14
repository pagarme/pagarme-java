package me.pagar.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import java.util.Collection;
import me.pagar.util.JSONUtils;

import javax.ws.rs.HttpMethod;
import me.pagar.model.filter.QueriableFields;

public class BalanceOperation extends PagarMeModel<String>{

    @Expose
    private Status status;

    @Expose
    private int balanceAmount;

    @Expose
    private int balanceOldAmount;

    @Expose
    private String type;

    @Expose
    private int amount;

    @Expose
    private int fee;

    @Expose
    private JsonObject movementObject;

    public BalanceOperation() {
        super();
    }

    public Status getStatus(){
        return status;
    }

    public int getBalanceAmount() {
        return balanceAmount;
    }

    public int getBalanceOldAmount() {
        return balanceOldAmount;
    }

    public String getType() {
        return type;
    }

    public int getAmount() {
        return amount;
    }

    public int getFee() {
        return fee;
    }

    private JsonObject getMovementObject() {
        return movementObject;
    }

    public PagarMeModel getMovementObjectAsPagarmeObject() {
        PagarMeModel movementObjectAsPagarMeModel = null;
        if(this.type.equals("anticipation"))
            movementObjectAsPagarMeModel = new BulkAnticipation();
        else if(this.type.equals("payable"))
            movementObjectAsPagarMeModel = new Payable();
        else if(this.type.equals("transfer"))
            movementObjectAsPagarMeModel = new Transfer();
        else
            movementObjectAsPagarMeModel = new MovementObject();

        return JSONUtils.getAsObject((JsonObject) movementObject, movementObjectAsPagarMeModel.getClass());
    }

    public BalanceOperation find(String id) throws PagarMeException {

        final PagarMeRequest request = new PagarMeRequest(HttpMethod.GET,
                String.format("/%s/%s/",getClassName(), id));

        final BalanceOperation other = JSONUtils.getAsObject((JsonObject) request.execute(), BalanceOperation.class);
        copy(other);
        flush();

        return other;
    }

    public Collection<BalanceOperation> findCollection(Integer totalPerPage, Integer page) throws Throwable {
        return JSONUtils.getAsList(super.paginate(totalPerPage, page), new TypeToken<Collection<BalanceOperation>>() {
        }.getType());
    }

    public Collection<BalanceOperation> findCollection(Integer totalPerPage, Integer page, QueriableFields balanceFilter) throws PagarMeException {
        JsonArray response = super.paginate(totalPerPage, page, balanceFilter);
        return JSONUtils.getAsList(response, new TypeToken<Collection<BalanceOperation>>() {
        }.getType());
    }

    @Override
    public String getClassName() {
        return "balance/operations";
    }

    private void copy(BalanceOperation other) {
        super.copy(other);
        this.status = other.getStatus();
        this.balanceAmount = other.getBalanceAmount();
        this.balanceOldAmount = other.getBalanceOldAmount();
        this.type = other.getType();
        this.amount = other.getAmount();
        this.fee = other.getFee();
        this.movementObject = other.getMovementObject();
    }

    public enum Status {
        @SerializedName("available")
        AVAILABLE,

        @SerializedName("transferred")
        TRANSFERRED,

        @SerializedName("waiting_funds")
        WAITING_FUNDS
    }
}
