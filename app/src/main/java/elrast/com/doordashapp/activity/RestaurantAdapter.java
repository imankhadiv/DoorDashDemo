package elrast.com.doordashapp.activity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import elrast.com.doordashapp.R;
import elrast.com.doordashapp.model.Restaurant;

/**
 * Created by iman on 10/13/17.
 */

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder> {
    public interface OnFavoriteRestaurantListener {
        void onStarImagePressed(int position);
    }

    private ArrayList<Restaurant> restaurants;
    private Context context;
    private OnFavoriteRestaurantListener onFavoriteRestaurantListener;

    RestaurantAdapter(ArrayList<Restaurant> restaurants) {
        this.restaurants = restaurants;
    }

    @Override
    public RestaurantViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        onFavoriteRestaurantListener = (OnFavoriteRestaurantListener) context;
        View itemView = LayoutInflater.from(context).inflate(R.layout.restaurant_list_item, parent, false);
        return new RestaurantViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RestaurantViewHolder holder, int position) {
        holder.bind(restaurants.get(position), position);
    }

    @Override
    public int getItemCount() {
        return restaurants.size();
    }

    class RestaurantViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name;
        TextView description;
        TextView status;
        ImageView coverImage;
        ImageView starImage;
        int position;

        RestaurantViewHolder(final View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            description = (TextView) itemView.findViewById(R.id.description);
            status = (TextView) itemView.findViewById(R.id.status);
            coverImage = (ImageView) itemView.findViewById(R.id.coverImage);
            starImage = (ImageView) itemView.findViewById(R.id.starImg);
            starImage.setOnClickListener(this);


        }

        void bind(Restaurant restaurant, int position) {
            this.position = position;
            name.setText(restaurant.getName());
            description.setText(restaurant.getDescription());
            status.setText(restaurant.getStatus());
            Picasso.with(context).load(restaurant.getCoverImgUrl()).into(coverImage);
            starImage.setActivated(restaurant.isFavorite());

        }

        @Override
        public void onClick(View v) {
            starImage.setActivated(!starImage.isActivated());
            if (context instanceof OnFavoriteRestaurantListener) {
                onFavoriteRestaurantListener.onStarImagePressed(position);
            }
        }
    }
}
