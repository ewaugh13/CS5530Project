
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class UsefulFeedbacks {
	
	public UsefulFeedbacks(){}
	
	public void getUsefulFeedbacks(int THID, int numFeedbcks, Statement stmt) throws SQLException, IOException
	{
		String query = "Select f.fid, f.feedText, avg(rating) From Feedback f, Rates r Where f.fid = r.fid AND f.THID = " + THID + " group by fid order by avg(rating) desc limit " + numFeedbcks;
		ResultSet rs=null;
		String output = "";
		try
		{
	   		 	rs=stmt.executeQuery(query);
	   		 	while (rs.next())
				{
	   		 		output += rs.getString("fid") + ", the feedback is " + rs.getString("feedText") + ", the average score is " + rs.getString("avg(rating)") + "\n";
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
		
		System.out.println("Here are the fids of the feedback and the text of the selected temporary house and average score of that said feedback.");
		System.out.println(output);
	}
	
}
