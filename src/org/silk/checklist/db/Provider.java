package org.silk.checklist.db;

import org.silk.checklist.db.table.*;
import org.silk.checklist.model.Auditor;
import org.silk.checklist.model.Bpartner;
import org.silk.checklist.model.Checklist;
import org.silk.checklist.model.Sheet;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.provider.BaseColumns;

public class Provider extends ContentProvider {
    private OpenHelper dbHelper;
    private SQLiteDatabase database;
    public static final String AUTHORITY = "org.silk.checklist.db.contentprovider";
    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    private static final int BPARTNERS = 1001;
    private static final int BPARTNER_ID = 1002;
    public static final String BPARTNER_PATH = "Bpartners";
    public static final Uri BPARTNER_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BPARTNER_PATH);
    public static final String BPARTNER_CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/Bpartners";
    public static final String BPARTNER_CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/Bpartner";
    static {
        sURIMatcher.addURI(AUTHORITY, BPARTNER_PATH, BPARTNERS);
        sURIMatcher.addURI(AUTHORITY, BPARTNER_PATH + "/#", BPARTNER_ID);
    }

    /*
    private static final int GROUPS = 1003;
    private static final int GROUP_ID = 1004;
    public static final String GROUP_PATH = "Groups";
    public static final Uri GROUP_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + GROUP_PATH);
    public static final String GROUP_CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/Groups";
    public static final String GROUP_CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/Group";
    static {
        sURIMatcher.addURI(AUTHORITY, GROUP_PATH, GROUPS);
        sURIMatcher.addURI(AUTHORITY, GROUP_PATH + "/#", GROUP_ID);
    }
     */
    private static final int CHECKLISTS = 1005;
    private static final int CHECKLIST_ID = 1006;
    public static final String CHECKLIST_PATH = "Checklists";
    public static final Uri CHECKLIST_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + CHECKLIST_PATH);
    public static final String CHECKLIST_CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/Checklists";
    public static final String CHECKLIST_CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/Checklist";
    static {
        sURIMatcher.addURI(AUTHORITY, CHECKLIST_PATH, CHECKLISTS);
        sURIMatcher.addURI(AUTHORITY, CHECKLIST_PATH + "/#", CHECKLIST_ID);
    }

    private static final int CHECKLIST_ITEMS = 1007;
    private static final int CHECKLIST_ITEM_ID = 1008;
    public static final String CHECKLIST_ITEM_PATH = "Checklist_Items";
    public static final Uri CHECKLIST_ITEM_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + CHECKLIST_ITEM_PATH);
    public static final String CHECKLIST_ITEM_CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/Checklist_Items";
    public static final String CHECKLIST_ITEM_CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/Checklist_Item";
    static {
        sURIMatcher.addURI(AUTHORITY, CHECKLIST_ITEM_PATH, CHECKLIST_ITEMS);
        sURIMatcher.addURI(AUTHORITY, CHECKLIST_ITEM_PATH + "/#", CHECKLIST_ITEM_ID);
    }

    private static final int ITEMS = 1009;
    private static final int ITEM_ID = 1010;
    public static final String ITEM_PATH = "Items";
    public static final Uri ITEM_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + ITEM_PATH);
    public static final String ITEM_CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/Items";
    public static final String ITEM_CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/Item";
    static {
        sURIMatcher.addURI(AUTHORITY, ITEM_PATH, ITEMS);
        sURIMatcher.addURI(AUTHORITY, ITEM_PATH + "/#", ITEM_ID);
    }

    private static final int SHEETS = 1011;
    private static final int SHEET_ID = 1012;
    public static final String SHEET_PATH = "Sheets";
    public static final Uri SHEET_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + SHEET_PATH);
    public static final String SHEET_CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/Sheets";
    public static final String SHEET_CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/Sheet";
    static {
        sURIMatcher.addURI(AUTHORITY, SHEET_PATH, SHEETS);
        sURIMatcher.addURI(AUTHORITY, SHEET_PATH + "/#", SHEET_ID);
    }

    private static final int ANSWERS = 1013;
    private static final int ANSWER_ID = 1014;
    public static final String ANSWER_PATH = "Answers";
    public static final Uri ANSWER_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + ANSWER_PATH);
    public static final String ANSWER_CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/Answers";
    public static final String ANSWER_CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/Answer";
    static {
        sURIMatcher.addURI(AUTHORITY, ANSWER_PATH, ANSWERS);
        sURIMatcher.addURI(AUTHORITY, ANSWER_PATH + "/#", ANSWER_ID);
    }

    private static final int AUDITORS = 1015;
    private static final int AUDITOR_ID = 1016;
    public static final String AUDITOR_PATH = "Auditors";
    public static final Uri AUDITOR_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + AUDITOR_PATH);
    public static final String AUDITOR_CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/Auditors";
    public static final String AUDITOR_CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/Auditor";
    static {
        sURIMatcher.addURI(AUTHORITY, AUDITOR_PATH, AUDITORS);
        sURIMatcher.addURI(AUTHORITY, AUDITOR_PATH + "/#", AUDITOR_ID);
    }


    public static Uri getContentUri(Class clazz){
    	if(clazz == Bpartner.class)
    		return BPARTNER_CONTENT_URI;
    	if(clazz == Auditor.class)
    		return AUDITOR_CONTENT_URI;
    	if(clazz == Checklist.class)
    		return CHECKLIST_CONTENT_URI;
    	if(clazz == Sheet.class)
    		return SHEET_CONTENT_URI;
    	else
    		return null;
    }

    @Override
    public boolean onCreate() {
        dbHelper = new OpenHelper(getContext());
        database = dbHelper.getWritableDatabase();
        return true;
    }

    @Override
    public String getType(Uri uri) {
        int uriType = sURIMatcher.match(uri);
        switch (uriType) {
        case BPARTNERS:
            return BPARTNER_CONTENT_TYPE;
        case BPARTNER_ID:
            return BPARTNER_CONTENT_ITEM_TYPE;
//        case GROUPS:
//            return GROUP_CONTENT_TYPE;
//        case GROUP_ID:
//            return GROUP_CONTENT_ITEM_TYPE;
        case CHECKLISTS:
            return CHECKLIST_CONTENT_TYPE;
        case CHECKLIST_ID:
            return CHECKLIST_CONTENT_ITEM_TYPE;
        case CHECKLIST_ITEMS:
            return CHECKLIST_ITEM_CONTENT_TYPE;
        case CHECKLIST_ITEM_ID:
            return CHECKLIST_ITEM_CONTENT_ITEM_TYPE;
        case ITEMS:
            return ITEM_CONTENT_TYPE;
        case ITEM_ID:
            return ITEM_CONTENT_ITEM_TYPE;
        case SHEETS:
            return SHEET_CONTENT_TYPE;
        case SHEET_ID:
            return SHEET_CONTENT_ITEM_TYPE;
        case ANSWERS:
            return ANSWER_CONTENT_TYPE;
        case ANSWER_ID:
            return ANSWER_CONTENT_ITEM_TYPE;
        case AUDITORS:
            return AUDITOR_CONTENT_TYPE;
        case AUDITOR_ID:
            return AUDITOR_CONTENT_ITEM_TYPE;
        }
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int uriType = sURIMatcher.match(uri);
        long id = 0;
        switch (uriType) {
        case BPARTNERS:
            id = database.insert(BpartnerTable.TABLE_NAME, null, values);
            getContext().getContentResolver().notifyChange(uri, null);
            return Uri.parse("content://" + AUTHORITY + "/" + BPARTNER_PATH + "/" + id);
//        case GROUPS:
//            id = database.insert(GroupTable.TABLE_NAME, null, values);
//            getContext().getContentResolver().notifyChange(uri, null);
//            return Uri.parse("content://" + AUTHORITY + "/" + GROUP_PATH + "/" + id);
        case CHECKLISTS:
            id = database.insert(ChecklistTable.TABLE_NAME, null, values);
            getContext().getContentResolver().notifyChange(uri, null);
            return Uri.parse("content://" + AUTHORITY + "/" + CHECKLIST_PATH + "/" + id);
        case CHECKLIST_ITEMS:
            id = database.insert(ChecklistItemTable.TABLE_NAME, null, values);
            getContext().getContentResolver().notifyChange(uri, null);
            return Uri.parse("content://" + AUTHORITY + "/" + CHECKLIST_ITEM_PATH + "/" + id);
        case ITEMS:
            id = database.insert(ItemTable.TABLE_NAME, null, values);
            getContext().getContentResolver().notifyChange(uri, null);
            return Uri.parse("content://" + AUTHORITY + "/" + ITEM_PATH + "/" + id);
        case SHEETS:
            id = database.insert(SheetTable.TABLE_NAME, null, values);
            getContext().getContentResolver().notifyChange(uri, null);
            return Uri.parse("content://" + AUTHORITY + "/" + SHEET_PATH + "/" + id);
        case ANSWERS:
            id = database.insert(AnswerTable.TABLE_NAME, null, values);
            getContext().getContentResolver().notifyChange(uri, null);
            return Uri.parse("content://" + AUTHORITY + "/" + ANSWER_PATH + "/" + id);
        case AUDITORS:
            id = database.insert(AuditorTable.TABLE_NAME, null, values);
            getContext().getContentResolver().notifyChange(uri, null);
            return Uri.parse("content://" + AUTHORITY + "/" + AUDITOR_PATH + "/" + id);
        default:
            throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        int uriType = sURIMatcher.match(uri);
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        switch (uriType) {
        case BPARTNERS:
            queryBuilder.setTables(BpartnerTable.TABLE_NAME);
            break;
        case BPARTNER_ID:
            queryBuilder.setTables(BpartnerTable.TABLE_NAME);
            queryBuilder.appendWhere(BaseColumns._ID + "=" + uri.getLastPathSegment());
            break;
//        case GROUPS:
//            queryBuilder.setTables(GroupTable.TABLE_NAME);
//            break;
//        case GROUP_ID:
//            queryBuilder.setTables(GroupTable.TABLE_NAME);
//            queryBuilder.appendWhere(BaseColumns._ID + "=" + uri.getLastPathSegment());
//            break;
        case CHECKLISTS:
            queryBuilder.setTables(ChecklistTable.TABLE_NAME);
            break;
        case CHECKLIST_ID:
            queryBuilder.setTables(ChecklistTable.TABLE_NAME);
            queryBuilder.appendWhere(BaseColumns._ID + "=" + uri.getLastPathSegment());
            break;
        case CHECKLIST_ITEMS:
            queryBuilder.setTables(ChecklistItemTable.TABLE_NAME);
            break;
        case CHECKLIST_ITEM_ID:
            queryBuilder.setTables(ChecklistItemTable.TABLE_NAME);
            queryBuilder.appendWhere(BaseColumns._ID + "=" + uri.getLastPathSegment());
            break;
        case ITEMS:
            queryBuilder.setTables(ItemTable.TABLE_NAME);
            break;
        case ITEM_ID:
            queryBuilder.setTables(ItemTable.TABLE_NAME);
            queryBuilder.appendWhere(BaseColumns._ID + "=" + uri.getLastPathSegment());
            break;
        case SHEETS:
            queryBuilder.setTables(SheetTable.TABLE_NAME);
            break;
        case SHEET_ID:
            queryBuilder.setTables(SheetTable.TABLE_NAME);
            queryBuilder.appendWhere(BaseColumns._ID + "=" + uri.getLastPathSegment());
            break;
        case ANSWERS:
            queryBuilder.setTables(AnswerTable.TABLE_NAME);
            break;
        case ANSWER_ID:
            queryBuilder.setTables(AnswerTable.TABLE_NAME);
            queryBuilder.appendWhere(BaseColumns._ID + "=" + uri.getLastPathSegment());
            break;
        case AUDITORS:
            queryBuilder.setTables(AuditorTable.TABLE_NAME);
            break;
        case AUDITOR_ID:
            queryBuilder.setTables(AuditorTable.TABLE_NAME);
            queryBuilder.appendWhere(BaseColumns._ID + "=" + uri.getLastPathSegment());
            break;
        default:
            throw new IllegalArgumentException("Unknown URI: " + uri);        
        }
        Cursor cursor = queryBuilder.query(database, projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int uriType = sURIMatcher.match(uri);
        int rowsDeleted = 0;
        switch (uriType) {
        case BPARTNERS:
            rowsDeleted = database.delete(BpartnerTable.TABLE_NAME, selection, selectionArgs);
            break;
        case BPARTNER_ID:
            String BpartnerId = uri.getLastPathSegment();
            if (TextUtils.isEmpty(selection)) {
                rowsDeleted = database.delete(BpartnerTable.TABLE_NAME, BaseColumns._ID + "=" + BpartnerId, null);
            } else {
                rowsDeleted = database.delete(BpartnerTable.TABLE_NAME, BaseColumns._ID + "=" + BpartnerId + " AND " + selection, selectionArgs);
            }
            break;
//        case GROUPS:
//            rowsDeleted = database.delete(GroupTable.TABLE_NAME, selection, selectionArgs);
//            break;
//        case GROUP_ID:
//            String GroupId = uri.getLastPathSegment();
//            if (TextUtils.isEmpty(selection)) {
//                rowsDeleted = database.delete(GroupTable.TABLE_NAME, BaseColumns._ID + "=" + GroupId, null);
//            } else {
//                rowsDeleted = database.delete(GroupTable.TABLE_NAME, BaseColumns._ID + "=" + GroupId + " AND " + selection, selectionArgs);
//            }
//            break;
        case CHECKLISTS:
            rowsDeleted = database.delete(ChecklistTable.TABLE_NAME, selection, selectionArgs);
            break;
        case CHECKLIST_ID:
            String ChecklistId = uri.getLastPathSegment();
            if (TextUtils.isEmpty(selection)) {
                rowsDeleted = database.delete(ChecklistTable.TABLE_NAME, BaseColumns._ID + "=" + ChecklistId, null);
            } else {
                rowsDeleted = database.delete(ChecklistTable.TABLE_NAME, BaseColumns._ID + "=" + ChecklistId + " AND " + selection, selectionArgs);
            }
            break;
        case CHECKLIST_ITEMS:
            rowsDeleted = database.delete(ChecklistItemTable.TABLE_NAME, selection, selectionArgs);
            break;
        case CHECKLIST_ITEM_ID:
            String ChecklistItemId = uri.getLastPathSegment();
            if (TextUtils.isEmpty(selection)) {
                rowsDeleted = database.delete(ChecklistItemTable.TABLE_NAME, BaseColumns._ID + "=" + ChecklistItemId, null);
            } else {
                rowsDeleted = database.delete(ChecklistItemTable.TABLE_NAME, BaseColumns._ID + "=" + ChecklistItemId + " AND " + selection, selectionArgs);
            }
            break;
        case ITEMS:
            rowsDeleted = database.delete(ItemTable.TABLE_NAME, selection, selectionArgs);
            break;
        case ITEM_ID:
            String ItemId = uri.getLastPathSegment();
            if (TextUtils.isEmpty(selection)) {
                rowsDeleted = database.delete(ItemTable.TABLE_NAME, BaseColumns._ID + "=" + ItemId, null);
            } else {
                rowsDeleted = database.delete(ItemTable.TABLE_NAME, BaseColumns._ID + "=" + ItemId + " AND " + selection, selectionArgs);
            }
            break;
        case SHEETS:
            rowsDeleted = database.delete(SheetTable.TABLE_NAME, selection, selectionArgs);
            break;
        case SHEET_ID:
            String SheetId = uri.getLastPathSegment();
            if (TextUtils.isEmpty(selection)) {
                rowsDeleted = database.delete(SheetTable.TABLE_NAME, BaseColumns._ID + "=" + SheetId, null);
            } else {
                rowsDeleted = database.delete(SheetTable.TABLE_NAME, BaseColumns._ID + "=" + SheetId + " AND " + selection, selectionArgs);
            }
            break;
        case ANSWERS:
            rowsDeleted = database.delete(AnswerTable.TABLE_NAME, selection, selectionArgs);
            break;
        case ANSWER_ID:
            String AnswerId = uri.getLastPathSegment();
            if (TextUtils.isEmpty(selection)) {
                rowsDeleted = database.delete(AnswerTable.TABLE_NAME, BaseColumns._ID + "=" + AnswerId, null);
            } else {
                rowsDeleted = database.delete(AnswerTable.TABLE_NAME, BaseColumns._ID + "=" + AnswerId + " AND " + selection, selectionArgs);
            }
            break;
        case AUDITORS:
            rowsDeleted = database.delete(AuditorTable.TABLE_NAME, selection, selectionArgs);
            break;
        case AUDITOR_ID:
            String AuditorId = uri.getLastPathSegment();
            if (TextUtils.isEmpty(selection)) {
                rowsDeleted = database.delete(AuditorTable.TABLE_NAME, BaseColumns._ID + "=" + AuditorId, null);
            } else {
                rowsDeleted = database.delete(AuditorTable.TABLE_NAME, BaseColumns._ID + "=" + AuditorId + " AND " + selection, selectionArgs);
            }
            break;
        default:
            throw new IllegalArgumentException("Unknown URI: " + uri);      
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int uriType = sURIMatcher.match(uri);
        int rowsUpdated = 0;
        switch (uriType) {
        case BPARTNERS:
            rowsUpdated = database.update(BpartnerTable.TABLE_NAME, values, selection, selectionArgs);
            break;
        case BPARTNER_ID:
            String BpartnerId = uri.getLastPathSegment();
            if (TextUtils.isEmpty(selection)) {
                rowsUpdated = database.update(BpartnerTable.TABLE_NAME, values, BaseColumns._ID + "=" + BpartnerId, null);
            } else {
                rowsUpdated = database.update(BpartnerTable.TABLE_NAME, values, BaseColumns._ID + "=" + BpartnerId + " AND " + selection, selectionArgs);
            }
            break;
//        case GROUPS:
//            rowsUpdated = database.update(GroupTable.TABLE_NAME, values, selection, selectionArgs);
//            break;
//        case GROUP_ID:
//            String GroupId = uri.getLastPathSegment();
//            if (TextUtils.isEmpty(selection)) {
//                rowsUpdated = database.update(GroupTable.TABLE_NAME, values, BaseColumns._ID + "=" + GroupId, null);
//            } else {
//                rowsUpdated = database.update(GroupTable.TABLE_NAME, values, BaseColumns._ID + "=" + GroupId + " AND " + selection, selectionArgs);
//            }
//            break;
        case CHECKLISTS:
            rowsUpdated = database.update(ChecklistTable.TABLE_NAME, values, selection, selectionArgs);
            break;
        case CHECKLIST_ID:
            String ChecklistId = uri.getLastPathSegment();
            if (TextUtils.isEmpty(selection)) {
                rowsUpdated = database.update(ChecklistTable.TABLE_NAME, values, BaseColumns._ID + "=" + ChecklistId, null);
            } else {
                rowsUpdated = database.update(ChecklistTable.TABLE_NAME, values, BaseColumns._ID + "=" + ChecklistId + " AND " + selection, selectionArgs);
            }
            break;
        case CHECKLIST_ITEMS:
            rowsUpdated = database.update(ChecklistItemTable.TABLE_NAME, values, selection, selectionArgs);
            break;
        case CHECKLIST_ITEM_ID:
            String ChecklistItemId = uri.getLastPathSegment();
            if (TextUtils.isEmpty(selection)) {
                rowsUpdated = database.update(ChecklistItemTable.TABLE_NAME, values, BaseColumns._ID + "=" + ChecklistItemId, null);
            } else {
                rowsUpdated = database.update(ChecklistItemTable.TABLE_NAME, values, BaseColumns._ID + "=" + ChecklistItemId + " AND " + selection, selectionArgs);
            }
            break;
        case ITEMS:
            rowsUpdated = database.update(ItemTable.TABLE_NAME, values, selection, selectionArgs);
            break;
        case ITEM_ID:
            String ItemId = uri.getLastPathSegment();
            if (TextUtils.isEmpty(selection)) {
                rowsUpdated = database.update(ItemTable.TABLE_NAME, values, BaseColumns._ID + "=" + ItemId, null);
            } else {
                rowsUpdated = database.update(ItemTable.TABLE_NAME, values, BaseColumns._ID + "=" + ItemId + " AND " + selection, selectionArgs);
            }
            break;
        case SHEETS:
            rowsUpdated = database.update(SheetTable.TABLE_NAME, values, selection, selectionArgs);
            break;
        case SHEET_ID:
            String SheetId = uri.getLastPathSegment();
            if (TextUtils.isEmpty(selection)) {
                rowsUpdated = database.update(SheetTable.TABLE_NAME, values, BaseColumns._ID + "=" + SheetId, null);
            } else {
                rowsUpdated = database.update(SheetTable.TABLE_NAME, values, BaseColumns._ID + "=" + SheetId + " AND " + selection, selectionArgs);
            }
            break;
        case ANSWERS:
            rowsUpdated = database.update(AnswerTable.TABLE_NAME, values, selection, selectionArgs);
            break;
        case ANSWER_ID:
            String AnswerId = uri.getLastPathSegment();
            if (TextUtils.isEmpty(selection)) {
                rowsUpdated = database.update(AnswerTable.TABLE_NAME, values, BaseColumns._ID + "=" + AnswerId, null);
            } else {
                rowsUpdated = database.update(AnswerTable.TABLE_NAME, values, BaseColumns._ID + "=" + AnswerId + " AND " + selection, selectionArgs);
            }
            break;
        case AUDITORS:
            rowsUpdated = database.update(AuditorTable.TABLE_NAME, values, selection, selectionArgs);
            break;
        case AUDITOR_ID:
            String AuditorId = uri.getLastPathSegment();
            if (TextUtils.isEmpty(selection)) {
                rowsUpdated = database.update(AuditorTable.TABLE_NAME, values, BaseColumns._ID + "=" + AuditorId, null);
            } else {
                rowsUpdated = database.update(AuditorTable.TABLE_NAME, values, BaseColumns._ID + "=" + AuditorId + " AND " + selection, selectionArgs);
            }
            break;
        default:
            throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsUpdated;
    }


}