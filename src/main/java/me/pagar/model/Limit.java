package me.pagar.model;

import com.google.gson.annotations.Expose;

public class Limit{
    @Expose(serialize = false)
    private Integer amount;
    @Expose(serialize = false)
    private Integer anticipationFee;
    @Expose(serialize = false)
    private Integer fee;

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public void setAnticipationFee(Integer anticipationFee) {
        this.anticipationFee = anticipationFee;
    }

    public void setFee(Integer fee) {
        this.fee = fee;
    }

    public Integer getAmount() {
        return amount;
    }
    public Integer getAnticipationFee() {
        return anticipationFee;
    }
    public Integer getFee() {
        return fee;
    }
}
