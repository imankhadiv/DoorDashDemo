package elrast.com.doordashapp.api;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

import elrast.com.doordashapp.model.Restaurant;

public class ApiUtilsTest {

    @Test
    public void shouldGetRestaurantListFromJson() throws Exception {
        
        JSONArray restListArray = new JSONArray();
        JSONObject restObject = new JSONObject();
        restObject.put("id", "1");
        restObject.put("name", "Starbucks");
        restObject.put("description", "food");
        restObject.put("cover_img_url", "http://");
        restObject.put("status", "open");
        restListArray.put(restObject);

        ArrayList<Restaurant> restaurants = new ArrayList<>();
        restaurants.add(new Restaurant("1", "Starbucks", "food", "http://", "open"));
        Assert.assertArrayEquals(restaurants.toArray(), ApiUtils.getRestaurantListFromJson(restListArray.toString()).toArray());
    }

}
