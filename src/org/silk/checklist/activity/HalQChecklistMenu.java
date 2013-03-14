package org.silk.checklist.activity;

import java.util.ArrayList;
import java.util.List;

import org.silk.checklist.R;
import org.silk.checklist.activity.ListMenuFragment.ListAdapter;
import org.silk.checklist.activity.auditor.AuditorListActivity;
import org.silk.checklist.activity.bpartner.BpartnerListActivity;
import org.silk.checklist.activity.checklist.ChecklistListActivity;
import org.silk.checklist.model.Bpartner;
import org.silk.checklist.model.Checklist;
import org.silk.checklist.model.Sheet;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class HalQChecklistMenu extends Fragment {
	String tag = "HalqChecklistMenu";
	ListView lvMenu;
	static final int MENU_SIZE = 4;
	static final int MENU_SHEET = 0;
	static final int MENU_CHECKLIST = 1;
	static final int MENU_BPARTNER = 2;
	static final int MENU_AUDITOR = 3;
	
	MyMenuItem[] mMenuItems; 
	
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Log.i(tag, "onCreate");
		mMenuItems = new MyMenuItem[MENU_SIZE];
		mMenuItems[MENU_SHEET] = new MyMenuItem(getResources().getString(R.string.menu_sheet), R.drawable.ic_search);
		mMenuItems[MENU_CHECKLIST] = new MyMenuItem(getResources().getString(R.string.menu_checklist), R.drawable.ic_search);
		mMenuItems[MENU_BPARTNER] = new MyMenuItem(getResources().getString(R.string.menu_bpartner), R.drawable.ic_search);
		mMenuItems[MENU_AUDITOR] = new MyMenuItem(getResources().getString(R.string.menu_auditor), R.drawable.ic_search);
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.halq_checklist_menu, container, false);
		lvMenu = (ListView) view.findViewById(R.id.lvMenu);
		lvMenu.setAdapter(new MenuListAdapter(getActivity()) {
			
			@Override
			public long getItemId(int position) {
				// TODO Auto-generated method stub
				return position;
			}
			
			@Override
			public Object getItem(int position) {
				// TODO Auto-generated method stub
				return mMenuItems[position];
			}
			
			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return mMenuItems.length;
			}
		} );

		lvMenu.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position,
					long id) {
				Intent intent = null;
				switch(position){
				case MENU_CHECKLIST:
					intent = new Intent(getActivity(), ChecklistListActivity.class);
					break;
				case MENU_BPARTNER:
					intent = new Intent(getActivity(), BpartnerListActivity.class);
					break;
				case MENU_AUDITOR:
					intent = new Intent(getActivity(), AuditorListActivity.class);
					break;
				}
				if(intent != null){
					startActivity(intent);
				}
			}
			
		});

		return view;
	}

	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu, inflater);
	}


	
}
