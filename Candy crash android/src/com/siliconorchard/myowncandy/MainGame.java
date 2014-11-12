package com.siliconorchard.myowncandy;

import java.util.ArrayList;
import java.util.HashMap;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.entity.scene.Scene;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;

import com.ironsource.mobilcore.MobileCore;
import com.ironsource.mobilcore.OnReadyListener;
import com.ironsource.mobilcore.MobileCore.AD_UNITS;
import com.ironsource.mobilcore.MobileCore.LOG_TYPE;
import com.siliconorchard.myowncandy.R;
import com.siliconorchard.myowncandy.config.MyConfig;
import com.siliconorchard.myowncandy.control.ButtonPauseGame;
import com.siliconorchard.myowncandy.control.ValueControl;
import com.siliconorchard.myowncandy.database.Database;
import com.siliconorchard.myowncandy.dialog.DialogCompleted;
import com.siliconorchard.myowncandy.dialog.DialogGameOver;
import com.siliconorchard.myowncandy.item.Bom;
import com.siliconorchard.myowncandy.item.DataItemJewel;
import com.siliconorchard.myowncandy.item.Jewel;
import com.siliconorchard.myowncandy.item.LoadResourcesJewel;
import com.siliconorchard.myowncandy.item.Matrix2D;
import com.siliconorchard.myowncandy.item.Square;
import com.siliconorchard.myowncandy.item.Star;
import com.siliconorchard.myowncandy.item.ThreadHide;
import com.siliconorchard.myowncandy.item.Thunder;
import com.siliconorchard.myowncandy.mylog.MyLog;
import com.siliconorchard.myowncandy.mystatic.Constant;
import com.siliconorchard.myowncandy.util.Util;

public class MainGame extends Game {

	MySharedPreferences mySharedPreferences;

	BackGround mBackGround;
	BgSquare mBgSquare;
	Text mText;
	public Square mSquare;

	LoadResourcesJewel mLoadResourcesJewel;
	public Bom mBom;
	public Thunder mThunder;
	HashMap<Point, Jewel> map_Jewel;

	Star mStar;

	ButtonPauseGame mButtonPauseGame;
	public ProgressBar mProgressBar;

	Coin mCoin;
//	 private RevMob revmob;
	int combos = 0;

	// ==============================================================================
	@Override
	protected void onCreate(Bundle pSavedInstanceState) {
		super.onCreate(pSavedInstanceState);
		this.getWindow().getAttributes().windowAnimations = R.style.Animations_Activity;
		MobileCore.init(this,"8YKF89U9YHXZUIMHFEQ0UDC1T9TOY", LOG_TYPE.DEBUG, AD_UNITS.ALL_UNITS);
		//MobileCore.getSlider().setContentViewWithSlider(this, R.layout.activity_select_level);
		// Look up the AdView as a resource and load a request.
		 MobileCore.setStickeezReadyListener(new OnReadyListener()
		 { @Override 
			 public void onReady(AD_UNITS adUnit) {
			 if (adUnit.equals(AD_UNITS.STICKEEZ)){
				 //do something 
			
		 }
		 } }
		 );
		 MobileCore.showStickee(this);
//		 revmob = RevMob.start(this);

		mySharedPreferences = new MySharedPreferences(this);
		mBackGround = new BackGround();
		mBgSquare = new BgSquare();
		mText = new Text();
		mSquare = new Square();

		mLoadResourcesJewel = new LoadResourcesJewel();
		mBom = new Bom();
		mThunder = new Thunder();

		map_Jewel = new HashMap<Point, Jewel>();

		mStar = new Star();
		mButtonPauseGame = new ButtonPauseGame();
		mProgressBar = new ProgressBar();

		mCoin = new Coin();

		// Control
		ValueControl.ini();
	}
	
	// ==============================================================================
	public Engine onLoadEngine() {
		super.onLoadEngine();
		return this.mEngine;
	}

