import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.io.BufferedReader;


public class Period 
{
	public Period(){}
	
	private static void insertPeriod(String input, Connection conn, Statement stmt) throws SQLException, IOException
	{
		String[] splitted = input.split(",");
		for(int i = 0; i < splitted.length; i++)
		{
			if(Pattern.matches("\\s*", splitted[i]))
			{
				splitted[i] = null;
			}
		}

		String query = " insert into Period (pid, fromDate, toDate)"
				+ " values (?, ?, ?)";

		// create the mysql insert preparedstatement
		PreparedStatement preparedStmt = conn.prepareStatement(query);
		setValues(preparedStmt, splitted); //sets the values and calls execute
	}
	
	private static void setValues(PreparedStatement preparedStmt, String[] splitted, date dateFrom, date dateTo) throws SQLException, IOException
	{
		try
		{
			preparedStmt.setInt(1, Integer.parseInt(splitted[0]));
			preparedStmt.setDate(2, dateFrom);
			preparedStmt.setDate(3, dateTo);
			exectueStmt(preparedStmt);
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		
	}
	
	private static void exectueStmt(PreparedStatement preparedStmt) throws SQLException, IOException
	{
		try
		{
			preparedStmt.execute();
			System.out.println("Successfully Favorited. \n");
		}
		catch (SQLException e)
		{
			System.out.println("Can not favorite the TH. Make sure all of your values are correct. \n");
		}
}
