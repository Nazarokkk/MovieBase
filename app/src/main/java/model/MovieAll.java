package model;

import java.util.ArrayList;

public class MovieAll {
 private String originalTitle;
    private ArrayList<String> genre;
    private ArrayList<String> prod_comp;
    private ArrayList<String> prod_countr;
    private String status;
    private String releaseDate;
    private String description;
    private String imageURL;
    private String budget;
    private String runtime;
    private String revenue;
    private String tagline;

    public MovieAll(){};

    public MovieAll(String originalTitle, ArrayList<String> genre, ArrayList<String> prod_comp, ArrayList<String> prod_countr, String status, String releaseDate, String description, String imageURL, String budget, String runtime, String revenue, String tagline) {
        this.originalTitle = originalTitle;
        this.genre = genre;
        this.prod_comp = prod_comp;
        this.prod_countr = prod_countr;
        this.status = status;
        this.releaseDate = releaseDate;
        this.description = description;
        this.imageURL = imageURL;
        this.budget = budget;
        this.runtime = runtime;
        this.revenue = revenue;
        this.tagline = tagline;
    }

    public ArrayList<String> getProd_comp() {
        return prod_comp;
    }

    public void setProd_comp(ArrayList<String> prod_comp) {
        this.prod_comp = prod_comp;
    }

    public ArrayList<String> getProd_countr() {
        return prod_countr;
    }

    public void setProd_countr(ArrayList<String> prod_countr) {
        this.prod_countr = prod_countr;
    }

    public ArrayList<String> getGenre() {
        return genre;
    }

    public void setGenre(ArrayList<String> genre) {
        this.genre = genre;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        if(tagline.equals(""))
        {
            this.tagline = "Nothing found";
        }
        else this.tagline = tagline;
    }

    public String getRevenue() {
        return revenue;
    }

    public void setRevenue(String revenue) {
        this.revenue = revenue;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