	// ==============================================================================
	public void onLoadResources() {
		super.onLoadResources();
		long t = System.currentTimeMillis();

		mBackGround.onLoadResources(mEngine, mContext);
		mBgSquare.onLoadResources(mEngine, mContext);
		mText.onLoadResources(mEngine, mContext);
		mSquare.onLoadResources(mEngine, mContext);

		mStar.onLoadResources(mEngine, mContext);
		mButtonPauseGame.onLoadResources(mEngine, mContext, this);
		mProgressBar.onLoadResources(mEngine, mContext);

		mLoadResourcesJewel.onLoadResources(mEngine, mContext);
		mBom.onLoadResources(mEngine, mContext);
		mThunder.onLoadResources(mEngine, mContext);

		mCoin.onLoadResources(mEngine, mContext);

		MyLog.error("Time onLoadResources = "
				+ (System.currentTimeMillis() - t) + " ms");
	}

	// ==============================================================================
	public Scene onLoadScene() {
		super.onLoadScene();
		ValueControl.isCompletedLevel = false;

		// Hiện thị background
		mBackGround.onLoadScene(mScene);
		// Thêm những hình vuông ở background
		mBgSquare.onLoadScene(mScene);
		// Hiện thị level và coin
		mText.onLoadScene(mScene);
		// Hiện thị button pause
		mButtonPauseGame.onLoadScene(mScene);
		// Hiện thị thanh thời gian
		mProgressBar.onLoadScene(mScene);
		if (ValueControl.TypeGame == ValueControl.TimeAttach)
			mProgressBar.setTotalTime(Level.timeLevel[Level.levelCurrent - 1]);
		else if (ValueControl.TypeGame == ValueControl.Classic)
			mProgressBar.updateProgressBarClassic();
		else if (ValueControl.TypeGame == ValueControl.NewMode)
			mProgressBar.updateProgressbarNewMode();

		// Đồng tiền khi người chơi ăn thì sẽ hiện thị
		mCoin.onLoadScene(mScene);
		mCoin.setTextCoin(mText);
		mCoin.setBigCoin(mBackGround.getBigCoin());
		// Số coin hiện đã có
		if (ValueControl.TypeGame == ValueControl.Classic)
			mCoin.setCoin(Level.coinLevelCurrentClassic);
		// Add star
		mStar.onLoadScene(mScene);
		// Add bom
		mBom.onLoadScene(mScene);
		// Add set
		mThunder.onLoadScene(mScene);

		mSquare.onLoadScene(mScene);

		// Khởi tạo ma trận ngẫu nhiên
		Matrix2D.ini();
		// load tài nguyên của các viên kim cương
		mLoadResourcesJewel.onLoadResources(mEngine, mContext);
		// Tạo các viên kim cương và đặt vào vị trí hiện thị
		loadFirstItem();
		return this.mScene;
	}

	// ==============================================================================
	/**
	 * Reset lại game
	 */
	public void resetGame() {
		combos = 0;
		ValueControl.isCompletedLevel = false;
		// Xóa bỏ toàn bộ kim cương hiện đang có
		resetMap();

		Level.squareCurrent = 0;

		mCoin.reset();
		mText.reset();

		if (ValueControl.TypeGame == ValueControl.TimeAttach) {
			mProgressBar.reload();
			mProgressBar.setTotalTime(Level.timeLevel[Level.levelCurrent - 1]);
		}
		// Khởi tạo ma trận ngẫu nhiên
		Matrix2D.ini();

		// Load lại toàn bộ kim cương và hiện thị lại
		loadFirstItem();
	}

	// ==============================================================================
	@Override
	protected void onResume() {
		super.onResume();
		resumeGame();

//		 RevMob revmob = RevMob.start(this, "536862d4afd6f3e937d487ec");
//		 revmob.showPopup(this);
	}

	// ==============================================================================
	@Override
	protected void onPause() {
		super.onPause();
		pauseGame();
	}

	// ==============================================================================
	public void onLoadComplete() {
		super.onLoadComplete();
	}

	// ==============================================================================
	// =====================================Method===================================
	// ==============================================================================
	public void pauseGame() {
		if (mProgressBar != null) {
			mProgressBar.setPause(true);
		}
	}

	// ==============================================================================
	public void resumeGame() {
		if (mProgressBar != null) {
			mProgressBar.setPause(false);
		}
	}

