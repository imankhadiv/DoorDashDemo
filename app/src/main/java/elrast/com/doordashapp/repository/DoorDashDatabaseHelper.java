package elrast.com.doordashapp.repository;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by iman on 10/13/17.
 */

public class DoorDashDatabaseHelper extends SQLiteOpenHelper {


    private static final int DB_VERSION = 3;
    public static final String DB_NAME = "doordash.db";
    public static final String _ID = "_id";
    public static final String COLUMN_RESTAURANT_ID = "restaurant_id";
    public static final String TABLE_FAVORITE = "favorites";
    private static DoorDashDatabaseHelper instance;

    private DoorDashDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + TABLE_FAVORITE + " ("
                + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_RESTAURANT_ID + " INTEGER)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public static synchronized DoorDashDatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DoorDashDatabaseHelper(context);
        }
        return instance;
    }
}
