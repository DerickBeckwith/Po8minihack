package com.teampo8.po8minihack;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

public class MainActivity extends Activity implements TabListener {
	// detects swipes
	float x1, x2, y1, y2;

	// fragments
	FragmentTransaction transaction = null;
	PlaceHolderFragment mainFragment = new PlaceHolderFragment(
			R.layout.fragment_main);
	PlaceHolderFragment rightFragment = new PlaceHolderFragment(
			R.layout.fragment_right);
	PlaceHolderFragment leftFragment = new PlaceHolderFragment(
			R.layout.fragmant_left);

	final int FRAGMENT_LEFT = 0;
	final int FRAGMENT_MAIN = 1;
	final int FRAGMENT_RIGHT = 2;
	int position = FRAGMENT_LEFT; // position of current fragment

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// setup tabs in the action bar
		ActionBar bar = getActionBar();
		bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		for (int i = 0; i < 3; i++) {
			Tab tab = bar.newTab();
			tab.setText("Tab " + (i + 1));
			tab.setTabListener(this);
			bar.addTab(tab);
		}

		// set main fragment
		transaction = getFragmentManager().beginTransaction();
		transaction.replace(R.id.container, leftFragment);
		transaction.commit();
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction transaction) {
		if (transaction.isEmpty()) {
			if (tab.getPosition() == FRAGMENT_LEFT) {
				position = FRAGMENT_LEFT;
				SwapFragment(leftFragment, transaction);
			} else if (tab.getPosition() == FRAGMENT_MAIN) {
				position = FRAGMENT_MAIN;
				SwapFragment(mainFragment, transaction);
			} else if (tab.getPosition() == FRAGMENT_RIGHT) {
				position = FRAGMENT_RIGHT;
				SwapFragment(rightFragment, transaction);
			}
		}
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction transaction) {

	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction transaction) {

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public boolean onTouchEvent(MotionEvent touchEvent) {
		switch (touchEvent.getAction()) {
		// when user first touches the screen we get the x any y coordinate
		case MotionEvent.ACTION_DOWN: {
			x1 = touchEvent.getX();
			y1 = touchEvent.getY();
			break;
		}
		case MotionEvent.ACTION_UP: {
			x2 = touchEvent.getX();
			y2 = touchEvent.getY();

			// if right to left sweep
			if (x1 > x2) {
				position++;
				if (position > 2)
					position = 2;

				if (position == FRAGMENT_MAIN) {
					SwapFragment(mainFragment, transaction);
					getActionBar().setSelectedNavigationItem(position);
				} else if (position == FRAGMENT_RIGHT) {
					SwapFragment(rightFragment, transaction);
					getActionBar().setSelectedNavigationItem(position);
				}
			}

			// if left to right sweep
			if (x1 < x2) {
				position--;
				if (position < 0)
					position = 0;

				if (position == FRAGMENT_MAIN) {
					SwapFragment(mainFragment, transaction);
					getActionBar().setSelectedNavigationItem(position);
				} else if (position == FRAGMENT_LEFT) {
					SwapFragment(leftFragment, transaction);
					getActionBar().setSelectedNavigationItem(position);
				}
			}
			break;
		}
		}
		return false;
	}

	private void SwapFragment(Fragment fragmentToSwap,
			FragmentTransaction transaction) {
		transaction = getFragmentManager().beginTransaction();
		transaction.replace(R.id.container, fragmentToSwap);
		transaction.addToBackStack(null);
		transaction.commit();
	}

	private final class PlaceHolderFragment extends Fragment {

		private final int id;

		public PlaceHolderFragment(int id) {
			this.id = id;
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(this.id, container, false);

			return rootView;
		}
	}
}
