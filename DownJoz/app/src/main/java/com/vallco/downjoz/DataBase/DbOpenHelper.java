package com.vallco.downjoz.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Javad on 7/19/2016.
 */
public class DbOpenHelper extends SQLiteOpenHelper {
    public static final String dbName = "dbDownJoz";
    public static final String tblName = "tblUsers";

    public static final String cid = "Id";
    public static final String cBookName = "BookName";
    public static final String cPrice = "Price";
    public static final String cAuthor = "Author";
    public static final String cProductDescription = "ProductDescription";
    public static final String cTypeVersion = "TypeVersion";
    public static final String cTypeProduct = "TypeProduct";
    public static final String cImageUrl = "ImageUrl";


    public static final String createTable = "CREATE TABLE " + tblName + "(" + cid + " INTEGER ,"
            + cBookName + " TEXT," + cPrice + " TEXT," + cProductDescription
            + " TEXT," + cTypeVersion + " TEXT," +  cTypeProduct + " TEXT," +  cAuthor + " TEXT,"  + cImageUrl + " TEXT);";


    public DbOpenHelper(Context context) {
        super(context, dbName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //  db.execSQL("DROP TABLE IF EXIST"+tblName);
        // onCreate(db);
    }
}
