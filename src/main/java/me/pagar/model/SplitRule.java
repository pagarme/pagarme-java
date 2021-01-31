package me.pagar.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.joda.time.DateTime;

public class SplitRule extends PagarMeModel<String> {

    @Expose
    @SerializedName("recipient_id")
    private String recipientId;

    @Expose
    @SerializedName("charge_processing_fee")
    private Boolean chargeProcessingFee;

    @Expose
    private Boolean liable;

    @Expose
    private Integer percentage;

    @Expose
    private Integer amount;

    @Expose
    @SerializedName("charge_remainder")
    private Boolean chargeRemainder;

    @Expose(serialize = false)
    @SerializedName("date_updated")
    private DateTime updatedAt;

    public String getRecipientId() {
        return recipientId;
    }

    public Boolean getChargeProcessingFee() {
        return chargeProcessingFee;
    }

    public Boolean getLiable() {
        return liable;
    }

    public Integer getPercentage() {
        return percentage;
    }

    public Integer getAmount() {
        return amount;
    }

    public Boolean getChargeRemainder() {
        return chargeRemainder;
    }

    public DateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setRecipientId(String recipientId) {
        this.recipientId = recipientId;
    }

    public void setChargeProcessingFee(Boolean chargeProcessingFee) {
        this.chargeProcessingFee = chargeProcessingFee;
    }

    public void setLiable(Boolean liable) {
        this.liable = liable;
    }

    public void setPercentage(Integer percentage) {
        this.percentage = percentage;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public void setChargeRemainder(Boolean chargeRemainder) {
        this.chargeRemainder = chargeRemainder;
    }

    @Override
    public void setId(String id) {
        throw new UnsupportedOperationException("Not allowed.");
    }

    @Override
    public void setClassName(String className) {
        throw new UnsupportedOperationException("Not allowed.");
    }

}
