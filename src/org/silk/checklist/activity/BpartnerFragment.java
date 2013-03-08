package org.silk.checklist.activity;

import org.silk.checklist.R;
import org.silk.checklist.model.Bpartner;
import org.silk.checklist.model.BpartnerBook;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class BpartnerFragment extends Fragment{
	private String tag = "BpartnerFragment";
	private BpAdapter mAdapter;
	private ListView lvList;
	private BpartnerBook mBpBook;


	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mBpBook = new BpartnerBook(getActivity().getApplicationContext());
		
		View view = inflater.inflate(R.layout.bpartner_list, null);
		lvList = (ListView) view.findViewById(R.id.list);
		mAdapter = new BpAdapter();
		lvList.setAdapter(mAdapter);
		return view;
	}


	class BpAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return mBpBook.count();
		}

		@Override
		public Object getItem(int position) {
			return mBpBook.getBpartner(position);
		}

		@Override
		public long getItemId(int position) {
			return ((Bpartner)getItem(position)).getId();
		}

		@Override
		public View getView(int position, View view, ViewGroup parent) {
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
			holder.tvDesc2.setText(bp.getPhone());
			return view;
		}
		private final class ViewHolder{
			TextView tvTitle,tvDesc1,tvDesc2;
		}
	}




	

}