	// ==============================================================================
	/**
	 * Tạo ra ma trận ngẫu nhiên lần đầu tiên
	 */
	public void loadFirstItem() {
		int startY = MyConfig.Y_START;
		// danh sách các vị trí cần add
		ArrayList<DataItemJewel> list_DataItemJewel_add = new ArrayList<DataItemJewel>();

		int[][] MTSquare = Level.getMT();
		if (ValueControl.TypeGame == ValueControl.NewMode) {
			mSquare.reset();
		}
		for (int i = 0; i < MyConfig.SUM_ROW_MATRIX; i++) {
			int startX = MyConfig.X_START;
			for (int j = 0; j < MyConfig.SUM_COLUMN_MATRIX; j++) {

				if (ValueControl.TypeGame == ValueControl.NewMode) {
					if (MTSquare[i][j] != 0)
						mSquare.addToList(i, j, startX, startY);
				}

				boolean haveBom = mBom.isHaveBom();
				boolean haveThunder = false;
				if (!haveBom)
					haveThunder = mThunder.isHaveThundeBall();

				DataItemJewel mDataItemJewel = new DataItemJewel(
						Matrix2D.MT[i][j], startX, startY, i, j, haveBom,
						haveThunder);

				list_DataItemJewel_add.add(mDataItemJewel);
				startX = startX + MyConfig.WIDTH_SQUARE;
			}
			startY = startY + MyConfig.HEIGHT_SQUARE;
		}

		addJewelToMap(list_DataItemJewel_add, true);

		MyLog.println("-------loadFirstItem--------");
		Matrix2D.showMatrix2D(Matrix2D.MT);
		MyLog.println("--------------------------------------");
	}

	// ==============================================================================
	/**
	 * Đưa toàn bộ các đối tượng vào map để dễ quản lý
	 * 
	 * @param index
	 *            :
	 * @param x
	 * @param y
	 * @param i
	 * @param j
	 */
	public void addJewelToMap(ArrayList<DataItemJewel> list_DataItemJewel_add,
			boolean first) {
		synchronized (map_Jewel) {
			if (ValueControl.isCompletedLevel)
				return;

			ArrayList<Jewel> list_Jewel_add = new ArrayList<Jewel>();

			for (int i = 0; i < list_DataItemJewel_add.size(); i++) {
				DataItemJewel data_item = list_DataItemJewel_add.get(i);

				int x = data_item.getX();
				int y = data_item.getY();

				int I = data_item.getI();
				int J = data_item.getJ();

				Jewel mJewel = new Jewel(this);
				mJewel.onLoadScene(mScene, mEngine, mLoadResourcesJewel
						.getResourceName(data_item.getIndex()), data_item
						.getIndex(), mStar.getTextureRegion());
				mJewel.setPositionJewel(x, y);
				mJewel.setIJ(I, J);

				// Đặt đối tượng jewel có bom
				if (data_item.getIsBom() == true) {
					int idBom = mBom.getIDBomNext();
					mJewel.setIdBom(idBom);
					mBom.addBom(x, y, idBom);
				}
				// Đặt đối tượng jewel có thunder
				if (data_item.getIsThunder() == true) {
					int idThunder = mThunder.getIdThunderBallNext();
					mJewel.setIdThunder(idThunder);
					mThunder.addThunderBall(x, y, idThunder);
				}
				list_Jewel_add.add(mJewel);
				map_Jewel.put(new Point(I, J), mJewel);
			}

			// Nếu là trường hợp đâu tiên thì sẽ hiện thị toàn bộ các viên kim
			// cương
			// với hiệu ứng rơi từ trên xuống
			if (first)
				showFirst();
		}
	}

	// -------------------------------------------------------
	public void showFirst() {
		synchronized (map_Jewel) {
			for (int i = 0; i < MyConfig.SUM_ROW_MATRIX; i++) {
				for (int j = 0; j < MyConfig.SUM_COLUMN_MATRIX; j++) {
					Jewel mJewel = map_Jewel.get(new Point(i, j));
					if (mJewel != null)
						mJewel.moveFirst();
				}
			}
			// Sau khi số kim cương đã rơi hết thì hiện thị text level
			showTextLevel();
		}
	}

