package com.vallco.downjoz.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.vallco.downjoz.utils.Const;
import com.vallco.downjoz.utils.ViewModel;

import java.util.ArrayList;

/**
 * Created by Javad on 7/19/2016.
 */
public class DbHandler {
    private Context mContext;
    private DbOpenHelper dbOpenHelper;
    private SQLiteDatabase sqLiteDb;

    public DbHandler(Context context) {
        mContext = context;
        dbOpenHelper = new DbOpenHelper(mContext);
    }

    public void openDataBase() {
        sqLiteDb = dbOpenHelper.getWritableDatabase();
    }

    public void closeDataBase() {
        dbOpenHelper.close();
    }

    public ArrayList<ViewModel> getAllData() {
        ArrayList<ViewModel> dataListAsDb = new ArrayList<ViewModel>();
        Cursor cursor = sqLiteDb.rawQuery("select * from " + dbOpenHelper.tblName, null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            ViewModel viewModel = new ViewModel(
                    cursor.getInt(Const.ID), cursor.getString(Const.BOOK_NAME),
                    cursor.getString(Const.IMAGE_URL), cursor.getString(Const.AUTHOR),
                    cursor.getString(Const.TYPE_VERSION), cursor.getString(Const.TYPE_PRODUCT),
                    cursor.getString(Const.PRODUCT_DESCRIPTION), null,
                    cursor.getString(Const.PRICE_PRODUCT));

            dataListAsDb.add(viewModel);

            cursor.moveToNext();
        }
        return dataListAsDb;
    }

    public Boolean checkId(int cId) {
        Boolean bolValue = false;
        Cursor cursor = sqLiteDb.rawQuery("select " + dbOpenHelper.cid + " from " +
                dbOpenHelper.tblName + " where " + dbOpenHelper.cid + " = " + cId, null);

        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            if (cursor.getInt(Const.ID) == cId) {
                bolValue = true;
            }
            cursor.moveToNext();
        }

        return bolValue;
    }

  /*


    if (Integer.parseInt( cursor.getString(dbOpenHelper.id))==cId){
                bolValue=true;


    public boolean idIsValid(int id) {
        Boolean aBoolean=false;
        Cursor cursor = sqLiteDb.rawQuery("SELECT * FROM " + dbOpenHelper.tblName + " WHERE ID=" + id, null);
        cursor.moveToFirst();
        int id1 = cursor.getInt(0);
            if(id==id1){
                aBoolean=true;
            }
        return aBoolean;
    }*/

    public void insert(int id, String nameBook, String price, String author, String productDescription, String typeVersion,String imageUrl) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(dbOpenHelper.cid, id);
        contentValues.put(dbOpenHelper.cBookName, nameBook);
        contentValues.put(dbOpenHelper.cPrice, price);
        contentValues.put(dbOpenHelper.cAuthor, author);
        contentValues.put(dbOpenHelper.cProductDescription, productDescription);
        contentValues.put(dbOpenHelper.cTypeVersion, typeVersion);
        contentValues.put(dbOpenHelper.cImageUrl, imageUrl);

        sqLiteDb.insert(dbOpenHelper.tblName, dbOpenHelper.cBookName, contentValues);
    }

    public Integer getCount() {
        Cursor cursor = sqLiteDb.query(dbOpenHelper.tblName, null, null, null, null, null, null, null);
        return cursor.getCount();
    }

    public void delete(int id) {
        sqLiteDb.delete(dbOpenHelper.tblName, "id=" + id, null);
    }

    public void update(String value, int column, int id) {
        ContentValues contentValues = new ContentValues();


        if (column == 1) {
            contentValues.put(dbOpenHelper.tblName, value);
        } else if (column == 2) {
            contentValues.put(dbOpenHelper.cPrice, value);
        } else if (column == 3) {
            contentValues.put(dbOpenHelper.cAuthor, value);
        }
        sqLiteDb.update(dbOpenHelper.tblName, contentValues, "id= " + id, null);

    }
}


