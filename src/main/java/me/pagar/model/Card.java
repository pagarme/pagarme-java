package me.pagar.model;

import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import me.pagar.util.JSONUtils;
import org.joda.time.DateTime;

import javax.ws.rs.HttpMethod;

public class Card extends PagarMeModel<String> {

    @Expose(deserialize = false)
    @SerializedName("card_hash")
    private String hash;

    @Expose(serialize = false)
    private Brand brand;

    @Expose
    @SerializedName("holder_name")
    private String holderName;

    @Expose(deserialize = false)
    @SerializedName("card_number")
    private String number;

    @Expose(serialize = false)
    @SerializedName("first_digits")
    private String firstDigits;

    @Expose(serialize = false)
    @SerializedName("last_digits")
    private String lastDigits;

    @Expose(serialize = false)
    private String fingerprint;

    @Expose(serialize = false)
    private String country;

    @Expose(deserialize = false)
    @SerializedName("customer_id")
    private Integer customerId;

    @Expose(serialize = false)
    private Boolean valid;

    @Expose(deserialize = false)
    @SerializedName("card_expiration_date")
    private String expiresAt;

    @Expose(serialize = false)
    @SerializedName("date_updated")
    private DateTime updatedAt;

    @Expose(serialize = false)
    @SerializedName("cvv")
    private Integer cvv;

    @Expose(serialize = false)
    private Customer customer;

    public Brand getBrand() {
        return brand;
    }

    public String getHolderName() {
        return holderName;
    }

    public String getFirstDigits() {
        return firstDigits;
    }

    public String getLastDigits() {
        return lastDigits;
    }

    public Integer getCvv() {
        return cvv;
    }

    public void setCvv(Integer cvv) {
        this.cvv = cvv;
    }

    public String getFingerprint() {
        return fingerprint;
    }

    public String getCountry() {
        return country;
    }

    public Boolean getValid() {
        return valid;
    }

    public DateTime getUpdatedAt() {
        return updatedAt;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setHash(String hash) {
        this.hash = hash;
        addUnsavedProperty("hash");
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
        addUnsavedProperty("holderName");
    }

    public void setNumber(String number) {
        this.number = number;
        addUnsavedProperty("number");
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
        addUnsavedProperty("customerId");
    }

    public void setExpiresAt(String expiresAt) {
        this.expiresAt = expiresAt;
        addUnsavedProperty("expiresAt");
    }

    public Card save() throws PagarMeException {
        final Card saved = super.save(getClass());
        copy(saved);

        return saved;
    }

    public Card find(String id) throws PagarMeException {

        final PagarMeRequest request = new PagarMeRequest(HttpMethod.GET,
                String.format("/%s/%s", getClassName(), id));

        final Card other = new JSONUtils().getAsObject((JsonObject) request.execute(), Card.class);
        copy(other);
        flush();

        return other;
    }

    public Card refresh() throws PagarMeException {
        final Card other = new JSONUtils().getAsObject(refreshModel(), Card.class);
        copy(other);
        flush();
        return other;
    }

    private void copy(Card other) {
        super.copy(other);
        this.updatedAt = other.updatedAt;
        this.brand = other.brand;
        this.holderName = other.holderName;
        this.firstDigits = other.firstDigits;
        this.lastDigits = other.lastDigits;
        this.fingerprint = other.fingerprint;
        this.country = other.country;
        this.valid = other.valid;
    }

    public enum Brand {

        @SerializedName("amex")
        AMEX,

        @SerializedName("aura")
        AURA,

        @SerializedName("discover")
        DISCOVER,

        @SerializedName("elo")
        ELO,

        @SerializedName("hipercard")
        HIPERCARD,

        @SerializedName("jcb")
        JCB,

        @SerializedName("visa")
        VISA,

        @SerializedName("mastercard")
        MASTERCARD

    }
}
