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
        public String getTitle(){ 
        	return null;
        };
        public String getDescription1(){
        	return null;
        }
        public String getDescription2(){
        	return null;
        }
        abstract public ContentValues toContentValues();
        abstract public void fromCSV(String csvLine);
        

}
