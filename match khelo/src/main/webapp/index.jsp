<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    
    <title>let's play a match</title>
    <link  rel = "stylesheet" href ="./styling.css" >  <!-- css file ko link kr diye ye HTML file se-->
</head>
  <!--linked this js file for backend programming-->
<body>
     <% 
        // Check if the session attribute is present
        Boolean signupSuccess = (Boolean) request.getSession().getAttribute("signupSuccess");
        if (signupSuccess != null && signupSuccess) {
    %>
            <script>alert('You signed up successfully!');</script>
    <% 
            // Clear the session attribute
            request.getSession().removeAttribute("signupSuccess");
        }
    %>
    <div class="headings">      <!--heading nam ka class home page ke liye taki background me image de sake -->
      <div class = "nvbar">
         <div class="icon">
            <div class ="logo">match lo</div>  <!--logo nam ka class bnaye taki match lo ko select kr ke color de sake -->
        
          <div class = "menu">   <!--menu class box ka kam kr rha jisme headings diye hai-->
            <ul>                
                   <li> <a href="index.jsp"> HOME </a></li>  <!--yha home page ka location diye hai taki click krne pr home page khule-->
                   <li> <a href="#"> MATCHES</a></li>  <!-- yha alag page bna ke unska location dena hai taki click me wo page khule-->
                   <li><a href="#"> TURNAMENTS </a></li>     <!--same as above-->           
            </ul>
           </div>
         </div>
       </div>
       <div class ="content">
        <h1 class="conthead">let's play the  match</h1>  <!--simple as it is text dala gya hai web me through this-->
        <p class="para">please join us to get the notification of<br>
                         match being played nearest you <br>
                         and get a fast entry in nearest<br> 
                         turnaments through the button <br>
                         given bellow 
        </p>
        <buttom class="join"> <a href="signup_page.jsp">sign up</a></buttom> <!--sign up ke liye alag page khulega to uska location diya hai-->
        <div class="form">
          <form action="LoginServlet" method="post">
            <h2>login here</h2>    <!--log in ka alg se form nam ka class/box bna diye taki styling kr ske -->
            <input type="email" name="email" placeholder="   username or email" id="email"><br>
            <input type="password" name="password"  placeholder="    password   " id="pass"><br>
            <button type="submit" class="btn">sign in</button> <!-- Changed button to submit type -->
          </form> 
        </div>
       </div> 
    </div>
   <!--<script  src="backend.js"></script>-->
                                             <!--linked this js file for backend programming ye last me link kr rhe taki
                                            ye all html file DOM pe load ho jaye aur hum asani se queryselector 
                                            wagarah use kr sake -->
</body>
</html>
