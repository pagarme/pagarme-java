package me.pagar.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.HttpMethod;

import org.joda.time.DateTime;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import me.pagar.model.BulkAnticipation.Timeframe;
import me.pagar.util.DateTimeTimestampAdapter;
import me.pagar.util.JSONUtils;
import me.pagar.RecipientStatus;
import me.pagar.AutoAnticipationType;

public class Recipient  extends PagarMeModel<String> {

    @Expose
    @SerializedName(value = "status")
    private RecipientStatus status;
    
    @Expose
    @SerializedName(value = "automatic_anticipation_enabled")
    private Boolean automaticAnticipationEnabled;

    @Expose
    @SerializedName(value = "transfer_enabled")
    private Boolean transferEnabled;

    @Expose
    @SerializedName(value = "anticipatable_volume_percentage")
    private Integer anticipatableVolumePercentage;

    @Expose
    @SerializedName(value = "automatic_anticipation_type")
    private AutoAnticipationType automaticAnticipationType;

    @Expose
    @SerializedName(value = "automatic_anticipation_days")
    private String automaticAnticipationDays;

    @Expose
    @SerializedName(value = "automatic_anticipation_1025_delay")
    private Integer automaticAnticipationDelay;

    @Expose
    @SerializedName(value = "transfer_day")
    private Integer transferDay;

    @Expose(deserialize = false)
    @SerializedName(value = "bank_account_id")
    private Integer bankAccountId;

    @Expose
    @SerializedName(value = "bank_account")
    private BankAccount bankAccount;

    @Expose
    @SerializedName(value = "transfer_interval")
    private TransferInterval transferInterval;

    @Expose(serialize = false)
    private DateTime lastTransfer;

    @Expose(serialize = false)
    @SerializedName("date_updated")
    private DateTime updatedAt;

    public Boolean isAutomaticAnticipationEnabled() {
        return automaticAnticipationEnabled;
    }

    public Boolean isTransferEnabled() {
        return transferEnabled;
    }

    public RecipientStatus getStatus() {
        return status;
    }

    public Integer getAnticipatableVolumePercentage() {
        return anticipatableVolumePercentage;
    }

    public AutoAnticipationType getAutomaticAnticipationType() {
        return automaticAnticipationType;
    }

    public String getAutomaticAnticipationDays() {
        return automaticAnticipationDays;
    }

    public Integer getAutomaticAnticipationDelay() {
        return automaticAnticipationDelay;
    }

    public Integer getTransferDay() {
        return transferDay;
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public TransferInterval getTransferInterval() {
        return transferInterval;
    }

    public DateTime getLastTransfer() {
        return lastTransfer;
    }

    public DateTime getUpdatedAt() {
        return updatedAt;
    }

    public Boolean getAutomaticAnticipationEnabled() {
        return automaticAnticipationEnabled;
    }
    
    public void setStatus(RecipientStatus status) {
        this.status = status;
        addUnsavedProperty("status");
    }

    public void setAutomaticAnticipationType(AutoAnticipationType automaticAnticipationType) {
        this.automaticAnticipationType = automaticAnticipationType;
        addUnsavedProperty("automaticAnticipationType");
    }

    public void setAutomaticAnticipationDays(String automaticAnticipationDays) {
        this.automaticAnticipationDays = automaticAnticipationDays;
        addUnsavedProperty("automaticAnticipationDays");
    }

    public void setAutomaticAnticipationDelay(Integer automaticAnticipationDelay) {
        this.automaticAnticipationDelay = automaticAnticipationDelay;
        addUnsavedProperty("automaticAnticipationDelay");
    }

    public void setAutomaticAnticipationEnabled(Boolean automaticAnticipationEnabled) {
        this.automaticAnticipationEnabled = automaticAnticipationEnabled;
        addUnsavedProperty("automatic_anticipation_enabled");
    }

    public void setTransferEnabled(Boolean transferEnabled) {
        this.transferEnabled = transferEnabled;
        addUnsavedProperty("transferEnabled");
    }

    public void setTransferDay(Integer transferDay) {
        this.transferDay = transferDay;
        addUnsavedProperty("transferDay");
    }

    public void setBankAccountId(Integer bankAccountId) {
        this.bankAccountId = bankAccountId;
        addUnsavedProperty("bankAccountId");
    }

    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
        addUnsavedProperty("bankAccount");
    }

    public void setTransferInterval(TransferInterval transferInterval) {
        this.transferInterval = transferInterval;
        addUnsavedProperty("transferInterval");
    }

    public void setAnticipatableVolumePercentage(Integer anticipatableVolumePercentage) {
        this.anticipatableVolumePercentage = anticipatableVolumePercentage;
    }

    public Recipient save() throws PagarMeException {
        final Recipient saved = super.save(getClass());
        copy(saved);

        return saved;
    }

    public Recipient find(String id) throws PagarMeException {

        final PagarMeRequest request = new PagarMeRequest(HttpMethod.GET,
                String.format("/%s/%s", getClassName(), id));

        final Recipient other = JSONUtils.getAsObject((JsonObject) request.execute(), Recipient.class);
        copy(other);
        flush();

        return other;
    }

