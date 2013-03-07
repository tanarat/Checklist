package org.silk.checklist;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.silk.checklist.db.Dao;
import org.silk.checklist.db.Provider;
import org.silk.checklist.model.Auditor;
import org.silk.checklist.model.Bpartner;
import org.silk.checklist.model.Checklist;
import org.silk.checklist.model.ChecklistItem;
import org.silk.checklist.model.Group;
import org.silk.checklist.model.Item;
import org.silk.checklist.model.ModelBase;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

public class LoadSampleDataTask<T extends ModelBase> extends AsyncTask<Void, Integer, Void> {
	private String tag = "LoadSampleDataTask";
	private Context context;

	private Dao mDao;
	
	public static Class[] clazzes= {Item.class, Checklist.class, ChecklistItem.class, Auditor.class, Bpartner.class, Group.class};
	public static String[] fileNames = {"Item.csv", "Checklist.csv", "Checklist_Item.csv", "Auditor.csv", "Bpartner.csv", "Group.csv"};
	public static Uri[] contentUris = {Provider.ITEM_CONTENT_URI, Provider.CHECKLIST_CONTENT_URI, Provider.CHECKLIST_ITEM_CONTENT_URI,
										Provider.AUDITOR_CONTENT_URI, Provider.BPARTNER_CONTENT_URI, Provider.GROUP_CONTENT_URI};
	
	public LoadSampleDataTask(Context context) {
		super();
		this.context = context;

	}

	@Override
	protected Void doInBackground(Void... params) {
		for (int i = 0; i < fileNames.length; i++) {
			List<T> list = loadFromCsvFile(fileNames[i], clazzes[i]);
			Log.i(tag, "Load " + fileNames[i] + ", " + list.size() + " item(s)");
			publishProgress(((i+1) * (ChecklistApp.MAX_PROGRESS_VALUE/2))/fileNames.length);
			updateDB(list,i);
			publishProgress(((i+1) * (ChecklistApp.MAX_PROGRESS_VALUE/2))/fileNames.length);

		}
		return null;
	}
	private void updateDB(List<T> list, int index){
		Dao dao = new Dao(clazzes[index], context, contentUris[index]);
		dao.destroy();
		for (Iterator<T> iterator = list.iterator(); iterator.hasNext();) {
			T t = (T) iterator.next();
			dao.insert(t);
			Log.i(tag, "Insert " + t.toString() );
		}
		
	}
	private List<T> loadFromCsvFile(String fileName,
			Class<T> clazz) {
		InputStream ins;
		List<T> list = new ArrayList<T>();
		try {
			ins = context.getResources().getAssets().open(fileName);
			String[] lines = ChecklistUtils.readToLines(ins);
			for (int i = 0; i < lines.length; i++) {
				T instance = clazz.newInstance();
				instance.fromCSV(lines[i]);
				list.add(instance);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	
}
