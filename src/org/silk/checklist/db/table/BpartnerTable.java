package org.silk.checklist.db.table;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;


public final class BpartnerTable {
    public static final String TABLE_NAME = "Bpartner_table";

    public static class BpartnerColumns implements BaseColumns {
        public static final String COMPANY_NAME = "company_name_column";
        public static final String ADDRESS = "address_column";
    }



    public static void onCreate(SQLiteDatabase db) {
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE " + BpartnerTable.TABLE_NAME + " (");
        sb.append(BaseColumns._ID + " INTEGER PRIMARY KEY, ");
        sb.append(BpartnerColumns.COMPANY_NAME + " TEXT, ");
        sb.append(BpartnerColumns.ADDRESS + " TEXT");
        sb.append(");");
        db.execSQL(sb.toString());
    }

    public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + BpartnerTable.TABLE_NAME);
        BpartnerTable.onCreate(db);
    }


}