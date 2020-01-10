<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="DAO.AdminDAO"%>
<%@ page import="Model.UserModel" %>
<%@ page import="java.sql.SQLException" %>
<%
    AdminDAO db = new AdminDAO();

    String query = request.getParameter("autocomplete");

    List<UserModel> models = null;
    try {
        models = db.getData(query);
    } catch (SQLException e) {
        request.setAttribute("error", "oop something went wrong...");
    }

    assert models != null;
    for (UserModel m : models) {
        System.out.println(m.getFirstName());
    }

    request.setAttribute("autocompleteModels", models);
    request.getRequestDispatcher("/admin.jsp").forward(request,response);
%>