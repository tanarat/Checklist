package org.silk.checklist.activity.checklist;

import java.util.HashSet;
import java.util.Set;

import org.silk.checklist.R;
import org.silk.checklist.db.ItemCheckedListener;
import org.silk.checklist.model.Checklist;
import org.silk.checklist.model.ModelBase;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

public class ChecklistCursorAdapter extends CursorAdapter {
	private ItemCheckedListener checkListener;
	private Set<Integer> checkedItemSet = new HashSet<Integer>();
	
	
	private int rowViewRes = R.layout.item_row_1;
	
	public void setCheckListener(ItemCheckedListener checkListener) {
		this.checkListener = checkListener;
	}

	public ChecklistCursorAdapter(Context context) {
		super(context, null, 0);

	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		// TODO Auto-generated method stub
		ViewHolder holder = (ViewHolder)view.getTag();
		Checklist t;
		t = new Checklist();
		t.fromCursor(cursor, context);
		holder.tvTitle.setText(t.getTitle());
		holder.tvDesc2.setText(t.getDescription2());
		if(checkedItemSet.contains(t.getId())){
			holder.chkSelect.setChecked(true);
		}else{
			holder.chkSelect.setChecked(false);
		}
		holder.chkSelect.setTag(t);
		
		
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
//		View view = LayoutInflater.from(context).inflate(R.layout.item_row_1, parent,false);
		View view = LayoutInflater.from(context).inflate(getRowViewRes(), parent,false);
		ViewHolder holder = new ViewHolder();
		holder.tvTitle = (TextView) view.findViewById(R.id.tvTitle);
		holder.tvDesc1 = (TextView) view.findViewById(R.id.tvDesc1);
		holder.tvDesc2 = (TextView) view.findViewById(R.id.tvDesc2);
		holder.chkSelect = (CheckBox) view.findViewById(R.id.chkSelect);
		view.setTag(holder);
		holder.chkSelect.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				CheckBox cb = (CheckBox) v;
				Checklist t = (Checklist)cb.getTag();
				if(cb.isChecked()){
					checkedItemSet.add(t.getId());
				}else{
					checkedItemSet.remove(t.getId());
				}
				notifyCheckItemChanged();
			}
		});
		return view;
	}
	private static final class ViewHolder{
		TextView tvTitle,tvDesc1,tvDesc2;
		CheckBox chkSelect;
	}
	public int getCheckedItemCount(){
		return checkedItemSet.size();
	}
	public void setCheckedItemIds(int[] checkedItemIds){
		clearCheckedItem();
		for (int i = 0; i < checkedItemIds.length; i++) {
			checkedItemSet.add(checkedItemIds[i]);
		}
		notifyCheckItemChanged();
	}
	public int[] getCheckedItemIds(){
		int[] ret = new int[checkedItemSet.size()];
		Integer[] arr = new Integer[checkedItemSet.size()];
		checkedItemSet.toArray(arr);
		for (int i = 0; i < arr.length; i++) {
			ret[i] = arr[i].intValue();
		}
		
		return ret;
	}
	public void clearCheckedItem(){
		checkedItemSet.clear();
		notifyCheckItemChanged();
	}

	private void notifyCheckItemChanged() {
		//notify check item has changed
		if(checkListener != null){
			checkListener.onCheckItemChecked();
		}
	}

	public int getRowViewRes() {
		return rowViewRes;
	}

	public void setRowViewRes(int rowViewRes) {
		this.rowViewRes = rowViewRes;
	}
	
}
