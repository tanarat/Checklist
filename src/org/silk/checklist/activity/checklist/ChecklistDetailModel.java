package org.silk.checklist.activity.checklist;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.silk.checklist.ItemListBuilder;
import org.silk.checklist.ListItem;

import org.silk.checklist.dao.ItemDao;
import org.silk.checklist.model.Item;

import android.content.Context;

public class ChecklistDetailModel {
	private List<Item> baseItemList = new ArrayList<Item>();
	private List<Item> lastLevelGroups = new ArrayList<Item>();
	private ItemListBuilder itemListBuilder = new ItemListBuilder();
	private List<Item> checkItemList = new ArrayList<Item>();
	
	private ItemDao itemDao;
	private Context context;
	private int checklistId;

	
	private ChecklistDetailModelChangeListener checklistDetailModelChangeListener;

	public interface ChecklistDetailModelChangeListener{
		public void onExpandableListChanged();
		public void onCheckItemListChanged();
	}
	public void setLastLevelGroupsListener(ChecklistDetailModelChangeListener listener){
		this.checklistDetailModelChangeListener = listener;
	}
	public ChecklistDetailModel(Context context, int checklistId) {
		super();
		this.context = context;
		itemDao = new ItemDao(context);
		baseItemList = itemDao.getBaseItemList();
		this.checklistId = checklistId;
	}

	public void setSelectedExpandableList(long id){
		if (itemListBuilder.getItem(id).isExpandable()) {
			itemListBuilder.toggleExpanded(id);
			notifyExpandableListChanged();
		} else {
			refreshCheckItemList((int)id);
		}
	}
	public void setCurrentBaseItemIndex(int baseItemIndex) {
		refreshExpandableList(baseItemIndex);
		if(lastLevelGroups.size() != 0){
			refreshCheckItemList(lastLevelGroups.get(0).getId());
		}else{
			refreshCheckItemList(-1);
		}
	}
	private void refreshCheckItemList(int parentId){
		if(parentId == -1){
			checkItemList.clear();
		}else{
			checkItemList = itemDao.getItem(checklistId, parentId);
		}
		notifyCheckItemListChanged();
	}
	private void refreshExpandableList(int baseItemIndex){
		itemListBuilder.clear();
		lastLevelGroups = itemDao.getLastLevelGroups(checklistId, baseItemList
				.get(baseItemIndex).getId());
		// put groups into itemListBuilder recursively
		for (Iterator<Item> iterator = lastLevelGroups.iterator(); iterator
				.hasNext();) {
			Item item = iterator.next();
			addGroupToItemListBuilder(item);
		}
		notifyExpandableListChanged();
	}
	private void notifyExpandableListChanged(){
		if(checklistDetailModelChangeListener != null){
			checklistDetailModelChangeListener.onExpandableListChanged();
		}
	}
	private void notifyCheckItemListChanged(){
		if(checklistDetailModelChangeListener != null){
			checklistDetailModelChangeListener.onCheckItemListChanged();
		}
	}

	private void addGroupToItemListBuilder(Item g) {
		// if this group has parent group and not be added into the list
		Item parent = g.getParent();
		if (parent != null // it has parent
				&& !itemDao.isBaseItem(parent.getId()) // its parent is not a
														// base item
				&& !itemListBuilder.alreadyAdd(new Long(g.getParentId()))) { // not
																				// already
																				// added
			// add the parent first
			addGroupToItemListBuilder(parent);
			// and then add itself
			addGroupToItemListBuilder(g);
		} else {
			ListItem item = new ListItem(new Long(g.getId()), g.getKey() + ". "
					+ g.getTitle());
			if (g.getParent() != null && !itemDao.isBaseItem(g.getParentId()))
				itemListBuilder.add(item, new Long(g.getParentId()));
			else
				itemListBuilder.add(item);
		}
	}
	public List<ListItem> getExpandableListItem(){
		return itemListBuilder.getItemArrayList();
	}
	public List<Item> getBaseItemList() {
		return baseItemList;
	}
	public List<Item> getCheckItemList() {
		// TODO Auto-generated method stub
		return checkItemList;
	}
	public void setBaseItemList(List<Item> baseItemList) {
		this.baseItemList = baseItemList;
	}

//	public List<Item> getLastLevelGroups() {
//		return lastLevelGroups;
//	}
//
//	public void setLastLevelGroups(List<Item> lastLevelGroups) {
//		this.lastLevelGroups = lastLevelGroups;
//	}

//	public ItemListBuilder getItemListBuilder() {
//		return itemListBuilder;
//	}
//
//	public void setItemListBuilder(ItemListBuilder itemListBuilder) {
//		this.itemListBuilder = itemListBuilder;
//	}

	public ItemDao getItemDao() {
		return itemDao;
	}

	public void setItemDao(ItemDao itemDao) {
		this.itemDao = itemDao;
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}
	

}
