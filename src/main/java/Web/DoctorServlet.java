package Web;

import DAO.DoctorDAO;
import Model.AppointmentModel;
import Model.TimeFrameModel;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
@WebServlet(urlPatterns = "/doctor/*")
public class DoctorServlet extends HttpServlet {

    private DoctorDAO doctorDAO;

    public void init(){
        doctorDAO = new DoctorDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("in get of doctor servlet");
        String[] uri = request.getRequestURI().split("/");
        String action = uri[uri.length - 1];
        System.out.println(action);

        try {
            switch (action){
                case  "getAllDoctorAppointments":
                    getAllAppointments(request,response);
                    break;
                case "updateTimeFrame":
                    updateTimeFrame(request,response);
                    break;
                case "insertTimeFrame":
                    insertTimeFrame(request,response);
                    break;
                case "markAbsent":
                    markAbsent(request,response);
                    break;
            }
        } catch (SQLException e){
            request.setAttribute("error", e.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher("/doctor.jsp");
            dispatcher.forward(request, response);
        } catch (NumberFormatException e ){
            request.setAttribute("error", "invalid fields");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/doctor.jsp");
            dispatcher.forward(request, response);
        }
    }

    private void getAllAppointments(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        List<AppointmentModel> appointments = doctorDAO.getAllAppointments(Integer.parseInt(request.getParameter("doctorId")));
        request.setAttribute("appointments", appointments);
        List<TimeFrameModel> timesframes = doctorDAO.getTimeFrames(Integer.parseInt(request.getParameter("doctorId")));
        request.setAttribute("timeframes", timesframes);
        request.getRequestDispatcher("/doctor.jsp").forward(request,response);
    }


    private void insertTimeFrame(HttpServletRequest request, HttpServletResponse response)
            throws SQLException,IOException, ServletException{
        String begindt = request.getParameter("beginDt");
        String endDt = request.getParameter("endDt");
        int doctorId =  Integer.parseInt(request.getParameter("doctorId"));

        List<TimeFrameModel> timesframes = doctorDAO.getTimeFrames(Integer.parseInt(request.getParameter("doctorId")));
        request.setAttribute("timeframes", timesframes);

        doctorDAO.insertTimeFrame(begindt,endDt,doctorId);
    }

    private void updateTimeFrame(HttpServletRequest request, HttpServletResponse response)
            throws SQLException,IOException, ServletException{
        String newBegindt = request.getParameter("newBegindt");
        String newEndDt = request.getParameter("newEndDt");
        String begindt = request.getParameter("beginDt");
        String endDt = request.getParameter("endDt");
        int doctorId =  Integer.parseInt(request.getParameter("doctorId"));
        System.out.println(begindt);
        List<TimeFrameModel> timesframes = doctorDAO.getTimeFrames(Integer.parseInt(request.getParameter("doctorId")));
        request.setAttribute("timeframes", timesframes);

        doctorDAO.updateTimeFrame(newBegindt,newEndDt, begindt,endDt,doctorId);
    }

    private void markAbsent(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException
            {
        String date = request.getParameter("date");
        int userId = Integer.parseInt(request.getParameter("userId"));
        int doctorId = Integer.parseInt(request.getParameter("doctorId"));
        doctorDAO.markAbsent(date,userId ,doctorId);
        List<TimeFrameModel> timesframes = doctorDAO.getTimeFrames(Integer.parseInt(request.getParameter("doctorId")));
        request.setAttribute("timeframes", timesframes);
        List<AppointmentModel> appointments = doctorDAO.getAllAppointments(Integer.parseInt(request.getParameter("doctorId")));
        request.setAttribute("appointments", appointments);
        request.getRequestDispatcher("/doctor.jsp").forward(request,response);
    }
}
