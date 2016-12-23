package me.pagar.util;

import org.joda.time.DateTime;

import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;

public interface DateTimeAdapter extends JsonDeserializer<DateTime>, JsonSerializer<DateTime>{

}
