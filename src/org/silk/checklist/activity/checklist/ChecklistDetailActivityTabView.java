package org.silk.checklist.activity.checklist;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.silk.checklist.ItemListBuilder;
import org.silk.checklist.ListItem;
import org.silk.checklist.R;
import org.silk.checklist.dao.Dao;
import org.silk.checklist.dao.ItemDao;
import org.silk.checklist.db.Provider;
import org.silk.checklist.model.Checklist;
import org.silk.checklist.model.Item;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;

public class ChecklistDetailActivityTabView extends Activity {
	Checklist mChecklist;
	int checklistId;
	Dao<Checklist> checklistDao;
	ItemDao itemDao;
	ItemListBuilder itemListBuilder;

	List<Item> lastLevelGroups = new ArrayList<Item>();

	List<Item> tabList = new ArrayList<Item>();
	List<Item> leftItemList = new ArrayList<Item>();
	List<Item> rightItemList = new ArrayList<Item>();

	ListView lvGroup, lvItem;
	TextView tv1;
	CheckItemListAdapter leftItemListAdapter;
	RightItemListAdapter rightItemListAdapter;
	MyAdapter expandListAdapter;

	private void loadLastLevelGroups(int checklistId, int baseGroupId) {
		lastLevelGroups = itemDao.getLastLevelGroups(checklistId, baseGroupId);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		checklistId = getIntent().getIntExtra("itemId", -1);
		checklistDao = new Dao<Checklist>(Checklist.class, this,
				Provider.CHECKLIST_CONTENT_URI);
		mChecklist = checklistDao.getById(checklistId);

		itemListBuilder = new ItemListBuilder();

		setContentView(R.layout.checklist_detail_tab_view);
		lvGroup = (ListView) findViewById(R.id.lvGroupList);
		lvItem = (ListView) findViewById(R.id.lvItemList);

		itemDao = new ItemDao(this);

		tabList = itemDao.getBaseItemList();

		// init tab
		TabHost tabs = (TabHost) findViewById(android.R.id.tabhost);
		tabs.setup();

		for (int i = 0; i < tabList.size(); i++) {
			TabHost.TabSpec spec = tabs.newTabSpec(String.valueOf(i));
			spec.setContent(R.id.content);
			spec.setIndicator(tabList.get(i).getTitle());
			tabs.addTab(spec);
		}
		tabs.setCurrentTab(0);

		tabs.setOnTabChangedListener(new OnTabChangeListener() {

			@Override
			public void onTabChanged(String tabIndex) {
				refreshGroupList(tabList.get(Integer.parseInt(tabIndex))
						.getId());
			}
		});

		tabs.setCurrentTab(0);

//		leftItemListAdapter = new LeftItemListAdapter(this, leftItemList);
//		lvGroup.setAdapter(leftItemListAdapter);
//		expandListAdapter = new MyAdapter(this, null);
//		lvGroup.setAdapter(expandListAdapter);
		lvGroup.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view,
					int position, long id) {
				refreshItemList((int) id);
			}
		});

		rightItemListAdapter = new RightItemListAdapter(this, rightItemList);
		lvItem.setAdapter(rightItemListAdapter);

	}

	private void refreshGroupList(int parentId) {
		// leftItemList = itemDao.getChildItemsOf(parentId);
		// leftItemListAdapter.setItemList(leftItemList);
		// leftItemListAdapter.notifyDataSetChanged();
		// if(leftItemList.size() != 0){
		// refreshItemList(leftItemList.get(0).getId());
		// }

		loadLastLevelGroups(checklistId, parentId);
		// put groups into itemListBuilder recursively
		for (Iterator<Item> iterator = lastLevelGroups.iterator(); iterator
				.hasNext();) {
			Item item = iterator.next();
			addGroupToItemListBuilder(item);
		}
		Log.i("refresh list",
				"last level group size : " + lastLevelGroups.size());
//		leftItemListAdapter.setItemList(lastLevelGroups);
		expandListAdapter = new MyAdapter(this, itemListBuilder.getItemArrayList());
		lvGroup.setAdapter(expandListAdapter);
		expandListAdapter.notifyDataSetChanged();
//		leftItemListAdapter.notifyDataSetChanged();
	}

	private void addGroupToItemListBuilder(Item g){
		//if this group has parent group and not be added into the list
//		if(g.getParentGroup() != null && !itemListBuilder.alreadyAdd(g.getParentGroup().getGroupId())){
		if(g.getParent() != null && !itemListBuilder.alreadyAdd(new Long(g.getParentId()))){	
			//add the parent first 
//			addGroupToItemListBuilder(g.getParentGroup());
			addGroupToItemListBuilder(g.getParent());
			//and then add itself
			addGroupToItemListBuilder(g);
		}else{
//			ListItem item = new ListItem(g.getGroupId(), g.getKey() + ". " + g.getGroupName());
			ListItem item = new ListItem(new Long(g.getId()), g.getKey() + ". " + g.getTitle());
//			if(g.getParentGroup() != null)
			if(g.getParent() != null)
//				itemListBuilder.add(item,g.getParentGroup().getGroupId());
				itemListBuilder.add(item,new Long(g.getParentId()));
			else
				itemListBuilder.add(item);
		}
	}
	private void refreshItemList(int groupId) {
		rightItemList = itemDao.getItemOf(groupId);
		rightItemListAdapter.setItemList(rightItemList);
		rightItemListAdapter.notifyDataSetChanged();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.checklist_detail_activity_tab_view,
				menu);
		return true;
	}

	

	class MyAdapter extends BaseAdapter{
		Context context;
		
		private List<ListItem> items;
		MyAdapter(Context context, List<ListItem> items){
			this.context = context;
			this.items = items;
			
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
			return ((ListItem)getItem(arg0)).getId();
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			// TODO Auto-generated method stub
			LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View expandableItem = inflater.inflate(R.layout.expandable_item, null);
			TextView tv = (TextView) expandableItem.findViewById(R.id.textView1);
			ImageView im = (ImageView) expandableItem.findViewById(R.id.imageView1);
			
			ListItem item = items.get(arg0);
			
			tv.setText(item.getName());
			if(item.isExpandable()){
				expandableItem.setBackgroundColor(context.getResources().getColor(android.R.color.darker_gray));
				if(item.isExpanded()){
					im.setImageResource(R.drawable.collapse);
				}else{
					im.setImageResource(R.drawable.expand);
				}
			}else{
				
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
