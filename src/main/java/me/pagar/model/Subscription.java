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

import me.pagar.PaymentMethod;
import me.pagar.SubscriptionStatus;
import me.pagar.util.JSONUtils;

public class Subscription extends PagarMeModel<Integer> {

    @Expose(deserialize = false)
    @SerializedName("card_hash")
    private String cardHash;

    @Expose(deserialize = false)
    @SerializedName("card_id")
    private String cardId;

    @Expose(deserialize = false)
    @SerializedName("plan_id")
    private Integer planId;

    @Expose(serialize = false)
    private Plan plan;

    @Expose(serialize = false)
    @SerializedName("current_transaction")
    private Transaction currentTransaction;

    @Expose
    @SerializedName("postback_url")
    private String postbackUrl;

    @Expose
    @SerializedName("payment_method")
    private PaymentMethod paymentMethod;

    @Expose
    @SerializedName("current_period_start")
    private DateTime currentPeriodStart;

    @Expose
    @SerializedName("current_period_end")
    private DateTime currentPeriodEnd;

    @Expose(serialize = false)
    private Integer charges;

    @Expose(serialize = false)
    private SubscriptionStatus status;

    @Expose(serialize = false)
    private Phone phone;

    @Expose(serialize = false)
    private Address address;

    @Expose
    private Customer customer;

    @Expose(serialize = false)
    private Card card;

    @Expose(deserialize = false)
    @SerializedName("card_number")
    private String cardNumber;

    @Expose(deserialize = false)
    @SerializedName("card_holder_name")
    private String cardHolderName;

    @Expose(deserialize = false)
    @SerializedName("card_expiration_date")
    private String cardExpirationDate; // Format: MMYY

    @Expose
    private Map<String, Object> metadata;

    public Subscription save() throws PagarMeException {
        final Subscription saved = super.save(getClass());
        copy(saved);

        return saved;
    }

    public Subscription find(Integer id) throws PagarMeException {

        final PagarMeRequest request = new PagarMeRequest(HttpMethod.GET,
                String.format("/%s/%d", getClassName(), id));

        final Subscription other = JSONUtils.getAsObject((JsonObject) request.execute(), Subscription.class);
        copy(other);
        flush();

        return other;
    }

    public Collection<Subscription> findCollection() throws PagarMeException {
        return findCollection(100, 0);
    }

    public Collection<Subscription> findCollection(Integer totalPerPage, Integer page) throws PagarMeException {
        return JSONUtils.getAsList(super.paginate(totalPerPage, page), new TypeToken<Collection<Subscription>>() {
        }.getType());
    }

    public Subscription cancel() throws PagarMeException {
        validateId();

        final PagarMeRequest request = new PagarMeRequest(HttpMethod.POST,
                String.format("/%s/%d/cancel", getClassName(), getId()));

        final Subscription other = JSONUtils.getAsObject((JsonObject) request.execute(), Subscription.class);
        copy(other);
        flush();

        return other;
    }

    public Collection<Transaction> transactions() throws PagarMeException {
        validateId();

        final Transaction transaction = new Transaction();

        final PagarMeRequest request = new PagarMeRequest(HttpMethod.GET,
                String.format("/%s/%s/%s", getClassName(), getId(), transaction.getClassName()));

        return JSONUtils.getAsList((JsonArray) request.execute(), new TypeToken<Collection<Postback>>() {
        }.getType());
    }

    public Subscription refresh() throws PagarMeException {
        final Subscription other = JSONUtils.getAsObject(refreshModel(), Subscription.class);
        copy(other);
        flush();
        return other;
    }

    private void copy(Subscription other) {
        setId(other.getId());
        setCardHash(other.getCardHash());
        setCardId(other.getCardId());
        setPlanId(other.getPlanId());
        setPlan(other.getPlan());
        setCurrentTransaction(other.getCurrentTransaction());
        setPostbackUrl(other.getPostbackUrl());
        setPaymentMethod(other.getPaymentMethod());
        setCurrentPeriodStart(other.getCurrentPeriodStart());
        setCurrentPeriodEnd(other.getCurrentPeriodEnd());
        setCharges(other.getCharges());
        setStatus(other.getStatus());
        setPhone(other.getPhone());
        setAddress(other.getAddress());
        setCustomer(other.getCustomer());
        setCard(other.getCard());
        setCardNumber(other.getCardNumber());
        setCardHolderName(other.getCardHolderName());
        setMetadata(other.getMetadata());
    }

    public String getCardHash() {
        return cardHash;
    }

    public String getCardId() {
        return cardId;
    }

    public Integer getPlanId() {
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

    public DateTime getCurrentPeriodStart() {
        return currentPeriodStart;
    }

    public DateTime getCurrentPeriodEnd() {
        return currentPeriodEnd;
    }

    public Integer getCharges() {
        return charges;
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

    public String getCardNumber() {
        return cardNumber;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public String getCardExpirationDate() {
        return cardExpirationDate;
    }

    public Map<String, Object> getMetadata() {
        return metadata;
    }

    public void setCardHash(String cardHash) {
        this.cardHash = cardHash;
        addUnsavedProperty("cardHash");
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
        addUnsavedProperty("cardId");
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
        addUnsavedProperty("planId");
    }

    public void setPostbackUrl(String postbackUrl) {
        this.postbackUrl = postbackUrl;
        addUnsavedProperty("postbackUrl");
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
        addUnsavedProperty("paymentMethod");
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
        addUnsavedProperty("customer");
    }

    public void setMetadata(Map<String, Object> metadata) {
        this.metadata = metadata;
        addUnsavedProperty("metadata");
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
        addUnsavedProperty("plan");
    }

    public void setCurrentTransaction(Transaction currentTransaction) {
        this.currentTransaction = currentTransaction;
        addUnsavedProperty("currentTransaction");
    }

    public void setCurrentPeriodStart(DateTime currentPeriodStart) {
        this.currentPeriodStart = currentPeriodStart;
        addUnsavedProperty("currentPeriodStart");
    }

    public void setCurrentPeriodEnd(DateTime currentPeriodEnd) {
        this.currentPeriodEnd = currentPeriodEnd;
        addUnsavedProperty("currentPeriodEnd");
    }

    public void setCharges(Integer charges) {
        this.charges = charges;
        addUnsavedProperty("charges");
    }

    public void setStatus(SubscriptionStatus status) {
        this.status = status;
        addUnsavedProperty("status");
    }

    public void setPhone(Phone phone) {
        this.phone = phone;
        addUnsavedProperty("phone");
    }

    public void setAddress(Address address) {
        this.address = address;
        addUnsavedProperty("address");
    }

    public void setCard(Card card) {
        this.card = card;
        addUnsavedProperty("card");
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
        addUnsavedProperty("cardNumber");
    }

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
        addUnsavedProperty("cardHolderName");
    }

    public void setCardExpirationDate(String cardExpirationDate) {
        this.cardExpirationDate = cardExpirationDate;
        addUnsavedProperty("cardExpirationDate");
    }
}
