package org.silk.checklist.activity;

import java.util.List;

import org.silk.checklist.R;
import org.silk.checklist.dao.Dao;
import org.silk.checklist.db.Provider;
import org.silk.checklist.model.Item;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ItemListAdapter extends BaseAdapter{
	private Dao<Item> dao;
	private List<Item> itemList;
	private Context context;
	public ItemListAdapter(Context context, List<Item> itemList){
		this.context = context;
		this.itemList = itemList;
//		dao = new Dao<Item>(Item.class, context, Provider.ITEM_CONTENT_URI);
//		itemList = dao.get(selection, seletecionArgs);
	}
	@Override
	public int getCount() {

		return itemList.size();
	}

	@Override
	public Object getItem(int position) {

		return itemList.get(position);
	}

	@Override
	public long getItemId(int position) {

		return ((Item)getItem(position)).getId();
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		ViewHolder holder;
		if(view == null){
			holder = new ViewHolder();
			view = LayoutInflater.from(context).inflate(R.layout.item_row_1, parent,false);
			holder.tvTitle = (TextView) view.findViewById(R.id.tvTitle);
			holder.tvDesc1 = (TextView) view.findViewById(R.id.tvDesc1);
			holder.tvDesc2 = (TextView) view.findViewById(R.id.tvDesc2);
			view.setTag(holder);
		}else{
			holder = (ViewHolder) view.getTag();
		}
		Item item = (Item) getItem(position);
		holder.tvTitle.setText((item.getTitle() != null)?  item.getTitle(): "" );
		holder.tvDesc1.setText((item.getDescription1() != null)? item.getDescription1() : "");

		holder.tvDesc2.setText((item.getDescription2() != null)?  item.getDescription2(): "" );
		
		return view;
	}
	private static final class ViewHolder{
		TextView tvTitle,tvDesc1,tvDesc2;
	}
	
}
