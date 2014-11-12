package com.siliconorchard.myowncandy.adapter;

import com.siliconorchard.myowncandy.R;

import android.content.Context;
import android.view.View;

public class ListViewAdapter {
	public Context mContext;
	public View view_time_attach, view_classic, view_new;
	public int size = 3;

	public ListViewAdapter(Context mContext) {
		this.mContext = mContext;
		view_time_attach = View.inflate(this.mContext, R.layout.time_attach,
				null);
		view_classic = View.inflate(this.mContext, R.layout.classic, null);
		view_new = View.inflate(this.mContext, R.layout.news, null);
	}

	public View getViewPosition(int position) {
		switch (position) {
		case 0:
			return view_time_attach;
		case 1:
			return view_classic;
		case 2:
			return view_new;
		default:
			return view_time_attach;
		}
	}

	public int getSize() {
		return size;
	}
}