	// -------------------------------------------------------
	public void showTextLevel() {
		Handler mHandler = new Handler();
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				// Hiện thị text thông báo level hiện tại
				mText.showTextLevel();
			}
		}, 2000);
	}

	// -------------------------------------------------------
	public void setVisiableBom(int idBom, boolean visiable) {
		mBom.setVisiableBom(idBom, visiable);
	}

	public void removeBom(int idBom) {
		mBom.removeBom(idBom);
	}

	public void setPositionBom(int x, int y, int idBom) {
		mBom.setPositionBom(x, y, idBom);
	}

	public void setVisiableThunderBall(int idThunder, boolean visiable) {
		mThunder.setVisiableThunderBall(idThunder, visiable);
	}

	public void removeThunderBall(int idThunder) {
		mThunder.removeThunderBall(idThunder);
	}

	public void setPositionThunderBall(int x, int y, int idThunder) {
		mThunder.setPositionThunderBall(x, y, idThunder);
	}

	// ==============================================================================
	/**
	 * Thao tác với ma trận:
	 * 
	 * Tìm ra các vị trí thỏa mãn điều kiện ăn được
	 */
	public void Buoc1MT() {
		if (ValueControl.isCompletedLevel)
			return;
		// Khi bắt đầu tính toán thì không cho người chơi di chuyển các viên kim
		// cương
		ValueControl.isTouchJewel = false;

		// List lưu lại vị trí thảo mãn điều kiện ăn
		ArrayList<Point> list_Buoc1MT = new ArrayList<Point>();

		for (int i = 0; i < MyConfig.SUM_ROW_MATRIX; i++) {
			for (int j = 0; j < MyConfig.SUM_COLUMN_MATRIX; j++) {
				// Vị trí này thỏa mãn điều kiện (viên kim cương này ăn được)
				if (checkRemove(i, j, Matrix2D.MT) != -1) {
					list_Buoc1MT.add(new Point(i, j));
				}
			}
		}

		// Tồn tại trường hợp ăn được
		if (list_Buoc1MT.size() > 0) {
			combos++;
			// Chuyển sang bước 2
			Buoc2MT(list_Buoc1MT);
		}
		// Nếu không có trường hợp nào ăn được ta đặt combos = 0
		// và cho phép người chơi tiếp tục di chuyển viên kim cương
		else {
			MyLog.println("Không còn quân để ăn");
			combos = 0;
			ValueControl.isTouchJewel = true;
			// Kiểm tra xem số điểm đã đủ chưa
			checkLevelCompleted();
		}
	}

	public void sortChildren() {
		try {
			mScene.sortChildren();
		} catch (Exception e) {
		}
	}

	// ==============================================================================
	/**
	 * Gán những vị trí thỏa mãn điều kiện thành giá trị -1
	 * 
	 * list_Buoc1MT: Danh sách vị trí những viên kim cương ăn được
	 */
	public void Buoc2MT(ArrayList<Point> list_Buoc1MT) {
		if (ValueControl.isCompletedLevel)
			return;

		ArrayList<Point> list_point = new ArrayList<Point>();

		// Kiểm tra xem nếu có bom thì tìm các vị trí lân cận nó
		for (int i = 0; i < list_Buoc1MT.size(); i++) {
			Point p = list_Buoc1MT.get(i);
			hasBom(p, list_point);
		}
		// Kiểm tra xem nếu có thunder thì tìm ra các vị trí cần xóa
		for (int i = 0; i < list_point.size(); i++) {
			Point p = list_point.get(i);
			hasThunder(p, list_point);
		}

		MyLog.println("-------Ma trận sau bước Buoc2MT--------");
		MyLog.println("-------Gán các giá trị thỏa mãn điều kiện = -1 --------");
		Matrix2D.showMatrix2D(Matrix2D.MT);
		MyLog.println("-----------------------");

		// Sau khi có toàn bộ danh sách các vị trí cần xóa và
		// ma trận cũng được gán giá trị vùng bị xóa
		Buoc2Map(list_point);

		MyLog.println("------------Buoc2Map-----------");
		showMap();
	}

	// ==============================================================================
	/**
	 * Nếu trong nhưng jewel cần xóa đi mà có bom thì tìm ra các vị trí lân cận
	 * cần xóa
	 * 
	 * @param p
	 *            : vị trí của viên kim cương
	 * @param list_point
	 *            : Danh sách chứa các viên kim cương cần loại bỏ (được ăn)
	 */
	public void hasBom(Point p, ArrayList<Point> list_point) {
		synchronized (map_Jewel) {
			// Lấy viên kim cương tại vị trí P
			Jewel mJewel = map_Jewel.get(p);
			// Tránh trường hợp đối tượng null
			if (mJewel == null)
				return;
			// Xác định vị trí hàng và cột
			int I = mJewel.getI();
			int J = mJewel.getJ();

			// Nếu list_point chưa chứa vị trí p thì ta thêm vị trí p vào
			if (!list_point.contains(p))
				list_point.add(new Point(I, J));
			// Gán giá trị trên ma trận = -1 (đã ăn)
			Matrix2D.MT[I][J] = -1;

			// Trường hợp có bom
			if (mJewel.getIsBom() == true) {
				mBom.addFire(mJewel.getpX(), mJewel.getpY());

				checkIJ(I - 1, J - 1, list_point);
				checkIJ(I - 1, J, list_point);
				checkIJ(I - 1, J + 1, list_point);

				checkIJ(I, J - 1, list_point);
				checkIJ(I, J + 1, list_point);

				checkIJ(I + 1, J - 1, list_point);
				checkIJ(I + 1, J, list_point);
				checkIJ(I + 1, J + 1, list_point);
			}
		}
	}

	// ==============================================================================
	public void hasThunder(Point p, ArrayList<Point> list_point) {
		synchronized (map_Jewel) {
			Jewel mJewel = map_Jewel.get(p);

			int I = mJewel.getI();
			int J = mJewel.getJ();

			// Có thunder
			if (mJewel.getIsThunder() == true) {

				int check = checkRemove(I, J, Matrix2D.MT);

				if (check == 2) {
					mThunder.addThunder(mJewel.getpX(), mJewel.getpY(), false);
					checkThunder2(I, J, list_point);
				}
				// Ăn theo cột
				else if (check == 1) {
					mThunder.addThunder(mJewel.getpX(), mJewel.getpY(), true);
					checkThunder1(I, J, list_point);
				}
				// Ăn theo cả hàng lẫn cột
				else if (check == 3) {
					mThunder.addThunder(mJewel.getpX(), mJewel.getpY(), true);
					mThunder.addThunder(mJewel.getpX(), mJewel.getpY(), false);

					checkThunder1(I, J, list_point);
					checkThunder2(I, J, list_point);
				}
			}
		}
	}

	public void checkThunder1(int I, int J, ArrayList<Point> list_point) {
		for (int i = 0; i < MyConfig.SUM_ROW_MATRIX; i++) {
			if (!list_point.contains(new Point(i, J))) {
				list_point.add(new Point(i, J));
				Matrix2D.MT[i][J] = -1;
			}
		}
	}

	public void checkThunder2(int I, int J, ArrayList<Point> list_point) {
		for (int j = 0; j < MyConfig.SUM_COLUMN_MATRIX; j++) {
			if (!list_point.contains(new Point(I, j))) {
				list_point.add(new Point(I, j));
				Matrix2D.MT[I][j] = -1;
			}
		}
	}

	// ==============================================================================
	/**
	 * Nếu vị trí i, j có bom thì tìm ra các điểm lân cận cần xóa
	 * 
	 * @param i
	 * @param j
	 * @param list_point
	 * @return
	 */
	public void checkIJ(int i, int j, ArrayList<Point> list_point) {
		if (checkIJ(i, j)) {
			Point p = new Point(i, j);
			if (!list_point.contains(p)) {
				Matrix2D.MT[i][j] = -1;
				list_point.add(p);
				hasBom(new Point(i, j), list_point);
				// hasThunder(new Point(i, j), list_point);
			}
		}
	}

	// ==============================================================================
	/**
	 * Kiểm tra xem vị trí i,j có nằm trong ma trận hay không
	 * 
	 * @param i
	 * @param j
	 * @return
	 */
	public boolean checkIJ(int i, int j) {
		try {
			@SuppressWarnings("unused")
			int tmp = Matrix2D.MT[i][j];
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	// ==============================================================================
	/**
	 * Xác định và di chuyển xuống dưới
	 */
	public void Buoc3MT() {
		if (ValueControl.isCompletedLevel)
			return;

		ArrayList<Point> list_SumMoveY = new ArrayList<Point>();
		HashMap<Point, Integer> map_value = new HashMap<Point, Integer>();

		for (int i = 0; i < MyConfig.SUM_ROW_MATRIX; i++) {
			for (int j = 0; j < MyConfig.SUM_COLUMN_MATRIX; j++) {

				if (Matrix2D.MT[i][j] != -1) {
					int dem = checkMaxMoveY(i, j);
					if (dem > 0) {
						Point key = new Point(i, j);
						list_SumMoveY.add(key);
						map_value.put(key, dem);
					}
				}
			}
		}

		for (int i = list_SumMoveY.size() - 1; i > -1; i--) {
			Point p = list_SumMoveY.get(i);
			int h = map_value.get(p);

			// Lưu lại giá trị cũ của ma trận
			final int gt_old = Matrix2D.MT[p.x][p.y];
			// Gán vị trị phải di chuyển thành -1
			Matrix2D.MT[p.x][p.y] = -1;

			// Xác định vị trí mới
			int i_new = p.x + h;
			int j_new = p.y;

			Matrix2D.MT[i_new][j_new] = gt_old;
		}

		MyLog.println("-------Ma trận sau bước Buoc3MT--------");
		MyLog.println("-------Di chuyển toàn bộ xuống dưới--------");
		Matrix2D.showMatrix2D(Matrix2D.MT);
		MyLog.println("-----------------------");

		// Di chuyển jewel
		count_thread_end_buoc3map = 0;
		total_thread_buoc3map = list_SumMoveY.size();
		if (total_thread_buoc3map > 0)
			Buoc3Map(list_SumMoveY, map_value);
		else
			Buoc4MT();

		MyLog.println("------------Buoc3Map-----------");
		showMap();
		// GC();
	}

	// ==============================================================================
	int count_thread_end_buoc3map = 0;
	int total_thread_buoc3map = 0;

	/**
	 * Chờ cho các jewel rơi xuống hết thì mới kích hoạt Buoc4MT
	 */
	public void ActiveBuoc4MT() {
		if (ValueControl.isCompletedLevel)
			return;
		Buoc4MT();
	}

	// ==============================================================================
	/**
	 * Thêm các giá trị hiện tại đang là -1
	 */
	public void Buoc4MT() {
		if (ValueControl.isCompletedLevel)
			return;

		ArrayList<DataItemJewel> list_Data = new ArrayList<DataItemJewel>();

		for (int i = 0; i < MyConfig.SUM_ROW_MATRIX; i++) {
			for (int j = 0; j < MyConfig.SUM_COLUMN_MATRIX; j++) {
				// Vị trí này thỏa mãn điều kiện
				if (Matrix2D.MT[i][j] == -1) {
					int index = Util.getRandomIndex(0,
							Constant.MAX_ITEM_JEWEL - 1);
					Matrix2D.MT[i][j] = index;
					Point p = Matrix2D.getPxPy(i, j);
					list_Data.add(new DataItemJewel(index, p.x, p.y, i, j, mBom
							.isHaveBom(), mThunder.isHaveThundeBall()));
				}
			}
		}

		if (list_Data.size() > 0) {
			addJewelToMap(list_Data, false);
			Buoc1MT();
		} else {
			Buoc1MT();
		}

		MyLog.println("-------Ma trận sau bước Buoc4MT--------");
		MyLog.println("-------Thêm các giá trị vào các vị trí đang có giá trị là -1--------");
		Matrix2D.showMatrix2D(Matrix2D.MT);
		MyLog.println("-----------------------");

		MyLog.println("------------Buoc4Map-----------");
		showMap();
	}

	// =========================================================================
	/**
	 * Ẩn các Jewel, remove jewel khỏi map
	 */
	public void Buoc2Map(ArrayList<Point> list_Buoc1MT) {
		synchronized (map_Jewel) {
			if (ValueControl.isCompletedLevel)
				return;
			try {
				ArrayList<Jewel> list_hide = new ArrayList<Jewel>();
				for (int i = 0; i < list_Buoc1MT.size(); i++) {
					try {
						Point p = list_Buoc1MT.get(i);
						Jewel mJewel = map_Jewel.get(p);

						if (mJewel != null) {
							list_hide.add(mJewel);
							mCoin.add(mJewel.getpX(), mJewel.getpY());
						}
						map_Jewel.remove(p);
					} catch (Exception e) {
					}
				}
				if (Menu.mSound != null)
					Menu.mSound.playCombo(combos);
				ThreadHide.RunThreadHide(list_hide, this);
			} catch (Exception e) {
				MyLog.println("error = " + e.toString());
			}
			// GC();
		}
	}

	// ==============================================================================
	/**
	 * Di chuyển các Jewel xuống vị trí mới
	 */
	public void Buoc3Map(ArrayList<Point> list_SumMoveY,
			HashMap<Point, Integer> map_value) {
		synchronized (map_Jewel) {
			for (int i = list_SumMoveY.size() - 1; i > -1; i--) {
				try {
					Point p = list_SumMoveY.get(i);
					int h = map_value.get(p);

					Jewel mJewel = map_Jewel.get(p);
					if (mJewel == null)
						continue;

					map_Jewel.remove(p);

					int y_new = mJewel.getpY() + (h * MyConfig.HEIGHT_SQUARE);
					int i_new = mJewel.getI() + h;
					int j_new = mJewel.getJ();

					mJewel.setIJ(i_new, j_new);
					map_Jewel.put(new Point(i_new, j_new), mJewel);

					// Khi gọi đến viên kim cương cuối cùng thì sẽ yêu cầu
					// active sang bước 4
					if (i == 0)
						mJewel.moveYJewels(y_new, true);
					else
						mJewel.moveYJewels(y_new, false);

					// ThreadMoveY.RunThreadMoveY(mJewel, y_new, this);
				} catch (Exception e) {
					MyLog.println("error = " + e.toString());
				}
			}
		}
	}

	// ==============================================================================
	/**
	 * reset map
	 */
	public void resetMap() {
		synchronized (map_Jewel) {
			for (int i = 0; i < MyConfig.SUM_ROW_MATRIX; i++) {
				for (int j = 0; j < MyConfig.SUM_COLUMN_MATRIX; j++) {
					Point key = new Point(i, j);
					Jewel mJewel = map_Jewel.get(key);
					if (mJewel != null) {
						mJewel.onDestroy();
					}
					map_Jewel.remove(key);
				}
			}
			map_Jewel.clear();
		}
	}

	// ==============================================================================
	/**
	 * Rọn rác
	 */
	public void GC() {
		try {
			System.gc();
			Thread.sleep(20);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	// ==============================================================================
	public void checkLevelCompleted() {
		if (ValueControl.TypeGame == ValueControl.TimeAttach
				|| ValueControl.TypeGame == ValueControl.Classic) {
			int total_coin = Level.coinLevel[Level.levelCurrent - 1];
			if (ValueControl.TypeGame == ValueControl.Classic)
				total_coin = Level.numberCoinLevelClassic[Level.levelCurrentClassic - 1];

			if (mCoin.getCoin() >= total_coin) {
				ValueControl.isCompletedLevel = true;
				ValueControl.isTouchJewel = false;

				if (ValueControl.TypeGame == ValueControl.TimeAttach) {
					// Dừng thanh trạng thái thời gian
					mProgressBar.setStop(true);
					// Lưu lại thời gian đạt được
					saveTimeOfLevel(Level.levelCurrent, mProgressBar.getTime());

					// Hoàn thành level tắng số level đã được mở khóa
					if (Level.levelCurrent + 1 > Level.levelUnlock) {
						mySharedPreferences
								.updateLevelUnlock(Level.levelCurrent + 1);
					}
				}
				// Hiện thị dialog hoàn thành level sau 1.5s
				mHandler_showDialogCompleted.sendEmptyMessage(0);
			}
		} else if (ValueControl.TypeGame == ValueControl.NewMode) {
			if (Level.squareCurrent >= Level.totalSquare) {
				ValueControl.isCompletedLevel = true;
				ValueControl.isTouchJewel = false;
				mHandler_showDialogCompleted.sendEmptyMessage(0);
			}
		}
	}

	// ==============================================================================
	/**
	 * Hiện thị dialog hoàn thành 1 level
	 */
	Handler mHandler_showDialogCompleted = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);

			Handler mHandler = new Handler();
			mHandler.postDelayed(new Runnable() {

				@Override
				public void run() {
					DialogCompleted mDialogCompleted = new DialogCompleted(
							MainGame.this);
					mDialogCompleted.show();
				}
			}, 1500);
		}
	};

	// ==============================================================================
	/**
	 * Lưu lại thời gian của level vừa chơi xong
	 * 
	 * @param level
	 * @param time
	 */
	public void saveTimeOfLevel(int level, int time) {
		// Lưu lại time
		Database mDatabase = new Database(this);
		mDatabase.openDatabase();
		mDatabase.insertTime(level, time);
		mDatabase.closeDatabase();
	}

	// ==============================================================================
	public void timeOut() {
		ValueControl.isTouchJewel = false;
		ValueControl.isCompletedLevel = false;
		mHandler_showDialogTimeOut.sendEmptyMessage(0);
	}

	// ==============================================================================
	final Handler mHandler_showDialogTimeOut = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			DialogGameOver mDialogGameOver = new DialogGameOver(MainGame.this);
			mDialogGameOver.show();
		}
	};

	// =========================================================================
	/**
	 * Kiểm tra xem tại vị trí I, J ở bên dưới có bao nhiêu vị trí có giá trị -1
	 * 
	 * @param I
	 * @param J
	 * @return
	 */
	public int checkMaxMoveY(int I, int J) {
		int dem = 0;
		for (int i = I + 1; i < MyConfig.SUM_ROW_MATRIX; i++) {
			if (Matrix2D.MT[i][J] == -1) {
				dem++;
			}
		}
		return dem;
	}

	// ==============================================================================
	/**
	 * Kiểm tra 1 vị trí xem có thỏa mãn điều kiện không. Nếu thỏa mãn thì lưu
	 * lại
	 * 
	 * @return return 1: ăn theo cột return 2: ăn theo hàng return 3: ăn theo cả
	 *         2 hướng
	 * 
	 * @param I
	 * @param J
	 * @param mt_tmp
	 * @return
	 */
	public int checkRemove(int I, int J, int[][] mt_tmp) {

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

		// Ăn cả ngang lẫn dọc
		if (count_colum > 1 && count_row > 1)
			return 3;
		// ăn hàng
		if (count_colum > 1)
			return 2;
		// ăn cột
		if (count_row > 1)
			return 1;

		// Không ăn
		return -1;
	}

	// ==============================================================================
	/**
	 * Hiện thị ma trận lấy theo map
	 */
	public void showMap() {
		synchronized (map_Jewel) {
			MyLog.println("=============================");
			MyLog.println("Ma trận theo map");
			for (int i = 0; i < MyConfig.SUM_ROW_MATRIX; i++) {
				for (int j = 0; j < MyConfig.SUM_COLUMN_MATRIX; j++) {
					Jewel mJewel = map_Jewel.get(new Point(i, j));

					if (mJewel == null) {
						MyLog.print(" nu");
						continue;
					}

					if (mJewel.ID_resource == -1)
						MyLog.print(" " + mJewel.ID_resource);
					else
						MyLog.print("  " + mJewel.ID_resource);
				}
				MyLog.println("");
			}

			for (int i = 0; i < MyConfig.SUM_ROW_MATRIX; i++) {
				for (int j = 0; j < MyConfig.SUM_COLUMN_MATRIX; j++) {
					Jewel mJewel = map_Jewel.get(new Point(i, j));

					if (mJewel == null) {
						continue;
					}

					if (mJewel.getI() != i || mJewel.getJ() != j) {
						MyLog.println("lỗi i = " + i + " j = " + j);
						MyLog.println("mJewel.getI() = " + mJewel.getI()
								+ " mJewel.getJ() = " + mJewel.getJ());
					}
				}
				MyLog.println("");
			}
			MyLog.println("=============================");
		}
	}

	// =============================================================
	// ============================GET _ SET========================
	public HashMap<Point, Jewel> getMap_Jewel() {
		return map_Jewel;
	}

	public void setMap_Jewel(HashMap<Point, Jewel> map_Jewel) {
		synchronized (map_Jewel) {
			this.map_Jewel = map_Jewel;
		}
	}

	public Star getmStar() {
		return mStar;
	}

	// ==============================================================================
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// if (keyCode == KeyEvent.KEYCODE_BACK && isLoadReset &&
		// ValueControl.isTouchJewel) {
		mButtonPauseGame.showDialogPause();
		// } else if (keyCode == KeyEvent.KEYCODE_MENU &&
		// ValueControl.isTouchJewel ) {
		// mButtonPauseGame.showDialogPause();
		// }
		return true;
	}

	// ==============================================================================
	@Override
	protected void onDestroy() {
		super.onDestroy();
		mProgressBar.setStop(true);
	}

	// ==============================================================================
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

	}
}