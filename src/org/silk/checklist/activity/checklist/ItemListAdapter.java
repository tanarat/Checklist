package org.silk.checklist.activity.checklist;

import java.util.List;

import org.silk.checklist.R;
import org.silk.checklist.model.Item;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public abstract class ItemListAdapter extends BaseAdapter{

	private List<Item> itemList;


	public List<Item> getItemList() {
		return itemList;
	}
	public void setItemList(List<Item> itemList) {
		this.itemList = itemList;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return itemList.size();
	}
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return itemList.get(position);
	}
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return ((Item)getItem(position)).getId();
	}
	
}
