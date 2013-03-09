package org.silk.checklist.model;
import java.util.Date;

import org.silk.checklist.db.table.SheetTable;

import android.provider.BaseColumns;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class Sheet extends ModelBase {
    private Context context;
    private int id;
    private String sheetName;
    private int bpartnerId;
    private Date checkDate;


    public Sheet() {
        super();
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void fromCursor(Cursor cursor, Context context) {
        this.id = cursor.getInt(cursor.getColumnIndex(BaseColumns._ID));
        this.sheetName = cursor.getString(cursor.getColumnIndex(SheetTable.SheetColumns.SHEET_NAME));
        this.bpartnerId = cursor.getInt(cursor.getColumnIndex(SheetTable.SheetColumns.BPARTNER_ID));
        this.checkDate = new Date(cursor.getLong(cursor.getColumnIndex(SheetTable.SheetColumns.CHECK_DATE)));
        this.context = context;
    }

    @Override
    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(SheetTable.SheetColumns.SHEET_NAME, this.sheetName);
        values.put(SheetTable.SheetColumns.BPARTNER_ID, this.bpartnerId);
        values.put(SheetTable.SheetColumns.CHECK_DATE, this.checkDate.getTime());
        return values;
    }

    public static Sheet newInstance(Cursor cursor, Context context) {
        Sheet Sheet = new Sheet();
        Sheet.fromCursor(cursor, context);
        return Sheet;
    }

	@Override
	public void fromCSV(String csvLine) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDescription1() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDescription2() {
		// TODO Auto-generated method stub
		return null;
	}


}