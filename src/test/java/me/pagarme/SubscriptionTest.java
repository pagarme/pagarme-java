package me.pagarme;

import java.util.Collection;
import java.util.Iterator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import me.pagar.SubscriptionStatus;
import me.pagar.model.Card;
import me.pagar.model.Customer;
import me.pagar.model.PagarMeException;
import me.pagar.model.Plan;
import me.pagar.model.SplitRule;
import me.pagar.model.Subscription;
import me.pagar.model.Transaction;
import me.pagar.model.Transaction.PaymentMethod;
import me.pagarme.factory.CardFactory;
import me.pagarme.factory.CustomerFactory;
import me.pagarme.factory.PlanFactory;
import me.pagarme.factory.SplitRulesFactory;
import me.pagarme.factory.SubscriptionFactory;
import static org.hamcrest.CoreMatchers.instanceOf;

public class SubscriptionTest extends BaseTest {

    private PlanFactory planFactory = new PlanFactory();
    private SubscriptionFactory subscriptionFactory = new SubscriptionFactory();
    private CustomerFactory customerFactory = new CustomerFactory();
    private CardFactory cardFactory = new CardFactory();
    private SplitRulesFactory splitRulesFactory = new SplitRulesFactory();

    private Plan defaultPlanWithTrialDays;
    private Plan defaultPlanWithoutTrialDays;
    private Customer defaultCustomer;
    private Card defaultCard;

    @Before
    public void beforeEach() throws PagarMeException {
        super.setUp();
        defaultCustomer = customerFactory.create();
        defaultCustomer.save();

        defaultPlanWithTrialDays = planFactory.create();
        defaultPlanWithTrialDays.save();

        defaultPlanWithoutTrialDays = planFactory.createPlanWithoutTrialDays();
        defaultPlanWithoutTrialDays.save();

        defaultCard = cardFactory.create();
        defaultCard.save();
    }

    @Test
    public void testCreateSubscription() throws Throwable {
        Subscription subscription = subscriptionFactory.createCreditCardSubscription(defaultPlanWithTrialDays.getId(), defaultCard.getId(), defaultCustomer);
        subscription.save();
        Assert.assertEquals(SubscriptionStatus.TRIALING, subscription.getStatus());
        Assert.assertNotNull(subscription.getId());
    }

    @Test
    public void testUpdateSubscriptionPlan() throws Throwable {
        Subscription subscription = subscriptionFactory.createCreditCardSubscription(defaultPlanWithTrialDays.getId(), defaultCard.getId(), defaultCustomer);
        subscription.save();

        Plan newPlan = planFactory.create();
        newPlan.save();

        String newPlanId = newPlan.getId();

        Subscription edittedSubscription = new Subscription();
        edittedSubscription.setId(subscription.getId());
        edittedSubscription.setUpdatableParameters(null, null, newPlanId);
        edittedSubscription.save();

        Assert.assertEquals(newPlanId, edittedSubscription.getPlan().getId());
    }

    @Test
    public void testFindSubscription() throws Throwable {
        Subscription subscription = subscriptionFactory.createCreditCardSubscription(defaultPlanWithTrialDays.getId(), defaultCard.getId(), defaultCustomer);
        subscription.save();

        Subscription foundSubscription = new Subscription().find(subscription.getId());

        Assert.assertEquals(subscription.getId(), foundSubscription.getId());
        Assert.assertEquals(subscription.getPostbackUrl(), foundSubscription.getPostbackUrl());
        Assert.assertEquals(subscription.getCharges(), foundSubscription.getCharges());
        Assert.assertEquals(subscription.getCreatedAt(), foundSubscription.getCreatedAt());
        Assert.assertEquals(subscription.getCurrentPeriodEnd(), foundSubscription.getCurrentPeriodEnd());
        Assert.assertEquals(subscription.getCurrentPeriodStart(), foundSubscription.getCurrentPeriodStart());
        Assert.assertEquals(subscription.getCurrentTransaction(), foundSubscription.getCurrentTransaction());
        Assert.assertEquals(subscription.getCustomer().getId(), foundSubscription.getCustomer().getId());
        Assert.assertEquals(PaymentMethod.CREDIT_CARD, foundSubscription.getPaymentMethod());
        Assert.assertEquals(subscription.getPlan().getId(), foundSubscription.getPlan().getId());
        Assert.assertEquals(subscription.getStatus(), foundSubscription.getStatus());

        Assert.assertEquals("some_metadata",foundSubscription.getMetadata().keySet().iterator().next());
        Assert.assertEquals("123456",foundSubscription.getMetadata().values().iterator().next());
    }

