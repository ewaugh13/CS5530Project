
import java.io.IOException;

import java.sql.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;

public class TH
{
	public TH() {}
	
	public void insertTH(String input, Connection conn, Statement stmt) throws SQLException, IOException
	{
		String[] splitted = input.split(",");
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

	
	public void updateTH(String input, Connection conn, Statement stmt) throws SQLException, IOException //check for that users login name here
	{
		String[] splitted = input.split(",");
		PreparedStatement preparedStmt = conn.prepareStatement(
			      "update THData set address = ?, THname = ?, yearBuilt = ?, category = ?");
		
		updateValues(preparedStmt, splitted);
		
	}

	private static void updateValues(PreparedStatement preparedStmt, String[] splitted) throws SQLException, IOException
	{
		try
		{
			preparedStmt.setString(1, splitted[0]);
			preparedStmt.setInt(2, Integer.parseInt(splitted[1]));
			preparedStmt.setString(3, splitted[2]);
			preparedStmt.setString(4, splitted[3]);
			
			updateStmt(preparedStmt, splitted);
		}
		catch(Exception e)
		{
			System.out.println("an inputed value did not match specifications of what is needed. try again");
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
	
	private static void updateStmt(PreparedStatement preparedStmt, String[] splitted) throws SQLException, IOException
	{
		try
		{
			preparedStmt.execute();
			System.out.println("TH updated");
		}
		catch (SQLException e)
		{
			System.out.println("Can not update the TH. Make sure all of your values are correct.");
		}
	}
	
}
