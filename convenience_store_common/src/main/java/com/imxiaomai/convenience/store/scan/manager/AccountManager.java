package com.imxiaomai.convenience.store.scan.manager;

import java.util.WeakHashMap;

import android.content.Context;
import android.text.TextUtils;

import com.imxiaomai.convenience.store.scan.StoreApplication;
import com.imxiaomai.convenience.store.scan.constants.AppConstants;
import com.imxiaomai.convenience.store.scan.enums.ShopTypeEnum;
import com.imxiaomai.convenience.store.scan.model.EshopEmpResponse;
import com.imxiaomai.convenience.store.scan.util.SharedPreferencesUtil;

/**
 * 登录管理
 * 
 * lixiaoming
 */
public class AccountManager {

    public interface AccountManagerListener {

        /**
         * 用户登录
         */
        public void onUserLogedin();

        /**
         * 用户注销
         */
        public void onUserLogedout();
    }

    private Context mContext;
    private static AccountManager accountManager;
    private WeakHashMap<AccountManagerListener, Void> listeners = new WeakHashMap<AccountManagerListener, Void>();


    public static AccountManager getInstance() {
    	if (accountManager == null) {
    		accountManager = new AccountManager();
    	}
    	return accountManager;
    }

    private AccountManager() {
    	mContext = StoreApplication.getContext();
	}
    
    /**
     * 保存用户信息
     * 
     * @param jsonObject
     * @param userName
     * @param password
     */
    public void saveUserData(EshopEmpResponse user, String userName, String password) {
    	
   		SharedPreferencesUtil.putString(AppConstants.SHARED_OPERATOR, user.getEmpAccount(), mContext);
		SharedPreferencesUtil.putString(AppConstants.SHARED_SHOPCODE, user.getEshopNo(), mContext);
		SharedPreferencesUtil.putString(AppConstants.SHARED_SHOPTYPE, ShopTypeEnum.getName(user.getEmpType()), mContext);
		SharedPreferencesUtil.putString(AppConstants.SHARED_SHOPNAME, user.getEshopName(), mContext);
		SharedPreferencesUtil.putString(AppConstants.SHARED_OPERATOR_NAME, user.getEmpName(), mContext);
		SharedPreferencesUtil.putString(AppConstants.SHARED_OPERATOR_TEL, user.getEmpTel(), mContext);
		
    	if (!TextUtils.isEmpty(userName) && !TextUtils.isEmpty(password)) {
    		SharedPreferencesUtil.putString(AppConstants.SHARED_USERNAME, userName, mContext);
    		SharedPreferencesUtil.putString(AppConstants.SHARED_PASSWORD, password, mContext);
    	}
    		
    }
    
    /**
     * 重置用户数据
     */
    public void resetUserData() {
    	SharedPreferencesUtil.putString(AppConstants.SHARED_USERNAME, "", mContext);
		SharedPreferencesUtil.putString(AppConstants.SHARED_PASSWORD, "", mContext);
    }
    
    /**
     * 获取用户名
     * @return
     */
    public String getUsername() {
    	return SharedPreferencesUtil.getString(AppConstants.SHARED_USERNAME, mContext);
    }
    
    /**
     * 获取密码
     * @return
     */
    public String getPassword() {
    	return SharedPreferencesUtil.getString(AppConstants.SHARED_PASSWORD, mContext);
    }
    
    /**
     * 获取操作人
     * 
     * @return
     */
    public String getOperator() {
    	return SharedPreferencesUtil.getString(AppConstants.SHARED_OPERATOR, mContext);
    }
    
    /**
     * 获取操作人真实姓名
     * 
     * @return
     */
    public String getRealName() {
    	return SharedPreferencesUtil.getString(AppConstants.SHARED_OPERATOR_NAME, mContext);
    }
    
    /**
     * 获取操作人电话
     * 
     * @return
     */
    public String getTel() {
    	return SharedPreferencesUtil.getString(AppConstants.SHARED_OPERATOR_TEL, mContext);
    }
    
    /**
     * 获取便利店编号
     * 
     * @return
     */
    public String getShopCode() {
    	return SharedPreferencesUtil.getString(AppConstants.SHARED_SHOPCODE, mContext);
    }
    
    /**
     * 获取便利店名称
     * 
     * @return
     */
    public String getShopName() {
    	return SharedPreferencesUtil.getString(AppConstants.SHARED_SHOPNAME, mContext);
    }
    
    /**
     * 获取便利店类型
     * 
     * @return
     */
    public String getShopType() {
    	return SharedPreferencesUtil.getString(AppConstants.SHARED_SHOPTYPE, mContext);
    }
    
    /**
     * 添加监听器
     *
     * @param listener
     */
    public void addListener(AccountManagerListener listener) {
        listeners.put(listener, null);
    }

    /**
     * 删除监听器
     */
    public void removeListener(AccountManagerListener listener) {
        listeners.remove(listener);
    }

    private void onLogin() {
    }
    
    private void onLogout() {
    }

}
