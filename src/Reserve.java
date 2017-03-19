import java.io.IOException;
import java.sql.*;
import java.util.concurrent.TimeUnit;

public class Reserve 
{
	public Reserve(){}
	
	public void insertReservation(String login, int THID, int pid, Connection conn, Statement stmt) throws SQLException, IOException
	{
		String query = " insert into Reserve (login, THID, pid, cost)"
				+ " values (?, ?, ?, ?)";
		
		long cost = 0;
		int costPerNight = 0;
		java.sql.Date dbFromDate = new java.sql.Date(0);
		java.sql.Date dbToDate = new java.sql.Date(0);
		String sql = "Select p.fromDate, p.toDate, a.pricePerNight From Period p, Available a Where p.pid = " + pid + " AND a.pid = " + pid;
		
		ResultSet rs = null;
		try
		{
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				costPerNight = Integer.parseInt(rs.getString("pricePerNight"));
				dbFromDate = rs.getDate("fromDate");
				dbToDate = rs.getDate("toDate");
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
		long diff = dbToDate.getTime() - dbFromDate.getTime();
		long numDays = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
		cost = numDays * costPerNight;

		if(cost > 0)
		{
			// create the mysql insert preparedstatement
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			setValues(preparedStmt, login, THID, pid, (int)cost, conn); //sets the values and calls execute
		}
		else
		{
			System.out.println("Cost was not greater than 0 so reservation could not be made. The owner put in wrong information. If you want to you can give him a bad review based on this THID" + THID + ". \n");
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

	public int displayAndSelectReservationTHID(Statement stmt, Connection conn)
	{
		
		return 0;
	}
	
	public int displayAndSelectPidAvialable(int THID, Statement stmt, Connection conn)
	{
		
		return 0;
	}
	
	public boolean visitSelectedAvailable(int THID, int pid, Statement stmt, Connection conn)
	{
		// return true if there selection made they have already reserved if not tell them that they havn't reserved the selection that they made
		return true;
	}
}
