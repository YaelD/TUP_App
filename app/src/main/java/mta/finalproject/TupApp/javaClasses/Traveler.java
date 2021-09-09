package mta.finalproject.TupApp.javaClasses;

//import engine.planTrip.RouteTrip;

import java.io.Serializable;
import java.util.regex.Pattern;

public class Traveler {
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String password;

    public Traveler(String firstName, String lastName, String emailAddress, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.password = password;
    }

    public Traveler(Traveler other)  {
        setFirstName(other.firstName);
        setLastName(other.lastName);
        setEmailAddress(other.emailAddress);
        setPassword(other.password);
    }

    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public String getEmailAddress() {
        return emailAddress;
    }
    public String getPassword() {
        return password;
    }

    public void setFirstName(String firstName)  {
        this.firstName = firstName.trim();
    }

    public void setLastName(String lastName) {
        this.lastName = lastName.trim();
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public void setPassword(String password)  {
        this.password = password;
    }


    @Override
    public String toString() {
        return "Traveler{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

}
