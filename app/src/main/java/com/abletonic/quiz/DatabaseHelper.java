package com.abletonic.quiz;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class DatabaseHelper extends SQLiteOpenHelper  {

    Context mcontext;
    String dbName, dbPath;


    public DatabaseHelper(Context context, int version) {
        super(context, "QuizDB.db", null, version);
        this.dbName = "QuizDB.db";
        this.mcontext = context;
        this.dbPath = "data/data/"+ "com.abletonic.quiz"+"/databases/";
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void CheckDb() {
        SQLiteDatabase checkDb = null;
        String filePath = dbPath + dbName;
        try {
            checkDb = SQLiteDatabase.openDatabase(filePath, null, 0);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        if(checkDb == null) {
            CopyDatabase();
        }
    }

    public void CopyDatabase() {
        this.getReadableDatabase();

        try {
            InputStream ios = mcontext.getAssets().open(dbName);
            OutputStream os = new FileOutputStream(dbPath+dbName);
            byte[] buffer = new byte[1024];
            int length;
            while((length = ios.read(buffer))>0) {
                os.write(buffer, 0, length);

            }
            os.flush();
            ios.close();
            os.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("CopyDB", "DB Copied");
    }

    public void OpenDatabase() {
        String filePath = dbPath + dbName;

        SQLiteDatabase.openDatabase(filePath, null, 0);
    }

    public Cursor getMainCategories() {
        String filePath = dbPath + dbName;
        SQLiteDatabase db = SQLiteDatabase.openDatabase(filePath, null, SQLiteDatabase.OPEN_READWRITE);
        Cursor res = db.rawQuery("select * from Category", null);
        return res;
    }

    public Cursor getSubCategories(int categoryID) {
        String id = String.valueOf(categoryID);
        String filePath = dbPath + dbName;
        SQLiteDatabase db = SQLiteDatabase.openDatabase(filePath, null, SQLiteDatabase.OPEN_READWRITE);
        Cursor res = db.rawQuery("select * from SubCategory where ID_CAT = ?", new String[]{id});
        return res;
    }

    public Integer getSubCategoriesLength(int categoryID) {
        String id = String.valueOf(categoryID);
        int count = 0;
        try {
            String filePath = dbPath + dbName;
            SQLiteDatabase db = SQLiteDatabase.openDatabase(filePath, null, SQLiteDatabase.OPEN_READWRITE);
            Cursor res = db.rawQuery("select * from SubCategory where ID_CAT = ?", new String[]{id});
            count = res.getCount();
        }
        catch (Exception e) {}
        return count;
    }

    public Cursor getQuestion(int subCategory_ID) {
        String filePath = dbPath + dbName;
        String id = String.valueOf(subCategory_ID);
        SQLiteDatabase db = SQLiteDatabase.openDatabase(filePath, null, SQLiteDatabase.OPEN_READWRITE);
        Cursor res = db.rawQuery("select * from Question where SubID = ?", new String[]{id});
        return res;
    }

    public Integer getMainCatQuestionsCount(int category_ID) {
        String id = String.valueOf(category_ID);
        int count = 0;
        try {
            String filePath = dbPath + dbName;
            SQLiteDatabase db = SQLiteDatabase.openDatabase(filePath, null, SQLiteDatabase.OPEN_READWRITE);
            Cursor res = db.rawQuery("select * from Question where CategoryID = ?", new String[]{id});
            count = res.getCount();
        }
        catch (Exception e) {}
        return count;
    }

    public Integer getSubCatQuestionsCount(int subCategory_ID) {
        String id = String.valueOf(subCategory_ID);
        int count = 0;
        try {
            String filePath = dbPath + dbName;
            SQLiteDatabase db = SQLiteDatabase.openDatabase(filePath, null, SQLiteDatabase.OPEN_READWRITE);
            Cursor res = db.rawQuery("select * from Question where SubID = ?", new String[]{id});
            count = res.getCount();
        }
        catch (Exception e) {}
        return count;
    }

}
