package Model;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DoctorModel {
    private static final String DATE_FORMAT = "yyyy-MM-dd";

    private double salary;
    public int doctorId;
    public long nif;
    public String firstName;
    public String lastName;
    public String birthDate;
    public String photo;
    public String address;
    public String specialtyName;

    public DoctorModel(double salary, int doctorId, long nif, String firstName, String lastName, String birthDate, String photo, String address, String specialtyName) {

        this.salary = salary;
        this.doctorId = doctorId;
        this.nif = nif;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.photo = photo;
        this.address = address;
        this.specialtyName = specialtyName;
    }

    public double getSalary() {
        return salary;
    }

    public int getDoctorId() {
        return doctorId;
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

    public String getSpecialtyName() {
        return specialtyName;
    }
}
