package com.siliconorchard.myowncandy.item;


import java.util.ArrayList;
import java.util.HashMap;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.entity.IEntity;
import org.anddev.andengine.entity.modifier.MoveYModifier;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.util.modifier.IModifier;

import com.siliconorchard.myowncandy.MainGame;
import com.siliconorchard.myowncandy.Menu;
import com.siliconorchard.myowncandy.MySprite;
import com.siliconorchard.myowncandy.config.MyConfig;
import com.siliconorchard.myowncandy.control.ValueControl;
import com.siliconorchard.myowncandy.mylog.MyLog;



import android.graphics.Point;

public class Jewel extends MySprite {
	HashMap<Point, Jewel> map_Jewel;

	// Tạo ra những ngôi sao chuyển động quanh jewel
	// StarJewel //mStarJewel;

	/**
	 * Lưu lại giá trị từng jewel bên cạnh jewel hiện tại
	 */
	Jewel left_Jewel = null, right_Jewel = null, top_Jewel = null,
			bottom_Jewel = null;

	int STATUS_MOVE = -1;
	final int MOVE_LEFT = 0, MOVE_RIGHT = 1, MOVE_TOP = 2, MOVE_BOTTOM = 3;

	private Sprite item_SP;
	/**
	 * Vị trí hiện thị
	 */
	private int pX = 0, pY = 0;
	/**
	 * Chỉ số trong mảng
	 */
	private int i = -1, j = -1;

	/**
	 * Giá trị của ressource
	 */
	public int ID_resource = -1;

	/**
	 * Xác định xem jewel này có mang theo bom hay không
	 */
	public boolean isBom = false;
	/**
	 * ID để lấy bom từ mapBom của class Bom
	 */
	public int idBom = -1;

	/**
	 * Xác định xem jewel này có mang theo thunder không
	 */
	public boolean isThunder = false;
	/**
	 * ID để lấy thunder từ mapThunder của class Thunder
	 */
	public int idThunder = -1;

	public Jewel(MainGame mMainGame) {
		this.mainGame = mMainGame;
		this.map_Jewel = mMainGame.getMap_Jewel();
		// mStarJewel = new StarJewel();
	}

