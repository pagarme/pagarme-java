package me.pagar.util;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

public class JSONUtils {

    private DateTimeAdapter customDateTimeAdapter;

    public final Gson getInterpreter() {
        return getCustomGsonBuilder().create();
    }

    public <T> T getAsObject(JsonObject json, Class<T> clazz) {
        return getCustomGsonBuilder().create().fromJson(json, clazz);
    }

    public <T> Collection<T> getAsList(JsonArray json, Type listType) {
        return getCustomGsonBuilder().create().fromJson(json, listType);
    }

    public String getAsJson(Object object) {
        return getCustomGsonBuilder().create().toJson(object);
    }

    public Map<String, Object> objectToMap(Object object) {
        final String json = getCustomGsonBuilder().create().toJson(object);
        return getCustomGsonBuilder().create().fromJson(json, new TypeToken<HashMap<String, Object>>() {
        }.getType());
    }

    public JsonObject treeToJson(Object object) {
        return getCustomGsonBuilder().create().toJsonTree(object).getAsJsonObject();
    }

    public JSONUtils setDateTimeFormat(DateTimeAdapter dateTimeAdapter){
        customDateTimeAdapter = dateTimeAdapter;
        return this;
    }

    private GsonBuilder getCustomGsonBuilder(){
        GsonBuilder customBuilder =  getDefaultGsonBuilder();
        if(customDateTimeAdapter != null){
            customBuilder.registerTypeAdapter(DateTime.class, customDateTimeAdapter);
        }
        return customBuilder;
    }
    
    private GsonBuilder getDefaultGsonBuilder(){
        return new GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .registerTypeAdapter(DateTime.class, new DateTimeIsoDateAdapter())
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .setExclusionStrategies(new ExclusionStrategy(){
                public boolean shouldSkipClass(Class<?> clazz) {
                    return false;
                }
                public boolean shouldSkipField(FieldAttributes fieldAttrs) {
                    return fieldAttrs.equals(null);
                }
            });
    }
}