    /*
    @Test
    public void testListSubscription() throws Throwable {
        Subscription subscription1 = subscriptionFactory.createCreditCardSubscription(defaultPlan.getId(), defaultCard.getId(), defaultCustomer);
        subscription1.save();

        Customer otherCustomer = customerFactory.create();
        otherCustomer.setEmail("other@email.com");
        otherCustomer.setDocumentNumber("31102987166");
        Subscription subscription2 = subscriptionFactory.createBoletoSubscription(defaultPlan.getId(), otherCustomer);
        subscription2.save();

        Collection<Subscription> subscriptions = new Subscription().findCollection(10, 1);

        Assert.assertEquals(2, subscriptions.size());
    }
    */
    @Test
    public void testTransactionsCollectionInSubscription() throws PagarMeException{
        Subscription subscription = subscriptionFactory.createCreditCardSubscription(defaultPlanWithoutTrialDays.getId(), defaultCard.getId(), defaultCustomer);
        subscription.save();
        Iterator t = subscription.transactions().iterator();
        while(t.hasNext()){
            Assert.assertThat(t.next(), instanceOf(Transaction.class));
        }
    }

    @Test
    public void testCancelSubscription() throws Throwable {
        Subscription subscription = subscriptionFactory.createCreditCardSubscription(defaultPlanWithTrialDays.getId(), defaultCard.getId(), defaultCustomer);
        subscription.save();

        subscription.cancel();

        Assert.assertEquals(subscription.getStatus(), SubscriptionStatus.CANCELED);
    }

    @Test
    public void testSavePostbackUrl() throws PagarMeException{
        Subscription subscription = subscriptionFactory.createCreditCardSubscription(defaultPlanWithTrialDays.getId(), defaultCard.getId(), defaultCustomer);
        subscription.setPostbackUrl("http://requestb.in/t5mzh9t5");
        subscription = subscription.save();

        Assert.assertEquals("http://requestb.in/t5mzh9t5", subscription.getPostbackUrl());
    }

    @Test
    public void testSplitSubscriptionPercentage() throws Throwable {

        Subscription subscription = subscriptionFactory.createCreditCardSubscription(defaultPlanWithoutTrialDays.getId(),defaultCard.getId(), defaultCustomer);
        Collection<SplitRule> splitRules = splitRulesFactory.createSplitRuleWithPercentage();

        subscription.setSplitRules(splitRules);
        subscription.save();

        Transaction foundTransaction = new Transaction().find(subscription.getCurrentTransaction().getId());

        Collection<SplitRule> foundSplitRules = foundTransaction.getSplitRules();
        Assert.assertEquals(splitRules.size(), foundSplitRules.size());
    }

    @Test
    public void testSplitSubscriptionAmount() throws Throwable {

        Subscription subscription = subscriptionFactory.createCreditCardSubscription(defaultPlanWithoutTrialDays.getId(),defaultCard.getId(), defaultCustomer);
        Collection<SplitRule> splitRules = splitRulesFactory.createSplitRuleWithAmount(defaultPlanWithoutTrialDays);

        subscription.setSplitRules(splitRules);
        subscription.save();

        Transaction foundTransaction = new Transaction().find(subscription.getCurrentTransaction().getId());

        Collection<SplitRule> foundSplitRules = foundTransaction.getSplitRules();
        Assert.assertEquals(splitRules.size(), foundSplitRules.size());
    }
}
