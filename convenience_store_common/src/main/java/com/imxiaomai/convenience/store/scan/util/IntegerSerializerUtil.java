package com.imxiaomai.convenience.store.scan.util;

import java.lang.reflect.Type;
import java.util.Date;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class IntegerSerializerUtil implements JsonSerializer<Integer>, JsonDeserializer<Integer> {

	@Override
	public JsonElement serialize(Integer integer, Type type, JsonSerializationContext context) {
		if (integer == null) {
			integer = 0;
		}
		return new JsonPrimitive(integer);
	}

	@Override
	public Integer deserialize(JsonElement element, Type type, JsonDeserializationContext context)
			throws JsonParseException {
		if(element == null) {
			return 0;
		}
		return element.getAsJsonPrimitive().getAsInt();
	}

}