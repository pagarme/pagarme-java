package me.pagarme.factory;

import java.util.HashMap;
import java.util.Map;
import me.pagar.model.Customer;
import me.pagar.model.Subscription;

public class SubscriptionFactory {
    private final Map<String, Object> METADATA =  new HashMap<String, Object>();

    public Subscription createCreditCardSubscription(String planId, String cardId, Customer customer){
        Subscription subscription = new Subscription();
        subscription.setCreditCardSubscriptionWithCardId(planId, cardId, customer);
        METADATA.put("some_metadata", "123456");
        subscription.setMetadata(METADATA);
        return subscription;
    }

    public Subscription createBoletoSubscription(String planId, Customer customer){
        Subscription subscription = new Subscription();
        subscription.setBoletoSubscription(planId, customer);
        return subscription;
    }
}
