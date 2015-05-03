package model;

public class Movie {
    private String title, imageURL;
    private String year;
    private double rating;
    private double popularity;
    private int id;

    public Movie() {
    }

    public Movie(String name, String imageURL, String year, double rating,
                 double popularity, int id) {
        this.title = name;
        this.imageURL = imageURL;
        this.year = year;
        this.rating = rating;
        this.popularity = popularity;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public double getPopularity() {
        return Math.round(popularity);
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
