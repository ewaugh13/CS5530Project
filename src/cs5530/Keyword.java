package cs5530;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Keyword 
{
	public Keyword(){}
	
	public int insertKeyword(String word, String language, Connection conn, Statement stmt) throws SQLException, IOException, ParseException, NumberFormatException
	{
		if(Pattern.matches("\\s*", word))
		{
			word = null;
		}
		if(Pattern.matches("\\s*", language))
		{
			language = null;
		}

		String widgetter = "select max(wid) from Keywords";

		String output = "";
		ResultSet rs = null;
		try
		{
			rs = stmt.executeQuery(widgetter);
			while (rs.next())
			{
				output = rs.getString("max(wid)");
			}
		}
   		catch(Exception e)
   		{	
   		}
		if(output == null || output.equals(""))
		{
			output = "0";
		}
		int wid = Integer.parseInt(output) + 1;
		
		output = "";
		String checker = "Select wid From Keywords Where word = '" + word + "'";
		ResultSet rs1 = null;
		try
		{
			rs1 = stmt.executeQuery(checker);
			while (rs1.next())
			{
				output = rs1.getString("wid");
			}
			rs1.close();
		}
   		catch(Exception e)
   		{
   			System.out.println(e);
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
		if(output == null || output.equals(""))
		{
			String query = " insert into Keywords (wid, word, wlanguage)"
					+ " values (?, ?, ?)";
			try
			{
				PreparedStatement preparedStmt = conn.prepareStatement(query);
				if(setValues(preparedStmt, wid, word, language)) //if values are set correctly it returns the wid and if not return 0
				{
					return wid;
				}
				return 0;	
			}
			catch(Exception e)
			{
				System.out.println("Word could not be added. Try again. \n");
				return 0;
			}
		}
		else
		{
			return Integer.parseInt(output);
		}
	}
	
	private static boolean setValues(PreparedStatement preparedStmt, int wid, String word, String language) throws SQLException, IOException
	{
		try
		{
			preparedStmt.setInt(1, wid);
			preparedStmt.setString(2, word);
			preparedStmt.setString(3, language);
			return exectueStmt(preparedStmt);
		}
		catch(Exception e)
		{
			System.out.println("Incorect values please try again. \n");
			return false;
		}
	}
	
	private static boolean exectueStmt(PreparedStatement preparedStmt) throws SQLException, IOException
	{
		try
		{
			preparedStmt.execute();
			return true;
		}
		catch (SQLException e)
		{
			System.out.println("Can not create Keyword. \n");
			return false;
		}
	}
	
	public void deleteWord(int wid, Connection conn) throws SQLException, IOException
	{
		String deleteSQL = "delete from Keywords where wid = ?";
	    PreparedStatement preparedDeleteStmt = conn.prepareStatement(deleteSQL);
	    
	    try
	    {
	    	preparedDeleteStmt.setInt(1, wid);
	    }
	    catch(Exception e)
	    {
	    	
	    }
	    
	    try
	    {
	    	preparedDeleteStmt.execute();
	    }
		
		catch(Exception e)
		{
			
		}
	}

	public List<Integer> userInput(Connection conn, Statement stmt) throws IOException, NumberFormatException, SQLException, ParseException
	{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("You may insert keywords about your temporary housing. \n");
		List<Integer> keyIDS =  new ArrayList<Integer>();
		while(true)
		{
			System.out.println("1. Insert new keyword");
			System.out.println("2. Finish adding keywords");
			System.out.println("please make a selection(if not 1 or 2 will automatically be 2):");
			
			String choice = "";
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
				String keyword;
				String language;
				
				System.out.println("please enter your key word(max 20 characters):");
				while ((keyword = in.readLine()) == null && keyword.length() == 0)
					;
				System.out.println("please enter the language of your keyword(optional):");
				while ((language = in.readLine()) == null && language.length() == 0)
					;
				keyIDS.add(insertKeyword(keyword, language, conn, stmt));
			}
			else
			{
				break;
			}
		}
		return keyIDS;
	}
	
	public List<Integer> userInputRemove(int THID, Connection conn, Statement stmt) throws IOException, NumberFormatException, SQLException, ParseException
	{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("You may remove keywords about your temporary housing. \n");
		
		String query = "Select k.wid, k.word From Keywords k, HasKeywords h Where h.wid = k.wid and h.THID = " + THID;
		String output = "";
		ResultSet rs=null;
		try
		{
	   		 	rs=stmt.executeQuery(query);
	   		 	while (rs.next())
				{
	   		 		output += rs.getString("wid") + ", " + rs.getString("word") + "\n";
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
		System.out.println("Here are the keywords by wid and the word for your house that you selected");
		System.out.println(output);
		
		List<Integer> keyIDS =  new ArrayList<Integer>();
		while(true)
		{
			System.out.println("1. Remove keyword");
			System.out.println("2. Finish removing keywords");
			System.out.println("please make a selection(if not 1 or 2 will automatically be 2):");
			
			String choice = "";
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
				String wid;
				
				System.out.println("please enter a wid to remove that keyword:");
				while ((wid = in.readLine()) == null && wid.length() == 0)
					;
				keyIDS.add(Integer.parseInt(wid));
			}
			else
			{
				break;
			}
		}
		return keyIDS;
	}

	public void insertIntoHasKeys(int THID, int wid, Connection conn) throws SQLException
	{
		String query = " insert into HasKeywords (THID, wid)"
				+ " values (?, ?)";
		PreparedStatement preparedStmt = conn.prepareStatement(query);
		try
		{
			preparedStmt.setInt(1, THID);
			preparedStmt.setInt(2, wid);
		}
		catch(Exception e)
		{
		}
		try
		{
			preparedStmt.execute();
		}
		catch(Exception e)
		{
		}
	}
	
	public void removeFromHasKeys(int THID, int wid, Connection conn) throws SQLException
	{
		String query = " delete from HasKeywords Where THID = ? AND wid = ?";
		PreparedStatement preparedStmt = conn.prepareStatement(query);
		try
		{
			preparedStmt.setInt(1, THID);
			preparedStmt.setInt(2, wid);
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		try
		{
			preparedStmt.execute();
		}
		catch(Exception e)
		{
		}
	}
}