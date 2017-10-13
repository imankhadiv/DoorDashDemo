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
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import elrast.com.doordashapp.R;
import elrast.com.doordashapp.api.DoorDashApi;
import elrast.com.doordashapp.model.Restaurant;

public class MainActivity extends AppCompatActivity implements RestaurantAdapter.OnFavoriteRestaurantListener {

    public static final String DOORDASH_LAT = "37.422740";
    public static final String DOORDASH_LNG = "-122.139956";
    private TextView resultTextView;
    private ProgressBar progressBarView;
    private ArrayList<Restaurant> restaurantList;
    private RecyclerView recyclerView;

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
                progressBarView.setVisibility(View.INVISIBLE);
                restaurantList = DoorDashApi.getRestaurantListFromJson(json);
                RestaurantAdapter restaurantAdapter = new RestaurantAdapter(restaurantList);
                recyclerView.setAdapter(restaurantAdapter);
            }
        }
    }
}
