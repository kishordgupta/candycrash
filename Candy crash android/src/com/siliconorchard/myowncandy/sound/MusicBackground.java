package com.siliconorchard.myowncandy.sound;

import com.siliconorchard.myowncandy.R;

import android.content.Context;
import android.media.MediaPlayer;

public class MusicBackground {
	MediaPlayer mediaPlayer;

	public void loadMusic(Context mContext) {
		mediaPlayer = MediaPlayer.create(mContext, R.raw.backgroundmusic);
		mediaPlayer.setVolume(0.2f, 0.2f);
		mediaPlayer
				.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
					@Override
					public void onCompletion(MediaPlayer mp) {
						play();
					}
				});
	}

	public void play() {
		mediaPlayer.seekTo(0);
		mediaPlayer.start();
	}

	public void pause() {
		if (mediaPlayer.isPlaying())
			mediaPlayer.pause();
	}

	public void resume() {
		if (!mediaPlayer.isPlaying())
			mediaPlayer.start();
	}

	public void release() {
		mediaPlayer.release();
	}
}