	// --------------------------------------------------------------------
	/**
	 * 
	 * @param mScene
	 * @param mEngine
	 * @param item_TR
	 * @param ID_resource
	 * @param star_TR
	 *            : TextureRegion dùng cho StarJewel
	 */
	public void onLoadScene(Scene mScene, Engine mEngine,
			TextureRegion item_TR, int ID_resource, TextureRegion star_TR) {
		this.mEngine = mEngine;
		this.mScene = mScene;
		this.ID_resource = ID_resource;
		this.item_SP = new Sprite(-100, -100, MyConfig.WIDTH_SQUARE,
				MyConfig.HEIGHT_SQUARE, item_TR.deepCopy()) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				// Kiểm tra xem nếu chưa xử lý xong thì sẽ không cho phép di
				// chuyển
				if (ValueControl.isTouchJewel) {
					touchJewel(pSceneTouchEvent, (int) pTouchAreaLocalX,
							(int) pTouchAreaLocalY);
				}
				return true;
			}
		};
		mScene.attachChild(item_SP);
		mScene.registerTouchArea(item_SP);
		// mStarJewel.onLoadScene(mEngine, mScene, star_TR);
		// item_SP.setVisible(false);
	}

	// --------------------------------------------------------------------
	public void touchJewel(TouchEvent event, int pX, int pY) {
		switch (event.getAction()) {
		case TouchEvent.ACTION_DOWN:
			getJewelOther();
			item_SP.setZIndex(100);
			sortChildren();
			break;
		case TouchEvent.ACTION_MOVE:
			move(pX, pY);
			break;
		case TouchEvent.ACTION_UP:
			boolean b = checkOk();
			// Nếu ăn được
			if (b) {
				// hoán đổi vị trí và gọi hàm checkAll của maingame
				mainGame.Buoc1MT();
			} else {
				// Không ăn được, sẽ đặt 2 viên kim cương ở vị trí cũ
				STATUS_MOVE = -1;
				setPositionJewel(this.pX, this.pY);
				resetJewelOther();
			}

			STATUS_MOVE = -1;

			item_SP.setZIndex(20);
			left_Jewel = null;
			right_Jewel = null;
			top_Jewel = null;
			bottom_Jewel = null;

			break;
		default:
			break;
		}
	}

	// ---------------------------------------------------------
	public void sortChildren() {
		try {
			mScene.sortChildren();
		} catch (Exception e) {
		}
	}

	// --------------------------------------------------------------------
	/**
	 * Di chuyển đối tượng
	 * 
	 * @param pX
	 * @param pY
	 */
	public void move(int pX, int pY) {

		int x_move = (int) item_SP.getX() + pX - (int) item_SP.getWidth() / 2;
		int y_move = (int) item_SP.getY() + pY - (int) item_SP.getHeight() / 2;

		// Giới hạn không cho vượt quá 1 ô
		if (y_move > this.pY + item_SP.getHeight()
				|| y_move < this.pY - item_SP.getHeight())
			return;
		if (x_move > this.pX + item_SP.getWidth()
				|| x_move < this.pX - item_SP.getWidth())
			return;

		// Giới hạn không cho ra khỏi màn hình
		if (Math.abs(this.pX - x_move) > Math.abs(this.pY - y_move)) {
			if (this.pX < x_move) {
				if (this.j + 1 == MyConfig.SUM_COLUMN_MATRIX)
					return;
				else {
					STATUS_MOVE = MOVE_RIGHT;
				}
			} else {
				if (this.j - 1 < 0)
					return;
				else {
					STATUS_MOVE = MOVE_LEFT;
				}
			}

			// di chuyển theo chiều x
			y_move = this.pY;
		} else {
			if (this.pY < y_move) {
				if (this.i + 1 == MyConfig.SUM_ROW_MATRIX)
					return;
				else {
					STATUS_MOVE = MOVE_BOTTOM;
				}
			} else {
				if (this.i - 1 < 0)
					return;
				else {
					STATUS_MOVE = MOVE_TOP;
				}
			}
			// di chuyển theo chiều y
			x_move = this.pX;
		}
		swap(x_move, y_move);
		setPositionSP(x_move, y_move);
	}

	// ---------------------------------------------------------
	/**
	 * Hoán đổi vị trí của 2 jewel
	 * 
	 * @param x_move
	 * @param y_move
	 */
	public void swap(int x_move, int y_move) {
		switch (STATUS_MOVE) {
		case MOVE_LEFT:
			if (left_Jewel != null) {
				int x = this.pX - x_move;
				left_Jewel.setPositionSP(left_Jewel.getpX() + x,
						left_Jewel.getpY());
			}
			break;
		case MOVE_RIGHT:
			if (right_Jewel != null) {
				int x = this.pX - x_move;
				right_Jewel.setPositionSP(right_Jewel.getpX() + x,
						right_Jewel.getpY());
			}
			break;
		case MOVE_TOP:
			if (top_Jewel != null) {
				int y = this.pY - y_move;
				top_Jewel.setPositionSP(top_Jewel.getpX(), top_Jewel.getpY()
						+ y);
			}
			break;
		case MOVE_BOTTOM:
			if (bottom_Jewel != null) {
				int y = this.pY - y_move;
				bottom_Jewel.setPositionSP(bottom_Jewel.getpX(),
						bottom_Jewel.getpY() + y);
			}
			break;
		default:
			break;
		}

		resetPositionJewelOther();
	}

	// ---------------------------------------------------------
	/**
	 * Kiểm tra xem giữa 2 viên kim cương khi đổi chỗ có ăn được không. Nếu ăn
	 * được return true, ngược lại return false.
	 * 
	 * Trong trường hợp ăn được sẽ đổi chỗ 2 viên kim cương này
	 * 
	 * @return
	 */
	public boolean checkOk() {
		boolean b = false;
		switch (STATUS_MOVE) {
		case MOVE_LEFT:
			b = checkMT(this, left_Jewel);
			if (b)
				swapJewel(left_Jewel);
			break;
		case MOVE_RIGHT:
			b = checkMT(this, right_Jewel);
			if (b)
				swapJewel(right_Jewel);
			break;
		case MOVE_TOP:
			b = checkMT(this, top_Jewel);
			if (b)
				swapJewel(top_Jewel);
			break;
		case MOVE_BOTTOM:
			b = checkMT(this, bottom_Jewel);
			if (b)
				swapJewel(bottom_Jewel);
			break;

		default:
			break;
		}

		return b;
	}

	// ---------------------------------------------------------
	/**
	 * Kiểm tra xem khi chuyển vị trí giữa 2 viên kim cương, nếu ăn được thì
	 * return true, ngược lại return false
	 * 
	 * @param jewel1
	 * @param jewel2
	 * @return
	 */
	public boolean checkMT(Jewel jewel1, Jewel jewel2) {
		try {
			int[][] MT_tmp = copy(Matrix2D.MT);

			MT_tmp[jewel1.getI()][jewel1.getJ()] = jewel2.getID_resource();
			MT_tmp[jewel2.getI()][jewel2.getJ()] = jewel1.getID_resource();

			boolean b = checkRemove(jewel1.getI(), jewel1.getJ(), MT_tmp);
			if (b)
				return true;
			b = checkRemove(jewel2.getI(), jewel2.getJ(), MT_tmp);
			if (b)
				return true;
		} catch (Exception e) {
			MyLog.error("error = " + e.toString());
		}
		return false;
	}

	// ---------------------------------------------------------
	public int[][] copy(int[][] mt) {
		int[][] mt_tmp = new int[MyConfig.SUM_ROW_MATRIX][MyConfig.SUM_COLUMN_MATRIX];
		for (int i = 0; i < MyConfig.SUM_ROW_MATRIX; i++) {
			for (int j = 0; j < MyConfig.SUM_COLUMN_MATRIX; j++)
				mt_tmp[i][j] = mt[i][j];
		}
		return mt_tmp;
	}

	// ---------------------------------------------------------
	/**
	 * Kiểm tra xem tại vị trí I,J trong ma trận mt có thỏa mãn điều kiện hay
	 * không Nếu thỏa mãn điều kiện ăn được thì return true. Ngược lại là return
	 * false
	 * 
	 * @param I
	 * @param J
	 * @param mt
	 * @return
	 */
	public boolean checkRemove(int I, int J, int[][] mt_tmp) {
		int count_colum = 0, count_row = 0;
		int gt = mt_tmp[I][J];

		// Duyệt theo chiều hàng tăng
		for (int i = I + 1; i < MyConfig.SUM_ROW_MATRIX; i++) {
			if (mt_tmp[i][J] == gt) {
				count_row++;
			} else
				break;
		}

		// Duyệt theo chiều hàng giảm
		for (int i = I - 1; i > -1; i--) {
			if (mt_tmp[i][J] == gt) {
				count_row++;
			} else
				break;
		}

		// Duyệt theo chiều cột tăng
		for (int j = J + 1; j < MyConfig.SUM_COLUMN_MATRIX; j++) {
			if (mt_tmp[I][j] == gt) {
				count_colum++;
			} else
				break;
		}

		// Duyệt theo chiều cột giảm
		for (int j = J - 1; j > -1; j--) {
			if (mt_tmp[I][j] == gt) {
				count_colum++;
			} else
				break;
		}

		if (count_colum > 1 || count_row > 1)
			return true;
		return false;
	}

	// ---------------------------------------------------------
	/**
	 * Hoán đổi vị trí của 2 jewel
	 * 
	 * @param jewel
	 */
	public void swapJewel(Jewel jewel) {
		synchronized (map_Jewel) {
			final int I1 = this.getI();
			final int J1 = this.getJ();
			final int ID1 = this.getID_resource();
			final int PX1 = this.getpX();
			final int PY1 = this.getpY();
			final boolean ISBOM1 = this.getIsBom();
			final boolean ISTHUNDER1 = this.getIsThunder();

			final int I2 = jewel.getI();
			final int J2 = jewel.getJ();
			final int ID2 = jewel.getID_resource();
			final int PX2 = jewel.getpX();
			final int PY2 = jewel.getpY();
			final boolean ISBOM2 = jewel.getIsBom();
			final boolean ISTHUNDER2 = jewel.getIsThunder();

			// Xóa bỏ 2 viên kim cương
			Point key = new Point(this.getI(), this.getJ());
			Jewel jewel_tmp = map_Jewel.get(key);

			map_Jewel.remove(key);
			if (jewel_tmp != null)
				jewel_tmp.onDestroy();

			key = new Point(jewel.getI(), jewel.getJ());
			jewel_tmp = map_Jewel.get(key);
			if (jewel_tmp != null)
				jewel_tmp.onDestroy();
			map_Jewel.remove(key);

			// Tạo ra 2 viên kim cương và đặt vào vị trí
			ArrayList<DataItemJewel> list_DataBuoc4Map = new ArrayList<DataItemJewel>();
			list_DataBuoc4Map.add(new DataItemJewel(ID2, PX1, PY1, I1, J1,
					ISBOM2, ISTHUNDER2));
			list_DataBuoc4Map.add(new DataItemJewel(ID1, PX2, PY2, I2, J2,
					ISBOM1, ISTHUNDER1));
			mainGame.addJewelToMap(list_DataBuoc4Map, false);

			// Sét lại giá trị trong ma trận
			Matrix2D.MT[I1][J1] = ID2;
			Matrix2D.MT[I2][J2] = ID1;
			// Hiện thị ma trận (Log để xem kết quả ma trận)
			Matrix2D.showMatrix2D(Matrix2D.MT);
			mainGame.showMap();
		}
	}

	// ---------------------------------------------------------
	/**
	 * Lưu lại 4 vị trí lân cận
	 */
	public void getJewelOther() {
		synchronized (map_Jewel) {
			left_Jewel = map_Jewel.get(new Point(getI(), getJ() - 1));
			right_Jewel = map_Jewel.get(new Point(getI(), getJ() + 1));
			top_Jewel = map_Jewel.get(new Point(getI() - 1, getJ()));
			bottom_Jewel = map_Jewel.get(new Point(getI() + 1, getJ()));
		}
	}

	// ---------------------------------------------------------
	/**
	 * Đặt trạng thái mặc định cho toàn bọ các jewel bên cạnh.
	 */
	public void resetJewelOther() {

		if (left_Jewel != null)
			left_Jewel.resetPositionJewel();

		if (right_Jewel != null)
			right_Jewel.resetPositionJewel();

		if (top_Jewel != null)
			top_Jewel.resetPositionJewel();

		if (bottom_Jewel != null)
			bottom_Jewel.resetPositionJewel();
	}

	// ---------------------------------------------------------
	public void resetPositionJewelOther() {
		if (STATUS_MOVE != MOVE_LEFT && left_Jewel != null)
			left_Jewel.resetPositionJewel();

		if (STATUS_MOVE != MOVE_RIGHT && right_Jewel != null)
			right_Jewel.resetPositionJewel();

		if (STATUS_MOVE != MOVE_TOP && top_Jewel != null)
			top_Jewel.resetPositionJewel();

		if (STATUS_MOVE != MOVE_BOTTOM && bottom_Jewel != null)
			bottom_Jewel.resetPositionJewel();
	}

	// ---------------------------------------------------------
	public void resetJewel() {
		item_SP.setScale(1f);
		item_SP.setRotation(0);
		item_SP.setVisible(true);
	}

	// ---------------------------------------------------------
	/**
	 * Hiện thị lần đầu tiên, các viên kim cương dơi từ trên xuống
	 */
	public void moveFirst() {
		float time = 1.5f;
		final MoveYModifier moveYModifier = new MoveYModifier(time, -100,
				this.getpY());
		item_SP.registerEntityModifier(moveYModifier);
		// Nếu có bom thì ta sẽ di chuyển bom cùng với kim cương
		if (isBom) {
			Sprite mbom = mainGame.mBom.getmapSpriteBomMapByKey(this.idBom);
			if (mbom != null) {
				final MoveYModifier moveYModifier_bom = new MoveYModifier(time,
						-100, this.getpY());
				mbom.registerEntityModifier(moveYModifier_bom);
			}
		}
		// Nếu có sét thì di chuyển luôn cả set
		if (isThunder) {
			Sprite mthunder = mainGame.mThunder
					.getSpriteThunderByKey(this.idThunder);
			if (mthunder != null) {
				final MoveYModifier moveYModifier_thunder = new MoveYModifier(
						time, -100, this.getpY());
				mthunder.registerEntityModifier(moveYModifier_thunder);
			}
		}
	}

	public void moveYJewels(final float yend, final boolean end) {
		float time = 0.5f;
		final MoveYModifier moveYModifier = new MoveYModifier(time,
				this.getpY(), yend,
				new MoveYModifier.IEntityModifierListener() {

					@Override
					public void onModifierStarted(IModifier<IEntity> pModifier,
							IEntity pItem) {
						mainGame.getmStar().addStar(Jewel.this.getpX(),
								Jewel.this.getpY());
						if (Menu.mSound != null)
							Menu.mSound.playImpactmic();
					}

					@Override
					public void onModifierFinished(
							IModifier<IEntity> pModifier, IEntity pItem) {
						Jewel.this.setPositionJewel(Jewel.this.getpX(),
								(int) yend);
						if (end)
							mainGame.ActiveBuoc4MT();
					}
				});
		item_SP.registerEntityModifier(moveYModifier);
		// Nếu có bom thì ta sẽ di chuyển bom cùng với kim cương
		if (isBom) {
			Sprite mbom = mainGame.mBom.getmapSpriteBomMapByKey(this.idBom);
			if (mbom != null) {
				final MoveYModifier moveYModifier_bom = new MoveYModifier(time,
						this.getpY(), yend);
				mbom.registerEntityModifier(moveYModifier_bom);
			}
		}
		// Nếu có sét thì di chuyển luôn cả set
		if (isThunder) {
			Sprite mthunder = mainGame.mThunder
					.getSpriteThunderByKey(this.idThunder);
			if (mthunder != null) {
				final MoveYModifier moveYModifier_thunder = new MoveYModifier(
						time, this.getpY(), yend);
				mthunder.registerEntityModifier(moveYModifier_thunder);
			}
		}
	}

	// ----------------------------------- GET - SET --------------------------

	public void setIdBom(int idBom) {
		isBom = true;
		this.idBom = idBom;
	}

	public void setIdThunder(int idThunder) {
		this.isThunder = true;
		this.idThunder = idThunder;
	}

	/**
	 * Xoay jewel theo 1 góc nào đó
	 * 
	 * @param pRotation
	 */
	public void rotation(int pRotation) {
		item_SP.setRotation(pRotation);
	}

	//
	// /**
	// * Hiện thị jewel
	// */
	// public void show() {
	// item_SP.setVisible(true);
	// }
	//
	// public void hide() {
	// item_SP.setVisible(false);
	// }
	// ---------------------------------------------------------
	public void setScale(float pScale) {
		item_SP.setScale(pScale);
	}

	// ---------------------------------------------------------
	public void setVisiable(boolean visiable) {
		item_SP.setVisible(visiable);
		// mStarJewel.setVisiable(visiable);
		mainGame.setVisiableBom(idBom, visiable);
		mainGame.setVisiableThunderBall(idThunder, visiable);
	}

	// ---------------------------------------------------------
	/**
	 * Vị trí hiện thị trên màn hình cua Jewel, đồng thời kéo thoe là hiện thị
	 * bom và thunder
	 * 
	 * @param x
	 * @param y
	 */
	public void setPositionJewel(int x, int y) {
		item_SP.setPosition(x, y);
		// mStarJewel.setPosition(x, y);
		mainGame.setPositionBom(x, y, idBom);
		mainGame.setPositionThunderBall(x, y, idThunder);
		this.pX = x;
		this.pY = y;
	}

	// ---------------------------------------------------------
	public void resetPositionJewel() {
		item_SP.setPosition(this.pX, this.pY);
		// mStarJewel.setPosition(this.pX, this.pY);
		mainGame.setPositionBom(this.pX, this.pY, idBom);
		mainGame.setPositionThunderBall(this.pX, this.pY, idThunder);
	}

	// ---------------------------------------------------------
	/**
	 * Vị trí hiện thị
	 * 
	 * @param x
	 * @param y
	 */
	public void setPositionSP(int x, int y) {
		item_SP.setPosition(x, y);
		// mStarJewel.setPosition(x, y);
		mainGame.setPositionBom(x, y, idBom);
		mainGame.setPositionThunderBall(x, y, idThunder);
	}

	// ---------------------------------------------------------
	/**
	 * Vị trí hiện thị trên ma trận
	 * 
	 * @param i
	 * @param j
	 */
	public void setIJ(int i, int j) {
		this.i = i;
		this.j = j;
	}

	// ---------------------------------------------------------
	public Sprite getItem_SP() {
		return item_SP;
	}

	public int getpX() {
		return pX;
	}

	public int getpY() {
		return pY;
	}

	public int getI() {
		return i;
	}

	public int getJ() {
		return j;
	}

	public int getID_resource() {
		return ID_resource;
	}

	public void setID_resource(int iD_resource) {
		ID_resource = iD_resource;
	}

	public boolean getIsBom() {
		return this.isBom;
	}

	public boolean getIsThunder() {
		return this.isThunder;
	}

	// ---------------------------------------------------------
	/**
	 * Xóa bỏ đối tượng
	 */
	public void onDestroy() {
		// mStarJewel.removeStarJewel();
		mainGame.removeBom(idBom);
		mainGame.removeThunderBall(idThunder);
		mEngine.runOnUpdateThread(new Runnable() {
			@Override
			public void run() {
				mScene.unregisterTouchArea(item_SP);
				mScene.detachChild(item_SP);
			}
		});
	}
}