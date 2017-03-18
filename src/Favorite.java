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
	
	public int selectFavorite(Statement stmt) throws SQLException, IOException
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
		if(THIDS.size() > 0)
		{
			System.out.println("Here are the THID's and names of the temporary houses:");
			System.out.println(output);
			System.out.println("Select the THID of the house you want to make your favorite:");
		
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
			System.out.println("There are no temporary houses listed so you can't select a favorite. \n");
			return 0;
		}
	}
}
