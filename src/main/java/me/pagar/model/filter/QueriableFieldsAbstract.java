package me.pagar.model.filter;

import java.util.HashMap;
import java.util.Map;

public abstract class QueriableFieldsAbstract implements QueriableFields{

    private Map<String, Object> filter = new HashMap<String, Object>();

    protected void setGreatedThan(String key, String value){
        filter.put(key, ">=" + value);
    }

    protected void setLessThan(String key, String value){
        filter.put(key, "<=" + value);
    }

    protected void setEquals(String key, String value){
        filter.put(key, value);
    }

    protected void setNotEquals(String key, String value){
        filter.put(key, "!=" + value);
    }

    public Map<String, Object> toMap() {
        return filter;
    }

}
