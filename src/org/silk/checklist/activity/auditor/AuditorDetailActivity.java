package org.silk.checklist.activity.auditor;

import org.silk.checklist.R;
import org.silk.checklist.activity.BaseDetailActivity;
import org.silk.checklist.dao.Dao;
import org.silk.checklist.db.Provider;
import org.silk.checklist.model.Auditor;
import org.silk.checklist.model.Auditor;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AuditorDetailActivity extends BaseDetailActivity<Auditor> {
	// private Context context;
	private Auditor auditor;
	private Dao<Auditor> dao;
	
	private EditText edtName;
//	private EditText edtAddress;
//	private EditText edtPhone;
	
	private Button btnSave;
	private Button btnDiscard;
	private Button btnDelete;
//	private boolean inserting = false;
	
	private boolean detailChanged = false;
	
	private Uri contentUri = Provider.AUDITOR_CONTENT_URI;

	public AuditorDetailActivity() {
		// this.context = context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		dao = new Dao<Auditor>(Auditor.class, this,
				contentUri);
		setContentView(R.layout.auditor_details);

		edtName = (EditText) findViewById(R.id.edtName);
//		edtAddress = (EditText) findViewById(R.id.edtAddress);
//		edtPhone = (EditText) findViewById(R.id.edtPhone);
		btnSave = (Button) findViewById(R.id.btnSave);
		btnDiscard = (Button) findViewById(R.id.btnDiscard);
		btnDelete = (Button) findViewById(R.id.btnDelete);

		Long itemId = getIntent().getLongExtra("itemId", -1);
		if (itemId != null && itemId.longValue() != -1) {
			auditor = dao.getById(itemId.intValue());
		}

		if (auditor != null) {
//			inserting = false;
			setMode(MODE_EDIT);
			edtName.setText(auditor.getName());
//			edtAddress.setText(auditor.getAddress());
//			edtPhone.setText(auditor.getPhone());
			btnDelete.setEnabled(true);
		} else {
			auditor = new Auditor();
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

	public Auditor getAuditor() {
		return auditor;
	}

	public void setAuditor(Auditor mAuditor) {
		this.auditor = mAuditor;
	}

	@Override
	public void finish() {
		Intent retIntent = new Intent();
		retIntent.putExtra("detailChanged", detailChanged);
		setResult(RESULT_OK, retIntent);
		super.finish();
	}

	private boolean validate() {
		auditor.setName(edtName.getText().toString());
//		auditor.setAddress(edtAddress.getText().toString());
//		auditor.setPhone(edtPhone.getText().toString());

		if (auditor.getName() == null
				|| auditor.getName().trim().length() == 0)
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

						dao.delete(auditor);

						Toast.makeText(AuditorDetailActivity.this, "deleted",
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
										dao.update(auditor);
									} else {
										dao.insert(auditor);
									}
									Toast.makeText(AuditorDetailActivity.this,
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

}
