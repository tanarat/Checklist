package org.silk.checklist.db.table;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;


public final class AuditorTable {
    public static final String TABLE_NAME = "Auditor_table";

    public static class AuditorColumns implements BaseColumns {
        public static final String AUDITOR_NAME = "auditor_name_column";
    }



    public static void onCreate(SQLiteDatabase db) {
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE " + AuditorTable.TABLE_NAME + " (");
        sb.append(BaseColumns._ID + " INTEGER PRIMARY KEY, ");
        sb.append(AuditorColumns.AUDITOR_NAME + " TEXT");
        sb.append(");");
        db.execSQL(sb.toString());
    }

    public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + AuditorTable.TABLE_NAME);
        AuditorTable.onCreate(db);
    }


}