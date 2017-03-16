
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class User {
	public User() {
		//List<String> UserLogins = new ArrayList<String>(); // might or might not need this

	}

	public String getUser(String input, Statement stmt) {
		// String sql = get info from input and run query to get information
		String output = "";
		return output;
	}

	public void insertUser(String input, Connection conn) throws SQLException, IOException
	{
		String[] splited = input.split(",");

		String query = " insert into Users (login, password, fullName, age, email, phoneNumber, address, userType)"
				+ " values (?, ?, ?, ?, ?, ?, ?, ?)";

		// create the mysql insert preparedstatement
		PreparedStatement preparedStmt = conn.prepareStatement(query);
		setValues(preparedStmt, splited); //sets the values and calls execute
	}
	
	private static void displayUserOptions(PreparedStatement preparedStmt, String[] splited) throws SQLException, IOException 
	{
		System.out.println("1. Try another login name:");
		System.out.println("2. Return to welcome screen:");
		
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
				System.out.println("please enter a new login name:");
				while ((login = in.readLine()) == null)
					;
				splited[0] = login;
				updateValues(preparedStmt, splited);
				break;
			}
			else
			{
				break;
			}
		}	
	}
	
	private static void exectueStmt(PreparedStatement preparedStmt, String[] splited) throws SQLException, IOException
	{
		try 
		{
			preparedStmt.execute();
			System.out.println("User created with login of " + splited[0] + " and password of " + splited[1] + "\n");
		} 
		catch (SQLException e) 
		{
			System.out.println("cannot create the user try again with a different login name or make sure that the other information fits the specification \n");
			displayUserOptions(preparedStmt, splited);
		}
	}
	
	private static void setValues(PreparedStatement preparedStmt, String[] splited) throws SQLException, IOException
	{
		boolean isUser;
		if (splited[7] == "yes") 
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
			preparedStmt.setInt(4, Integer.parseInt(splited[3]));
			preparedStmt.setString(5, splited[4]);
			preparedStmt.setString(6, splited[5]);
			preparedStmt.setString(7, splited[6]);
			preparedStmt.setBoolean(8, isUser);
			
			exectueStmt(preparedStmt, splited);
		}
		catch (Exception e)
		{
			System.out.println("a inputed value did not match specifications of what is needed try again");
			displayUserOptions(preparedStmt, splited);
		}
	}
	
	private static void updateValues(PreparedStatement preparedStmt, String[] splited) throws SQLException, IOException
	{
		try
		{
			preparedStmt.setString(1, splited[0]);	
			exectueStmt(preparedStmt, splited);
		}
		catch (Exception e)
		{
			System.out.println("a inputed value did not match specifications of what is needed try again");
			displayUserOptions(preparedStmt, splited);
		}
	}
}
