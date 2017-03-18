import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.text.ParseException;

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
				per.deletePeriod(pid, conn);
			}
		}
	}
}
