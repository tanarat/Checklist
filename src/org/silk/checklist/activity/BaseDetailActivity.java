package org.silk.checklist.activity;

import org.silk.checklist.model.ModelBase;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;

public abstract class BaseDetailActivity<T extends ModelBase> extends FragmentActivity{

	public static final int MODE_INSERT = 0;
	public static final int MODE_EDIT = 1;
	
	private int mode;
	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}
	public boolean isInsertMode(){
		return mode == MODE_INSERT; 
	}
	public boolean isEditMode(){
		return mode == MODE_EDIT; 
	}
	/*
	
	//Generalize
	private T model;
	private Dao<T> dao;

	private Button btnSave;
	private Button btnDiscard;
	private Button btnDelete;
	
	
	
	//Specific
//	private Uri contentUri = Provider.getContentUriFor();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.base_details);
		dao = new Dao<T>(getModelClass(), this,
				getContentUri());
	
		btnSave = (Button) findViewById(R.id.btnSave);
		btnDiscard = (Button) findViewById(R.id.btnDiscard);
		btnDelete = (Button) findViewById(R.id.btnDelete);
		
		Long itemId = getIntent().getLongExtra("itemId", -1);
		if (itemId != null && itemId.longValue() != -1) {
			model = dao.getById(itemId.intValue());
		}
		if (model != null) {
			setMode(MODE_EDIT);
//			edtName.setText(auditor.getName());
//			edtAddress.setText(auditor.getAddress());
//			edtPhone.setText(auditor.getPhone());
			btnDelete.setEnabled(true);
		} else {
			Class<T> clazz = getModelClass();
			try {
				model = clazz.newInstance();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			setMode(MODE_INSERT);
			btnDelete.setEnabled(false);
		}
	}
	
	protected void buttonClick(View view) {
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

	abstract protected Uri getContentUri();
	abstract protected Class<T> getModelClass();
	abstract protected void save();
	abstract protected void delete();
*/

	
}
