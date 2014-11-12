package com.siliconorchard.myowncandy.util;

public class UtilFormat {

	/**
	 * Chuyển thời gian từ s sang định dạng string
	 * 
	 * @param time
	 *            : Thời gian tính theo milis
	 * @return : Sẽ chuyển thành dạng string có định dạng hh:pp:ss
	 *         (giờ:phút:giây)
	 * 
	 * @example time = 65; => return 01:05
	 */
	public static String getTime(int time) {
		int s = time, p = 0, h = 0;

		h = s / 3600;
		s = s % 3600;
		p = s / 60;
		s = s % 60;

		String gio = "", phut = "", giay = "";

		if (String.valueOf(h).length() < 2)
			gio = "0" + String.valueOf(h);
		else
			gio = String.valueOf(h);
		if (String.valueOf(p).length() < 2)
			phut = "0" + String.valueOf(p);
		else
			phut = String.valueOf(p);
		if (String.valueOf(s).length() < 2)
			giay = "0" + String.valueOf(s);
		else
			giay = String.valueOf(s);

		if (h == 0)
			return phut + ":" + giay;
		else if (p == 0 && h == 0)
			return giay;

		if (gio.equals("00"))
			return phut + ":" + giay;
		return gio + ":" + phut + ":" + giay;
	}
}