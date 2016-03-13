package com.imxiaomai.convenience.store.scan.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.imxiaomai.convenience.store.common.R;
import com.imxiaomai.convenience.store.scan.activity.LoginActivity;
import com.imxiaomai.convenience.store.scan.manager.AccountManager;
import com.imxiaomai.convenience.store.scan.util.IntentUtils;

public class WelcomeFragment extends BaseLoginFragment {
	
	private ImageView welcome_iv;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return  inflater.inflate(R.layout.welcome_activity, container, false);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initView();
		initData();
	}

	private void initView() {
		welcome_iv = (ImageView)getActivity().findViewById(R.id.welcome_iv);
		Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.welcomeshow_anim);
		welcome_iv.setAnimation(animation);
		animation.start();
	}

	private void initData() {
		checkCrashFileUpload();
		
		String userName = AccountManager.getInstance().getUsername();
		String password = AccountManager.getInstance().getPassword();
		if (!TextUtils.isEmpty(userName) && !TextUtils.isEmpty(password)) {
			login(userName, password);
		} else {
	    	Intent intentLogin = new Intent(getActivity(), LoginActivity.class);
	        startActivity(intentLogin);
	        getActivity().finish();
		}
	}

	@Override
	public void loginSuccess() {
		AccountManager.getInstance().saveUserData(user, null,null);
		startService();
		IntentUtils.showMainActivity(getActivity());
		getActivity().finish();
	}

	@Override
	protected void loginFailed() {
		IntentUtils.showLoginActivity(getActivity());
		getActivity().finish();
	}
}
