package com.siliconorchard.myowncandy;


import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TextureRegion;

import com.siliconorchard.myowncandy.config.MyConfig;



import android.content.Context;

/**
 * Hiện thị hình vuông tạo cảnh nền, hiện thị bên dưới các viên ngọc
 * 
 * @author gioi
 * 
 */
public class BgSquare extends MySprite {
	private BitmapTextureAtlas bg_BTA;
	private TextureRegion bg_TR;

	public void draw() {
		int width_content = MyConfig.SCREENWIDTH - MyConfig.getTotalPadding();
		int height_content = MyConfig.SCREENHIEGHT
				- (int) MyConfig.getHeightTop()
				- (int) MyConfig.getHeightBottom();

		if (width_content < height_content) {
			MyConfig.WIDTH_SQUARE = width_content / 7;
			MyConfig.HEIGHT_SQUARE = MyConfig.WIDTH_SQUARE;
		} else {
			MyConfig.WIDTH_SQUARE = height_content / 7;
			MyConfig.HEIGHT_SQUARE = MyConfig.WIDTH_SQUARE;
		}

		MyConfig.SUM_ROW_MATRIX = 7;// Tổng
									// số
									// hàng
		MyConfig.SUM_COLUMN_MATRIX = 7;// Tổng
										// số
										// cột

		// Chiều rộng, cao hiện thị toàn bộ ma trận jewel
		int widthJewelShow = MyConfig.SUM_COLUMN_MATRIX * MyConfig.WIDTH_SQUARE;
		int heightJewelShow = MyConfig.SUM_ROW_MATRIX * MyConfig.HEIGHT_SQUARE;

		MyConfig.X_START = (width_content - widthJewelShow) / 2;
		MyConfig.Y_START = (int) MyConfig.getHeightTop()
				+ (height_content - heightJewelShow) / 2;

		int startY = MyConfig.Y_START;
		for (int i = 0; i < MyConfig.SUM_ROW_MATRIX; i++) {
			int stX = MyConfig.X_START;

			int k = 0;
			if (i % 2 == 0)
				k = 0;
			else
				k = 1;

			for (int j = 0; j < MyConfig.SUM_COLUMN_MATRIX; j++) {
				if (k % 2 == 0) {
					Sprite tmp_Sprite = new Sprite(stX, startY,
							MyConfig.WIDTH_SQUARE, MyConfig.HEIGHT_SQUARE,
							this.bg_TR.deepCopy());
					mScene.attachChild(tmp_Sprite);
				}
				k++;
				stX = stX + MyConfig.WIDTH_SQUARE;
			}

			startY = startY + MyConfig.HEIGHT_SQUARE;
		}
	}

	public void onLoadResources(Engine mEngine, Context mContext) {
		this.mEngine = mEngine;
		this.mContext = mContext;
		this.mainGame = (MainGame) mContext;

		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("image/");
		this.bg_BTA = new BitmapTextureAtlas(64, 64,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.bg_TR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				this.bg_BTA, mContext, "bg_square.png", 0, 0);
		mEngine.getTextureManager().loadTextures(this.bg_BTA);
	}

	public void onLoadScene(Scene mScene) {
		this.mScene = mScene;
		draw();
	}
}