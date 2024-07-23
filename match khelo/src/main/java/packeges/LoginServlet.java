package packeges;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        // Retrieve form data
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // Database connection
        String url = "jdbc:mysql://localhost:3306/college";
        String user = "root";
        String dbPassword = "";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish connection
            conn = DriverManager.getConnection(url, user, dbPassword);

            // SQL query to check if the user exists with the given email and password
            String sql = "SELECT * FROM userinformation WHERE email = ? AND password = ?";

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, email);
            pstmt.setString(2, password);

            // Execute query
            rs = pstmt.executeQuery();

            if (rs.next()) {
                // Retrieve user information from the ResultSet
                String name = rs.getString("name");
                String gameState = rs.getString("game");
                String district = rs.getString("district");
                String state = rs.getString("state");

                // Create a session
                HttpSession session = request.getSession();
                
                // Set session attributes with user information
                session.setAttribute("name", name);
                session.setAttribute("gameState", gameState);
                session.setAttribute("district", district);
                session.setAttribute("email", email);
                session.setAttribute("state", state);          
            
                // Forward to DisplayUserData servlet to display user data
                request.getRequestDispatcher("DisplayUser.jsp").forward(request, response);
            } else {
                out.println("<h3 style=\"color: red\">Invalid email or password. Please try again.</h3>");
            }

        } catch (Exception e) {
            e.printStackTrace();
            out.println("Database connection or query error: " + e.getMessage());
        } finally {
            // Close resources
            try {
                if (rs != null)
                    rs.close();
                if (pstmt != null)
                    pstmt.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
