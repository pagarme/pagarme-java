package me.pagar.util;

import com.google.common.base.Strings;
import com.google.gson.*;

import me.pagar.format.CommonFormats;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormat;

import java.lang.reflect.Type;

public class LocalDateAdapter implements JsonDeserializer<LocalDate>, JsonSerializer<LocalDate> {

    private final DateTimeFormatter formatter;

    public LocalDateAdapter() {
        this.formatter = DateTimeFormat.forPattern(CommonFormats.DATE_TIME);
    }

    public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        final String dateTime = json.getAsString();
        return Strings.isNullOrEmpty(dateTime) ? null : formatter.parseLocalDate(dateTime);
    }

    public JsonElement serialize(LocalDate src, Type typeOfSrc, JsonSerializationContext context) {
        return src == null ? null : new JsonPrimitive(formatter.print(src));
    }

}
