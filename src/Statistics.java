import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Statistics 
{
	public Statistics(){}
	
	public List<String> getAllCategoriesInVisit(Statement stmt) throws SQLException
	{
		List<String> categories = new ArrayList<String>();
		String sql = "Select t.category From THData t, Visit v Where v.THID = t.THID group by t.THID, t.category";
		
		ResultSet rs = null;
		rs = stmt.executeQuery(sql);
		try
		{
	   		 	rs=stmt.executeQuery(sql);
	   		 	while (rs.next())
				{
	   		 		if(!categories.contains(rs.getString("category")))
	   		 		{
	   		 			categories.add(rs.getString("category"));
	   		 		}
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
		return categories;
	}
	
	public List<String> getAllCategoriesInFeedback(Statement stmt) throws SQLException
	{
		List<String> categories = new ArrayList<String>();
		String sql = "Select t.category From THData t, Feedback f Where f.THID = t.THID group by t.THID, t.category";
		
		ResultSet rs = null;
		rs = stmt.executeQuery(sql);
		try
		{
	   		 	rs=stmt.executeQuery(sql);
	   		 	while (rs.next())
				{
	   		 		if(!categories.contains(rs.getString("category")))
	   		 		{
	   		 			categories.add(rs.getString("category"));
	   		 		}
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
		return categories;
	}

	public void statForMostVisited(List<String> categories, Statement stmt, int limitAmount) throws SQLException
	{
		String sql = "";
		for(int i = 0; i < categories.size(); i++)
		{
			if(i == categories.size() - 1)
			{
				sql += "(Select t.THID, count(t.THID), t.category FROM Visit v, THData t Where v.THID = t.THID and t.category = '" + categories.get(i) + "' group by t.THID, t.category order by category, count(t.THID) desc limit " + limitAmount + ")";
			}
			else
			{
				sql += "(Select t.THID, count(t.THID), t.category FROM Visit v, THData t Where v.THID = t.THID and t.category = '" + categories.get(i) + "' group by t.THID, t.category order by category, count(t.THID) desc limit " + limitAmount + ") union all ";
			}
		}
		
		Map<String, String> output = new HashMap<String, String>();
		ResultSet rs = null;
		rs = stmt.executeQuery(sql);
		try
		{
	   		 	rs=stmt.executeQuery(sql);
	   		 	while (rs.next())
				{
	   		 		if(output.containsKey(rs.getString("category")))
	   		 		{
	   		 			String addOn = rs.getString("THID") + ", " + rs.getString("count(t.THID)") + "\n";
	   		 			output.put(rs.getString("category"), output.get(rs.getString("category")) + addOn);
	   		 		}
	   		 		else
	   		 		{
	   		 			String addOn = rs.getString("THID") + ", " + rs.getString("count(t.THID)") + "\n";
	   		 			output.put(rs.getString("category"), addOn);
	   		 		}
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
		
		for ( Map.Entry<String, String> entry : output.entrySet() ) 
		{
		    String key = entry.getKey();
		    String value = entry.getValue();
		    System.out.println("For the category " + key + " here are the results with the THID and the number of times it was visited:");
		    System.out.println(value);
		}
	}

	public void statMostExpensive(List<String> categories, Statement stmt, int limitAmount) throws SQLException
	{
		String sql = "";
		for(int i = 0; i < categories.size(); i++)
		{
			if(i == categories.size() - 1)
			{
				sql += "(Select t.THID, avg(v.cost), t.category FROM Visit v, THData t Where v.THID = t.THID and t.category = '" + categories.get(i) + "' group by t.THID, t.category order by category, count(v.cost) desc limit " + limitAmount + ")";
			}
			else
			{
				sql += "(Select t.THID, avg(v.cost), t.category FROM Visit v, THData t Where v.THID = t.THID and t.category = '" + categories.get(i) + "' group by t.THID, t.category order by category, count(v.cost) desc limit " + limitAmount + ") union all ";
			}
		}
		
		Map<String, String> output = new HashMap<String, String>();
		ResultSet rs = null;
		rs = stmt.executeQuery(sql);
		try
		{
	   		 	rs=stmt.executeQuery(sql);
	   		 	while (rs.next())
				{
	   		 		if(output.containsKey(rs.getString("category")))
	   		 		{
	   		 			String addOn = rs.getString("THID") + ", " + rs.getString("avg(v.cost)") + "\n";
	   		 			output.put(rs.getString("category"), output.get(rs.getString("category")) + addOn);
	   		 		}
	   		 		else
	   		 		{
	   		 			String addOn = rs.getString("THID") + ", " + rs.getString("avg(v.cost)") + "\n";
	   		 			output.put(rs.getString("category"), addOn);
	   		 		}
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
		
		for ( Map.Entry<String, String> entry : output.entrySet() ) 
		{
		    String key = entry.getKey();
		    String value = entry.getValue();
		    System.out.println("For the category " + key + " here are the results with the THID and avg cost of visiting:");
		    System.out.println(value);
		}
	}

	public void statMostPopular(List<String> categories, Statement stmt, int limitAmount) throws SQLException
	{
		String sql = "";
		for(int i = 0; i < categories.size(); i++)
		{
			if(i == categories.size() - 1)
			{
				sql += "(Select t.THID, avg(f.score), t.category FROM Feedback f, THData t Where f.THID = t.THID and t.category = '" + categories.get(i) + "' group by t.THID, t.category order by category, count(f.score) desc limit " + limitAmount + ")";
			}
			else
			{
				sql += "(Select t.THID, avg(f.score), t.category FROM Feedback f, THData t Where f.THID = t.THID and t.category = '" + categories.get(i) + "' group by t.THID, t.category order by category, count(f.score) desc limit " + limitAmount + ") union all ";
			}
		}
		
		Map<String, String> output = new HashMap<String, String>();
		ResultSet rs = null;
		rs = stmt.executeQuery(sql);
		try
		{
	   		 	rs=stmt.executeQuery(sql);
	   		 	while (rs.next())
				{
	   		 		if(output.containsKey(rs.getString("category")))
	   		 		{
	   		 			String addOn = rs.getString("THID") + ", " + rs.getString("avg(f.score)") + "\n";
	   		 			output.put(rs.getString("category"), output.get(rs.getString("category")) + addOn);
	   		 		}
	   		 		else
	   		 		{
	   		 			String addOn = rs.getString("THID") + ", " + rs.getString("avg(f.score)") + "\n";
	   		 			output.put(rs.getString("category"), addOn);
	   		 		}
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
		
		for ( Map.Entry<String, String> entry : output.entrySet() ) 
		{
		    String key = entry.getKey();
		    String value = entry.getValue();
		    System.out.println("For the category " + key + " here are the results with the THID and avg score of user feedbacks:");
		    System.out.println(value);
		}
	}
}
