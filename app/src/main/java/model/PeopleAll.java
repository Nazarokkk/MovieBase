package model;

/**
 * Created by Admin on 06.03.15.
 */
public class PeopleAll {

    private double id;
    private String name;
    private String birthday;
    private String placeOfBirth;
    private String biography;
    private String imageURL;

    public PeopleAll(){}


    public PeopleAll(String imageURL, String biography, String placeOfBirth, String birthday, String name, double id) {
        this.imageURL = imageURL;
        this.biography = biography;
        this.placeOfBirth = placeOfBirth;
        this.birthday = birthday;
        this.name = name;
        this.id = id;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public double getId() {
        return id;
    }

    public void setId(double id) {
        this.id = id;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPlaceOfBirth() {
        return placeOfBirth;
    }

    public void setPlaceOfBirth(String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
