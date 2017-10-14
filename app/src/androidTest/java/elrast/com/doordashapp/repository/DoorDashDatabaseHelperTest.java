package elrast.com.doordashapp.repository;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;

import org.junit.Test;

import static org.junit.Assert.assertTrue;


public class DoorDashDatabaseHelperTest {

    @Test
    public void shouldCreateDatabase() throws Exception {

        Context appContext = InstrumentationRegistry.getTargetContext();
        DoorDashDatabaseHelper dbHelper = DoorDashDatabaseHelper.getInstance(appContext);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        assertTrue("Database Could not open", database.isOpen());
    }

}