package com.imxiaomai.convenience.store.scan.fragment;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.imxiaomai.convenience.store.common.R;

public class ListDilogFragment extends DialogFragment implements OnItemClickListener {
	
	private ListView listView;
	private String[] dataList; 
	private ItemListener itemListener;
	
	public static ListDilogFragment newInstance() {
		ListDilogFragment listDilogFragment = new ListDilogFragment();
		return listDilogFragment;
	}
	
	public void init(ItemListener itemListener,String[] dataList) {
		this.itemListener = itemListener;
		this.dataList = dataList;
	}
	
	@Override
	public void onActivityCreated(Bundle arg0) {
		super.onActivityCreated(arg0);
		ArrayAdapter< String > adapter = new ArrayAdapter< String >(getActivity(), 
                android.R.layout.simple_list_item_1, dataList); 
		listView.setAdapter(adapter); 
		listView.setOnItemClickListener( this ); 
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.list_dilog_fragment, null, false); 
		listView = (ListView) view.findViewById(R.id.list);
		getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE); 
		return view;
	}
	
	
	public interface ItemListener {
		void setItem(String item); 
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		 itemListener.setItem(dataList[position]);
		 dismiss();
	}
}