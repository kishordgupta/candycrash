package com.siliconorchard.myowncandy.control;


import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.entity.modifier.LoopEntityModifier;
import org.anddev.andengine.entity.modifier.RotationModifier;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TextureRegion;

import com.siliconorchard.myowncandy.MainGame;
import com.siliconorchard.myowncandy.Menu;
import com.siliconorchard.myowncandy.MySprite;
import com.siliconorchard.myowncandy.config.MyConfig;
import com.siliconorchard.myowncandy.dialog.DialogPause;



import android.content.Context;

public class ButtonPauseGame extends MySprite {
	private MainGame mainGame;
	private BitmapTextureAtlas pause_BTA;
	private Sprite pause_SP;
	private TextureRegion pause_TR;

	// ---------------------------------------------------------------------------
	public void onLoadResources(Engine mEngine, Context mContext,
			MainGame mainGame) {
		this.mEngine = mEngine;
		this.mContext = mContext;
		this.mainGame = mainGame;

		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("image/");
		this.pause_BTA = new BitmapTextureAtlas(128, 128,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.pause_TR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				this.pause_BTA, mContext, "pause.png", 0, 0);
		mEngine.getTextureManager().loadTextures(this.pause_BTA);
	}

	// ---------------------------------------------------------------------------
	public void onLoadScene(Scene mScene) {
		this.mScene = mScene;

		int w = (int) (pause_TR.getWidth() * MyConfig.getRaceWidth());
		int h = pause_TR.getHeight() * w / pause_TR.getWidth();

		int x = (MyConfig.SCREENWIDTH / 2) - (w / 2);
		int y = MyConfig.SCREENHIEGHT - h
				- (int) (10 * MyConfig.getRaceHeight());

		this.pause_SP = new Sprite(x, y, w, h, this.pause_TR) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {

				if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN
						&& ValueControl.isTouchJewel) {
					showDialogPause();
				}
				return true;
			}
		};
		this.mScene.attachChild(pause_SP);
		this.mScene.registerTouchArea(pause_SP);

		// Xoay button pause
		setRotation();
	}

	// ---------------------------------------------------------------------------
	// Xoay button pause
	public void setRotation() {
		try {
			RotationModifier mRotationModifier = new RotationModifier(10, 0,
					360);
			LoopEntityModifier mLoopEntityModifier = new LoopEntityModifier(
					mRotationModifier);
			this.pause_SP.registerEntityModifier(mLoopEntityModifier);
		} catch (Exception e) {
		}
	}

	// ---------------------------------------------------------------------------
	public void showDialogPause() {
		if (ValueControl.isPauseGame == false) {
			try {
				if (Menu.mSound != null)
					Menu.mSound.playHarp();
				mainGame.pauseGame();
				DialogPause mDialogPause = new DialogPause(mContext, 0);
				mDialogPause.show();
			} catch (Exception e) {
			}
		}
	}
}