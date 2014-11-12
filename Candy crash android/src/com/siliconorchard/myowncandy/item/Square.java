package com.siliconorchard.myowncandy.item;


import java.util.ArrayList;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TextureRegion;

import com.siliconorchard.myowncandy.Level;
import com.siliconorchard.myowncandy.MainGame;
import com.siliconorchard.myowncandy.MySprite;
import com.siliconorchard.myowncandy.config.MyConfig;



import android.content.Context;

public class Square extends MySprite {
	private BitmapTextureAtlas square_BTA;
	private TextureRegion square_TR;
	private ArrayList<MySquare> mListSquare;

	public void onLoadResources(Engine mEngine, Context mContext) {
		this.mEngine = mEngine;
		this.mContext = mContext;
		this.mainGame = (MainGame) mContext;

		mListSquare = new ArrayList<MySquare>();

		this.square_BTA = new BitmapTextureAtlas(128, 128,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.square_TR = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(this.square_BTA, mContext, "square.png", 0, 0);
		mEngine.getTextureManager().loadTextures(this.square_BTA);
	}

	public void onLoadScene(Scene mScene) {
		this.mScene = mScene;
	}

	public void addToList(int i, int j, float x, float y) {
		synchronized (mListSquare) {
			MySquare mMySquare = new MySquare(i, j, x, y);
			mListSquare.add(mMySquare);
		}
	}

	// Xóa bỏ 1 hình chữ nhật
	public void removeIJ(int I, int J) {
		synchronized (mListSquare) {
			for (int i = 0; i < mListSquare.size(); i++) {
				MySquare mMySquare = mListSquare.get(i);
				if (mMySquare.i == I && mMySquare.j == J) {
					remove(mMySquare.getmSprite());
					mListSquare.remove(i);
					Level.squareCurrent++;
					mainGame.mProgressBar.updateProgressbarNewMode();
					break;
				}
			}
		}
	}

	public void reset() {
		synchronized (mListSquare) {
			for (int i = 0; i < mListSquare.size(); i++) {
				remove(mListSquare.get(i).getmSprite());
			}
			mListSquare.clear();
		}
	}

	public void remove(final Sprite mSprite) {
		mEngine.runOnUpdateThread(new Runnable() {
			@Override
			public void run() {
				mScene.detachChild(mSprite);
			}
		});
	}

	class MySquare {
		public int i, j;
		Sprite mSprite;

		MySquare(int i, int j, float x, float y) {
			this.i = i;
			this.j = j;
			mSprite = new Sprite(x, y, MyConfig.WIDTH_SQUARE,
					MyConfig.HEIGHT_SQUARE, square_TR);
			mScene.attachChild(mSprite);
		}

		public Sprite getmSprite() {
			return mSprite;
		}

		public void setmSprite(Sprite mSprite) {
			this.mSprite = mSprite;
		}
	}
}