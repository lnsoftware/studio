package com.imxiaomai.convenience.store.scan.widget;

import android.content.Context;
import android.text.Html;
import android.text.Spanned;
import android.util.AttributeSet;
import android.widget.TextView;

public class HtmlTextView extends TextView {
	public HtmlTextView(Context context) {
		super(context);
	}

	public HtmlTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public HtmlTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	@Override
	public void setText(CharSequence text, BufferType type) {
		Spanned spanned = Html.fromHtml(text.toString());
		super.setText(spanned, type);
	}
}
