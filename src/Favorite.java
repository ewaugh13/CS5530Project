import java.io.IOException;
import java.sql.Connection;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Favorite 
{
	public Favorite(){}
	
	public void updateFavorite(String login, int THID, Connection conn, Statement stmt) throws SQLException, IOException
	{
		String query = "insert into Favorites (THID, login, date) " + 
					   "values(?, ?, ?)";
		String deleteOld = "delete from Favorites where login = " + login;
		
		String output = "";
		ResultSet rs = null;
		try
		{
			rs = stmt.executeQuery(deleteOld);
		}
		catch(Exception e)
		{
			
		}
		
		PreparedStatement preparedStmt = conn.prepareStatement(query);
		setValues(preparedStmt, THID, login); //sets the values and calls execute
		
	}
	
	private static void setValues(PreparedStatement preparedStmt, int THID, String login) throws SQLException, IOException
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date date = new Date();
		System.out.println(dateFormat.format(date));
		try
		{
			preparedStmt.setInt(1, THID);
			preparedStmt.setString(2, login);
			preparedStmt.setDate(3, (java.sql.Date) date); 
			
			exectueStmt(preparedStmt);

		}
		catch(Exception e)
		{
			System.out.println("an inputed value did not match specifications of what is needed. try again");
		}
		
	}
	
	private static void exectueStmt(PreparedStatement preparedStmt) throws SQLException, IOException
	{
		try
		{
			preparedStmt.execute();
			System.out.println("Successfully Favorited");
		}
		catch (SQLException e)
		{
			System.out.println("Can not favorite the TH. Make sure all of your values are correct.");
		}
	}
}
