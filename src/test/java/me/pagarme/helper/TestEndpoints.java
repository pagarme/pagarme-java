package me.pagarme.helper;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonObject;

import me.pagar.model.PagarMe;
import me.pagar.model.PagarMeException;
import me.pagar.model.PagarMeRequest;
import me.pagar.model.Transaction;
import me.pagar.util.JSONUtils;

public class TestEndpoints {

    public Transaction payBoleto(Transaction transaction) throws PagarMeException{
        PagarMeRequest request = new PagarMeRequest("PUT", "/transactions/" + transaction.getId());
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("status", "paid");
        parameters.put("api_key", PagarMe.getApiKey());
        request.setParameters(parameters);
        return new JSONUtils().getAsObject((JsonObject)request.execute(), Transaction.class);
    }
}
