package elrast.com.doordashapp.repository;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import java.util.TreeSet;


public class DatabaseUtils {

    private static final String TAG = DatabaseUtils.class.getSimpleName();
    private SQLiteDatabase database;

    public DatabaseUtils(SQLiteDatabase database) {
        this.database = database;
    }

    public void insertAFavoriteRestaurant(int restaurantId) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(DoorDashDatabaseHelper.COLUMN_RESTAURANT_ID, restaurantId);
        try {
            database.insert(DoorDashDatabaseHelper.TABLE_FAVORITE, null, contentValues);
        } catch (SQLiteException ex) {
            Log.e(TAG, ex.getMessage());
        }
    }
    public void deleteAFavoriteRestaurant(int restaurantId) {
        try {
            database.delete(DoorDashDatabaseHelper.TABLE_FAVORITE, DoorDashDatabaseHelper.COLUMN_RESTAURANT_ID + " = ?", new String[]{String.valueOf(restaurantId)});
        } catch (SQLiteException ex) {
            Log.e(TAG, ex.getMessage());
        }
    }

    public TreeSet<Integer> findFavoritesRestaurants() {
        TreeSet<Integer> restaurantsIds = new TreeSet<>();
        Cursor cursor = null;
        try {
            cursor = database.query(DoorDashDatabaseHelper.TABLE_FAVORITE, new String[]{DoorDashDatabaseHelper.COLUMN_RESTAURANT_ID}, null, null, null, null, null);

            while (cursor.moveToNext()) {
                restaurantsIds.add(cursor.getInt(0));
            }
        } catch (SQLiteException ex) {
            Log.e(TAG, ex.getMessage());
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return restaurantsIds;
    }
}
