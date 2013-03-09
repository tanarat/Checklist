package org.silk.checklist.activity;

import java.util.ArrayList;
import java.util.List;

import org.silk.checklist.R;
import org.silk.checklist.db.Dao;
import org.silk.checklist.db.Provider;
import org.silk.checklist.model.Bpartner;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

public class CopyOfBpartnerFragment extends Fragment implements OnItemClickListener {
	private String tag = "BpartnerFragment";
	private BpAdapter mAdapter;
	private ListView lvList;
	private List<Bpartner> mBpartners;
	private Dao<Bpartner> bpDao;
	private Button btnNew, btnDelete;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		bpDao = new Dao<Bpartner>(Bpartner.class, getActivity(),
				Provider.BPARTNER_CONTENT_URI);
		reload();

		View view = inflater.inflate(R.layout.bpartner_list, null);
		btnNew = (Button) view.findViewById(R.id.btnNew);
		btnNew.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				editBpartner(-1);
			}
		});

		btnDelete = (Button) view.findViewById(R.id.btnDelete);
		btnDelete.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				delete();
			}
		});

		lvList = (ListView) view.findViewById(R.id.list);
		mAdapter = new BpAdapter() {
			@Override
			public int getCount() {
				return mBpartners.size();
			}

			@Override
			public Object getItem(int position) {
				return mBpartners.get(position);
			}

			@Override
			public long getItemId(int position) {
				return mBpartners.get(position).getId();
			}
		};
		lvList.setAdapter(mAdapter);
		lvList.setOnItemClickListener(this);

		return view;
	}

	private void enableDelete(){
		if(pickSelectedBP().size() > 0)
			btnDelete.setEnabled(true);
		else
			btnDelete.setEnabled(false);
	}
	private List<Bpartner> pickSelectedBP() {
		List<Bpartner> selectBPs = new ArrayList<Bpartner>();
		for (int i = 0; i < mBpartners.size(); i++) {
			Bpartner bp = mBpartners.get(i);
			if (bp.isChecked()) {
				selectBPs.add(bp);
			}
		}
		return selectBPs;
	}

	private void delete() {
		final List<Bpartner> selectBPs = pickSelectedBP();
		new AlertDialog.Builder(getActivity())
		.setTitle(R.string.dialog_title_confirm_delete)
		.setMessage(R.string.dialog_message_confirm_delete)
		.setPositiveButton(R.string.button_ok, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				for (Bpartner bpartner : selectBPs) {
					bpDao.delete(bpartner);
				}
				reload();
				mAdapter.notifyDataSetChanged();
			}
		})
		.setNegativeButton(R.string.button_cancle, null).show();
	}

	private void reload() {
		mBpartners = bpDao.get(null, null);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);

	}

	abstract class BpAdapter extends BaseAdapter {
		@Override
		public View getView(int position, View view, ViewGroup parent) {
			ViewHolder holder;

			if (view == null) {
				view = LayoutInflater.from(
						getActivity().getApplicationContext()).inflate(
						R.layout.item_row_1, parent, false);
				holder = new ViewHolder();
				holder.tvTitle = (TextView) view.findViewById(R.id.tvTitle);
				holder.tvDesc1 = (TextView) view.findViewById(R.id.tvDesc1);
				holder.tvDesc2 = (TextView) view.findViewById(R.id.tvDesc2);
				holder.chkSelect = (CheckBox) view.findViewById(R.id.chkSelect);
				view.setTag(holder);

				holder.chkSelect.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						CheckBox cb = (CheckBox) v;
						Bpartner bpartner = (Bpartner) cb.getTag();
						bpartner.setChecked(cb.isChecked());
						enableDelete();
					}
				});

			} else {
				holder = (ViewHolder) view.getTag();

			}
			Bpartner bp = (Bpartner) getItem(position);
			holder.tvTitle.setText(bp.getCompanyName());
			holder.tvDesc2.setText(bp.getPhone());
			holder.chkSelect.setChecked(bp.isChecked());
			holder.chkSelect.setTag(bp);
			return view;
		}

		private final class ViewHolder {
			TextView tvTitle, tvDesc1, tvDesc2;
			CheckBox chkSelect;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> adapter, View view, int position,
			long id) {
		editBpartner(id);
	}

	private void editBpartner(long id) {
		Intent intent = new Intent(getActivity(), BpartnerDetailActivity.class);
		intent.putExtra("bpartnerId", id);
		startActivityForResult(intent, BPARTNER_DETAIL);
	}

	public static final int BPARTNER_DETAIL = 100;

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == BPARTNER_DETAIL && resultCode == Activity.RESULT_OK) {
			if (data.getBooleanExtra("detailChanged", false)) {
				reload();
				mAdapter.notifyDataSetChanged();
			}
		}
	}

}
