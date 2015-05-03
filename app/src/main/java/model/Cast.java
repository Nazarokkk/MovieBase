package model;

public class Cast {

    private String name;
    private double id;
    private String imageURL;
    private String character;

    public Cast(){}

    public Cast(String name, double id, String imageURL, String character) {
        this.name = name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
