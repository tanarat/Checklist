package org.silk.checklist.activity;

import java.util.List;

import org.silk.checklist.R;
import org.silk.checklist.dao.Dao;
import org.silk.checklist.db.Provider;
import org.silk.checklist.model.Auditor;
import org.silk.checklist.model.Auditor;
import org.silk.checklist.model.Bpartner;
import org.silk.checklist.model.Checklist;
import org.silk.checklist.model.Sheet;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

public class SheetDetailActivity extends BaseDetailActivity<Sheet> {
	// private Context context;
	private Sheet sheet;
	private Dao<Sheet> sheetDao;
	private Dao<Bpartner> bpDao;
	private Dao<Checklist> checklistDao;
	
	private EditText edtName;
	private Spinner spBpartner;
	private Spinner spChecklist;
//	private EditText edtAddress;
//	private EditText edtPhone;
	
	private Button btnSave;
	private Button btnDiscard;
	private Button btnDelete;
//	private boolean inserting = false;
	
	private boolean detailChanged = false;
	
	private Uri contentUri = Provider.SHEET_CONTENT_URI;

	public SheetDetailActivity() {
		// this.context = context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		sheetDao = new Dao<Sheet>(Sheet.class, this,
				contentUri);
		bpDao = new Dao<Bpartner>(Bpartner.class, this, Provider.BPARTNER_CONTENT_URI);
		checklistDao = new Dao<Checklist>(Checklist.class, this, Provider.CHECKLIST_CONTENT_URI);
		
		setContentView(R.layout.sheet_details);

		edtName = (EditText) findViewById(R.id.edtName);
		spBpartner = (Spinner) findViewById(R.id.spBpartner);
		ArrayAdapter<Bpartner> bpAdapter = new ArrayAdapter<Bpartner>(this, android.R.layout.simple_list_item_1, bpDao.get(null, null));
		spBpartner.setAdapter(bpAdapter);
		ArrayAdapter<Checklist> chAdapter = new ArrayAdapter<Checklist>(this, android.R.layout.simple_list_item_1, checklistDao.get(null, null));
		spChecklist = (Spinner) findViewById(R.id.spChecklist);
		spChecklist.setAdapter(chAdapter);
		
//		edtAddress = (EditText) findViewById(R.id.edtAddress);
//		edtPhone = (EditText) findViewById(R.id.edtPhone);
		
		
		
		btnSave = (Button) findViewById(R.id.btnSave);
		btnDiscard = (Button) findViewById(R.id.btnDiscard);
		btnDelete = (Button) findViewById(R.id.btnDelete);

		Long itemId = getIntent().getLongExtra("itemId", -1);
		if (itemId != null && itemId.longValue() != -1) {
			sheet = sheetDao.getById(itemId.intValue());
		}

		if (sheet != null) {
//			inserting = false;
			setMode(MODE_EDIT);
			edtName.setText(sheet.getSheetName());
//			edtAddress.setText(auditor.getAddress());
//			edtPhone.setText(auditor.getPhone());
			btnDelete.setEnabled(true);
		} else {
			sheet = new Sheet();
			setMode(MODE_INSERT);
			btnDelete.setEnabled(false);
		}

		edtName.addTextChangedListener(new GenericTextWatcher(
				edtName));
//		edtAddress.addTextChangedListener(new GenericTextWatcher(edtAddress));
//		edtPhone.addTextChangedListener(new GenericTextWatcher(edtPhone));
	}

	public void buttonClick(View view) {
		switch (view.getId()) {
		case R.id.btnSave:
			save();
			break;
		case R.id.btnDelete:
			delete();
			break;
		case R.id.btnDiscard:
			finish();
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
	}

	public Sheet getSheet() {
		return sheet;
	}

	public void setSheet(Sheet sheet) {
		this.sheet = sheet;
	}

	@Override
	public void finish() {
		Intent retIntent = new Intent();
		retIntent.putExtra("detailChanged", detailChanged);
		setResult(RESULT_OK, retIntent);
		super.finish();
	}

	private boolean validate() {
		sheet.setSheetName(edtName.getText().toString());
//		auditor.setAddress(edtAddress.getText().toString());
//		auditor.setPhone(edtPhone.getText().toString());

		if (sheet.getSheetName() == null
				|| sheet.getSheetName().trim().length() == 0)
			return false;
		else
			return true;
	}

	private void delete() {
		if (isInsertMode())
			return;
		new AlertDialog.Builder(this)
				.setTitle(R.string.dialog_title_confirm_delete)
				.setMessage(R.string.dialog_message_confirm_delete)
				.setPositiveButton(R.string.button_ok, new OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub

						sheetDao.delete(sheet);

						Toast.makeText(SheetDetailActivity.this, "deleted",
								Toast.LENGTH_SHORT).show();
						detailChanged = true;
						finish();

					}
				})
				.setNegativeButton(R.string.button_cancle,
						new OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub

							}
						}).show();
	}

	private void save() {

		if (validate()) {
			new AlertDialog.Builder(this)
					.setTitle(R.string.dialog_title_confirm_save)
					.setMessage(R.string.dialog_message_confirm_save)
					.setPositiveButton(R.string.button_ok,
							new OnClickListener() {

								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub

									if (isEditMode()) {
										sheetDao.update(sheet);
									} else {
										sheetDao.insert(sheet);
										System.out.println("insert new sheet ..");
									}
									Toast.makeText(SheetDetailActivity.this,
											"saved", Toast.LENGTH_SHORT).show();
									detailChanged = true;
									finish();

								}
							})
					.setNegativeButton(R.string.button_cancle,
							new OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub

								}
							}).show();
		} else {
			new AlertDialog.Builder(this)
					.setTitle(R.string.dialog_title_error)
					.setMessage(
							R.string.dialog_message_require_company_name_error)
					.setPositiveButton(R.string.button_ok,
							new OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									edtName.requestFocus();
								}
							}).show();
		}

	}

	private class GenericTextWatcher implements TextWatcher {
		private View view;

		private GenericTextWatcher(View view) {
			this.view = view;
		}

		public void beforeTextChanged(CharSequence charSequence, int i, int i1,
				int i2) {
		}

		public void onTextChanged(CharSequence charSequence, int i, int i1,
				int i2) {
		}

		public void afterTextChanged(Editable editable) {
			if (edtName.getText().toString().trim().length() != 0)
				btnSave.setEnabled(true);
			else
				btnSave.setEnabled(false);

		}

	}

	private class BpartnerAdapter extends BaseAdapter{
		Context context;
		List<Bpartner> bpList;
		BpartnerAdapter(Context context, List<Bpartner> bpList){
			this.context = context;
			this.bpList = bpList;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return bpList.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return bpList.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return ((Bpartner)getItem(position)).getId();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
}
