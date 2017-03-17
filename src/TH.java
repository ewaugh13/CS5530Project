
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.io.BufferedReader;

public class TH
{
	public TH() {}
	
	public void insertTH(String input, Connection conn, Statement stmt) throws SQLException, IOException
	{
		String[] splitted = input.split(",");
		for(int i = 0; i < splitted.length; i++)
		{
			if(Pattern.matches("\\s*", splitted[i]))
			{
				splitted[i] = null;
			}
		}
		
		String query = " insert into THData (THID, url, address, THname, yearBuilt, category, login)"
				     + " values (?, ?, ?, ?, ?, ?, ?)";
		String THIDgetter = "select max(THID) from THData";
		
		String output = "";
		ResultSet rs = null;
		rs = stmt.executeQuery(THIDgetter);
		while (rs.next())
		{
			output = rs.getString("max(THID)");
		}
		if(output == "")
		{
			output = "0";
		}
		
		int THID = Integer.parseInt(output) + 1;
		String URL = "https://uotel.com/temporary-housing/" + THID;
		
		// create the mysql insert preparedstatement
		PreparedStatement preparedStmt = conn.prepareStatement(query);
		setValues(preparedStmt, splitted, THID, URL); //sets the values and calls execute
	}

	public int selectTHUpdate(Statement stmt, String login) throws SQLException, IOException
	{
		String query = "Select THID, THname From THData T Where T.login = " + login;
		String output = "";
		List<Integer> THIDS = new ArrayList<Integer>();
		ResultSet rs=null;
		try
		{
	   		 	rs=stmt.executeQuery(query);
	   		 	while (rs.next())
				{
	   		 		THIDS.add(Integer.parseInt(rs.getString("THID")));
	   		 		output += rs.getString("THID") + ", " + rs.getString("THname") + "\n"; 
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
		if(THIDS.size() > 0)
		{
			System.out.println("Here are the THID's of the temporary houses you listed and the names of them:");
			System.out.println(output);
			System.out.println("Select the THID of the house you want to update:");
		
			String choice;
			int c = 0;
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
			while ((choice = in.readLine()) == null)
				;
			try 
			{
				c = Integer.parseInt(choice);
			} 
			catch (Exception e) 
			{
			}
			if(THIDS.contains(c))
			{
				return c; //returns THID
			}
			else
			{
				return 0;
			}
		}
		else
		{
			System.out.println("You have no temporary houses listed so there are none to update.");
			return 0;
		}
	}
	
	public void updateTH(Connection conn, String input, int THID) throws SQLException, IOException //check for that users login name here
	{		
		String[] splitted = input.split(",");
		for(int i = 0; i < splitted.length; i++)
		{
			if(Pattern.matches("\\s*", splitted[i]))
			{
				splitted[i] = null;
			}
		}
			
		PreparedStatement preparedStmt = conn.prepareStatement(
			      "update THData set address = ?, THname = ?, yearBuilt = ?, category = ? Where THID = " + THID);
		
		updateValues(preparedStmt, splitted);
		
	}

	private static void updateValues(PreparedStatement preparedStmt, String[] splitted) throws SQLException, IOException
	{
		try
		{
			preparedStmt.setString(1, splitted[0]); // address
			preparedStmt.setString(2, splitted[1]); // th name
			preparedStmt.setInt(3, Integer.parseInt(splitted[2])); //year built
			preparedStmt.setString(4, splitted[3]); //category
			
			updateStmt(preparedStmt);
		}
		catch(Exception e)
		{
			System.out.println("an inputed value did not match specifications of what is needed. Please try again.");
		}
	}

	private static void setValues(PreparedStatement preparedStmt, String[] splitted, int THID, String URL) throws SQLException, IOException
	{
		try
		{
			preparedStmt.setInt(1, THID);
			preparedStmt.setString(2, URL);
			preparedStmt.setString(3, splitted[0]); //address
			preparedStmt.setString(4, splitted[1]); //THname
			preparedStmt.setInt(5, Integer.parseInt(splitted[2])); //yearBuilt
			preparedStmt.setString(6, splitted[3]); //category
			preparedStmt.setString(7, splitted[4]); //login
			
			exectueStmt(preparedStmt, splitted, THID);

		}
		catch(Exception e)
		{
			System.out.println("an inputed value did not match specifications of what is needed. try again");
		}
		
	}
	
	private static void exectueStmt(PreparedStatement preparedStmt, String[] splited, int THID) throws SQLException, IOException
	{
		try
		{
			preparedStmt.execute();
			System.out.println("TH created with THID " + THID);
		}
		catch (SQLException e)
		{
			System.out.println("Can not create the TH. Make sure all of your values are correct.");
		}
	}
	
	private static void updateStmt(PreparedStatement preparedStmt) throws SQLException, IOException
	{
		try
		{
			preparedStmt.execute();
			System.out.println("TH updated \n");
		}
		catch (SQLException e)
		{
			System.out.println("Can not update the TH. Make sure all of your values are correct. \n");
		}
	}
	
}
