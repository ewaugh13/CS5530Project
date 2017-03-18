import java.io.IOException;
import java.sql.*;
import java.util.Calendar;
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
		
		String query = " insert into Feedback (fid, THID, login, feedText, score, fbdate)"
			     + " values (?, ?, ?, ?, ?, ?)";
		
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
		
		if(output == "1")
		{
			System.out.println("You are not allowed to edit an existing feedback. \n");
		}
		else
		{
			String pidgetter = "select max(fid) from Feedback";
			
			
			String output1 = "";
			ResultSet rs1 = null;
			try
			{
				rs1 = stmt.executeQuery(pidgetter);
				while (rs1.next())
				{
					output = rs1.getString("max(fid)");
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

			String query1 = " insert into Feedback (fid, THID, login, feedText, score, fbdate"
					+ " values (?, ?, ?, ?, ?, ?)";
			
			
			java.sql.Date currentDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
			try
			{
				

				
				java.sql.Date currentSQL = new java.sql.Date(currentDate.getTime());


				// create the mysql insert preparedstatement
				PreparedStatement preparedStmt = conn.prepareStatement(query);
				setValues(preparedStmt, splitted, fid, currentSQL); //if values are set correctly it returns the pid and if not return 0
				
				
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
}

