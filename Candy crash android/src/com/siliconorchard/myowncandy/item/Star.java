package com.siliconorchard.myowncandy.item;


import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TextureRegion;

import com.siliconorchard.myowncandy.MySprite;
import com.siliconorchard.myowncandy.config.MyConfig;
import com.siliconorchard.myowncandy.util.Util;



import android.content.Context;

public class Star extends MySprite {
	int max = 10;
	private BitmapTextureAtlas star_BTA;

	private TextureRegion star_TR;

	int timedelay = 20;

	public void addStar(final int x, final int y) {
		show1(x, y);
	}

	public TextureRegion getTextureRegion() {
		return star_TR;
	}

	public void onLoadResources(Engine mEngine, Context mContext) {
		this.mEngine = mEngine;
		this.mContext = mContext;

		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("image/");
		this.star_BTA = new BitmapTextureAtlas(64, 64,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.star_TR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				this.star_BTA, mContext, "particleSparkle.png", 0, 0);
		mEngine.getTextureManager().loadTextures(this.star_BTA);
	}

	public void onLoadScene(Scene mScene) {
		this.mScene = mScene;
	}

	public void removeStar(final Sprite[] sp) {
		mEngine.runOnUpdateThread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < sp.length; i++)
					mScene.detachChild(sp[i]);
			}
		});
	}

	public void show1(final int x, final int y) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				Sprite[] star_SP = new Sprite[3];
				int min = -MyConfig.WIDTH_SQUARE / 2;
				int max = MyConfig.WIDTH_SQUARE / 2;

				int w = (int) (star_TR.getWidth() * MyConfig.getRaceWidth());
				int h = star_TR.getHeight() * w / star_TR.getWidth();

				for (int i = 0; i < star_SP.length; i++) {

					int x_tmp = Util.getRandomIndex(min, max);
					x_tmp = x + x_tmp;

					int y_tmp = Util.getRandomIndex(min, max);
					y_tmp = y + y_tmp;

					star_SP[i] = new Sprite(x_tmp, y_tmp, w, h, star_TR);
					mScene.attachChild(star_SP[i]);
					star_SP[i].setZIndex(300);
				}
				int count = 0;
				float pScale = 1f;
				while (count < max) {
					try {
						Thread.sleep(timedelay);
						count++;
						for (int i = 0; i < star_SP.length; i++) {
							int x_tmp = Util.getRandomIndex(-5, 5);
							int y_tmp = Util.getRandomIndex(1, 4);

							star_SP[i].setPosition(star_SP[i].getX() + x_tmp,
									star_SP[i].getY() + y_tmp);
							star_SP[i].setScale(pScale);
						}
						pScale = pScale - 0.08f;
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				removeStar(star_SP);
			}
		}).start();
	}
}