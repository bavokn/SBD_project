package Web;

import DAO.AppointmentDAO;
import Model.AppointmentModel;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
@WebServlet(urlPatterns = "/appointment/*")
public class AppointmentServlet extends HttpServlet {

    private AppointmentDAO appointmentDAO;

    public void init() { appointmentDAO = new AppointmentDAO(); }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("in get of appointment servlet");
        String[] uri = request.getRequestURI().split("/");
        String action = uri[uri.length - 1];
        System.out.println(action);

        try {
           switch (action){
               case  "getAllAppointments":
                   System.out.println("in getAllAppointments ----------- ");
                   getAllAppointments(request,response);
                   break;
               case "edit":
                   updateAppointment(request,response);
                   break;
               case "delete":
                   deleteAppointment(request,response);
                   break;
               case "new":
                   newAppointment(request,response);

           }
       } catch (SQLException e){
           request.setAttribute("error", e.getMessage());
           RequestDispatcher dispatcher = request.getRequestDispatcher("/appointment.jsp");
           dispatcher.forward(request, response);
       } catch (NumberFormatException e ){
            request.setAttribute("error", "invalid fields");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/appointment.jsp");
            dispatcher.forward(request, response);
        }
    }

        private void getAllAppointments(HttpServletRequest request, HttpServletResponse response)
                throws SQLException, IOException, ServletException {
            List<AppointmentModel> appointments = appointmentDAO.getAllAppointments(Integer.parseInt(request.getParameter("userId")));
            request.setAttribute("appointments", appointments);
            System.out.println("in function ----------- ");
            request.getRequestDispatcher("/appointment.jsp").forward(request,response);
            System.out.println("dispatcher created ----------- ");
        }

    private void deleteAppointment(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        String date = request.getParameter("date");
        int userId = Integer.parseInt(request.getParameter("userId"));
        int doctorId = Integer.parseInt(request.getParameter("doctorId"));
        this.appointmentDAO.deleteAppoitement(date,userId,doctorId);
        List<AppointmentModel> appointments = appointmentDAO.getAllAppointments(Integer.parseInt(request.getParameter("userId")));
        request.setAttribute("appointments", appointments);
        request.getRequestDispatcher("/appointment.jsp").forward(request,response);
    }

    private void updateAppointment(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        String date = request.getParameter("date");
        int userId = Integer.parseInt(request.getParameter("userId"));
        int doctorId = Integer.parseInt(request.getParameter("doctorId"));
        this.appointmentDAO.updateAppointment(date,userId,doctorId);
        List<AppointmentModel> appointments = appointmentDAO.getAllAppointments(Integer.parseInt(request.getParameter("userId")));
        request.setAttribute("appointments", appointments);
        request.getRequestDispatcher("/appointment.jsp").forward(request,response);
    }

    private void newAppointment(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        String date = request.getParameter("date");
        int userId = Integer.parseInt(request.getParameter("userId"));
        int doctorId = Integer.parseInt(request.getParameter("doctorId"));
        this.appointmentDAO.makeAppoitement(date,userId,doctorId);
        List<AppointmentModel> appointments = appointmentDAO.getAllAppointments(Integer.parseInt(request.getParameter("userId")));
        request.setAttribute("appointments", appointments);
        request.getRequestDispatcher("/appointment.jsp").forward(request,response);
    }
}
