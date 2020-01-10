package Model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AppointmentModel {

    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private String datetime;
    private int userId;
    private int doctorId;
    private String name;
    private int absent;

    public AppointmentModel(Date datetime, int userId, int doctorId, String name) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);
        this.datetime = simpleDateFormat.format(datetime);
        this.userId = userId;
        this.doctorId = doctorId;
        this.name = name;
    }

    public AppointmentModel(String datetime, int userId, int doctorId, String name, int absent) {
        this.datetime = datetime;
        this.userId = userId;
        this.doctorId = doctorId;
        this.name = name;
        this.absent = absent;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public String getName() {
        return name;
    }

    public void setDoctorName(String doctorName) {
        this.name = doctorName;
    }

    public int getAbsent() {
        return absent;
    }
}
