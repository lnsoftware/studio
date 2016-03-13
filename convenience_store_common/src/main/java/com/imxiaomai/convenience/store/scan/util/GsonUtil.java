package com.imxiaomai.convenience.store.scan.util;

import java.text.DateFormat;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonUtil {
	public static Gson getInstance() {

		GsonBuilder gb = new GsonBuilder();
		gb.registerTypeAdapter(Date.class, new DateSerializerUtil())
				.setDateFormat(DateFormat.LONG);
		return gb.create();
	}
}
