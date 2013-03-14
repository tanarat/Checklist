package org.silk.checklist.model;
import java.util.StringTokenizer;

import org.silk.checklist.ChecklistApp;
import org.silk.checklist.dao.Dao;
import org.silk.checklist.db.Provider;
import org.silk.checklist.db.table.ItemTable;

import android.net.Uri;
import android.provider.BaseColumns;
import android.util.Log;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class Item extends ModelBase {
	
	public static final int TYPE_GROUP = 0;
	public static final int TYPE_ITEM = 1;
	
    private Context context;
    private int id;
    private String key;
    private String title;
    private int parentId;
    private int baseId;
    private int type;
    
    
    private Item parent;
    private Item base;

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
        this.key = cursor.getString(cursor.getColumnIndex(ItemTable.ItemColumns.ITEM_KEY));
        this.title = cursor.getString(cursor.getColumnIndex(ItemTable.ItemColumns.ITEM_TITLE));
        this.parentId = cursor.getInt(cursor.getColumnIndex(ItemTable.ItemColumns.PARENT_ID));
        this.baseId = cursor.getInt(cursor.getColumnIndex(ItemTable.ItemColumns.BASE_ID));
        this.type = cursor.getInt(cursor.getColumnIndex(ItemTable.ItemColumns.ITEM_TYPE));
        this.context = context;
        
    }

    @Override
    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(ItemTable.ItemColumns._ID, this.id);
        values.put(ItemTable.ItemColumns.ITEM_KEY, this.key);
        values.put(ItemTable.ItemColumns.ITEM_TITLE, this.title);
        values.put(ItemTable.ItemColumns.PARENT_ID, this.parentId);
        values.put(ItemTable.ItemColumns.BASE_ID, this.baseId);
        values.put(ItemTable.ItemColumns.ITEM_TYPE, this.type);
        return values;
    }

    public static Item newInstance(Cursor cursor, Context context) {
        Item Item = new Item();
        Item.fromCursor(cursor, context);
        return Item;
    }

	@Override
	public void fromCSV(String csvLine) {
		String tag = "fromCSV";
		StringTokenizer token = new StringTokenizer(csvLine,ChecklistApp.CSV_DELIMITER);
		int id = Integer.parseInt(token.nextToken());
		Log.i(tag, "id : " + id);
		setId(id);
		
		String key = token.nextToken();
		Log.i(tag, "key : " + key);
		setKey(key);
		
		String title = token.nextToken();
		Log.i(tag, "title : " + title);
		setTitle(title);
		
		int parentId = Integer.parseInt(token.nextToken());
		Log.i(tag, "parent id : " + parentId);
		setParentId(parentId);
		
		int baseId = Integer.parseInt(token.nextToken());
		Log.i(tag, "base id : " + baseId);
		setBaseId(baseId);
		
		int type = Integer.parseInt(token.nextToken());
		Log.i(tag, "type : " + type);
		setType(type);
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return getId() + " " + getTitle();
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String itemTitle) {
		this.title = itemTitle;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int id) {
		this.parentId = id;
	}

	public int getBaseId() {
		return baseId;
	}

	public void setBaseId(int id) {
		this.baseId = id;
	}

	public void setId(int id) {
		this.id = id;
	}


	@Override
	public String getDescription1() {
		// TODO Auto-generated method stub
		if(parent == null)
			return "Desc1";
		else
			return parent.getKey() + " " + parent.getTitle();
		
	}

	@Override
	public String getDescription2() {
		if(base == null)
			return "Desc2";
		else
			return base.getKey() + " " + base.getTitle();
	}

	public Item getParent(){
		
		return parent;
	}
	public Item getBase() {
		return base;
	}

	public void setParent(Item parent) {
		this.parent = parent;
	}

	public void setBase(Item base) {
		this.base = base;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
}