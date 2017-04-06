<%@ page language="java" import="cs5530.*" %>
<%@ page import="java.util.*" %>
<html>
<head>
<script LANGUAGE="javascript">


function returnToUserOptions()
{
	 window.location = "userMenu.jsp";
}

</script> 
</head>
<body>

<%
HttpSession sess = request.getSession();
Connector connector = new Connector();
String login = sess.getAttribute("login").toString();
List<Map.Entry<Integer, Integer>> dictionaryOfReserveIDs = new ArrayList<Map.Entry<Integer, Integer>>();
dictionaryOfReserveIDs = (List<Map.Entry<Integer, Integer>>)sess.getAttribute("Reservations");

Available available = new Available();
StringBuilder stringBuilder = new StringBuilder();
for (int i = 0; i < dictionaryOfReserveIDs.size(); i++)
{
	available.displayReservations(dictionaryOfReserveIDs.get(i).getKey(), dictionaryOfReserveIDs.get(i).getValue(), connector.stmt, stringBuilder);
}
String[] lines = stringBuilder.toString().split("\n");
%>
<p><b>Finalize these reservations: </b><BR><BR>
<%
for(int i = 0; i < lines.length; i++)
{
	out.println(lines[i]); %><BR><%
}
String selection = request.getParameter("yesNo");

if(selection == null)
{%>
	    <form name="user_selection" method=get action="finalizeReservations.jsp">
	    Do you want to make these reservations (yes or no):
		<select id="yesOrNoSelection" name="yesNo">
			<option value = "yes">yes</option>
			<option value = "no">no</option>
		</select>
		<BR>
		<input type=submit>
		</form>
<%
}
else
{
	if(selection.equals("yes"))
	{
		Reserve reserve = new Reserve();
		for (int i = 0; i < dictionaryOfReserveIDs.size(); i++)
		{
			reserve.insertReservation(login, dictionaryOfReserveIDs.get(i).getKey(), dictionaryOfReserveIDs.get(i).getValue(), connector.con, connector.stmt);
		}
		
		for (int i = 0; i < dictionaryOfReserveIDs.size(); i++)
		{
			available.removeAvailabe(dictionaryOfReserveIDs.get(i).getKey(), dictionaryOfReserveIDs.get(i).getValue(), connector.con);
		}
		out.println("Reservations made.");
	}
	else
	{
		out.println("Reservations cancelled.");
	}
	connector.closeStatement();
	connector.closeConnection();%>
	<FORM NAME="Return_button" METHOD="POST" ONCLICK="returnToUserOptions()" action="userMenu.jsp">
    <INPUT TYPE="HIDDEN" NAME="ReturnButton">
    <INPUT TYPE="BUTTON" VALUE="Go back to user options menu">
	</FORM>
<%
}
%>

</body>
