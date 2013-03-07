package org.silk.checklist.model;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

import org.silk.checklist.ChecklistApp;
import org.silk.checklist.db.table.AuditorTable;

import android.provider.BaseColumns;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class Auditor extends ModelBase {
    private Context context;
    private int id;
    private String auditorName;


    public Auditor() {
        super();
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void fromCursor(Cursor cursor, Context context) {
        this.id = cursor.getInt(cursor.getColumnIndex(BaseColumns._ID));
        this.auditorName = cursor.getString(cursor.getColumnIndex(AuditorTable.AuditorColumns.AUDITOR_NAME));
        this.context = context;
    }

    @Override
    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(AuditorTable.AuditorColumns.AUDITOR_NAME, this.auditorName);
        return values;
    }

    public static Auditor newInstance(Cursor cursor, Context context) {
        Auditor Auditor = new Auditor();
        Auditor.fromCursor(cursor, context);
        return Auditor;
    }

	@Override
	public void fromCSV(String csvLine) {
		StringTokenizer token = new StringTokenizer(csvLine,
				ChecklistApp.CSV_DELIMITER);

		setId(Integer.parseInt(token.nextToken()));
		setAuditorName(token.nextToken());
		
	}

	public String getAuditorName() {
		return auditorName;
	}

	public void setAuditorName(String auditorName) {
		this.auditorName = auditorName;
	}

	public void setId(int id) {
		this.id = id;
	}


}