    public Collection<Recipient> findCollection(int totalPerPage, int page) throws PagarMeException {
        return JSONUtils.getAsList(super.paginate(totalPerPage, page), new TypeToken<Collection<Recipient>>() {
        }.getType());
    }

    public Balance balance() throws PagarMeException {
        final PagarMeRequest request = new PagarMeRequest(HttpMethod.GET,
                String.format("/%s/%s/%s", getClassName(), getId(), Balance.class.getSimpleName().toLowerCase()));

        return JSONUtils.getAsObject((JsonObject) request.execute(), Balance.class);
    }

    public Limit getMaxAnticipationLimit(DateTime paymentDate, Timeframe timeframe) throws PagarMeException{
        BulkAnticipationLimits limits = getAnticipationLimits(paymentDate, timeframe);
        Limit max = limits.getMaximum();
        return max;
    }

    public Limit getMinAnticipationLimit(DateTime paymentDate, Timeframe timeframe) throws PagarMeException{
        BulkAnticipationLimits limits = getAnticipationLimits(paymentDate, timeframe);
        Limit min = limits.getMinimum();
        return min;
    }

    private BulkAnticipationLimits getAnticipationLimits(DateTime paymentDate, Timeframe timeframe) throws PagarMeException{
        validateId();
        String path = String.format("/%s/%s/bulk_anticipations/limits", getClassName(), getId());
        final PagarMeRequest request = new PagarMeRequest(HttpMethod.GET, path);
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("payment_date", paymentDate.getMillis());
        parameters.put("timeframe", timeframe.name().toLowerCase());
        request.setParameters(parameters);
        JsonObject response = request.execute();
        BulkAnticipationLimits limits = JSONUtils.getAsObject(response, BulkAnticipationLimits.class);
        return limits;
    }

    public BulkAnticipation anticipate(BulkAnticipation anticipation) throws PagarMeException{
        validateId();
        String path = String.format("/%s/%s/%s", getClassName(), getId(), anticipation.getClassName());
        final PagarMeRequest request = new PagarMeRequest(HttpMethod.POST, path);
        Map<String, Object> parameters = JSONUtils.objectToMap(anticipation, new DateTimeTimestampAdapter());
        request.setParameters(parameters);
        JsonObject response = request.execute();
        BulkAnticipation newAnticipation = JSONUtils.getAsObject(response, BulkAnticipation.class);
        return newAnticipation;
    }

    public BulkAnticipation cancelAnticipation(BulkAnticipation anticipation) throws PagarMeException{
        validateId();
        String path = String.format("/%s/%s/%s/%s/cancel", getClassName(), getId(), anticipation.getClassName(), anticipation.getId());
        final PagarMeRequest request = new PagarMeRequest(HttpMethod.POST, path);
        JsonObject response = request.execute();
        BulkAnticipation canceledAnticipation = JSONUtils.getAsObject(response, BulkAnticipation.class);
        return canceledAnticipation;
    }

    public Collection<BulkAnticipation> findAnticipations(int count, int page) throws PagarMeException{
        validateId();
        String path = String.format("/%s/%s/bulk_anticipations", getClassName(), getId());
        final PagarMeRequest request = new PagarMeRequest(HttpMethod.GET, path);
        request.getParameters().put("count", count);
        request.getParameters().put("page", page);
        JsonArray response = request.<JsonArray>execute();
        Collection<BulkAnticipation> anticipations = JSONUtils.getAsList(response, new TypeToken<Collection<BulkAnticipation>>(){}.getType());
        return anticipations;
    }

    public KYCLink generateKYCLink() throws PagarMeException {
        validateId();
        String path = String.format("recipients/%s/kyc_link", getId());
        final PagarMeRequest request = new PagarMeRequest(HttpMethod.POST, path);
        JsonObject response = request.execute();
        KYCLink kycLink = JSONUtils.getAsObject(response, KYCLink.class);
        return kycLink;
    }

    public Recipient refresh() throws PagarMeException {
        final Recipient other = JSONUtils.getAsObject(refreshModel(), Recipient.class);
        copy(other);
        flush();
        return other;
    }

    private void copy(Recipient other) {
        super.copy(other);
        this.automaticAnticipationEnabled = other.automaticAnticipationEnabled;
        this.transferEnabled = other.transferEnabled;
        this.transferDay = other.transferDay;
        this.anticipatableVolumePercentage = other.anticipatableVolumePercentage;
        this.lastTransfer = other.lastTransfer;
        this.transferInterval = other.transferInterval;
        this.updatedAt = other.updatedAt;
        this.bankAccount = other.bankAccount;
        this.status = other.status;
        this.automaticAnticipationType = other.automaticAnticipationType;
        this.automaticAnticipationDays = other.automaticAnticipationDays;
        this.automaticAnticipationDelay = other.automaticAnticipationDelay;
    }

    public enum TransferInterval {
        @SerializedName("daily")
        DAILY,

        @SerializedName("weekly")
        WEEKLY,

        @SerializedName("monthly")
        MONTHLY
    }
}
