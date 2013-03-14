package org.silk.checklist.activity;

import org.silk.checklist.R;
import org.silk.checklist.dao.Dao;
import org.silk.checklist.db.ItemCheckedListener;
import org.silk.checklist.db.ModelBaseListCursorAdapter;
import org.silk.checklist.db.Provider;
import org.silk.checklist.model.Auditor;
import org.silk.checklist.model.Bpartner;
import org.silk.checklist.model.ModelBase;
import org.silk.checklist.model.Sheet;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public abstract class ModelBaseListActivity<T extends ModelBase> extends FragmentActivity implements OnClickListener,
		OnItemClickListener, LoaderCallbacks<Cursor>, ItemCheckedListener {
	private String tag = "ModelBaseList";
	private ModelBaseListCursorAdapter<T> mCursorAdapter;
	private ListView mListView;

	private Uri contentUri;
	private Dao<T> mDao;
	private Class<? extends BaseDetailActivity<T>> detailActivityClass;
	private int cursorId;
	
	private int titleRes;

	// menu button
	private Button btnNew, btnDelete;
	
	private Class<T> mClazz;

	public void setContentUri(Uri contentUri){
		this.contentUri = contentUri;
	}
	public void  setDetailActivityClass(Class<? extends BaseDetailActivity<T>> detailActivityClass){
		this.detailActivityClass = detailActivityClass;
	}
	public void setCursorId(int cursorId){
		this.cursorId = cursorId;
	}
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		contentUri = getContentUri();
		mClazz = getModelClass();
	}
	protected abstract Uri getContentUri();
	protected abstract Class<T> getModelClass();
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		
		mDao = new Dao<T>(mClazz, this, contentUri);
		View view = inflater.inflate(R.layout.content_list, null);
		TextView tvTitle = (TextView) view.findViewById(R.id.tvTitle);
		tvTitle.setText(getTitleRes());
		btnNew = (Button) view.findViewById(R.id.btnNew);

		btnNew.setOnClickListener(this);

		btnDelete = (Button) view.findViewById(R.id.btnDelete);

		btnDelete.setOnClickListener(this);

		mListView = (ListView) view.findViewById(R.id.list);

		mCursorAdapter = new ModelBaseListCursorAdapter<T>(this, mClazz);
		mCursorAdapter.setCheckListener(this);
		if(savedInstanceState != null){
			int[] checkedItemIds = savedInstanceState.getIntArray("checkedItemIds");
			mCursorAdapter.setCheckedItemIds(checkedItemIds);
		}

		mListView.setAdapter(mCursorAdapter);
		mListView.setOnItemClickListener(this);
		getSupportLoaderManager().initLoader(cursorId, null, this);
		registerForContextMenu(mListView);
		
		return view;
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
		Intent intent = new Intent(this, detailActivityClass);
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

								int[] checkedIds = mCursorAdapter.getCheckedItemIds();
								for (int i = 0; i < checkedIds.length; i++) {
									T t = mDao.getById(checkedIds[i]);
//									Log.i(tag, "Delete Bpartner id : " + bp.getId());
									mDao.delete(t);
								}
								mCursorAdapter.clearCheckedItem();
								
							}
						}).setNegativeButton(R.string.button_cancle, null)
				.show();
	}


	@Override
	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		outState.putIntArray("checkedItemIds", mCursorAdapter.getCheckedItemIds());

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
		mCursorAdapter.swapCursor(cursor);
		Log.i(tag, "finish load cursor : " +  cursor.getCount());
	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		mCursorAdapter.swapCursor(null);
	}

	@Override
	public void onCheckItemChecked() {
		// TODO Auto-generated method stub
		if(mCursorAdapter.getCheckedItemCount() > 0){
			btnDelete.setEnabled(true);
		}else{
			btnDelete.setEnabled(false);
		}
		
	}
	
	public int getTitleRes() {
		return titleRes;
	}
	public void setTitleRes(int titleRes) {
		this.titleRes = titleRes;
	}

}
