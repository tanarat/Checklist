package org.silk.checklist.activity.checklist;

import java.util.ArrayList;
import java.util.List;

import org.silk.checklist.R;
import org.silk.checklist.activity.BaseDetailActivity;
import org.silk.checklist.activity.ItemListAdapter;
import org.silk.checklist.dao.Dao;
import org.silk.checklist.dao.ItemDao;
import org.silk.checklist.db.ItemListCursorAdapter;
import org.silk.checklist.db.ModelBaseListCursorAdapter;
import org.silk.checklist.db.Provider;
import org.silk.checklist.db.table.ChecklistItemTable;
import org.silk.checklist.db.table.ItemTable;
import org.silk.checklist.model.Auditor;
import org.silk.checklist.model.Auditor;
import org.silk.checklist.model.Checklist;
import org.silk.checklist.model.ChecklistItem;
import org.silk.checklist.model.Item;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


// Detail of checklist item is a list of items
//public class ChecklistDetailActivity extends BaseDetailActivity<Checklist> implements LoaderCallbacks<Cursor> {
public class ChecklistDetailActivity extends BaseDetailActivity<Checklist> {
//	
	// private Context context;
	private Checklist checklist;
	private Dao<Checklist> daoChecklist;
	private Dao<ChecklistItem> daoChecklistItem;
//	private Dao<Item> daoItem;
	private ItemDao daoItem;
	
//	private ModelBaseListCursorAdapter<Item> itemListAdapter;
	private ItemListAdapter itemListAdapter;
	private ListView lvItems;
	private Button btnFilter;
	private Uri contentUri = Provider.CHECKLIST_CONTENT_URI;
	private int cursorId = 10;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.checklist_details);
		daoChecklist = new Dao<Checklist>(Checklist.class, this, contentUri);
		daoChecklistItem = new Dao<ChecklistItem>(ChecklistItem.class, this, Provider.CHECKLIST_ITEM_CONTENT_URI);
//		daoItem = new Dao<Item>(Item.class, this, Provider.ITEM_CONTENT_URI);
		daoItem = new ItemDao(this);
		
//		itemListAdapter = new ModelBaseListCursorAdapter<Item>(this, Item.class);
//		itemListAdapter.setRowViewRes(R.layout.item_row_1);
		

		lvItems = (ListView) findViewById(R.id.lvItems);

		

		Long checklistId = getIntent().getLongExtra("itemId", -1);
		if (checklistId != null && checklistId.longValue() != -1) {
			checklist = daoChecklist.getById(checklistId.intValue());
		}
//		List<Integer> itemIdList = new ArrayList<Integer>();
		if (checklist != null) {
			StringBuilder sb = new StringBuilder();
			sb.append(ItemTable.ItemColumns._ID + " in ( ")  ;
			List<ChecklistItem> checklistItems = daoChecklistItem.get(ChecklistItemTable.ChecklistItemColumns.CHECKLIST_ID + " = " + checklistId, null);
			for (int i = 0; i < checklistItems.size(); i++) {
				ChecklistItem checklistItem = checklistItems.get(i);
//				itemIdList.add(checklistItem.getItemId());
				sb.append(String.valueOf(checklistItem.getItemId()));
				if(i < (checklistItems.size() - 1) ){
					sb.append(", ");
				}
			}
			sb.append(" )");
			
//			List<Item> itemList = daoItem.get(sb.toString(), null);
			List<Item> itemList = daoItem.get(sb.toString(), null);
			itemListAdapter = new ItemListAdapter(this, itemList);
			lvItems.setAdapter(itemListAdapter);
		}
//		getSupportLoaderManager().initLoader(cursorId, null, this);
	}
	
	
	
