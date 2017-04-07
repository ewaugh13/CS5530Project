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

function removePeriodMenu()
{
	window.location = "removePeriod.jsp";
}

function updateKeyWordsMenu()
{
	window.location = "updateKeyWords.jsp";	
}

function viewHousesMenu()
{
	window.location = "viewHouses.jsp";
}

function returnToUserMenu()
{
	window.location = "userMenu.jsp";
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
	<FORM NAME="RemovePeriod_button" METHOD="POST" ONCLICK="removePeriodMenu()" action="removePeriod.jsp">
        <INPUT TYPE="HIDDEN" NAME="RemovePeriodButton">
        <INPUT TYPE="BUTTON" VALUE="Remove a period of availability">
    </FORM>
    <BR>
	<FORM NAME="UpdateKeyWords_button" METHOD="POST" ONCLICK="updateKeyWordsMenu()" action="updateKeyWords.jsp">
        <INPUT TYPE="HIDDEN" NAME="UpdateKeyWordsButton">
        <INPUT TYPE="BUTTON" VALUE="Update the keywords of a house you have listed">
    </FORM>
    <BR>
	<FORM NAME="ViewHouses_button" METHOD="POST" ONCLICK="viewHousesMenu()" action="viewHouses.jsp">
        <INPUT TYPE="HIDDEN" NAME="ViewHousesButton">
        <INPUT TYPE="BUTTON" VALUE="View the houses that you have listed">
    </FORM>
    <BR>
    <FORM NAME="Return_button" METHOD="POST" ONCLICK="returnToUserMenu()" action="userMenu.jsp">
    <INPUT TYPE="HIDDEN" NAME="ReturnButton">
  	<INPUT TYPE="BUTTON" VALUE="Go back to User Menu" >
  	</FORM>
</body>
