package DAO;

import Model.AppointmentModel;
import Model.TimeFrameModel;
import Model.UserModel;

import java.util.Date;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DoctorDAO extends DAO  {
    private static final String INSERT_DOCTOR_SQL = "INSERT INTO Doctor" + "  (name, email, country) VALUES "
            + " (?, ?, ?);";

    private static final String SELECT_DOCTOR_BY_ID = "select id,name,email,country from users where id =?";
    private static final String SELECT_TIMEFRAMES = "select * from TimeFrame where doctorId = ?";
    private static final String GET_ALL_APPOINTMENTS = "SELECT * FROM Appointment  WHERE doctorId = ?";
    private static final String SELECT_DOCTOR_NAME = "SELECT firstName,LastName FROM User where userId = ?";
    private static final String SELECT_USER_NAME = "SELECT firstName,LastName FROM User where userId = ?";
    private static final String INSERT_TIMEFRAME = "INSERT INTO TimeFrame VALUES (?,?,?)";
    private static final String UPDATE_TIMEFRAME = "UPDATE TimeFrame SET beginDt = ? , endDt = ? where  beginDt = ? and endDt = ? and doctorId = ?";
    private static final String MARK_ABSENT_USER = "UPDATE User SET absence_counter = absence_counter + 1 where userId = ?";
    private static final String MARK_ABSENT_TIMEFRAME = "UPDATE Appointment SET absence_counter = 1 where unix_timestamp(dateTimeSlot) = unix_timestamp(?)" +
            "and userId = ? and doctorId = ?";


    private static final String DELETE_DOCTOR_SQL = "delete from users where id = ?;";
    private static final String UPDATE_DOCTOR_SQL = "update users set name = ?,email= ?, country =? where id = ?;";

    public DoctorDAO(){ }

    public List<TimeFrameModel> getTimeFrames(int doctorId) throws SQLException {
        List<TimeFrameModel> timeFrameModels =  new ArrayList<>();
        Connection connection = this.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_TIMEFRAMES);
        preparedStatement.setInt(1,doctorId);

        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()){
            java.util.Date beginDt;
            java.util.Date endDt;
            Timestamp begindtTimestamp = rs.getTimestamp("beginDt");
            Timestamp endDtTimeStamp = rs.getTimestamp("endDt");

            beginDt = new Date(begindtTimestamp.getTime());
            endDt = new Date(endDtTimeStamp.getTime());

            timeFrameModels.add(new TimeFrameModel(beginDt,endDt,doctorId));
        }

        return timeFrameModels;
    }

    public List<AppointmentModel> getAllAppointments(int doctorId) throws SQLException
    {
        List<AppointmentModel> appointments =  new ArrayList<>();

        Connection connection = this.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_APPOINTMENTS);
        preparedStatement.setInt(1,doctorId);
        ResultSet rs = preparedStatement.executeQuery();

        while (rs.next()) {
            java.util.Date date;
            Timestamp timestamp = rs.getTimestamp("dateTimeSlot");
            date = new java.util.Date(timestamp.getTime());
            int userId =  rs.getInt("userId");

            appointments.add(new AppointmentModel(date,userId,doctorId,getUserName(userId)));
        }
        return appointments;
    }

    public String getUserName(int userId) throws SQLException
    {
        String firstName = "";
        String lastName = "";

        Connection connection = this.getConnection();
        ArrayList<String> names = new ArrayList<>();
        PreparedStatement getDoctor = connection.prepareStatement(SELECT_USER_NAME);
        getDoctor.setDouble(1, userId);
        ResultSet doctorName = getDoctor.executeQuery();
        while (doctorName.next()){
            firstName = doctorName.getString("firstName");
            lastName = doctorName.getString("lastName");
        }

        return firstName + " " + lastName;
    }

    public void updateTimeFrame(String newBeginDt,String newEndDt,String begindt,String endDt, int doctorId) throws SQLException{
        Connection connection = this.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_TIMEFRAME);
        preparedStatement.setString(1,newBeginDt);
        preparedStatement.setString(2,newEndDt);
        preparedStatement.setString(3,begindt);
        preparedStatement.setString(4,endDt);
        preparedStatement.setInt(5,doctorId);
        preparedStatement.executeUpdate();
    }

    public void insertTimeFrame(String begindt,String endDt, int doctorId) throws SQLException
    {
        Connection connection = this.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(INSERT_TIMEFRAME);
        preparedStatement.setString(1,begindt);
        preparedStatement.setString(2,endDt);
        preparedStatement.setInt(3,doctorId);
        preparedStatement.execute();
    }

    //TODO add absence of users

    public void markAbsent(String date, int userId, int doctorId) throws SQLException
    {
        Connection connection = this.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(MARK_ABSENT_USER);
        preparedStatement.setInt(1,userId);
        preparedStatement.execute();
        preparedStatement = connection.prepareStatement(MARK_ABSENT_TIMEFRAME);
        preparedStatement.setString(1,date);
        preparedStatement.setInt(2,userId);
        preparedStatement.setInt(3,doctorId);
        preparedStatement.execute();
    }

}
