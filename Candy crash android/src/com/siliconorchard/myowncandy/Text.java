package com.siliconorchard.myowncandy;


import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.entity.IEntity;
import org.anddev.andengine.entity.modifier.DelayModifier;
import org.anddev.andengine.entity.modifier.ParallelEntityModifier;
import org.anddev.andengine.entity.modifier.RotationModifier;
import org.anddev.andengine.entity.modifier.ScaleModifier;
import org.anddev.andengine.entity.modifier.SequenceEntityModifier;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.entity.text.ChangeableText;
import org.anddev.andengine.opengl.font.Font;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;
import org.anddev.andengine.util.modifier.IModifier;

import com.siliconorchard.myowncandy.config.MyConfig;
import com.siliconorchard.myowncandy.control.ValueControl;



import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;

public class Text extends MySprite {
	private BitmapTextureAtlas coin_BTA;
	public TiledTextureRegion coin_TTR;
	int heightFont = 50;
	AnimatedSprite mASP;
	private Font mFont;

	private BitmapTextureAtlas mFontTexture;
	private ChangeableText textcoin;// Số coin đang có

	private ChangeableText textcoinlevel;// Số coin cần đạt được

	private ChangeableText textlevel;// Hiện thị level hiện tại

	public void onLoadResources(Engine mEngine, Context mContext) {

		this.mEngine = mEngine;
		this.mContext = mContext;
		this.mainGame = (MainGame) mContext;

		heightFont = (int) (heightFont * MyConfig.getRaceHeight());

		this.mFontTexture = new BitmapTextureAtlas(1024, 512,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mFont = new Font(this.mFontTexture, Typeface.createFromAsset(
				mContext.getAssets(), "font/greek.ttf"), heightFont, true,
				Color.rgb(184, 71, 159));
		mEngine.getTextureManager().loadTexture(this.mFontTexture);
		mEngine.getFontManager().loadFont(this.mFont);

		this.coin_BTA = new BitmapTextureAtlas(256, 32,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		coin_TTR = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(
				this.coin_BTA, mContext, "spinning_coin.png", 0, 0, 8, 1);
		mEngine.getTextureManager().loadTexture(this.coin_BTA);
	}

	public void onLoadScene(Scene mScene) {
		this.mScene = mScene;

		int y = ((int) (MyConfig.HEIGHT_TOP * MyConfig.getRaceHeight()) / 2)
				- (heightFont / 2);
		int x = (int) (96 * MyConfig.getRaceWidth());

		textcoin = new ChangeableText(x, y, this.mFont, "0", 200);
		if (ValueControl.TypeGame == ValueControl.Classic)
			textcoin.setText("" + Level.coinLevelCurrentClassic);
		mScene.attachChild(textcoin);

		String txt_textlevel = "L." + Level.levelCurrent;
		// Nếu kiểu chơi game là classic thì hiện thị level của classic
		if (ValueControl.TypeGame == ValueControl.Classic)
			txt_textlevel = "L." + Level.levelCurrentClassic;
		else if (ValueControl.TypeGame == ValueControl.NewMode)
			txt_textlevel = "L." + Level.levelCurrentNewMode;

		x = MyConfig.SCREENWIDTH - mFont.getStringWidth(txt_textlevel) - 3;
		textlevel = new ChangeableText(x, 2, this.mFont, txt_textlevel, 200);
		mScene.attachChild(textlevel);

		// Hiện thị ảnh coin đang xoay
		int w = (int) (coin_TTR.getWidth() / 8 * MyConfig.getRaceWidth());
		int h = coin_TTR.getHeight() * w / (coin_TTR.getWidth() / 8);

		y = (int) (textlevel.getY() + textlevel.getHeight());
		x = (int) (MyConfig.SCREENWIDTH - (this.coin_TTR.getWidth() / 8)
				* MyConfig.RACE_WIDTH);

		mASP = new AnimatedSprite(x, y, w, h, this.coin_TTR.deepCopy());
		mASP.animate(30);
		mScene.attachChild(mASP);

		int coinlevel = Level.coinLevel[Level.levelCurrent - 1];
		if (ValueControl.TypeGame == ValueControl.Classic)
			coinlevel = Level.numberCoinLevelClassic[Level.levelCurrentClassic - 1];
		else if (ValueControl.TypeGame == ValueControl.NewMode) {
			coinlevel = Level.getTotalSquare(Level.levelCurrentNewMode - 1);
			Level.totalSquare = coinlevel;
			Level.squareCurrent = 0;
		}

		x = MyConfig.SCREENWIDTH - mFont.getStringWidth("" + coinlevel)
				- (int) mASP.getWidth();
		y = (int) (textlevel.getY() + textlevel.getHeight());

		textcoinlevel = new ChangeableText(x, y, this.mFont, "" + coinlevel,
				200);
		mScene.attachChild(textcoinlevel);
	}

	public void removeChangeableText(final ChangeableText textLevelStart) {
		mEngine.runOnUpdateThread(new Runnable() {
			@Override
			public void run() {
				mScene.detachChild(textLevelStart);
			}
		});
	}

	public void reset() {
		// Số coin đang có
		textcoin.setText("0");
		// Level hiện tại
		String txt_textlevel = "L." + Level.levelCurrent;
		if (ValueControl.TypeGame == ValueControl.Classic)
			txt_textlevel = "L." + Level.levelCurrentClassic;
		else if (ValueControl.TypeGame == ValueControl.NewMode)
			txt_textlevel = "L." + Level.levelCurrentNewMode;

		int x = MyConfig.SCREENWIDTH - mFont.getStringWidth(txt_textlevel) - 3;
		textlevel.setPosition(x, textlevel.getY());
		textlevel.setText(txt_textlevel);

		// Số coin cần đạt được trong level đó
		int coinlevel = Level.coinLevel[Level.levelCurrent - 1];
		if (ValueControl.TypeGame == ValueControl.Classic)
			coinlevel = Level.numberCoinLevelClassic[Level.levelCurrentClassic - 1];
		else if (ValueControl.TypeGame == ValueControl.NewMode)
			coinlevel = Level.getTotalSquare(Level.levelCurrentNewMode - 1);

		x = (int) (MyConfig.SCREENWIDTH
				- this.mFont.getStringWidth("" + coinlevel) - mASP.getWidth());

		textcoinlevel.setPosition(x, textcoinlevel.getY());
		textcoinlevel.setText("" + coinlevel);
	}

	public void setTextCoin(int coin) {
		textcoin.setText(String.valueOf(coin));
	}

	// -------------------------------------------------------------------------
	public void sortChildren() {
		try {
			mScene.sortChildren();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	// -------------------------------------------------------------------------
	/**
	 * Hiện thị text thông báo level hiện tại
	 */
	public void showTextLevel() {
		String textLevel = "Level " + Level.levelCurrent;
		// Hiện thị level theo kiểu classic
		if (ValueControl.TypeGame == ValueControl.Classic)
			textLevel = "Level " + Level.levelCurrentClassic;
		else if (ValueControl.TypeGame == ValueControl.NewMode)
			textLevel = "Level " + Level.levelCurrentNewMode;

		float pX = MyConfig.getCenterX() - mFont.getStringWidth(textLevel) / 2;
		float pY = MyConfig.getCenterY() - heightFont / 2;

		final ChangeableText levelNext = new ChangeableText(pX, pY, mFont,
				textLevel);
		levelNext.setScale(0f);
		mScene.attachChild(levelNext);

		ScaleModifier mScaleModifier_in = new ScaleModifier(0.5f, 0f, 2f);
		RotationModifier mRotationModifier_in = new RotationModifier(0.5f, 0,
				360);
		ParallelEntityModifier mParallelEntityModifier_in = new ParallelEntityModifier(
				mScaleModifier_in, mRotationModifier_in);

		DelayModifier mDelayModifier = new DelayModifier(1.5f);

		ScaleModifier mScaleModifier_out = new ScaleModifier(0.5f, 2f, 0f);
		RotationModifier mRotationModifier_out = new RotationModifier(0.5f, 0,
				-360);
		ParallelEntityModifier mParallelEntityModifier_out = new ParallelEntityModifier(
				mScaleModifier_out, mRotationModifier_out);

		SequenceEntityModifier mSequenceEntityModifier = new SequenceEntityModifier(
				new SequenceEntityModifier.ISubSequenceShapeModifierListener() {
					@Override
					public void onSubSequenceStarted(
							IModifier<IEntity> pModifier, IEntity pItem,
							int pIndex) {
					}

					@Override
					public void onSubSequenceFinished(
							IModifier<IEntity> pModifier, IEntity pItem,
							int pIndex) {
						if (pIndex == 2) {
							// Xóa bỏ đối tượng
							removeChangeableText(levelNext);
							mainGame.Buoc1MT();
							// Cho phép người chơi bắt đầu chơi
							ValueControl.isTouchJewel = true;

							if (ValueControl.TypeGame == ValueControl.TimeAttach) {
								// Bắt đầu đếm thời gian
								mainGame.mProgressBar.start();
							}
						}
					}
				}, mParallelEntityModifier_in, mDelayModifier,
				mParallelEntityModifier_out);
		levelNext.registerEntityModifier(mSequenceEntityModifier);
	}
}