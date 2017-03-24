import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/*
 * [5pts] User awards: At random points of time, the admin user wants to give awards to the ‘best’ users; thus, the admin user needs to know:
• the top m most ‘trusted’ users (the trust score of a user is the count of users ‘trusting’ him/her, minus the count of users ‘not-trusting him/her) 
• the top m most ‘useful’ users (the usefulness score of a user is the average ‘usefulness’ of all of his/her feedbacks combined)
 */

public class UserAwards {
	
	public UserAwards(){}
	
	public boolean AdminChecker(String login, Connection conn, Statement stmt) throws SQLException, IOException
	{
		String isAdmin = "0";
		String sql = "select userType from Users where login = '" + login + "'";
		
		ResultSet rs = null;
		try
		{
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				isAdmin = rs.getString("userType");
				
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
		if(isAdmin.equals("1"))
		{
			return true;
		}
		else
		{
			System.out.println("Sorry, this is an Admin-only feature. \n");
			return false;
		}
	}
	
	public void mostTrustedUsers(int m, Connection conn, Statement stmt) throws SQLException, IOException
	{
		String query = "select t2.login2, (count(t2.login1)-(select count(t3.login1) from Trust t3 where t3.login2 = t2.login2 and t3.istrusted = false)) as C from Trust t2 "
				+ "where t2.isTrusted = true group by (t2.login2) order by C desc limit " + m;
		
		ResultSet rs=null;
		String output = "";
		try
		{
	   		 	rs=stmt.executeQuery(query);
	   		 	while (rs.next())
				{
	   		 		output += "The User is " + rs.getString("login2") + ", their Score is " + rs.getString("C") + "\n";
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
		
		System.out.println("Here are the top " + m + " most trusted users and their trustworthiness scores. \n");
		System.out.println(output);
	}
	
	public void mostUsefulUsers(int m, Connection conn, Statement stmt) throws SQLException, IOException
	{
		String query = "select u.login, avg(r.rating) as R from Users u, Rates r, Feedback f where f.login = u.login and f.fid = r.fid group by u.login order by R desc limit " + m;
		
		ResultSet rs=null;
		String output = "";
		try
		{
	   		 	rs=stmt.executeQuery(query);
	   		 	while (rs.next())
				{
	   		 		output += "The User is " + rs.getString("login") + ", their Score is " + rs.getString("R") + "\n";
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
		
		System.out.println("Here are the top " + m + " most useful users and their usefulness scores. \n");
		System.out.println(output);
	}
	

}
