
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class User {
	public User() {}

	public String getUser(String input, Statement stmt) {
		// String sql = get info from input and run query to get information
		String output = "";
		return output;
	}

	public boolean insertUser(String input, Connection conn) throws SQLException, IOException
	{
		String[] splitted = input.split(",");
		for(int i = 0; i < splitted.length; i++)
		{
			if(Pattern.matches("\\s*", splitted[i]))
			{
				splitted[i] = null;
			}
		}

		String query = " insert into Users (login, password, fullName, age, email, phoneNumber, address, userType)"
				+ " values (?, ?, ?, ?, ?, ?, ?, ?)";

		// create the mysql insert preparedstatement
		PreparedStatement preparedStmt = conn.prepareStatement(query);
		return setValues(preparedStmt, splitted); //sets the values and calls execute
	}
	
	private static boolean displayUserOptions(PreparedStatement preparedStmt, String[] splited) throws SQLException, IOException 
	{
		System.out.println("1. Try another login name:");
		System.out.println("2. Return to welcome screen:");
		System.out.println("please enter your choice:");
		
		String choice;
		int c = 0;
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String login;
		
		while (true)
		{
			while ((choice = in.readLine()) == null)
			;
			try 
			{
				c = Integer.parseInt(choice);
			} 
			catch (Exception e) 
			{
				continue;
			}
			if (c < 1 | c > 2)
			{
				continue;
			}
			if(c == 1)
			{
				System.out.println("please enter a new login name: ");
				while ((login = in.readLine()) == null)
					;
				splited[0] = login;
				return updateValues(preparedStmt, splited);
			}
			else
			{
				return false;
			}
		}	
	}
	
	private static boolean exectueStmt(PreparedStatement preparedStmt, String[] splited) throws SQLException, IOException
	{
		try 
		{
			preparedStmt.execute();
			System.out.println("User created with login of " + splited[0] + " and password of " + splited[1] + "\n");
			return true;
		} 
		catch (SQLException e) 
		{
			System.err.println(e.getMessage());
			System.out.println("Cannot create the user try again with a different login name or make sure that the other information fits the specification \n");
			return displayUserOptions(preparedStmt, splited);
		}
	}
	
	private static boolean setValues(PreparedStatement preparedStmt, String[] splited) throws SQLException, IOException
	{
		boolean isUser;
		if (splited[7].equals("yes"))
		{
			isUser = true;
		} 
		else 
		{
			isUser = false;
		}
		try
		{
			preparedStmt.setString(1, splited[0]);
			preparedStmt.setString(2, splited[1]);
			preparedStmt.setString(3, splited[2]);
			if(Integer.parseInt(splited[3]) < 18)
			{
				throw new SQLException();
			}
			preparedStmt.setInt(4, Integer.parseInt(splited[3]));
			preparedStmt.setString(5, splited[4]);
			preparedStmt.setString(6, splited[5]);
			preparedStmt.setString(7, splited[6]);
			preparedStmt.setBoolean(8, isUser);
			
			return exectueStmt(preparedStmt, splited);
		}
		catch (Exception e)
		{
			System.out.println("An inputed value did not match specifications of what is needed. Either try a new login name or if you aren't 18 or older please have an adult help you. \n");
			return displayUserOptions(preparedStmt, splited);
		}
	}
	
	private static boolean updateValues(PreparedStatement preparedStmt, String[] splited) throws SQLException, IOException
	{
		try
		{
			preparedStmt.setString(1, splited[0]);	
			return exectueStmt(preparedStmt, splited);
		}
		catch (Exception e)
		{
			System.out.println("An inputed value did not match specifications of what is needed try again. \n");
			return displayUserOptions(preparedStmt, splited);
		}
	}
	
	public boolean loginIntoAccount(String input, Statement stmt)
	{
		String[] splited = input.split(",");
		String sql = "Select * From Users u Where u.login = '" + splited[0] + "' AND u.password = '" + splited[1] + "'";
		ResultSet rs = null;
		 	try
		 	{
		 		rs = stmt.executeQuery(sql);
		 		String result = "";
	   		 	while (rs.next())
				{
	   		 		result = rs.getString("login"); 
				}
		 		if(result.equals(splited[0]))
		 		{
		 			rs.close();
			 		return true;
		 		}
		 		rs.close();
		 	}
		 	catch(Exception e)
		 	{
		 		//System.out.println("cannot execute the query");
		 	}
		 	finally
		 	{
		 		try
		 		{
		 			if (rs!=null && !rs.isClosed())
		 				rs.close();
		 		}
		 		catch(Exception e)
		 		{
		 			//System.out.println("cannot close resultset");
		 		}
		 	}
		 	return false;
	}
}
