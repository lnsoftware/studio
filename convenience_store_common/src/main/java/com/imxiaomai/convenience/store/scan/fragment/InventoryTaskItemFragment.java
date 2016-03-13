package com.imxiaomai.convenience.store.scan.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.imxiaomai.convenience.store.common.R;
import com.imxiaomai.convenience.store.scan.StoreApplication;
import com.imxiaomai.convenience.store.scan.constants.HttpStatus;
import com.imxiaomai.convenience.store.scan.http.ServiceResult;
import com.imxiaomai.convenience.store.scan.manager.AccountManager;
import com.imxiaomai.convenience.store.scan.manager.ProductManager;
import com.imxiaomai.convenience.store.scan.model.ProductResponse;
import com.imxiaomai.convenience.store.scan.model.StockInventoryResult;
import com.imxiaomai.convenience.store.scan.model.StockInventoryResultRequest;
import com.imxiaomai.convenience.store.scan.rest.InventoryRest;
import com.imxiaomai.convenience.store.scan.util.NumberTypeUtil;
import com.imxiaomai.convenience.store.scan.util.ToastUtils;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.List;
/**
 * 盘点项目页面
 * @author li dajun
 */
public class InventoryTaskItemFragment extends BaseFragment implements OnClickListener {
	/**商品编号*/
	private TextView tv_inventory_sku;
	/**商品名称*/
	private TextView tv_prd_specification;
    /**商品规格*/
    private TextView prd_name_tv;
	/**商品条形码*/
	private EditText prd_no_et;
	/**数量*/
	private EditText prd_num_et;
	
	/**提交*/
	private Button submit_bt;
	/**手动输入商品条码*/
	private Button prd_no_input_bt;
	
	/**需要盘点的列表*/
	private List<StockInventoryResult> stockInventoryResultList;
	/**商品管理类*/
	private ProductManager pm;
	/**商品名称*/
	private String prdName;
    /**任务号*/
    private String mTitle;

