package se.academy.asgeirr;

public class Person {
    private String image;
    private String firstName;
    private String lastName;
    private boolean identified;
    private boolean female;

    public Person(String image, String firstName, String lastName, boolean female) {
        this.image = image;
        this.firstName = firstName;
        this.lastName = lastName;
        this.identified = false;
        this.female = female;
    }

    //region Getters and Setters


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean isIdentified() {
        return identified;
    }

    public void setIdentified(boolean identified) {
        this.identified = identified;
    }

    public boolean isFemale() {
        return female;
    }

    public void setFemale(boolean female) {
        this.female = female;
    }
    //endregion
}
