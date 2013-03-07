package org.silk.checklist;


import org.silk.checklist.model.Auditor;
import org.silk.checklist.model.Bpartner;
import org.silk.checklist.model.Checklist;
import org.silk.checklist.model.ChecklistItem;
import org.silk.checklist.model.Item;
import org.silk.checklist.model.Group;

import android.app.Application;


public class ChecklistApp extends Application{
	public String tag = "ChecklistApp";
	public static String CSV_DELIMITER = ",";
	public static final int MIN_PROGRESS_VALUE = 0;
	public static final int MAX_PROGRESS_VALUE = 100;
	

	
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		
	}
//
//	public void loadSampleData(){
////		importFromFile(IMPORT_GROUP,"Group.csv");
//		List<Item> items = loadFromCsvFile("Item.csv", Item.class);
//		System.out.println("item size: " + items.size());
//
//		List<Checklist> checklists = loadFromCsvFile("Checklist.csv", Checklist.class);
//		System.out.println("checklist size: " + checklists.size());
//
//		List<ChecklistItem> checklistItems = loadFromCsvFile("Checklist_Item.csv", ChecklistItem.class);
//		System.out.println("checklist-item size : " + checklistItems.size());
//		
//		List<Bpartner> bpartners = loadFromCsvFile("Bpartner.csv", Bpartner.class);
//		System.out.println("bpartner size : " + bpartners.size());
//		
//		List<Auditor> auditors =  loadFromCsvFile("Auditor.csv", Auditor.class);
//		System.out.println("auditor size : " + auditors.size());
//	}
//	
//	//<modifier> <type-declaration> <return-type> <method-name>
//	public <T extends ModelBase> List<T> loadFromCsvFile(String fileName, Class<T> clazz){
//		InputStream ins;
//		List<T> list = new ArrayList<T>();
//		try {
//			ins = getResources().getAssets().open(fileName);
//			String[] lines = ChecklistUtils.readToLines(ins);
//			for (int i = 0; i < lines.length; i++) {
//				T instance = clazz.newInstance();
//				instance.fromCSV(lines[i]);
//				list.add(instance);
//			}
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (InstantiationException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IllegalAccessException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return list;
//	}
	
}
