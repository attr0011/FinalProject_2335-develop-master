package com.bearm.owlbotdictionary;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * This class references information from the course's labs, slides, and the android wiki
 * This class is for our database.
 * It contains the variables for our table and all of the data that is added to it
 */

public class OpenHelper extends SQLiteOpenHelper {

    public static final String filename = "OWLDbase";
    public static final int version = 1;
    public static final String TABLE_NAME = "Dictionary";
    public static final String COLM_ID = "id";
    public static final String COL_WORD = "Word";
    public static final String COL_PRON = "Pronunciation";
    public static final String COL_DEF = "Definition";
    public static final String COL_TYPE = "Type";
    public static final String COL_IMAGE = "Image";
    public static final String COL_EXAMPLE = "EXAMPLE";

    public OpenHelper(Context context) {
        super(context, filename, null, version);
    }

    // should be the creation statement
    @Override
    public void onCreate(SQLiteDatabase dbase) {
        //Create table Data ( _id INTEGER PRIMARY KEY AUTOINCREMENT, Message TEXT, SendOrReceive INTEGER, TimeSent TEXT );
        //String rs = String.format(" %s %s %s", "FirstString" , "10", "10.0" );

        //                                      //TABLE_NAME               take care of id numbers
        dbase.execSQL( String.format( "Create table %s ( %s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT );"
                , TABLE_NAME, COLM_ID, COL_WORD, COL_PRON, COL_DEF, COL_TYPE, COL_IMAGE, COL_EXAMPLE) );
    }

    // delete current table and create a new one
    @Override
    public void onUpgrade(SQLiteDatabase dbase, int oldVersion, int newVersion) {
        dbase.execSQL( "DROP TABLE IF EXISTS " + TABLE_NAME ); //deletes the current data
        //create a new table:

        this.onCreate(dbase); //when the database is created for the first time.
    }
}
