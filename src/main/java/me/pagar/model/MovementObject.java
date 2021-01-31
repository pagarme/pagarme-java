package me.pagar.model;

import com.google.gson.annotations.Expose;
import java.util.HashMap;

public class MovementObject extends PagarMeModel<String> {

    @Expose
    private String object;

    @Expose
    private Integer amount;

    @Expose
    private String type;

    @Expose
    private String status;

    @Expose
    private String transactionId;

    @Expose
    private String bankAccountId;

    @Expose
    private HashMap<String, Object> metadata;

    public MovementObject() {
        super();
    }

    public String getObject() {
        return object;
    }

    public Integer getAmount() {
        return amount;
    }

    public String getType() {
        return type;
    }

    public String getStatus() {
        return status;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getBankAccountId() {
        return bankAccountId;
    }

    public HashMap<String, Object> getMetadata() {
        return metadata;
    }
}
