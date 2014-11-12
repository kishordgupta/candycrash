package com.siliconorchard.myowncandy.item;


import java.util.HashMap;

import org.anddev.andengine.engine.Engine;
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

public class Thunder extends MySprite {
	private BitmapTextureAtlas thunderball;
	private TextureRegion thunderaball_TR;

	private BitmapTextureAtlas thunder_BTA;
	private TiledTextureRegion thunder_TTR;
	private int w_thunder = 1, h_thunder = 1;

	private BitmapTextureAtlas thunder_horizon_BTA;
	private TiledTextureRegion thunder_horizon_TTR;
	private int w_thunder_horizon = 1, h_thunder_horizon = 1;

	private int w_thnderball = 0, h_thunderball = 0;

	private HashMap<Integer, Sprite> mapSprite;
	private int idThunderBall = 0;

	public void onLoadResources(Engine mEngine, Context mContext) {
		this.mEngine = mEngine;
		this.mContext = mContext;
		this.mainGame = (MainGame) mContext;

		this.mapSprite = new HashMap<Integer, Sprite>();

		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("image/");
		this.thunderball = new BitmapTextureAtlas(128, 128,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.thunderaball_TR = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(this.thunderball, mContext, "thunderball.png",
						0, 0);
		mEngine.getTextureManager().loadTextures(this.thunderball);

		this.thunder_BTA = new BitmapTextureAtlas(512, 256,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.thunder_TTR = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(this.thunder_BTA, mContext,
						"thunder.png", 0, 0, 1, 4);
		this.mEngine.getTextureManager().loadTexture(this.thunder_BTA);

		this.thunder_horizon_BTA = new BitmapTextureAtlas(256, 512,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.thunder_horizon_TTR = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(this.thunder_horizon_BTA, mContext,
						"thunder_horizon.png", 0, 0, 4, 1);
		this.mEngine.getTextureManager().loadTexture(this.thunder_horizon_BTA);
	}

	public void onLoadScene(Scene mScene) {
		this.mScene = mScene;

		int width_content = MyConfig.SCREENWIDTH - MyConfig.getTotalPadding();
		int height_content = MyConfig.SCREENHIEGHT
				- (int) MyConfig.getHeightTop()
				- (int) MyConfig.getHeightBottom();

		w_thnderball = (int) (MyConfig.WIDTH_SQUARE * MyConfig.getRaceWidth());
		h_thunderball = w_thnderball;

		w_thunder = width_content;
		h_thunder = MyConfig.HEIGHT_SQUARE;

		w_thunder_horizon = MyConfig.WIDTH_SQUARE;
		h_thunder_horizon = height_content;
	}

	public Sprite getSpriteThunderByKey(int key) {
		return mapSprite.get(key);
	}

	public void addThunderBall(int x, int y, int key) {
		synchronized (mapSprite) {
			if (ValueControl.isCompletedLevel)
				return;

			Sprite mb = new Sprite(x, y, w_thnderball, h_thunderball,
					thunderaball_TR.deepCopy());
			mb.setZIndex(210);
			mb.setVisible(true);
			this.mScene.attachChild(mb);
			this.mapSprite.put(key, mb);
		}
	}

	/**
	 * Ngẫu nhiên từ 1-30. Nếu giá trị ngẫu nhiên là 5 thì là có bom
	 * 
	 * @return
	 */
	public boolean isHaveThundeBall() {
		int i = Util.getRandomIndex(1, 30);
		if (i == 1)
			return true;
		return false;
	}

	public int getIdThunderBallNext() {
		idThunderBall++;
		return this.idThunderBall;
	}

	public void setVisiableThunderBall(int idThunderBall, boolean visiable) {
		synchronized (mapSprite) {
			if (mapSprite.containsKey(idThunderBall)) {
				final Sprite mb = mapSprite.get(idThunderBall);
				mb.setVisible(visiable);
			}
		}
	}

	public void setPositionThunderBall(int x, int y, int idThunderBall) {
		synchronized (mapSprite) {
			if (mapSprite.containsKey(idThunderBall)) {
				final Sprite mb = mapSprite.get(idThunderBall);
				mb.setPosition(x, y);
			}
		}
	}

	public void removeThunderBall(int idThunderBall) {
		synchronized (mapSprite) {
			if (mapSprite.containsKey(idThunderBall)) {
				final Sprite mb = mapSprite.get(idThunderBall);
				handleRemoveSprite(mb);
			}
		}
	}

	public void handleRemoveSprite(final Sprite mb) {
		Message message = mHandler_removeSprite.obtainMessage(0, mb);
		mHandler_removeSprite.sendMessage(message);
	}

	Handler mHandler_removeSprite = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			removeSprite((Sprite) msg.obj);
		}
	};

	public void removeSprite(final Sprite mb) {
		mEngine.runOnUpdateThread(new Runnable() {
			@Override
			public void run() {
				mScene.detachChild(mb);
			}
		});
	}

	public void addThunder(float x, float y, boolean horizon) {
		if (ValueControl.isCompletedLevel)
			return;
		if (Menu.mSound != null)
			Menu.mSound.playThunder();

		AnimatedSprite mThunder = null;
		if (horizon) {
			mThunder = new AnimatedSprite(x, MyConfig.Y_START,
					w_thunder_horizon, h_thunder_horizon,
					thunder_horizon_TTR.deepCopy());
		} else {
			mThunder = new AnimatedSprite(MyConfig.X_START, y, w_thunder,
					h_thunder, thunder_TTR.deepCopy());
		}
		mThunder.animate(30);
		mThunder.setZIndex(250);
		mScene.attachChild(mThunder);

		final AnimatedSprite tmp_AnimatedSprite = mThunder;
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Message message = mHandler.obtainMessage(0, tmp_AnimatedSprite);
				mHandler.sendMessage(message);
			}
		}).start();
	}

	Handler mHandler = new Handler() {
		@Override
		public void handleMessage(final Message msg) {
			super.handleMessage(msg);
			handleRemoveAnimatedSprite((AnimatedSprite) msg.obj);
		}
	};

	public void handleRemoveAnimatedSprite(final AnimatedSprite mb) {
		Message message = mHandler_removeAnimatedSprite.obtainMessage(0, mb);
		mHandler_removeAnimatedSprite.sendMessage(message);
	}

	Handler mHandler_removeAnimatedSprite = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			removeAnimatedSprite((AnimatedSprite) msg.obj);
		}
	};

	public void removeAnimatedSprite(final AnimatedSprite mb) {
		mEngine.runOnUpdateThread(new Runnable() {
			@Override
			public void run() {
				mScene.detachChild(mb);
			}
		});
	}
}