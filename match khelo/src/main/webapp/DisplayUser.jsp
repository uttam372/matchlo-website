<%@ page import="java.io.*, java.sql.*, jakarta.servlet.*, jakarta.servlet.http.*, jakarta.servlet.jsp.*, java.util.*" %>
<!DOCTYPE html>
<html>
<head>
    <title>Let's Play</title>
    <link rel="stylesheet" href="./diaplayuser_css.css">
</head>
<body>
<%
    response.setContentType("text/html;charset=UTF-8");

    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;

    try {
        // Load the JDBC driver
        Class.forName("com.mysql.cj.jdbc.Driver");

        // Establish connection to the database
        String url = "jdbc:mysql://localhost:3306/college";
        String username = "root";
        String password = "";
        conn = DriverManager.getConnection(url, username, password);

        // Prepare SQL statement to retrieve data from the userinformation table
        String sql = "SELECT * FROM userinformation";
        stmt = conn.prepareStatement(sql);

        // Execute SQL query
        rs = stmt.executeQuery();

        // Start HTML output
        String myname = (String) session.getAttribute("name");
        int a = 0;

        // Display the data
        out.println("<div class='container'>");
        while (rs.next()) {
            String name = rs.getString("name");
            String email = rs.getString("email");
            String game = rs.getString("game");
            String state = rs.getString("state");

            if (myname != null && myname.equals(name)) {
                continue;
            }

            // Display each entry in a box format
            out.println("<div class='res'>");
            out.println("<p>Name: " + name + "</p>");
            out.println("<p>State: " + state + "</p>");
            out.println("<p>Game: " + game + "</p>");
            out.println("<button type='submit' class='redb'>Challenge</button>");
            out.println("</div>");
        }
        out.println("</div>");

    } catch (ClassNotFoundException ex) {
        out.println("Error loading JDBC driver: " + ex.getMessage());
        ex.printStackTrace(new PrintWriter(out));
    } catch (SQLException ex) {
        out.println("Database connection or query error: " + ex.getMessage());
        ex.printStackTrace(new PrintWriter(out));
    } finally {
        // Close the resources
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        } catch (SQLException ex) {
            out.println("Error closing resources: " + ex.getMessage());
            ex.printStackTrace(new PrintWriter(out));
        }
    }
%>
</body>
</html>
