<%@ page language="java" import="cs5530.*" %>
<%@ page import="java.util.*" %>
<html>
<style>

</style>

<head>
<script LANGUAGE="javascript">

function check_for_THID(form_obj){
	if(form_obj.inputTHID.value == ""){
		alert("Search field should be nonempty");
		return false;
	}
	return true;
}

function check_for_pid(form_obj){
	if(form_obj.inputPID.value == ""){
		alert("Search field should be nonempty");
		return false;
	}
	return true;
}

function returnToUserMenu()
{
	window.location = "userMenu.jsp";
}

function finalizeReservations()
{
	window.location = "finalizeReservations.jsp";
}

function selectAnotherTH()
{
	window.location = "reserve.jsp"; 
}


</script> 
</head>
<body>

<%
HttpSession sess = request.getSession();
Connector connector = new Connector();
String login = sess.getAttribute("login").toString();

Available available = new Available();
StringBuilder stringBuilder = new StringBuilder();
int inputTHID = 0;
int inputPID = 0;
inputTHID = Integer.parseInt(sess.getAttribute("THID").toString());
List<Integer> THIDS = available.displayTHAvialable(connector.stmt, stringBuilder);
String[] lines = stringBuilder.toString().split("\n");
for(int i = 0; i < lines.length; i++)
{
	out.println(lines[i]); %><BR><%
}
String input = request.getParameter("inputTHID");
try
{
	inputTHID = Integer.parseInt(input);
}
catch(Exception e)
{
}

if(inputTHID <= 0){
%>
	<BR>Select an available TH by THID<BR>
	<form name="user_selectTH" method=get onsubmit="return check_for_THID(this)" action="reserve.jsp">
		Selected THID:
		<input type=number name="inputTHID" value="THID">
		<BR>
		<input type=submit>
	</form>
	<FORM NAME="Return_button" METHOD="POST" ONCLICK="returnToUserMenu()" action="userMenu.jsp">
        <INPUT TYPE="HIDDEN" NAME="ReturnButton">
        <INPUT TYPE="BUTTON" VALUE="Go back to User Menu" >
    </FORM>
<%
} else {
  
  
  	if(THIDS.contains(inputTHID)) {
  		out.println("Temporary house selected.");%><BR><%
  		sess.setAttribute("THID", inputTHID);
  		stringBuilder = new StringBuilder();
  		List<Integer> PIDS = available.displayPidAvialable(inputTHID, connector.stmt, stringBuilder);
  		lines = stringBuilder.toString().split("\n");
  		%><BR><%
  		for(int i = 0; i < lines.length; i++)
  		{
  			out.println(lines[i]); %><BR><%
  		}
  		input = request.getParameter("inputPID");
  		try
  		{
  			inputPID = Integer.parseInt(input);
  		}
  		catch(Exception e)
  		{
  		}

  		if(inputPID <= 0){
  		%>
  			<BR>Select an available period by pid<BR>
  			<form name="user_selectPeriod" method=get onsubmit="return check_for_pid(this) action="reserve.jsp">
  				Selected pid:
  				<input type=number name="inputPID" value="PID">
  				<BR>
  				<input type=submit>
  			</form>
  			<FORM NAME="Return_button" METHOD="POST" ONCLICK="returnToUserMenu()" action="userMenu.jsp">
  		        <INPUT TYPE="HIDDEN" NAME="ReturnButton">
  		        <INPUT TYPE="BUTTON" VALUE="Go back to User Menu" >
  		    </FORM>
  		<%

  		} else {
  		  	if(PIDS.contains(inputPID)) 
  		  	{ 
  		  		out.println("Period selected.");%><BR><BR><%
  		  		RecommendedTH recommended = new RecommendedTH();
  		  	    String[] recomendedTHS = recommended.getRecommendedTH(inputTHID, connector.stmt).split("\n");
  		  		for(int i = 0; i < recomendedTHS.length; i++)
  		  		{
  		  			out.println(recomendedTHS[i]); %><BR><%
  		  		}
  		  		
  		  		List<Map.Entry<Integer, Integer>> dictionaryOfReserveIDs = (List<Map.Entry<Integer, Integer>>)sess.getAttribute("Reservations");
  		  		if(dictionaryOfReserveIDs ==  null)
  		  		{
  		  			dictionaryOfReserveIDs = new ArrayList<Map.Entry<Integer, Integer>>();
  		  		}
  		  		dictionaryOfReserveIDs.add(new AbstractMap.SimpleEntry(inputTHID, inputPID));
  		  		Set<Map.Entry<Integer, Integer>> hs = new HashSet<>();
  		  		hs.addAll(dictionaryOfReserveIDs);
  		  		dictionaryOfReserveIDs.clear();
  		  		dictionaryOfReserveIDs.addAll(hs);
  		  		sess.setAttribute("Reservations", dictionaryOfReserveIDs);
  		  		
  		  		connector.closeStatement();
  	 			connector.closeConnection();
  		  		sess.setAttribute("THID", 0);%>
  		  		
  	  			<FORM NAME="reserveAgain_button" METHOD="POST" ONCLICK="selectAnotherTH()" action="reserve.jsp">
  	  		        <INPUT TYPE="HIDDEN" NAME="ReserveAgainButton">
  	  		        <INPUT TYPE="BUTTON" VALUE="Reserve another temporary house" >
  	  		    </FORM>
  	  			<FORM NAME="Finalize_button" METHOD="POST" ONCLICK="finalizeReservations()" action="finalizeReservations.jsp">
  	  		        <INPUT TYPE="HIDDEN" NAME="FinalizeButton">
  	  		        <INPUT TYPE="BUTTON" VALUE="Finalize reservations of selected temporary house(s)" >
  	  		    </FORM>
  	  		    
  		  <% } else { 
  				out.println("No available temporary house with the pid of: " + inputPID + ". Please select from the available periods for the temporary houses selected.");
  				connector.closeStatement();
  		 		connector.closeConnection();%>
  				<FORM NAME="Return_button" METHOD="POST" ONCLICK="returnToUserMenu()" action="userMenu.jsp">
  		        <INPUT TYPE="HIDDEN" NAME="ReturnButton">
  		        <INPUT TYPE="BUTTON" VALUE="Go back to User Menu" >
  		    	</FORM>
  		  <% }
  		}  // We are ending the braces for else here
  		
     } else { 
		out.println("No available temporary house with the THID of: " + inputTHID + ". Please select from the available temporary houses.");
		connector.closeStatement();
 		connector.closeConnection();%>
		<FORM NAME="Return_button" METHOD="POST" ONCLICK="returnToUserMenu()" action="userMenu.jsp">
        <INPUT TYPE="HIDDEN" NAME="ReturnButton">
        <INPUT TYPE="BUTTON" VALUE="Go back to User Menu" >
    	</FORM>
  <% } 

}  // We are ending the braces for else here
%>

</body>
