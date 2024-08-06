package me.pagar.model.filter;

import me.pagar.model.BalanceOperation.Status;
import org.joda.time.DateTime;

public class BalanceOperationQueriableFields extends QueriableFieldsAbstract implements QueriableFields {

    private static final String STATUS = "status";
    private static final String START_DATE = "start_date";
    private static final String END_DATE = "end_date";

    public void statusEquals(Status status) {
        super.setEquals(STATUS, status.name().toLowerCase());
    }

    public void createdAfter(DateTime date) {
        super.setEquals(START_DATE, Long.toString(date.getMillis()));
    }

    public void createdBefore(DateTime date) {
        super.setEquals(END_DATE, Long.toString(date.getMillis()));
    }

    public String pagarmeRelatedModel() {
        return "balances";
    }
}
