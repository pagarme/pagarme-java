package me.pagarme.factory;


import me.pagar.model.Plan;

public class PlanFactory {

    private static final int PLAN_DEFAULT_AMOUNT = 100;
    private static final int PLAN_DEFAULT_DAYS = 30;

    public Plan create() {
        Plan plan = new Plan();
        plan.setName("Test Plan");
        plan.setAmount(PLAN_DEFAULT_AMOUNT);
        plan.setDays(PLAN_DEFAULT_DAYS);
        return plan;
    }


}
