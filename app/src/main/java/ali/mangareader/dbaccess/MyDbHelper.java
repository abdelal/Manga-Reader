package ali.mangareader.dbaccess;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by master on 20/01/2016.
 */
public class MyDbHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "MyNorthwind";
    private static final int DB_VERSION = 1;
    private String CREATE_TABLE_Jsons = "" +
            "CREATE TABLE Jsons(_ID String NOT NULL PRIMARY KEY " +
            "                       Name TEXT NOT NULL," +
            "                       Value TEXT NOT NULL;";




    public MyDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_Jsons);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE Jsons");


        onCreate(db);
    }
}