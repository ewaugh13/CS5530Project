package cs5530;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Browsing 
{
	public Browsing(){}
	
	public String displayAndSelectCityOrState(Statement stmt) throws IOException
	{
		String query = "Select city, state From THData";
		List<String> States = new ArrayList<String>();
		List<String> Cities = new ArrayList<String>();
		ResultSet rs=null;
		try
		{
	   		 	rs=stmt.executeQuery(query);
	   		 	while (rs.next())
				{
	   		 		if(!States.contains(rs.getString("state")))
	   		 		{
	   		 			States.add(rs.getString("state")); 
	   		 		}
	   		 		if(!Cities.contains(rs.getString("city")))
	   		 		{
	   		 			Cities.add(rs.getString("city")); 
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
		if(States.size() > 0 || Cities.size() > 0)
		{
			System.out.println("Here are all the states that we have temporary houses listed:");
			for(int i = 0; i < States.size(); i++)
			{
				System.out.println(States.get(i));
			}
			
			System.out.println("\nHere are all the cities that we have temporary houses listed:");
			for(int i = 0; i < Cities.size(); i++)
			{
				System.out.println(Cities.get(i));
			}
			System.out.println("\nPlease make a selection of city or state that is listed:");
			
			String choice;
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
			while ((choice = in.readLine()) == null)
				;
			if(States.contains(choice) || Cities.contains(choice))
			{
				System.out.println("Selection made. \n");
				return choice; //returns state or city
			}
			else
			{
				System.out.println("Wrong city or state. Please try again with a city or state that has a temporary house listed. \n");
				return "";
			}
		}
		else
		{
			System.out.println("There are no temporary houses available so you can select a state or city. \n");
			return "";
		}
	}

	public List<Integer> displayAndSelectKeywords(Statement stmt) throws IOException
	{
		String query = "Select word From Keywords";
		List<String> Keywords = new ArrayList<String>();
		ResultSet rs=null;
		try
		{
	   		 	rs=stmt.executeQuery(query);
	   		 	while (rs.next())
				{
	   		 		Keywords.add(rs.getString("word")); 
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
		
		List<Integer> selectedWIDS = new ArrayList<Integer>();
		if(Keywords.size() > 0)
		{
			System.out.println("You can select up to 20 keywords. \n");
			
			System.out.println("Here are all the keywords that we have listed:");
			for(int i = 0; i < Keywords.size(); i++)
			{
				System.out.println(Keywords.get(i));
			}
			
			int count = 0;
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			
			while(count < 20)
			{	
				System.out.println("\n1. Add a keyword to browse by.");
				System.out.println("2. Done selecting keywords for browsing.");
				System.out.println("please make a selection (if not 1 or 2 will automatically be 2):");
				String choice;
				int c = 0;
				
				while ((choice = in.readLine()) == null && choice.length() == 0)
					;
				try 
				{
					c = Integer.parseInt(choice);
				} 
				catch (Exception e) 
				{
					c = 2;
				}
				
				if(c == 1)
				{
					if(Keywords.size() > 0)
					{
						System.out.println("\nPlease make a selection of keyword that is listed:");
					
						while ((choice = in.readLine()) == null)
							;
						if(Keywords.contains(choice))
						{
							System.out.println("Selection made.");
							String sqlConvert = "Select wid From Keywords where word = '" + choice + "'";
							
							ResultSet rs1=null; 
							try
							{
									rs1=stmt.executeQuery(sqlConvert);
						   		 	while (rs1.next())
									{
						   		 		selectedWIDS.add(Integer.parseInt(rs1.getString("wid")));
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
					   				if (rs1!=null && !rs1.isClosed())
					   					rs1.close();
					   		 	}
					   		 	catch(Exception e)
					   		 	{
					   		 		System.out.println("cannot close resultset");
					   		 	}
					   		}
						}
						else
						{
							System.out.println("Wrong keyword. Please try again with a keyword that is listed. \n");
						}
					}
					else
					{
						System.out.println("There are no more keywords to select. \n");
						break;
					}
				}
				else
				{
					break;
				}
				count++;
			}
		}
		else
		{
			System.out.println("There are not keywords to select. \n");
		}
		return selectedWIDS;
	}
		
	public String displayAndSelectCategory(Statement stmt) throws IOException
	{
		String query = "Select category From THData";
		List<String> categories = new ArrayList<String>();
		ResultSet rs=null;
		try
		{
	   		 	rs=stmt.executeQuery(query);
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
		if(categories.size() > 0)
		{
			System.out.println("Here are all the categories of houses that we have listed:");
			for(int i = 0; i < categories.size(); i++)
			{
				System.out.println(categories.get(i));
			}

			System.out.println("\nPlease make a selection of category that is listed:");
			
			String choice;
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
			while ((choice = in.readLine()) == null)
				;
			if(categories.contains(choice))
			{
				System.out.println("Selection made. \n");
				return choice; //returns state or city
			}
			else
			{
				System.out.println("Wrong category. Please try again with a category that has a temporary house listed. \n");
				return "";
			}
		}
		else
		{
			System.out.println("There are no temporary houses available so you can select a category. \n");
			return "";
		}
	}
	
	public void displayHouses(String login, int lowPrice, int highPrice, boolean price, String selectCityOrState, boolean cityOrState, boolean orAndCityState, String selectCategory, boolean category, boolean orAndCategory, String sortingMethod, Statement stmt)
	{
		String query = "";
		if(price)
		{
			query = "Select t0.THID, t0.THName From Available a, THData t0 Where a.THID = t0.THID AND a.pricePerNight >= " + lowPrice + " AND a.pricePerNight <= " + highPrice;
			if(cityOrState)
			{
				if(orAndCityState) //and case
				{
					query += " AND ";
				}
				else
				{
					query += " OR ";
				}
			}
			else if(category)
			{
				if(orAndCategory) //and case
				{
					query += " AND ";
				}
				else
				{
					query += " OR ";
				}
			}
		}
		else if(sortingMethod.equals("price"))
		{
			query = "Select t0.THID, t0.THName From Available a, THData t0 Where a.THID = t0.THID AND ";
		}
		else if(sortingMethod.equals("feedback"))
		{
			query = "Select t0.THID, t0.THName From Feedback f, THData t0 Where f.THID = t0.THID AND ";
		}
		else if(sortingMethod.equals("trust"))
		{
			query = "Select t0.THID, t0.THName From Feedback f, THData t0, Trust tr Where f.THID = t0.THID AND f.login = tr.login1 and tr.isTrusted = true and tr.login1 = " + login + " AND ";
		}
		else
		{
			query = "Select t0.THID, t0.THname From THData t0 Where";
		}
		if(cityOrState)
		{
			query += " t0.city = '" + selectCityOrState + "' OR t0.state = '" + selectCityOrState + "'";
			if(category)
			{
				if(orAndCategory) //and case
				{
					query += " AND ";
				}
				else
				{
					query += " OR ";
				}
			}
		}
		if(category)
		{
			query += " t0.category = '" + selectCategory + "'";
		}
		query += " Group by t0.THID";
		if(sortingMethod.equals("price"))
		{
			query += " order by pricePerNight asc";
		}
		else if(sortingMethod.equals("feedback"))
		{
			query += " order by avg(f.score) desc";
		}
		else if(sortingMethod.equals("trust"))
		{
			query += " order by avg(f.score) desc";
		}
		String output = "";
		ResultSet rs=null;
		try
		{
	   		 	rs=stmt.executeQuery(query);
	   		 	while (rs.next())
				{
	   		 		output += rs.getString("THID") + ", " + rs.getString("THname") + "\n"; 
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

		if(sortingMethod.equals("price"))
		{
			System.out.println("Here are the THID's and names based on your selection sorted by " + sortingMethod + "(if nothing printed then nothing matched):");
		}
		else if(sortingMethod.equals("feedback"))
		{
			System.out.println("Here are the THID's and names based on your selection sorted by " + sortingMethod + " score " + "(if nothing printed then nothing matched):");
		}
		else
		{
			System.out.println("Here are the THID's and names based on your selection sorted by " + sortingMethod + " score of users you trust " + "(if nothing printed then nothing matched):");
		}
		System.out.println(output);
	}

	public void displayHousesByKeywords(String login, List<Integer> wids, int lowPrice, int highPrice, boolean price, boolean orAndPrice, String selectCityOrState, boolean cityOrState, boolean orAndCityState, String selectCategory, boolean category, boolean orAndCategory, String sortingMethod, Statement stmt)
	{
		String query = "Select t0.THID, t0.THname From";
		for(int i = 0; i < wids.size(); i++)
		{
			if(i == wids.size() - 1)
			{
				query += " THData t" + i + ", HasKeywords h" + i;
			}
			else
			{
				query += " THData t" + i + ", HasKeywords h" + i + ",";
			}
		}
		if(price)
		{
			query += ", Available a Where a.THID = t0.THID AND a.pricePerNight >= " + lowPrice + " AND a.pricePerNight <= " + highPrice;
			if(orAndPrice) //and case
			{
				query += " AND ";
			}
			else
			{
				query += " OR ";
			}
		}
		else if(sortingMethod.equals("price"))
		{
			query += ", Available a Where a.THID = t0.THID AND ";
		}
		else if(sortingMethod.equals("feedback"))
		{
			query += ", Feedback f Where f.THID = t0.THID AND ";
		}
		else if(sortingMethod.equals("trust"))
		{
			query += ", Feedback f, Trust tr Where f.THID = t0.THID AND f.login = tr.login1 and tr.isTrusted = true and tr.login1 = " + login + " AND ";
		}
		else
		{
			query += " Where ";
		}
		for(int i = 0; i < wids.size(); i++) //add wheres with equal thid
		{
			query += "t" + i + ".THID = h" + i + ".THID AND ";
		}
		for(int i = 0; i < wids.size(); i++) //add where with equal wid
		{
			if(i == wids.size() - 1)
			{
				query += "h" + i + ".wid = " + wids.get(i);
			}
			else
			{
				query += "h" + i + ".wid = " + wids.get(i) + " AND ";
			}
		}
		if(cityOrState)
		{
			if(orAndCityState) //and case
			{
				query += " AND ";
			}
			else
			{
				query += " OR ";
			}
			query += " t0.city = '" + selectCityOrState + "' OR t0.state = '" + selectCityOrState + "'";
		}
		if(category)
		{
			if(orAndCategory) //and case
			{
				query += " AND ";
			}
			else
			{
				query += " OR ";
			}
			query += " t0.category = '" + selectCategory + "'";
		}
		query += " Group by t0.THID";
		if(sortingMethod.equals("price"))
		{
			query += " order by pricePerNight asc";
		}
		else if(sortingMethod.equals("feedback"))
		{
			query += " order by avg(f.score) desc";
		}
		else if(sortingMethod.equals("trust"))
		{
			query += " order by avg(f.score) desc";
		}
		String output = "";
		ResultSet rs=null;
		try
		{
			rs=stmt.executeQuery(query);
		   	while (rs.next())
			{
		   		output += rs.getString("THID") + ", " + rs.getString("THname") + "\n"; 
			} 
		   		rs.close();
	   	}
	   	catch(Exception e)
	   	{
	   		System.out.println(e);
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
		
		if(sortingMethod.equals("price"))
		{
			System.out.println("Here are the THID's and names based on your selection sorted by " + sortingMethod + "(if nothing printed then nothing matched):");
		}
		else if(sortingMethod.equals("feedback"))
		{
			System.out.println("Here are the THID's and names based on your selection sorted by " + sortingMethod + " score " + "(if nothing printed then nothing matched):");
		}
		else
		{
			System.out.println("Here are the THID's and names based on your selection sorted by " + sortingMethod + " score of users you trust " + "(if nothing printed then nothing matched):");
		}
		System.out.println(output);
	}

}
