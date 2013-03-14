package org.silk.checklist.activity;

import java.util.List;

import org.silk.checklist.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public abstract class MenuListAdapter extends BaseAdapter {

	private Context context;
	public MenuListAdapter(Context context){
		this.context = context;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		if(view == null){
			holder = new ViewHolder();
			view = LayoutInflater.from(context).inflate(R.layout.menu_list_row, null);
			holder.tvMenuTitle = (TextView) view.findViewById(R.id.tvMenuTitle);
			holder.imMenuIcon = (ImageView) view.findViewById(R.id.imMenuIcon);
			view.setTag(holder);
		}else{
			holder = (ViewHolder) view.getTag();
		}
		MyMenuItem menuItem = (MyMenuItem) getItem(position);
		holder.tvMenuTitle.setText(menuItem.getMenuTitle());
		holder.imMenuIcon.setImageDrawable(context.getResources().getDrawable(menuItem.getMenuIcon()));
		return view;
	}
	
	private static class ViewHolder{
		TextView tvMenuTitle;
		ImageView imMenuIcon;
	}
}
