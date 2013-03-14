package org.silk.checklist.activity.bpartner;

import org.silk.checklist.R;
import org.silk.checklist.activity.BaseDetailActivity;
import org.silk.checklist.dao.Dao;
import org.silk.checklist.db.Provider;
import org.silk.checklist.model.Bpartner;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
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

public class BpartnerDetailActivity extends BaseDetailActivity<Bpartner> {
	// private Context context;
	private Bpartner mBpartner;
	private EditText edtCompanyName;
	private EditText edtAddress;
	private EditText edtPhone;
	private Button btnSave;
	private Button btnDiscard;
	private Button btnDelete;
	private boolean inserting = false;
	private Dao<Bpartner> bpDao;
	private boolean detailChanged = false;

	public BpartnerDetailActivity() {
		// this.context = context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		bpDao = new Dao<Bpartner>(Bpartner.class, this,
				Provider.BPARTNER_CONTENT_URI);
		setContentView(R.layout.bpartner_details);

		edtCompanyName = (EditText) findViewById(R.id.edtCompanyName);
		edtAddress = (EditText) findViewById(R.id.edtAddress);
		edtPhone = (EditText) findViewById(R.id.edtPhone);
		btnSave = (Button) findViewById(R.id.btnSave);
		btnDiscard = (Button) findViewById(R.id.btnDiscard);
		btnDelete = (Button) findViewById(R.id.btnDelete);

		Long bpartnerId = getIntent().getLongExtra("itemId", -1);
		if (bpartnerId != null && bpartnerId.longValue() != -1) {
			mBpartner = bpDao.getById(bpartnerId.intValue());
		}

		if (mBpartner != null) {
			inserting = false;
			edtCompanyName.setText(mBpartner.getCompanyName());
			edtAddress.setText(mBpartner.getAddress());
			edtPhone.setText(mBpartner.getPhone());
			btnDelete.setEnabled(true);
		} else {
			mBpartner = new Bpartner();
			inserting = true;
			btnDelete.setEnabled(false);
		}

		edtCompanyName.addTextChangedListener(new GenericTextWatcher(
				edtCompanyName));
		edtAddress.addTextChangedListener(new GenericTextWatcher(edtAddress));
		edtPhone.addTextChangedListener(new GenericTextWatcher(edtPhone));
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

	public Bpartner getBpartner() {
		return mBpartner;
	}

	public void setBpartner(Bpartner mBpartner) {
		this.mBpartner = mBpartner;
	}

	@Override
	public void finish() {
		Intent retIntent = new Intent();
		retIntent.putExtra("detailChanged", detailChanged);
		setResult(RESULT_OK, retIntent);
		super.finish();
	}

	private boolean validate() {
		mBpartner.setCompanyName(edtCompanyName.getText().toString());
		mBpartner.setAddress(edtAddress.getText().toString());
		mBpartner.setPhone(edtPhone.getText().toString());

		if (mBpartner.getCompanyName() == null
				|| mBpartner.getCompanyName().trim().length() == 0)
			return false;
		else
			return true;
	}

	private void delete() {
		if (inserting)
			return;
		new AlertDialog.Builder(this)
				.setTitle(R.string.dialog_title_confirm_delete)
				.setMessage(R.string.dialog_message_confirm_delete)
				.setPositiveButton(R.string.button_ok, new OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub

						bpDao.delete(mBpartner);

						Toast.makeText(BpartnerDetailActivity.this, "deleted",
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

									if (!inserting) {
										bpDao.update(mBpartner);
									} else {
										bpDao.insert(mBpartner);
									}
									Toast.makeText(BpartnerDetailActivity.this,
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
									edtCompanyName.requestFocus();
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
			if (edtCompanyName.getText().toString().trim().length() != 0)
				btnSave.setEnabled(true);
			else
				btnSave.setEnabled(false);

		}

	}

}
