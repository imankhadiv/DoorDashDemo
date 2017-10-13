package elrast.com.doordashapp.model;

public class Restaurant {
    private String id;
    private String name;
    private String description;
    private String coverImgUrl;
    private String status;
    private boolean favorite;


    public Restaurant(String id, String name, String description, String coverImgUrl, String status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.coverImgUrl = coverImgUrl;
        this.status = status;
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

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }
}
