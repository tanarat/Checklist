package org.silk.checklist.model;
import java.util.StringTokenizer;

import org.silk.checklist.ChecklistApp;
import org.silk.checklist.db.table.BpartnerTable;

import android.provider.BaseColumns;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class Bpartner extends ModelBase {
    private Context context;
    private int id;
    private String companyName;
    private String address;


    public Bpartner() {
        super();
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void fromCursor(Cursor cursor, Context context) {
        this.id = cursor.getInt(cursor.getColumnIndex(BaseColumns._ID));
        this.companyName = cursor.getString(cursor.getColumnIndex(BpartnerTable.BpartnerColumns.COMPANY_NAME));
        this.address = cursor.getString(cursor.getColumnIndex(BpartnerTable.BpartnerColumns.ADDRESS));
        this.context = context;
    }

    @Override
    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(BpartnerTable.BpartnerColumns.COMPANY_NAME, this.companyName);
        values.put(BpartnerTable.BpartnerColumns.ADDRESS, this.address);
        return values;
    }

    public static Bpartner newInstance(Cursor cursor, Context context) {
        Bpartner Bpartner = new Bpartner();
        Bpartner.fromCursor(cursor, context);
        return Bpartner;
    }

	@Override
	public void fromCSV(String csvLine) {
		StringTokenizer token = new StringTokenizer(csvLine,
				ChecklistApp.CSV_DELIMITER);
		setId(Integer.parseInt(token.nextToken()));
		setCompanyName(token.nextToken());
		setAddress(token.nextToken());
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setId(int id) {
		this.id = id;
	}


}