import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class Available 
{
	public Available(){}
	
	public void createAvailableTime(int pricePerNight, int THID, Statement stmt, Connection conn) throws IOException, SQLException, ParseException
	{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		Period per = new Period();
		String dateFrom; 
		String dateTo;
		
		System.out.println("please enter a start for the reservation (in the format of yyyy/MM/dd):");
		while ((dateFrom = in.readLine()) == null && dateFrom.length() == 0)
			;
		System.out.println("please enter a end for the reservation (in the format of yyyy/MM/dd):");
		while ((dateTo = in.readLine()) == null && dateTo.length() == 0)
			;
		
		int pid = per.insertPeriod(dateFrom, dateTo, conn, stmt);
		if(pid > 0)
		{	
			String query = " insert into Available (pid, THID, pricePerNight)"
					+ " values (?, ?, ?)";

			// create the mysql insert preparedstatement
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			try
			{
				preparedStmt.setInt(1, pid);
				preparedStmt.setInt(2, THID);
				preparedStmt.setInt(3, pricePerNight);
			}
			catch(Exception e)
			{
				System.out.println("Value inserted wrong. Try again and double check the price per night and the dates inserted. \n");
			}
			
			try
			{
				preparedStmt.execute();
				System.out.println("Period of availability created. \n");
			}
			catch(Exception e)
			{
				System.out.println("Could not create the available time try again. \n");
				//deleate the pid created
			}
		}
	}

	public int displayAndSelectTHAvialable(Statement stmt, Connection conn) throws IOException
	{
		String query = "Select t.THID, t.THname From THData t, Available a Where a.THID = t.THID";
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
			System.out.println("Here are the THID's of the temporary houses you that are avilable and the names of them:");
			System.out.println(output);
			System.out.println("Select the THID of the house you want to view dates avialable:");

		
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
				System.out.println("Wrong THID. Please select an availabe TH by THID next time. \n");
				return 0;
			}
		}
		else
		{
			System.out.println("There are no temporary houses available sorry. \n");
			return 0;
		}
	}

	public int displayAndSelectPidAvialable(int THID, Statement stmt, Connection conn) throws IOException
	{
		String query = "Select p.pid, p.fromDate, p.toDate, a.pricePerNight From Available a, Period p Where a.THID = " + THID + " AND a.pid = p.pid";
		String output = "";
		ResultSet rs=null;
		List<Integer> PIDS = new ArrayList<Integer>();
		try
		{
	   		 	rs=stmt.executeQuery(query);
	   		 	while (rs.next())
				{
	   		 		PIDS.add(Integer.parseInt(rs.getString("pid")));
	   		 		output += rs.getString("pid") + ", " + rs.getString("fromDate") + ", " + rs.getString("toDate") + ", price per night $" + rs.getString("pricePerNight") + "\n"; 
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
			System.out.println("Here are the pid's of the dates that the house you are looking at are available:");
			System.out.println(output);
			System.out.println("Select the pid of the time you want to reserve the temporary house:");

		
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
				return c; //returns THID
			}
			else
			{
				System.out.println("Wrong pid. Please select an availabe period by pid next time. \n");
				return 0;
			}
		}
		else
		{
			System.out.println("There are no temporary houses available sorry. \n");
			return 0;
		}
	}

	public void displayReservations(int THID, int pid, Statement stmt)
	{
		String sql = "Select t.THname, p.fromDate, p.toDate, a.pricePerNight From Period p, THData t, Available a Where p.pid = " + pid + " AND t.THID = " + THID + " AND a.THID = " + THID + " AND a.pid = " + pid;
		String output = "";
		ResultSet rs = null;
		try
		{
	   		 	rs=stmt.executeQuery(sql);
	   		 	while (rs.next())
				{
	   		 		output = rs.getString("THname") + ", " + rs.getString("fromDate") + ", " + rs.getString("toDate") + ", per night $" + rs.getString("pricePerNight"); 
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

	public void removeAvailabe(int THID, int pid, Connection conn) throws SQLException
	{
		String deleteSQL = "delete from Available where THID = ? AND pid = ?";
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
