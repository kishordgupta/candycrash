package com.siliconorchard.myowncandy.dialog;

import com.ironsource.mobilcore.CallbackResponse;
import com.ironsource.mobilcore.MobileCore;
import com.ironsource.mobilcore.CallbackResponse.TYPE;
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
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class DialogPause extends Dialog implements
		android.view.View.OnClickListener {
	int action = -1;

	Button button_continue, button_menu, button_reset, button_playagain;
	RelativeLayout dialog_pause;

	ImageView imageView_sound, imageView_music;

	MainGame mainGame;

	MySharedPreferences mySharedPreferences;

	public DialogPause(Context context, int action) {
		super(context);
		this.mainGame = (MainGame) context;
		this.action = action;

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setBackgroundDrawableResource(R.drawable.bg_null);

	//	this.getWindow().getAttributes().windowAnimations = R.style.Animations_SmileWindow;

		setContentView(R.layout.dialog_pause);

		dialog_pause = (RelativeLayout) findViewById(R.id.dialog_pause);
		Util.resizeDialog(dialog_pause, this.mainGame);

		this.setCancelable(false);

		mySharedPreferences = new MySharedPreferences(context);
		mySharedPreferences.getIsMusic();
		mySharedPreferences.getIsSound();

		imageView_sound = (ImageView) findViewById(R.id.imageView_sound);
		imageView_sound.setOnClickListener(this);

		imageView_music = (ImageView) findViewById(R.id.imageView_music);
		imageView_music.setOnClickListener(this);

		button_continue = (Button) findViewById(R.id.button_continue);
		button_continue.setOnClickListener(this);

		button_reset = (Button) findViewById(R.id.button_reset);
		button_reset.setOnClickListener(this);

		button_menu = (Button) findViewById(R.id.button_menu);
		button_menu.setOnClickListener(this);

		button_playagain = (Button) findViewById(R.id.button_playagain);
		button_playagain.setOnClickListener(this);

		// Pause game
		if (action == 0) {
			button_playagain.setVisibility(View.GONE);
		} else {
			button_reset.setVisibility(View.GONE);
			button_continue.setVisibility(View.GONE);
		}

		if (ValueControl.isSound == true) {
			imageView_sound.setImageResource(R.drawable.soundon);
		} else {
			imageView_sound.setImageResource(R.drawable.soundoff);
		}

		if (ValueControl.isMusic == true) {
			imageView_music.setImageResource(R.drawable.music_on);
		} else {
			imageView_music.setImageResource(R.drawable.music_off);
		}
	}

	@Override
	public void onClick(View view) {
		if (Menu.mSound != null)
			Menu.mMusicBackground.pause();

		switch (view.getId()) {

		case R.id.button_continue:
			
			/*MobileCore.showOfferWall(this.mainGame, 
					 new CallbackResponse() { @Override
				 public void onConfirmation(TYPE type) { */		mainGame.resumeGame();//} });
		
			this.dismiss();
			break;

		case R.id.button_menu:
		/*	MobileCore.showOfferWall(this.mainGame, 
					 new CallbackResponse() { @Override
				 public void onConfirmation(TYPE type) { 		
					*/		mainGame.finish();//} });
		
			this.dismiss();
			break;

		case R.id.button_reset:/*
			MobileCore.showOfferWall(this.mainGame, 
					 new CallbackResponse() { @Override
				 public void onConfirmation(TYPE type) { 		*/mainGame.resetGame();//} });
		
			this.dismiss();
			break;

		case R.id.button_playagain:
			/*MobileCore.showOfferWall(this.mainGame, 
					 new CallbackResponse() { @Override
				 public void onConfirmation(TYPE type) { 	*/	mainGame.resetGame();//} });
		
			this.dismiss();
			break;

		case R.id.imageView_sound:
			updateSoundIcon();
			break;

		case R.id.imageView_music:
			updateMusicIcon();
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

	public void updateMusicIcon() {
		if (ValueControl.isMusic == true) {
			mySharedPreferences.updateIsMusic(false);
			imageView_music.setImageResource(R.drawable.music_off);
			if (Menu.mMusicBackground != null)
				Menu.mMusicBackground.pause();
		} else {
			mySharedPreferences.updateIsMusic(true);
			imageView_music.setImageResource(R.drawable.music_on);
			if (Menu.mMusicBackground != null)
				Menu.mMusicBackground.resume();
		}
	}

	public void updateSoundIcon() {
		if (ValueControl.isSound == true) {
			mySharedPreferences.updateIsSound(false);
			imageView_sound.setImageResource(R.drawable.soundoff);
		} else {
			mySharedPreferences.updateIsSound(true);
			imageView_sound.setImageResource(R.drawable.soundon);
		}
	}
}