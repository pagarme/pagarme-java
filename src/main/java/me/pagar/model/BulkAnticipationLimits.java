package me.pagar.model;

import com.google.gson.annotations.Expose;

public class BulkAnticipationLimits {

    @Expose(serialize = false)
    private Limit maximum;
    @Expose(serialize = false)
    private Limit minimum;

    public Limit getMaximum() {
        return maximum;
    }

    public Limit getMinimum() {
        return minimum;
    }
}
