package org.silk.checklist.db.table;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;


public final class ChecklistItemTable {
    public static final String TABLE_NAME = "Checklist_Item_table";

    public static class ChecklistItemColumns implements BaseColumns {
        public static final String CHECKLIST_ID = "checklist_id_column";
        public static final String ITEM_ID = "item_id_column";
    }



    public static void onCreate(SQLiteDatabase db) {
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE " + ChecklistItemTable.TABLE_NAME + " (");
        sb.append(BaseColumns._ID + " INTEGER PRIMARY KEY, ");
        sb.append(ChecklistItemColumns.CHECKLIST_ID + " INTEGER, ");
        sb.append(ChecklistItemColumns.ITEM_ID + " INTEGER");
        sb.append(");");
        db.execSQL(sb.toString());
    }

    public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ChecklistItemTable.TABLE_NAME);
        ChecklistItemTable.onCreate(db);
    }


}