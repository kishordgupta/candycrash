package com.siliconorchard.myowncandy.config;
 
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;

import android.app.Activity;
 
public class MyConfig {
//    public static String keyAdmob = "a1519bbe58e50af";
   
    /**
    * Chiều cao phần top và bottom của background
    */
    public static int HEIGHT_TOP = 125, HEIGHT_BOTTOM = 190;
 
    /**
    * Khoảng cách lề 2 bên
    */
    public static int PADDING_LEFT = 0, PADDING_RIGHT = 0;
    public static float RACE_HEIGHT = 1f;
 
    public static float RACE_WIDTH = 1f;
 
    /**
    * Yêu cầu màn hình nằm thẳng đứng
    */
    public static final ScreenOrientation ScreenOrientation_Default = ScreenOrientation.PORTRAIT;
 
    /**
    * Chiều rộng và chiều cao của màn hình hiện thị
    */
    public static int SCREENWIDTH = 480, SCREENHIEGHT = 800;
 
    /**
    * Số hàng và số cột hiện thị ma trận
    */
    public static int SUM_ROW_MATRIX = -1, SUM_COLUMN_MATRIX = -1;
 
    /**
    * Chiều cao và chiểu rộng của 1 ô vuông hiện thị dưới 1 viên ngọc
    */
    public static int WIDTH_SQUARE = 68, HEIGHT_SQUARE = 68;
 
    /**
    * Vị trí ban đầu bắt đầu vẽ các viên ngọc
    */
    public static int X_START = -1, X_END = -1, Y_START = -1, Y_END = -1;
 
    /**
    * Lấy vị trí x ở giữa màn hình
    *
    * @return
    */
    public static int getCenterX() {
        return SCREENWIDTH / 2;
    }
 
    /**
    * Lấy vị trí y ở giữa màn hình
    *
    * @return
    */
    public static int getCenterY() {
        return SCREENHIEGHT / 2;
    }
 
    public static void getDisplayScreen(Activity mActivity) {
        SCREENWIDTH = mActivity.getWindowManager().getDefaultDisplay().getWidth();
        SCREENHIEGHT = mActivity.getWindowManager().getDefaultDisplay().getHeight();
 
        RACE_WIDTH = (float) SCREENWIDTH / (float) 480;
        RACE_HEIGHT = (float) SCREENHIEGHT / (float) 800;
       
        WIDTH_SQUARE = SCREENWIDTH / 7;
        HEIGHT_SQUARE = WIDTH_SQUARE;
    }
 
    public static float getHeightBottom() {
        return HEIGHT_BOTTOM * RACE_HEIGHT;
    }
 
    public static float getHeightTop() {
        return HEIGHT_TOP * RACE_HEIGHT;
    }
 
    /**
    * Tỉ lệ chiều cao
    *
    * @return
    */
    public static float getRaceHeight() {
        return RACE_HEIGHT;
    }
 
    /**
    * Tỉ lệ chiều rộng
    *
    * @return
    */
    public static float getRaceWidth() {
        return RACE_WIDTH;
    }
 
    public static int getTotalPadding() {
        return (int) (PADDING_LEFT * RACE_WIDTH + PADDING_RIGHT * PADDING_RIGHT);
    }
}