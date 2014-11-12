package com.siliconorchard.myowncandy.item;


import java.util.HashMap;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.entity.Entity;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import com.siliconorchard.myowncandy.MainGame;
import com.siliconorchard.myowncandy.Menu;
import com.siliconorchard.myowncandy.MySprite;
import com.siliconorchard.myowncandy.config.MyConfig;
import com.siliconorchard.myowncandy.control.ValueControl;
import com.siliconorchard.myowncandy.util.Util;



import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;

public class Bom extends MySprite {
	private BitmapTextureAtlas bom_BTA;
	private TextureRegion bom_TR;

	private BitmapTextureAtlas bom_m_BTA;
	private TiledTextureRegion bom_m_TTR;
	private int w = 0, h = 0;

	private HashMap<Integer, Sprite> mapSpriteBom;
	private int idBom = 0;

	public Vibrator v;

	public void onLoadResources(Engine mEngine, Context mContext) {
		this.mEngine = mEngine;
		this.mContext = mContext;
		this.mainGame = (MainGame) mContext;

		v = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);

		this.mapSpriteBom = new HashMap<Integer, Sprite>();

		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("image/");
		this.bom_BTA = new BitmapTextureAtlas(128, 128,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.bom_TR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				this.bom_BTA, mContext, "bom.png", 0, 0);
		mEngine.getTextureManager().loadTextures(this.bom_BTA);

		this.bom_m_BTA = new BitmapTextureAtlas(1024, 1024,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		bom_m_TTR = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(this.bom_m_BTA, mContext, "bom_m.png", 0,
						0, 8, 6);
		mEngine.getTextureManager().loadTexture(this.bom_m_BTA);
	}

	public void onLoadScene(Scene mScene) {
		this.mScene = mScene;
		w = (int) (bom_TR.getWidth() * MyConfig.getRaceWidth());
		h = bom_TR.getHeight() * w / bom_TR.getWidth();
	}

	public void addBom(int x, int y, int key) {
		synchronized (mapSpriteBom) {
			if (ValueControl.isCompletedLevel)
				return;

			Sprite mb = new Sprite(x, y, w, h, bom_TR.deepCopy());
			mb.setZIndex(200);
			mb.setVisible(true);
			this.mScene.attachChild(mb);
			this.mapSpriteBom.put(key, mb);
		}
	}

	public int getWidth() {
		return this.w;
	}

	public int getHeight() {
		return this.h;
	}

	public TextureRegion getTextureRegion() {
		return bom_TR.deepCopy();
	}

	public Sprite getmapSpriteBomMapByKey(int key) {
		return mapSpriteBom.get(key);
	}

	/**
	 * Ngẫu nhiên từ 1-30. Nếu giá trị ngẫu nhiên là 5 thì là có bom
	 * 
	 * @return
	 */
	public boolean isHaveBom() {
		int i = Util.getRandomIndex(1, 30);
		if (i == 1)
			return true;
		return false;
	}

	public int getIDBomNext() {
		idBom++;
		return this.idBom;
	}

	public void setVisiableBom(int idBom, boolean visiable) {
		synchronized (mapSpriteBom) {
			if (mapSpriteBom.containsKey(idBom)) {
				final Sprite mb = mapSpriteBom.get(idBom);
				mb.setVisible(visiable);
			}
		}
	}

	public void setPositionBom(int x, int y, int idBom) {
		synchronized (mapSpriteBom) {
			if (mapSpriteBom.containsKey(idBom)) {
				final Sprite mb = mapSpriteBom.get(idBom);
				mb.setPosition(x, y);
			}
		}
	}

	public void removeBom(int idBom) {
		synchronized (mapSpriteBom) {
			if (mapSpriteBom.containsKey(idBom)) {
				final Sprite mb = mapSpriteBom.get(idBom);
				handleRemoveSprite(mb);
			}
		}
	}

	public void addFire(float x, float y) {
		if (ValueControl.isCompletedLevel)
			return;
		if (Menu.mSound != null)
			Menu.mSound.playBomb();
		float w = (bom_m_TTR.getWidth() / 8) / 2;
		float h = (bom_m_TTR.getHeight() / 6) / 2;
		x = x - w;
		y = y - h;

		final AnimatedSprite mAnimatedSprite = new AnimatedSprite(x, y,
				bom_m_TTR.deepCopy());
		if (MyConfig.SCREENWIDTH >= 480)
			mAnimatedSprite.setScale(3f);
		else if (MyConfig.SCREENWIDTH < 480 && MyConfig.SCREENWIDTH >= 320)
			mAnimatedSprite.setScale(1.5f);

		mAnimatedSprite.animate(30);
		mScene.attachChild(mAnimatedSprite);

		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(8 * 6 * 30);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				handleRemoveSprite(mAnimatedSprite);
			}
		}).start();

	}

	public void handleRemoveSprite(final Entity mb) {
		Message message = mHandler.obtainMessage(0, mb);
		mHandler.sendMessage(message);
	}

	Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			removeSprite((Entity) msg.obj);
		}
	};

	public void removeSprite(final Entity mb) {
		mEngine.runOnUpdateThread(new Runnable() {
			@Override
			public void run() {
				try {
					mScene.detachChild(mb);
				} catch (Exception e) {
				}
			}
		});
	}
}