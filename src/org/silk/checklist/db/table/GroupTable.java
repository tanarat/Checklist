package org.silk.checklist.db.table;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;


public final class GroupTable {
    public static final String TABLE_NAME = "Group_table";

    public static class GroupColumns implements BaseColumns {
        public static final String GROUP_KEY = "group_key_column";
        public static final String GROUP_NAME = "group_name_column";
        public static final String PARENT_GROUP_ID = "parent_group_id_column";
        public static final String BASE_GROUP_ID = "base_group_id_column";
    }



    public static void onCreate(SQLiteDatabase db) {
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE " + GroupTable.TABLE_NAME + " (");
        sb.append(BaseColumns._ID + " INTEGER PRIMARY KEY, ");
        sb.append(GroupColumns.GROUP_KEY + " TEXT, ");
        sb.append(GroupColumns.GROUP_NAME + " TEXT, ");
        sb.append(GroupColumns.PARENT_GROUP_ID + " INTEGER, ");
        sb.append(GroupColumns.BASE_GROUP_ID + " INTEGER");
        sb.append(");");
        db.execSQL(sb.toString());
    }

    public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + GroupTable.TABLE_NAME);
        GroupTable.onCreate(db);
    }


}