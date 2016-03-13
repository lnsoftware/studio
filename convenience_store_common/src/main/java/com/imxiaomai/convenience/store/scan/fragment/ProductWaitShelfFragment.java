package com.imxiaomai.convenience.store.scan.fragment;

import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.imxiaomai.convenience.store.common.R;
import com.imxiaomai.convenience.store.scan.adpater.WaitOnShelfAdapter;
import com.imxiaomai.convenience.store.scan.data.WaitOnShelfItem;

/**
 * 待上架商品明细
 */
public class ProductWaitShelfFragment extends BaseFragment {

	private ListView listView;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.wait_onshelf_product_fragment, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initView();
		setBackListener();
		listView = (ListView) getActivity().findViewById(R.id.waitOnShelf_lv);
		List<WaitOnShelfItem> datas = (List<WaitOnShelfItem>)getActivity().getIntent().getSerializableExtra("waitDatas");
	    listView.setAdapter(new WaitOnShelfAdapter(getActivity(), datas));	
	}

	private void initView() {
		initHeadView();
		setHeadTitle("待上架商品");
		setActionVisible(View.GONE);
	}
}
