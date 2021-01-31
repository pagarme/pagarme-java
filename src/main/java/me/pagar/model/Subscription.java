package me.pagar.model;

import java.util.Collection;
import java.util.Map;

import javax.ws.rs.HttpMethod;

import org.joda.time.DateTime;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import java.util.HashMap;
import java.util.List;

import me.pagar.model.Transaction.PaymentMethod;
import me.pagar.SubscriptionStatus;
import me.pagar.util.JSONUtils;

public class Subscription extends PagarMeModel<String> {

    @Expose(deserialize = false)
    private String cardHash;
    @Expose(deserialize = false)
    private String cardId;
    @Expose(deserialize = false)
    private String planId;

    @Expose
    private Customer customer;
    @Expose
    private String postbackUrl;
    @Expose
    private PaymentMethod paymentMethod;
    @Expose
    private DateTime currentPeriodStart;
    @Expose
    private DateTime currentPeriodEnd;
    @Expose
    private Map<String, Object> metadata;
    @Expose(deserialize = false)
    private Collection<SplitRule> splitRules;
    @Expose
    @SerializedName("soft_descriptor")
    private String softDescriptor;

    @Expose(serialize = false)
    private Plan plan;
    @Expose(serialize = false)
    private Transaction currentTransaction;
    @Expose(serialize = false)
    private Integer charges;
    @Expose(serialize = false)
    private SubscriptionStatus status;
    @Expose(serialize = false)
    private Phone phone;
    @Expose(serialize = false)
    private Address address;
    @Expose
    private Card card;
    @Expose
    private List<Integer> settledCharges;

    public Subscription save() throws PagarMeException {
        final Subscription saved = super.save(getClass());
        copy(saved);

        return saved;
    }

    public Subscription find(String id) throws PagarMeException {

        final PagarMeRequest request = new PagarMeRequest(HttpMethod.GET,
                String.format("/%s/%s", getClassName(), id));

        final Subscription other = JSONUtils.getAsObject((JsonObject) request.execute(), Subscription.class);
        copy(other);
        flush();

        return other;
    }

    public Collection<Subscription> findCollection(Integer totalPerPage, Integer page) throws PagarMeException {
        return JSONUtils.getAsList(super.paginate(totalPerPage, page), new TypeToken<Collection<Subscription>>() {
        }.getType());
    }

    public Subscription cancel() throws PagarMeException {
        validateId();

        final PagarMeRequest request = new PagarMeRequest(HttpMethod.POST,
                String.format("/%s/%s/cancel", getClassName(), getId()));

        final Subscription other = JSONUtils.getAsObject((JsonObject) request.execute(), Subscription.class);
        copy(other);
        flush();

        return other;
    }

    public Collection<Transaction> transactions(Integer count, Integer page) throws PagarMeException {
        validateId();

        final Transaction transaction = new Transaction();

        final PagarMeRequest request = new PagarMeRequest(HttpMethod.GET,
                String.format("/%s/%s/%s", getClassName(), getId(), transaction.getClassName()));
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("count", count);
            params.put("page", page);
            request.setParameters(params);
        return JSONUtils.getAsList((JsonArray) request.execute(), new TypeToken<Collection<Transaction>>() {
        }.getType());
    }
    
    public Collection<Transaction> transactions() throws PagarMeException{
        return transactions(10,1);
    }

    public Subscription refresh() throws PagarMeException {
        final Subscription other = JSONUtils.getAsObject(refreshModel(), Subscription.class);
        copy(other);
        flush();
        return other;
    }

    public Collection<Postback> postbacks() throws PagarMeException {
        validateId();

        final Postback postback = new Postback();

        final PagarMeRequest request = new PagarMeRequest(HttpMethod.GET,
                String.format("/%s/%s/%s", getClassName(), getId(), postback.getClassName()));

        return JSONUtils.getAsList((JsonArray) request.execute(), new TypeToken<Collection<Postback>>() {
        }.getType());
    }

    public Postback postbacks(final String postbackId) throws PagarMeException {
        validateId();

        final Postback postback = new Postback();

        final PagarMeRequest request = new PagarMeRequest(HttpMethod.GET,
                String.format("/%s/%s/%s/%s", getClassName(), getId(), postback.getClassName(), postbackId));

        return JSONUtils.getAsObject((JsonObject) request.execute(), Postback.class);
    }

