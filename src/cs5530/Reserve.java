package cs5530;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
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

	public int displayAndSelectReservationTHID(String login, Statement stmt) throws IOException
	{
		String query = "Select t.THID, t.THname From THData t, Reserve r Where r.THID = t.THID AND r.login = '" + login + "'";
		String output = "";
		List<Integer> THIDS = new ArrayList<Integer>();
		ResultSet rs=null;
		try
		{
	   		 	rs=stmt.executeQuery(query);
	   		 	while (rs.next())
				{
	   		 		if(!THIDS.contains(Integer.parseInt(rs.getString("THID"))))
	   		 		{
	   		 			THIDS.add(Integer.parseInt(rs.getString("THID")));
	   		 			output += rs.getString("THID") + ", " + rs.getString("THname") + "\n"; 
	   		 		}
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
			System.out.println("Here are the THID's of the temporary houses that you reserved and the names of them:");
			System.out.println(output);
			System.out.println("Select the THID of the house you want to record a stay at:");

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
				System.out.println("Wrong THID. Please select a reserved TH by THID next time. \n");
				return 0;
			}
		}
		else
		{
			System.out.println("There are no temporary houses that you reserved. You may reserve one. \n");
			return 0;
		}
	}
	
	public int displayAndSelectPidReservation(int THID, Statement stmt) throws IOException
	{
		String query = "Select p.pid, p.fromDate, p.toDate, r.cost From Reserve r, Period p Where r.THID = " + THID + " AND r.pid = p.pid";
		String output = "";
		ResultSet rs=null;
		List<Integer> PIDS = new ArrayList<Integer>();
		try
		{
	   		 	rs=stmt.executeQuery(query);
	   		 	while (rs.next())
				{
	   		 		PIDS.add(Integer.parseInt(rs.getString("pid")));
	   		 		output += rs.getString("pid") + ", " + rs.getString("fromDate") + ", " + rs.getString("toDate") + ", total cost $" + rs.getString("cost") + "\n"; 
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
		if(PIDS.size() > 0)
		{
			System.out.println("Here are the pid's of the dates that the house you are looking at that you reserved and the total cost.");
			System.out.println(output);
			System.out.println("Select the pid of the time you want to record a stay at the temporary house:");

		
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
			if(PIDS.contains(c))
			{
				return c; //returns pid
			}
			else
			{
				System.out.println("Wrong pid. Please select a reserved period by pid next time. \n");
				return 0;
			}
		}
		else
		{
			return 0;
		}
	}

	public void displayVisits(int THID, int pid, Statement stmt)
	{
		String sql = "Select t.THname, p.fromDate, p.toDate, r.cost From Period p, THData t, Reserve r Where p.pid = " + pid + " AND t.THID = " + THID + " AND r.THID = " + THID + " AND r.pid = " + pid;
		String output = "";
		ResultSet rs = null;
		try
		{
	   		 	rs=stmt.executeQuery(sql);
	   		 	while (rs.next())
				{
	   		 		output = rs.getString("THname") + ", " + rs.getString("fromDate") + ", " + rs.getString("toDate") + ", total cost $" + rs.getString("cost"); 
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
		System.out.println(output);
	}

	public void removeReservation(int THID, int pid, Connection conn) throws SQLException
	{
		String deleteSQL = "delete from Reserve where THID = ? AND pid = ?";
	    PreparedStatement preparedDeleteStmt = conn.prepareStatement(deleteSQL);
	    try
	    {
	    	preparedDeleteStmt.setInt(1, THID);
	    	preparedDeleteStmt.setInt(2, pid);
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
