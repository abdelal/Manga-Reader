
package ali.mangareader.dbaccess;
import android.database.sqlite.SQLiteDatabase;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Dao {
    private static Dao instance;
    private final SQLiteDatabase db;

    private Dao(Context context) {
        MyDbHelper helper = new MyDbHelper(context);
        db = helper.getWritableDatabase();
    }

    public static synchronized Dao getSharedInstance(Context context) {
        if (instance == null)
            instance = new Dao(context);
        return instance;
    }

    public void AddMainList(String name, String  Json) {
        ContentValues value = new ContentValues();
        value.put("Name", name);
        value.put("Json", Json);
        db.insert("Jsons", null, value);
    }

    public Cursor getCursor() {
        return db.rawQuery("SELECT * FROM Jsons", null);
    }
    public String getJson() {
        return db.rawQuery("SELECT * FROM Jsons " +
                "           Where Name='Json" , null).getColumnName(1);

    }



}