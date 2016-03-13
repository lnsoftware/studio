package com.imxiaomai.convenience.store.scan.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;

import com.imxiaomai.convenience.store.common.R;
import com.imxiaomai.convenience.store.scan.manager.AccountManager;
import com.imxiaomai.convenience.store.scan.util.IntentUtils;
import com.imxiaomai.convenience.store.scan.util.ToastUtils;

public class LoginFragment extends BaseLoginFragment {

	private EditText etUsername;
	private EditText etPassword;
	private CheckBox cbShowpassword;
	private Button btnLogin;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.login_fragment, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initTitleView();
		initView();
		setListener();
		initData();
	}

	private void initTitleView() {
		initHeadView();
		setBackVisible(View.GONE);
		setActionVisible(View.GONE);
		setHeadTitle(getString(R.string.login_btn));
	}

	private void initView() {
		etUsername = (EditText) getActivity().findViewById(R.id.et_username);
		etPassword = (EditText) getActivity().findViewById(R.id.et_password);
		cbShowpassword = (CheckBox) getActivity().findViewById(R.id.cb_show_password);
		btnLogin = (Button) getActivity().findViewById(R.id.btn_login);
	}

	private void setListener() {
		cbShowpassword.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					cbShowpassword.setButtonDrawable(R.drawable.btn_checked_by_list);
					// 设置EditText文本为可见的
					etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
					int length = etPassword.getText().toString().trim().length();
					etPassword.setSelection(length);
				} else {
					cbShowpassword.setButtonDrawable(R.drawable.btn_checked_list);
					// 设置EditText文本为隐藏的
					etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
					int length = etPassword.getText().toString().trim().length();
					etPassword.setSelection(length);
				}
			}

		});

		btnLogin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				if (!validate()) {
					getloadUtil().showProgress("登录中...");
					login(etUsername.getText().toString().trim(), etPassword.getText().toString().trim());
				}
			}

		});
	}

	/**
	 * 用户名和密码验证
	 * 
	 * @return
	 */
	private boolean validate() {
		String userName = etUsername.getText().toString().trim();
		if (TextUtils.isEmpty(userName)) {
			ToastUtils.showShort(getActivity(), "用户名为空");
			return true;
		}

		String password = etPassword.getText().toString().trim();
		if (TextUtils.isEmpty(password)) {
			ToastUtils.showShort(getActivity(), "密码为空");
			return true;
		}

		return false;
	}

	private void initData() {
		String userName = AccountManager.getInstance().getUsername();
		String password = AccountManager.getInstance().getPassword();

		if (!TextUtils.isEmpty(userName)) {
			etUsername.setText(userName);
		}

		if (!TextUtils.isEmpty(password)) {
			etPassword.setText(password);
		}
	}

	@Override
	public void loginSuccess() {
		getloadUtil().closeProgress();
		startService();
		AccountManager.getInstance().saveUserData(user, etUsername.getText().toString().trim(),
				etPassword.getText().toString().trim());
		IntentUtils.showMainActivity(getActivity());
		getActivity().finish();
	}

	@Override
	protected void loginFailed() {
		getloadUtil().closeProgress();
		ToastUtils.showShort(getActivity(), "登录失败!");
	}

}
