package Model;

import java.text.SimpleDateFormat;
import java.util.Date;
public class UserModel {
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);

    private int userID;
    private long nif;
    private String firstName;
    private String lastName;
    private String birthDate;
    private String photo;
    private String address;
    private String gender;
    private int absence_counter;

    public UserModel(int userID, long nif, String firstName, String lastName, String birthDate, String photo, String address, String gender, int absence_counter) {

        this.userID = userID;
        this.nif = nif;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.photo = photo;
        this.address = address;
        this.gender = gender;
        this.absence_counter = absence_counter;
    }

    public UserModel(int userID, long nif, String firstName, String lastName, String birthDate, String photo, String address, String gender) {
        this.userID = userID;
        this.nif = nif;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.photo = photo;
        this.address = address;
        this.gender = gender;
        this.absence_counter = 0;
    }

    public int getUserID() {
        return userID;
    }

    public long getNif() {
        return nif;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getPhoto() {
        return photo;
    }

    public String getAddress() {
        return address;
    }

    public String getGender() {
        return gender;
    }

    public int getAbsence_counter() {
        return absence_counter;
    }
}
