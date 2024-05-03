package me.pagar.model;

import com.google.gson.annotations.Expose;

public class RegisterPhone extends PagarMeModel<String>  {
    @Expose
    private String type;

    @Expose
    private String ddd;

    @Expose
    private String number;

    public String getType() {
        return type;
    }

    public String getDdd() {
        return ddd;
    }

    public String getNumber() {
        return number;
    }
}
