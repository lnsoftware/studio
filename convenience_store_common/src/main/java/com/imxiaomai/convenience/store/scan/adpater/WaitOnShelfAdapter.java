package com.imxiaomai.convenience.store.scan.adpater;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.imxiaomai.convenience.store.common.R;
import com.imxiaomai.convenience.store.scan.data.WaitOnShelfItem;

public class WaitOnShelfAdapter extends SimpleBaseAdapter<WaitOnShelfItem> {

	public WaitOnShelfAdapter(Context context, List<WaitOnShelfItem> data) {
		super(context, data);
	}

	@Override
	public int getItemResource() {
		return R.layout.layout_wait_onshelf_product_item;
	}

	@Override
	public View getItemView(int position, View convertView, ViewHolder holder) {
		WaitOnShelfItem onShelfItem = data.get(position);
		if (onShelfItem != null) {
			((TextView)holder.getView(R.id.prdno_tv)).setText(onShelfItem.getPrdNo());
			((TextView)holder.getView(R.id.prdname_tv)).setText(onShelfItem.getPrdName());
			((TextView)holder.getView(R.id.plannum_tv)).setText(onShelfItem.getPlanNum());
			((TextView)holder.getView(R.id.onshelf_tv)).setText(onShelfItem.getOnShelfNum());
			((TextView)holder.getView(R.id.waitonshelf_tv)).setText(onShelfItem.getWaitOnShelfNum());
			((TextView)holder.getView(R.id.num_tv)).setText(String.valueOf(position + 1));
		}
		return convertView;
	}

}
