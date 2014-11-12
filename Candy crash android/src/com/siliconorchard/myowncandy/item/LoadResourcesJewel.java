package com.siliconorchard.myowncandy.item;


import java.io.File;
import java.io.FileOutputStream;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.atlas.bitmap.source.FileBitmapTextureAtlasSource;
import org.anddev.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.anddev.andengine.opengl.texture.region.TextureRegion;

import com.siliconorchard.myowncandy.MySprite;
import com.siliconorchard.myowncandy.mystatic.Constant;
import com.siliconorchard.picker.AndroidCustomGalleryActivity;



import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

/**
 * Load resource jewel
 * 
 * @author gioi
 * 
 */
public class LoadResourcesJewel extends MySprite {
	private BitmapTextureAtlas[] item_BTA;
	private TextureRegion[] item_TR;

	public TextureRegion getResourceName(int i) {
		return item_TR[i];
	}

	public void onLoadResources(Engine mEngine, Context mContext) {
		this.mEngine = mEngine;
		this.mContext = mContext;

		item_BTA = new BitmapTextureAtlas[Constant.MAX_ITEM_JEWEL];
		item_TR = new TextureRegion[Constant.MAX_ITEM_JEWEL];

		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("image/jewel/");

		for (int i = 0; i < Constant.MAX_ITEM_JEWEL; i++) {
			this.item_BTA[i] = new BitmapTextureAtlas(128, 128,
					TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		/*	this.item_TR[i] = BitmapTextureAtlasTextureRegionFactory
					.createFromAsset(this.item_BTA[i], mContext,
							Constant.ITEM_NAME_JEWEL[i], 0, 0);*/
			   // Texturas
			/*File dir=Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
			Bitmap b= BitmapFactory.decodeFile(AndroidCustomGalleryActivity.sel.get(i));
			                Bitmap out = Bitmap.createScaledBitmap(b, 128, 128, false);

			            File file = new File(dir, i+"resize.png");
			            FileOutputStream fOut;
			            try {
			                fOut = new FileOutputStream(file);
			                out.compress(Bitmap.CompressFormat.PNG, 100, fOut);
			                fOut.flush();
			                fOut.close();
			                b.recycle();
			                out.recycle();

			            } catch (Exception e) { // TODO

			            }*/
	  //   File root = Environment.getExternalStorageDirectory();
	     //  File imageFile = new File(AndroidCustomGalleryActivity.sel.get(i));
	//  Log.d("kd", ""+file.getAbsolutePath().toString() + "/"+AndroidCustomGalleryActivity.sel.get(i));
			FileBitmapTextureAtlasSource pBitmapTextureAtlasSource = new FileBitmapTextureAtlasSource(AndroidCustomGalleryActivity.impfile.get(i));
	          
			this.item_TR[i]=BitmapTextureAtlasTextureRegionFactory.createFromSource(this.item_BTA[i],  pBitmapTextureAtlasSource,0,0);
		mEngine.getTextureManager().loadTextures(this.item_BTA[i]);
		}
	}
}