package me.pagar.model;

import java.util.HashMap;
import java.util.Map;

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
    @Expose(deserialize = false)
    private Boolean build;

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

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public void setFee(Integer fee) {
        this.fee = fee;
    }

    public void setAnticipationFee(Integer anticipationFee) {
        this.anticipationFee = anticipationFee;
    }

    public void setBuilding(Boolean building) {
        this.building = building;
    }

    public void setPaymentDate(DateTime paymentDate) {
        this.paymentDate = paymentDate;
    }

    public void setTimeframe(Timeframe timeframe) {
        this.timeframe = timeframe;
    }

    public void setRequestedAmount(Integer requestedAmount) {
        this.requestedAmount = requestedAmount;
    }

    public void setBuild(Boolean build) {
        this.build = build;
    }

    public Boolean getBuild() {
        return build;
    }

    public void setRequiredParametersForAnticipationLimit(DateTime paymentDate, Timeframe timeframe){
        this.paymentDate = paymentDate;
        this.timeframe = timeframe;
    }

    public void setRequiredParametersForCreation(DateTime paymentDate, Timeframe timeframe, Integer requestedAmount, Boolean build){
        this.paymentDate = paymentDate;
        this.timeframe = timeframe;
        this.requestedAmount = requestedAmount;
        if(build){
            this.build = build;
        }
    }

    public enum Status{

        @SerializedName("building")
        BUILDING, 

        @SerializedName("pending")
        PENDING, 

        @SerializedName("approved")
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
