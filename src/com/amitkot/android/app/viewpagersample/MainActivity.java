package com.amitkot.android.app.viewpagersample;

import com.anicca.adnroid.app.pagedviews.R;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

/**
 * An activity demonstrating the ability to hold a ViewPager with two draggable
 * views overlapping one another.
 * 
 */
public class MainActivity extends FragmentActivity implements SwitchingActivity {
	private static final int MARGIN_OVERLAP = -100;

	private static final int FIRST_ITEM = 0;
	private static final int SECOND_ITEM = 1;

	MyAdapter mAdapter;
	ViewPager mPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_pager);

		mAdapter = new MyAdapter(getSupportFragmentManager());

		mPager = (ViewPager) findViewById(R.id.pager);
		mPager.setAdapter(mAdapter);
		mPager.setPageMargin(MARGIN_OVERLAP);
	}

	@Override
	public void switchTo(int num) {
		mPager.setCurrentItem(num);
	}

	public class MyAdapter extends FragmentPagerAdapter {

		public MyAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public int getCount() {
			return 2;
		}

		@Override
		public Fragment getItem(int position) {
			ColorFragment f = null;
			switch (position) {
			case FIRST_ITEM:
				f = ColorFragment.newInstance(FIRST_ITEM, Color.BLUE);
				break;
			case SECOND_ITEM:
				f = ColorFragment.newInstance(SECOND_ITEM, Color.RED);
				break;
			}
			return f;
		}
	}

	public static class ColorFragment extends Fragment {
		private int mColor;
		private SwitchingActivity mSwitchingActivity;
		private int mNum;

		public ColorFragment() {
		}

		static ColorFragment newInstance(int num, int colorCode) {
			ColorFragment f = new ColorFragment();

			Bundle args = new Bundle();
			args.putInt("num", num);
			args.putInt("color", colorCode);
			f.setArguments(args);

			return f;
		}

		@Override
		public void onAttach(Activity activity) {
			super.onAttach(activity);

			try {
				mSwitchingActivity = (SwitchingActivity) activity;
			} catch (ClassCastException e) {
				throw new ClassCastException(activity.toString()
						+ "Must implement SwitchingActivity");
			}
		}

		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			mNum = getArguments() != null ? getArguments().getInt("num") : 0;
			mColor = getArguments() != null ? getArguments().getInt("color")
					: Color.WHITE;
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View v = new FrameLayout(getActivity());
			LayoutParams lp = new LinearLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
			v.setLayoutParams(lp);
			v.setBackgroundColor(mColor);
			v.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					mSwitchingActivity.switchTo(mNum);
				}
			});
			return v;
		}
	}

}