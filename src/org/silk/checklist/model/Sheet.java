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
    private int checklistId;
    private int bpartnerId;
    private Date checkDate;
    
    private Checklist checklist;
    private Bpartner bpartner;


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
        this.checklistId = cursor.getInt(cursor.getColumnIndex(SheetTable.SheetColumns.CHECKLIST_ID));
        this.bpartnerId = cursor.getInt(cursor.getColumnIndex(SheetTable.SheetColumns.BPARTNER_ID));
        this.checkDate = new Date(cursor.getLong(cursor.getColumnIndex(SheetTable.SheetColumns.CHECK_DATE)));
        this.context = context;
    }

    @Override
    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(SheetTable.SheetColumns.SHEET_NAME, this.sheetName);
        values.put(SheetTable.SheetColumns.CHECKLIST_ID, this.checklistId);
        values.put(SheetTable.SheetColumns.BPARTNER_ID, this.bpartnerId);
//        values.put(SheetTable.SheetColumns.CHECK_DATE, this.checkDate.getTime());
        values.put(SheetTable.SheetColumns.CHECK_DATE, (new Date()).toString());
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
		return this.sheetName;
	}

	@Override
	public String getDescription1() {
		// TODO Auto-generated method stub
		if(checklist != null){
			return checklist.getChecklistName();
		}
		return "Desc1";
	}

	@Override
	public String getDescription2() {
		// TODO Auto-generated method stub
		if(bpartner != null){
			return bpartner.getCompanyName();
		}
		return "Desc2";
	}

	public String getSheetName() {
		return sheetName;
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	public int getBpartnerId() {
		return bpartnerId;
	}

	public void setBpartnerId(int bpartnerId) {
		this.bpartnerId = bpartnerId;
	}

	public Date getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Checklist getChecklist() {
		return checklist;
	}

	public void setChecklist(Checklist checklist) {
		this.checklist = checklist;
	}

	public Bpartner getBpartner() {
		return bpartner;
	}

	public void setBpartner(Bpartner bpartner) {
		this.bpartner = bpartner;
	}


}