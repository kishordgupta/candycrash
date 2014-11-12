package com.siliconorchard.myowncandy.dialog;

import com.ironsource.mobilcore.CallbackResponse;
import com.ironsource.mobilcore.MobileCore;
import com.ironsource.mobilcore.CallbackResponse.TYPE;
import com.siliconorchard.myowncandy.Level;
import com.siliconorchard.myowncandy.MainGame;
import com.siliconorchard.myowncandy.Menu;
import com.siliconorchard.myowncandy.MySharedPreferences;
import com.siliconorchard.myowncandy.R;
import com.siliconorchard.myowncandy.control.ValueControl;
import com.siliconorchard.myowncandy.util.Util;

import android.app.Dialog;
import android.content.Context;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;

public class DialogCompleted extends Dialog implements
		android.view.View.OnClickListener {
	Button button_next, button_replay, button_exit;
	RelativeLayout dialog_completed;
	MainGame mainGame;
	MySharedPreferences mySharedPreferences;

	public DialogCompleted(Context context) {
		super(context);
		this.mainGame = (MainGame) context;
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setBackgroundDrawableResource(R.drawable.bg_null);
		//this.getWindow().getAttributes().windowAnimations = R.style.Animations_SmileWindow;

		setContentView(R.layout.dialog_completed);

		mySharedPreferences = new MySharedPreferences(context);

		dialog_completed = (RelativeLayout) findViewById(R.id.dialog_completed);
		Util.resizeDialog(dialog_completed, this.mainGame);
		this.setCancelable(false);

		button_next = (Button) findViewById(R.id.button_next);
		button_next.setOnClickListener(this);

		if (ValueControl.TypeGame == ValueControl.TimeAttach) {
			if (Level.levelCurrent >= Level.maxLevel) {
				Level.levelCurrent = Level.maxLevel;
				button_next.setVisibility(View.GONE);
			}
		} else if (ValueControl.TypeGame == ValueControl.Classic) {
			if (Level.levelCurrentClassic >= Level.numberCoinLevelClassic.length) {
				Level.levelCurrentClassic = Level.numberCoinLevelClassic.length;
				button_next.setVisibility(View.GONE);
			}
		} else if (ValueControl.TypeGame == ValueControl.NewMode) {
			if (Level.levelCurrentNewMode >= Level.totalLevelSquare) {
				Level.levelCurrentNewMode = Level.totalLevelSquare;
				button_next.setVisibility(View.GONE);
			}
		}

		button_replay = (Button) findViewById(R.id.button_replay);
		button_replay.setOnClickListener(this);

		button_exit = (Button) findViewById(R.id.button_exit);
		button_exit.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		if (Menu.mSound != null)
			Menu.mSound.playHarp();
		switch (view.getId()) {

		case R.id.button_next:
			if (ValueControl.TypeGame == ValueControl.TimeAttach) {
				Level.levelCurrent++;
				mySharedPreferences.updateLevelCurrent(Level.levelCurrent);
			} else if (ValueControl.TypeGame == ValueControl.Classic) {
				Level.levelCurrentClassic++;
				Level.coinLevelCurrentClassic = 0;
				mySharedPreferences
						.updateLevelCurrentClassic(Level.levelCurrentClassic);
			} else if (ValueControl.TypeGame == ValueControl.NewMode) {
				Level.levelCurrentNewMode++;
				Level.squareCurrent = 0;
				Level.totalSquare = Level
						.getTotalSquare(Level.levelCurrentNewMode - 1);
				mySharedPreferences
						.updateLevelCurrentNewMode(Level.levelCurrentNewMode);
			}
			 MobileCore.showOfferWall(this.mainGame, 
					 new CallbackResponse() { @Override
				 public void onConfirmation(TYPE type) { 	mainGame.resetGame(); } });
		
			this.dismiss();
			break;

		case R.id.button_replay:
			mainGame.resetGame();
			this.dismiss();
			break;

		case R.id.button_exit:

			// Lưu lại số coin đang chơi và level
			if (ValueControl.TypeGame == ValueControl.TimeAttach) {
				if (Level.levelCurrent + 1 < Level.maxLevel)
					Level.levelCurrent++;
				mySharedPreferences.updateLevelCurrent(Level.levelCurrent);
			} else if (ValueControl.TypeGame == ValueControl.Classic) {
				if (Level.levelCurrentClassic + 1 < Level.numberCoinLevelClassic.length)
					Level.levelCurrentClassic++;
				Level.coinLevelCurrentClassic = 0;
				mySharedPreferences
						.updateCoinLevelCurrentClassic(Level.coinLevelCurrentClassic);
				mySharedPreferences
						.updateLevelCurrentClassic(Level.levelCurrentClassic);
			} else if (ValueControl.TypeGame == ValueControl.NewMode) {
				if (Level.levelCurrentNewMode + 1 < Level.totalLevelSquare)
					Level.levelCurrentNewMode++;
				mySharedPreferences
						.updateLevelCurrentNewMode(Level.levelCurrentNewMode);
			}
			/* MobileCore.showOfferWall(this.mainGame, 
					 new CallbackResponse() { @Override
				 public void onConfirmation(TYPE type) { 	*/mainGame.finish(); //} });
			
			this.dismiss();
			break;
		default:
			break;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			return false;
		}
		return false;
	}

}