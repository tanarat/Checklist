package org.silk.checklist.db.table;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;


public final class SheetTable {
    public static final String TABLE_NAME = "Sheet_table";

    public static class SheetColumns implements BaseColumns {
        public static final String SHEET_NAME = "sheet_name_column";
        public static final String CHECKLIST_ID = "checklist_id_column";
        public static final String BPARTNER_ID = "bpartner_id_column";
        public static final String CHECK_DATE = "check_date_column";
    }



    public static void onCreate(SQLiteDatabase db) {
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE " + SheetTable.TABLE_NAME + " (");
        sb.append(BaseColumns._ID + " INTEGER PRIMARY KEY, ");
        sb.append(SheetColumns.SHEET_NAME + " TEXT, ");
        sb.append(SheetColumns.CHECKLIST_ID + " INTEGER, ");
        sb.append(SheetColumns.BPARTNER_ID + " INTEGER, ");
        sb.append(SheetColumns.CHECK_DATE + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP");
        sb.append(");");
        db.execSQL(sb.toString());
    }

    public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + SheetTable.TABLE_NAME);
        SheetTable.onCreate(db);
    }


}