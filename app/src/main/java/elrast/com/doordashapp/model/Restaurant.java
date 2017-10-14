package elrast.com.doordashapp.model;

import android.support.annotation.NonNull;

public class Restaurant implements Comparable<Restaurant> {
    private String id;
    private String name;
    private String description;
    private String coverImgUrl;
    private String status;
    private Boolean favorite;


    public Restaurant(String id, String name, String description, String coverImgUrl, String status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.coverImgUrl = coverImgUrl;
        this.status = status;
        this.favorite = false;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getCoverImgUrl() {
        return coverImgUrl;
    }

    public String getStatus() {
        return status;
    }

    public Boolean getFavorite() {
        return favorite;
    }

    public void setFavorite(Boolean favorite) {
        this.favorite = favorite;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Restaurant that = (Restaurant) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null)
            return false;
        if (coverImgUrl != null ? !coverImgUrl.equals(that.coverImgUrl) : that.coverImgUrl != null)
            return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        return favorite != null ? favorite.equals(that.favorite) : that.favorite == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (coverImgUrl != null ? coverImgUrl.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (favorite != null ? favorite.hashCode() : 0);
        return result;
    }

    @Override
    public int compareTo(@NonNull Restaurant o) {
        return -this.getFavorite().compareTo(o.getFavorite());
    }
}
