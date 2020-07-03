package com.raihan_sr.medicina.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.raihan_sr.medicina.util.Utils;

public class MySQLiteDB extends SQLiteOpenHelper {

    public static final String SHARED_PREF_NAME ="Button_Visibility";
    public static final String BOOLEAN_SHARED_PREF = "Boolean";

    private static final String DATABASE_NAME="medicina.db";
    private static final String PROFILE_TABLE_NAME ="profile_info";
    private static final String MEDICINE_TABLE_NAME ="medicine_info";
    private static final int VERSION_NUMBER= 2;

    private static final String PROFILE_ID ="_Id";
    public static final String NAME = "Name";
    public static final String SEX = "Sex";
    public static final String BIRTH_DATE ="Birth_Date";
    public static final String AGE ="Age";
    public static final String BLOOD_GROUP ="Blood_Group";
    public static final String HEIGHT ="Height";
    public static final String WEIGHT ="Weight";
    public static final String ADDRESS ="Address";
    public static final String NOTES ="Notes";

    public static final String MEDICINE_ID ="_Id";
    public static final String MEDICINE_NAME ="Medicine_Name";
    public static final String MEDICINE_POWER ="Medicine_Power";
    public static final String MEDICINE_TYPE ="Medicine_Type";
    public static final String MEDICINE_SHIFT ="Medicine_Shift";
    public static final String MEDICINE_TIME ="Medicine_Time";
    public static final String MEDICINE_ALARM ="Medicine_Alarm";
    public static final String MEDICINE_NOTES ="Medicine_Notes";


    private static final String CREATE_PROFILE_TABLE = "CREATE TABLE "+ PROFILE_TABLE_NAME +"( "+ PROFILE_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "+NAME+" VARCHAR(255), "
                                                +SEX+" VARCHAR(6), " + BIRTH_DATE +" VARCHAR(25), " +AGE+" INTEGER(3), "+ BLOOD_GROUP +" VARCHAR(3), "+ HEIGHT +" VARCHAR(25), "
                                                + WEIGHT +" VARCHAR(25), "+ADDRESS+" VARCHAR(255), "+ NOTES +" DESCRIPTION(255) ); ";

    private static final String CREATE_MEDICINE_TABLE = "CREATE TABLE " + MEDICINE_TABLE_NAME +"( "+MEDICINE_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, " + MEDICINE_NAME+" VARCHAR(255), "
                                                        + MEDICINE_POWER+" VARCHAR(25), " + MEDICINE_TYPE+" VARCHAR(25), " + MEDICINE_SHIFT +" VARCHAR(12), " + MEDICINE_TIME + " VARCHAR(12), "
                                                        + MEDICINE_ALARM +" VARCHAR(12), " + MEDICINE_NOTES +" VARCHAR(255) ); ";

    private static final String PROFILE_DROP_TABLE = "DROP TABLE IF EXISTS "+ PROFILE_TABLE_NAME;
    private static final String MEDICINE_DROP_TABLE = "DROP TABLE IF EXISTS "+ MEDICINE_TABLE_NAME;

    private static final String PROFILE_SELECT_ALL = "SELECT * FROM "+ PROFILE_TABLE_NAME;
    private static final String MEDICINE_SELECT_ALL = "SELECT * FROM "+ MEDICINE_TABLE_NAME;

    private Context context;


    public MySQLiteDB(Context context) {
        super(context, DATABASE_NAME, null, VERSION_NUMBER);
        this.context = context;
    }


    @Override public void onCreate(SQLiteDatabase db) {

        try{

            db.execSQL(CREATE_PROFILE_TABLE);
            db.execSQL(CREATE_MEDICINE_TABLE);
            Log.i(Utils.TAG, "OnCreate is called");

        }
        catch (Exception e){

            Log.i(Utils.TAG, "Exception: "+e.getMessage());
        }

    }


    @Override public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        try{

            db.execSQL(PROFILE_DROP_TABLE);
            db.execSQL(MEDICINE_DROP_TABLE);
            onCreate(db);
            Log.i(Utils.TAG, "OnUpgrade is called");

        }
        catch (Exception e){

            Log.i(Utils.TAG, "Exception: "+e.getMessage());

        }
    }


    /* Create Profile Data */
    public long insertProfileData(String name, String sex, String birth_date, String age, String blood_group, String height, String weight, String address, String description){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME,name);
        contentValues.put(SEX,sex);
        contentValues.put(BIRTH_DATE,birth_date);
        contentValues.put(AGE,age);
        contentValues.put(BLOOD_GROUP,blood_group);
        contentValues.put(HEIGHT,height);
        contentValues.put(WEIGHT,weight);
        contentValues.put(ADDRESS,address);
        contentValues.put(NOTES,description);
        return sqLiteDatabase.insert(PROFILE_TABLE_NAME,null,contentValues);
    }


    /* Display Profile Data */
    public Cursor displayProfileData(){

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        return sqLiteDatabase.rawQuery(PROFILE_SELECT_ALL,null);
    }


    /* Update Profile Data */
    public void updateProfile(String id, String key, String value){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(key,value);
        sqLiteDatabase.update(PROFILE_TABLE_NAME,contentValues, PROFILE_ID +" = ?", new String[]{id});
    }


    /* Delete Full Profile */
    public void deleteProfile(String id, String key){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(key,"");
        sqLiteDatabase.update(PROFILE_TABLE_NAME,contentValues, PROFILE_ID +" = ?", new String[]{id});
    }


    /* Delete Profile Data */
    public void deleteProfileData(String id){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(PROFILE_TABLE_NAME, PROFILE_ID + " = ?", new String[]{id});
    }



    /* Create Medicine Data */
    public long insertMedicineData(String name, String power, String type, String shift, String time, String alarm, String notes){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MEDICINE_NAME,name);
        contentValues.put(MEDICINE_POWER,power);
        contentValues.put(MEDICINE_TYPE,type);
        contentValues.put(MEDICINE_SHIFT,shift);
        contentValues.put(MEDICINE_TIME,time);
        contentValues.put(MEDICINE_ALARM,alarm);
        contentValues.put(MEDICINE_NOTES,notes);
        return sqLiteDatabase.insert(MEDICINE_TABLE_NAME,null,contentValues);
    }


    /* Display Medicine Data */
    public Cursor displayMedicineData(){

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        return sqLiteDatabase.rawQuery(MEDICINE_SELECT_ALL,null);
    }


    /* Update Medicine Data */
    public boolean updateMedicineData(String id, String name, String power, String type, String shift, String time, String alarm, String notes){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MEDICINE_NAME,name);
        contentValues.put(MEDICINE_POWER,power);
        contentValues.put(MEDICINE_TYPE,type);
        contentValues.put(MEDICINE_SHIFT,shift);
        contentValues.put(MEDICINE_TIME,time);
        contentValues.put(MEDICINE_ALARM,alarm);
        contentValues.put(MEDICINE_NOTES,notes);
        sqLiteDatabase.update(MEDICINE_TABLE_NAME,contentValues, MEDICINE_ID +" = ?", new String[]{id});
        return true;
    }


    /* Delete Medicine data */
    public int deleteMedicineData(String id){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        return sqLiteDatabase.delete(MEDICINE_TABLE_NAME, MEDICINE_ID +" = ?", new String[]{id});
    }
}
