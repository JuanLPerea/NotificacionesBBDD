package jlp.notificacionesbbdd;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by TorreJL on 21/04/2017.
 */



public class DatabaseHelper extends SQLiteOpenHelper {

    private static DatabaseHelper sInstance;

    private static final String DATABASE_NAME = "database_name";
    private static final String DATABASE_TABLE = "table_name";
    private static final String sqlCreateDatos = "CREATE TABLE datos(id INTEGER, notificacion TEXT, publicado TEXT)";
    private static final int DATABASE_VERSION = 1;
    private static Cursor cursor;
    private static SQLiteDatabase db;
    private static int id;

    public static synchronized DatabaseHelper getInstance(Context context) {

        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new DatabaseHelper(context.getApplicationContext());

        }
        return sInstance;
    }

    /**
     * Constructor should be private to prevent direct instantiation.
     * make call to static method "getInstance()" instead.
     */
    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        id = 0;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS datos");

        sqLiteDatabase.execSQL(sqlCreateDatos);

      //  insertarDato("Linea 1");
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS datos");

        sqLiteDatabase.execSQL(sqlCreateDatos);

        insertarDato("Linea 1");

    }


    public void openDB() {
        db = sInstance.getWritableDatabase();

        db.execSQL("DROP TABLE IF EXISTS datos");

        db.execSQL(sqlCreateDatos);

        cursor = db.rawQuery("SELECT id, notificacion, publicado FROM datos", null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

    }

    public void moverPrincipio() {
        cursor.moveToFirst();
    }

    public String[] obtenerDato(int id) {
        cursor = db.rawQuery("SELECT id, notificacion, publicado FROM datos", null);
        cursor.moveToPosition(id);

        String[] msg = {cursor.getInt(0) + "" , cursor.getString(1) , cursor.getString(2)};
        return msg;
    }

    public int numregistros(){
        return cursor.getCount();
    }

    public void publicado(int registro) {
        // Cambiar el registro en el que esté el cursor a 'publicado' (Poner Y)
        db.execSQL("UPDATE datos SET publicado = 'Y' WHERE id = " + registro + "");
    }

    public void insertarDato(String dato) {
        db.execSQL("INSERT INTO datos (id,notificacion,publicado) VALUES ('" + id + "' , '" + dato + "' , 'N')");
        id++;
    }

    public void vertodo() {
        cursor = db.rawQuery("SELECT id, notificacion, publicado FROM datos", null);

        if (cursor != null) {
            cursor.moveToFirst();

            do {
                Log.d("DATOS", "ID: " + cursor.getInt(0) + " - Texto: " + cursor.getString(1) + " - Publicado? " + cursor.getString(2));
            } while (cursor.moveToNext());
        }
    }
}


