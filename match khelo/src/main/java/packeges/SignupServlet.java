package packeges;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.Attributes;
import javax.naming.NamingException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/SignupServlet")
public class SignupServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        // Retrieve form data
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String state = request.getParameter("state");
        String district = request.getParameter("district");
        String game = request.getParameter("game");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("pass");

        // Check if password and confirm password match
        if (!password.equals(confirmPassword)) {
            out.println("Passwords do not match!");
            return;
        }

        // Validate email format
        if (!isValidEmail(email)) {
            out.println("Invalid email format!");
            return;
        }

        // Validate email domain
        if (!hasMXRecord(email)) {
            out.println("Invalid email domain!");
            return;
        }

        // Verify email using SMTP
        if (!verifyEmailSMTP(email)) {
            out.println("Email address does not exist!");
            return;
        }

        // Database connection
        String url = "jdbc:mysql://localhost:3306/college";
        String user = "root";
        String dbPassword = "";

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish connection
            conn = DriverManager.getConnection(url, user, dbPassword);

            // SQL query to insert data into users table
            String sql = "INSERT INTO userinformation (name, email, state, district, game, password) VALUES (?, ?, ?, ?, ?, ?)";

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setString(2, email);
            pstmt.setString(3, state);
            pstmt.setString(4, district);
            pstmt.setString(5, game);
            pstmt.setString(6, password);

            // Execute update
            int rows = pstmt.executeUpdate();

            if (rows > 0) {
                request.getSession().setAttribute("signupSuccess", true);
                response.sendRedirect("index.jsp");
            } else {
                out.println("Error occurred during sign up.");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            out.println("Database connection or query error: " + e.getMessage());
        } finally {
            // Close resources
            try {
                if (pstmt != null)
                    pstmt.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Method to validate email format
    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    // Method to check if the domain has MX records
    private boolean hasMXRecord(String email) {
        String domain = email.substring(email.indexOf("@") + 1);
        Hashtable<String, String> env = new Hashtable<>();
        env.put("java.naming.factory.initial", "com.sun.jndi.dns.DnsContextFactory");

        try {
            DirContext ictx = new InitialDirContext(env);
            Attributes attrs = ictx.getAttributes(domain, new String[]{"MX"});
            return attrs.get("MX") != null;
        } catch (NamingException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to verify email existence using SMTP
    private boolean verifyEmailSMTP(String email) {
        String domain = email.substring(email.indexOf("@") + 1);
        try {
            Socket socket = new Socket("smtp." + domain, 25);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            // Read initial response from the server
            reader.readLine();
            // Say HELO
            writer.write("HELO example.com\r\n");
            writer.flush();
            reader.readLine();
            // Set MAIL FROM
            writer.write("MAIL FROM:<your-email@example.com>\r\n");
            writer.flush();
            reader.readLine();
            // Set RCPT TO
            writer.write("RCPT TO:<" + email + ">\r\n");
            writer.flush();
            String response = reader.readLine();

            // Close connection
            writer.write("QUIT\r\n");
            writer.flush();
            socket.close();

            // Check if the response is positive
            return response.contains("250");
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
