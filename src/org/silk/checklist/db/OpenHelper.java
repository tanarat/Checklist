package org.silk.checklist.db;

import org.silk.checklist.db.table.AnswerTable;
import org.silk.checklist.db.table.AuditorTable;
import org.silk.checklist.db.table.BpartnerTable;
import org.silk.checklist.db.table.ChecklistItemTable;
import org.silk.checklist.db.table.ChecklistTable;
import org.silk.checklist.db.table.GroupTable;
import org.silk.checklist.db.table.ItemTable;
import org.silk.checklist.db.table.SheetTable;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class OpenHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "silk.checklist.db";
    private static final int DATABASE_VERSION = 1;

    public OpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        BpartnerTable.onCreate(db);
        GroupTable.onCreate(db);
        ChecklistTable.onCreate(db);
        ChecklistItemTable.onCreate(db);
        ItemTable.onCreate(db);
        SheetTable.onCreate(db);
        AnswerTable.onCreate(db);
        AuditorTable.onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        BpartnerTable.onUpgrade(db, oldVersion, newVersion);
        GroupTable.onUpgrade(db, oldVersion, newVersion);
        ChecklistTable.onUpgrade(db, oldVersion, newVersion);
        ChecklistItemTable.onUpgrade(db, oldVersion, newVersion);
        ItemTable.onUpgrade(db, oldVersion, newVersion);
        SheetTable.onUpgrade(db, oldVersion, newVersion);
        AnswerTable.onUpgrade(db, oldVersion, newVersion);
        AuditorTable.onUpgrade(db, oldVersion, newVersion);
    }


}