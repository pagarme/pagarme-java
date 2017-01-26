package me.pagar.model;

import com.google.gson.annotations.Expose;

public class Limit{
    @Expose(serialize = false)
    private Integer amount;
    @Expose(serialize = false)
    private Integer anticipationFee;
    @Expose(serialize = false)
    private Integer fee;

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
