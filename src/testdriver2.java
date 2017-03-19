
import java.sql.*;
import java.text.ParseException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.*;

public class testdriver2 {

	/**
	 * @param args
	 */
	public static void loginOrCreateAccount() 
	{
		// add more options I think based on the 14 sections but there could be
		// more?
		System.out.println("        Welcome to the Uotel System     ");
		System.out.println("1. Register into Uotel System");
		System.out.println("2. Login into Uotel System");
		System.out.println("3. exit Uotel System");
		System.out.println("please enter your choice:");
	}

	public static void displayUserOptions()
	{
		System.out.println("1. Reserve a Uotel temorary housing");
		System.out.println("2. List or update a temporary housing you own. Can also add a period available for your TH");
		System.out.println("3. Record a place you stayed at");
		System.out.println("4. Record your favorite place to stay");
		System.out.println("5. Record feedback on a temporary house");
		System.out.println("6. Assess a feedback given");
		System.out.println("7. Declare a user as trustworthy or not");
		System.out.println("8. exit Uotel System");
		System.out.println("please enter your choice:");
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Connector con = null;
		String choice;
		int c = 0;
		try {
			// remember to replace the password
			con = new Connector();
			System.out.println("Database connection established");

			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			String login;
			login = userCreateOrLogin(in, con);
			if(!login.equals(""))
			{
				userOptions(in, con, login);
			}

		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			System.err.println("Either connection error or query execution error!");
		} 
		finally 
		{
			if (con != null) 
			{
				try 
				{
					con.closeConnection();
				}
				catch (Exception e) 
				{
					/* ignore close errors */
				}
			}
		}
	}

	public static String userCreateOrLogin(BufferedReader in, Connector con) throws IOException, SQLException {
		String choice;
		int c = 0;
		while (true) {
			loginOrCreateAccount();
			String login;
			String password;
			while ((choice = in.readLine()) == null && choice.length() == 0)
				;
			try {
				c = Integer.parseInt(choice);
			} catch (Exception e) {

				continue;
			}
			if (c < 1 | c > 3)
				continue;
			if (c == 1) // creating a user
			{
				String fullName;
				String age; // going to need to parse this one when inserting
				String email;
				String phoneNumber;
				String address;
				String userType;

				System.out.println("please enter a login name:");
				while ((login = in.readLine()) == null && login.length() == 0)
					;
				System.out.println("please enter a password:");
				while ((password = in.readLine()) == null && password.length() == 0)
					;
				System.out.println("please enter your full name:");
				while ((fullName = in.readLine()) == null && fullName.length() == 0)
					;
				System.out.println("please enter your age(have to be 18 or older to use Uotel):");
				while ((age = in.readLine()) == null && age.length() == 0)
					;
				System.out.println("please enter your email:");
				while ((email = in.readLine()) == null && email.length() == 0)
					;
				System.out.println("you can enter your phone number(optional) in the format of 303-987-1234:");
				while ((phoneNumber = in.readLine()) == null && phoneNumber.length() == 0)
					;
				System.out.println("you can enter your address(optional):");
				while ((address = in.readLine()) == null && address.length() == 0)
					;
				System.out.println("are you a admin(yes or no):");
				while ((userType = in.readLine()) == null && userType.length() == 0)
					;

				User users = new User();
				String input = login + "," + password + "," + fullName + "," + age + "," + email + "," + phoneNumber
						+ "," + address + "," + userType;
				boolean resultOfCreate = users.insertUser(input, con.con);
				if(resultOfCreate)
				{
					return login;
				}
			} 
			else if (c == 2) // login by giving a user login and matching password
			{
				System.out.println("please enter a login name:");
				while ((login = in.readLine()) == null && login.length() == 0)
					;
				System.out.println("please enter a password:");
				while ((password = in.readLine()) == null && password.length() == 0)
					;
				User users = new User();
				String input = login + "," + password;
				boolean loginResult = users.loginIntoAccount(input, con.stmt);
				if (loginResult) 
				{
					System.out.println("successful login \n");
					return login;
				} 
				else 
				{
					System.out.println("login and password did not match please try again or create account if you don't have one");
				}
			} 
			else 
			{
				System.out.println("Thank you for using Uotel System. \n");
				con.stmt.close();
				return "";
			}
		}
	}

