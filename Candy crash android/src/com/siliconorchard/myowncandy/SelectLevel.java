package com.siliconorchard.myowncandy;

import com.google.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.ironsource.mobilcore.MobileCore;
import com.ironsource.mobilcore.MobileCore.AD_UNITS;
import com.ironsource.mobilcore.MobileCore.LOG_TYPE;
import com.ironsource.mobilcore.OnReadyListener;
import com.siliconorchard.myowncandy.R;
import com.siliconorchard.myowncandy.adapter.ListViewAdapter;
import com.siliconorchard.myowncandy.adapter.StarAdapter;
import com.siliconorchard.myowncandy.adapter.ViewPagerAdapter;
import com.siliconorchard.myowncandy.control.ValueControl;
import com.siliconorchard.myowncandy.util.UtilActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SelectLevel extends MyApp implements OnClickListener {
	LinearLayout content;
	MySharedPreferences mMySharedPreferences;
	Button button_back, btn_next, btn_back;
	boolean finish = false;

	ViewPager mViewPager;
	ViewPagerAdapter mViewPagerAdapter;
	ListViewAdapter mListViewAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		UtilActivity.requestWindowFeature(this);
		//setContentView(R.layout.activity_select_level);
		MobileCore.init(this,"8YKF89U9YHXZUIMHFEQ0UDC1T9TOY", LOG_TYPE.DEBUG, AD_UNITS.ALL_UNITS);
		MobileCore.getSlider().setContentViewWithSlider(this, R.layout.activity_select_level);
		// Look up the AdView as a resource and load a request.
		 MobileCore.setStickeezReadyListener(new OnReadyListener()
		 { @Override 
			 public void onReady(AD_UNITS adUnit) {
			 if (adUnit.equals(AD_UNITS.STICKEEZ)){
				 //do something 
			
		 }
		 } }
		 );
		 MobileCore.showStickee(this);
		
/*		AdView adView = (AdView) this.findViewById(R.id.admob);
		adView.loadAd(new AdRequest());

		// Look up the AdView as a resource and load a request.
		AdView adVieww = (AdView) this.findViewById(R.id.admobb);
		adVieww.loadAd(new AdRequest());
*/
		mMySharedPreferences = new MySharedPreferences(this);

		// Thêm phần quảng cáo
		// addAdmob();

		// addAdBanner();

		button_back = (Button) findViewById(R.id.button_back);
		button_back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (Menu.mSound != null)
					Menu.mSound.playHarp();
				SelectLevel.this.finish();
			}
		});

		btn_next = (Button) findViewById(R.id.btn_next);
		btn_next.setOnClickListener(this);

		btn_back = (Button) findViewById(R.id.btn_back);
		btn_back.setOnClickListener(this);
	}

	@Override
	protected void onResume() {
		super.onResume();

		mListViewAdapter = new ListViewAdapter(this);
		mViewPager = (ViewPager) findViewById(R.id.my_view_pager);
		mViewPagerAdapter = new ViewPagerAdapter(this, mListViewAdapter);
		mViewPager.setAdapter(mViewPagerAdapter);

		addContentTimeAttach();
		addContentClassic();
		addContentNewMode();

		switch (ValueControl.TypeGame) {
		case ValueControl.TimeAttach:
			mViewPager.setCurrentItem(0);
			break;
		case ValueControl.Classic:
			mViewPager.setCurrentItem(1);
			break;
		case ValueControl.NewMode:
			mViewPager.setCurrentItem(2);
			break;
		default:
			break;
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		finish = true;
	}

	public void addContentTimeAttach() {
		GridView gridview = (GridView) mListViewAdapter.getViewPosition(0)
				.findViewById(R.id.gridview);
		StarAdapter mStarAdapter = new StarAdapter(this);
		gridview.setAdapter(mStarAdapter);
	}

	public void addContentClassic() {
		Button btn_play = (Button) mListViewAdapter.getViewPosition(1)
				.findViewById(R.id.btn_play);
		btn_play.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (Menu.mSound != null)
					Menu.mSound.playHarp();
				ValueControl.TypeGame = ValueControl.Classic;
				mMySharedPreferences.updateTypeGame(ValueControl.TypeGame);
				nextMainGame();
			}
		});

		TextView txt_level = (TextView) mListViewAdapter.getViewPosition(1)
				.findViewById(R.id.txt_level);
		txt_level.setText("Level: " + Level.levelCurrentClassic + "/"
				+ Level.numberCoinLevelClassic.length);

		TextView txt_coin = (TextView) mListViewAdapter.getViewPosition(1)
				.findViewById(R.id.txt_coin);
		txt_coin.setText("Coin: " + Level.coinLevelCurrentClassic + "/"
				+ Level.numberCoinLevelClassic[Level.levelCurrentClassic - 1]);
	}

	public void addContentNewMode() {
		Button btn_play_new_mode = (Button) mListViewAdapter.getViewPosition(2)
				.findViewById(R.id.btn_play_new_mode);
		btn_play_new_mode.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (Menu.mSound != null)
					Menu.mSound.playHarp();
				ValueControl.TypeGame = ValueControl.NewMode;
				mMySharedPreferences.updateTypeGame(ValueControl.TypeGame);
				nextMainGame();
			}
		});

		TextView txt_level = (TextView) mListViewAdapter.getViewPosition(2)
				.findViewById(R.id.txt_level);
		txt_level.setText("Level: " + Level.levelCurrentNewMode + "/"
				+ Level.totalLevelSquare);
	}

	public void nextMainGame() {
		Intent mIntent = new Intent(this, MainGame.class);
		startActivity(mIntent);
	}

	@Override
	public void onClick(View v) {
		if (Menu.mSound != null)
			Menu.mSound.playHarp();
		switch (v.getId()) {
		case R.id.btn_back:
			if (mViewPager.getCurrentItem() > 0)
				mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1);
			break;
		case R.id.btn_next:
			if (mViewPager.getCurrentItem() < mViewPager.getChildCount())
				mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
			break;
		default:
			break;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (Menu.mSound != null)
				Menu.mSound.playHarp();
			this.finish();
		}
		return false;
	}
}