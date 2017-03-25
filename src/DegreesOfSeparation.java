import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DegreesOfSeparation {
	
	public DegreesOfSeparation(){}
	
	public void separationGetter(String loginA, String loginB, Connection conn, Statement stmt) throws SQLException, IOException
	{
		String degree = "";
		String query = "Select f1.login, f2.login From Favorites f1, Favorites f2 Where f1.login <> f2.login AND f1.login = '" + loginA + "' and "
				+ "f2.login = '" + loginB + "' and f1.THID = f2.THID group by f1.login, f2.login";

		ResultSet rs = null;
		try
		{
			rs = stmt.executeQuery(query);
			while (rs.next())
			{
				degree = rs.getString("u1.login");
				
			}
			rs.close();
		}
   		catch(Exception e)
   		{
   			//System.out.println(e);
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
		if(!(degree.equals("") || degree == null))
		{
			System.out.println("Users " + loginA + " and " + loginB + " are separated by 1 degree. \n");
		}
		else
		{
			String query2 = "Select f3.login From Favorites f1, Favorites f2, Favorites f3, Favorites f4 Where f3.login = f4.login "
					+ "AND f3.login <> f2.login AND f2.login = '" + loginA + "' AND f3.THID = f2.THID AND f4.login <> f1.login AND f1.login = '" + loginB + "' "
					+ "AND f4.THID = f1.THID AND f1.THID <> f2.THID group by f3.login";
			ResultSet rs2 = null;
			try
			{
				rs2 = stmt.executeQuery(query2);
				while (rs2.next())
				{
					degree = rs2.getString("u1.login");
					
				}
				rs2.close();
			}
	   		catch(Exception e)
	   		{
	   			//System.out.println(e);
	   		}
	   		finally
	   		{
	   			try
	   			{
	   				if (rs2!=null && !rs2.isClosed())
	   					rs2.close();
	   		 	}
	   		 	catch(Exception e)
	   		 	{
	   		 		System.out.println("cannot close resultset");
	   		 	}
	   		}
			if(!(degree.equals("") || degree == null))
			{
				System.out.println("Users " + loginA + " and " + loginB + " are separated by 2 degrees. \n");
			}
			else
			{
				System.out.println("Users " + loginA + " and " + loginB + " are not separated by 1 or 2 degres. \n");

			}
		}
	}
	
}
