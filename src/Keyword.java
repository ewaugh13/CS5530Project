import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.util.regex.Pattern;

public class Keyword 
{
	public Keyword(){}
	
	public int insertKeyword(String word, String language, Connection conn, Statement stmt) throws SQLException, IOException, ParseException, NumberFormatException
	{
		if(Pattern.matches("\\s*", word))
		{
			word = null;
		}
		
		String pidgetter = "select max(wid) from Keywords";
		
		
		String output = "";
		ResultSet rs = null;
		try
		{
			rs = stmt.executeQuery(pidgetter);
			while (rs.next())
			{
				output = rs.getString("max(wid)");
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
		
		if(output == null || output.equals(""))
		{
			output = "0";
		}
		int wid = Integer.parseInt(output) + 1;

		String query = " insert into Keywords (wid, word, wlangauage)"
				+ " values (?, ?, ?)";
		
		
		try
		{
		PreparedStatement preparedStmt = conn.prepareStatement(query);
		if(setValues(preparedStmt, wid, word, language)) //if values are set correctly it returns the pid and if not return 0
		{
			return wid;
		}
			return 0;
			
		}
		catch(Exception e)
		{
			System.out.println("Word could not be added. Try again. \n");
			return 0;
		}
	}
	
	private static boolean setValues(PreparedStatement preparedStmt, int wid, String word, String language) throws SQLException, IOException
	{
		try
		{
			preparedStmt.setInt(1, wid);
			preparedStmt.setString(2, word);
			preparedStmt.setString(3, language);
			return exectueStmt(preparedStmt);
		}
		catch(Exception e)
		{
			System.out.println("Incorect values please try again. \n");
			return false;
		}
		
	}
	
	private static boolean exectueStmt(PreparedStatement preparedStmt) throws SQLException, IOException
	{
		try
		{
			preparedStmt.execute();
			return true;
		}
		catch (SQLException e)
		{
			System.out.println("Can not create Keyword. \n");
			return false;
		}
	}
	
	public void deleteWord(int wid, Connection conn) throws SQLException, IOException
	{
		String deleteSQL = "delete from Keywords where wid = ?";
	    PreparedStatement preparedDeleteStmt = conn.prepareStatement(deleteSQL);
	    
	    try
	    {
	    	preparedDeleteStmt.setInt(1, wid);
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
	}
}