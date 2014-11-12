package com.siliconorchard.myowncandy;


import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TextureRegion;

import com.siliconorchard.myowncandy.config.MyConfig;
import com.siliconorchard.myowncandy.control.ValueControl;
import com.siliconorchard.myowncandy.util.Util;



import android.content.Context;

public class BackGround extends MySprite {
	private BitmapTextureAtlas bg_bottom_BTA;
	private Sprite bg_bottom_SP;
	private TextureRegion bg_bottom_TR;

	private BitmapTextureAtlas bg_BTA;
	private Sprite bg_SP;
	private BitmapTextureAtlas bg_top_BTA;

	private Sprite bg_top_SP;
	private TextureRegion bg_top_TR;
	private TextureRegion bg_TR;

	private BitmapTextureAtlas bigcoin_BTA;
	private Sprite bigcoin_SP;
	private TextureRegion bigcoin_TR;

	private BitmapTextureAtlas square_BTA;
	private Sprite square_SP;
	private TextureRegion square_TR;

	int Y_BOTTOM = 0;
	int Y_TOP = 0;

	// -----------------------------------------------------
	public Sprite getBigCoin() {
		if (ValueControl.TypeGame != ValueControl.NewMode)
			return bigcoin_SP;
		else
			return square_SP;
	}

	// -----------------------------------------------------
	public void onLoadResources(Engine mEngine, Context mContext) {
		this.mEngine = mEngine;
		this.mContext = mContext;
		this.mainGame = (MainGame) mContext;

		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("image/");

		//int bg = Util.getRandomIndex(1, 4);

		this.bg_BTA = new BitmapTextureAtlas(512, 1024,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.bg_TR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				this.bg_BTA, mContext, "bg_1" +  ".jpg", 0, 0);
		mEngine.getTextureManager().loadTextures(this.bg_BTA);

		this.bg_top_BTA = new BitmapTextureAtlas(512, 512,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.bg_top_TR = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(this.bg_top_BTA, mContext, "topwallpaper.png",
						0, 0);
		mEngine.getTextureManager().loadTextures(this.bg_top_BTA);

		this.bg_bottom_BTA = new BitmapTextureAtlas(512, 512,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.bg_bottom_TR = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(this.bg_bottom_BTA, mContext,
						"bottomwallpaper.png", 0, 0);
		mEngine.getTextureManager().loadTextures(this.bg_bottom_BTA);

		if (ValueControl.TypeGame == ValueControl.NewMode) {
			this.square_BTA = new BitmapTextureAtlas(128, 128,
					TextureOptions.BILINEAR_PREMULTIPLYALPHA);
			this.square_TR = BitmapTextureAtlasTextureRegionFactory
					.createFromAsset(this.square_BTA, mContext,
							"square_big.png", 0, 0);
			mEngine.getTextureManager().loadTextures(this.square_BTA);
		} else {
			this.bigcoin_BTA = new BitmapTextureAtlas(128, 128,
					TextureOptions.BILINEAR_PREMULTIPLYALPHA);
			this.bigcoin_TR = BitmapTextureAtlasTextureRegionFactory
					.createFromAsset(this.bigcoin_BTA, mContext, "bigcoin.png",
							0, 0);
			mEngine.getTextureManager().loadTextures(this.bigcoin_BTA);
		}
	}

	// -----------------------------------------------------
	public void onLoadScene(Scene mScene) {
		this.mScene = mScene;
		// Cảnh nền
		this.bg_SP = new Sprite(0, 0, MyConfig.SCREENWIDTH,
				MyConfig.SCREENHIEGHT, this.bg_TR);
		mScene.attachChild(bg_SP);

		Y_TOP = (int) (MyConfig.HEIGHT_TOP * MyConfig.getRaceHeight());
		float h = (int) (bg_top_TR.getHeight() * MyConfig.getRaceHeight());

		this.bg_top_SP = new Sprite(0, 0, MyConfig.SCREENWIDTH, h,
				this.bg_top_TR);
		mScene.attachChild(bg_top_SP);

		h = (int) (bg_bottom_TR.getHeight() * MyConfig.getRaceHeight());
		Y_BOTTOM = (int) (MyConfig.SCREENHIEGHT - h);

		this.bg_bottom_SP = new Sprite(0, Y_BOTTOM, MyConfig.SCREENWIDTH, h,
				this.bg_bottom_TR);
		mScene.attachChild(bg_bottom_SP);

		if (ValueControl.TypeGame == ValueControl.NewMode) {
			int y = (int) ((MyConfig.HEIGHT_TOP / 2 - square_TR.getHeight() / 2) * MyConfig
					.getRaceHeight());
			int w = (int) (square_TR.getWidth() * MyConfig.RACE_WIDTH);
			h = w * square_TR.getHeight() / square_TR.getWidth();
			this.square_SP = new Sprite(0, y, w, h, this.square_TR);
			mScene.attachChild(square_SP);
		} else {
			int y = (int) ((MyConfig.HEIGHT_TOP / 2 - bigcoin_TR.getHeight() / 2) * MyConfig
					.getRaceHeight());
			int w = (int) (bigcoin_TR.getWidth() * MyConfig.RACE_WIDTH);
			h = w * bigcoin_TR.getHeight() / bigcoin_TR.getWidth();
			this.bigcoin_SP = new Sprite(0, y, w, h, this.bigcoin_TR);
			mScene.attachChild(bigcoin_SP);
		}
	}
}