package com.siliconorchard.myowncandy.control;

public class ValueControl {
	/**
	 * Trạng thái cho biết level hiện tại đang chơi đã hoàn thành hay chưa
	 */
	public static boolean isCompletedLevel = false;

	/**
	 * Quản lý tắt mở âm nhạc nền
	 */
	public static boolean isMusic = true;

	/**
	 * Trạng thái của tạm dừng hay đang chạy
	 */
	public static boolean isPauseGame = false;

	/**
	 * Quản lý tắt mở âm thanh
	 */
	public static boolean isSound = true;

	/**
	 * Nếu isTouchJewel = true thì xử lý touch trên jewel Ngược lại thì không xử
	 * lý
	 */
	public static boolean isTouchJewel = false;

	public static void ini() {
		isTouchJewel = false;
		isPauseGame = false;
	}

	// Kiểu game đang chơi
	public final static int TimeAttach = 0;
	public final static int Classic = 1;
	public final static int NewMode = 2;
	public static int TypeGame = TimeAttach;
}