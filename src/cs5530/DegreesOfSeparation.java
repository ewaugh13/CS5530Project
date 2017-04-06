package cs5530;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

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
				degree = rs.getString("f1.login");
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
					degree = rs2.getString("f3.login");
					System.out.println(degree);
					
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
	
	public Entry<String, String> displayAllUsersInFavorites(Statement stmt) throws IOException, SQLException
	{
		String sql = "Select login From Favorites group by login";
		List<String> logins = new ArrayList<String>();
		
		String output = "";
		ResultSet rs = null;
		rs = stmt.executeQuery(sql);
		try
		{
	   		 	rs=stmt.executeQuery(sql);
	   		 	while (rs.next())
				{
	   		 		logins.add(rs.getString("login"));
	   		 		output += rs.getString("login") + "\n";
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
		if(logins.size() > 0)
		{
			String choice1 = "";
			String choice;
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Here are all the user logins that have favorited a house:");
			System.out.println(output);
			System.out.println("Select your first user:");
		
			while ((choice = in.readLine()) == null)
				;
			if(logins.contains(choice))
			{
				choice1 = choice; //returns login
			}
			else
			{
				System.out.println("Selected user does not exist in options avialable please try again");
				return new AbstractMap.SimpleEntry<String, String>("", "");
			}
			System.out.println("Select your second user:");
			while ((choice = in.readLine()) == null)
				;
			if(choice1.equals(choice))
			{
				System.out.println("Please do not select the same user as the previous one");
			}
			if(logins.contains(choice))
			{
				return new AbstractMap.SimpleEntry<String, String>(choice1, choice); //returns both logins
			}
			System.out.println("Selected user does not exist in options avialable please try again");
			return new AbstractMap.SimpleEntry<String, String>("", "");
		}
		else
		{
			System.out.println("There are no users who have favorited a house. \n");
			return new AbstractMap.SimpleEntry<String, String>("", "");
		}
	}
}
