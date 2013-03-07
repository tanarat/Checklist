package org.silk.checklist.model;

import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

import org.silk.checklist.ChecklistApp;
import org.silk.checklist.db.table.ChecklistItemTable;

import android.provider.BaseColumns;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class ChecklistItem extends ModelBase {
	private Context context;
	private int id;
	private int checklistId;
	private int itemId;

	public ChecklistItem() {
		super();
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public void fromCursor(Cursor cursor, Context context) {
		this.id = cursor.getInt(cursor.getColumnIndex(BaseColumns._ID));
		this.checklistId = cursor
				.getInt(cursor
						.getColumnIndex(ChecklistItemTable.ChecklistItemColumns.CHECKLIST_ID));
		this.itemId = cursor
				.getInt(cursor
						.getColumnIndex(ChecklistItemTable.ChecklistItemColumns.ITEM_ID));
		this.context = context;
	}

	@Override
	public ContentValues toContentValues() {
		ContentValues values = new ContentValues();
		values.put(ChecklistItemTable.ChecklistItemColumns.CHECKLIST_ID,
				this.checklistId);
		values.put(ChecklistItemTable.ChecklistItemColumns.ITEM_ID, this.itemId);
		return values;
	}

	public static ChecklistItem newInstance(Cursor cursor, Context context) {
		ChecklistItem ChecklistItem = new ChecklistItem();
		ChecklistItem.fromCursor(cursor, context);
		return ChecklistItem;
	}

	@Override
	public void fromCSV(String csvLine) {

		StringTokenizer token = new StringTokenizer(csvLine,
				ChecklistApp.CSV_DELIMITER);
		int checklistId = Integer.parseInt(token.nextToken());
		int itemId = Integer.parseInt(token.nextToken());
		setId(checklistId);
		setItemId(itemId);

	}

	public int getChecklistId() {
		return checklistId;
	}

	public void setChecklistId(int checklistId) {
		this.checklistId = checklistId;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public void setId(int id) {
		this.id = id;
	}

}