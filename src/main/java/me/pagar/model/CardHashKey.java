package me.pagar.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CardHashKey extends PagarMeModel<Integer>{

    @Expose(serialize = false)
    private String ip;

    @Expose(serialize = false)
    @SerializedName("public_key")
    private String publicKey;

    public String getPublicKey() {
        return this.publicKey;
    }

    public String getIp() {
        return this.ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    @Override
    public void setId(Integer id) {
        throw new UnsupportedOperationException("Not allowed.");
    }

    @Override
    public void setClassName(String className) {
        throw new UnsupportedOperationException("Not allowed.");
    }
}
