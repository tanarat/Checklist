package org.silk.checklist.activity;

import org.silk.checklist.R;
import org.silk.checklist.activity.bpartner.BpartnerDetailActivity;
import org.silk.checklist.dao.Dao;
import org.silk.checklist.db.ItemCheckedListener;
import org.silk.checklist.db.ModelBaseListCursorAdapter;
import org.silk.checklist.db.Provider;
import org.silk.checklist.model.Bpartner;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

public class BpartnerFragment extends Fragment implements OnClickListener,
		OnItemClickListener, LoaderCallbacks<Cursor>, ItemCheckedListener {
	private String tag = "BpartnerFragment";
	private ModelBaseListCursorAdapter<Bpartner> listAdapter;
	private ListView lvBpartner;

	private Dao<Bpartner> bpDao;

	// menu button
	private Button btnNew, btnDelete;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		//for delete operation
		bpDao = new Dao<Bpartner>(Bpartner.class, getActivity(), Provider.BPARTNER_CONTENT_URI);

		View view = inflater.inflate(R.layout.content_list, null);
		btnNew = (Button) view.findViewById(R.id.btnNew);

		btnNew.setOnClickListener(this);

		btnDelete = (Button) view.findViewById(R.id.btnDelete);

		btnDelete.setOnClickListener(this);

		lvBpartner = (ListView) view.findViewById(R.id.list);

		listAdapter = new ModelBaseListCursorAdapter<Bpartner>(getActivity(),Bpartner.class);
		listAdapter.setCheckListener(this);
		if(savedInstanceState != null){
			int[] checkedItemIds = savedInstanceState.getIntArray("checkedItemIds");
			listAdapter.setCheckedItemIds(checkedItemIds);
		}

		lvBpartner.setAdapter(listAdapter);
		lvBpartner.setOnItemClickListener(this);
		getActivity().getSupportLoaderManager().initLoader(0, null, this);
		return view;
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
		viewBpartnerDetail(-1);
	}

	private void viewBpartnerDetail(long id) {
		Intent intent = new Intent(getActivity(), BpartnerDetailActivity.class);
		intent.putExtra("bpartnerId", id);
		startActivityForResult(intent, BPARTNER_DETAIL);
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

								int[] checkedBpIds = listAdapter.getCheckedItemIds();
								for (int i = 0; i < checkedBpIds.length; i++) {
									Bpartner bp = bpDao.getById(checkedBpIds[i]);
									Log.i(tag, "Delete Bpartner id : " + bp.getId());
									bpDao.delete(bp);
								}
								listAdapter.clearCheckedItem();
								
							}
						}).setNegativeButton(R.string.button_cancle, null)
				.show();
	}


	@Override
	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		outState.putIntArray("checkedItemIds", listAdapter.getCheckedItemIds());

	}

	@Override
	public void onItemClick(AdapterView<?> adapter, View view, int position,
			long id) {
		viewBpartnerDetail(id);
	}

	public static final int BPARTNER_DETAIL = 100;

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == BPARTNER_DETAIL && resultCode == Activity.RESULT_OK) {
			if (data.getBooleanExtra("detailChanged", false)) {
//				getActivity().getSupportLoaderManager().restartLoader(0,null,this);
//				listAdapter.notifyDataSetChanged();
				System.out.println("......... detail changed.......");
			}
		}
	}
	
	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle bundle) {
		return new CursorLoader(getActivity(), Provider.BPARTNER_CONTENT_URI,
				null, null, null, null);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		listAdapter.swapCursor(cursor);
		Log.i(tag, "finish load cursor : " +  cursor.getCount());
	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		listAdapter.swapCursor(null);
	}

	@Override
	public void onCheckItemChecked() {
		// TODO Auto-generated method stub
		if(listAdapter.getCheckedItemCount() > 0){
			btnDelete.setEnabled(true);
		}else{
			btnDelete.setEnabled(false);
		}
		
	}

}
