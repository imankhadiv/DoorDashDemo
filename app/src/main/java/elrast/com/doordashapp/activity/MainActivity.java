package elrast.com.doordashapp.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;

import elrast.com.doordashapp.R;
import elrast.com.doordashapp.api.DoorDashApi;

public class MainActivity extends AppCompatActivity {

    public static final String DOORDASH_LAT = "37.422740";
    public static final String DOORDASH_LNG = "-122.139956";
    private TextView resultTextView;
    private ProgressBar progressBarView;

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
        resultTextView = (TextView) contentMainView.findViewById(R.id.textView);
        progressBarView = (ProgressBar) contentMainView.findViewById(R.id.progressBar);

        URL url = DoorDashApi.buildUrl(DOORDASH_LAT, DOORDASH_LNG);

        new RestaurantQueryTask().execute(url);
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
                //restaurantList = ApiUtil.getRestaurantListFromJson(json);
                //TextView textView = (TextView) findViewById(R.id.textView);
                progressBarView.setVisibility(View.INVISIBLE);
                resultTextView.setText(json);

                //textView.setText(restaurantList.toString());
                // new FavoriteRestaurantsTask(RETRIEVE).execute();
            }
        }
    }
}