	public static void userOptions(BufferedReader in, Connector con, String login) throws Exception 
	{
		String choice;
		int c = 0;
		List<Map.Entry<Integer, Integer>> dictionaryOfReserveIDs = new ArrayList<Map.Entry<Integer, Integer>>();
		List<Map.Entry<Integer, Integer>> dictionaryOfVisitIDs = new ArrayList<Map.Entry<Integer, Integer>>();
		while (true) 
		{
			displayUserOptions();
			
			while ((choice = in.readLine()) == null && choice.length() == 0)
				;
			try 
			{
				c = Integer.parseInt(choice);
			} 
			catch (Exception e) 
			{
				continue;
			}
			if (c < 1 | c > 8)
				continue;
			if (c == 1) // reserving a place
			{
				reserveTH(in, con, login, dictionaryOfReserveIDs);
			} 
			else if (c == 2) // user creates or updates a TH
			{
				insertOrUpdateTH(in, con, login);
			}
			else if(c == 3) // record a stay
			{
				
			}
			else if(c == 4) //record a favorite place to stay
			{
				Favorite favorite = new Favorite();
				TH th = new TH();
				int selectionID = th.selectAllTH(con.stmt);
				
				favorite.updateFavorite(login, selectionID, con.con, con.stmt);
			}
			else if(c == 5) // give feedback
			{
				Feedback feedback = new Feedback();
				TH th = new TH();
				int selectionID = th.selectAllTH(con.stmt);
				
				String feedbackText;
				String score; // going to need to parse this one when inserting

				System.out.println("give your feedback on the place. Max 140 characters(optional):");
				while ((feedbackText = in.readLine()) == null && feedbackText.length() == 0)
					;
				System.out.println("please enter a score (0 to 10 with 0 being terrible and 10 being excellent):");
				while ((score = in.readLine()) == null && score.length() == 0)
					;
				int scoreInt = Integer.parseInt(score);
				if(scoreInt > 10 || scoreInt < 0)
				{
					System.out.println("Score not in the range specified try again. \n");
					continue;
				}
				String input = selectionID + "," + login + "," + feedbackText + "," + scoreInt;
				
				feedback.insertFeedback(input, con.con, con.stmt);
			}
			else if(c == 6) // assess feedback
			{
				System.out.println("1. Assess feedback based on TH");
				System.out.println("2. Assess any feedback given");
				System.out.println("please enter your choice (if not 1 or 2 automatically does 2):");
				
				String assessChoice = "";
				int assessC;
				while ((assessChoice = in.readLine()) == null && assessChoice.length() == 0)
					;
				try 
				{
					assessC = Integer.parseInt(assessChoice);
				} 
				catch (Exception e) 
				{
					assessC = 2;
				}
				
				int selectionFID = 0;
				if(assessC == 1)
				{
					TH th = new TH();
					int selectionID = th.selectAllTH(con.stmt);
					
					Feedback feedback = new Feedback();
					selectionFID = feedback.selectFidFromTH(selectionID, login, con.stmt);					
				}
				else
				{
					Feedback feedback = new Feedback();
					selectionFID = feedback.selectFid(login, con.stmt);
				}
				
				if(selectionFID > 0)
				{
					System.out.println("1. What is your score for them with 0 being useless, 1 being useful and 2 being very useful");
					System.out.println("please enter your choice (if not 0, 1 or 2 automatically does 1):");
				
					String ratesInput = "";
					int ratesC;
					while ((ratesInput = in.readLine()) == null && ratesInput.length() == 0)
						;
					try 
					{
						ratesC = Integer.parseInt(assessChoice);
					} 
					catch (Exception e) 
					{
						ratesC = 1;
					}
					String rating = "";
					if(ratesC == 0)
					{
						rating = "useless";
					}
					else if(ratesC == 1)
					{
						rating = "useful";
					}
					else
					{
						rating = "very useful";
					}
					Rates rates = new Rates();
					rates.insertRating(login, selectionFID, rating, con.con, con.stmt);
				}
			}
			else if(c == 7)
			{
				
			}
			else 
			{
				logoutSelection(login, dictionaryOfReserveIDs, dictionaryOfVisitIDs, in, con);
				System.out.println("Thank you for using Uotel System. \n");
				con.stmt.close();
				break;
			}
		}
	}
	
