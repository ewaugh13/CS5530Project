<%@ page language="java" import="cs5530.*" %>
<html>
<head>
<script LANGUAGE="javascript">

function check_all_fields(form_obj){
	if( form_obj.attributeAddress.value == "" || form_obj.attributeCity.value == "" || form_obj.attributeState.value == "" 
	|| form_obj.attributeTHName.value == "" || form_obj.inputYearBuilt.value == "" || form_obj.attributeCategory.value == ""){
		alert("Search field should be nonempty");
		return false;
	}
	return true;
}

function returnToTHMenu()
{
	window.location = "thMenu.jsp";
}

</script> 
</head>
<body>

<%
HttpSession sess = request.getSession();
String login = sess.getAttribute("login").toString();
int THID = 0;
try
{
	THID = Integer.parseInt(sess.getArrtibute("THID"));
}
catch(Exception e)
{	
}

String inputAddress = request.getParameter("inputAddress");
String inputCity = request.getParameter("inputCity");
String inputState = request.getParameter("inputState");
String inputZip = request.getParameter("inputZip");
String inputTHName = request.getParameter("inputTHName");
String inputYearBuilt = request.getParameter("inputYearBuilt");
String inputCategory = request.getParameter("inputCategory");

if(inputAddress == null || inputCity == null || inputState == null || inputTHName == null || inputYearBuilt == null || inputCategory == null && THID != 0){
%>

	Enter all necessary information: 
	<form name="th_create" method=get onsubmit="return check_all_fields(this)" action="insertTH.jsp">
		<br>Address:
		<input type=hidden name="inputAddress" value="address">
		<input type=text name="attributeAddress" length=100>
		<BR><BR>
		<br>City:
		<input type=hidden name="inputCity" value="city">
		<input type=text name="attributeCity" length=50>
		<BR><BR>
		<br>State:
		<input type=hidden name="inputState" value="state">
		<input type=text name="attributeState" length=50>
		<BR><BR>
		<br>Zip (optional):
		<input type=hidden name="inputZip" value="zip">
		<input type=text name="attributeZip" length=5>
		<BR><BR>
		<br>TH Name:
		<input type=hidden name="inputTHName" value="thName">
		<input type=text name="attributeTHName" length=30>
		<BR><BR>
		<br>Year Built:
		<input type=hidden name="inputYearBuilt" value="yearBuilt">
		<input type=text name="atttibuteYearBuilt" length=4>
		<BR><BR>
		<br>Category:
		<input type=hidden name="inputCategory" value="category">
		<input type=text name="attributeCategory" length=40>
		<input type=submit>
	</form>

<%

} else {

	String address = request.getParameter("attributeAddress");
	String city = request.getParameter("attributeCity");
	String state = request.getParameter("attributeState");
	String zip = request.getParameter("attributeZip");
	String thName = request.getParameter("attributeTHName");
	String yearBuilt = request.getParameter("atttibuteYearBuilt");
	String category = request.getParameter("attributeCategory");
	Connector connector = new Connector();
	TH thData = new TH();
	String input = address + "," + city + "," + state + "," + zip + "," + thName + "," + yearBuilt + "," + category;
	thData.insertTH(input, connector.con, connector.stmt);
%>  

  <p><b>Created a temporary house with your input: </b><BR><BR>
  

<%
}  // We are ending the braces for else here
%>

</body>
