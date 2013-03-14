package org.silk.checklist.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.silk.checklist.db.Provider;
import org.silk.checklist.db.table.ChecklistItemTable;
import org.silk.checklist.db.table.GroupTable;
import org.silk.checklist.db.table.ItemTable;
import org.silk.checklist.model.ChecklistItem;
import org.silk.checklist.model.Item;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

public class ItemDao extends Dao<Item> {
	private String tag = "ItemDao";
	private Context mContext;
	private Uri mContentUri;
	private String mSelection;
	private String mSortOrder;

	public ItemDao(Context context) {
		super(Item.class, context, Provider.ITEM_CONTENT_URI);
		this.mContext = context;
		mContentUri = Provider.ITEM_CONTENT_URI;
	}

	public List<Item> getItemOf(int groupId) {
		String selection = ItemTable.ItemColumns.PARENT_ID + " = " + groupId;
		return get(selection, null);
	}

	public Item get(int position) {
		Item item = null;
		item = (Item) super.get(position);
		item.setParent(getById(item.getParentId()));
		item.setBase(getById(item.getBaseId()));

		return item;
	}

	public List<Item> get(String selection, String[] seletecionArgs) {
		ArrayList<Item> results = new ArrayList<Item>();
		String mergedSelection = null;
		if (selection != null && mSelection == null) {
			mergedSelection = selection;
		} else if (selection == null && mSelection != null) {
			mergedSelection = mSelection;
		} else if (selection != null && mSelection != null) {
			mergedSelection = mSelection + " AND " + selection;
		}
		Cursor cursor = mContext.getContentResolver().query(mContentUri, null,
				mergedSelection, seletecionArgs, mSortOrder);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Item item = new Item();
			item.fromCursor(cursor, mContext);

			item.setParent(getById(item.getParentId()));
			item.setBase(getById(item.getBaseId()));
			results.add(item);
			cursor.moveToNext();
		}
		cursor.close();
		return results;
	}

	public List<Item> getBaseItemList() {
		// TODO Auto-generated method stub
		String selection = ItemTable.ItemColumns.PARENT_ID + " = 0";
		return get(selection, null);

	}

	public List<Item> getChildItemsOf(int parentId) {
		String selection = ItemTable.ItemColumns.PARENT_ID + " = " + parentId;
		return get(selection, null);
	}

	/*
	 * step 1: select itemId from Checklist_Item where checklistId = ?
	 * 
	 * step 2: select parentId from Item where itemId in ([step 1])
	 */
	public List<Item> getLastLevelGroups(int checklistId, int baseGroupId) {

		Dao<ChecklistItem> checklistItemDao = new Dao<ChecklistItem>(
				ChecklistItem.class, mContext,
				Provider.CHECKLIST_ITEM_CONTENT_URI);
		String checklistItemsSelection = ChecklistItemTable.ChecklistItemColumns.CHECKLIST_ID
				+ " = " + checklistId;
//		Log.i(tag, checklistItemsSelection);
		
		List<ChecklistItem> checklistItems = checklistItemDao.get(
				checklistItemsSelection, null);
//		Log.i(tag, "checklistItems size : " + checklistItems.size());
		
		StringBuilder sb = new StringBuilder();
		if(baseGroupId != 0 && baseGroupId != -1){
			sb.append(ItemTable.ItemColumns.BASE_ID + " = " + baseGroupId);
		}
		
		if (checklistItems.size() != 0) {
			if(sb.length() != 0){
				sb.append(" AND ");
			}
			sb.append(ItemTable.ItemColumns._ID + " in ( ");
			for (Iterator<ChecklistItem> iterator = checklistItems.iterator(); iterator
					.hasNext();) {
				ChecklistItem checklistItem = (ChecklistItem) iterator.next();
				Item item = getById(checklistItem.getItemId());
				sb.append(item.getParentId());
				if (iterator.hasNext()) {
					sb.append(", ");
				} else {
					sb.append(")");
				}
			}
		}

//		sb.append(" AND " + ItemTable.ItemColumns.BASE_ID + " = " + baseGroupId);
//		Log.i(tag, sb.toString());

		String selection = sb.toString();
		return get(selection, null);

	}

	public List<Item> getLastLevelGroups(int checklistId) {

		return getLastLevelGroups(checklistId, -1);
	}

	public boolean isBaseItem(int id) {
		// TODO Auto-generated method stub
		Item item = getById(id);
		return item.getParentId() == 0;
	}

	/*
	 * select itemId from Checklist_Item where checklistId = ?
	 */
	public List<Item> getItem(int checklistId, int parentId) {
		Dao<ChecklistItem> checklistItemDao = new Dao<ChecklistItem>(
				ChecklistItem.class, mContext,
				Provider.CHECKLIST_ITEM_CONTENT_URI);
		String checklistItemsSelection = ChecklistItemTable.ChecklistItemColumns.CHECKLIST_ID
				+ " = " + checklistId;
		Log.i("A", checklistItemsSelection);
		
		List<ChecklistItem> checklistItems = checklistItemDao.get(
				checklistItemsSelection, null);
		Log.i("A", "checklistItems size : " + checklistItems.size());
		
		StringBuilder sb = new StringBuilder();
		if(parentId != 0 && parentId != -1){
			sb.append(ItemTable.ItemColumns.PARENT_ID + " = " + parentId);
		}
		
		if (checklistItems.size() != 0) {
			if(sb.length() != 0){
				sb.append(" AND ");
			}
			sb.append(ItemTable.ItemColumns._ID + " in ( ");
			for (Iterator<ChecklistItem> iterator = checklistItems.iterator(); iterator
					.hasNext();) {
				ChecklistItem checklistItem = (ChecklistItem) iterator.next();
				Item item = getById(checklistItem.getItemId());
				sb.append(item.getId());
				if (iterator.hasNext()) {
					sb.append(", ");
				} else {
					sb.append(")");
				}
			}
		}


		Log.i("A", sb.toString());

		String selection = sb.toString();
		return get(selection, null);

	}
}
