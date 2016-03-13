package com.imxiaomai.convenience.store.scan.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum ShopTypeEnum {

	SELFOP(10, "自营"),
	JOINOP(20, "加盟");
	

	int type;

	String name;

	ShopTypeEnum(int type, String name) {
		this.type = type;
		this.name = name;
	}
	
	public static String getName(int type) {
		for (ShopTypeEnum ele : ShopTypeEnum.values()) {
		    if (ele.getType() == type)
		    	return ele.getName();
		}
		return null;
	}
	
	public static List<Map<String,Object>> getList(){
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		for (ShopTypeEnum ele : ShopTypeEnum.values()) {
		   Map<String,Object> map = new HashMap<String,Object>();
		   map.put("key", ele.getType());
		   map.put("value", ele.getName());
		   list.add(map);
		}
		return list;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
