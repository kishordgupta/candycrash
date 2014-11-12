package com.siliconorchard.myowncandy;

//import gioi.developer.mylib.ShowDialogDownloadGame;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.ironsource.mobilcore.MobileCore;
import com.ironsource.mobilcore.OnReadyListener;
import com.ironsource.mobilcore.MobileCore.AD_UNITS;
import com.ironsource.mobilcore.MobileCore.LOG_TYPE;
import com.siliconorchard.myowncandy.R;
import com.siliconorchard.myowncandy.control.ValueControl;
import com.siliconorchard.myowncandy.dialog.DialogExit;
import com.siliconorchard.myowncandy.sound.MusicBackground;
import com.siliconorchard.myowncandy.sound.Sound;

public class Menu extends MyApp implements OnClickListener {

	public static MusicBackground mMusicBackground;
	public static Sound mSound;
	Button play, more, score;
	ImageView imageView_sound, imageView_music, imageView_rate;
	Animation mAnimation_in_left, mAnimation_in_right, mAnimation_out_left,
			mAnimation_out_right;

	MySharedPreferences mySharedPreferences;

	String resultText = "Angry Bird Blast";

	// ShowDialogDownloadGame mShowDialogDownloadGame;
//	public RevMob revmob;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// MyConfig.getDisplayScreen(this);
	//	setContentView(R.layout.menu);
		MobileCore.init(this,"8YKF89U9YHXZUIMHFEQ0UDC1T9TOY", LOG_TYPE.DEBUG, AD_UNITS.ALL_UNITS);
		MobileCore.getSlider().setContentViewWithSlider(this, R.layout.menu);
		// Look up the AdView as a resource and load a request.
		 MobileCore.setStickeezReadyListener(new OnReadyListener()
		 { @Override 
			 public void onReady(AD_UNITS adUnit) {
			 if (adUnit.equals(AD_UNITS.STICKEEZ)){
				 //do something 
				 MobileCore.showStickee(Menu.this);
		 }
		 } }
		 );
		 MobileCore.showStickee(this);
		// Look up the AdView as a resource and load a request.
//		AdView adView = (AdView) this.findViewById(R.id.admob);
//		adView.loadAd(new AdRequest());

		// mShowDialogDownloadGame = new ShowDialogDownloadGame(this, true);

		// Thêm phần quảng cáo
		// addAdmob();

		// addAdBanner();
//		revmob = RevMob.start(this);

		mSound = null;
		mSound = new Sound();
		mMusicBackground = null;
		mMusicBackground = new MusicBackground();
		mySharedPreferences = new MySharedPreferences(this);

		mySharedPreferences.getIsMusic();
		mySharedPreferences.getIsSound();
		mySharedPreferences.getLevelUnlock();
		mySharedPreferences.getTypeGame();
		mySharedPreferences.getLevelClassic();
		mySharedPreferences.getLevelNewMode();

		mSound.loadSound(this);
		mMusicBackground.loadMusic(this);

		play = (Button) findViewById(R.id.play);
		play.setOnClickListener(this);

		score = (Button) findViewById(R.id.score);
		score.setOnClickListener(this);

		more = (Button) findViewById(R.id.more);
		more.setOnClickListener(this);

		imageView_sound = (ImageView) findViewById(R.id.imageView_sound);
		imageView_sound.setOnClickListener(this);

		imageView_music = (ImageView) findViewById(R.id.imageView_music);
		imageView_music.setOnClickListener(this);

	/*	imageView_rate = (ImageView) findViewById(R.id.imageView_rate);
		imageView_rate.setOnClickListener(this);*/

		changIconMusicSound(true);

		// Quảng cáo
		// AndroidSDKProvider.initSDK(this);
	}

	/*
	 * Share Button Clicked
	 */
	public void btnShareClicked(View view) {
		Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
		shareIntent.setType("text/plain");
		shareIntent
				.putExtra(
						android.content.Intent.EXTRA_TEXT,
						resultText
								+ ". via https://play.google.com/store/apps/details?id=com.siliconorchard.myowncandy");
		view.getContext().startActivity(
				Intent.createChooser(shareIntent, "Share via"));
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mSound = null;
		mMusicBackground.release();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (mSound != null)
				mSound.playHarp();

			// if (!mShowDialogDownloadGame.showDialog())
			showDialogExit();
		}
		return false;
	}

	@Override
	protected void onResume() {
		super.onResume();


		
		mAnimation_in_left = AnimationUtils.loadAnimation(this,
				R.anim.slide_in_left);
		mAnimation_in_right = AnimationUtils.loadAnimation(this,
				R.anim.slide_in_right);
		mAnimation_out_left = AnimationUtils.loadAnimation(this,
				R.anim.slide_out_left);
		mAnimation_out_right = AnimationUtils.loadAnimation(this,
				R.anim.slide_out_right);

		play.startAnimation(mAnimation_in_left);
		more.startAnimation(mAnimation_in_right);

		changIconMusicSound(false);

		
	}

	public void animationOut() {
		play.startAnimation(mAnimation_out_left);
		score.startAnimation(mAnimation_out_right);
		more.startAnimation(mAnimation_out_left);

		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(550);
					nextSelectLevel();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	public void changIconMusicSound(boolean check) {
		if (ValueControl.isSound == true) {
			imageView_sound.setImageResource(R.drawable.soundon);
		} else {
			imageView_sound.setImageResource(R.drawable.soundoff);
		}

		if (ValueControl.isMusic == true) {
			if (check)
				mMusicBackground.play();
			imageView_music.setImageResource(R.drawable.music_on);
		} else {
			imageView_music.setImageResource(R.drawable.music_off);
		}
	}

	public void nextSelectLevel() {
		Intent mIntent = new Intent(this, SelectLevel.class);
		this.startActivity(mIntent);
	}

	@Override
	public void onClick(View v) {
		if (Menu.mSound != null)
			mSound.playHarp();

		switch (v.getId()) {
		case R.id.play:
			// animationOut();
			nextSelectLevel();
			break;
		case R.id.score:
			break;
		case R.id.more:
			nextApplicationOther();
			break;
		case R.id.imageView_rate:
			nextRate();
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

	public void showDialogExit() {
		DialogExit mDialogExit = new DialogExit(this);
		mDialogExit.show();
	}

	// ------------------------------------------------------
	public void nextApplicationOther() {
		try {
			Intent marketIntent = new Intent(
					Intent.ACTION_VIEW,
					Uri.parse("https://play.google.com/store/apps/developer?id=quizgrandmasters"));
			startActivity(marketIntent);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void nextRate() {
		try {
			Intent marketIntent = new Intent(
					Intent.ACTION_VIEW,
					Uri.parse("https://play.google.com/store/apps/details?id=com.siliconorchard.myowncandy"));
			startActivity(marketIntent);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void updateMusicIcon() {
		if (ValueControl.isMusic == true) {
			mySharedPreferences.updateIsMusic(false);
			imageView_music.setImageResource(R.drawable.music_off);
			mMusicBackground.pause();
		} else {
			mySharedPreferences.updateIsMusic(true);
			imageView_music.setImageResource(R.drawable.music_on);
			mMusicBackground.resume();
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