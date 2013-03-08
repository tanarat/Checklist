package org.silk.checklist.activity;

import org.silk.checklist.R;
import org.silk.checklist.model.Bpartner;
import org.silk.checklist.model.BpartnerBook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class BpartnerFragment extends Fragment implements OnItemClickListener{
	private String tag = "BpartnerFragment";
	private BpAdapter mAdapter;
	private ListView lvList;
	private BpartnerBook mBpBook;
	private Button btnNew;
	


	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mBpBook = new BpartnerBook(getActivity().getApplicationContext());
		
		View view = inflater.inflate(R.layout.bpartner_list, null);
		btnNew = (Button) view.findViewById(R.id.btnNew);
		btnNew.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				editBpartner(-1);
			}
		});
		lvList = (ListView) view.findViewById(R.id.list);
		mAdapter = new BpAdapter();
		lvList.setAdapter(mAdapter);
		lvList.setOnItemClickListener(this);
		
		return view;
	}


//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//		// TODO Auto-generated method stub
//		super.onCreate(savedInstanceState);
//		if(savedInstanceState != null){
//			
//		}
//	}


	@Override
	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);

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
	private BpartnerListener bpListener;
	public interface BpartnerListener{
		public void onBpartnerSelected(Bpartner bpartner);
	}
	
	@Override
	public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
		editBpartner(id);
	}


	private void editBpartner(long id) {
		Intent intent = new Intent(getActivity(),BpartnerDetailActivity.class);
		intent.putExtra("bpartnerId", id);
		startActivityForResult(intent, BPARTNER_DETAIL);
	}
	
	public static final int BPARTNER_DETAIL = 100;
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		if(requestCode == BPARTNER_DETAIL && resultCode == Activity.RESULT_OK){
			if(data.getBooleanExtra("detailChanged", false)){
				mAdapter.notifyDataSetChanged();
			}
		}
	}


	public BpartnerListener getBpListener() {
		return bpListener;
	}
	public void setBpListener(BpartnerListener bpListener) {
		this.bpListener = bpListener;
	}




	

}
