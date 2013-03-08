package org.silk.checklist.activity;

import java.util.List;

import org.silk.checklist.R;
import org.silk.checklist.db.Dao;
import org.silk.checklist.db.Provider;
import org.silk.checklist.model.Bpartner;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class BpartnerFragment extends Fragment{
	private String tag = "BpartnerFragment";
//	private BpartnerCursorAdapter mAdapter;
	private BpAdapter mAdapter;
	private ListView lvList;
	private Dao<Bpartner> bpDao;
	List<Bpartner> bpList;
	
	


	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		bpDao = new Dao<Bpartner>(Bpartner.class, getActivity().getApplicationContext(), Provider.BPARTNER_CONTENT_URI);
		bpList = bpDao.get(null, null);
		Log.i(tag, "[onCreateView]: bpList size :" + bpList.size());
		
		View view = inflater.inflate(R.layout.bpartner_list, null);
		lvList = (ListView) view.findViewById(R.id.list);
//		bpartnerAdapter = new BpartnerCursorAdapter(getActivity().getApplicationContext());
		mAdapter = new BpAdapter();
		lvList.setAdapter(mAdapter);
		return view;
	}


	class BpAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return bpList.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return bpList.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return bpList.get(position).getId();
		}

		@Override
		public View getView(int position, View view, ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder holder;
			if(view == null){
				view = LayoutInflater.from(getActivity().getApplicationContext()).inflate(R.layout.item_row_1, parent,false);
				holder = new ViewHolder();
				holder.tvTitle = (TextView) view.findViewById(R.id.tvTitle);
				holder.tvDesc1 = (TextView) view.findViewById(R.id.tvDesc1);
				holder.tvDesc2 = (TextView) view.findViewById(R.id.tvDesc2);
				view.setTag(holder);
			}else{
				holder = (ViewHolder) view.getTag();
			}
			Bpartner bp = (Bpartner) getItem(position);
			holder.tvTitle.setText(bp.getCompanyName());
			return view;
		}
		private final class ViewHolder{
			TextView tvTitle,tvDesc1,tvDesc2;
		}
	}




	

}
