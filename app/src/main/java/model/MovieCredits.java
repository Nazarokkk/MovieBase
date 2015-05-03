package model;

public class MovieCredits {

    private String movie;
    private double id;
    private String imageURL;
    private String character;

    public MovieCredits(){}

    public MovieCredits(String movie, double id, String imageURL, String character) {
        this.movie = movie;
        this.id = id;
        this.imageURL = imageURL;
        this.character = character;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public String getMovie() {
        return movie;
    }

    public void setMovie(String movie) {
        this.movie = movie;
    }

    public double getId() {
        return id;
    }

    public void setId(double id) {
        this.id = id;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}

