package org.silk.checklist.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.silk.checklist.db.Dao;

import android.content.Context;
import android.support.v4.widget.CursorAdapter;
import android.widget.ListAdapter;

public class ListModel<T extends ModelBase> {
//	private List<T> mList; 
//	private Dao<T> dao;
//	private ListAdapter listAdapter;
	private CursorAdapter cursorAdapter;
	private Set<Integer> checkedItemIds = new HashSet<Integer>();
	private Context context;
	public ListModel(Context context, Class<T> clazz) {
		this.context = context;
//		dao = new Dao<T>(clazz, context, null);
	}
//	public T getItem(int position){
////		return dao.get(position);
//		return (T) cursorAdapter.getItem(position);
//	}
//	public void delete(T item){
////		dao.delete(item);
//
//	}
//	public int count(){
////		return dao.size();
//		return cursorAdapter.getCount();
//	}
	
	/*
	 * return number of item deleted
	 */
	public int deleteAllSelected() {
		return 0;
	}
	public Set<Integer> getCheckedItemIds() {
		return checkedItemIds;
	}
	public void setCheckItem(int itemId, boolean checked){
		if(checked){
			checkedItemIds.add(itemId);
		}else{
			checkedItemIds.remove(itemId);
		}
	}
	public void addCheckedItemId(int itemId){
		checkedItemIds.add(itemId);
	}
	public int getCheckedItemCount(){
		return checkedItemIds.size();
	}
	public CursorAdapter getCursorAdapter() {
		return cursorAdapter;
	}
	public void setCursorAdapter(CursorAdapter cursorAdapter) {
		this.cursorAdapter = cursorAdapter;
	}
}
