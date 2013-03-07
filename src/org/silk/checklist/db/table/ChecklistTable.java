package org.silk.checklist.db.table;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;


public final class ChecklistTable {
    public static final String TABLE_NAME = "Checklist_table";

    public static class ChecklistColumns implements BaseColumns {
        public static final String CHECKLIST_NAME = "checklist_name_column";
        public static final String UPDATED = "updated_column";
    }



    public static void onCreate(SQLiteDatabase db) {
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE " + ChecklistTable.TABLE_NAME + " (");
        sb.append(BaseColumns._ID + " INTEGER PRIMARY KEY, ");
        sb.append(ChecklistColumns.CHECKLIST_NAME + " TEXT, ");
        sb.append(ChecklistColumns.UPDATED + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP");
        sb.append(");");
        db.execSQL(sb.toString());
    }

    public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ChecklistTable.TABLE_NAME);
        ChecklistTable.onCreate(db);
    }


}