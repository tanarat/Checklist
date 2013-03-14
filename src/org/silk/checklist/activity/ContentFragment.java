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

public class ContentFragment<T extends ModelBase> extends Fragment implements OnClickListener,
		OnItemClickListener, LoaderCallbacks<Cursor>, ItemCheckedListener {
	private String tag = "ContentFragment";
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
	public void setModelClass(Class<T> clazz){
		this.mClazz = clazz;
	}
	public void setContentUri(Uri contentUri){
		this.contentUri = contentUri;
	}
	public void  setDetailActivityClass(Class<? extends BaseDetailActivity<T>> detailActivityClass){
		this.detailActivityClass = detailActivityClass;
	}
	public void setCursorId(int cursorId){
		this.cursorId = cursorId;
	}
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		//for delete operation
		if(contentUri == null){
			contentUri = Provider.getContentUri(mClazz);
		}
		mDao = new Dao<T>(mClazz, getActivity(), contentUri);

		View view = inflater.inflate(R.layout.content_list, null);
		TextView tvTitle = (TextView) view.findViewById(R.id.tvTitle);
		tvTitle.setText(getTitleRes());
		btnNew = (Button) view.findViewById(R.id.btnNew);

		btnNew.setOnClickListener(this);

		btnDelete = (Button) view.findViewById(R.id.btnDelete);

		btnDelete.setOnClickListener(this);

		mListView = (ListView) view.findViewById(R.id.list);

		mCursorAdapter = new ModelBaseListCursorAdapter<T>(getActivity(), mClazz);
		mCursorAdapter.setCheckListener(this);
		if(savedInstanceState != null){
			int[] checkedItemIds = savedInstanceState.getIntArray("checkedItemIds");
			mCursorAdapter.setCheckedItemIds(checkedItemIds);
		}

		mListView.setAdapter(mCursorAdapter);
		mListView.setOnItemClickListener(this);
		getActivity().getSupportLoaderManager().initLoader(cursorId, null, this);
		registerForContextMenu(mListView);
		
		return view;
	}
	
	

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
//		if(mClazz == Sheet.class){
//			menu.add(Menu.NONE, 0, Menu.NONE, R.string.action_evaluate);
//		}
//		menu.add(Menu.NONE, MENU_EDIT, Menu.NONE, R.string.menuEdit);
		super.onCreateContextMenu(menu, v, menuInfo);
	}
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
//		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
//				.getMenuInfo();
//
//		switch (item.getItemId()) {
//		case 0:
//			evaluate(info.position);
//			break;
////		case MENU_EDIT:
////			editDot(info.position);
////			break;
//		default:
//			break;
//		}
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

	private void evaluate(int position){
		
	}
	private void onNewButtonClick() {
		viewDetail(-1);
	}

	private void viewDetail(long id) {
		System.out.println("view detail .." + id);
		Intent intent = new Intent(getActivity(), detailActivityClass);
		intent.putExtra("itemId", id);
		startActivity(intent);

	}

	



	private void onDeleteButtonClick() {
		
		new AlertDialog.Builder(getActivity())
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

//	public static final int VIEW_DETAIL = 100;
//
//	@Override
//	public void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//		if (requestCode == VIEW_DETAIL && resultCode == Activity.RESULT_OK) {
//			if (data.getBooleanExtra("detailChanged", false)) {
////				getActivity().getSupportLoaderManager().restartLoader(0,null,this);
////				listAdapter.notifyDataSetChanged();
////				System.out.println("......... detail changed.......");
//			}
//		}
//	}
	
	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle bundle) {
		return new CursorLoader(getActivity(), contentUri,
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
