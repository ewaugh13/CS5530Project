<%@ page language="java" import="cs5530.*" %>
<html>
<head>
<script LANGUAGE="javascript">

function check_all_fields(form_obj){
	if( form_obj.attributeLogin.value == "" || form_obj.attributePassword.value == "" || form_obj.attributeFullName.value == "" 
	|| form_obj.attributeAge.value == "" || form_obj.attributeEmail.value == "" || form_obj.attributeAdmin.value == ""){
		alert("Search field should be nonempty");
		return false;
	}
	return true;
}

function changeWindow()
{
	 window.location = "userMenu.jsp";
}

function returnToMain()
{
	window.location = "mainMenu.jsp";
}

</script> 
</head>
<body>

<%
String inputLogin = request.getParameter("inputLogin");
String inputPassword = request.getParameter("inputPassword");
String inputFullName = request.getParameter("inputFullName");
String inputAge = request.getParameter("inputAge");
String inputEmail = request.getParameter("inputEmail");
String inputPhoneNumber = request.getParameter("inputPhoneNumber");
String inputAddress = request.getParameter("inputAddress");
String inputAdmin = request.getParameter("inputAdmin");


if(inputLogin == null || inputPassword == null || inputFullName == null || inputAge == null || inputEmail == null || inputAdmin == null){
%>

	Enter all necessary information: 
	<form name="user_register" method=get onsubmit="return check_all_fields(this)" action="register.jsp">
		<br>Login:
		<input type=hidden name="inputLogin" value="login">
		<input type=text name="attributeLogin" length=30>
		<BR><BR>
		<br>Password:
		<input type=hidden name="inputPassword" value="password">
		<input type=text name="attributePassword" length=16>
		<BR><BR>
		<br>Full Name:
		<input type=hidden name="inputFullName" value="fullName">
		<input type=text name="attributeFullName" length=40>
		<BR><BR>
		<br>Age (Have to be over 18):
		<input type=hidden name="inputAge" value="age">
		<input type=text name="attributeAge" length=3>
		<BR><BR>
		<br>Email:
		<input type=hidden name="inputEmail" value="email">
		<input type=text name="attributeEmail" length=50>
		<BR><BR>
		<br>Phone Number - Optional (Format of 303-721-1445):
		<input type=hidden name="inputPhoneNumber" value="phoneNumber">
		<input type=text name="atttibutePhoneNumber" length=12>
		<BR><BR>
		<br>Address - Optional:
		<input type=hidden name="inputAddress" value="address">
		<input type=text name="attributeAddress" length=100>
		<BR><BR>
		<br>Admin (Enter 'yes' or 'no'):
		<input type=hidden name="inputAdmin" value="admin">
		<input type=text name="attributeAdmin" length=3>
		<BR>
		<input type=submit>
	</form>

<%

} else {

	String login = request.getParameter("attributeLogin");
	String password = request.getParameter("attributePassword");
	String fullName = request.getParameter("attributeFullName");
	String age = request.getParameter("attributeAge");
	String email = request.getParameter("attributeEmail");
	String phoneNumber = request.getParameter("atttibutePhoneNumber");
	String address = request.getParameter("attributeAddress");
	String admin = request.getParameter("attributeAdmin");
	Connector connector = new Connector();
	User users = new User();
	String input = login + "," + password + "," + fullName + "," + age + "," + email + "," + phoneNumber + "," + address + "," + admin;
	boolean registerResult = users.insertUser(input, connector.con);
	if(registerResult)
	{
		HttpSession sess = request.getSession(); 
		sess.setAttribute("login", login);
		sess.setAttribute("connector", connector);
	}
	
%>  

  <p><b>Attempting to login: </b><BR><BR>
  
  <% if(registerResult) { %>
  		<%connector.closeStatement();
 		connector.closeConnection();%>
		<script type="text/javascript"> changeWindow(); </script>	
  <% } 
  else { %>
		<% out.println("Could not register with login name of: " + login + " and password: " + password + ". Please check all input fields or a different login");
		connector.closeStatement();
 		connector.closeConnection();%>
		<FORM NAME="Return_button" METHOD="POST" ONCLICK="returnToMain()" action="mainMenu.jsp">
        <INPUT TYPE="HIDDEN" NAME="ReturnButton">
        <INPUT TYPE="BUTTON" VALUE="Go back to login menu" >
    	</FORM>
  <% } %>

<%
}  // We are ending the braces for else here
%>

</body>
