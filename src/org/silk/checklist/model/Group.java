package org.silk.checklist.model;
import java.util.StringTokenizer;

import org.silk.checklist.ChecklistApp;
import org.silk.checklist.db.table.GroupTable;

import android.provider.BaseColumns;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class Group extends ModelBase {
    private Context context;
    private int id;
    private String groupKey;
    private String groupName;
    private int parentGroupId;
    private int baseGroupId;


    public Group() {
        super();
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void fromCursor(Cursor cursor, Context context) {
        this.id = cursor.getInt(cursor.getColumnIndex(BaseColumns._ID));
        this.groupKey = cursor.getString(cursor.getColumnIndex(GroupTable.GroupColumns.GROUP_KEY));
        this.groupName = cursor.getString(cursor.getColumnIndex(GroupTable.GroupColumns.GROUP_NAME));
        this.parentGroupId = cursor.getInt(cursor.getColumnIndex(GroupTable.GroupColumns.PARENT_GROUP_ID));
        this.baseGroupId = cursor.getInt(cursor.getColumnIndex(GroupTable.GroupColumns.BASE_GROUP_ID));
        this.context = context;
    }

    @Override
    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(GroupTable.GroupColumns.GROUP_KEY, this.groupKey);
        values.put(GroupTable.GroupColumns.GROUP_NAME, this.groupName);
        values.put(GroupTable.GroupColumns.PARENT_GROUP_ID, this.parentGroupId);
        values.put(GroupTable.GroupColumns.BASE_GROUP_ID, this.baseGroupId);
        return values;
    }

    public static Group newInstance(Cursor cursor, Context context) {
        Group Group = new Group();
        Group.fromCursor(cursor, context);
        return Group;
    }

	@Override
	public void fromCSV(String csvLine) {
		StringTokenizer token = new StringTokenizer(csvLine,
				ChecklistApp.CSV_DELIMITER);
		setId(Integer.parseInt(token.nextToken()));
		setGroupKey(token.nextToken());
		setGroupName(token.nextToken());
		setParentGroupId(Integer.parseInt(token.nextToken()));
		setBaseGroupId(Integer.parseInt(token.nextToken()));
		
	}

	public String getGroupKey() {
		return groupKey;
	}

	public void setGroupKey(String groupKey) {
		this.groupKey = groupKey;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public int getParentGroupId() {
		return parentGroupId;
	}

	public void setParentGroupId(int parentGroupId) {
		this.parentGroupId = parentGroupId;
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