package org.silk.checklist.activity.checklist;

import org.silk.checklist.R;
import org.silk.checklist.R.layout;
import org.silk.checklist.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.TextView;

public class ItemListActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tem_list);
		TextView tv = (TextView) findViewById(R.id.textView1);
		Intent intent = getIntent();
		tv.setText("Base group id : " + intent.getIntExtra("baseGroupId", -1));
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.item_list, menu);
		return true;
	}

}
