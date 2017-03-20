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

	public void displayHousesByCityOrState(String selection, String sortingMethod, Statement stmt)
	{
		String query = "Select THID, THname From THData Where city = '" + selection + "' OR state = '" + selection + "'";
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

		System.out.println("Here are the THID's and names that match by the city or state that you selected:");
		System.out.println(output);
	}

	public List<String> displayAndSelectKeywords(Statement stmt) throws IOException
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
		
		List<String> selectedWords = new ArrayList<String>();
		if(Keywords.size() > 0)
		{
			System.out.println("You can select up to 5 keywords. \n");
			
			System.out.println("Here are all the keywords that we have listed:");
			for(int i = 0; i < Keywords.size(); i++)
			{
				System.out.println(Keywords.get(i));
			}
			
			int count = 0;
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			
			while(count < 5)
			{	
				System.out.println("\n1. Add a keyword to browse by.");
				System.out.println("2. Done selecting keywords do browsing.");
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
							selectedWords.add(choice);
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
		return selectedWords;
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

	public void displayHousesByCategory(String selection, String sortingMethod, Statement stmt)
	{
		String query = "Select THID, THname From THData Where category = '" + selection + "'";
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

		System.out.println("Here are the THID's and names that match by the category that you selected:");
		System.out.println(output);
	}
}
