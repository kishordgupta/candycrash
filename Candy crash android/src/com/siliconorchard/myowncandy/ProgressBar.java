package com.siliconorchard.myowncandy;
 

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.entity.primitive.Rectangle;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TextureRegion;

import com.siliconorchard.myowncandy.config.MyConfig;



import android.content.Context;
 
public class ProgressBar extends MySprite {
    /**
    * Tạm dừng hoặc tiếp tục
    */
    boolean isPause = false;
    boolean isStop = false;
    Rectangle mRectangle;
 
    private BitmapTextureAtlas prb_BTA;
    private Sprite prb_SP;
    private TextureRegion prb_TR;
    int time_tmp = 0;
 
    /**
    * Tổng thời gian
    */
    int total_time = -1;
 
    float width_rect = 0;
 
    public int getTime() {
        return total_time - time_tmp;
    }
 
    public void onLoadResources(Engine mEngine, Context mContext) {
        this.mEngine = mEngine;
        this.mContext = mContext;
        this.mainGame = (MainGame) mContext;
 
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("image/");
        this.prb_BTA = new BitmapTextureAtlas(512, 64,TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        this.prb_TR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.prb_BTA, mContext, "progressbar.png", 0, 0);
        mEngine.getTextureManager().loadTextures(this.prb_BTA);
    }
 
    public void onLoadScene(Scene mScene) {
        this.mScene = mScene;
        int y = MyConfig.SCREENHIEGHT- (int) (MyConfig.HEIGHT_BOTTOM * MyConfig.getRaceHeight()) + (int) (10 * MyConfig.getRaceHeight());
        int x = (MyConfig.SCREENWIDTH - (int) (prb_TR.getWidth() * MyConfig.getRaceWidth())) / 2;
 
        width_rect = (int) (prb_TR.getWidth() * MyConfig.getRaceWidth()) - (int) (10 * MyConfig.getRaceWidth());
 
        int h = (int) (prb_TR.getHeight() * MyConfig.getRaceHeight()) - (int) (10 * MyConfig.getRaceHeight());
 
        mRectangle = new Rectangle(x + (int) (5 * MyConfig.getRaceWidth()), y + 3, width_rect, h);
        mRectangle.setColor(1f, 0.01f, 0.02f);
        this.mScene.attachChild(mRectangle);
 
        int w = (int) (prb_TR.getWidth() * MyConfig.getRaceWidth());
        h = prb_TR.getHeight() * w / prb_TR.getWidth();
 
        this.prb_SP = new Sprite(x, y, w, h, this.prb_TR);
        this.mScene.attachChild(prb_SP);
    }
 
    public void reload() {
        mRectangle.setVisible(true);
        prb_SP.setVisible(true);
        isStop = false;
        isPause = false;
        mRectangle.setWidth(width_rect);
    }
 
    public void setPause(boolean isPause) {
        this.isPause = isPause;
    }
 
    public void setStop(boolean isStop) {
        this.isStop = isStop;
    }
 
    public void setTotalTime(int total_time) {
        this.total_time = total_time;
    }
 
    public void start() {
        if (total_time < 0)
            return;
        time_tmp = total_time;
        final float w = width_rect / time_tmp;
        mRectangle.setVisible(true);
        mRectangle.setWidth(width_rect);
 
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!isStop && time_tmp > 0) {
                    if (!isPause) {
                        try {
                            Thread.sleep(1000);
                            time_tmp--;
                            updateProgressBar(w);
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }
                mRectangle.setVisible(false);
                if (time_tmp <= 0)
                    mainGame.timeOut();
            }
        }).start();
    }
 
    public void updateProgressBar(float w) {
        if (time_tmp > 0) {
            mRectangle.setWidth(mRectangle.getWidth() - w);
        }
    }
   
    //--------------------------------Classic------------------------
    public void updateProgressBarClassic(){
        float tmp1 = Level.coinLevelCurrentClassic;
        float tmp2 = Level.numberCoinLevelClassic[Level.levelCurrentClassic - 1];
        //Tính toán xem chiều dài của thanh progressbar là bao nhiêu
        float newW = width_rect * (tmp1/tmp2);
        mRectangle.setWidth(newW);
    }
    //--------------------------------NewMode------------------------
    public void updateProgressbarNewMode(){
        float newW = width_rect * ((float)Level.squareCurrent/(float)Level.totalSquare);
        mainGame.mText.setTextCoin(Level.squareCurrent);
        mRectangle.setWidth(newW);
    }
}