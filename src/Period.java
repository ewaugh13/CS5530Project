import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;
import java.util.Date;


public class Period 
{
	public Period(){}
	
	private static void insertPeriod(String dateFrom, String dateTo, Connection conn, Statement stmt) throws SQLException, IOException, ParseException
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
		rs = stmt.executeQuery(pidgetter);
		while (rs.next())
		{
			output = rs.getString("max(pid)");
		}
		if(output == "")
		{
			output = "0";
		}

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
			setValues(preparedStmt, Integer.getInteger(output), dateFromSQL, dateToSQL); //sets the values and calls execute
		}
		catch(Exception e)
		{
			System.out.println("Dates not put in the correct format please try again.");
		}
	}
	
	private static void setValues(PreparedStatement preparedStmt, int pid, java.sql.Date dateFrom, java.sql.Date dateTo) throws SQLException, IOException
	{
		try
		{
			preparedStmt.setInt(1, pid);
			preparedStmt.setDate(2, dateFrom);
			preparedStmt.setDate(3, dateTo);
			exectueStmt(preparedStmt);
		}
		catch(Exception e)
		{
			System.out.println("Incorect values please try again.");
		}
		
	}
	
	private static void exectueStmt(PreparedStatement preparedStmt) throws SQLException, IOException
	{
		try
		{
			preparedStmt.execute();
			System.out.println("Successfully created period. \n");
		}
		catch (SQLException e)
		{
			System.out.println("Can not create the period. \n");
		}
	}
}
