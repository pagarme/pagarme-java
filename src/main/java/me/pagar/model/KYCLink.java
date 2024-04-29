package me.pagar.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.joda.time.DateTime;

public class KYCLink {

    @Expose(serialize = false)
    private String base64;

    @Expose(serialize = false)
    private String url;

    @Expose(serialize = false)
    @SerializedName("expiration_date")
    private DateTime expirationDate;

    public String getBase64() {
        return base64;
    }

    public String getUrl() {
        return url;
    }

    public DateTime getExpirationDate() {
        return expirationDate;
    }
}
