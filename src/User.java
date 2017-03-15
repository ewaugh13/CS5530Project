
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class User {
	public User() 
	{
		List<String> UserLogins = new ArrayList<String>();
	}
	
	public String getUser(String input, Statement stmt)
	{
		//String sql = get info from input and run query to get information
		String output = "";
		return output;
	}
	
	public void insertUser(String input, Statement stmt) //for registering users
	{
		String[] splited = input.split(",");
		boolean isUser;
		if(splited[7] == "yes")
		{
			isUser = true;
		}
		else
		{
			isUser = false;
		}
		String sql = "INSERT INTO Users VALUES('" + splited[0] + "', '" + splited[1] + "', '" + splited[2] + "', " + 
		Integer.parseInt(splited[3]) + ", '" + splited[4] + "', '" + splited[5] + "', '" + splited[6] + "', " + isUser + ")";
		
		ResultSet rs = null;
	 	System.out.println("executing " + sql + "\n");
	 	try
	 	{
		 	rs = stmt.executeQuery(sql);
		 	rs.close();
	 	}
	 	catch(Exception e)
	 	{
	 		System.out.println("cannot execute the query \n");
	 	}
	 	finally
	 	{
	 		try
	 		{
		 		if (rs!=null && !rs.isClosed())
		 		{
		 			rs.close();
		 		}
	 		}
	 		catch(Exception e)
	 		{
	 			System.out.println("cannot close result set");
	 		}
	 	}
	}
}
