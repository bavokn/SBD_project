package DAO;

import Model.DoctorModel;
import Model.UserModel;


import java.sql.SQLException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AdminDAO extends DAO {
//2 - Search user records indicating part of the name (using autocomplete mechanism)
//4 - Export / import the user form to / from an XML document (including photo and scheduled queries)
//6 - List, by specialty, total queries rescheduled last week ???????????
//7 - List, by specialty, the total number of consultations performed, consultations missing and scheduled appointments.
//9 - List for each doctor, in a certain period, the hours spent with missed and canceled appointments

    private static final String INSERT_DOCTOR ="INSERT INTO Doctor VALUES(?,?,?,?,?,?,?,?,?)";
    private static final String UPDATE_DOCTOR ="UPDATE Doctor SET salary=?,nif=?,firstName=?,lastName=?,birthDate=?,photo=?,address=?,specialtyName=? WHERE doctorId = ?";
    private static final String INSERT_USER ="INSERT INTO User VALUES(?,?,?,?,?,?,?,?,?)";
    private static final String UPDATE_USER ="UPDATE User SET nif=?,firstName=?,lastName=?,birthDate=?,photo=?,address=?,gender=? WHERE userId = ?";
    private static final String COUNT_EVERY_SPECIALTY ="select specialtyName,COUNT(*) from Doctor group by specialtyName";
    private static final String SELECT_ABSENCE_MORE_THAN_THREE = "SELECT * from User where absence_counter > 3";
    private static final String SELECT_NAME_AUTOCOMPOLETE = "SELECT * from User where firstName LIKE ?";
    private static final String SPECIALTY_PAST_APPOINTMENTS = "select d.specialtyName,count(*) from Appointment a, Doctor d where a.doctorId = d.DoctorId and a.dateTimeSlot < CURDATE() GROUP BY d.specialtyName";
    private static final String SPECIALTY_SCHEDULED_APPOINTMENTS = "select d.specialtyName,count(*) from Appointment a, Doctor d where a.doctorId = d.DoctorId and a.dateTimeSlot > CURDATE() GROUP BY d.specialtyName";
    private static final String ABSENT_APPOINTMENTS = "select d.specialtyName, count(*) from Appointment a, Doctor d where absence_counter = 1 and d.doctorId = a.doctorId GROUP BY d.specialtyName";
    private static final String DOCTORID_LAST_WEEK_APPOINTMENTS = "SELECT doctorId from Appointment where dateTimeSlot BETWEEN DATE_SUB(NOW(), INTERVAL 7 DAY) AND NOW()";
    private static final String SELECT_NUMBER_CONSULTATIONS_PER_SPECIALTY = "select specialtyName,COUNT(*) from Doctor WHERE doctorId = ? group by specialtyName";


    public void createDoctor(DoctorModel doctorModel)
    throws SQLException {
        Connection connection= this.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(INSERT_DOCTOR);
        preparedStatement.setDouble(1,doctorModel.getSalary());
        preparedStatement.setInt(2,doctorModel.getDoctorId());
        preparedStatement.setLong(3,doctorModel.getNif());
        preparedStatement.setString(4,doctorModel.getFirstName());
        preparedStatement.setString(5,doctorModel.getLastName());
        preparedStatement.setString(6,doctorModel.getBirthDate());
        preparedStatement.setString(7,doctorModel.getPhoto());
        preparedStatement.setString(8,doctorModel.getAddress());
        preparedStatement.setString(9,doctorModel.getSpecialtyName());

        System.out.println("added parameters");

        preparedStatement.execute();
        System.out.println("executed insert doctor");

    }

    public void updateDoctor(DoctorModel doctorModel)
    throws SQLException{
        Connection connection= this.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_DOCTOR);
        preparedStatement.setDouble(1,doctorModel.getSalary());
        preparedStatement.setLong(2,doctorModel.getNif());
        preparedStatement.setString(3,doctorModel.getFirstName());
        preparedStatement.setString(4,doctorModel.getLastName());
        preparedStatement.setString(5,doctorModel.getBirthDate());
        preparedStatement.setString(6,doctorModel.getBirthDate());
        preparedStatement.setString(7,doctorModel.getAddress());
        preparedStatement.setString(8,doctorModel.getSpecialtyName());
        preparedStatement.setInt(9,doctorModel.getDoctorId());

        preparedStatement.executeUpdate();
    }

    public void createUser(UserModel userModel)  {
        System.out.println("in createUser method");
        Connection connection= this.getConnection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(INSERT_USER);

            preparedStatement.setInt(1,userModel.getUserID());
            preparedStatement.setLong(2,userModel.getNif());
            preparedStatement.setString(3,userModel.getFirstName());
            preparedStatement.setString(4,userModel.getLastName());
            preparedStatement.setString(5,userModel.getBirthDate());
            preparedStatement.setString(6,userModel.getPhoto());
            preparedStatement.setString(7,userModel.getAddress());
            preparedStatement.setString(8,userModel.getGender());
            preparedStatement.setInt(9,userModel.getAbsence_counter());

            System.out.println("parameters set");


            preparedStatement.execute();
            System.out.println("user inserted");
        } catch (SQLException e) {
            this.printSQLException(e);
        }
        System.out.println("preparedstament created");

    }

    public void updateUser(UserModel userModel)
    throws SQLException{
        Connection connection= this.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER);

        preparedStatement.setLong(1,userModel.getNif());
        preparedStatement.setString(2,userModel.getFirstName());
        preparedStatement.setString(3,userModel.getLastName());
        preparedStatement.setString(4,userModel.getBirthDate());
        preparedStatement.setString(5,userModel.getPhoto());
        preparedStatement.setString(6,userModel.getAddress());
        preparedStatement.setString(7,userModel.getGender());
        preparedStatement.setInt(8,userModel.getUserID());

        preparedStatement.executeUpdate();
    }

    public HashMap<String, Integer> countEverySpecialty() throws SQLException
    {
        HashMap<String, Integer> map = new HashMap<>();
        Connection connection = this.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(COUNT_EVERY_SPECIALTY);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()){
            map.put(resultSet.getString("specialtyName"),resultSet.getInt("COUNT(*)"));
        }
        return map;
    }

    public List<UserModel> usersAbsent() throws SQLException
    {
        List<UserModel> models = new ArrayList<>();
        Connection connection = this.getConnection();
        PreparedStatement preparedStatement= connection.prepareStatement(SELECT_ABSENCE_MORE_THAN_THREE);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next())
        {
             int userID = rs.getInt("userId");
             long nif = rs.getLong("nif");
             String firstName = rs.getString("firstName");
             String lastName = rs.getString("lastName");
             String birthDate = rs.getString("birthDate");
             String photo = rs.getString("photo");
             String address = rs.getString("address");
             String gender = rs.getString("gender");
             int absence_counter = rs.getInt("absence_counter");


            models.add(new UserModel(userID,nif,firstName,lastName,birthDate,photo,address,gender,absence_counter));
        }

        return models;
    }

    public List<UserModel> getData(String query) throws SQLException {

        List <UserModel> models = new ArrayList<>();
        Connection connection = this.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_NAME_AUTOCOMPOLETE);
        preparedStatement.setString(1,query + "%");
        ResultSet rs = preparedStatement.executeQuery();

        while (rs.next()){
            int userId = rs.getInt("userId");
            long nif = rs.getLong("nif");
            String firstName = rs.getString("firstName");
            String lastName = rs.getString("lastName");
            String birthDate = rs.getString("birthDate");
            String photo = rs.getString("photo");
            String address = rs.getString("address");
            String gender = rs.getString("gender");

            models.add(new UserModel(userId,nif,firstName,lastName,birthDate,photo,address,gender));
        }

        return models;
    }

    public HashMap<String, Integer> specialtyPastAppointments() throws SQLException
    {
        HashMap<String, Integer> map = new HashMap<>();
        Connection connection = this.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(SPECIALTY_PAST_APPOINTMENTS);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()){
            map.put(resultSet.getString("specialtyName"),resultSet.getInt("COUNT(*)"));
        }
        return map;
    }

    public HashMap<String, Integer> specialtyScheduledAppointments() throws SQLException
    {
        HashMap<String, Integer> map = new HashMap<>();
        Connection connection = this.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(SPECIALTY_SCHEDULED_APPOINTMENTS);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()){
            map.put(resultSet.getString("specialtyName"),resultSet.getInt("COUNT(*)"));
        }
        return map;
    }

    public HashMap<String, Integer> absentAppointments() throws SQLException
    {
        HashMap<String, Integer> map = new HashMap<>();
        Connection connection = this.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(ABSENT_APPOINTMENTS);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()){
            map.put(resultSet.getString("specialtyName"),resultSet.getInt("COUNT(*)"));
        }
        return map;
    }

//    //List, by specialty, the total number of consultations performed,
//    public HashMap<String,Integer> consultationsPerSpecialty()
//    throws SQLException{
//        Connection connection = this.getConnection();
//        PreparedStatement preparedStatement = connection.prepareStatement(DOCTORID_LAST_WEEK_APPOINTMENTS);
//        ResultSet rs = preparedStatement.executeQuery();
//    }
}
