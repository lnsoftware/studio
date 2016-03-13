package com.imxiaomai.convenience.store.scan.util;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;

/**
 * 
 * 选择对话框 仿IOS
 * 
 */
public class CActionSheetDialog extends PopupWindow {

	private Context context;
	private String title;
	private String cancel = "取消";
	private Typeface typeface;
	private LinearLayout rootLayout;
	private LinearLayout contentLayout;
	private LinearLayout parentLayout;
	private ScrollView sheetLayout;
	private List<SheetItem> sheetItemList;
	private OnActionSheetClickListener onActionSheetClickListener;

	CTextView cancelTextView;

	private int DP = 0;
	private int WIDTH = 0;
	private int HEIGHT = 0;
	private int titleColor = Color.parseColor("#8F8F8F");
	// private int cancelColor = Color.parseColor("#3DB399");
	private int textColor = Color.parseColor("#0f72cf");
	private int bgClickColor = Color.parseColor("#E6E6E6");

	private int baseDp = 0;

	private CancelListener cancelListener;

	public interface CancelListener {
		public void onCancelListener();
	}

	/**
	 * 构造方法
	 * 
	 * @param context
	 */
	@SuppressWarnings("deprecation")
	public CActionSheetDialog(Context context) {
		Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		this.context = context;
		this.DP = dp2px(context, 1);
		this.WIDTH = display.getWidth();
		this.HEIGHT = display.getHeight();

		if (WIDTH <= 480 || HEIGHT <= 800) {
			baseDp = 35;
		} else if ((WIDTH > 480 && WIDTH <= 720) || (HEIGHT > 800 && HEIGHT <= 1280)) {
			baseDp = 40;
		} else {
			baseDp = 45;
		}

	}

	/**
	 * DP 转化 PX (Convert DP to PX)
	 * 
	 * @param context
	 *            上下文(Context or Activity)
	 * @param dp
	 *            DP大小(number of DP)
	 * @return int PX大小(number of PX)
	 */
	private int dp2px(Context context, float dp) {
		return (int) (dp * context.getResources().getDisplayMetrics().density + 0.5f);
	}

