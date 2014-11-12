package com.siliconorchard.myowncandy.item;


import java.util.ArrayList;

import com.siliconorchard.myowncandy.MainGame;
import com.siliconorchard.myowncandy.control.ValueControl;


/**
 * Tạo hiệu ứng thu nhỏ jewel rồi ẩn jewel đó
 * 
 * @author gioi
 * 
 */
public class ThreadHide {
	public static void RunThreadHide(final ArrayList<Jewel> list_hide,
			final MainGame mainGame) {

		new Thread(new Runnable() {
			int degree = 0;
			int degree_average = 360 / 13;
			float pScale = 1.3f;

			@Override
			public void run() {
				while (pScale > 0.1f && !ValueControl.isCompletedLevel) {
					try {
						Thread.sleep(20);
						for (int i = 0; i < list_hide.size(); i++) {
							Jewel mJewel = list_hide.get(i);
							mJewel.setScale(pScale);
							mJewel.rotation(degree);
						}
						degree = degree + degree_average;
						pScale = pScale - 0.1f;
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				for (int i = 0; i < list_hide.size(); i++) {
					Jewel mJewel = list_hide.get(i);
					mainGame.mSquare.removeIJ(mJewel.getI(), mJewel.getJ());
					mJewel.onDestroy();
				}
				mainGame.Buoc3MT();
			}
		}).start();
	}
}