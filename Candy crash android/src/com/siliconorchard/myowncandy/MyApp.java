package com.siliconorchard.myowncandy;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.google.ads.*;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.AdRequest;
import com.siliconorchard.myowncandy.util.Util;
import com.siliconorchard.myowncandy.util.UtilActivity;

public class MyApp extends Activity {
	AdView	adView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		UtilActivity.requestWindowFeature(this);
		adView = new AdView(this);
	    adView.setAdSize(AdSize.BANNER);
	    adView.setAdUnitId("ca-app-pub-2884314377778347/1381120712");
//	    LinearLayout layout = (LinearLayout) findViewById(R.id.admob);
	  //  layout.addView(adView);

	    // Create an ad request. Check logcat output for the hashed device ID to
	    // get test ads on a physical device.
	    AdRequest adRequest = new AdRequest.Builder()
	        .build();

	    // Start loading the ad in the background.
	    adView.loadAd(adRequest);
	}

	// -------------------------------------------------
	/**
	 * Load thêm phần quảng cáo
	 */
	// public void addAdmob() {
	// try {
	// LinearLayout admob = (LinearLayout) findViewById(R.id.admob);
	// if (admob != null) {
	// adView = new AdView(this, AdSize.BANNER, MyConfig.keyAdmob);
	// admob.addView(adView);
	// adView.loadAd(new AdRequest());
	// }
	// } catch (Exception e) {
	// }
	// }

	// -------------------------------------------------
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		try {
			if (adView != null)
				adView.destroy();
		} catch (Exception e) {
		}
	}

	// -------------------------------------------------
	public void showDialogDownloadGamePiakchu() {
		if (!Util.appInstalledOrNot("candy.developer.jewels_new_pro", this)) {
			AlertDialog.Builder md = new AlertDialog.Builder(this);
			md.setTitle("Tải game miễn phí");
			md.setMessage("Tải game Candy Crush Saga miễn phí");
			md.setNegativeButton("Đồng ý",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface arg0, int arg1) {
							try {
								Intent marketIntent = new Intent(
										Intent.ACTION_VIEW,
										Uri.parse("https://play.google.com/store/apps/details?id=candy.developer.jewels_new_pro"));
								startActivity(marketIntent);
							} catch (Exception e) {
								// TODO: handle exception
							}
						}
					});
			md.setPositiveButton("Đóng", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					MyApp.this.finish();
				}
			});
			md.show();
		} else
			MyApp.this.finish();
	}

	// -------------------------------------------------
	// public void addAdBanner() {
	// try {
	// ImageView imageView_adBanner = (ImageView)
	// findViewById(R.id.imageView_adBanner);
	// AdBanner mAdBanner = new AdBanner(this, imageView_adBanner);
	// } catch (Exception e) {
	//
	// }
	// }
}