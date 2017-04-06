package cs5530;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Pattern;

public class Feedback {
	
	public Feedback(){}
	
	public void insertFeedback(String input, Connection conn, Statement stmt) throws SQLException, IOException
	{
		
		String[] splitted = input.split(",");
		for(int i = 0; i < splitted.length; i++)
		{
			if(Pattern.matches("\\s*", splitted[i]))
			{
				splitted[i] = null;
			}
		}

		String countChecker = "select count(THID) from Feedback Where login = " + splitted[1] + " AND THID = " + splitted[0];
		
		String output = "";
		ResultSet rs = null;
		try
		{
			rs = stmt.executeQuery(countChecker);
			while (rs.next())
			{
				output = rs.getString("count(THID)");
			}
		}
   		catch(Exception e)
   		{
   			
   		}
		
		if(output.equals("1"))
		{
			System.out.println("You are not allowed to edit an existing feedback. \n");
		}
		else
		{
			String fidgetter = "select max(fid) from Feedback f";

			String output1 = "";
			ResultSet rs1 = null;
			try
			{
				rs1 = stmt.executeQuery(fidgetter);
				while (rs1.next())
				{
					output1 = rs1.getString("max(fid)");
				}
				rs1.close();
			}
	   		catch(Exception e)
	   		{
	   		}
	   		finally
	   		{
	   			try
	   			{
	   				if (rs1!=null && !rs.isClosed())
		   		 			rs1.close();
	   		 	}
	   		 	catch(Exception e)
	   		 	{
	   		 		//System.out.println("cannot close resultset");
	   		 	}
	   		}
			if(output1 == null || output1.equals(""))
			{
				output1 = "0";
			}
			int fid = Integer.parseInt(output1) + 1;

			String query = " insert into Feedback (fid, THID, login, feedText, score, fbdate)"
				     + " values (?, ?, ?, ?, ?, ?)";
			
			
			java.sql.Date currentDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
			try
			{
				java.sql.Date currentSQLDate = new java.sql.Date(currentDate.getTime());


				// create the mysql insert preparedstatement
				PreparedStatement preparedStmt = conn.prepareStatement(query);
				setValues(preparedStmt, splitted, fid, currentSQLDate); //if values are set correctly it returns the pid and if not return 0
			}
			catch(Exception e)
			{
				System.out.println("Dates not put in the correct format please try again. \n");
			}
		}
	}
	
	private static void setValues(PreparedStatement preparedStmt, String[] splitted, int fid, java.sql.Date date) throws SQLException, IOException
	{
		try
		{
			preparedStmt.setInt(1, fid);
			preparedStmt.setInt(2, Integer.parseInt(splitted[0]));
			preparedStmt.setString(3, splitted[1]);
			preparedStmt.setString(4, splitted[2]);
			preparedStmt.setInt(5, Integer.parseInt(splitted[3]));
			preparedStmt.setDate(6, date);
			
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

	public int selectFidFromTH(int THID, String login, Statement stmt) throws SQLException, IOException
	{
		String sql = "Select fid, login, feedText, score From Feedback Where THID = " + THID + " AND login <> '" + login + "'";
		
		List<Integer> FIDS = new ArrayList<Integer>();
		
		String output = "";
		ResultSet rs = null;
		rs = stmt.executeQuery(sql);
		try
		{
	   		 	rs=stmt.executeQuery(sql);
	   		 	while (rs.next())
				{
	   		 		FIDS.add(Integer.parseInt(rs.getString("fid")));
	   		 		output += rs.getString("fid") + ", " + rs.getString("login") + ", " + rs.getString("feedText") + ", " + rs.getString("score") + "\n"; 
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
		if(FIDS.size() > 0)
		{
			System.out.println("Here are the fid's, the user's name, the feedback text and the score of the feedback based on the searched THID:");
			System.out.println(output);
			System.out.println("Select the fid:");
		
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
			if(FIDS.contains(c))
			{
				return c; //returns THID
			}
			else
			{
				System.out.println("Feedback not selected correcly please try again. \n");
				return 0;
			}
		}
		else
		{
			System.out.println("There are no feedbacks listed for this THID (does not include your own). \n");
			return 0;
		}
	}
	
	public int selectFid(String login, Statement stmt) throws SQLException, IOException
	{
		String sql = "Select fid, login, feedText, score From Feedback Where login <> '" + login + "'";
		
		List<Integer> FIDS = new ArrayList<Integer>();
		
		String output = "";
		ResultSet rs = null;
		rs = stmt.executeQuery(sql);
		try
		{
	   		 	rs=stmt.executeQuery(sql);
	   		 	while (rs.next())
				{
	   		 		FIDS.add(Integer.parseInt(rs.getString("fid")));
	   		 		output += rs.getString("fid") + ", " + rs.getString("login") + ", " + rs.getString("feedText") + ", " + rs.getString("score") + "\n"; 
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
		if(FIDS.size() > 0)
		{
			System.out.println("Here are the fid's, the user's name, the feedback text and the score of the feedback:");
			System.out.println(output);
			System.out.println("Select the fid:");
		
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
			if(FIDS.contains(c))
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
			System.out.println("There are no feedbacks listed (does not include your own). \n");
			return 0;
		}
	}
}

