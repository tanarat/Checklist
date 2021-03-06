package org.silk.checklist.activity;

import org.silk.checklist.ChecklistApp;
import org.silk.checklist.LoadSampleDataTask;
import org.silk.checklist.R;
import org.silk.checklist.activity.ListMenuFragment.OnListMenuSelectedListener;
import org.silk.checklist.activity.auditor.AuditorDetailActivity;
import org.silk.checklist.activity.bpartner.BpartnerDetailActivity;
import org.silk.checklist.activity.checklist.ChecklistDetailActivity;
import org.silk.checklist.db.Provider;
import org.silk.checklist.model.Auditor;
import org.silk.checklist.model.Bpartner;
import org.silk.checklist.model.Checklist;
import org.silk.checklist.model.ModelBase;
import org.silk.checklist.model.Sheet;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.Toast;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.slidingmenu.lib.SlidingMenu;
import com.slidingmenu.lib.app.SlidingFragmentActivity;

public class MainActivity extends SlidingFragmentActivity implements OnListMenuSelectedListener{
	String tag = "MainActivity";
	ListMenuFragment menuFrag;
	Fragment contentFrag;
	SlidingMenu sm;

	FragmentTransaction t;

//	private Fragment bpFragment;
	private ContentFragment<Sheet> sheetContentFrag;
	private ContentFragment<Checklist> checklistContentFrag;
	private ContentFragment<Bpartner> bpartnerContentFrag;
	private ContentFragment<Auditor> auditorContentFrag;
	
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
		
		if(savedInstanceState != null){
			contentFrag = getSupportFragmentManager().getFragment(savedInstanceState, "contentFragment"); 
		}else{
			contentFrag = new SampleListFragment();
		}
		getSupportFragmentManager()
		.beginTransaction()
		.replace(R.id.content_frame, contentFrag)
		.commit();
		// set the Behind View
		setBehindContentView(R.layout.menu_frame);
		initSlidingMenu();
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		menuFrag.setOnListMenuSelectedListener(this);
		
		checklistContentFrag = new ContentFragment<Checklist>();
		checklistContentFrag.setModelClass(Checklist.class);
		checklistContentFrag.setContentUri(Provider.CHECKLIST_CONTENT_URI);
		checklistContentFrag.setDetailActivityClass(ChecklistDetailActivity.class);
		checklistContentFrag.setTitleRes(R.string.checklist_list_title);
		checklistContentFrag.setCursorId(0);
		

		
		bpartnerContentFrag = new ContentFragment<Bpartner>();
		bpartnerContentFrag.setModelClass(Bpartner.class);
		bpartnerContentFrag.setContentUri(Provider.BPARTNER_CONTENT_URI);
		bpartnerContentFrag.setDetailActivityClass(BpartnerDetailActivity.class);
		bpartnerContentFrag.setTitleRes(R.string.bpartner_list_title);
		bpartnerContentFrag.setCursorId(1);
		
		
		auditorContentFrag = new ContentFragment<Auditor>();
		auditorContentFrag.setModelClass(Auditor.class);
		auditorContentFrag.setContentUri(Provider.AUDITOR_CONTENT_URI);
		auditorContentFrag.setDetailActivityClass(AuditorDetailActivity.class);
		auditorContentFrag.setTitleRes(R.string.auditor_list_title);
		bpartnerContentFrag.setCursorId(2);
		

		sheetContentFrag = new ContentFragment<Sheet>();
		sheetContentFrag.setModelClass(Sheet.class);
		sheetContentFrag.setContentUri(Provider.SHEET_CONTENT_URI);
		sheetContentFrag.setDetailActivityClass(SheetDetailActivity.class);
		sheetContentFrag.setTitleRes(R.string.sheet_list_title);
		sheetContentFrag.setCursorId(3);
	}

	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		getSupportFragmentManager().putFragment(outState, "contentFragment", contentFrag);
		System.out.println("...............main..........");
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
	
	@Override
	public void onListMenuSelected(int position) {
		// TODO Auto-generated method stub
		toggle();
		switch(position){
		case 0:
			contentFrag = new HalQChecklistMenu();
//			Toast.makeText(getApplicationContext(), "item 3", Toast.LENGTH_SHORT).show();
//			contentFrag = sheetContentFrag;
//			break;
//		case 1:
//			Toast.makeText(getApplicationContext(), "item 0", Toast.LENGTH_SHORT).show();
//			contentFrag = checklistContentFrag;
//			break;
//		case 2:
//			Toast.makeText(getApplicationContext(), "item 1", Toast.LENGTH_SHORT).show();
//			contentFrag = bpartnerContentFrag;
//			break;
//		case 3:
//			Toast.makeText(getApplicationContext(), "item 2", Toast.LENGTH_SHORT).show();
//			contentFrag = auditorContentFrag;
//			break;
		
		}
		
		changeContentFragment();

	}

	private void changeContentFragment() {
		t = this.getSupportFragmentManager().beginTransaction();
		t.replace(R.id.content_frame, contentFrag );
		t.addToBackStack("a");
		t.commit();
	}

	
}
