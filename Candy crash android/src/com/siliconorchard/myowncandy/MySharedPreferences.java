package com.siliconorchard.myowncandy;

import com.siliconorchard.myowncandy.control.ValueControl;

import android.content.Context;
import android.content.SharedPreferences;

public class MySharedPreferences {
	SharedPreferences.Editor editor;

	SharedPreferences my_share;
	public final String PREFS_NAME = "MyPrefs";

	public MySharedPreferences(Context mContext) {
		my_share = mContext.getSharedPreferences(PREFS_NAME, 0);
		editor = my_share.edit();
	}

	/**
	 * quản lý nhạc nền
	 */
	public void getIsMusic() {
		ValueControl.isMusic = my_share.getBoolean("isMusic", true);
	}

	/**
	 * Quản lý âm thanh
	 */
	public void getIsSound() {
		ValueControl.isSound = my_share.getBoolean("isSound", true);
	}

	/**
	 * Level hiện tại
	 */
	public void getLevel() {
		Level.levelCurrent = my_share.getInt("levelCurrent", 1);
	}

	/**
	 * Level đã được mở khóa
	 */
	public void getLevelUnlock() {
		Level.levelUnlock = my_share.getInt("levelUnlock", 1);
	}

	/**
	 * Kiểu chơi
	 */
	public void getTypeGame() {
		// Mặc định ban đầu là kiểu chơi theo thời gian
		ValueControl.TypeGame = my_share.getInt("TypeGame", 0);
	}

	/**
	 * Cập nhật trạng thái bật tắt nhạc nền
	 * 
	 * @param isMusic
	 */
	public void updateIsMusic(boolean isMusic) {
		ValueControl.isMusic = isMusic;
		editor.putBoolean("isMusic", isMusic);
		editor.commit();
	}

	/**
	 * cập nhật trạng thái bật tắt âm thanh
	 * 
	 * @param isSound
	 */
	public void updateIsSound(boolean isSound) {
		ValueControl.isSound = isSound;
		editor.putBoolean("isSound", isSound);
		editor.commit();
	}

	/**
	 * Cập nhật level hiện tại đang chơi
	 * 
	 * @param levelCurrent
	 */
	public void updateLevelCurrent(int levelCurrent) {
		Level.levelCurrent = levelCurrent;
		editor.putInt("levelCurrent", levelCurrent);
		editor.commit();
	}

	/**
	 * Cập nhật level đã được mở khóa
	 * 
	 * @param levelUnlock
	 */
	public void updateLevelUnlock(int levelUnlock) {
		Level.levelUnlock = levelUnlock;
		editor.putInt("levelUnlock", levelUnlock);
		editor.commit();
	}

	/**
	 * Cập nhật chọn kiểu game
	 * 
	 * @param TypeGame
	 */
	public void updateTypeGame(int TypeGame) {
		ValueControl.TypeGame = TypeGame;
		editor.putInt("TypeGame", TypeGame);
		editor.commit();
	}

	/**
	 * Level hiện tại kiểu classic
	 */
	public void getLevelClassic() {
		Level.levelCurrentClassic = my_share.getInt("levelCurrentClassic", 1);
	}

	/**
	 * get giá trị coin hiện đang có của level đang chơi
	 */
	public void getCoinLevelCurrentClassic() {
		Level.coinLevelCurrentClassic = my_share.getInt(
				"coinLevelCurrentClassic", 0);
	}

	/**
	 * Level hiện tại kiểu new mode
	 */
	public void getLevelNewMode() {
		Level.levelCurrentNewMode = my_share.getInt("levelCurrentNewMode", 1);
	}

	/**
	 * Cập nhật kiểu game classic
	 * 
	 * @param TypeGame
	 */
	public void updateLevelCurrentClassic(int levelCurrentClassic) {
		Level.levelCurrentClassic = levelCurrentClassic;
		editor.putInt("levelCurrentClassic", levelCurrentClassic);
		editor.commit();
	}

	/**
	 * Cập nhật số coin mà level classic dang có
	 * 
	 * @param TypeGame
	 */
	public void updateCoinLevelCurrentClassic(int coinLevelCurrentClassic) {
		Level.coinLevelCurrentClassic = coinLevelCurrentClassic;
		editor.putInt("coinLevelCurrentClassic", coinLevelCurrentClassic);
		editor.commit();
	}

	/**
	 * Cập nhật kiểu game new mode
	 * 
	 * @param TypeGame
	 */
	public void updateLevelCurrentNewMode(int levelCurrentNewMode) {
		Level.levelCurrentNewMode = levelCurrentNewMode;
		editor.putInt("levelCurrentNewMode", levelCurrentNewMode);
		editor.commit();
	}
}