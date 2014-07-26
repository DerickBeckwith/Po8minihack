package com.teampo8.po8minihack;

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

public class MainActivity extends Activity {

	private final String TAG = "MainActivity";

	// detects swipes
	float x1, x2;
	float y1, y2;

	// fragments
	FragmentTransaction transaction = null;
	PlaceHolderFragment mainFragment = new PlaceHolderFragment();
	PlaceHolderFragmentRight rightFragment = new PlaceHolderFragmentRight();
	PlaceHolderFragmentLeft leftFragment = new PlaceHolderFragmentLeft();

	final int FRAGMENT_LEFT = 0;
	final int FRAGMENT_MAIN = 1;
	final int FRAGMENT_RIGHT = 2;
	int position = FRAGMENT_MAIN; // position of current fragment

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// set main fragment
		transaction = getFragmentManager().beginTransaction();
		transaction.replace(R.id.container, mainFragment);
		transaction.commit();
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
			case MotionEvent.ACTION_DOWN:
			{
				x1 = touchEvent.getX();
				y1 = touchEvent.getY();
				break;
			}
			case MotionEvent.ACTION_UP:
			{
				x2 = touchEvent.getX();
				y2 = touchEvent.getY();
				
				// if left to right sweep
				if (x1 < x2) {
					position++;
					if (position > 2)
						position = 2;
					
					if (position == FRAGMENT_MAIN) {
						transaction = getFragmentManager().beginTransaction();
						transaction.replace(R.id.container, mainFragment);
						transaction.addToBackStack(null);
						transaction.commit();
					} else if (position == FRAGMENT_RIGHT) {
						transaction = getFragmentManager().beginTransaction();
						transaction.replace(R.id.container, rightFragment);
						transaction.addToBackStack(null);
						transaction.commit();
					}
				}
				
				// if right to left sweep
				if (x1 > x2) {
					position--;
					if (position < 0)
						position = 0;
					
					if (position == FRAGMENT_MAIN) {
						transaction = getFragmentManager().beginTransaction();
						transaction.replace(R.id.container, mainFragment);
						transaction.addToBackStack(null);
						transaction.commit();
					} else if (position == FRAGMENT_LEFT) {
						transaction = getFragmentManager().beginTransaction();
						transaction.replace(R.id.container, leftFragment);
						transaction.addToBackStack(null);
						transaction.commit();
					}
				}
				break;
			}
		}
		return false;
	}

	private static class PlaceHolderFragment extends Fragment {

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);

			return rootView;
		}
	}

	private static class PlaceHolderFragmentRight extends Fragment {

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_right,
					container, false);

			return rootView;
		}
	}

	private static class PlaceHolderFragmentLeft extends Fragment {

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragmant_left, container,
					false);

			return rootView;
		}
	}
}
