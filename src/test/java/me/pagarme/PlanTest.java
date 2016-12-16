package me.pagarme;

import java.util.Collection;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import me.pagar.model.Plan;
import me.pagarme.factory.PlanFactory;

public class PlanTest extends BaseTest {

    private PlanFactory planFactory = new PlanFactory();

    @Before
    public void setUp() {
        super.setUp();
    }

    @Test
    public void testCreatePlan() throws Throwable {

        Plan plan = planFactory.create();
        plan.save();

        Assert.assertNotNull(plan.getId());
    }

    @Test
    public void testFindPlan() throws Throwable {
        Plan plan = planFactory.create();
        plan.save();

        Plan searchPlan = new Plan();
        searchPlan.find(plan.getId());

        Assert.assertEquals(searchPlan.getId(), plan.getId());
    }

    @Test
    public void testListPlan() throws Throwable {
        Plan plan = planFactory.create();
        plan.save();

        Collection<Plan> plans = plan.findCollection();
        Assert.assertTrue(plans.size() >= 1);
    }

}