//
//	public void buttonClick(View view) {
//		switch (view.getId()) {
//		case R.id.btnSave:
//			save();
//			break;
//		case R.id.btnDelete:
//			delete();
//			break;
//		case R.id.btnDiscard:
//			finish();
//		}
//	}
//
//	@Override
//	protected void onSaveInstanceState(Bundle outState) {
//		// TODO Auto-generated method stub
//		super.onSaveInstanceState(outState);
//	}
//
//	public Auditor getAuditor() {
//		return auditor;
//	}
//
//	public void setAuditor(Auditor mAuditor) {
//		this.auditor = mAuditor;
//	}
//
//	@Override
//	public void finish() {
//		Intent retIntent = new Intent();
//		retIntent.putExtra("detailChanged", detailChanged);
//		setResult(RESULT_OK, retIntent);
//		super.finish();
//	}
//
//	private boolean validate() {
//		auditor.setName(edtName.getText().toString());
////		auditor.setAddress(edtAddress.getText().toString());
////		auditor.setPhone(edtPhone.getText().toString());
//
//		if (auditor.getName() == null
//				|| auditor.getName().trim().length() == 0)
//			return false;
//		else
//			return true;
//	}
//
//	private void delete() {
//		if (isInsertMode())
//			return;
//		new AlertDialog.Builder(this)
//				.setTitle(R.string.dialog_title_confirm_delete)
//				.setMessage(R.string.dialog_message_confirm_delete)
//				.setPositiveButton(R.string.button_ok, new OnClickListener() {
//
//					public void onClick(DialogInterface dialog, int which) {
//						// TODO Auto-generated method stub
//
//						dao.delete(auditor);
//
//						Toast.makeText(ChecklistDetailActivity.this, "deleted",
//								Toast.LENGTH_SHORT).show();
//						detailChanged = true;
//						finish();
//
//					}
//				})
//				.setNegativeButton(R.string.button_cancle,
//						new OnClickListener() {
//
//							@Override
//							public void onClick(DialogInterface dialog,
//									int which) {
//								// TODO Auto-generated method stub
//
//							}
//						}).show();
//	}
//
//	private void save() {
//
//		if (validate()) {
//			new AlertDialog.Builder(this)
//					.setTitle(R.string.dialog_title_confirm_save)
//					.setMessage(R.string.dialog_message_confirm_save)
//					.setPositiveButton(R.string.button_ok,
//							new OnClickListener() {
//
//								public void onClick(DialogInterface dialog,
//										int which) {
//									// TODO Auto-generated method stub
//
//									if (isEditMode()) {
//										dao.update(auditor);
//									} else {
//										dao.insert(auditor);
//									}
//									Toast.makeText(ChecklistDetailActivity.this,
//											"saved", Toast.LENGTH_SHORT).show();
//									detailChanged = true;
//									finish();
//
//								}
//							})
//					.setNegativeButton(R.string.button_cancle,
//							new OnClickListener() {
//
//								@Override
//								public void onClick(DialogInterface dialog,
//										int which) {
//									// TODO Auto-generated method stub
//
//								}
//							}).show();
//		} else {
//			new AlertDialog.Builder(this)
//					.setTitle(R.string.dialog_title_error)
//					.setMessage(
//							R.string.dialog_message_require_company_name_error)
//					.setPositiveButton(R.string.button_ok,
//							new OnClickListener() {
//
//								@Override
//								public void onClick(DialogInterface dialog,
//										int which) {
//									// TODO Auto-generated method stub
//									edtName.requestFocus();
//								}
//							}).show();
//		}
//
//	}
//
//	private class GenericTextWatcher implements TextWatcher {
//		private View view;
//
//		private GenericTextWatcher(View view) {
//			this.view = view;
//		}
//
//		public void beforeTextChanged(CharSequence charSequence, int i, int i1,
//				int i2) {
//		}
//
//		public void onTextChanged(CharSequence charSequence, int i, int i1,
//				int i2) {
//		}
//
//		public void afterTextChanged(Editable editable) {
//			if (edtName.getText().toString().trim().length() != 0)
//				btnSave.setEnabled(true);
//			else
//				btnSave.setEnabled(false);
//
//		}
//
//	}

//	@Override
//	public Loader<Cursor> onCreateLoader(int id, Bundle bundle) {
//		return new CursorLoader(this, contentUri,
//				null, null, null, null);
//	}
//
//	@Override
//	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
//		itemListAdapter.swapCursor(cursor);
//	}
//
//	@Override
//	public void onLoaderReset(Loader<Cursor> arg0) {
//		itemListAdapter.swapCursor(null);
//	}

}
