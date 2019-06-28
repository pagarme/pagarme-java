
package me.pagar.model;

import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class BalanceOperation extends PagarMeModel<String> {

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

    public Status getStatus() {
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

    public enum Status {
        @SerializedName("available")
        AVAILABLE,
        @SerializedName("transferred")
        TRANSFERRED,
        @SerializedName("waiting_funds")
        WAITING_FUNDS
    }
}
