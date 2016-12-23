package me.pagar.util;

import java.lang.reflect.Type;

import org.joda.time.DateTime;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;

public class DateTimeTimestampAdapter implements DateTimeAdapter{

    public JsonElement serialize(DateTime src, Type typeOfSrc, JsonSerializationContext context) {
        return src == null ? null : new JsonPrimitive(src.getMillis());
    }

    public DateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        String timestamp = json.getAsString();
        return new DateTime(Long.valueOf(timestamp));
    }

}
