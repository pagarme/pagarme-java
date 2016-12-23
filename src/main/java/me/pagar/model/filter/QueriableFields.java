package me.pagar.model.filter;

import java.util.Map;

import me.pagar.model.PagarmeRelatable;

public interface QueriableFields extends PagarmeRelatable {

    public Map<String, Object> toMap();
}