    protected Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			getloadUtil().closeProgress();
			switch (msg.what) {
			case LOADDATA_SUCCESS:
				//成功后的操作
				if (stockInventoryResultList.size()>1) {//如果任务大于1就把第一个remove掉
					ToastUtils.showShort(getActivity(), "单条提交成功");
					stockInventoryResultList.remove(stockInventoryResultList.get(0));
					refresh();
				}else {
					stockInventoryResultList = null;//把所有都删除掉
                    toFinishActivity();
					ToastUtils.showShort(getActivity(), "该任务已完成");
				}
				break;
			case LOADDATA_FAILED:
				ToastUtils.showShort(getActivity(),null==msg.obj?"加载数据失败":(String) msg.obj);
				break;
			}
		}
	};

    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.inventory_tsak_item_fragment, container, false);
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initView();
		setListener();
		initData();
	}
	/**
	 * 初始化控件
	 */
	private void initView() {
		initHeadView();
		setActionVisible(View.GONE);

        tv_inventory_sku = (TextView)getActivity().findViewById(R.id.tv_inventory_sku);
		prd_name_tv = (TextView)getActivity().findViewById(R.id.prd_name_tv);
        tv_prd_specification = (TextView)getActivity().findViewById(R.id.tv_prd_specification);

		prd_no_et = (EditText) getActivity().findViewById(R.id.prd_no_et);
		prd_num_et = (EditText) getActivity().findViewById(R.id.prd_num_et);
		prd_num_et.setEnabled(false);
		
		prd_no_input_bt = (Button) getActivity().findViewById(R.id.prd_no_input_bt);
		submit_bt = (Button) getActivity().findViewById(R.id.submit_bt);
	}
	/**
	 * 初始化数据
	 */
	@SuppressWarnings("unchecked")
	private void initData() {
		pm = ProductManager.getInstance(StoreApplication.getContext()); 
		Intent intent = getActivity().getIntent();
		//得到上一个页面的值
        mTitle = intent.getStringExtra("title");
        TextView title = (TextView) getActivity().findViewById(R.id.head_title_tv);
        title.setTextSize(18);
        setHeadTitle("盘点任务[" + mTitle + "]");
        stockInventoryResultList = (List<StockInventoryResult>) intent.getSerializableExtra("list");
		refresh();
	}
	/**
	 * 刷新页面
	 */
	private void refresh(){
		if (stockInventoryResultList!=null) {
			if (stockInventoryResultList.size()!= 0 ) {
                String sku = stockInventoryResultList.get(0).getSku();
                ProductResponse product = pm.getProduct(sku);
                if (null == product) {
                    toFinishActivity();
                    ToastUtils.showLong(getActivity(), "SKU:<" + sku + ">不存在,请核实!");
                    return;
                }

                prdName = product.getName();
				prd_name_tv.setText(prdName);
                tv_prd_specification.setText(product.getSpecification());
                tv_inventory_sku.setText(stockInventoryResultList.get(0).getSku());
				prd_num_et.setText("");
				prd_no_et.setText("");
				
			}else{
				ToastUtils.showShort(getActivity(), "没有需要盘点的货目");
                toFinishActivity();
			}
		}else {
			ToastUtils.showShort(getActivity(), "没有需要盘点的货目");
            toFinishActivity();
        }
	}
	/**
	 * 添加监听
	 */
	private void setListener() {
		setBackListener();
		submit_bt.setOnClickListener(this);
		prd_no_input_bt.setOnClickListener(this);
	}
	
	/**
	 * 扫码得到的码号
	 */
	@Override
	public void onScanResult(String result) {
		super.onScanResult(result);

		if (NumberTypeUtil.isGbCodeOrSku(result)) {// 如果扫的是商品号
			String newSku;
				if (NumberTypeUtil.isGbCode(result)) {
					newSku = NumberTypeUtil.gb2Sku(result);
				}else {
					newSku = result;
				}
				if (newSku != null) {
					if (newSku.equals(stockInventoryResultList.get(0).getSku())) {//如果扫到的商品号是所需要的
                        prd_no_et.setText(result);
                        prd_num_et.setEnabled(true);
                    }else {
                        ToastUtils.showShort(getActivity(), "请扫描<"+prdName+">的条码");
                    }
				}
		} else {
			ToastUtils.showLong(getActivity(), "请扫描或输入商品号");
		}
	}

	@Override
	public void onClick(View view) {
		int id = view.getId();
		if (id == R.id.submit_bt) {
			getloadUtil().showProgress("正在提交...");
			StockInventoryResultRequest request = new StockInventoryResultRequest();
            StockInventoryResult result = stockInventoryResultList.get(0);
            request.setInventoryNo(result.getInventoryNo());//盘点号
			request.setInventoryTaskNo(result.getInventoryTaskNo());//盘点任务号
			request.setSku(result.getSku());//SKU
            request.setOperator(AccountManager.getInstance().getOperator());//上传操作人
			request.setEshopNo(result.getEshopNo());//便利店
			Integer prdNum = Integer.valueOf(prd_num_et.getText().toString().trim());
				if (prdNum>=0) {
					request.setActualNum(prdNum);//实际盘点数量
				}else {
					ToastUtils.showShort(getActivity(), "请输入正整数");
					return;
				}
			
			InventoryRest rest = new InventoryRest();
			try {
				rest.onShelf(getActivity(), request, new ServiceResult() {
					Message msg = Message.obtain();
					@Override
					public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
						try {
							JSONObject jsonObject = new JSONObject(new String(responseBody));
							if (jsonObject.getInt("code") == HttpStatus.OK.getCode()) {//访问成功
								msg.what = LOADDATA_SUCCESS;
								msg.obj = jsonObject.getString("message");
								handler.sendMessage(msg);
							} else {
								Message msg = new Message();
								msg.what = LOADDATA_FAILED;
								msg.obj = jsonObject.getString("message");
								handler.sendMessage(msg);
							}
						} catch (JSONException e) {
							msg.what = LOADDATA_FAILED;
							msg.obj = getActivity().getResources().getString(R.string.resut_parse_failed);
							handler.sendMessage(msg);
							e.printStackTrace();
						}
						
					}
					
					@Override
					public void onFailure(int statusCode, Header[] headers,
							byte[] responseBody, Throwable error) {
						msg.what = LOADDATA_FAILED;
						msg.obj = getActivity().getResources().getString(R.string.resut_parse_failed);
						handler.sendMessage(msg);
					}
				});
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}else if (id == R.id.prd_no_input_bt) {
			inputBarcode("输入商品条码");
		}
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		handler.removeCallbacksAndMessages(null);
	}
}
