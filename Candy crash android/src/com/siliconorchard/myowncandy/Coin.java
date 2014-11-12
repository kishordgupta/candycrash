package com.siliconorchard.myowncandy;


import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.entity.IEntity;
import org.anddev.andengine.entity.modifier.MoveModifier;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;
import org.anddev.andengine.util.modifier.IModifier;

import com.siliconorchard.myowncandy.config.MyConfig;
import com.siliconorchard.myowncandy.control.ValueControl;
import com.siliconorchard.myowncandy.mylog.MyLog;



import android.content.Context;

public class Coin extends MySprite {

	private int coin = 0;
	private BitmapTextureAtlas coin_BTA;
	public TiledTextureRegion coin_TTR;

	Text mText;
	private int x_end = 0, y_end = 0;

	public void add(int px_start, int py_start) {
		if (ValueControl.isCompletedLevel == true)
			return;

		int w = (int) (coin_TTR.getWidth() / 8 * MyConfig.getRaceWidth());
		int h = (coin_TTR.getHeight() * w) / (coin_TTR.getWidth() / 8);

		final AnimatedSprite mAnimatedSprite = new AnimatedSprite(100, 50, w,
				h, this.coin_TTR.deepCopy());
		mAnimatedSprite.animate(30);
		this.mScene.attachChild(mAnimatedSprite);
		MoveModifier moveModifier = new MoveModifier(1f, px_start, this.x_end,
				py_start, this.y_end,
				new MoveModifier.IEntityModifierListener() {

					@Override
					public void onModifierStarted(IModifier<IEntity> pModifier,
							IEntity pItem) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onModifierFinished(
							IModifier<IEntity> pModifier, IEntity pItem) {
						upCoin();
						removeAnimatedSprite(mAnimatedSprite);
					}
				});
		mAnimatedSprite.registerEntityModifier(moveModifier);
	}

	public int getCoin() {
		return coin;
	}

	public void onLoadResources(Engine mEngine, Context mContext) {
		this.mEngine = mEngine;
		mainGame = (MainGame) mContext;
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("image/");
		this.coin_BTA = new BitmapTextureAtlas(256, 32,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		coin_TTR = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(
				this.coin_BTA, mContext, "spinning_coin.png", 0, 0, 8, 1);
		mEngine.getTextureManager().loadTexture(this.coin_BTA);
	}

	public void onLoadScene(Scene mScene) {
		this.mScene = mScene;
	}

	public void removeAnimatedSprite(final AnimatedSprite mAnimatedSprite) {
		mEngine.runOnUpdateThread(new Runnable() {
			@Override
			public void run() {
				mScene.detachChild(mAnimatedSprite);
			}
		});
	}

	public void reset() {
		coin = 0;
		if (ValueControl.TypeGame == ValueControl.Classic) {
			MyLog.println("reset updateProgressBarClassic");
			Level.coinLevelCurrentClassic = this.coin;
			mainGame.mProgressBar.updateProgressBarClassic();
		} else if (ValueControl.TypeGame == ValueControl.NewMode) {
			mText.setTextCoin(0);
			mainGame.mProgressBar.updateProgressbarNewMode();
		}
	}

	public void setBigCoin(Sprite bigcoin) {
		this.x_end = (int) (bigcoin.getX() + (bigcoin.getWidth() / 2) - (coin_TTR
				.getWidth() / 8) / 2);
		this.y_end = (int) (bigcoin.getY() + (bigcoin.getHeight() / 2) - (coin_TTR
				.getHeight() / 2));
	}

	public void setCoin(int coin) {
		this.coin = coin;
		if (ValueControl.TypeGame == ValueControl.Classic) {
			Level.coinLevelCurrentClassic = this.coin;
			mainGame.mProgressBar.updateProgressBarClassic();
		}
	}

	public void setTextCoin(Text mText) {
		this.mText = mText;
	}

	public void upCoin() {
		if (!ValueControl.isCompletedLevel) {
			if (ValueControl.TypeGame == ValueControl.Classic
					|| ValueControl.TypeGame == ValueControl.TimeAttach) {
				coin = coin + 10;
				mText.setTextCoin(coin);
				if (ValueControl.TypeGame == ValueControl.Classic) {
					Level.coinLevelCurrentClassic = this.coin;
					mainGame.mProgressBar.updateProgressBarClassic();
				} else if (ValueControl.TypeGame == ValueControl.NewMode) {
					Level.squareCurrent++;
					mainGame.mProgressBar.updateProgressbarNewMode();
				}
				mainGame.checkLevelCompleted();
			}
		}
	}
}