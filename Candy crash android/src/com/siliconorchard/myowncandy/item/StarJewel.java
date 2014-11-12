package com.siliconorchard.myowncandy.item;


import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.opengl.texture.region.TextureRegion;

import com.siliconorchard.myowncandy.MySprite;
import com.siliconorchard.myowncandy.config.MyConfig;
import com.siliconorchard.myowncandy.util.Util;


public class StarJewel extends MySprite {
	boolean isRemove = false;
	Sprite mSprite;

	public void onLoadScene(Engine mEngine, Scene mScene, TextureRegion star_TR) {
		this.mEngine = mEngine;
		this.mScene = mScene;

		int w = (int) (star_TR.getWidth() * MyConfig.getRaceWidth());
		int h = star_TR.getHeight() * w / star_TR.getWidth();

		mSprite = new Sprite(-100, -100, w, h, star_TR.deepCopy());
		mScene.attachChild(mSprite);
		mSprite.setZIndex(300);
		mSprite.setVisible(false);
		show();
	}

	public void removeStarJewel() {
		isRemove = true;
		mEngine.runOnUpdateThread(new Runnable() {
			@Override
			public void run() {
				mScene.detachChild(mSprite);
			}
		});
	}

	public void setPosition(int x, int y) {
		mSprite.setPosition(x, y);
	}

	public void setVisiable(boolean visiable) {
		mSprite.setVisible(visiable);
	}

	public void show() {
		new Thread(new Runnable() {

			float pScale = 0.01f;
			int re = 0;
			boolean swap = false;
			int timedelay = Util.getRandomIndex(30, 80);

			@Override
			public void run() {
				while (!isRemove) {
					try {
						Thread.sleep(timedelay);
						mSprite.setScale(pScale);
						mSprite.setRotation(re);

						if (!swap) {
							pScale = pScale + 0.03f;
							if (pScale > 1) {
								pScale = 1f;
								swap = true;
							}
						} else {
							pScale = pScale - 0.03f;
							if (pScale < 0.01f) {
								pScale = 0.01f;
								swap = false;
							}
						}

						re = re + 10;
						if (re > 360) {
							re = 0;
							timedelay = Util.getRandomIndex(30, 80);
						}

					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}
		}).start();
	}
}