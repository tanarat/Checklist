package org.silk.checklist.activity;

import org.silk.checklist.ChecklistApp;
import org.silk.checklist.LoadSampleDataTask;
import org.silk.checklist.R;
import org.silk.checklist.R.id;
import org.silk.checklist.R.layout;
import org.silk.checklist.R.menu;
import org.silk.checklist.R.string;
import org.silk.checklist.model.ModelBase;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.ActionBar.TabListener;
import com.slidingmenu.lib.SlidingMenu;
import com.slidingmenu.lib.app.SlidingFragmentActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class CopyOfMainActivity extends SlidingFragmentActivity {
//	public MainActivity() {
//		super(R.string.left_and_right);
//		// TODO Auto-generated constructor stub
//	}

	// implements
	// LeftMenuFragment.OnLeftMenuItemSelectedListener,
	// PlaceFragment.OnItemSelectedListener {
	private SlidingMenu mSlidingMenu;
	private ListMenuFragment menuFrag = new ListMenuFragment();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getSlidingMenu().setMode(SlidingMenu.LEFT);
		getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		
//		setContentView(R.layout.content_frame);
//		getSupportFragmentManager()
//		.beginTransaction()
//		.replace(R.id.content_frame, new SampleListFragment())
//		.commit();

		getSupportFragmentManager()
		.beginTransaction()
		.replace(R.id.menu_frame, menuFrag)
		.commit();
		
		
	}

	

//	 @Override
//	 public boolean onCreateOptionsMenu(Menu menu) {
//	 // Inflate the menu; this adds items to the action bar if it is present.
//	 getMenuInflater().inflate(R.menu.main, menu);
//	 return true;
//	 }
//	
//	 @Override
//	 public boolean onOptionsItemSelected(MenuItem item) {
//	 switch (item.getItemId()) {
//	 case R.id.action_load_sample_data:
//	 loadSampleData();
//	 break;
//	 }
//	 return super.onOptionsItemSelected(item);
//	 }

	public void loadSampleData() {
		final ProgressDialog mProgressDialog = new ProgressDialog(
				CopyOfMainActivity.this);
		mProgressDialog.setMessage(getString(R.string.message_please_wait));
		mProgressDialog.setTitle(getString(R.string.message_processing));
		// mProgressDialog.setCancelable(true);
		mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		mProgressDialog.setMax(ChecklistApp.MAX_PROGRESS_VALUE);
		// mProgressDialog.setOnCancelListener(this);

		final LoadSampleDataTask<ModelBase> mLoadSampleTask = new LoadSampleDataTask<ModelBase>(
				CopyOfMainActivity.this) {
			@Override
			protected void onPostExecute(Void result) {
				mProgressDialog.dismiss();
			}

			@Override
			protected void onPreExecute() {
				mProgressDialog.setProgress(0);
				mProgressDialog.show();
			}

			@Override
			protected void onProgressUpdate(Integer... values) {
				mProgressDialog.setProgress(values[0]);
			}
		};

		new AlertDialog.Builder(this).setTitle(R.string.load_sample_data_title)
				.setMessage(R.string.load_sample_data_message)
				.setPositiveButton(R.string.button_ok, new OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {

						mLoadSampleTask.execute();
					}
				}).setNegativeButton(R.string.button_cancle, null).show();

	}

}
