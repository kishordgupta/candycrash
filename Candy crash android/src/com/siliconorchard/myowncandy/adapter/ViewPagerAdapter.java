package com.siliconorchard.myowncandy.adapter;
 
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
 
public class ViewPagerAdapter extends PagerAdapter {
    Context mContext;
    ListViewAdapter mListViewAdapter;
   
    public ViewPagerAdapter(Context mContext, ListViewAdapter mListViewAdapter) {
        this.mContext = mContext;
        this.mListViewAdapter = mListViewAdapter;
    }
 
    @Override
    public int getCount() {
        return mListViewAdapter.getSize();
    }
 
    @Override
    public Object instantiateItem(View container, int position) {
        View v = mListViewAdapter.getViewPosition(position);
        ((ViewPager)container).addView(v);
        return v;
    }
 
    @Override
    public void destroyItem(View collection, int position, Object view) {
        ((ViewPager)collection).removeView((View) view);
    }
 
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
   
    @Override
    public void finishUpdate(ViewGroup container) {
        super.finishUpdate(container);
    }
}