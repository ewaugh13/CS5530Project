import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class RecommendedTH {
	
	public RecommendedTH(){}
	

	public void getRecommendedTH(int THID, Statement stmt) throws SQLException, IOException
	{
		String query = "Select v1.THID, t.THname, count(v1.THID) From Visit v, Visit v1, THData t Where v.login = v1.login and v.THID <> v1.THID and v.THID = " + THID + " "
				+ "and t.THID = v1.THID group by v1.THID order by count(v1.THID) desc";
		ResultSet rs=null;
		String output = "";
		try
		{
	   		 	rs=stmt.executeQuery(query);
	   		 	while (rs.next())
				{
	   		 		output += "The THID is " + rs.getString("THID") + ", the Name is " + rs.getString("THName") + "\n";
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
		
		System.out.println("Based on your Reservation of " + THID + ", here are some other Temporary Houses you might be interested in.");
		System.out.println(output);
	}

}
