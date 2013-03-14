package org.silk.checklist.activity.checklist;

import java.util.ArrayList;
import java.util.List;

import org.silk.checklist.R;
import org.silk.checklist.dao.ItemDao;
import org.silk.checklist.model.Item;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CheckItemListAdapter extends BaseAdapter {
	private Context context;
	private List<Item> itemList = new ArrayList<Item>();

	public CheckItemListAdapter(Context context) {
		this.context = context;
	}
	public CheckItemListAdapter(Context context, List<Item> itemList) {
		this.context = context;
		this.itemList = itemList;
	}
	public void setItemList(List<Item> itemList){
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
		return ((Item) getItem(position)).getId();

	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		ViewHolder holder;
		if (view == null) {
			holder = new ViewHolder();
			view = LayoutInflater.from(context).inflate(R.layout.item_row_2,
					parent, false);
			holder.tvTitle = (TextView) view.findViewById(R.id.tvTitle);
			holder.tvDesc1 = (TextView) view.findViewById(R.id.tvDesc1);
			holder.tvDesc2 = (TextView) view.findViewById(R.id.tvDesc2);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		Item item = (Item) getItem(position);
		holder.tvTitle.setText(item.getTitle());
		holder.tvDesc1.setText("");
		holder.tvDesc2.setText("");

		return view;
	}

	private static final class ViewHolder {
		TextView tvTitle, tvDesc1, tvDesc2;
	}
}