	private static void insertOrUpdateTH(BufferedReader in, Connector con, String login) throws IOException, SQLException, ParseException
	{
		String choice;
		int c = 0;
		while (true) 
		{
			System.out.println("1. Create a temporary housing");
			System.out.println("2. Update a temporary housing");
			System.out.println("3. Create time available for your temporary housing");
			System.out.println("4. View your listed temporary houses");
			System.out.println("5. Go back to user options");
			System.out.println("please enter your choice:");
			
			while ((choice = in.readLine()) == null && choice.length() == 0)
				;
			try 
			{
				c = Integer.parseInt(choice);
			} 
			catch (Exception e) 
			{
				continue;
			}
			if (c < 1 | c > 5)
				continue;		
		
			String address; 
			String city;
			String state;
			String zip;
			String THName;
			String yearBuilt;
			String category;

			TH Thdata = new TH();
			if(c == 1)
			{
				System.out.println("please enter a address:");
				while ((address = in.readLine()) == null && address.length() == 0)
					;
				System.out.println("please enter a city:");
				while ((city = in.readLine()) == null && city.length() == 0)
					;
				System.out.println("please enter a state:");
				while ((state = in.readLine()) == null && state.length() == 0)
					;
				System.out.println("please enter a zip(optional max 5 numbers i.e 80122):");
				while ((zip = in.readLine()) == null && zip.length() == 0)
					;
				System.out.println("please enter your THName:");
				while ((THName = in.readLine()) == null && THName.length() == 0)
					;
				System.out.println("please enter the yearBuilt:");
				while ((yearBuilt = in.readLine()) == null && yearBuilt.length() == 0)
					;
				System.out.println("please enter the category:");
				while ((category = in.readLine()) == null && category.length() == 0)
					;
				
				String input = address + "," + city + "," + state + "," + zip + "," + THName + "," + yearBuilt + "," + category + "," + login;
				Thdata.insertTH(input, con.con, con.stmt);
			}
			else if(c == 2)
			{
				int THID = Thdata.selectTH(con.stmt, login, "update");
				
				if(THID > 0)
				{
					System.out.println("please enter a address:");
					while ((address = in.readLine()) == null && address.length() == 0)
						;
					System.out.println("please enter a city:");
					while ((city = in.readLine()) == null && city.length() == 0)
						;
					System.out.println("please enter a state:");
					while ((state = in.readLine()) == null && state.length() == 0)
						;
					System.out.println("please enter a zip(optional max 5 numbers i.e 80122):");
					while ((zip = in.readLine()) == null && zip.length() == 0)
						;
					System.out.println("please enter your THName:");
					while ((THName = in.readLine()) == null && THName.length() == 0)
						;
					System.out.println("please enter the yearBuilt:");
					while ((yearBuilt = in.readLine()) == null && yearBuilt.length() == 0)
						;
					System.out.println("please enter the category:");
					while ((category = in.readLine()) == null && category.length() == 0)
						;
				
					String input = address + "," + city + "," + state + "," + zip + "," + THName + "," + yearBuilt + "," + category;
					Thdata.updateTH(con.con, input, THID);
				}
			}
			else if(c == 3)
			{
				Available available = new Available();
				String pricePerNight = "";
				int THID = Thdata.selectTH(con.stmt, login, "available");
				
				if(THID > 0)
				{
					System.out.println("please enter what the price per night will be (just enter an integer value like 24. No dollar sign or decimal.):");
					while ((pricePerNight = in.readLine()) == null && pricePerNight.length() == 0)
						;
					try
					{
						available.createAvailableTime(Integer.parseInt(pricePerNight), THID, con.stmt, con.con);
					}
					catch(Exception e)
					{
						System.out.println("Make sure your price per night is the right value. \n");
					}
				}	
			}
			else if(c == 4)
			{
				TH th = new TH();
				th.displayUsersTH(con.stmt, login);
			}
			else
			{
				break;
			}
		}
	}

