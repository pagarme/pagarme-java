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

    @Expose
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
        //???
        if(build){
            this.build = build;
        }else{
            this.building = build;
        }
        
    }

    public Map<String, Object> toMap(){
        HashMap<String, Object> mappedThis = new HashMap<String, Object>();
        if(this.paymentDate != null){
            mappedThis.put("payment_date", this.paymentDate.getMillis());
        }
        if(this.timeframe != null){
            mappedThis.put("timeframe", this.timeframe.name().toLowerCase());
        }
        if(this.build != null){
            mappedThis.put("build", this.build.toString());
        }
        if(this.requestedAmount != null){
            mappedThis.put("requested_amount", requestedAmount);
        }
        return mappedThis;
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
