package org.silk.checklist.activity.checklist;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.silk.checklist.ItemListBuilder;
import org.silk.checklist.ListItem;
import org.silk.checklist.R;

import org.silk.checklist.activity.checklist.ChecklistDetailModel.ChecklistDetailModelChangeListener;
import org.silk.checklist.dao.ItemDao;
import org.silk.checklist.model.Item;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.TabHost.OnTabChangeListener;

public class ChecklistDetailActivityTabView2 extends Activity implements
		OnTabChangeListener, OnItemClickListener, ChecklistDetailModelChangeListener {

	private ChecklistDetailModel model;
	ListView expanableListView, checkItemListView;

	MyAdapter expandListAdapter;
	CheckItemListAdapter checkItemListAdapter;

	int checklistId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.checklist_detail_tab_view);
		expanableListView = (ListView) findViewById(R.id.lvGroupList);
		checkItemListView = (ListView) findViewById(R.id.lvItemList);

		checklistId = getIntent().getIntExtra("itemId", -1);
		model = new ChecklistDetailModel(this, checklistId);
		model.setLastLevelGroupsListener(this);
		
		initTab();
		
		expandListAdapter = new MyAdapter(this);
		expanableListView.setAdapter(expandListAdapter);
		expanableListView.setOnItemClickListener(this);
		
		checkItemListAdapter = new CheckItemListAdapter(this);
		checkItemListView.setAdapter(checkItemListAdapter);
		
		
		
		
	}
//	long selectItemId ;
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
		model.setSelectedExpandableList(id);
	}

	@Override
	public void onExpandableListChanged() {
		expandListAdapter.setItemList(model.getExpandableListItem());
		expandListAdapter.notifyDataSetChanged();
	}
	
	@Override
	public void onCheckItemListChanged() {
		checkItemListAdapter.setItemList(model.getCheckItemList());
		checkItemListAdapter.notifyDataSetChanged();
	}


	@Override
	public void onTabChanged(String tabIndex) {
		model.setCurrentBaseItemIndex(Integer.parseInt(tabIndex));
		
	}
	

	private void initTab() {
		TabHost tabs = (TabHost) findViewById(android.R.id.tabhost);
		tabs.setup();

		for (int i = 0; i < model.getBaseItemList().size(); i++) {
			TabHost.TabSpec spec = tabs.newTabSpec(String.valueOf(i));
			spec.setContent(R.id.content);
			spec.setIndicator(model.getBaseItemList().get(i).getTitle());
			tabs.addTab(spec);
		}
		tabs.setCurrentTab(0);

		tabs.setOnTabChangedListener(this);

		tabs.setCurrentTab(0);
	}

	

	class MyAdapter extends BaseAdapter {
		Context context;

		private List<ListItem> items = new ArrayList<ListItem>();

		MyAdapter(Context context, List<ListItem> items) {
			this.context = context;
			this.items = items;

		}
		MyAdapter(Context context) {
			this.context = context;
		}
		public void setItemList(List<ListItem> itemList){
			this.items = itemList;
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return items.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return items.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return ((ListItem) getItem(arg0)).getId();
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			// TODO Auto-generated method stub
			LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View expandableItem = inflater.inflate(R.layout.expandable_item,
					null);
			TextView tv = (TextView) expandableItem
					.findViewById(R.id.textView1);
			ImageView im = (ImageView) expandableItem
					.findViewById(R.id.imageView1);

			ListItem item = items.get(arg0);

			tv.setText(item.getName());
			if (item.isExpandable()) {
				expandableItem.setBackgroundColor(context.getResources()
						.getColor(android.R.color.darker_gray));
				if (item.isExpanded()) {
					im.setImageResource(R.drawable.collapse);
				} else {
					im.setImageResource(R.drawable.expand);
				}
			} else {

				im.setImageResource(R.drawable.next);
			}

			return expandableItem;
		}

		public TextView getGenericView() {
			// Layout parameters for the ExpandableListView
			AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
					ViewGroup.LayoutParams.MATCH_PARENT, 64);

			TextView textView = new TextView(context);
			textView.setLayoutParams(lp);
			// Center the text vertically
			textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
			// Set the text starting position
			textView.setPadding(36, 0, 0, 0);
			return textView;
		}

	}







	
}
