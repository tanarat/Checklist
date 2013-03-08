package org.silk.checklist.activity;

import org.silk.checklist.R;
import org.silk.checklist.db.Dao;
import org.silk.checklist.db.Provider;
import org.silk.checklist.model.Bpartner;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
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

public class BpartnerDetailFragment extends Fragment {
	// private Context context;
	private Bpartner mBpartner;
	private EditText edtCompanyName;
	private EditText edtAddress;
	private EditText edtPhone;
	private Button btnSave;

	private Dao<Bpartner> bpDao;

	public BpartnerDetailFragment() {
		// this.context = context;
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		System.out.println("........details............");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		bpDao = new Dao<Bpartner>(Bpartner.class, getActivity(),
				Provider.BPARTNER_CONTENT_URI);
		View view = inflater.inflate(R.layout.bpartner_details, container,
				false);
		edtCompanyName = (EditText) view.findViewById(R.id.edtCompanyName);
		edtAddress = (EditText) view.findViewById(R.id.edtAddress);
		edtPhone = (EditText) view.findViewById(R.id.edtPhone);
		btnSave = (Button) view.findViewById(R.id.btnSave);
		btnSave.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				save();
			}
		});

		if (mBpartner != null) {
			edtCompanyName.setText(mBpartner.getCompanyName());
			edtAddress.setText(mBpartner.getAddress());
			edtPhone.setText(mBpartner.getPhone());
		}

		edtCompanyName.addTextChangedListener(new GenericTextWatcher(
				edtCompanyName));
		edtAddress.addTextChangedListener(new GenericTextWatcher(edtAddress));
		edtPhone.addTextChangedListener(new GenericTextWatcher(edtPhone));
		return view;
	}

	public Bpartner getBpartner() {
		return mBpartner;
	}

	public void setBpartner(Bpartner mBpartner) {
		this.mBpartner = mBpartner;
	}

	private void save() {

		new AlertDialog.Builder(getActivity())
				.setTitle(R.string.dialog_title_confirm_save)
				.setMessage(R.string.dialog_message_confirm_save)
				.setPositiveButton(R.string.button_ok, new OnClickListener() {
					
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						mBpartner.setCompanyName(edtCompanyName.getText().toString());
						mBpartner.setAddress(edtAddress.getText().toString());
						mBpartner.setPhone(edtPhone.getText().toString());
						bpDao.update(mBpartner);
						Toast.makeText(getActivity(), "saved", Toast.LENGTH_SHORT).show();
						getFragmentManager().popBackStack();
					}
				})
				.setNegativeButton(R.string.button_cancle, new OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						getFragmentManager().popBackStack();
					}
				})
				.show();
		
		
		
	}

	private void enableSave() {
		// TODO Auto-generated method stub
		if (!btnSave.isEnabled()) {
			btnSave.setEnabled(true);
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
			enableSave();
		}

	}

}
