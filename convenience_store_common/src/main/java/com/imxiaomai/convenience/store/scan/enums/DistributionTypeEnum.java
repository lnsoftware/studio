package com.imxiaomai.convenience.store.scan.enums;

/**
 * @author hubenquan
 *配送类型。
 */
public enum DistributionTypeEnum {
	/**
	 * 配送。
	 */
	DELIVERY(10, "配送"),
	/**
	 * 自提。
	 */
	PICKUP(20, "自提");

	int type;

	String name;

	DistributionTypeEnum(int type, String name) {
		this.type = type;
		this.name = name;
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

	public static String getName(int type) {
		for (DistributionTypeEnum ele : DistributionTypeEnum.values()) {
		    if (ele.getType() == type)
		    	return ele.getName();
		}
		return null;
	}

}
