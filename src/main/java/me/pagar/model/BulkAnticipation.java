package me.pagar.model;

import org.joda.time.DateTime;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BulkAnticipation extends PagarMeModel<String> {

    @Expose(serialize = false)
    private Status status;
    @Expose(serialize = false)
    private Integer amount;
    @Expose(serialize = false)
    private Integer fee;
    @Expose(serialize = false)
    private Integer anticipationFee;
    @Expose(serialize = false)
    private Boolean building;

    @Expose
    private DateTime paymentDate;
    @Expose
    private Timeframe timeframe;

    @Expose(deserialize=false)
    private Integer requestedAmount;

    public DateTime getPaymentDate() {
        return paymentDate;
    }

    public Timeframe getTimeframe() {
        return timeframe;
    }

    public Boolean getBuilding() {
        return building;
    }

    public Integer getRequestedAmount() {
        return requestedAmount;
    }

    public Status getStatus() {
        return status;
    }

    public Integer getAmount() {
        return amount;
    }

    public Integer getFee() {
        return fee;
    }

    public Integer getAnticipationFee() {
        return anticipationFee;
    }

    public void setRequiredParametersForAnticipationLimit(DateTime paymentDate, Timeframe timeframe){
        this.paymentDate = paymentDate;
        this.timeframe = timeframe;
    }

    public void setRequiredParametersForCreation(DateTime paymentDate, Timeframe timeframe, Integer requestedAmount){
        this.paymentDate = paymentDate;
        this.timeframe = timeframe;
        this.requestedAmount = requestedAmount;
    }

    public enum Status{

        @SerializedName("building")
        BUILDING, 

        @SerializedName("pending")
        PENDING, 

        @SerializedName("aproved")
        APPROVED, 

        @SerializedName("refused")
        REFUSED, 

        @SerializedName("canceled")
        CANCELED;
    }

    public enum Timeframe{

        @SerializedName("start")
        START, 

        @SerializedName("end")
        END;
    }
}
