package elrast.com.doordashapp.api;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import elrast.com.doordashapp.model.Restaurant;

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

    public static ArrayList<Restaurant> getRestaurantListFromJson(String json) {
        ArrayList<Restaurant> books = new ArrayList<>();

        final String ID = "id";
        final String NAME = "name";
        final String DESCRIPTION = "description";
        final String COVER_IMAGE_URL = "cover_img_url";
        final String STATUS = "status";
        final String DELIVERY_FEE = "delivery_fee";

        ArrayList<Restaurant> restaurantList = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Restaurant restaurant =
                        new Restaurant(jsonObject.getString(ID),
                                jsonObject.getString(NAME),
                                jsonObject.getString(DESCRIPTION),
                                jsonObject.getString(COVER_IMAGE_URL),
                                jsonObject.getString(STATUS));
                restaurantList.add(restaurant);
            }
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        return restaurantList;
    }
}
