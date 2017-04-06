<%@ page language="java" import="cs5530.*" %>
<html>
<head>
<script LANGUAGE="javascript">



function loginWindow()
{
	window.location = "login.jsp";
}

function registerWindow()
{
	window.location = "register.jsp";
}	

</script> 
</head>
<body>
	Welcome to the Uotel system<BR>
	Select register or login:
	<BR><BR>
	<FORM NAME="login_button" METHOD="POST" ONCLICK="loginWindow()" action="login.jsp">
        <INPUT TYPE="HIDDEN" NAME="loginButton">
        <INPUT TYPE="BUTTON" VALUE="Login" >
    </FORM>
	<BR>
	<FORM NAME="register_button" METHOD="POST" ONCLICK="registerWindow()" action="register.jsp">
        <INPUT TYPE="HIDDEN" NAME="registerButton">
        <INPUT TYPE="BUTTON" VALUE="Register">
    </FORM>
</body>
