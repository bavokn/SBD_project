package DAO;

import Model.AppointmentModel;
import java.sql.*;
import java.util.ArrayList;
import java.util.*;

public class AppointmentDAO extends DAO {

    private static final String INSERT_APPOINTMENT_SQL = "INSERT INTO Appointment" + "  (dateTimeSlot, userId, doctorId) VALUES "
            + " (?, ?, ?);";

    private static final String DELETE_APPOINTMENT_SQL = "DELETE FROM Appointment where  unix_timestamp(dateTimeSlot) = unix_timestamp(?)  and userId = ? and doctorId = ?";

    private static final String GET_ALL_APPOINTMENTS = "SELECT * FROM Appointment  WHERE userId = ?";

    private static final String UPDATE_APPOINTMENT = "UPDATE Appointment SET dateTimeSlot = ? where userId = ? and doctorId = ?";

    private static final String SELECT_DOCTOR_NAME = "SELECT firstName,LastName FROM Doctor where doctorId = ?";


    public void makeAppoitement(String date , int userId, int doctorId) throws SQLException
    {
        Connection connection = this.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_APPOINTMENT_SQL);
            preparedStatement.setString(1, date);
            preparedStatement.setInt(2, userId);
            preparedStatement.setInt(3, doctorId);
            preparedStatement.execute();
    }

    public void deleteAppoitement(String date , int userId, int doctorId) throws SQLException
    {
        Connection connection = this.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_APPOINTMENT_SQL);
            preparedStatement.setString(1, date);
            preparedStatement.setInt(2, userId);
            preparedStatement.setInt(3, doctorId);
            System.out.println(preparedStatement);
            preparedStatement.execute();
    }

    public List<AppointmentModel> getAllAppointments(int userId)
    throws SQLException{
        List<AppointmentModel> appointments =  new ArrayList<>();

        Connection connection = this.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_APPOINTMENTS);
        preparedStatement.setInt(1,userId);
        ResultSet rs = preparedStatement.executeQuery();

        while (rs.next()) {
            java.util.Date date;
            Timestamp timestamp = rs.getTimestamp("dateTimeSlot");
            date = new java.util.Date(timestamp.getTime());
            int doctorId =  rs.getInt("doctorId");

            appointments.add(new AppointmentModel(date,userId,doctorId,getDoctorNames(doctorId)));
        }
        return appointments;
    }

    public String getDoctorNames(int doctorId) throws SQLException
    {
        String firstName = "";
        String lastName = "";

        Connection connection = this.getConnection();
            ArrayList<String> names = new ArrayList<>();
            PreparedStatement getDoctor = connection.prepareStatement(SELECT_DOCTOR_NAME);
            getDoctor.setDouble(1, doctorId);
            ResultSet doctorName = getDoctor.executeQuery();
            while (doctorName.next()){
                 firstName = doctorName.getString("firstName");
                 lastName = doctorName.getString("lastName");
            }

        return firstName + " " + lastName;
    }



    public void updateAppointment(String date, double userId, double doctorId)throws SQLException
    {
        Connection connection = this.getConnection();
            PreparedStatement preparedStatement =  connection.prepareStatement(UPDATE_APPOINTMENT);
            preparedStatement.setString(1,date);
            preparedStatement.setDouble(2,userId);
            preparedStatement.setDouble(3,doctorId);

            preparedStatement.executeUpdate();
    }
}
