import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;
import java.util.Date;


public class Period 
{
	public Period(){}
	
	public int insertPeriod(String dateFrom, String dateTo, Connection conn, Statement stmt) throws SQLException, IOException, ParseException, NumberFormatException
	{

		if(Pattern.matches("\\s*", dateFrom))
		{
			dateFrom = null;
		}
		else if(Pattern.matches("\\s*", dateTo))
		{
			dateTo = null;
		}
		String pidgetter = "select max(pid) from Period";
		
		
		String output = "";
		ResultSet rs = null;
		try
		{
			rs = stmt.executeQuery(pidgetter);
			while (rs.next())
			{
				output = rs.getString("max(pid)");
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
		int pid = Integer.parseInt(output) + 1;

		String query = " insert into Period (pid, fromDate, toDate)"
				+ " values (?, ?, ?)";
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
		
		try
		{
			Date parsedTo = format.parse(dateTo);
			Date parsedFrom = format.parse(dateFrom);
			
			java.sql.Date dateFromSQL = new java.sql.Date(parsedFrom.getTime());
			java.sql.Date dateToSQL = new java.sql.Date(parsedTo.getTime());

			// create the mysql insert preparedstatement
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			if(setValues(preparedStmt, pid, dateFromSQL, dateToSQL)) //if values are set correctly it returns the pid and if not return 0
			{
				return pid;
			}
			return 0;
			
		}
		catch(Exception e)
		{
			System.out.println("Dates not put in the correct format please try again. \n");
			return 0;
		}
	}
	
	private static boolean setValues(PreparedStatement preparedStmt, int pid, java.sql.Date dateFrom, java.sql.Date dateTo) throws SQLException, IOException
	{
		try
		{
			preparedStmt.setInt(1, pid);
			preparedStmt.setDate(2, dateFrom);
			preparedStmt.setDate(3, dateTo);
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
			System.out.println("Can not create the period. \n");
			return false;
		}
	}
}
