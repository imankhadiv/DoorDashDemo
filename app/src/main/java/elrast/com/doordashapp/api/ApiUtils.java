package elrast.com.doordashapp.api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import elrast.com.doordashapp.model.Restaurant;


public class ApiUtils {

    public static ArrayList<Restaurant> getRestaurantListFromJson(final String json) {

        final String ID = "id";
        final String NAME = "name";
        final String DESCRIPTION = "description";
        final String COVER_IMAGE_URL = "cover_img_url";
        final String STATUS = "status";

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
