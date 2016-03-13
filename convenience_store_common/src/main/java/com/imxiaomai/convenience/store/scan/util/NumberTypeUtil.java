package com.imxiaomai.convenience.store.scan.util;

import java.util.regex.Pattern;

import com.imxiaomai.convenience.store.scan.StoreApplication;
import com.imxiaomai.convenience.store.scan.manager.GbCodeManager;

public class NumberTypeUtil {
	/** 采购单编号 */
	private static final String PURCHASE_ORDER_REG = "^[P]{1}[O]{1}";
	/** 商品国标码 */
	private static final String GB_CODE_REG = "^\\s*";
	/** SKU */
	private static final String SKU_REG = "^\\d{6}+$";
	/** 销售订单编号 */
	private static final String SALAS_ORDER_REG = "^XM(001|002|003)\\d{8}$";
	/** 销售线下订单编号 */
	private static final String SALAS_ORDER_OFF_REG = "^\\s*";
    /**销退上架*/
	private static final String XT_ON_SHELF_REG = "^[X]{1}[T]{1}";
	/** 盘点任务编号 */
	private static final String INVENTORY_CODE_REG = "^[P]{1}[R]{1}";
	
	private static final String PURCHASE_ORDER_PRE = "PO";
	private static final String SALAS_ORDER_PRE = "XM";
	private static final String INVENTORY_CODE_PRE = "PR";

	/**
	 * 判断是不是采购单编号
	 * 
	 * @param num
	 * @return
	 */
	public static boolean isPurchaseNum(String num) {
		if (StringUtil.empty(num)) {
			return false;
		}
		Pattern pattern = Pattern.compile(PURCHASE_ORDER_REG);
		return pattern.matcher(num).find();
	}

	/**
	 * 判断是不是国标码或SKU
	 * 
	 * @param num
	 * @return
	 */
	public static boolean isGbCodeOrSku(String num) {
		if (StringUtil.empty(num)) {
			return false;
		}
		Pattern pattern = Pattern.compile(GB_CODE_REG);
		Pattern pattern_SKU = Pattern.compile(SKU_REG);
		return pattern_SKU.matcher(num).find() || pattern.matcher(num).find();
	}
	/**
	 * 判断是不是国标码
	 *
	 * @param num
	 * @return 如果num有对应的sku就认为是国标码
	 */
	public static boolean isGbCode(String num) {
		if (StringUtil.empty(num)) {
			return false;
		}
		Pattern pattern = Pattern.compile(GB_CODE_REG);
		return pattern.matcher(num).find();
      //return GbCodeManager.getInstance(WmsApplication.getContext()).isExistSku(num);
	}
	/**
	 * 判断是不是SKU
	 * 
	 * @param num
	 * @return
	 */
	public static boolean isSku(String num) {
		if (StringUtil.empty(num)) {
			return false;
		}
		Pattern pattern = Pattern.compile(SKU_REG);
		return pattern.matcher(num).find();
	}

	/**
	 * 判断是不是销售单编号
	 * 
	 * @param num
	 * @return
	 */
	public static boolean isSalasOrder(String num) {
		if (StringUtil.empty(num)) {
			return false;
		}
		Pattern pattern = Pattern.compile(SALAS_ORDER_REG);
		return pattern.matcher(num).find();
	}
	
	/**
	 * 判断是不是线下销售单编号
	 * 
	 * @param num
	 * @return
	 */
	public static boolean isSalasOrderOff(String num) {
		if (StringUtil.empty(num)) {
			return false;
		}
		Pattern pattern = Pattern.compile(SALAS_ORDER_OFF_REG);
		return pattern.matcher(num).find();
	}
	
    /**
     * 判断是不是销退上架单编号
     *
     * @param num
     * @return
     */
    public static boolean isXTOnShelfNo(String num) {
        if (StringUtil.empty(num)) {
            return false;
        }
        Pattern pattern = Pattern.compile(XT_ON_SHELF_REG);
        return pattern.matcher(num).find();
    }
	
