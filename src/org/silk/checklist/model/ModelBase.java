package org.silk.checklist.model;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

abstract public class ModelBase {

        public ModelBase() {
                super();
        }
        
        abstract public void fromCursor(Cursor cursor, Context context);
        abstract public int getId();
        abstract public String getTitle();
        abstract public String getDescription1();
        abstract public String getDescription2();
        abstract public ContentValues toContentValues();
        abstract public void fromCSV(String csvLine);
        

}
