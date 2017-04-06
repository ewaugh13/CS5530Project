package cs5530;

import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.io.BufferedReader;

public class TH
{
	public TH() {}
	
	public void insertTH(String input, Connection conn, Statement stmt) throws SQLException, IOException, NumberFormatException, ParseException
	{
		Keyword key = new Keyword();
		List<Integer> keyIDS = key.userInput(conn, stmt);
		
		String[] splitted = input.split(",");
		for(int i = 0; i < splitted.length; i++)
		{
			if(Pattern.matches("\\s*", splitted[i]))
			{
				splitted[i] = null;
			}
		}
		
		String query = " insert into THData (THID, url, address, city, state, zip, THname, yearBuilt, category, login)"
				     + " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		String THIDgetter = "select max(THID) from THData";
		
		
		String output = "";
		ResultSet rs = null;
		rs = stmt.executeQuery(THIDgetter);
		while (rs.next())
		{
			output = rs.getString("max(THID)");
		}
		if(output == "" || output == null)
		{
			output = "0";
		}
				
		
		int THID = Integer.parseInt(output) + 1;
		String URL = "https://uotel.com/temporary-housing/" + THID;
		
		
		// create the mysql insert preparedstatement
		PreparedStatement preparedStmt = conn.prepareStatement(query);
		setValues(preparedStmt, stmt, conn, keyIDS, splitted, THID, URL); //sets the values and calls execute
	}

	public int selectTH(Statement stmt, String login, String operation) throws SQLException, IOException
	{
		String query = "";
		if(operation.equals("update") || operation.equals("available"))
		{
			query = "Select THID, THname From THData T Where T.login = " + login;
		}
		else
		{
			query = "Select THID, THname From THData";
		}
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
			if(operation.equals("update"))
			{
				System.out.println("Select the THID of the house you want to update:");
			}
			else if(operation.equals("available"))
			{
				System.out.println("Select the THID of the house you want to make a available:");
			}
			else if(operation.equals("feedback"))
			{
				System.out.println("Select the THID of the house you want to view the average feedback rates score");
			}
			else if(operation.equals("remove"))
			{
				System.out.println("Select the THID of the house you want to view the remove a period");
			}
			else if(operation.equals("keyword"))
			{
				System.out.println("Select the THID of the house you want to add or remove keywords");
			}
		
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
				System.out.println("Wrong THID. Please select a correct TH by THID next time. \n");
				return 0;
			}
		}
		else
		{
			System.out.println("You have no temporary houses listed so there are none to update. \n");
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
			      "update THData set address = ?, city = ?, state = ?, zip = ?, THname = ?, yearBuilt = ?, category = ? Where THID = " + THID);
		updateValues(preparedStmt, splitted);
		
	}

	private static void updateValues(PreparedStatement preparedStmt, String[] splitted) throws SQLException, IOException
	{
		try
		{
			preparedStmt.setString(1, splitted[0].toLowerCase()); // address
			preparedStmt.setString(2, splitted[1].toLowerCase()); //city
			preparedStmt.setString(3, splitted[2].toLowerCase()); //state
			preparedStmt.setString(4, splitted[3].toLowerCase()); //zip

			preparedStmt.setString(5, splitted[4]); // th name
			preparedStmt.setInt(6, Integer.parseInt(splitted[5])); //year built
			preparedStmt.setString(7, splitted[6].toLowerCase()); //category
			
			updateStmt(preparedStmt);
		}
		catch(Exception e)
		{
			System.out.println("an inputed value did not match specifications of what is needed. Please try again.");
		}
	}

	private static void setValues(PreparedStatement preparedStmt, Statement stmt, Connection conn, List<Integer> keyIDS, String[] splitted, int THID, String URL) throws SQLException, IOException
	{
		try
		{
			preparedStmt.setInt(1, THID);
			preparedStmt.setString(2, URL);
			preparedStmt.setString(3, splitted[0].toLowerCase()); //address
			preparedStmt.setString(4, splitted[1].toLowerCase()); //city
			preparedStmt.setString(5, splitted[2].toLowerCase()); //state
			preparedStmt.setString(6, splitted[3].toLowerCase()); //zip
			preparedStmt.setString(7, splitted[4]); //THname
			preparedStmt.setInt(8, Integer.parseInt(splitted[5])); //yearBuilt
			preparedStmt.setString(9, splitted[6].toLowerCase()); //category
			preparedStmt.setString(10, splitted[7]); //login
			
			exectueStmt(preparedStmt, keyIDS, splitted, THID, stmt, conn);

		}
		catch(Exception e)
		{
			Keyword key = new Keyword();
			for(int i = 0; i < keyIDS.size(); i++)
			{
				if(keyIDS.get(i) > 0)
				{
					key.deleteWord(keyIDS.get(i), conn);
				}
			}
			System.out.println("an inputed value did not match specifications of what is needed, and keywords deleted. Please try again. \n");
		}
		
	}

	private static void exectueStmt(PreparedStatement preparedStmt, List<Integer> keyIDS, String[] splitted, int THID, Statement stmt, Connection conn) throws SQLException, IOException
	{
		Keyword key = new Keyword();
		try
		{
			preparedStmt.execute();
			System.out.println("TH created with THID " + THID + "\n");
			
			for(int i = 0; i < keyIDS.size(); i++)
			{
				if(keyIDS.get(i) > 0)
				{
					key.insertIntoHasKeys(THID, keyIDS.get(i), conn);
				}
			}
		}
		catch (SQLException e)
		{
			System.out.println(e);
			for(int i = 0; i < keyIDS.size(); i++)
			{
				if(keyIDS.get(i) > 0)
				{
					key.deleteWord(keyIDS.get(i), conn);
				}
			}
			System.out.println("Can not create the TH. Make sure all of your values are correct. (Your keywords were also removed). \n");
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

	public List<Integer> selectAllTH(Statement stmt, StringBuilder stringBuilder) throws SQLException, IOException
	{
		String sql = "Select THID, THname From THData";
		List<Integer> THIDS = new ArrayList<Integer>();
		
		String output = "";
		ResultSet rs = null;
		rs = stmt.executeQuery(sql);
		try
		{
	   		 	rs=stmt.executeQuery(sql);
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
		stringBuilder.append("Here are the THID's and names of the temporary houses: \n");
		stringBuilder.append(output);
		stringBuilder.append("Select the THID: \n");
		return THIDS;
	}

	public void displayUsersTH(Statement stmt, String login) throws SQLException
	{
		String sql = "Select THID, THname From THData Where login = " + login;
		
		String output = "";
		ResultSet rs = null;
		rs = stmt.executeQuery(sql);
		try
		{
	   		 	rs=stmt.executeQuery(sql);
	   		 	while (rs.next())
				{
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
		System.out.println("Here are the temporary houses that you have listed (if blank you have listed none, list if you want to)");
		System.out.println(output);
	}
}
