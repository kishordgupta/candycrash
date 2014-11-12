package com.siliconorchard.myowncandy;

import java.util.ArrayList;

import com.siliconorchard.myowncandy.config.MyConfig;

public class Level {

	/**
	 * số coin cần phải đạt được theo level đó
	 */
	public static int[] coinLevel = new int[] { 200, // 1
			400, // 2
			800, // 3

			1000, // 4
			1500, // 5
			2000, // 6

			3000, // 7
			3500, // 8
			4000, // 9

			4500, // 10
			5000, // 11
			6500, // 12

			7000, // 13
			8000, // 14
			9000, // 15

			10000, // 16
			11000, // 17
			13000, // 18

			15000, // 19
			30000, // 20
			35000, // 21

			40000, // 22
			50000, // 23
			60000, // 24
			70000, // 25

			75000, // 26
			80000, // 27
			85000, // 28

			90000, // 29
			100000 // 30
	};

	public static int levelCurrent = 1;

	public static int maxLevel = 30;

	public static int levelUnlock = 1;

	/**
	 * Thời gian theo từng level (đơn vị milis)
	 */
	public static int[] timeLevel = new int[] { 60, // 1
			60, // 2
			60, // 3

			120, // 4
			120, // 5
			120, // 6

			180, // 7
			180, // 8
			180, // 9

			260, // 10
			260, // 11
			260, // 12

			320, // 13
			320, // 14
			320, // 15

			380, // 16
			380, // 17
			380, // 18

			440, // 19
			440, // 20
			440, // 21

			500, // 22
			500, // 23
			560, // 24
			600, // 25

			700, // 26
			740, // 27
			760, // 28

			800, // 29
			900, // 30

	};

	// Type Classic
	/**
	 * Level đang chơi hiện tại
	 */
	public static int levelCurrentClassic = 1;
	/**
	 * Số coin đang chơi
	 */
	public static int coinLevelCurrentClassic = 0;
	/**
	 * Ma trận quy định số lượng coin cần vượt qua (50 level)
	 */
	public static int[] numberCoinLevelClassic = new int[] { 200, 300, 400,
			500, 800, 900, 1000, 1200, 1500, 1800, 2000, 2400, 2800, 3000,
			3400, 3600, 4000, 4500, 4900, 5200, 6000, 6500, 7000, 7800, 8500,
			9500, 10000, 15000, 20000, 25000, 30000, 40000, 50000, 60000,
			70000, 80000, 85000, 90000, 95000, 100000, 101000, 105000, 110000,
			120000, 130000, 140000, 150000, 170000, 200000, 300000 };

	/**
	 * Type New Mode
	 */
	public static int levelCurrentNewMode = 1;
	public static ArrayList<int[][]> arrayListMode = new ArrayList<int[][]>();
	public static int totalSquare = 0;
	public static int squareCurrent = 0;
	public static int totalLevelSquare = 20;

