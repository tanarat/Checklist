package org.silk.checklist.activity;

import org.silk.checklist.R;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ListMenuFragment extends Fragment implements OnItemClickListener {
	private ListView lvMenu;
	private OnListMenuSelectedListener menuSelectedListener;
	
	public void setOnListMenuSelectedListener(OnListMenuSelectedListener listener){
		menuSelectedListener = listener;
	}
	public interface OnListMenuSelectedListener{
		public void onListMenuSelected(int position);
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.left_menu, null);
		lvMenu = (ListView) view.findViewById(R.id.lvMenuItem);
		lvMenu.setOnItemClickListener(this);
		if(menuSelectedListener != null){
			menuSelectedListener.onListMenuSelected(0);
		}
		return view;
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		ListAdapter adapter = new ListAdapter(getActivity());
//		adapter.add(new MenuItem(getString(R.string.menu_sheet), R.drawable.ic_search));
		adapter.add(new MenuItem(getString(R.string.main_menu_halq_checklist), R.drawable.ic_search));

//		adapter.add(new MenuItem(getString(R.string.menu_checklist),R.drawable.ic_search));
//		adapter.add(new MenuItem(getString(R.string.menu_bpartner),R.drawable.ic_search));
//		adapter.add(new MenuItem(getString(R.string.menu_auditor), R.drawable.ic_search));
		lvMenu.setAdapter(adapter);
		
	}

	private class MenuItem {
		public String tag;
		public int iconRes;
		public MenuItem(String tag, int iconRes) {
			this.tag = tag; 
			this.iconRes = iconRes;
		}
	}

	public class ListAdapter extends ArrayAdapter<MenuItem> {

		public ListAdapter(Context context) {
			super(context, 0);
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = LayoutInflater.from(getContext()).inflate(R.layout.row, null);
			}
			ImageView icon = (ImageView) convertView.findViewById(R.id.row_icon);
			icon.setImageResource(getItem(position).iconRes);
			TextView title = (TextView) convertView.findViewById(R.id.row_title);
			title.setText(getItem(position).tag);

			return convertView;
		}

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
		if(menuSelectedListener != null){
			menuSelectedListener.onListMenuSelected(position);
		}
	}
}
