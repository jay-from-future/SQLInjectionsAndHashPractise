package com.grishalyullin.servlets;

import com.grishalyullin.persistence.JdbcConnection;
import com.grishalyullin.utils.HashHelper;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.Statement;

public class LoginServlet extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(LoginServlet.class.getName());

    private static final String FIND_USER = "SELECT id_user, username, password FROM users WHERE username = usernameParam";

    private JdbcConnection connection = new JdbcConnection();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        try {
            Statement statement = connection.getConnection().createStatement();
            String query = FIND_USER.replace("usernameParam", "'" + username + "'");
            LOG.info("Query : " + query);
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                String idStr = resultSet.getString(1);
                String usernameStr = resultSet.getString(2);
                String pass = resultSet.getString(3);
                if (pass.equals(HashHelper.generateHash(password))) {
                    req.getSession().setAttribute("userId", idStr);
                    req.getSession().setAttribute("userName", username);
                } else {
                    req.setAttribute("errorMessage", "Incorrect password for user: \"" + usernameStr + "\"");
                }
            } else {
                req.setAttribute("errorMessage", "Incorrect username or password. Try again");
            }
        } catch (Exception e) {
            req.setAttribute("errorMessage", e.getMessage());
        }
        getServletContext().getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, resp);
    }
}
