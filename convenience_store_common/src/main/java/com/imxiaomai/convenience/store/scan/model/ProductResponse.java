package com.imxiaomai.convenience.store.scan.model;

import java.io.Serializable;
import java.util.List;

/**
 * 从采销系统同步到的商品信息DTO对象
 * @author ligang.xiaomai
 *
 */
public class ProductResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**sku编码，对应唯一商品*/
	private String sku;
	/**商品名称*/
	private String name;
	/**商品有效期*/
	private String period;
	/**商品单位*/
	private String unit;
	/**商品规格*/
	private String specification;
    /**销售规格*/
    private String saleSize;
	/**商品和国标码的一对多关系*/
	private List<String> gbCodeList;

	public String getSku() {
		return sku;
	}

    public String getSaleSize() {
        return saleSize;
    }

    public void setSaleSize(String saleSize) {
        this.saleSize = saleSize;
    }

    public void setSku(String sku) {
		this.sku = sku;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getSpecification() {
		return specification;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
	}

	public List<String> getGbCodeList() {
		return gbCodeList;
	}

	public void setGbCodeList(List<String> gbCodeList) {
		this.gbCodeList = gbCodeList;
	}
	
	


}
