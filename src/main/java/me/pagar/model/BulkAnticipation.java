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

    @Expose
    private DateTime paymentDate;
    @Expose
    private Timeframe timeframe;

    @Expose(deserialize=false)
    private Boolean building;
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

    public class LimitParameters{
        public void setPaymentDate(DateTime paymentDate){
            BulkAnticipation.this.paymentDate = paymentDate;
        }

        public void setTimeFrame(Timeframe timeframe){
            BulkAnticipation.this.timeframe = timeframe;
        }
    }

    public class CreateParameters{
        public void setPaymentDate(DateTime paymentDate){
            BulkAnticipation.this.paymentDate = paymentDate;
        }

        public void setTimeFrame(Timeframe timeframe){
            BulkAnticipation.this.timeframe = timeframe;
        }

        public void setRequestedAmount(Integer requestedAmount) {
            BulkAnticipation.this.requestedAmount = requestedAmount;
        }

        public void setBuilding(Boolean building) {
            BulkAnticipation.this.building = building;
        }
    }

    public enum Status{

        @SerializedName("building")
        BUILDING, PENDING, 

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
