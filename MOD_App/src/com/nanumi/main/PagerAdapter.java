package com.nanumi.main;

import com.nanumi.sub.SubFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class PagerAdapter extends FragmentPagerAdapter {
	private static int NUM_ITEMS = 4;

	public PagerAdapter(FragmentManager fragmentManager) {
		super(fragmentManager);
	}

	@Override
	public int getCount() {
		return NUM_ITEMS;
	}

	/** 
	 * 0~4까지 각각 레이아웃 
	 */
	@Override
	public Fragment getItem(int position) {
		switch (position) {
		case 0:
			return SubFragment.newInstance();
		case 1:
			return SubFragment.newInstance();
		case 2:
			return SubFragment.newInstance();
		case 3:
			return SubFragment.newInstance();
		default:
			return null;
		}
	}

	// Returns the page title for the top indicator
	@Override
	public CharSequence getPageTitle(int position) {
		return "Page " + position;
	}
}
