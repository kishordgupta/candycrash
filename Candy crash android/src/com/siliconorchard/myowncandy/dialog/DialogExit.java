package com.siliconorchard.myowncandy.dialog;

import com.ironsource.mobilcore.CallbackResponse;
import com.ironsource.mobilcore.CallbackResponse.TYPE;
import com.ironsource.mobilcore.MobileCore.AD_UNITS;
import com.ironsource.mobilcore.MobileCore.LOG_TYPE;
import com.ironsource.mobilcore.MobileCore;
import com.siliconorchard.myowncandy.Menu;
import com.siliconorchard.myowncandy.R;
import com.siliconorchard.myowncandy.util.Util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;

public class DialogExit extends Dialog implements
		android.view.View.OnClickListener {

	Button button_yes, button_no;
	RelativeLayout dialog_exit;

	Activity mActivity;

	public DialogExit(Context context) {
		super(context);
		this.mActivity = (Activity) context;

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setBackgroundDrawableResource(R.drawable.bg_null);

	//	this.getWindow().getAttributes().windowAnimations = R.style.Animations_SmileWindow;
		MobileCore.init(this.mActivity ,"8YKF89U9YHXZUIMHFEQ0UDC1T9TOY", LOG_TYPE.DEBUG, AD_UNITS.ALL_UNITS);
		
		setContentView(R.layout.dialog_exit);

		dialog_exit = (RelativeLayout) findViewById(R.id.dialog_exit);
		Util.resizeDialog(dialog_exit, this.mActivity);

		button_yes = (Button) findViewById(R.id.button_yes);
		button_yes.setOnClickListener(this);

		button_no = (Button) findViewById(R.id.button_no);
		button_no.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		try {
			if (Menu.mSound != null)
				Menu.mSound.playHarp();

			switch (view.getId()) {
			case R.id.button_yes:
				 MobileCore.showOfferWall(this.mActivity, 
						 new CallbackResponse() { @Override
					 public void onConfirmation(TYPE type) { mActivity.finish(); } });
				
				this.dismiss();
				break;

			case R.id.button_no:
				this.dismiss();
				break;

			default:
				break;
			}
		} catch (Exception e) {
		}
	}

}