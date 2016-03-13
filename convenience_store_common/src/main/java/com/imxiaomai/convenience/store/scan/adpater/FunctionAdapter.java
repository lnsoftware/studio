package com.imxiaomai.convenience.store.scan.adpater;

import java.util.List;

import android.content.Context;
import android.view.View;

import com.imxiaomai.convenience.store.common.R;
import com.imxiaomai.convenience.store.scan.model.Function;

public class FunctionAdapter extends SimpleBaseAdapter<Function> {

	public FunctionAdapter(Context context, List<Function> data) {
		super(context, data);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getItemResource() {
		return R.layout.main_item;
	}

	@Override
	public View getItemView(int position, View convertView, SimpleBaseAdapter<Function>.ViewHolder holder) {
		// TODO Auto-generated method stub
		return null;
	}

}
