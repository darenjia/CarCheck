package com.bokun.bkjcb.carcheck.Database;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.bokun.bkjcb.carcheck.Utils.Constants;

/**
 * Created by DengShuai on 2018/11/2.
 * Description :
 */
public class DataBaseOpenHelper extends SQLiteOpenHelper {

    public DataBaseOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DataBaseOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    public DataBaseOpenHelper(Context context, String name, int version, SQLiteDatabase.OpenParams openParams) {
        super(context, name, version, openParams);
    }
    public DataBaseOpenHelper(Context context){
        this(context,"data.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(Constants.DataBaseCreateSQL.CREATE_PLAN);
        sqLiteDatabase.execSQL(Constants.DataBaseCreateSQL.CREATE_RECORD);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
