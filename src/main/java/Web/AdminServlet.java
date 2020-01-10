package Web;

import DAO.AdminDAO;
import DAO.XMLWriter;
import Model.DoctorModel;
import Model.UserModel;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import static Web.NullCoalescing.coalesce;

@WebServlet(urlPatterns = "/admin/*")
public class AdminServlet extends HttpServlet{

    private AdminDAO adminDAO;

    @Override
    public void init() throws ServletException {
        adminDAO = new AdminDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request,response);
        System.out.println("in post of admin servlet");
        String[] uri = request.getRequestURI().split("/");
        String action = uri[uri.length - 1];
        System.out.println(action);
        try {
            switch (action){
                case  "insertOrUpdateDoctor":
                    if (request.getParameter("insert") != null){
                        insertDoctor(request,response);
                    }else if (request.getParameter("save") != null){
                        saveUser(request,response);
                    }else{
                        updateDoctor(request,response);
                    }
                    break;
                case "insertOrUpdatetUser":
                    if (request.getParameter("insert") != null){
                        insertUser(request,response);
                    } else if (request.getParameter("save") != null){
                        saveUser(request,response);
                    }
                    else if (request.getParameter("update") != null){
                        updateUser(request,response);
                    }
                    break;
            }
        } catch (SQLException e) {
            request.setAttribute("error", e.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher("/admin.jsp");
            dispatcher.forward(request, response);
        }
        catch (NumberFormatException e){
            request.setAttribute("error", "invalid fields");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/admin.jsp");
            dispatcher.forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("in get of admin servlet");
        String[] uri = request.getRequestURI().split("/");
        String action = uri[uri.length - 1];
        System.out.println(action);

        try {
            switch (action){
                case "absentUsers":
                    getAbsentUsers(request,response);
                    break;
                case "specialtyDoctors":
                    countEverySpecial(request,response);
                    break;
                case "specialtyPastAppointments":
                    specialtyPastAppointments(request,response);
                    break;
                case "specialtyScheduledAppointments":
                    specialtyScheduledAppointments(request,response);
                    break;
                case "absentAppointments":
                    absentAppointments(request,response);
                    break;
            }
        } catch (SQLException e){
            request.setAttribute("error", e.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher("/admin.jsp");
            dispatcher.forward(request, response);
        } catch (NumberFormatException e ){
            request.setAttribute("error", "invalid fields");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/admin.jsp");
            dispatcher.forward(request, response);
        }
    }

    public void insertDoctor(HttpServletRequest request, HttpServletResponse response)
        throws SQLException, IOException, ServletException{

         double salary =  coalesce(Double.parseDouble(request.getParameter("salary")),0.0);
         int doctorId = coalesce(Integer.parseInt(request.getParameter("doctorId")),0);
         long nif = coalesce(Long.parseLong(request.getParameter("nif")), 0L);
         String firstName = coalesce(request.getParameter("firstName"),"");
         String lastName = coalesce(request.getParameter("lastName"),"");
         String birthDate = coalesce(request.getParameter("birthDate"),"0000-00-00");
         String photo = coalesce(request.getParameter("photo"),"No photo");
         String address = coalesce(request.getParameter("address"),"No address");
         String specialtyName = coalesce(request.getParameter("specialtyName"),"");

        DoctorModel model = new DoctorModel(salary,doctorId,nif,firstName,lastName,birthDate,photo,address,specialtyName);

        adminDAO.createDoctor(model);
        request.getRequestDispatcher("/admin.jsp").forward(request,response);
    }
    public void updateDoctor(HttpServletRequest request, HttpServletResponse response)
        throws SQLException, IOException, ServletException{

        double salary = Double.parseDouble(request.getParameter("salary"));
        int doctorId = Integer.parseInt(request.getParameter("doctorId"));
        long nif = Long.parseLong(request.getParameter("nif"));
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String birthDate = request.getParameter("birthDate");
        String photo = request.getParameter("photo");
        String address = request.getParameter("address");
        String specialtyName = request.getParameter("specialtyName");

        DoctorModel model = new DoctorModel(salary,doctorId,nif,firstName,lastName,birthDate,photo,address,specialtyName);

        adminDAO.updateDoctor(model);
        request.getRequestDispatcher("/admin.jsp").forward(request,response);
    }
    
    public void insertUser(HttpServletRequest request, HttpServletResponse response)
        throws SQLException, IOException, ServletException{
        int userId = Integer.parseInt(request.getParameter("userId"));
        long nif = Long.parseLong(request.getParameter("nif"));
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String birthDate = request.getParameter("birthDate");
        String photo = request.getParameter("photo");
        String address = request.getParameter("address");
        String gender = request.getParameter("gender");

        UserModel model = new UserModel(userId,nif,firstName,lastName,birthDate,photo,address,gender);
        System.out.println("user created");

        adminDAO.createUser(model);
        request.getRequestDispatcher("/admin.jsp").forward(request,response);
    }
    
    public void updateUser(HttpServletRequest request, HttpServletResponse response)
    throws SQLException, IOException, ServletException{
        int userId = Integer.parseInt(request.getParameter("userId"));
        long nif = Long.parseLong(request.getParameter("nif"));
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String birthDate = request.getParameter("birthDate");
        String photo = request.getParameter("photo");
        String address = request.getParameter("address");
        String gender = request.getParameter("gender");

        UserModel model = new UserModel(userId,nif,firstName,lastName,birthDate,photo,address,gender);

        adminDAO.updateUser(model);
        request.getRequestDispatcher("/admin.jsp").forward(request,response);
    }

    public void getAbsentUsers(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException{
        List<UserModel> models;
        models = adminDAO.usersAbsent();
        request.setAttribute("users", models);
        request.getRequestDispatcher("/admin.jsp").forward(request,response);
    }

    public void countEverySpecial(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException
    {
        HashMap<String,Integer> map;
        map = adminDAO.countEverySpecialty();
        request.setAttribute("specialtymap",map);
        request.getRequestDispatcher("/admin.jsp").forward(request,response);
    }

    public void saveUser(HttpServletRequest request, HttpServletResponse response) throws  IOException, ServletException
    {
        int userId = Integer.parseInt(request.getParameter("userId"));
        long nif = Long.parseLong(request.getParameter("nif"));
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String birthDate = request.getParameter("birthDate");
        String photo = request.getParameter("photo");
        String address = request.getParameter("address");
        String gender = request.getParameter("gender");

        UserModel model = new UserModel(userId,nif,firstName,lastName,birthDate,photo,address,gender);
        System.out.println("created model, ready to write XML");
        XMLWriter.UserToXML(model);
    }

    public void specialtyPastAppointments(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException
    {
        HashMap<String,Integer> map;
        map = adminDAO.specialtyPastAppointments();
        map.forEach((key, value) -> System.out.println(value));
        request.setAttribute("specialtyPasAppointmentsmap",map);
        request.getRequestDispatcher("/admin.jsp").forward(request,response);
    }

    public void specialtyScheduledAppointments(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException
    {
        HashMap<String,Integer> map;
        map = adminDAO.specialtyScheduledAppointments();
        map.forEach((key, value) -> System.out.println(value));
        request.setAttribute("specialtyScheduledAppointmentsmap",map);
        request.getRequestDispatcher("/admin.jsp").forward(request,response);
    }

    public void absentAppointments(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException
    {
        HashMap<String,Integer> map;
        map = adminDAO.absentAppointments();
        map.forEach((key, value) -> System.out.println(value));
        request.setAttribute("absentMap",map);
        request.getRequestDispatcher("/admin.jsp").forward(request,response);
    }
}
