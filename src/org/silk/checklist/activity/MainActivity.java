package org.silk.checklist.activity;

import org.silk.checklist.ChecklistApp;
import org.silk.checklist.LoadSampleDataTask;
import org.silk.checklist.R;
import org.silk.checklist.R.id;
import org.silk.checklist.R.layout;
import org.silk.checklist.R.menu;
import org.silk.checklist.R.string;
import org.silk.checklist.model.ModelBase;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_load_sample_data:
			loadSampleData();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	private void loadSampleData() {
		final ProgressDialog mProgressDialog = new ProgressDialog(MainActivity.this);
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
