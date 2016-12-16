package me.pagar.model;

import java.util.Collection;

import javax.ws.rs.HttpMethod;

import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import me.pagar.PaymentMethod;
import me.pagar.util.JSONUtils;

public class Plan extends PagarMeModel<Integer> {

    @Expose
    private Integer amount;

    @Expose
    private Integer days;

    @Expose
    private String name;

    @Expose
    @SerializedName("trial_days")
    private Integer trialDays;

    @Expose
    @SerializedName("payment_methods")
    private Collection<PaymentMethod> paymentMethods;

    @Expose
    private Integer charges;

    @Expose
    private Integer installments;

    public Plan save() throws PagarMeException {
        final Plan saved = super.save(getClass());
        copy(saved);

        return saved;
    }

    public Plan find(Integer id) throws PagarMeException {

        final PagarMeRequest request = new PagarMeRequest(HttpMethod.GET,
                String.format("/%s/%d", getClassName(), id));

        final Plan other = JSONUtils.getAsObject((JsonObject) request.execute(), Plan.class);
        copy(other);
        flush();

        return other;
    }

    public Collection<Plan> findCollection() throws PagarMeException {
        return findCollection(100, 0);
    }

    public Collection<Plan> findCollection(Integer totalPerPage, Integer page) throws PagarMeException {
        return JSONUtils.getAsList(super.paginate(totalPerPage, page), new TypeToken<Collection<Plan>>() {
        }.getType());
    }

    public Plan refresh() throws PagarMeException {
        final Plan other = JSONUtils.getAsObject(refreshModel(), Plan.class);
        copy(other);
        flush();
        return other;
    }

    private void copy(Plan other) {
        setId(other.getId());
        setAmount(other.getAmount());
        setDays(other.getDays());
        setName(other.getName());
        setTrialDays(other.getTrialDays());
        setPaymentMethods(other.getPaymentMethods());
        setCharges(other.getCharges());
        setInstallments(other.getInstallments());
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
        addUnsavedProperty("amount");
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
        addUnsavedProperty("days");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        addUnsavedProperty("name");
    }

    public Integer getTrialDays() {
        return trialDays;
    }

    public void setTrialDays(Integer trialDays) {
        this.trialDays = trialDays;
        addUnsavedProperty("trialDays");
    }

    public Collection<PaymentMethod> getPaymentMethods() {
        return paymentMethods;
    }

    public void setPaymentMethods(Collection<PaymentMethod> paymentMethods) {
        this.paymentMethods = paymentMethods;
        addUnsavedProperty("paymentMethods");
    }

    public Integer getCharges() {
        return charges;
    }

    public void setCharges(Integer charges) {
        this.charges = charges;
        addUnsavedProperty("charges");
    }

    public Integer getInstallments() {
        return installments;
    }

    public void setInstallments(Integer installments) {
        this.installments = installments;
        addUnsavedProperty("installments");
    }

}
