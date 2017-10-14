package elrast.com.doordashapp.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

import elrast.com.doordashapp.R;
import elrast.com.doordashapp.api.ApiUtils;
import elrast.com.doordashapp.api.DoorDashApi;
import elrast.com.doordashapp.model.Restaurant;
import elrast.com.doordashapp.repository.DatabaseUtils;
import elrast.com.doordashapp.repository.DoorDashDatabaseHelper;

public class MainActivity extends AppCompatActivity implements RestaurantAdapter.OnFavoriteRestaurantListener {

    public static final String DOORDASH_LAT = "37.422740";
    public static final String DOORDASH_LNG = "-122.139956";
    private static final String UPDATE = "update";
    private static final String DELETE = "delete";
    private static final String RETRIEVE = "retrieve";
    private ProgressBar progressBarView;
    private ArrayList<Restaurant> restaurantList;
    private RecyclerView recyclerView;
    private DoorDashDatabaseHelper databaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.syncState();

        View appBarMain = findViewById(R.id.app_bar_main);
        View contentMainView = appBarMain.findViewById(R.id.content_main);
        progressBarView = (ProgressBar) contentMainView.findViewById(R.id.progressBar);
        recyclerView = (RecyclerView) findViewById(R.id.restaurantRecyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        URL url = DoorDashApi.buildUrl(DOORDASH_LAT, DOORDASH_LNG);
        new RestaurantQueryTask().execute(url);
    }

    @Override
    public void onStarImagePressed(int position) {
        Toast.makeText(this, position + " id " + restaurantList.get(position).getName(), Toast.LENGTH_LONG).show();
        if (restaurantList.get(position).getFavorite()) {
            new FavoriteRestaurantsTask(DELETE).execute(Integer.valueOf(restaurantList.get(position).getId()));
            restaurantList.get(position).setFavorite(false);
        } else {
            new FavoriteRestaurantsTask(UPDATE).execute(Integer.valueOf(restaurantList.get(position).getId()));
            restaurantList.get(position).setFavorite(true);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void defineFavorites(Set<Integer> favoriteIds) {
        for (int i = 0; i < restaurantList.size(); i++) {
            // must be TODO
            if (favoriteIds == null || favoriteIds.size() == 0) {
                return;
            }
            if (favoriteIds.contains(Integer.valueOf(restaurantList.get(i).getId()))) {
                restaurantList.get(i).setFavorite(true);
            }
        }
    }

    private void loadUI() {
        Collections.sort(restaurantList);
        progressBarView.setVisibility(View.INVISIBLE);
        RestaurantAdapter restaurantAdapter = new RestaurantAdapter(restaurantList);
        recyclerView.setAdapter(restaurantAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (databaseHelper != null) {
            databaseHelper.close();
            databaseHelper = null;
        }
    }

    private class RestaurantQueryTask extends AsyncTask<URL, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBarView.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(URL... params) {
            URL url = params[0];
            String jsonResult = null;
            try {
                jsonResult = DoorDashApi.getJsonString(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return jsonResult;
        }

        @Override
        protected void onPostExecute(String json) {
            if (json != null && !json.isEmpty()) {
                restaurantList = ApiUtils.getRestaurantListFromJson(json);
                new FavoriteRestaurantsTask(RETRIEVE).execute();
            }
        }
    }

    private class FavoriteRestaurantsTask extends AsyncTask<Integer, Void, Void> {

        String action;
        Set<Integer> favoriteIds;

        FavoriteRestaurantsTask(String action) {
            this.action = action;
        }

        @Override
        protected Void doInBackground(Integer... params) {
            databaseHelper = DoorDashDatabaseHelper.getInstance(MainActivity.this);
            DatabaseUtils databaseUtils = new DatabaseUtils(databaseHelper.getWritableDatabase());

            switch (action) {

                case RETRIEVE:
                    favoriteIds = databaseUtils.findFavoritesRestaurants();
                    break;
                case DELETE:
                    databaseUtils.deleteAFavoriteRestaurant(params[0]);
                    break;
                default:
                    databaseUtils.insertAFavoriteRestaurant(params[0]);
                    break;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            switch (action) {
                case RETRIEVE:
                    defineFavorites(favoriteIds);
                    loadUI();
                    break;
                default:
                    break;
            }
        }
    }
}