	public static void iniArrayListMode() {

		int[][] new1 = new int[][] { { 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 1, 1, 1, 0, 0 },
				{ 0, 0, 1, 1, 1, 0, 0 }, { 0, 0, 1, 1, 1, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0 } };
		int[][] new2 = new int[][] { { 1, 1, 1, 0, 0, 0, 0 },
				{ 1, 1, 1, 0, 0, 0, 0 }, { 1, 1, 1, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 1, 1, 1 },
				{ 0, 0, 0, 0, 1, 1, 1 }, { 0, 0, 0, 0, 1, 1, 1 } };
		int[][] new3 = new int[][] { { 0, 0, 0, 0, 1, 1, 1 },
				{ 0, 0, 0, 0, 1, 1, 1 }, { 0, 0, 0, 0, 1, 1, 1 },
				{ 0, 0, 0, 0, 0, 0, 0 }, { 1, 1, 1, 0, 0, 0, 0 },
				{ 1, 1, 1, 0, 0, 0, 0 }, { 1, 1, 1, 0, 0, 0, 0 } };
		int[][] new4 = new int[][] { { 0, 0, 1, 1, 1, 0, 0 },
				{ 0, 0, 1, 1, 1, 0, 0 }, { 1, 1, 1, 1, 1, 1, 1 },
				{ 1, 1, 1, 1, 1, 1, 1 }, { 1, 1, 1, 1, 1, 1, 1 },
				{ 0, 0, 1, 1, 1, 0, 0 }, { 0, 0, 1, 1, 1, 0, 0 } };
		int[][] new5 = new int[][] { { 1, 1, 0, 0, 0, 1, 1 },
				{ 1, 1, 1, 0, 1, 1, 1 }, { 0, 1, 1, 1, 1, 1, 0 },
				{ 0, 0, 1, 1, 1, 0, 0 }, { 0, 1, 1, 1, 1, 1, 0 },
				{ 1, 1, 1, 0, 1, 1, 1 }, { 1, 1, 0, 0, 0, 1, 1 } };
		int[][] new6 = new int[][] { { 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 1, 1, 0, 1, 1, 0 }, { 1, 1, 1, 1, 1, 1, 1 },
				{ 0, 1, 1, 1, 1, 1, 0 }, { 0, 0, 1, 1, 1, 0, 0 },
				{ 0, 0, 0, 1, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0 } };
		int[][] new7 = new int[][] { { 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 1, 0, 0, 0 }, { 0, 0, 1, 1, 1, 0, 0 },
				{ 0, 1, 1, 1, 1, 1, 0 }, { 0, 0, 1, 1, 1, 0, 0 },
				{ 0, 0, 0, 1, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0 } };
		int[][] new8 = new int[][] { { 1, 1, 1, 0, 1, 1, 1 },
				{ 1, 1, 0, 0, 0, 1, 1 }, { 1, 0, 0, 0, 0, 0, 1 },
				{ 0, 0, 0, 0, 0, 0, 0 }, { 1, 0, 0, 0, 0, 0, 1 },
				{ 1, 1, 0, 0, 0, 1, 1 }, { 1, 1, 1, 0, 1, 1, 1 } };
		int[][] new9 = new int[][] { { 1, 1, 1, 1, 1, 1, 1 },
				{ 0, 0, 0, 0, 0, 0, 0 }, { 1, 1, 1, 1, 1, 1, 1 },
				{ 0, 0, 0, 0, 0, 0, 0 }, { 1, 1, 1, 1, 1, 1, 1 },
				{ 0, 0, 0, 0, 0, 0, 0 }, { 1, 1, 1, 1, 1, 1, 1 } };
		int[][] new10 = new int[][] { { 1, 0, 1, 0, 1, 0, 1 },
				{ 1, 0, 1, 0, 1, 0, 1 }, { 1, 0, 1, 0, 1, 0, 1 },
				{ 1, 0, 1, 0, 1, 0, 1 }, { 1, 0, 1, 0, 1, 0, 1 },
				{ 1, 0, 1, 0, 1, 0, 1 }, { 1, 0, 1, 0, 1, 0, 1 } };
		int[][] new11 = new int[][] { { 1, 1, 1, 1, 1, 1, 1 },
				{ 0, 1, 1, 1, 1, 1, 0 }, { 0, 0, 1, 1, 1, 0, 0 },
				{ 0, 0, 0, 1, 0, 0, 0 }, { 0, 0, 1, 1, 1, 0, 0 },
				{ 0, 1, 1, 1, 1, 1, 0 }, { 1, 1, 1, 1, 1, 1, 1 } };
		int[][] new12 = new int[][] { { 0, 1, 0, 1, 0, 1, 0 },
				{ 1, 0, 1, 0, 1, 0, 1 }, { 0, 1, 0, 1, 0, 1, 0 },
				{ 1, 0, 1, 0, 1, 0, 1 }, { 0, 1, 0, 1, 0, 1, 0 },
				{ 1, 0, 1, 0, 1, 0, 1 }, { 0, 1, 0, 1, 0, 1, 0 } };
		int[][] new13 = new int[][] { { 1, 1, 0, 0, 0, 0, 0 },
				{ 1, 1, 0, 1, 1, 0, 0 }, { 0, 0, 0, 1, 1, 0, 0 },
				{ 0, 1, 1, 0, 0, 1, 1 }, { 0, 1, 1, 0, 1, 1, 0 },
				{ 0, 0, 0, 0, 1, 1, 0 }, { 0, 1, 1, 0, 0, 0, 0 } };
		int[][] new14 = new int[][] { { 1, 1, 1, 1, 0, 0, 0 },
				{ 1, 1, 1, 1, 0, 0, 0 }, { 1, 1, 1, 1, 0, 0, 0 },
				{ 1, 1, 1, 1, 0, 0, 0 }, { 1, 1, 1, 1, 0, 0, 0 },
				{ 1, 1, 1, 1, 0, 0, 0 }, { 1, 1, 1, 1, 0, 0, 0 } };
		int[][] new15 = new int[][] { { 0, 0, 0, 1, 1, 1, 1 },
				{ 0, 0, 0, 1, 1, 1, 1 }, { 0, 0, 0, 1, 1, 1, 1 },
				{ 0, 0, 0, 1, 1, 1, 1 }, { 0, 0, 0, 1, 1, 1, 1 },
				{ 0, 0, 0, 1, 1, 1, 1 }, { 0, 0, 0, 1, 1, 1, 1 } };
		int[][] new16 = new int[][] { { 1, 1, 1, 1, 1, 1, 1 },
				{ 1, 1, 1, 1, 1, 1, 1 }, { 1, 1, 1, 1, 1, 1, 1 },
				{ 1, 1, 1, 1, 1, 1, 1 }, { 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0 } };
		int[][] new17 = new int[][] { { 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0 },
				{ 1, 1, 1, 1, 1, 1, 1 }, { 1, 1, 1, 1, 1, 1, 1 },
				{ 1, 1, 1, 1, 1, 1, 1 }, { 1, 1, 1, 1, 1, 1, 1 } };
		int[][] new18 = new int[][] { { 1, 1, 1, 1, 1, 1, 1 },
				{ 1, 1, 0, 0, 0, 1, 1 }, { 1, 0, 1, 0, 1, 0, 1 },
				{ 1, 0, 0, 1, 0, 0, 1 }, { 1, 0, 1, 0, 1, 0, 1 },
				{ 1, 1, 0, 0, 0, 1, 1 }, { 1, 1, 1, 1, 1, 1, 1 } };
		int[][] new19 = new int[][] { { 1, 1, 1, 1, 1, 1, 1 },
				{ 1, 0, 0, 1, 0, 0, 1 }, { 1, 0, 0, 1, 0, 0, 1 },
				{ 1, 1, 1, 1, 1, 1, 1 }, { 1, 0, 0, 1, 0, 0, 1 },
				{ 1, 0, 0, 1, 0, 0, 1 }, { 1, 1, 1, 1, 1, 1, 1 } };
		int[][] new20 = new int[][] { { 1, 1, 1, 1, 1, 1, 1 },
				{ 1, 1, 1, 1, 1, 1, 1 }, { 1, 1, 1, 1, 1, 1, 1 },
				{ 1, 1, 1, 1, 1, 1, 1 }, { 1, 1, 1, 1, 1, 1, 1 },
				{ 1, 1, 1, 1, 1, 1, 1 }, { 1, 1, 1, 1, 1, 1, 1 } };
		arrayListMode.add(new1);
		arrayListMode.add(new2);
		arrayListMode.add(new3);
		arrayListMode.add(new4);
		arrayListMode.add(new5);
		arrayListMode.add(new6);
		arrayListMode.add(new7);
		arrayListMode.add(new8);
		arrayListMode.add(new9);
		arrayListMode.add(new10);
		arrayListMode.add(new11);
		arrayListMode.add(new12);
		arrayListMode.add(new13);
		arrayListMode.add(new14);
		arrayListMode.add(new15);
		arrayListMode.add(new16);
		arrayListMode.add(new17);
		arrayListMode.add(new18);
		arrayListMode.add(new19);
		arrayListMode.add(new20);
	}

	/**
	 * Tổng số ô vuông có
	 * 
	 * @param index
	 * @return
	 */
	public static int getTotalSquare(int index) {
		arrayListMode.clear();
		iniArrayListMode();

		int[][] mt = arrayListMode.get(index);
		int dem = 0;
		for (int i = 0; i < MyConfig.SUM_ROW_MATRIX; i++)
			for (int j = 0; j < MyConfig.SUM_COLUMN_MATRIX; j++) {
				if (mt[i][j] != 0)
					dem++;
			}
		return dem;
	}

	public static int[][] getMT() {
		arrayListMode.clear();
		iniArrayListMode();
		return arrayListMode.get(Level.levelCurrentNewMode - 1);
	}
}