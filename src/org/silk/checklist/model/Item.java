package org.silk.checklist.model;
import java.util.StringTokenizer;

import org.silk.checklist.ChecklistApp;
import org.silk.checklist.db.table.ItemTable;

import android.net.Uri;
import android.provider.BaseColumns;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class Item extends ModelBase {
    private Context context;
    private int id;
    private String itemTitle;
    private int groupId;
    private int baseGroupId;


    public Item() {
        super();
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void fromCursor(Cursor cursor, Context context) {
        this.id = cursor.getInt(cursor.getColumnIndex(BaseColumns._ID));
        this.itemTitle = cursor.getString(cursor.getColumnIndex(ItemTable.ItemColumns.ITEM_TITLE));
        this.groupId = cursor.getInt(cursor.getColumnIndex(ItemTable.ItemColumns.GROUP_ID));
        this.baseGroupId = cursor.getInt(cursor.getColumnIndex(ItemTable.ItemColumns.BASE_GROUP_ID));
        this.context = context;
    }

    @Override
    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(ItemTable.ItemColumns.ITEM_TITLE, this.itemTitle);
        values.put(ItemTable.ItemColumns.GROUP_ID, this.groupId);
        values.put(ItemTable.ItemColumns.BASE_GROUP_ID, this.baseGroupId);
        return values;
    }

    public static Item newInstance(Cursor cursor, Context context) {
        Item Item = new Item();
        Item.fromCursor(cursor, context);
        return Item;
    }

	@Override
	public void fromCSV(String csvLine) {
		StringTokenizer token = new StringTokenizer(csvLine,ChecklistApp.CSV_DELIMITER);
		setId(Integer.parseInt(token.nextToken()));
		setItemTitle(token.nextToken());
		setGroupId(Integer.parseInt(token.nextToken()));
		setBaseGroupId(Integer.parseInt(token.nextToken()));
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return getId() + " " + getItemTitle();
	}
	
	public String getItemTitle() {
		return itemTitle;
	}

	public void setItemTitle(String itemTitle) {
		this.itemTitle = itemTitle;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public int getBaseGroupId() {
		return baseGroupId;
	}

	public void setBaseGroupId(int baseGroupId) {
		this.baseGroupId = baseGroupId;
	}

	public void setId(int id) {
		this.id = id;
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