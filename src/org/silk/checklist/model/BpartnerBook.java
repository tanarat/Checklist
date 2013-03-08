package org.silk.checklist.model;

import org.silk.checklist.db.Dao;
import org.silk.checklist.db.Provider;

import android.content.Context;

public class BpartnerBook {
	private Dao<Bpartner> bpDao; 
	private Context context;
	
	
	public BpartnerBook(Context context) {
		super();
		this.context = context;
		bpDao = new Dao<Bpartner>(Bpartner.class, context, Provider.BPARTNER_CONTENT_URI);
	}

	public int count() {
		// TODO Auto-generated method stub
		return bpDao.size();
	}

	public Bpartner getBpartner(int position) {
		// TODO Auto-generated method stub
		return bpDao.get(position);
	}
	
	
}
