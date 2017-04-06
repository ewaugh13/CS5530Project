<%@ page language="java" import="cs5530.*" %>
<html>
<head>
<script LANGUAGE="javascript">

function check_all_fields(form_obj){
	if( form_obj.attributeLogin.value == "" || form_obj.attributePassword.value == ""){
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
if(inputLogin == null || inputPassword == null){
%>

	Enter you login name followed by password
	<form name="user_login" method=get onsubmit="return check_all_fields(this)" action="login.jsp">
		login name: <BR>
		<input type=hidden name="inputLogin" value="login">
		<input type=text name="attributeLogin" length=30>
		<BR><BR>
		password: <BR>
		<input type=hidden name="inputPassword" value="password">
		<input type=text name="attributePassword" length=16>
		<BR>
		<input type=submit>
	</form>

<%

} else {

	String login = request.getParameter("attributeLogin");
	String password = request.getParameter("attributePassword");
	Connector connector = new Connector();
	User users = new User();
	String input = login + "," + password;
	boolean loginResult = users.loginIntoAccount(input, connector.stmt);
	if(loginResult)
	{
		HttpSession sess = request.getSession(); 
		sess.setAttribute("login", login);
	} // do a back button if false
	
%>  

  <p><b>Attempting to login: </b><BR><BR>
  
  <% if(loginResult) { %>
  		<%connector.closeStatement();
 		connector.closeConnection();%>
  		<script type="text/javascript"> changeWindow(); </script>
  		
  <% } else { %>
		<% out.println("Could not login with login name of: " + login + ". Please use a correct login name and password.");
		connector.closeStatement();
 		connector.closeConnection();%>
		<FORM NAME="Return_button" METHOD="POST" ONCLICK="returnToMain()" action="mainMenu.jsp">
        <INPUT TYPE="HIDDEN" NAME="ReturnButton">
        <INPUT TYPE="BUTTON" VALUE="Go back to login menu">
    	</FORM>
		
  <% } %>

<%
}  // We are ending the braces for else here
%>

</body>
