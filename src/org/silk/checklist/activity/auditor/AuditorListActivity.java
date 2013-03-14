package org.silk.checklist.activity.auditor;

import org.silk.checklist.ChecklistApp;
import org.silk.checklist.R;
import org.silk.checklist.dao.Dao;
import org.silk.checklist.db.ItemCheckedListener;
import org.silk.checklist.db.Provider;
import org.silk.checklist.model.Auditor;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class AuditorListActivity extends FragmentActivity implements OnClickListener,
		OnItemClickListener, LoaderCallbacks<Cursor>, ItemCheckedListener {
	private String tag = "AuditorList";

	private AuditorCursorAdapter cursorAdapter;
	private ListView mListView;
	private Uri contentUri = Provider.AUDITOR_CONTENT_URI;
	private Dao<Auditor> mDao;

	private int cursorId = ChecklistApp.CURSOR_ID_AUDITOR;
	


	// menu button
	private Button btnNew, btnDelete;
	
//	private Class<Bpartner> mClazz;


	
	@Override
	protected void onCreate(Bundle bundle) {
		// TODO Auto-generated method stub
		super.onCreate(bundle);
		mDao = new Dao<Auditor>(Auditor.class, this, contentUri);

		setContentView(R.layout.content_list);
		TextView tvTitle = (TextView) findViewById(R.id.tvTitle);
		tvTitle.setText(R.string.auditor_list_title);
		btnNew = (Button) findViewById(R.id.btnNew);

		btnNew.setOnClickListener(this);

		btnDelete = (Button) findViewById(R.id.btnDelete);

		btnDelete.setOnClickListener(this);

		mListView = (ListView) findViewById(R.id.list);


		cursorAdapter = new AuditorCursorAdapter(this);
		cursorAdapter.setCheckListener(this);
		if(bundle != null){
			int[] checkedItemIds = bundle.getIntArray("checkedItemIds");
			cursorAdapter.setCheckedItemIds(checkedItemIds);
		}

		mListView.setAdapter(cursorAdapter);
		mListView.setOnItemClickListener(this);
		getSupportLoaderManager().initLoader(cursorId, null, this);
		registerForContextMenu(mListView);
		
		
	}


	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {

		super.onCreateContextMenu(menu, v, menuInfo);
	}
	@Override
	public boolean onContextItemSelected(MenuItem item) {

		return super.onContextItemSelected(item);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnNew:
			onNewButtonClick();
			break;
		case R.id.btnDelete:
			onDeleteButtonClick();
			break;
		}
	}


	private void onNewButtonClick() {
		viewDetail(-1);
	}

	private void viewDetail(long id) {
		System.out.println("view detail .." + id);
		Intent intent = new Intent(this, AuditorDetailActivity.class);
		intent.putExtra("itemId", id);
		startActivity(intent);

	}

	



	private void onDeleteButtonClick() {
		
		new AlertDialog.Builder(this)
				.setTitle(R.string.dialog_title_confirm_delete)
				.setMessage(R.string.dialog_message_confirm_delete)
				.setPositiveButton(R.string.button_ok,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {

								int[] checkedIds = cursorAdapter.getCheckedItemIds();
								for (int i = 0; i < checkedIds.length; i++) {
									Auditor t = mDao.getById(checkedIds[i]);
//									Log.i(tag, "Delete Bpartner id : " + bp.getId());
									mDao.delete(t);
								}
								cursorAdapter.clearCheckedItem();
								
							}
						}).setNegativeButton(R.string.button_cancle, null)
				.show();
	}


	@Override
	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		outState.putIntArray("checkedItemIds", cursorAdapter.getCheckedItemIds());

	}

	@Override
	public void onItemClick(AdapterView<?> adapter, View view, int position,
			long id) {
		viewDetail(id);
	}


	
	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle bundle) {
		return new CursorLoader(this, contentUri,
				null, null, null, null);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		cursorAdapter.swapCursor(cursor);
		Log.i(tag, "finish load cursor : " +  cursor.getCount());
	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		cursorAdapter.swapCursor(null);
	}

	@Override
	public void onCheckItemChecked() {
		// TODO Auto-generated method stub
		if(cursorAdapter.getCheckedItemCount() > 0){
			btnDelete.setEnabled(true);
		}else{
			btnDelete.setEnabled(false);
		}
		
	}
	

}
