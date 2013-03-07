package org.silk.checklist.activity;

import org.silk.checklist.ChecklistApp;
import org.silk.checklist.LoadSampleDataTask;
import org.silk.checklist.R;
import org.silk.checklist.model.ModelBase;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import android.widget.TextView;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;


import com.slidingmenu.lib.SlidingMenu;
import com.slidingmenu.lib.app.SlidingFragmentActivity;

public class MainActivity extends SlidingFragmentActivity 
//implements
//		LeftMenuFragment.OnLeftMenuItemSelectedListener,
//		PlaceFragment.OnItemSelectedListener 
		{
	String tag = "MainActivity";
	ListMenuFragment menuFrag;

	SlidingMenu sm;

	FragmentTransaction t;

	public MainActivity() {
		// super(R.string.app_name);
		// TODO Auto-generated constructor stub
		Log.i(tag, "constructor");

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(tag, "onCreate");
		// set the Content View
		setContentView(R.layout.activity_main);
		// set the Behind View
		setBehindContentView(R.layout.menu_frame);
		initSlidingMenu();
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);


	}

	private void initSlidingMenu() {
		FragmentTransaction t = this.getSupportFragmentManager()
				.beginTransaction();
		menuFrag = new ListMenuFragment();
		t.replace(R.id.menu_frame, menuFrag);
		t.commit();
		// customize the SlidingMenu
		sm = getSlidingMenu();
		sm.setShadowWidthRes(R.dimen.shadow_width);
		sm.setShadowDrawable(R.drawable.shadow);
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		sm.setFadeDegree(0.35f);
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
	}

	 @Override
	 public boolean onCreateOptionsMenu(Menu menu) {
		 com.actionbarsherlock.view.MenuInflater inflater = getSupportMenuInflater();
		   inflater.inflate(R.menu.main, (com.actionbarsherlock.view.Menu) menu);
		   return super.onCreateOptionsMenu(menu);
	 }

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			toggle();
			return true;
		case R.id.action_load_sample_data:
			loadSampleData();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	

//	@Override
//	public void onLeftMenuItemSelected(long id) {
//		// TODO Auto-generated method stub
//		toggle();
//		if (id == 0) {
//			currentFragment = contentFrags[0];
//		}
//		if (id == 1) {
//			currentFragment = contentFrags[1];
//		} else if (id == 2) {
//			currentFragment = contentFrags[2];
//		}
//
//		updateContentFragment();
//		
//	}

//	@Override
//	public void onItemSelected(long id) {
//		// TODO Auto-generated method stub
//
//		currentFragment = new PlaceDetailFragment();
//		updateContentFragment();
//	}
//
//	private void updateContentFragment() {
//		t = this.getSupportFragmentManager().beginTransaction();
//		t.replace(R.id.contentFragment, currentFragment);
//		t.addToBackStack("a");
//		t.commit();
//	}
	private void loadSampleData() {
		final ProgressDialog mProgressDialog = new ProgressDialog(
				MainActivity.this);
		mProgressDialog.setMessage(getString(R.string.message_please_wait));
		mProgressDialog.setTitle(getString(R.string.message_processing));
		// mProgressDialog.setCancelable(true);
		mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		mProgressDialog.setMax(ChecklistApp.MAX_PROGRESS_VALUE);
		// mProgressDialog.setOnCancelListener(this);

		final LoadSampleDataTask<ModelBase> mLoadSampleTask = new LoadSampleDataTask<ModelBase>(
				MainActivity.this) {
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
