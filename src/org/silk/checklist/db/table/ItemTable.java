package org.silk.checklist.db.table;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;


public final class ItemTable {
    public static final String TABLE_NAME = "Item_table";

    public static class ItemColumns implements BaseColumns {
        public static final String ITEM_TITLE = "item_title_column";
        public static final String GROUP_ID = "group_id_column";
        public static final String BASE_GROUP_ID = "base_group_id_column";
    }



    public static void onCreate(SQLiteDatabase db) {
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE " + ItemTable.TABLE_NAME + " (");
        sb.append(BaseColumns._ID + " INTEGER PRIMARY KEY, ");
        sb.append(ItemColumns.ITEM_TITLE + " TEXT, ");
        sb.append(ItemColumns.GROUP_ID + " INTEGER, ");
        sb.append(ItemColumns.BASE_GROUP_ID + " INTEGER");
        sb.append(");");
        db.execSQL(sb.toString());
    }

    public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ItemTable.TABLE_NAME);
        ItemTable.onCreate(db);
    }


}