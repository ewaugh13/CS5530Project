import java.io.IOException;
import java.sql.*;


public class Rates {
	
	public Rates(){}
	
	public void insertRating(String login, int fid, int rating, Connection conn, Statement stmt) throws SQLException, IOException
	{
		String query = " insert into Rates (login, fid, rating)"
			     + " values (?, ?, ?)";
		
		//possibly use ==

		String deleteSQL = "delete from Rates where login = ? and fid = ?";
		PreparedStatement preparedDeleteStmt = conn.prepareStatement(deleteSQL);
		    
		try
		{
			preparedDeleteStmt.setString(1, login);
			preparedDeleteStmt.setInt(2, fid);
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
			
		PreparedStatement preparedStmt = conn.prepareStatement(query);
		try
		{	
			setValues(preparedStmt, login, fid, rating);		
		}
		catch(Exception e)
		{
			System.out.println("Input not in correct format. Please try again. \n");
		}

	}
	private static void setValues(PreparedStatement preparedStmt, String login, int fid, int rating) throws SQLException, IOException
	{
		try
		{
			preparedStmt.setString(1, login);
			preparedStmt.setInt(2, fid);
			preparedStmt.setInt(3, rating);
			
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
			System.out.println("Successfully added Feedback. \n");
		}
		catch (SQLException e)
		{
			System.out.println(e);
			System.out.println("Could not add Feedback. Make sure all of your inputs are correct. \n");
		}
	}
	
}
