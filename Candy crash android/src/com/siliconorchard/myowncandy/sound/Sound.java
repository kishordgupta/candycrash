package com.siliconorchard.myowncandy.sound;

import com.siliconorchard.myowncandy.R;
import com.siliconorchard.myowncandy.control.ValueControl;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

public class Sound {
	SoundPool mSoundPool;
	Context context;
	int harp = -1, impactmic = -1, combo0 = -1, combo1 = -1, combo2 = -1,
			combo3 = -1, combo4 = -1, combo5 = -1, combo6 = -1, combo7 = -1,
			combo8 = -1, combo9 = -1, combo10 = -1, combo11 = -1, bomb = -1,
			bomb_wave = -1, thunder = -1;

	float volume = 0.5f;

	public void loadSound(Context context) {
		this.context = context;
		mSoundPool = new SoundPool(3, AudioManager.STREAM_MUSIC, 100);
		harp = mSoundPool.load(this.context, R.raw.harp, 1);
		impactmic = mSoundPool.load(this.context, R.raw.impactmic, 1);

		combo0 = mSoundPool.load(this.context, R.raw.combo0, 1);
		combo1 = mSoundPool.load(this.context, R.raw.combo1, 1);
		combo2 = mSoundPool.load(this.context, R.raw.combo2, 1);
		combo3 = mSoundPool.load(this.context, R.raw.combo3, 1);
		combo4 = mSoundPool.load(this.context, R.raw.combo4, 1);
		combo5 = mSoundPool.load(this.context, R.raw.combo5, 1);
		combo6 = mSoundPool.load(this.context, R.raw.combo6, 1);
		combo7 = mSoundPool.load(this.context, R.raw.combo7, 1);
		combo8 = mSoundPool.load(this.context, R.raw.combo8, 1);
		combo9 = mSoundPool.load(this.context, R.raw.combo9, 1);
		combo10 = mSoundPool.load(this.context, R.raw.combo10, 1);
		combo11 = mSoundPool.load(this.context, R.raw.combo11, 1);

		bomb = mSoundPool.load(this.context, R.raw.bomb, 1);
		bomb_wave = mSoundPool.load(this.context, R.raw.bomb_wave, 1);

		thunder = mSoundPool.load(this.context, R.raw.thunder, 1);
	}

	public void offSound() {
		volume = 0;
	}

	public void playHarp() {
		if (ValueControl.isSound) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					mSoundPool.play(harp, volume, volume, 1, 0, 1f);
				}
			}).start();
		}
	}

	public void playImpactmic() {
		if (ValueControl.isSound) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					mSoundPool.play(impactmic, volume, volume, 1, 0, 1f);
				}
			}).start();
		}
	}

	public void playBomb() {
		if (ValueControl.isSound) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					mSoundPool.play(bomb_wave, volume, volume, 1, 0, 1f);
				}
			}).start();
		}
	}

	public void playThunder() {
		if (ValueControl.isSound) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					mSoundPool.play(thunder, volume, volume, 1, 0, 1f);
				}
			}).start();
		}
	}

	public void playCombo(final int combo) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				if (ValueControl.isSound) {
					switch (combo) {
					case 1:
						mSoundPool.play(combo0, volume, volume, 1, 0, 1f);
						break;
					case 2:
						mSoundPool.play(combo1, volume, volume, 1, 0, 1f);
						break;
					case 3:
						mSoundPool.play(combo2, volume, volume, 1, 0, 1f);
						break;
					case 4:
						mSoundPool.play(combo3, volume, volume, 1, 0, 1f);
						break;
					case 5:
						mSoundPool.play(combo4, volume, volume, 1, 0, 1f);
						break;
					case 6:
						mSoundPool.play(combo5, volume, volume, 1, 0, 1f);
						break;
					case 7:
						mSoundPool.play(combo6, volume, volume, 1, 0, 1f);
						break;
					case 8:
						mSoundPool.play(combo7, volume, volume, 1, 0, 1f);
						break;
					case 9:
						mSoundPool.play(combo8, volume, volume, 1, 0, 1f);
						break;
					case 10:
						mSoundPool.play(combo9, volume, volume, 1, 0, 1f);
						break;
					case 11:
						mSoundPool.play(combo10, volume, volume, 1, 0, 1f);
						break;
					default:
						mSoundPool.play(combo11, volume, volume, 1, 0, 1f);
						break;
					}
				}
			}
		}).start();
	}
}