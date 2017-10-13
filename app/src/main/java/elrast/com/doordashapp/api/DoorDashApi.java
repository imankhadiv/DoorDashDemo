package elrast.com.doordashapp.api;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by iman on 10/13/17.
 */

public class DoorDashApi {

    private static final String TAG = DoorDashApi.class.getSimpleName();
    private static final String BASE_API_URL = "https://api.doordash.com/v2/restaurant/";
    private static final String LATITUDE = "lat";
    private static final String LONGITUDE = "lng";

    private DoorDashApi() {
    }

    public static URL buildUrl(String lat, String lng) {

        URL url = null;
        Uri uri = Uri.parse(BASE_API_URL).buildUpon().appendQueryParameter(LATITUDE, lat)
                .appendQueryParameter(LONGITUDE, lng)
                .build();
        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static String getJsonString(URL url) throws IOException {

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        try {
            InputStream inputStream = connection.getInputStream();
            Scanner scanner = new Scanner(inputStream);
            scanner.useDelimiter("\\A");
            boolean hasData = scanner.hasNext();
            if (hasData) {
                return scanner.next();

            } else {
                return null;
            }
        } catch (Exception ex) {
            Log.e(TAG, ex.toString());
            return null;
        } finally {
            connection.disconnect();
        }
    }

}
