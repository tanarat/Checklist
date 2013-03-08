package org.silk.checklist.db;

import org.silk.checklist.R;
import org.silk.checklist.model.Bpartner;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class BpartnerCursorAdapter extends CursorAdapter{

	public BpartnerCursorAdapter(Context context) {
		super(context, null, 0);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		// TODO Auto-generated method stub
		ViewHolder holder = (ViewHolder)view.getTag();
		Bpartner bpartner = Bpartner.newInstance(cursor, context);
		holder.tvTitle.setText(bpartner.getCompanyName());
		
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		View view = LayoutInflater.from(context).inflate(R.layout.item_row_1, parent,false);
		ViewHolder holder = new ViewHolder();
		holder.tvTitle = (TextView) view.findViewById(R.id.tvTitle);
		holder.tvDesc1 = (TextView) view.findViewById(R.id.tvDesc1);
		holder.tvDesc2 = (TextView) view.findViewById(R.id.tvDesc2);
		view.setTag(holder);
		return view;
	}
	private static final class ViewHolder{
		TextView tvTitle,tvDesc1,tvDesc2;
	}

}
