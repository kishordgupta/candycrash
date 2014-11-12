package com.siliconorchard.myowncandy;


import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.opengl.view.RenderSurfaceView;
import org.anddev.andengine.ui.activity.BaseGameActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.siliconorchard.myowncandy.config.MyConfig;



import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.FrameLayout;

public class Game extends BaseGameActivity {
	public Camera mCamera = null;
	public Context mContext;
	public Engine mEngine = null;
	public Scene mScene = null;

	@Override
	protected void onCreate(Bundle pSavedInstanceState) {
		super.onCreate(pSavedInstanceState);

		mContext = this;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onLoadComplete() {
		// TODO Auto-generated method stub

	}

	@Override
	public Engine onLoadEngine() {
		this.mCamera = new Camera(0, 0, MyConfig.SCREENWIDTH,
				MyConfig.SCREENHIEGHT);
		this.mEngine = new Engine(new EngineOptions(true,
				MyConfig.ScreenOrientation_Default, new RatioResolutionPolicy(
						MyConfig.SCREENWIDTH, MyConfig.SCREENHIEGHT),
				this.mCamera).setNeedsSound(true).setNeedsMusic(true));

		// // --------------------------------------------------
		// // Yêu cầu sử lý đa chạm
		// try {
		// if (MultiTouch.isSupported(this)) {
		// this.mEngine.setTouchController(new MultiTouchController());
		// if (MultiTouch.isSupportedDistinct(this)) { // Thiết bị có hỗ
		// // trợ.
		// System.out.println("Support Multitouch");
		// } else {
		// System.out.println("Not Support Multitouch");
		// }
		// } else {
		// System.out.println("Not Support Multitouch");
		// }
		// } catch (Exception e) {
		// System.out.println("Not Support Multitouch");
		// }
		// // --------------------------------------------------
		return mEngine;
	}
@Override
protected void onSetContentView() {
	// TODO Auto-generated method stub
	  final FrameLayout frameLayout = new FrameLayout(this);

	AdView adView = new AdView(this);
    adView.setAdSize(AdSize.BANNER);
    adView.setAdUnitId("ca-app-pub-2884314377778347/2857853914");

    adView.refreshDrawableState();
    adView.setVisibility(AdView.VISIBLE);
//    LinearLayout layout = (LinearLayout) findViewById(R.id.admob);
  //  layout.addView(adView);
    final FrameLayout.LayoutParams adViewLayoutParams =
            new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,
                                         FrameLayout.LayoutParams.WRAP_CONTENT,
                                         Gravity.CENTER_HORIZONTAL|Gravity.BOTTOM);
    final FrameLayout.LayoutParams frameLayoutLayoutParams =
            new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                                         FrameLayout.LayoutParams.MATCH_PARENT);


    // Create an ad request. Check logcat output for the hashed device ID to
    // get test ads on a physical device.
    AdRequest adRequest = new AdRequest.Builder()
        .build();

    // Start loading the ad in the background.
    adView.loadAd(adRequest);
    this.mRenderSurfaceView = new RenderSurfaceView(this);
    mRenderSurfaceView.setRenderer(mEngine);
    // SURFACE layout ? //
    final android.widget.FrameLayout.LayoutParams surfaceViewLayoutParams =
            new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.MATCH_PARENT);

    // ADD the surface view and adView to the frame //
    frameLayout.addView(this.mRenderSurfaceView, surfaceViewLayoutParams);
    frameLayout.addView(adView, adViewLayoutParams);

    // SHOW AD //
    this.setContentView(frameLayout, frameLayoutLayoutParams);
}
	@Override
	public void onLoadResources() {

	}

	@Override
	public Scene onLoadScene() {
		mScene = new Scene();
		mScene.setTouchAreaBindingEnabled(true);
		return this.mScene;
	}

}