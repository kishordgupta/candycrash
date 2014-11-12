package com.siliconorchard.myowncandy.item;

public class DataItemJewel {
	int i = 0, j = 0, x = 0, y = 0, index = 0;
	boolean isBom = false, isThunder = false;

	public DataItemJewel(int index, int x, int y, int i, int j, boolean isBom,
			boolean isThunder) {
		this.index = index;
		this.x = x;
		this.y = y;
		this.i = i;
		this.j = j;
		this.isBom = isBom;
		this.isThunder = isThunder;
	}

	public int getI() {
		return i;
	}

	public int getJ() {
		return j;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getIndex() {
		return index;
	}

	public boolean getIsBom() {
		return this.isBom;
	}

	public boolean getIsThunder() {
		return this.isThunder;
	}

}