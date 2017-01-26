package me.pagar.util;

import com.google.common.base.Strings;

import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;

import me.pagar.format.CommonFormats;

import org.joda.time.DateTime;

public interface DateTimeAdapter extends JsonDeserializer<DateTime>, JsonSerializer<DateTime>{
}
