package com.imxiaomai.convenience.store.scan.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.imxiaomai.convenience.store.common.R;
import com.imxiaomai.convenience.store.scan.activity.InventoryTaskItemActivity;
import com.imxiaomai.convenience.store.scan.constants.HttpStatus;
import com.imxiaomai.convenience.store.scan.http.ServiceResult;
import com.imxiaomai.convenience.store.scan.manager.AccountManager;
import com.imxiaomai.convenience.store.scan.model.StockInventoryResult;
import com.imxiaomai.convenience.store.scan.model.StockInventoryTaskResponse;
import com.imxiaomai.convenience.store.scan.model.StockInventoryTaskResquest;
import com.imxiaomai.convenience.store.scan.rest.InventoryTaskRest;
import com.imxiaomai.convenience.store.scan.util.NumberTypeUtil;
import com.imxiaomai.convenience.store.scan.util.ToastUtils;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 盘点作业
 * @author li dajun
 */
public class InventoryTaskFragment extends BaseFragment implements OnClickListener {
    public static final int INPUTED_OK = 1;
    /**解析任务*/
    public static final int PARSE_DATA = 2;
    /** 提交按 */
	private Button submit_bt;
	/**自动获取任务*/
	private Button task_no_auto_input_bt;
    /**手工输入任务号*/
	private Button task_no_input_bt;
	
	/**任务号*/
	private EditText task_no_input_et;
	
	private InventoryTaskRest rest;
	
	private StockInventoryTaskResponse task;

