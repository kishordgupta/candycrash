package com.siliconorchard.myowncandy.adapter;


import java.util.HashMap;

import com.siliconorchard.myowncandy.Level;
import com.siliconorchard.myowncandy.MainGame;
import com.siliconorchard.myowncandy.Menu;
import com.siliconorchard.myowncandy.MySharedPreferences;
import com.siliconorchard.myowncandy.R;
import com.siliconorchard.myowncandy.control.ValueControl;
import com.siliconorchard.myowncandy.database.Database;



import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class StarAdapter extends BaseAdapter {
	private Context mContext;
	HashMap<Integer, Integer> map_time;
	MySharedPreferences mMySharedPreferences;

	public StarAdapter(Context c) {
		mContext = c;
		mMySharedPreferences = new MySharedPreferences(mContext);
		Database mDatabase = new Database(mContext);
		mDatabase.openDatabase();
		map_time = mDatabase.getAllTime();
		mDatabase.closeDatabase();
	}

	public int getCount() {
		return Level.maxLevel;
	}

	public Object getItem(int position) {
		return null;
	}

	public long getItemId(int position) {
		return 0;
	}

	// create a new ImageView for each item referenced by the Adapter
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = null;
		int time = 0;
		if (map_time.containsKey(position + 1)) {
			time = map_time.get(position + 1);
		}
		// if (convertView == null) { // if it's not recycled, initialize some
		// attributes
		v = View.inflate(mContext, R.layout.item_grid_star, null);

		LinearLayout unlock = (LinearLayout) v.findViewById(R.id.unlock);
		ImageView lock = (ImageView) v.findViewById(R.id.lock);

		if (time == 0 && Level.levelUnlock < position + 1) {
			unlock.setVisibility(View.GONE);
		} else {
			lock.setVisibility(View.GONE);

			TextView textView = (TextView) v.findViewById(R.id.textView);
			textView.setText("" + (position + 1));

			ImageView star1 = (ImageView) v.findViewById(R.id.star1);
			ImageView star2 = (ImageView) v.findViewById(R.id.star2);
			ImageView star3 = (ImageView) v.findViewById(R.id.star3);

			int alpha = 90;
			if (time == 0) {
				star1.setAlpha(alpha);
				star2.setAlpha(alpha);
				star3.setAlpha(alpha);
			} else {
				int star = getStarByLevel(position + 1, time);
				if (star < 1)
					star1.setAlpha(alpha);

				if (star < 2)
					star2.setAlpha(alpha);

				if (star < 3)
					star3.setAlpha(alpha);
			}
			final int positionA = position + 1;
			unlock.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (Menu.mSound != null)
						Menu.mSound.playHarp();
					mMySharedPreferences.updateLevelCurrent(positionA);
					ValueControl.TypeGame = ValueControl.TimeAttach;
					mMySharedPreferences.updateTypeGame(ValueControl.TypeGame);
					nextMainGame();
				}
			});
		}

		// } else {
		// v = (View) convertView;
		// }
		return v;
	}

	public int getStarByLevel(int level, int time) {
		int star = 0;
		int timeLevel = Level.timeLevel[level - 1] / 5;
		if (time <= timeLevel * 2) {
			return 3;// 3 sao
		} else if (time <= timeLevel * 3) {
			return 2;// 2 sao
		} else if (time <= timeLevel * 4) {
			return 1;// 2 sao
		}
		return star;
	}

	public void nextMainGame() {
		Intent mIntent = new Intent(mContext, MainGame.class);
		mContext.startActivity(mIntent);
	}

}