<%@ page language="java" import="cs5530.*" %>
<html>
<head>
<script LANGUAGE="javascript">



function reserveMenu()
{
	window.location = "reserve.jsp";
}	

function button2()
{
	window.location = "thMenu.jsp";
}

function visitMenu()
{
	window.location = "visit.jsp";
}

</script> 
</head>
<body>
	<%
	HttpSession sess = request.getSession();
	String login = sess.getAttribute("login").toString();
	sess.setAttribute("THID", 0);
	%>
	Logged in as the user
	<% out.println(login); %> 
	<BR><BR>
	Select an option:
	<FORM NAME="Reserve_button" METHOD="POST" ONCLICK="reserveMenu()" action="reserve.jsp">
        <INPUT TYPE="HIDDEN" NAME="ReserveButton">
        <INPUT TYPE="BUTTON" VALUE="Reserve a temporary house" >
    </FORM>
	<BR>
	<FORM NAME="TemporaryHouse_button" METHOD="POST" ONCLICK="button2()" action="thMenu.jsp">
        <INPUT TYPE="HIDDEN" NAME="temporaryHouseButton">
        <INPUT TYPE="BUTTON" VALUE="Create or update temporary house">
    </FORM>
    <BR>
	<FORM NAME="Visit_button" METHOD="POST" ONCLICK="visitMenu()" action="visit.jsp">
        <INPUT TYPE="HIDDEN" NAME="VisitButton">
        <INPUT TYPE="BUTTON" VALUE="Record a visit at a temporary house">
    </FORM>
</body>
