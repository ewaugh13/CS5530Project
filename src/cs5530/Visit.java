package cs5530;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Visit 
{
	public Visit() {}
	
	public void insertVisit(String login, int THID, int pid, Connection conn, Statement stmt) throws SQLException, IOException
	{
		String query = " insert into Visit (login, THID, pid, cost)"
				+ " values (?, ?, ?, ?)";
		
		int cost = 0;
		String sql = "Select r.cost From Reserve r Where r.pid = " + pid;
		
		ResultSet rs = null;
		try
		{
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				cost = Integer.parseInt(rs.getString("cost"));
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

		if(cost > 0)
		{
			// create the mysql insert preparedstatement
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			setValues(preparedStmt, login, THID, pid, cost, conn); //sets the values and calls execute
		}
		else
		{
			System.out.println("Cost was not greater than 0 so visit could not be made. The owner put in wrong information. If you want to you can give him a bad review based on this THID" + THID + ". \n");
		}
	} 
	
	private void setValues(PreparedStatement preparedStmt, String login, int THID, int pid, int cost, Connection conn) throws SQLException, IOException
	{
		try
		{
			preparedStmt.setString(1, login);
			preparedStmt.setInt(2, THID);
			preparedStmt.setInt(3, pid);
			preparedStmt.setInt(4, cost);
		}
		catch (Exception e)
		{
		}
		try
		{
			preparedStmt.execute();
		}
		catch(Exception e)
		{
		}
	}
}
