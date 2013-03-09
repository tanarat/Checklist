package org.silk.checklist.model;
import java.util.StringTokenizer;

import org.silk.checklist.ChecklistApp;
import org.silk.checklist.db.table.ItemTable;
import org.silk.checklist.db.table.ItemTable.ItemColumns;

import android.provider.BaseColumns;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class CopyOfItem extends ModelBase {
    private Context context;
    private int id;
    private String itemTitle;


    public CopyOfItem() {
        super();
    }

    @Override
    public int getId() {
        return id;
    }

    public String getItemTitle() {
		return itemTitle;
	}

	public void setItemTitle(String itemTitle) {
		this.itemTitle = itemTitle;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
    public void fromCursor(Cursor cursor, Context context) {
        this.id = cursor.getInt(cursor.getColumnIndex(BaseColumns._ID));
        this.itemTitle = cursor.getString(cursor.getColumnIndex(ItemTable.ItemColumns.ITEM_TITLE));
        this.context = context;
    }

    @Override
    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(ItemTable.ItemColumns.ITEM_TITLE, this.itemTitle);
        return values;
    }

    public static CopyOfItem newInstance(Cursor cursor, Context context) {
        CopyOfItem Item = new CopyOfItem();
        Item.fromCursor(cursor, context);
        return Item;
    }

	@Override
	public void fromCSV(String csvLine) {
		// TODO Auto-generated method stub
		StringTokenizer token = new StringTokenizer(csvLine,ChecklistApp.CSV_DELIMITER);
		String str = token.nextToken();
		setId(Integer.parseInt(str));
		setItemTitle(token.nextToken());
//		Long groupId = Long.parseLong(token.nextToken());
//		Long baseGroupId = Long.parseLong(token.nextToken());
		
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return getId() + " " + getItemTitle();
	}

	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDescription1() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDescription2() {
		// TODO Auto-generated method stub
		return null;
	}


}