	protected Handler handler = new Handler() {
        /**任务号*/
        private String mTaskNo;
        private List<StockInventoryResult> mList;

        @Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			getloadUtil().closeProgress();
			switch (msg.what) {
			case LOADDATA_SUCCESS:
				if (task == null) {
					ToastUtils.showShort(getActivity(), "当前没有需要盘点的任务!");
					toFinishActivity();
					return;
				}

                mList = task.getInventoryResultList();
                if (mList.size() >= 1) {
                    mTaskNo = task.getInventoryTaskNo();
                    task_no_input_et.setText(mTaskNo);
                } else {
                    handler.sendEmptyMessage(LOADDATA_NUll);
                }

				break;
            case PARSE_DATA:
                List<StockInventoryResult> newList = new ArrayList<StockInventoryResult>();//用来储存临时的已经盘点过的item
                for (StockInventoryResult aList : mList) {
                    if (null != aList.getIsInputed() && aList.getIsInputed() == INPUTED_OK) {//盘点输入完成
                        newList.add(aList);
                    }
                }
                if (newList.size() > 0) {
                    for (StockInventoryResult aNewList : newList) {
                        mList.remove(aNewList);//把盘点过的item删除
                    }
                }else if (mList.size() < 1) {
                    handler.sendEmptyMessage(LOADDATA_NUll);
                    mList.clear();
                    newList.clear();
                    task_no_input_et.setText("");
                    toFinishActivity();
                    return;
                }
				Intent intent = new Intent(getActivity(),InventoryTaskItemActivity.class);
				//把值传给下一个页面
				intent.putExtra("list",(Serializable) mList);
                intent.putExtra("title", mTaskNo);
                startActivityForResult(intent, 1);
                break;
			case LOADDATA_NUll:
                task_no_input_et.setText("");
				ToastUtils.showShort(getActivity(), "没有盘点任务!");
				break;
			case LOADDATA_FAILED:
				String message = (msg.obj != null ? (String)msg.obj : "获取盘点任务失败");
				ToastUtils.showShort(getActivity(), message);
                toFinishActivity();
				break;
			}
		}
	};
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.inventory_task_fragment, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initView();
		setListener();
	}

	private void initView() {
		initHeadView();
		setHeadTitle("盘点作业");
		setActionVisible(View.GONE);

		submit_bt = (Button) getActivity().findViewById(R.id.submit_bt);
        task_no_auto_input_bt = (Button)getActivity().findViewById(R.id.task_no_auto_input_bt);
        task_no_input_bt = (Button)getActivity().findViewById(R.id.task_no_input_bt);
		task_no_input_et = (EditText)getActivity().findViewById(R.id.task_no_input_et);
	}

	@Override
	public void onResume() {
		super.onResume();
//        getData();
	}
	
	/**
	 * 点击事件
	 */
	@Override
	public void onClick(View view) {
		int id = view.getId();
		if (id == R.id.submit_bt) {
            //getData();
            if (!TextUtils.isEmpty(task_no_input_et.getText().toString().trim())) {
                handler.sendEmptyMessage(PARSE_DATA);
            } else {
                ToastUtils.showShort(getActivity(),"请先获取任务...");
            }
        }else if (id == R.id.task_no_auto_input_bt) {
			getData("");
		}else if (id == R.id.task_no_input_bt) {
            inputBarcode("输入盘点任务号");
        }

	}

    @Override
    public void onScanResult(String result) {
        super.onScanResult(result);
        if(TextUtils.isEmpty(result)) return;

        String inventoryNo = NumberTypeUtil.autoAddPR(result);
        if (NumberTypeUtil.isInventoryNo(inventoryNo)) {
            task_no_input_et.setText(inventoryNo);
        } else {
            task_no_input_et.setText("");
            ToastUtils.showShort(getFragmentActivity(),"请输入正确的盘点单号");
        }
    }

    /**
	 * 得到盘点数据
     * 如果inventoryNo为空，就自动去服务端拿一个任务，如果不为空，就用这个单号去服务端拿相应的任务
     * @param inventoryNo
     */
	private void getData(String inventoryNo) {
        //获取盘点任务实体类
		StockInventoryTaskResquest request = new StockInventoryTaskResquest();
        //上传时间
		request.setOperateTime(new Date());
        //上传操作人
        request.setOperator(AccountManager.getInstance().getOperator());
        //上传仓便利店编号
        request.setEshopNo(AccountManager.getInstance().getShopCode());
        if (!TextUtils.isEmpty(inventoryNo)) {
            request.setInventoryTaskNo(inventoryNo);
        }

		getloadUtil().showProgress("获取盘点任务中...");

		if(rest == null) {
			rest = new InventoryTaskRest();
		}

        try {
            rest.getInventoryTask(getActivity(), request, new ServiceResult() {
                Message msg = Message.obtain();
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        try {
                            JSONObject jsonObject = new JSONObject(new String(responseBody));
                            if (jsonObject != null && jsonObject.getInt("code") == HttpStatus.OK.getCode()) {//网络访问成功
                                task = rest.parseInventoryTask(jsonObject.getString("result"));
                                msg.obj = task;
                                msg.what = LOADDATA_SUCCESS;
                                handler.sendMessage(msg);
                            }else if (jsonObject.getInt("code") == HttpStatus.OK_NODATA.getCode()) {
                                handler.sendEmptyMessage(LOADDATA_NUll);
                            } else if (jsonObject != null) {
                                msg.obj = jsonObject.getString("message");
                                msg.what = LOADDATA_FAILED;
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
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    msg.what = LOADDATA_FAILED;
                	if (getFragmentActivity() != null) {
						msg.obj = getFragmentActivity().getResources().getString(R.string.resut_parse_failed) + statusCode;
					} else {
						msg.obj = null;
					}
                    handler.sendMessage(msg);
                }
            });
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            getloadUtil().closeProgress();
            ToastUtils.showLong(getActivity(),e.getMessage());
        }

	}

	/**
	 * 注册监听
	 */
	private void setListener() {
		setBackListener();
		submit_bt.setOnClickListener(this);
		task_no_auto_input_bt.setOnClickListener(this);
        task_no_input_bt.setOnClickListener(this);
        //为盘点输入框添加一个监听，如果task 为空就去getData
        task_no_input_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (null == task && !TextUtils.isEmpty(s.toString())) {
                    getData(s.toString());
                } else if (null !=task && !TextUtils.isEmpty(s.toString()) && !task.getInventoryTaskNo().equals(s.toString()) ) {
                    task = null;
                    getData(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		task_no_input_et.setText("");
	}

	/**
	 * 退出时把没用的数据清空
	 */
	@Override
	public void onDestroy() {
		super.onDestroy();
		handler.removeCallbacksAndMessages(null);
	}
}