	private static List<Map.Entry<Integer, Integer>> reserveTH(BufferedReader in, Connector con, String login, List<Map.Entry<Integer, Integer>> dictionaryOfReserveIDs) throws IOException, SQLException
	{
		while(true)
		{
			Available available = new Available();
			
			int THID = available.displayAndSelectTHAvialable(con.stmt, con.con);
			if(THID > 0)
			{
				int pid = available.displayAndSelectPidAvialable(THID, con.stmt, con.con);
				if(pid > 0)
				{
					dictionaryOfReserveIDs.add(new AbstractMap.SimpleEntry(THID, pid));
				}
			}
			
			String choice;
			int c;

			System.out.println("1. Reserve another temorary housing.");
			System.out.println("2. Return to previous screen.");
			System.out.println("please enter your choice (a choice that isn't 1 or 2 will automatically be choice 2): \n");
				
			while ((choice = in.readLine()) == null && choice.length() == 0)
				;
			try 
			{
				c = Integer.parseInt(choice);
			} 
			catch (Exception e) 
			{
				c = 2;
				continue;
			}
			if (c == 1) // reserve another place
			{
				continue;
			} 
			else // return to menu
			{
				return dictionaryOfReserveIDs;
			}
		}
	}

	private static List<Map.Entry<Integer, Integer>> visitTH(BufferedReader in, Connector con, String login, List<Map.Entry<Integer, Integer>> dictionaryOfVisitIDs) throws IOException, SQLException
	{
		while(true)
		{
			Reserve reserve = new Reserve();
			
			int THID = reserve.displayAndSelectReservationTHID(con.stmt, con.con);
			if(THID > 0)
			{
				int pid = reserve.displayAndSelectPidAvialable(THID, con.stmt, con.con);
				if(pid > 0)
				{
					if(reserve.visitSelectedAvailable(THID, pid, con.stmt, con.con))
					{
						dictionaryOfVisitIDs.add(new AbstractMap.SimpleEntry(THID, pid));
					}
				}
			}
			
			String choice;
			int c;

			System.out.println("1. Record visit to another temorary housing.");
			System.out.println("2. Return to previous screen.");
			System.out.println("please enter your choice (a choice that isn't 1 or 2 will automatically be choice 2): \n");
				
			while ((choice = in.readLine()) == null && choice.length() == 0)
				;
			try 
			{
				c = Integer.parseInt(choice);
			} 
			catch (Exception e) 
			{
				c = 2;
				continue;
			}
			if (c == 1) // reserve another place
			{
				continue;
			} 
			else // return to menu
			{
				return dictionaryOfVisitIDs;
			}
		}
	}
	
	private static void logoutSelection(String login, List<Map.Entry<Integer, Integer>> dictionaryOfReserveIDs, List<Map.Entry<Integer, Integer>> dictionaryOfVisitIDs, BufferedReader in, Connector con) throws IOException, SQLException
	{
		if(dictionaryOfReserveIDs.size() > 0)
		{
			Available available = new Available();
			for (int i = 0; i < dictionaryOfReserveIDs.size(); i++)
			{
				//get key is THID get value is pid
				available.displayReservations(dictionaryOfReserveIDs.get(i).getKey(), dictionaryOfReserveIDs.get(i).getValue(), con.stmt);
			}
			
			String yesOrNo = "";
			System.out.println("Would you like to confirm these reservations?. \n");
			
			System.out.println("please enter yes or no (a choice that isn't yes will automatically be no):");
			while ((yesOrNo = in.readLine()) == null && yesOrNo.length() == 0)
				;
			
			Reserve reserve = new Reserve();
			if(yesOrNo.equals("yes"))
			{
				for (int i = 0; i < dictionaryOfReserveIDs.size(); i++)
				{
					//get key is THID get value is pid
					reserve.insertReservation(login, dictionaryOfReserveIDs.get(i).getKey(), dictionaryOfReserveIDs.get(i).getValue(), con.con, con.stmt);
				}
				System.out.println("Reservations made thank you. \n");
			}
			else
			{
				System.out.println("Reservations cancelled and cleared. \n");
			}
			
			Period period = new Period();			
			for (int i = 0; i < dictionaryOfReserveIDs.size(); i++)
			{
				available.removeAvailabe(dictionaryOfReserveIDs.get(i).getKey(), dictionaryOfReserveIDs.get(i).getValue(), con.con);
			}
		}
		else if(dictionaryOfVisitIDs.size() > 0)
		{
			// do the stuff here
		}
		dictionaryOfReserveIDs.clear();
		dictionaryOfVisitIDs.clear();
	}
}