	/**
	 * 判断是不是盘点任务编号
	 * 
	 * @param num
	 * @return
	 */
	public static boolean isInventoryNo(String num) {
		if (StringUtil.empty(num)) {
			return false;
		}
		Pattern pattern = Pattern.compile(INVENTORY_CODE_REG);
		return pattern.matcher(num).find();
	}
	
	/**
	 * 用于自动填充手工输入采购单号无前缀的情况
	 * 
	 * @param num
	 * @return
	 */
	public static String autoAddPo(String num) {
		if (StringUtil.notEmpty(num)) {
			String po = "";
			if (num.startsWith(PURCHASE_ORDER_PRE)) {
				po = num;
			} else if ("po".equalsIgnoreCase(getPreTwoChar(num))) {
				po = num.replace(getPreTwoChar(num), PURCHASE_ORDER_PRE);
			} else {
				po = PURCHASE_ORDER_PRE + num.trim();
			}
			if (isPurchaseNum(po)) {
				return po;
			}
		}
		return null;
	}

	/**
	 * 用于自动填充手工输入销售单编号无前缀的情况
	 * 
	 * @param num
	 * @return
	 */
	public static String autoAddXM(String num) {
		if (StringUtil.notEmpty(num)) {
			String xm = "";
			if (num.startsWith(SALAS_ORDER_PRE)) {
				xm = num;
			} else if ("xm".equalsIgnoreCase(getPreTwoChar(num))) {
				xm = num.replace(getPreTwoChar(num), SALAS_ORDER_PRE);
			} else {
				xm = SALAS_ORDER_PRE + num.trim();
			}
			if (isSalasOrder(xm)) {
				return xm;
			}
		}
		return null;
	}

    /**
     * 用于自动填充手工输入销退上架编号无前缀的情况
     *
     * @param num
     * @return
     */
    public static String autoAddXT(String num) {
        if (StringUtil.notEmpty(num)) {
            String xt = "";
            if (num.startsWith(XT_ON_SHELF_REG)) {
                xt = num;
            } else if ("xt".equalsIgnoreCase(getPreTwoChar(num))) {
                xt = num.replace(getPreTwoChar(num), XT_ON_SHELF_REG);
            } else {
                xt = XT_ON_SHELF_REG + num.trim();
            }
            if (isXTOnShelfNo(xt)) {
                return xt;
            }
        }
        return null;
    }

    /**
	 * 用于自动填充手工输入盘点任务无前缀的情况
	 * @param num
	 * @return
	 */
    public static String autoAddPR(String num) {
        if (StringUtil.notEmpty(num)) {
            String pr = "";
            if (num.startsWith(INVENTORY_CODE_PRE)) {
            	pr = num;
            } else if ("pr".equalsIgnoreCase(getPreTwoChar(num))) {
            	pr = num.replace(getPreTwoChar(num), INVENTORY_CODE_PRE);
            } else {
            	pr = INVENTORY_CODE_PRE + num.trim();
            }
            if (isInventoryNo(pr)) {
                return pr;
            }
        }
        return null;
    }
    
	private static String getPreTwoChar(String num) {
		if (StringUtil.empty(num) || num.length() < 2) {
			return "";
		}
		return num.substring(0, 2);
	}

	/**
	 * 获取国标码对应的sku
	 * 
	 * @param gbCode
	 * @return
	 */
	public static String gb2Sku(String gbCode) {
		
		if(isSku(gbCode)) {
			if(GbCodeManager.getInstance(StoreApplication.getContext()).isExistSku(gbCode)) {
				return gbCode ;
			}
		}
		if(isGbCode(gbCode)) {
			return GbCodeManager.getInstance(StoreApplication.getContext()).getSku(gbCode);
		}

		return null;
		
	}
	
}
