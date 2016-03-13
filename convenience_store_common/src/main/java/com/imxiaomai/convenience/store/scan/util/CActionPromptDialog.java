package com.imxiaomai.convenience.store.scan.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

/**
 * 仿IOS类似Android AlertDialog 仅有一个确定按钮
 * 
 * @author lixiaoming
 *
 */
public class CActionPromptDialog {

	private final int MATCH_PARENT = LayoutParams.MATCH_PARENT;
	private final int WRAP_CONTENT = LayoutParams.WRAP_CONTENT;
	private final int radius = 8;
	private final float[] outerR = new float[] { radius, radius, radius, radius, radius, radius, radius, radius };

	private String title = "标题文字可使用setTitle()设置";
	private String content = "内容文字可使用setContent()设置";
	private String actionTitle = "确定";
	
	private boolean canceledOnTouchOutside;
	
	private Context context;
	private AlertDialog dialog;
	private Typeface typeface;
	private float scale;
	private int screenWidth;
	private TextView titleTextView;
	private TextView contentTextView;
	private CTextView actionTextView;

	private int backgroundColor = Color.parseColor("#FFFFFFFF");
	private int titleColor = Color.parseColor("#131313");
	private int contentColor = Color.parseColor("#454545");
	private int lineHorizontalColor = Color.parseColor("#55888888");
	private int lineVerticalColor = Color.parseColor("#55888888");
	private int btnNormalColor = Color.parseColor("#0f72cf");
	private int btnClickColor = Color.parseColor("#E6E6E6");
	
	private CActionListener actionListener;

	@SuppressWarnings("deprecation")
	public CActionPromptDialog(Context context) {
		this.context = context;
		this.scale = context.getResources().getDisplayMetrics().density;
		this.screenWidth = ((Activity) context).getWindowManager().getDefaultDisplay().getWidth();
	}

