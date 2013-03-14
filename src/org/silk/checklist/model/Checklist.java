package org.silk.checklist.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

import org.silk.checklist.ChecklistApp;
import org.silk.checklist.db.table.ChecklistTable;

import android.provider.BaseColumns;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class Checklist extends ModelBase {
	private Context context;
	private int id;
	private String checklistName;
	private Date updated;

	public Checklist() {
		super();
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public void fromCursor(Cursor cursor, Context context) {
		this.id = cursor.getInt(cursor.getColumnIndex(BaseColumns._ID));
		this.checklistName = cursor
				.getString(cursor
						.getColumnIndex(ChecklistTable.ChecklistColumns.CHECKLIST_NAME));
		this.updated = new Date(cursor.getLong(cursor
				.getColumnIndex(ChecklistTable.ChecklistColumns.UPDATED)));
		this.context = context;
	}

	@Override
	public ContentValues toContentValues() {
		ContentValues values = new ContentValues();
		values.put(ChecklistTable.ChecklistColumns.CHECKLIST_NAME,
				this.checklistName);
		values.put(ChecklistTable.ChecklistColumns.UPDATED,
				this.updated.getTime());
		return values;
	}

	public static Checklist newInstance(Cursor cursor, Context context) {
		Checklist Checklist = new Checklist();
		Checklist.fromCursor(cursor, context);
		return Checklist;
	}

	@Override
	public void fromCSV(String csvLine) {
		StringTokenizer token = new StringTokenizer(csvLine,
				ChecklistApp.CSV_DELIMITER);
		setId(Integer.parseInt(token.nextToken()));
		setChecklistName(token.nextToken());
		setUpdated(new Date());
	}

	public String getChecklistName() {
		return checklistName;
	}

	public void setChecklistName(String checklistName) {
		this.checklistName = checklistName;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return getId() + " " + getChecklistName();
	}

	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return checklistName;
	}

//	@Override
//	public String getDescription1() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public String getDescription2() {
//		// TODO Auto-generated method stub
//		return null;
//	}

}