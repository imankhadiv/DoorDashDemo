package elrast.com.doordashapp.repository;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.support.test.InstrumentationRegistry;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import java.util.TreeSet;

public class DatabaseUtilsTest {

    private SQLiteDatabase database;
    private DoorDashDatabaseHelper dbHelper;
    private DatabaseUtils databaseUtils;

    @Before
    public void setUp() {

        Context appContext = InstrumentationRegistry.getTargetContext();
        dbHelper = DoorDashDatabaseHelper.getInstance(appContext);
        database = dbHelper.getWritableDatabase();
        databaseUtils = new DatabaseUtils(database);
    }

    @Test
    public void shouldInsertAFavoriteRestaurant() throws Exception {

        SQLiteStatement statement = database.compileStatement("SELECT COUNT(*) FROM favorites");
        long beforeInsertCount = statement.simpleQueryForLong();
        databaseUtils.insertAFavoriteRestaurant(100);
        long afterInsertCount = statement.simpleQueryForLong();
        Assert.assertEquals(1, afterInsertCount - beforeInsertCount);
        databaseUtils.deleteAFavoriteRestaurant(100);

    }

    @Test
    public void deleteAFavoriteRestaurant() throws Exception {

        databaseUtils.insertAFavoriteRestaurant(100);
        SQLiteStatement statement = database.compileStatement("SELECT COUNT(*) FROM favorites where restaurant_id = 100;");
        long afterInsertCount = statement.simpleQueryForLong();
        databaseUtils.deleteAFavoriteRestaurant(100);
        long afterDeleteCount = statement.simpleQueryForLong();
        Assert.assertEquals(1, afterInsertCount - afterDeleteCount);
    }

    @Test
    public void findFavoritesRestaurants() throws Exception {

        databaseUtils.insertAFavoriteRestaurant(100);
        TreeSet<Integer> favorites = databaseUtils.findFavoritesRestaurants();
        databaseUtils.deleteAFavoriteRestaurant(100);

        Assert.assertTrue(!favorites.isEmpty());
    }

    @Test
    public void teardown() {
        database.close();
        dbHelper.close();
    }

}