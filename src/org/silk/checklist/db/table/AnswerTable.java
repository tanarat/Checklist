package org.silk.checklist.db.table;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;


public final class AnswerTable {
    public static final String TABLE_NAME = "Answer_table";

    public static class AnswerColumns implements BaseColumns {
        public static final String SHEET_ID = "sheet_id_column";
        public static final String ITEM_ID = "item_id_column";
        public static final String ANSWER = "answer_column";
    }



    public static void onCreate(SQLiteDatabase db) {
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE " + AnswerTable.TABLE_NAME + " (");
        sb.append(BaseColumns._ID + " INTEGER PRIMARY KEY, ");
        sb.append(AnswerColumns.SHEET_ID + " INTEGER, ");
        sb.append(AnswerColumns.ITEM_ID + " INTEGER, ");
        sb.append(AnswerColumns.ANSWER + " INTEGER");
        sb.append(");");
        db.execSQL(sb.toString());
    }

    public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + AnswerTable.TABLE_NAME);
        AnswerTable.onCreate(db);
    }


}