package cs5530;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/*
Trust:  create table Trust (login1 char(30) not null, login2 char(30) not null, isTrusted boolean not null, primary key (login1, login2), 
foreign key (login1) references Users(login), foreign key (login2) references Users(login), check(login1 != login2))
*/
public class Trust {
	
	public Trust(){}
	
	public void insertRating(String login1, String login2, boolean isTrusted, Connection conn, Statement stmt) throws SQLException, IOException
	{
		if(!login1.equals(login2))
		{
			String query = "insert into Trust (login1, login2, isTrusted)"
					 + " values (?, ?, ?)";
			
			String deleteSQL = "delete from Trust where login1 = ? and login2 = ?";
		    PreparedStatement preparedDeleteStmt = conn.prepareStatement(deleteSQL);
		    
		    try
		    {
		    	preparedDeleteStmt.setString(1, login1);
				preparedDeleteStmt.setString(2, login2);
		    }
		    catch(Exception e)
		    {		
		    }
		    
		    try
		    {
		    	preparedDeleteStmt.execute();
		    }	
			catch(Exception e)
			{
			}
			try
			{
				PreparedStatement preparedStmt = conn.prepareStatement(query);
				setValues(preparedStmt, login1, login2, isTrusted);
			}
			catch(Exception e)
			{
				System.out.println("Input not in correct format. Please try again. \n");
			}
		}
		else
		{
			System.out.println("You can not give yourself a trustworthiness rating. Please pick a valid user to rate. \n");
		}
	}
	
	private static void setValues(PreparedStatement preparedStmt, String login1, String login2, boolean isTrusted) throws SQLException, IOException
	{
		try
		{
			preparedStmt.setString(1, login1);
			preparedStmt.setString(2, login2);
			preparedStmt.setBoolean(3, isTrusted);

			
			exectueStmt(preparedStmt);
		}
		catch(Exception e)
		{
			System.out.println("an inputed value did not match specifications of what is needed. Please try again. \n");
		}
	}
	
	private static void exectueStmt(PreparedStatement preparedStmt) throws SQLException, IOException
	{
		try
		{
			preparedStmt.execute();
			System.out.println("Successfully rated User. \n");
		}
		catch (SQLException e)
		{
			System.out.println(e);
			System.out.println("Could not rate User. Make sure all of your inputs are correct. \n");
		}
	}
	
	public String displayAndSelectUser(String login, Statement stmt) throws IOException
	{
		String query = "Select u.login, u.fullName From Users u Where u.login <> '" + login + "'";
		String output = "";
		List<String> Logins = new ArrayList<String>();
		ResultSet rs=null;
		try
		{
	   		 	rs=stmt.executeQuery(query);
	   		 	while (rs.next())
				{
	   		 		Logins.add(rs.getString("login"));
	   		 		output += rs.getString("login") + ", " + rs.getString("fullName") + "\n"; 
				} 
	   		 	rs.close();
   		}
   		catch(Exception e)
   		{
   			
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
   		 		System.out.println("cannot close resultset");
   		 	}
   		}
		if(Logins.size() > 0)
		{
			System.out.println("Here are the logins and full names of all the other users in the system. \n");
			System.out.println(output);
			System.out.println("Select the login of the user that you want to make trustworthy or not:");

		
			String choice;
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
			while ((choice = in.readLine()) == null)
				;
			if(Logins.contains(choice))
			{
				return choice; //returns THID
			}
			else
			{
				System.out.println("Wrong login. Please select an availabe user by thier login next time. \n");
				return "";
			}
		}
		else
		{
			System.out.println("There are no other users. Tell your friends about us. \n");
			return "";
		}
	}

	public List<String> displayAndUsers(String login, Statement stmt, StringBuilder stringbuilder) throws IOException
	{
		String query = "Select u.login, u.fullName From Users u Where u.login <> '" + login + "'";
		String output = "";
		List<String> Logins = new ArrayList<String>();
		ResultSet rs=null;
		try
		{
	   		 	rs=stmt.executeQuery(query);
	   		 	while (rs.next())
				{
	   		 		Logins.add(rs.getString("login"));
	   		 		output += rs.getString("login") + ", " + rs.getString("fullName") + "\n"; 
				} 
	   		 	rs.close();
   		}
   		catch(Exception e)
   		{
   			
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
   		 		System.out.println("cannot close resultset");
   		 	}
   		}
		stringbuilder.append("Here are the logins and full names of all the other users in the system. \n");
		stringbuilder.append(output);
		stringbuilder.append("Select the login of the user that you want to make trustworthy or not: \n");
		return Logins;
	}
}
