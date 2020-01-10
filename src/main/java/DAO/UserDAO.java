package DAO;

import Model.UserModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



public class UserDAO extends DAO  {

    private static final String SELECT_ALL_USERS = "select * from User;";

    public UserDAO(){
    }

    public void makeAppointMent(Double userId){
        
    }

    public void cancelAppointment(Double userId){

    }

    public void UpdateAppointment(Double userId){

    }

    public double listAllAppointements(){
        return 0.0;
    }

    public List<UserModel> selectAllUsers() {
        List<UserModel> users = new ArrayList<>();
        try (Connection connection = this.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USERS);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                //double userID, double nif, String firstName, String lastName, Date birthDate, String photo, String address, String gender
                int userId = rs.getInt("userId");
                long nif =  rs.getLong("nif");
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                String birthDate = rs.getString("birthDate");
                String photo = rs.getString("photo");
                String address = rs.getString("address");
                String gender = rs.getString("gender");

                users.add(new UserModel(userId,nif,firstName,lastName,birthDate,photo,address,gender));
            }
            System.out.println("done adding users");
        } catch (SQLException e) {
            this.printSQLException(e);
        }
        return users;
    }
}
