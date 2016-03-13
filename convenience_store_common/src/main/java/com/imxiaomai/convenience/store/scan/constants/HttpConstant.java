package com.imxiaomai.convenience.store.scan.constants;


public class HttpConstant {
	
	//开发
//	public static String SERVER_URL = "http://172.16.110.67:8080/cb-wms-web/services";
//	public static String SERVER_URL = "http://172.16.101.44:8080/services";
//	public static String SERVER_URL = "http://172.16.111.15:8080/services";
//	public static String SERVER_URL = "http://172.16.110.147:8080/eshop-s-web/services";
//	public static String SERVER_URL = "http://172.16.101.45:8080/services";
//	public static String SERVER_URL = "http://172.16.110.146:8080/services";
//	public static String SERVER_URL = "http://172.16.110.246:8080/cb-wms-web/services";
//	public static String SERVER_URL = "http: //172.16.110.158:8080/services";

	//测试
	public static String SERVER_URL = "http://101.200.73.104:8082/eshop-s-web/services";
//	public static String SERVER_URL = "http://123.57.157.21:8081/wms-web/services";
//	public static String SERVER_URL = "http://101.200.73.104:8082/wms-web/services";

	//正式
//	public static String SERVER_URL = "http://wms.cb.imxiaomai.com/services";
	
	public static final String CONTENT_TYPE = "application/json";
	
	public static final int CONNECTION_TIME_OUT = 60 * 1000;

	public static final int CONNECTION_MAXIMUM = 2;

	public static final String DIR = "";
	
	public static final String END_SUFFIX = "/?timeStamp=%s&cipherText=%s&baseWarehouseCode=%s";
	
	/**获取采购单的商品明细*/
	public static final String GET_PURCHASE_PRD_LIST = DIR+"/inbound/getPrePurchaseProduct";
	/**采购上架*/
	public static final String PURCHASE_ON_SELF = DIR+"/inbound/addInbound";
	/** 登录 */
	public static final String GET_URL_LOGIN = DIR+"/app/doLogin";
	/**获取订单汇总信息*/
	public static final String POST_ORDER_STATICS = DIR+"/outbound/custorderstatistics";
	/**确认出库*/
	public static final String ORDER_OFF_SHELF = DIR+"/outbound/affirm";
	/**库存查询*/
	public static final String STOCK_LOCATION_QUERY = DIR+"/app/getStockInfo";
	/**获取商品基础信息*/
	public static final String LOAD_BASE_PRODUCT = DIR+"/product/getAllProducts";
	/** 升级 */
	public static final String GET_CHECK_UPDATE = DIR+"/app/update";
	/** 上传crash文件 */
	public static final String UPLOAD_CRASH_FILE = DIR+"/app/uploadLogfile";
    /** 获取盘点任务*/
    public static final String POST_INVENTORY_TASK = DIR +"/inventory/getInventoryTask";
    /** 盘点*/
    public static final String INVENTORY_PRD_INFO = DIR +"/inventory/addInventoryResult";
    /**通过销退单号获取销退商品*/
    public static final String POST_REVOKE_SHELF_ORDER_INFO = DIR +"/inboundonshelf/getonshelflistbyrevokeno";
    /**销退上架接口*/
    public static final String REVOKE_SHELF_ON_SHELF = DIR +"/inboundonshelf/saveinboundrevokeonshelf";
}
