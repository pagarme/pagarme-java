package me.pagar.model;

import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ws.rs.HttpMethod;

/**
 *
 * @author jonatasmaxi
 */
public class Calculation {

    public Calculation() {

    }

    public List<Installment> calculateInstallments(int amount,
            int freeInstallments, double interestRate, int maxInstallments) throws PagarMeException {

        final PagarMeRequest request = new PagarMeRequest(HttpMethod.GET, "/transactions/calculate_installments_amount");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("amount", amount);
        params.put("max_installments", maxInstallments);
        params.put("free_installments", freeInstallments);
        params.put("interest_rate", interestRate);
        request.setParameters(params);
        JsonObject response = request.execute();
        JsonObject installmentsArray = response.getAsJsonObject("installments");
        List<Installment> installments = new ArrayList<Installment>();
        for (int i = 1; i <= maxInstallments; i++) {
            Installment objInstallment = new Installment();
            JsonObject installment = installmentsArray.getAsJsonObject(""+i);
            objInstallment.setInstallmentAmount(Integer.parseInt(installment.get("installment_amount").toString()));
            objInstallment.setAmount(Integer.parseInt(installment.get("amount").toString()));
            objInstallment.setInstallment(Integer.parseInt(installment.get("installment").toString()));
            installments.add(objInstallment);
        }
        return installments;
    }
}
