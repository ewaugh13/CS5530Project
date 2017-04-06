<%@ page language="java" import="cs5530.*" %>
<html>
<head>
<script LANGUAGE="javascript">



function insertTHMenu()
{
	window.location = "insertTH.jsp";
}	

function updateTHMenu()
{
	window.location = "updateTH.jsp";
}

function periodMenu()
{
	window.location = "period.jsp";
}

</script> 
</head>
<body>
	<BR><BR>
	Select an option:
	<FORM NAME="InsertTH_button" METHOD="POST" ONCLICK="insertTHMenu()" action="insertTH.jsp">
        <INPUT TYPE="HIDDEN" NAME="insertTHButton">
        <INPUT TYPE="BUTTON" VALUE="List a temporary house" >
    </FORM>
	<BR>
	<FORM NAME="UpdateTH_button" METHOD="POST" ONCLICK="updateTHMenu()" action="updateTH.jsp">
        <INPUT TYPE="HIDDEN" NAME="updateTHButton">
        <INPUT TYPE="BUTTON" VALUE="Update a temporary house you own">
    </FORM>
    <BR>
	<FORM NAME="Period_button" METHOD="POST" ONCLICK="periodMenu()" action="period.jsp">
        <INPUT TYPE="HIDDEN" NAME="PeriodButton">
        <INPUT TYPE="BUTTON" VALUE="Create a period of availability">
    </FORM>
    <BR>
	<FORM NAME="Visit_button" METHOD="POST" ONCLICK="visitMenu()" action="visit.jsp">
        <INPUT TYPE="HIDDEN" NAME="VisitButton">
        <INPUT TYPE="BUTTON" VALUE="Record a visit at a temporary house">
    </FORM>
    <BR>
	<FORM NAME="Visit_button" METHOD="POST" ONCLICK="visitMenu()" action="visit.jsp">
        <INPUT TYPE="HIDDEN" NAME="VisitButton">
        <INPUT TYPE="BUTTON" VALUE="Record a visit at a temporary house">
    </FORM>
    <BR>
	<FORM NAME="Visit_button" METHOD="POST" ONCLICK="visitMenu()" action="visit.jsp">
        <INPUT TYPE="HIDDEN" NAME="VisitButton">
        <INPUT TYPE="BUTTON" VALUE="Record a visit at a temporary house">
    </FORM>
</body>
