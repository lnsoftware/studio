package com.imxiaomai.convenience.store.scan.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.imxiaomai.convenience.store.common.R;
import com.imxiaomai.convenience.store.scan.activity.RevokeDetailActivity;
import com.imxiaomai.convenience.store.scan.manager.RevokeManager;
import com.imxiaomai.convenience.store.scan.util.NumberTypeUtil;
import com.imxiaomai.convenience.store.scan.util.ToastUtils;

import java.util.ArrayList;

/**
 * 退货管理
 * Created by Li dajun
 * Date: 2015-10-27
 * Time: 16:06
 */
public class RevokefFragment extends BaseFragment implements View.OnClickListener {
    /**获取*/
    private Button action_bt;
    /**手工输入*/
    private Button input_bt;
    /**返架单号*/
    private EditText order_num_et;
    private ListView mListView;
    private ArrayList<String> mRevokeNo;
    private RevokeInfoAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.revoke_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
        initView();
        setListener();
    }

    private void initData() {
        mRevokeNo = RevokeManager.getInstance().getRevokeNo();
    }

    private void initView() {
        initHeadView();
        setActionVisible(View.GONE);

        action_bt = (Button) getActivity().findViewById(R.id.action_bt);
        input_bt = (Button) getActivity().findViewById(R.id.input_bt);
        order_num_et = (EditText) getActivity().findViewById(R.id.order_num_et);

        mListView = (ListView) getActivity().findViewById(R.id.return_listview);

        mAdapter = new RevokeInfoAdapter();
        mListView.setAdapter(mAdapter);

        setHeadTitle("退货管理");
        order_num_et.setHint("请输入单号");
    }

    private void setListener() {
        setBackListener();
        action_bt.setOnClickListener(this);
        input_bt.setOnClickListener(this);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                order_num_et.setText(mRevokeNo.get(position));
            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.action_bt) {
            String revokeNo = order_num_et.getText().toString().trim();
            if (TextUtils.isEmpty(revokeNo)) {
                ToastUtils.showShort(getActivity(), "单号不能为空");
                return;
            } else {
                getRevokeNo(revokeNo);
            }
        } else if (id == R.id.input_bt){
            inputBarcode("输入单号");
        }
    }

    /**
     * 通过单号获取销退任务
     * @param revokeNo
     */
    private void getRevokeNo(String revokeNo) {
        Intent intent = new Intent(getActivity(),RevokeDetailActivity.class);
        intent.putExtra("revokeNo", revokeNo);
        startActivity(intent);
    }

    @Override
    public void onScanResult(String result) {
        super.onScanResult(result);
           String orderOn = NumberTypeUtil.autoAddXM(result);
            if(NumberTypeUtil.isSalasOrder(orderOn)) {
                order_num_et.setText(orderOn);
                action_bt.performClick();
            } else if (NumberTypeUtil.isSalasOrderOff(result)) {
            	order_num_et.setText(result);
                action_bt.performClick();
            } else {
                ToastUtils.showShort(getActivity(), "单号不正确！");
            }
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
        mAdapter.notifyDataSetChanged();
        mAdapter.notifyDataSetInvalidated();
        order_num_et.setText("");
    }

    private class RevokeInfoAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mRevokeNo.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, final ViewGroup parent) {

            final ViewHolder viewHolder;
            if (null == convertView) {
                viewHolder = new ViewHolder();
                convertView = View.inflate(getActivity(), R.layout.item_revoke_shelf, null);
                viewHolder.mTitleText = (TextView) convertView.findViewById(R.id.return_type_tv);
                viewHolder.mRevokeNo = (TextView) convertView.findViewById(R.id.return_no_tv);
                viewHolder.mButtonDel = (Button) convertView.findViewById(R.id.but_delete_return_num);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
                viewHolder.mTitleText.setText("销退:");
                viewHolder.mButtonDel.setEnabled(RevokeManager.getInstance().mayDeleteForRevokeNo(mRevokeNo.get(position)));
                viewHolder.mRevokeNo.setText(mRevokeNo.get(position));

            viewHolder.mButtonDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RevokeManager.getInstance().deleteRevokeNo(mRevokeNo.get(position));
                    mRevokeNo.remove(position);
                    mAdapter.notifyDataSetChanged();
                }
            });
            return convertView;
        }
    }
        static class ViewHolder{
            public TextView mTitleText;
            public TextView mRevokeNo;
            public Button mButtonDel;
        }
}



