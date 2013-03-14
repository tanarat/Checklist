package org.silk.checklist.model;
import org.silk.checklist.db.table.AnswerTable;

import android.provider.BaseColumns;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class Answer extends ModelBase {
    private Context context;
    private int id;
    private int sheetId;
    private int itemId;
    private int answer;


    public Answer() {
        super();
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void fromCursor(Cursor cursor, Context context) {
        this.id = cursor.getInt(cursor.getColumnIndex(BaseColumns._ID));
        this.sheetId = cursor.getInt(cursor.getColumnIndex(AnswerTable.AnswerColumns.SHEET_ID));
        this.itemId = cursor.getInt(cursor.getColumnIndex(AnswerTable.AnswerColumns.ITEM_ID));
        this.answer = cursor.getInt(cursor.getColumnIndex(AnswerTable.AnswerColumns.ANSWER));
        this.context = context;
    }

    @Override
    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(AnswerTable.AnswerColumns.SHEET_ID, this.sheetId);
        values.put(AnswerTable.AnswerColumns.ITEM_ID, this.itemId);
        values.put(AnswerTable.AnswerColumns.ANSWER, this.answer);
        return values;
    }

    public static Answer newInstance(Cursor cursor, Context context) {
        Answer Answer = new Answer();
        Answer.fromCursor(cursor, context);
        return Answer;
    }

	@Override
	public void fromCSV(String csvLine) {
		// TODO Auto-generated method stub
		
	}


}