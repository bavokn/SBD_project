package Model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeFrameModel {

    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private String begindt;
    private String endDt;
    private int doctorId;

    public TimeFrameModel(Date begindt, Date endDt, int doctorId) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);
        this.begindt = simpleDateFormat.format(begindt);
        this.endDt = simpleDateFormat.format(endDt);
        this.doctorId = doctorId;
    }

    public String getBegindt() {
        return begindt;
    }

    public String getEndDt() {
        return endDt;
    }

    public int getDoctorId() {
        return doctorId;
    }
}
