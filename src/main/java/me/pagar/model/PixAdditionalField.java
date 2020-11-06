package me.pagar.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PixAdditionalField {
    @Expose
    private String name;

    @Expose
    private String value;

    public PixAdditionalField() {
        super();
    }

    public PixAdditionalField(final String name, final String value) {
        this();
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
