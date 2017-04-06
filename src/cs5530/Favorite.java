package cs5530;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Favorite 
{
	public Favorite(){}
	
	public void updateFavorite(String login, int THID, Connection conn, Statement stmt) throws SQLException, IOException
	{
		String query = " insert into Favorites (THID, login, fvdate)"
				+ " values (?, ?, ?)";
		String deleteSQL = "delete from Favorites where login = ?";
		/*
	    PreparedStatement preparedDeleteStmt = conn.prepareStatement(deleteSQL);
	    try
	    {
	    	preparedDeleteStmt.setString(1, login);
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
		*/
		
		PreparedStatement preparedStmt = conn.prepareStatement(query);
		setValues(preparedStmt, THID, login); //sets the values and calls execute
		
	}
	
	private static void setValues(PreparedStatement preparedStmt, int THID, String login) throws SQLException, IOException
	{
		java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
		try
		{
			preparedStmt.setInt(1, THID);
			preparedStmt.setString(2, login);
			preparedStmt.setDate(3, date);
			
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
			System.out.println("Successfully Favorited. \n");
		}
		catch (SQLException e)
		{
			System.out.println(e);
			System.out.println("Can not favorite the TH. Make sure all of your values are correct. \n");
		}
	}

}
