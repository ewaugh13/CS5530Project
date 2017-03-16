
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
		output = rs.getString("THID");
		if(output == "")
		{
			output = "0";
		}
		
		int THID = Integer.parseInt(output) + 1;
		String URL = "https://Uotel.com/temporary-housing/" + THID;
		
		// create the mysql insert preparedstatement
		PreparedStatement preparedStmt = conn.prepareStatement(query);
		setValues(preparedStmt, splitted, THID, URL); //sets the values and calls execute
	}

	
	public void updateTH() //check for that users login name here
	{
		
	}


	private static void setValues(PreparedStatement preparedStmt, String[] splited, int THID, String URL) throws SQLException, IOException
	{
		
		try
		{
			preparedStmt.setInt(1, THID);
			preparedStmt.setString(2, URL);
			preparedStmt.setString(3, splited[0]); //address
			preparedStmt.setString(4, splited[1]); //THname
			preparedStmt.setInt(5, Integer.parseInt(splited[2])); //yearBuilt
			preparedStmt.setString(6, splited[3]); //category
			preparedStmt.setString(7, splited[4]); //login
			
			exectueStmt(preparedStmt, splited);
		}
		catch(Exception e)
		{
			System.out.println("an inputed value did not match specifications of what is needed try again");

		}
		
	}
	
	private static void exectueStmt(PreparedStatement preparedStmt, String[] splited) throws SQLException, IOException
	{
		try
		{
		preparedStmt.execute();
		System.out.println("TH created with THID " + splited[0]);
		}
		catch (SQLException e)
		{
			System.out.println("Can not create the TH. Make sure all of your values are correct.");
			
		}
	}
	
}