	/**
	 * 构建内容视图
	 * 
	 * @return 内容视图
	 */
	private View createContentView() {
		// 根布局
		rootLayout = new LinearLayout(context);
		rootLayout.setLayoutParams(getParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		rootLayout.setOrientation(LinearLayout.VERTICAL);
		rootLayout.setBackgroundColor(Color.parseColor("#77000000"));
		rootLayout.setGravity(Gravity.BOTTOM);
		rootLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dismiss();
			}
		});

		parentLayout = new LinearLayout(context);
		parentLayout.setLayoutParams(getParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		parentLayout.setOrientation(LinearLayout.VERTICAL);
		// parentLayout.setBackgroundColor(backgroundColor);

		LinearLayout childLayout = new LinearLayout(context);
		LayoutParams childParams = getParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		childParams.setMargins(8 * DP, 0, 8 * DP, 0);
		childLayout.setLayoutParams(childParams);
		childLayout.setOrientation(LinearLayout.VERTICAL);
		childLayout.setBackgroundDrawable(createBackground(Color.WHITE, 204, 10));

		// 标题
		TextView titleTextView = new TextView(context);
		titleTextView.setLayoutParams(getParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		titleTextView.setPadding(0, 10 * DP, 0, 10 * DP);
		titleTextView.setMinHeight(baseDp * DP);
		titleTextView.setTextSize(14);
		titleTextView.setGravity(Gravity.CENTER);
		titleTextView.setTextColor(titleColor);

		Drawable titleDrawable = createBackground(Color.WHITE, 204, new float[] { 10, 10, 10, 10, 0, 0, 0, 0 });
		titleTextView.setBackgroundDrawable(titleDrawable);

		if (title == null) {
			titleTextView.setVisibility(View.GONE);
		} else {
			titleTextView.setVisibility(View.VISIBLE);
			titleTextView.setText(title);
		}

		// 内容外层布局
		sheetLayout = new ScrollView(context);
		sheetLayout.setLayoutParams(getParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		sheetLayout.setFadingEdgeLength(0);

		// 内容内层布局
		contentLayout = new LinearLayout(context);
		contentLayout.setLayoutParams(getParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		contentLayout.setOrientation(LinearLayout.VERTICAL);
		sheetLayout.addView(contentLayout);

		// 取消按钮
		cancelTextView = new CTextView(context);
		LayoutParams params = getParams(LayoutParams.MATCH_PARENT, baseDp * DP);
		// params.setMargins(8 * DP, 8 * DP, 8 * DP, 8 * DP);
		cancelTextView.setLayoutParams(params);
		cancelTextView.setTextColor(textColor);
		cancelTextView.setTextSize(17);
		cancelTextView.setGravity(Gravity.CENTER);
		cancelTextView.setText(cancel);

		Drawable nomal = createBackground(Color.WHITE, 204, 10);
		Drawable pressed = createBackground(bgClickColor, 204, 10);
		StateListDrawable drawable = cancelTextView.getStateListDrawable(nomal, pressed);

		cancelTextView.setBackgroundDrawable(drawable);
		cancelTextView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				cancelTextView.postInvalidate();
				dismiss();
				if (cancelListener != null) {
					cancelListener.onCancelListener();
				}
			}
		});

		LinearLayout cancelLayout = new LinearLayout(context);
		LayoutParams cancelParam = getParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		cancelParam.setMargins(8 * DP, 8 * DP, 8 * DP, 8 * DP);
		cancelLayout.setLayoutParams(cancelParam);
		cancelLayout.setBackgroundDrawable(createBackground(Color.WHITE, 204, 10));
		cancelLayout.addView(cancelTextView);

		// 控件创建结束
		childLayout.addView(titleTextView);
		childLayout.addView(sheetLayout);
		parentLayout.addView(childLayout);
		parentLayout.addView(cancelLayout);
		rootLayout.addView(parentLayout);
		if (typeface != null) {
			titleTextView.setTypeface(typeface);
			cancelTextView.setTypeface(typeface);
		}
		return rootLayout;
	}

	/**
	 * 设置取消按钮文字
	 * 
	 * @param str
	 */
	public void setCancelText(String cancel) {
		this.cancel = cancel;
	}

	public CancelListener getCancelListener() {
		return cancelListener;
	}

	public void setCancelListener(CancelListener cancelListener) {
		this.cancelListener = cancelListener;
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

	/**
	 * 构建视图
	 * 
	 * @return
	 */
	@SuppressWarnings("deprecation")
	private CActionSheetDialog builder() {
		setBackgroundDrawable(new BitmapDrawable());
		setContentView(createContentView());
		setWidth(WIDTH);
		setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
		setFocusable(true);
		return this;
	}

	/**
	 * 根据宽高获得参数
	 * 
	 * @param width
	 *            宽
	 * @param height
	 *            高
	 * @return
	 */
	private LayoutParams getParams(int width, int height) {
		return new LayoutParams(width, height);
	}

	/**
	 * 设置标题内容
	 * 
	 * @param title
	 *            内容
	 * @return
	 */
	public CActionSheetDialog setTitle(String title) {
		this.title = title;
		return this;
	}

	/**
	 * 添加选项
	 * 
	 * @param strItem
	 *            选项标题
	 * @param color
	 *            选项标题颜色
	 * @param listener
	 *            选项点击监听
	 * @return
	 */
	public CActionSheetDialog addSheetItem(String strItem, int color, OnSheetItemClickListener listener) {
		if (sheetItemList == null) {
			sheetItemList = new ArrayList<SheetItem>();
		}
		sheetItemList.add(new SheetItem(strItem, color, listener));
		return this;
	}

	/**
	 * 添加选项
	 * 
	 * @param strItem
	 *            选项标题
	 * @param color
	 *            选项标题颜色
	 * @return
	 */
	public CActionSheetDialog addSheetItem(String strItem, int color) {
		return addSheetItem(strItem, color, null);
	}

	/**
	 * 设置选项
	 */
	private void setSheetItems() {
		if (sheetItemList == null || sheetItemList.size() <= 0) {
			return;
		}

		int size = sheetItemList.size();
		if (size >= 7) {
			LayoutParams params = (LayoutParams) sheetLayout.getLayoutParams();
			params.height = HEIGHT / 2;
			sheetLayout.setLayoutParams(params);
		}

		for (int i = 1; i <= size; i++) {
			final int index = i;
			SheetItem sheetItem = sheetItemList.get(i - 1);
			String itemName = sheetItem.name;
			int color = sheetItem.color;
			final OnSheetItemClickListener listener = (OnSheetItemClickListener) sheetItem.itemClickListener;

			CTextView textView = new CTextView(context);
			textView.setText(itemName);
			textView.setTextSize(17);
			textView.setGravity(Gravity.CENTER);
			if (typeface != null) {
				textView.setTypeface(typeface);
			}
			textView.setTextColor(color);
			textView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, baseDp * DP));

			Drawable nomal = null;
			Drawable pressed = null;
			if (i == 1 && size == 1 || i == size) {
				nomal = createBackground(Color.WHITE, 204, new float[] { 0, 0, 0, 0, 10, 10, 10, 10 });
				pressed = createBackground(bgClickColor, 204, new float[] { 0, 0, 0, 0, 10, 10, 10, 10 });
			} else {
				nomal = createBackground(Color.WHITE, 204, 0);
				pressed = createBackground(bgClickColor, 204, 0);
			}
			StateListDrawable drawable = textView.getStateListDrawable(nomal, pressed);

			textView.setBackgroundDrawable(drawable);

			textView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					dismiss();
					if (listener != null) {
						listener.onClick();
					}
					if (onActionSheetClickListener != null) {
						onActionSheetClickListener.onClick(index);
					}
				}
			});

			View view = new View(context);
			LayoutParams params = getParams(LayoutParams.MATCH_PARENT, (int) (0.5 * DP));
			params.setMargins(2 * DP, 0, 2 * DP, 0);
			view.setLayoutParams(params);
			view.setBackgroundColor(Color.parseColor("#33444444"));

			contentLayout.addView(view);
			contentLayout.addView(textView);
		}
	}

	/**
	 * 展示控件
	 */
	public void show() {
		builder();
		setSheetItems();
		AlphaAnimation animation1 = new AlphaAnimation(0.3f, 1.0f);
		animation1.setDuration(200);
		TranslateAnimation animation = new TranslateAnimation(0, 0, HEIGHT, 0);
		animation.setDuration(350);
		rootLayout.startAnimation(animation1);
		parentLayout.startAnimation(animation);
		showAtLocation(((ViewGroup) (((Activity) context).findViewById(android.R.id.content))).getChildAt(0), Gravity.BOTTOM, 0, 0);
	}

	@Override
	public void dismiss() {
		TranslateAnimation animation = new TranslateAnimation(0, 0, 0, HEIGHT);
		animation.setDuration(350);
		parentLayout.startAnimation(animation);
		animation.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				dismissAction();
				System.out.println("dismissAction");
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}
		});
	}

	/**
	 * 调用父类的dismiss方法
	 */
	private void dismissAction() {
		super.dismiss();
	}

	/**
	 * 设置自定义字体
	 * 
	 * @param typeface
	 */
	public void setTypeface(Typeface typeface) {
		this.typeface = typeface;
	}

	/**
	 * 设置全局监听
	 * 
	 * @param onActionSheetClickListener
	 *            监听器
	 */
	public void setOnActionSheetClickListener(OnActionSheetClickListener onActionSheetClickListener) {
		this.onActionSheetClickListener = onActionSheetClickListener;
	}

	public interface OnSheetItemClickListener {
		void onClick();
	}

	public interface OnActionSheetClickListener {
		void onClick(int which);
	}

	public class SheetItem {
		String name;
		OnSheetItemClickListener itemClickListener;
		int color;

		public SheetItem(String name, int color, OnSheetItemClickListener itemClickListener) {
			this.name = name;
			this.color = color;
			this.itemClickListener = itemClickListener;
		}
	}

	public int getTextColor() {
		return textColor;
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
			// bg.addState(View.PRESSED_ENABLED_STATE_SET, pressed);
			bg.addState(View.PRESSED_ENABLED_STATE_SET, selected);
			bg.addState(View.ENABLED_FOCUSED_STATE_SET, selected);
			bg.addState(View.ENABLED_STATE_SET, normal);
			bg.addState(View.FOCUSED_STATE_SET, selected);
			bg.addState(View.EMPTY_STATE_SET, normal);
			return bg;
		}

	}

}
