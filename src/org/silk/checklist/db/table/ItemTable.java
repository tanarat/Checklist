package org.silk.checklist.db.table;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;


public final class ItemTable {
    public static final String TABLE_NAME = "Item";

    public static class ItemColumns implements BaseColumns {
    	public static final String ITEM_KEY = "key";
        public static final String ITEM_TITLE = "title";
        public static final String PARENT_ID = "parent_id";
        public static final String BASE_ID = "base_id";
        public static final String ITEM_TYPE = "type";
    }



    public static void onCreate(SQLiteDatabase db) {
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE " + ItemTable.TABLE_NAME + " (");
        sb.append(BaseColumns._ID + " INTEGER PRIMARY KEY, ");
        sb.append(ItemColumns.ITEM_KEY + " TEXT, ");
        sb.append(ItemColumns.ITEM_TITLE + " TEXT, ");
        sb.append(ItemColumns.PARENT_ID + " INTEGER, ");
        sb.append(ItemColumns.BASE_ID + " INTEGER, ");
        sb.append(ItemColumns.ITEM_TYPE + " INTEGER");
        sb.append(");");
        db.execSQL(sb.toString());
    }

    public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ItemTable.TABLE_NAME);
        ItemTable.onCreate(db);
    }


}