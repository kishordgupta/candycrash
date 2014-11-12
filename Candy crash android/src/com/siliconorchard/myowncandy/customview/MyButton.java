package com.siliconorchard.myowncandy.customview;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

public class MyButton extends Button {

	public Typeface FONT_NAME = null;

	public MyButton(Context context) {
		super(context);
		setFont(context);
	}

	public MyButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		setFont(context);
	}

	public MyButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		setFont(context);
	}

	public void setFont(Context context) {
		if (FONT_NAME == null)
			FONT_NAME = Typeface.createFromAsset(context.getAssets(),
					"font/UVNBUTLONG1.TTF");
		this.setTypeface(FONT_NAME);
	}
}