	public CActionPromptDialog setTitle(String title) {
		this.title = title;
		return this;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public CActionPromptDialog setTypeface(Typeface typeface) {
		this.typeface = typeface;
		return this;
	}

	public CActionPromptDialog setBackgroundColor(int backgroundColor) {
		this.backgroundColor = backgroundColor;
		return this;
	}

	public CActionPromptDialog setTitleColor(int titleColor) {
		this.titleColor = titleColor;
		return this;
	}

	public CActionPromptDialog setLineHorizontalColor(int lineHorizontalColor) {
		this.lineHorizontalColor = lineHorizontalColor;
		return this;
	}

	public CActionPromptDialog setLineVerticalColor(int lineVerticalColor) {
		this.lineVerticalColor = lineVerticalColor;
		return this;
	}

	public CActionPromptDialog setActionButton(String title, CActionListener listener) {
		this.actionTitle = title;
		this.actionListener = listener;
		return this;
	}
	
	
	
	/**
	 * 
	 */
	/*public void setCancelBtnElable() {
		cancelTextView.setClickable(false);
		cancelTextView.setEnabled(false);
		cancelTextView.setFocusable(false);
		cancelTextView.setFocusableInTouchMode(false);
		
		//Drawable nomal = createBackground(Color.WHITE, 204, new float[] { 0, 0, 0, 0, 0, 0, 10, 10 });
		Drawable pressed = createBackground(btnClickColor, 204, new float[] { 0, 0, 0, 0, 0, 0, 10, 10 });
		//StateListDrawable drawable = cancelTextView.getStateListDrawable(nomal, pressed);
		cancelTextView.setBackgroundDrawable(pressed);
	}*/

	@SuppressWarnings("deprecation")
	public void show() {
		dialog = new AlertDialog.Builder(context).create();
		RoundRectShape roundRectShape = new RoundRectShape(outerR, null, null);
		ShapeDrawable shapeDrawable = new ShapeDrawable(roundRectShape);
		shapeDrawable.getPaint().setColor(backgroundColor);
		shapeDrawable.getPaint().setStyle(Paint.Style.FILL);
		final LinearLayout windowLayout = new LinearLayout(context);
		windowLayout.setLayoutParams(getParams(MATCH_PARENT, WRAP_CONTENT));
		windowLayout.setOrientation(LinearLayout.VERTICAL);
		windowLayout.setBackgroundDrawable(shapeDrawable);
		windowLayout.getViewTreeObserver().addOnPreDrawListener(new OnPreDrawListener() {
			@Override
			public boolean onPreDraw() {
				FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(screenWidth - dp2px(60), WRAP_CONTENT);
				params.leftMargin = dp2px(20);
				params.rightMargin = dp2px(20);
				windowLayout.setLayoutParams(params);
				windowLayout.getViewTreeObserver().removeOnPreDrawListener(this);
				return false;
			}
		});

		LinearLayout tipLinearLayout = new LinearLayout(context);
		tipLinearLayout.setOrientation(LinearLayout.VERTICAL);
		tipLinearLayout.setGravity(Gravity.CENTER);
		//if (SDeviceUtil.getDeviceScreen(context) >= SDeviceUtil.SCREEN_720P) {
			if(TextUtils.isEmpty(title) || TextUtils.isEmpty(content)){
				tipLinearLayout.setLayoutParams(getParams2(MATCH_PARENT, dp2px(100f)));
			}else{
				tipLinearLayout.setLayoutParams(getParams2(MATCH_PARENT, dp2px(150f)));
			}
		//} else {
		//	if(TextUtils.isEmpty(title) || TextUtils.isEmpty(content)){
		//		tipLinearLayout.setLayoutParams(getParams2(MATCH_PARENT, dp2px(40f)));
		//	}else{
		//		tipLinearLayout.setLayoutParams(getParams2(MATCH_PARENT, dp2px(80f)));
		//	}
		//}

		titleTextView = new TextView(context);
		titleTextView.setTextColor(titleColor);
		titleTextView.setGravity(Gravity.CENTER);
		titleTextView.setTextSize(18f);
		titleTextView.setText(title);
		TextPaint textPaint = titleTextView.getPaint();
		textPaint.setFakeBoldText(true);

		contentTextView = new TextView(context);
		contentTextView.setTextColor(contentColor);
		contentTextView.setGravity(Gravity.CENTER);
		contentTextView.setTextSize(16f);
		contentTextView.setText(content);

		View lineHorizontal = new View(context);
		lineHorizontal.setLayoutParams(getParams(MATCH_PARENT, dp2px(0.5f)));
		lineHorizontal.setBackgroundColor(lineHorizontalColor);
		LinearLayout buttonLayout = new LinearLayout(context);
		if (SDeviceUtil.getDeviceScreen(context) >= SDeviceUtil.SCREEN_720P) {
			buttonLayout.setLayoutParams(getParams(MATCH_PARENT, dp2px(45f)));
		} else {
			buttonLayout.setLayoutParams(getParams(MATCH_PARENT, dp2px(40f)));
		}
		buttonLayout.setOrientation(LinearLayout.HORIZONTAL);

		LayoutParams params = new LayoutParams(dp2px(0f), MATCH_PARENT, 1);
		actionTextView = new CTextView(context);
		actionTextView.setLayoutParams(params);
		actionTextView.setTextColor(btnNormalColor);
		actionTextView.setTextSize(17);
		actionTextView.setGravity(Gravity.CENTER);
		actionTextView.setText(actionTitle);
		Drawable nomal = createBackground(Color.WHITE, 204, new float[] { 0, 0, 0, 0, 10, 10, 10, 10 });
		Drawable pressed = createBackground(btnClickColor, 204, new float[] { 0, 0, 0, 0, 10, 10, 10, 10 });
		Drawable drawable = actionTextView.getStateListDrawable(nomal, pressed);
		actionTextView.setBackgroundDrawable(drawable);
		actionTextView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				dismiss();
				if (actionListener != null) {
					actionListener.onAction();
				}
			}
		});

		buttonLayout.addView(actionTextView);
		if(!TextUtils.isEmpty(title)){
			tipLinearLayout.addView(titleTextView);
		}
		if(!TextUtils.isEmpty(content)){
			tipLinearLayout.addView(contentTextView);
		}

		windowLayout.addView(tipLinearLayout);
		windowLayout.addView(lineHorizontal);
		windowLayout.addView(buttonLayout);

		if (typeface != null) {
			titleTextView.setTypeface(typeface);
			actionTextView.setTypeface(typeface);
		}
		dialog.setCanceledOnTouchOutside(canceledOnTouchOutside);
		
		dialog.show();
		dialog.setContentView(windowLayout);
	}

	/**
	 * 生成图片
	 */
	public ShapeDrawable createBackground(int color, int alpha, float radius) {
		float[] outerR = new float[] { radius, radius, radius, radius, radius, radius, radius, radius };
		RoundRectShape roundRectShape = new RoundRectShape(outerR, null, null);
		ShapeDrawable shapeDrawable = new ShapeDrawable(roundRectShape);
		shapeDrawable.getPaint().setColor(color);
		shapeDrawable.getPaint().setAlpha(alpha);
		shapeDrawable.getPaint().setStyle(Paint.Style.FILL);
		return shapeDrawable;
	}

	/**
	 * 生成图片
	 */
	public ShapeDrawable createBackground(int color, int alpha, float[] outerR) {
		RoundRectShape roundRectShape = new RoundRectShape(outerR, null, null);
		ShapeDrawable shapeDrawable = new ShapeDrawable(roundRectShape);
		shapeDrawable.getPaint().setColor(color);
		shapeDrawable.getPaint().setAlpha(alpha);
		shapeDrawable.getPaint().setStyle(Paint.Style.FILL);
		return shapeDrawable;
	}

	private LayoutParams getParams(int width, int height) {
		LayoutParams params = new LayoutParams(width, height);
		return params;
	}

	private LayoutParams getParams2(int width, int height) {
		LayoutParams params = new LayoutParams(width, height);
		params.leftMargin = dp2px(10);
		params.rightMargin = dp2px(10);
		return params;
	}

	private int dp2px(float dp) {
		return (int) (dp * scale + 0.5f);
	}

	/**
     * 
     */
	public void dismiss() {
		if (dialog != null) {
			dialog.dismiss();
		}
	}

	public void setCanceledOnTouchOutside(boolean canceledOnTouchOutside) {
		this.canceledOnTouchOutside = canceledOnTouchOutside;
	}

	/**
	 * 
	 * @author zhanghaipeng
	 *
	 */
	class CTextView extends TextView {

		public CTextView(Context context) {
			super(context);
		}

		/**
		 * 
		 * @param mImageIds
		 * @return
		 */
		public StateListDrawable getStateListDrawable(Drawable normal, Drawable selected) {
			StateListDrawable bg = new StateListDrawable();
			bg.addState(View.PRESSED_ENABLED_STATE_SET, selected);
			bg.addState(View.ENABLED_FOCUSED_STATE_SET, selected);
			bg.addState(View.ENABLED_STATE_SET, normal);
			bg.addState(View.FOCUSED_STATE_SET, selected);
			bg.addState(View.EMPTY_STATE_SET, normal);
			return bg;
		}
	}
	
	public interface CActionListener {
		public void onAction();
	}
	
}