    public Subscription settleCharges(Integer chargesToSettle) throws PagarMeException {
        validateId();
        final PagarMeRequest request = new PagarMeRequest(HttpMethod.POST,
                String.format("/%s/%s/settle_charge", getClassName(), getId()));
        if (chargesToSettle != null) {
            Map<String, Object> charges = new HashMap<String, Object>();
            charges.put("charges", chargesToSettle);
            request.setParameters(charges);
        }
        return JSONUtils.getAsObject((JsonObject) request.execute(), Subscription.class);
    }

    public Subscription settleCharges() throws PagarMeException {
        return settleCharges(null);
    }

    private void copy(Subscription other) {
        super.copy(other);
        this.plan = other.getPlan();
        this.currentTransaction = other.getCurrentTransaction();
        this.postbackUrl = other.getPostbackUrl();
        this.currentPeriodStart = other.getCurrentPeriodStart();
        this.currentPeriodEnd = other.getCurrentPeriodEnd();
        this.charges = other.getCharges();
        this.status = other.getStatus();
        this.metadata = other.getMetadata();
        this.customer = other.getCustomer();
        this.paymentMethod = other.getPaymentMethod();
        this.splitRules = other.getSplitRules();
        this.softDescriptor = other.getSoftDescriptor();
        this.phone = other.getPhone();
        this.address = other.getAddress();
        this.card = other.getCard();
        this.settledCharges = other.getSettledCharges();
    }

    public void setCreditCardSubscriptionWithCardHash(String planId, String cardHash, Customer customer) {
        this.planId = planId;
        this.cardHash = cardHash;
        this.customer = customer;
        this.paymentMethod = PaymentMethod.CREDIT_CARD;
    }

    public void setCreditCardSubscriptionWithCardId(String planId, String cardId, Customer customer) {
        this.planId = planId;
        this.cardId = cardId;
        this.customer = customer;
        this.paymentMethod = PaymentMethod.CREDIT_CARD;
    }

    public void setBoletoSubscription(String planId, Customer customer) {
        this.planId = planId;
        this.paymentMethod = PaymentMethod.BOLETO;
        this.customer = customer;
    }

    public void setRequiredUpdateParameters(String id) {
        setId(id);
    }

    @Deprecated
    public void setUpdatableParameters(String cardId, String cardHash, String planId) {
        this.planId = planId;
        this.cardId = cardId;
        this.cardHash = cardHash;
    }

    public void setPostbackUrl(String postbackUrl) {
        this.postbackUrl = postbackUrl;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public void setCardHash(String cardHash) {
        this.cardHash = cardHash;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public void setSplitRules(final Collection<SplitRule> splitRules) {
        this.splitRules = splitRules;
    }
    
    public void setSoftDescriptor(String softDescriptor) {
        this.softDescriptor = softDescriptor;
    }
    
    public void setMetadata(final Map<String, Object> metadata) {
        this.metadata = metadata;
    }

    public String getCardHash() {
        return cardHash;
    }

    public String getCardId() {
        return cardId;
    }

    public String getPlanId() {
        return planId;
    }

    public Plan getPlan() {
        return plan;
    }

    public Transaction getCurrentTransaction() {
        return currentTransaction;
    }

    public String getPostbackUrl() {
        return postbackUrl;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public Collection<SplitRule> getSplitRules() {
        return splitRules;
    }
    
    public String getSoftDescriptor() {
        return softDescriptor;
    }

    public DateTime getCurrentPeriodStart() {
        return currentPeriodStart;
    }

    public DateTime getCurrentPeriodEnd() {
        return currentPeriodEnd;
    }

    public Integer getCharges() {
        return charges;
    }

    public void setStatus(SubscriptionStatus status) {
        this.status = status;
    }
    public SubscriptionStatus getStatus() {
        return status;
    }

    public Phone getPhone() {
        return phone;
    }

    public Address getAddress() {
        return address;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Card getCard() {
        return card;
    }

    public Map<String, Object> getMetadata() {
        return metadata;
    }

    public List<Integer> getSettledCharges() {
        return settledCharges;
    }

}
