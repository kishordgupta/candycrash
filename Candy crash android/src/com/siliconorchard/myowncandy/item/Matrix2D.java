package com.siliconorchard.myowncandy.item;


import java.util.ArrayList;

import com.siliconorchard.myowncandy.config.MyConfig;
import com.siliconorchard.myowncandy.mylog.MyLog;
import com.siliconorchard.myowncandy.mystatic.Constant;
import com.siliconorchard.myowncandy.util.Util;



import android.graphics.Point;

public class Matrix2D {
	public static int[][] MT;

	/**
	 * Tạo ra 1 ma trận ngẫu nhiên cho lần đầu tiên
	 */
	public static void createFirstMatrix() {
		for (int i = 0; i < MyConfig.SUM_ROW_MATRIX; i++) {
			for (int j = 0; j < MyConfig.SUM_COLUMN_MATRIX; j++) {
				MT[i][j] = Util.getRandomIndex(0, Constant.MAX_ITEM_JEWEL - 1);
				// MT[i][j] = 0;
			}
		}
	}

	/**
	 * Tạo ma trận để test
	 */
	public static void createMatrixTest() {
		for (int i = 0; i < MyConfig.SUM_ROW_MATRIX; i++) {
			int k = i;
			for (int j = 0; j < MyConfig.SUM_COLUMN_MATRIX; j++) {
				k++;
				if (k > 5)
					k = 0;
				MT[i][j] = k;
			}
		}

		MT[0][5] = 0;
		MT[0][3] = 0;
		MT[0][4] = 0;
		//
		// MT[0][1] = 0;
		// MT[1][1] = 0;
		// MT[2][1] = 0;

		// MT[3][0] = 1;
		// MT[3][1] = 1;
		// MT[3][2] = 1;
		// MT[3][3] = 0;
		// MT[3][4] = 0;
		// MT[3][5] = 0;
		//
		// MT[3][0] = 0;
		// MT[3][1] = 0;
		// MT[3][2] = 0;
		// MT[3][3] = 0;
		// MT[3][4] = 0;
		// MT[3][5] = 0;
		//
		// MT[5][0] = 0;
		// MT[5][1] = 0;
		// MT[5][2] = 0;
		// MT[5][3] = 0;
		// MT[5][4] = 0;
		// MT[5][5] = 0;
	}

	/**
	 * Lấy các vị trí có giá trị = -1. Các vị trí này là vị trí của các đối
	 * tượng được di chuyển đi, cần thêm các jewel mới vào vị trí đó.
	 * 
	 * @return
	 */
	public static ArrayList<Point> getPoint() {
		ArrayList<Point> list_point = new ArrayList<Point>();
		for (int i = 0; i < MyConfig.SUM_ROW_MATRIX; i++) {
			for (int j = 0; j < MyConfig.SUM_COLUMN_MATRIX; j++) {
				if (MT[i][j] == -1) {
					list_point.add(new Point(i, j));
				}
			}
		}
		return list_point;
	}

	/**
	 * Chả lại vị trí hiện thị với vị trí hàng và vị trí cột
	 * 
	 * @param i
	 * @param j
	 * @return
	 */
	public static Point getPxPy(int i, int j) {
		int Px = MyConfig.X_START + (j * MyConfig.WIDTH_SQUARE);
		int Py = MyConfig.Y_START + (i * MyConfig.HEIGHT_SQUARE);

		return new Point(Px, Py);
	}

	/**
	 * Khởi tạo ma trận.
	 */
	public static void ini() {
		MT = new int[MyConfig.SUM_ROW_MATRIX][MyConfig.SUM_COLUMN_MATRIX];
		// createMatrixTest();
		createFirstMatrix();
	}

	/**
	 * Hiện thị ma trận
	 */
	public static void showMatrix2D(int[][] mt) {
		for (int i = 0; i < MyConfig.SUM_ROW_MATRIX; i++) {
			for (int j = 0; j < MyConfig.SUM_COLUMN_MATRIX; j++) {
				if (mt[i][j] == -1)
					MyLog.print(" " + mt[i][j]);
				else
					MyLog.print("  " + mt[i][j]);
			}
			MyLog.println("");
		}
	}

	/**
	 * Ma trận số định nghĩa các giá trị hiện thị các viên ngọc
	 * 
	 * @return
	 */
	public int[][] getMT() {
		return MT;
	}

	/**
	 * Sét ma trận số định nghĩa các giá trị hiện thị các viên ngọc
	 * 
	 * @return
	 */
	public void setMT(int[][] mT) {
		MT = mT;
	}

}