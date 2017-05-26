package com.thesis.luna.vacationtracker;

/**
 * Created by Luna on 4/5/17.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Luna on 4/5/17.
 */

public class DatabaseHelper extends SQLiteOpenHelper
{
    // If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "VacationInfo.db";

    public static final String TABLE_NAME = "vacation_table";
    public static final String TABLE_NAME2 = "vacation_title_names";
    public static final String TABLE_NAME3 = "record_table";


    //COLUMNS FOR TABLE 1 VACATION TABLE
    public static final String COLUMN_NAME_ID = "_ID";
    public static final String COLUMN_NAME_FROM = "vacation_from";
    public static final String COLUMN_NAME_TO = "vacation_to";
    public static final String COLUMN_NAME_START= "vacation_start";
    public static final String COLUMN_NAME_END = "vacation_end";


    //COLUMNS FOR TABLE 2 VACATION TITLE NAMES
   // public static final String VACATIONID = "_id";
    public static final String VACATIONID = COLUMN_NAME_ID;
    public static final String VACATIONNAME = "vacation_name";


    //COLUMNS FOR TABLE 3
    public static final String RECORDID = "id";
    public static final String RECORDPLACE = "record_place";
    public static final String RECORDADDRESS = "record_address";
    public static final String RECORDNOTE = "record_note";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        COLUMN_NAME_FROM + " TEXT," + COLUMN_NAME_TO + " TEXT," +
                        COLUMN_NAME_START + " TEXT," + COLUMN_NAME_END + " TEXT)";
        db.execSQL(SQL_CREATE_ENTRIES.toString());

        String SQL_CREATE_VACATION_TITLE =
                "CREATE TABLE " + TABLE_NAME2 + " (" +
                        VACATIONID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        VACATIONNAME + " TEXT)";
        db.execSQL(SQL_CREATE_VACATION_TITLE.toString());

        String SQL_CREATE_RECORD =  "CREATE TABLE " + TABLE_NAME3 + " (" +
                RECORDID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                RECORDPLACE + " TEXT," + RECORDADDRESS + " TEXT," +
                RECORDNOTE + " TEXT)";
        db.execSQL(SQL_CREATE_RECORD.toString());

        if (!db.isReadOnly()) {
            // Enable foreign key constraints
            db.execSQL("PRAGMA foreign_keys=ON;");
        }


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS"+ TABLE_NAME);


        onCreate(db);

    }

    public void query()
    {
       /* String SQL = ;

        select Vacation_Table.vacation_from, Vacation_Table.vacation_to,Vacation_Table.vacation_start,Vacation_Table.vacation_end
        From Vacation_Title_Names, Vacation_Table
        Where Vacation_Title_Names._id = Vacation_Table._ID*/
    }

   public void insertData(VacationInfoLog vacationInfoLog) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_NAME_FROM, vacationInfoLog.getFrom());
        values.put(COLUMN_NAME_TO, vacationInfoLog.getTo());
        values.put(COLUMN_NAME_START, vacationInfoLog.getStart());
        values.put(COLUMN_NAME_END, vacationInfoLog.getEnd());

        // Inserting row
        db.insert(TABLE_NAME, null, values);
        db.close(); // Closing database connection
    }


    // Deleting all
    public void deleteAllData() {
        // Delete All Query
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DatabaseHelper.TABLE_NAME, null, null);
    }

    //-------------TABLE 2------------//

    public void insertData2(VacationTitleLog vacationTitleLog) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues v = new ContentValues();

        v.put(VACATIONNAME, vacationTitleLog.getTitle());

        // Inserting Row
        db.insert(TABLE_NAME2,null,v);
        db.close(); // Closing database connection
    }

    // Deleting all
    public void deleteAllData2() {
        // Delete All Query
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DatabaseHelper.TABLE_NAME2, null, null);
    }

    //-------------TABLE 3------------//

    public void insertData3(RecordLog r) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues v = new ContentValues();

        v.put(RECORDPLACE, r.getPlace());
        v.put(RECORDADDRESS,r.getAddress());
        v.put(RECORDNOTE,r.getNote());

        // Inserting Row
        db.insert(TABLE_NAME3,null,v);
        db.close(); // Closing database connection
    }


    // Deleting all
    public void deleteAllData3() {
        // Delete All Query
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DatabaseHelper.TABLE_NAME3, null, null);
    }




    // Getting all titles
    public List<VacationTitleLog> getAllData3() {
        List<VacationTitleLog> list = new ArrayList<VacationTitleLog>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_NAME2;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                VacationTitleLog vacationTitleLog = new VacationTitleLog();
                vacationTitleLog.setID(Integer.parseInt(cursor.getString(0)));
                vacationTitleLog.setTitle(cursor.getString(1));

                //Adding vacation title to list
                list.add(vacationTitleLog);
            } while (cursor.moveToNext());
        }
        //Return vacation list
        return list;
    }


    // Getting all vacations
    public List<VacationInfoLog> getAllData() {
        List<VacationInfoLog> list = new ArrayList<VacationInfoLog>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                VacationInfoLog vacationInfoLog = new VacationInfoLog();
                vacationInfoLog.setID(Integer.parseInt(cursor.getString(0)));
                vacationInfoLog.setFrom(cursor.getString(1));
                vacationInfoLog.setTo(cursor.getString(2));
                vacationInfoLog.setStart(cursor.getString(3));
                vacationInfoLog.setEnd(cursor.getString(4));


                //Adding vacation to list
                list.add(vacationInfoLog);
            } while (cursor.moveToNext());
        }
        //Return vacation list
        return list;
    }

    // Getting all records
    public List<RecordLog> getAllData2() {
        List<RecordLog> list = new ArrayList<RecordLog>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_NAME3;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                RecordLog recordLog = new RecordLog();
                recordLog.setID(Integer.parseInt(cursor.getString(0)));
                recordLog.setPlace(cursor.getString(1));
                recordLog.setAddress(cursor.getString(2));
                recordLog.setNote(cursor.getString(3));


                //Adding vacation record to list
                list.add(recordLog);
            } while (cursor.moveToNext());
        }
        //Return vacation list
        return list;
    }


    public void queryToVacationTitle() {


        SQLiteDatabase db = this.getWritableDatabase();
        /*Select *
                From Vacation_Table, Vacation_Title_Names
        Where Vacation_Title_Names._id = Vacation_Table._ID


        SELECT * FROM expense INNER JOIN refuel
        ON exp_id = expense_id
        WHERE refuel_id = 1*/

        String selectQuery = "SELECT * FROM " + TABLE_NAME + " INNER JOIN " + TABLE_NAME2
                + " ON " + VACATIONID + " = " + COLUMN_NAME_ID
                + " WHERE " + VACATIONID+ " = " +  COLUMN_NAME_ID;
        Cursor c = db.rawQuery(selectQuery, null);


        db.execSQL(c.toString());
        System.out.println(selectQuery.toString());

    }
}
