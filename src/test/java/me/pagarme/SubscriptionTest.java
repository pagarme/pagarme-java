package me.pagarme;

import java.util.Collection;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import me.pagar.SubscriptionStatus;
import me.pagar.model.Address;
import me.pagar.model.Card;
import me.pagar.model.Customer;
import me.pagar.model.Phone;
import me.pagar.model.Plan;
import me.pagar.model.Subscription;
import me.pagarme.factory.PlanFactory;
import me.pagarme.factory.SubscriptionFactory;

public class SubscriptionTest extends BaseTest {

    private PlanFactory planFactory = new PlanFactory();
    private SubscriptionFactory subscriptionFactory = new SubscriptionFactory();

    private static String CARD_HOLDER_NAME = "Pagarme LTDA";
    private static String CARD_NUMBER = "4111111111111111";
    private static Integer CARD_CVV = 401;
    private static String CARD_EXPIRATION_DATE = "1022";

    @Before
    public void setUp() {
        super.setUp();
    }

    @Test
    public void testCreateSubscription() throws Throwable {

        Subscription subscription = createSubscription();
        subscription.save();

        Assert.assertEquals(subscription.getStatus(), SubscriptionStatus.paid);
        Assert.assertNotNull(subscription.getId());
    }


    @Test
    public void testUpdateSubscriptionPlan() throws Throwable {

        Subscription subscription = createSubscription();
        subscription.save();

        Plan plan = planFactory.create();
        plan.setAmount(111);
        plan.save();

        Integer planId = plan.getId();

        Subscription editSubscription = new Subscription();
        editSubscription.setId(subscription.getId());
        editSubscription.setPlanId(planId);
        editSubscription.save();

        Assert.assertEquals(editSubscription.getPlan().getId(), planId);
    }

    @Test
    public void testFindSubscription() throws Throwable {
        Subscription subscription = createSubscription();
        subscription.save();

        Subscription searchSubscription = new Subscription();
        searchSubscription.find(subscription.getId());

        Assert.assertEquals(subscription.getId(), searchSubscription.getId());
        Assert.assertEquals(subscription.getCard().getId(), searchSubscription.getCard().getId());
    }

    @Test
    public void testListSubscription() throws Throwable {
        Subscription subscription = createSubscription();
        subscription.save();

        Collection<Subscription> subscriptions = subscription.findCollection();

        Assert.assertTrue(subscriptions.size() >= 1);
    }

    @Test
    public void testCancelSubscription() throws Throwable {
        Subscription subscription = createSubscription();
        subscription.save();

        subscription.cancel();

        Assert.assertEquals(subscription.getStatus(), SubscriptionStatus.canceled);
    }

    private Subscription createSubscription() throws Throwable {
        Customer customer = this.customerCommon();
        Address address = this.addressCommon();
        Phone phone = this.phoneCommon();

        customer.setPhone(phone);
        customer.setAddress(address);

        Plan plan = planFactory.create();
        plan.save();

        Card card = new Card();
        card.setHolderName(CARD_HOLDER_NAME);
        card.setNumber(CARD_NUMBER);
        card.setExpiresAt(CARD_EXPIRATION_DATE);
        card.setCvv(CARD_CVV);
        card.save();

        Subscription subscription = new Subscription();
        subscription.setCustomer(customer);
        subscription.setCardId(card.getId());
        subscription.setPlanId(plan.getId());

        return subscription;
    }

}