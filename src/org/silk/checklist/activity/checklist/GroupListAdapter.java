package org.silk.checklist.activity.checklist;

import org.silk.checklist.R;
import org.silk.checklist.model.Group;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public abstract class GroupListAdapter extends BaseAdapter{

	private Context context;
	public GroupListAdapter(Context context){
		this.context = context;

	}
	

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		ViewHolder holder;
		if(view == null){
			holder = new ViewHolder();
			view = LayoutInflater.from(context).inflate(R.layout.item_row_2, parent,false);
			holder.tvTitle = (TextView) view.findViewById(R.id.tvTitle);
			holder.tvDesc1 = (TextView) view.findViewById(R.id.tvDesc1);
			holder.tvDesc2 = (TextView) view.findViewById(R.id.tvDesc2);
			view.setTag(holder);
		}else{
			holder = (ViewHolder) view.getTag();
		}
		Group group = (Group) getItem(position);
		holder.tvTitle.setText(group.getGroupName());
		holder.tvDesc1.setText(group.getGroupKey());
		holder.tvDesc2.setText("");
		
		return view;
	}
	private static final class ViewHolder{
		TextView tvTitle,tvDesc1,tvDesc2;
	}
	
}
