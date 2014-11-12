package com.siliconorchard.myowncandy.item;


import java.util.ArrayList;

import com.siliconorchard.myowncandy.MainGame;
import com.siliconorchard.myowncandy.control.ValueControl;


public class ThreadAdd {
	public static void RunThreadShow(final ArrayList<Jewel> list_Jewel_add,
			final MainGame mainGame, final boolean active, final boolean anim) {
		new Thread(new Runnable() {
			int degree = 360;
			int degree_average = 360 / 10;
			float pScale = 1;

			@Override
			public void run() {
				for (int i = 0; i < list_Jewel_add.size(); i++) {
					Jewel mJewel = list_Jewel_add.get(i);
					mJewel.setVisiable(true);
				}
				if (anim) {
					while (pScale < 10 && !ValueControl.isCompletedLevel) {
						try {
							Thread.sleep(20);
							for (int i = 0; i < list_Jewel_add.size(); i++) {
								Jewel mJewel = list_Jewel_add.get(i);
								mJewel.setScale(pScale / 10);
								mJewel.rotation(degree);
							}
							degree = degree - degree_average;
							pScale = pScale + 1;
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}

				for (int i = 0; i < list_Jewel_add.size(); i++) {
					Jewel mJewel = list_Jewel_add.get(i);
					mJewel.setScale(1f);
					mJewel.rotation(0);
					mJewel.setVisiable(true);
				}

				if (active == true)
					mainGame.Buoc1MT();
			}
		}).start();
	}
}