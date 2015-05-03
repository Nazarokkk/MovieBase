package model;


public class Crew {

    private String name;
    private double id;
    private String imageURL;
    private String job;
    private String department;

    public Crew(){};

    public Crew(String name, double id, String imageURL, String job, String department) {
        this.name = name;
        this.id = id;
        this.imageURL = imageURL;
        this.job = job;
        this.department = department;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
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

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }
}
