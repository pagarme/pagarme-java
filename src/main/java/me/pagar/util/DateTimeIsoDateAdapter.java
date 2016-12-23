package me.pagar.util;

import java.lang.reflect.Type;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import com.google.common.base.Strings;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;

public class DateTimeIsoDateAdapter  implements DateTimeAdapter {

    private final DateTimeFormatter formatter;

    public DateTimeIsoDateAdapter() {
        this.formatter = ISODateTimeFormat.dateTime();
    }

    public DateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        final String dateTime = json.getAsString();
        try {
            return Strings.isNullOrEmpty(dateTime) ? null : formatter.parseDateTime(dateTime);
        } catch (Exception e) {
            return null;
        }
    }

    public JsonElement serialize(DateTime src, Type typeOfSrc, JsonSerializationContext context) {
        return src == null ? null : new JsonPrimitive(formatter.print(src));
    }

}
