package com.example.database01;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import java.util.ArrayList;

public class MyOpenHelper extends SQLiteOpenHelper {
    private static final String COMMENTS_TABLE_CREATE = "CREATE TABLE comments(_id INTEGER PRIMARY KEY AUTOINCREMENT, user TEXT, comment TEXT)";
    private static final String DB_NAME = "comments.sqlite";
    private static final int DB_VERSION = 1;
    private SQLiteDatabase db;

    public MyOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        db=this.getWritableDatabase();
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(COMMENTS_TABLE_CREATE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertar(String nombre,String comentario){
        ContentValues cv = new ContentValues();
        cv.put("user", nombre);
        cv.put("comment", comentario);
        db.insert("comments", null, cv);
    }

    public void borrar(int id){
        String[] args = new String[]{String.valueOf(id)};
        db.delete("comments", "_id=?", args);
    }

    public ArrayList<Comentario> getComments(){
        ArrayList<Comentario> lista=new ArrayList<Comentario>();
        Cursor c = db.rawQuery("select _id, user,comment from comments", null);
        if (c != null && c.getCount()>0) {
            c.moveToFirst();
            do {
                @SuppressLint("Range") String user = c.getString(c.getColumnIndex("user"));
                @SuppressLint("Range") String comment = c.getString(c.getColumnIndex("comment"));
                @SuppressLint("Range") int id=c.getInt(c.getColumnIndex("_id"));
                Comentario com =new Comentario(id,user,comment);
                lista.add(com);
            } while (c.moveToNext());
        }

        c.close();
        return lista;
    }
}