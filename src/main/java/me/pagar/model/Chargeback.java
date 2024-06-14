package me.pagar.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import java.util.Collection;
import java.util.Date;
import me.pagar.util.JSONUtils;

/**
 *
 * @author ta-ma
 */
public class Chargeback extends PagarMeModel<String> {

    @Expose
    private String status;
    @Expose
    private int amount;
    @Expose
    private String cardBrand;
    @Expose
    @SerializedName("transaction_id")
    private String tramsactionId;
    @Expose
    private String installment;
    @Expose
    private Date createdAt;
    @Expose
    private Date updatedAt;
    @Expose
    private Date accrualDate;
    @Expose
    private String reasonCode;
    @Expose 
    private int cycle;

    public String getStatus() {
        return status;
    }

    public String getTramsactionId() {
        return tramsactionId;
    }

    public String getInstallment() {
        return installment;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public Date getAccrualDate() {
        return accrualDate;
    }

    public String getReasonCode() {
        return reasonCode;
    }

    public Collection<Chargeback> getAll(Integer totalPerPage, Integer page) throws PagarMeException {
        return JSONUtils.getAsList(super.paginate(totalPerPage, page), new TypeToken<Collection<Chargeback>>() {
        }.getType());
    }

    public Collection<Chargeback> get() throws PagarMeException {
        return getAll(10, 1);
    }
}
