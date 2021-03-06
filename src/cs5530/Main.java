package cs5530;

import java.sql.*;
import java.text.ParseException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.*;
import java.util.Map.Entry;

public class Main {

	/**
	 * @param args
	 */
	public static void loginOrCreateAccountOptions() 
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
		System.out.println("8. Browse temporary houses by user specification");
		System.out.println("9. Display usefulness feedbacks based on ratings");
		System.out.println("10. Check Degrees of Separation for 2 users");
		System.out.println("11. View statistics");
		System.out.println("12. View user award info (Admin Only)");
		System.out.println("13. exit Uotel System");
		System.out.println("please enter your choice:");
	}
	
	public static void displayTHOptions()
	{
		System.out.println("1. Create a temporary housing");
		System.out.println("2. Update a temporary housing");
		System.out.println("3. Create time available for your temporary housing");
		System.out.println("4. Remove time available for your temporary housing (if not already reserved)");
		System.out.println("5. Add or remove keywords");
		System.out.println("6. View your listed temporary houses");
		System.out.println("7. Go back to user options");
		System.out.println("please enter your choice:");
	}

	public static void displayBrowsingOptions()
	{
		System.out.println("1. Browse by range in price per night(can only check the temporary houses that are available for this one)");
		System.out.println("2. Browse by city or state");
		System.out.println("3. Browse by keywords");
		System.out.println("4. Browse by category");
		System.out.println("5. Browse by what has been selected");
		System.out.println("6. Stop browseing and return to previous menu");
		System.out.println("please enter your choice (if you select something that you already have no changes will be made to selection):");
	}

	public static void displayAndOrOptions()
	{
		System.out.println("1. Do this and another selection");
		System.out.println("2. Do this or another selection");
		System.out.println("please enter your choice (if not 1 or 2 will automatically be 1 and if you are only searching by one it doesn't matter):");
	}
	
	public static void displaySortingOptions()
	{
		System.out.println("1. Display in order of cheapest first (will only select temporary houses that are available)");
		System.out.println("2. Average score of feedbacks (will only select temporary houses that have feedbacks)");
		System.out.println("3. Average score of trusted user feedbacks (will only select temporary houses that have feedback from users you have declared as trusted)");
		System.out.println("please enter your choice:");
	}
	
	public static void displayStatsOptions()
	{
		System.out.println("1. The most popular temporary houses for each category based on visits");
		System.out.println("2. The avg cost of visits for temporary houses for each category");
		System.out.println("3. The most highly rated temporary hosues for each category based on user feedback");
		System.out.println("please enter your choice:");
	}
	
	public static void main(String[] args) {
		Connector con = null;
		try 
		{
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
			loginOrCreateAccountOptions();
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
			if (c < 1 | c > 13)
				continue;
			if (c == 1) // reserving a place
			{
				reserveTH(in, con, login);
			} 
			else if (c == 2) // user creates or updates a TH
			{
				insertOrUpdateTH(in, con, login);
			}
			else if(c == 3) // record a stay
			{
				visitTH(in, con, login);
			}
			else if(c == 4) //record a favorite place to stay
			{
				Favorite favorite = new Favorite();
				TH th = new TH();
				StringBuilder stringBuilder = new StringBuilder();
				List<Integer> THIDS = th.selectAllTH(con.stmt, stringBuilder);
				if(THIDS.size() > 0)
				{
					choice = "";
					c = 0;
				
					while ((choice = in.readLine()) == null)
						;
					try 
					{
						c = Integer.parseInt(choice);
					} 
					catch (Exception e) 
					{
					}
					if(THIDS.contains(c))
					{
						System.out.println("House favorited with the THID of " + c);
						favorite.updateFavorite(login, c, con.con, con.stmt);
					}
					else
					{
						System.out.println("Select a correct temporary house by THID");
					}
				}
				else
				{
					System.out.println("There are no temporary houses listed. \n");
				}
			}
			else if(c == 5) // give feedback
			{
				giveFeedback(in, con, login);
			}
			else if(c == 6) // assess feedback
			{
				assessFeedback(in, con, login);
			}
			else if(c == 7) //declare trustworthy
			{
				declareTrustworthy(in, con, login);
			}
			else if(c == 8) //browsing
			{
				browsing(in, con, login);
			}
			else if(c == 9) // usefull feedbacks
			{
				UsefulFeedbacks usb = new UsefulFeedbacks();
				TH th = new TH();
				int THID = th.selectTH(con.stmt, login, "feedback");
				
				System.out.println("How many feedbacks do you want to display (any amount will do but if you don't select a number it will automatically be 10):");
				while ((choice = in.readLine()) == null && choice.length() == 0)
					;
				try 
				{
					c = Integer.parseInt(choice);
				} 
				catch (Exception e) 
				{
					c = 10;
				}
				usb.getUsefulFeedbacks(THID, c, con.stmt);
			}
			else if(c == 10) // degrees of seperation
			{
				DegreesOfSeparation ds = new DegreesOfSeparation();
				Entry<String, String> selectedUsers = ds.displayAllUsersInFavorites(con.stmt);
				ds.separationGetter(selectedUsers.getKey(), selectedUsers.getValue(), con.con, con.stmt);
			}
			else if(c == 11)
			{
				Statistics stats = new Statistics();
				String option = "";
				while(true)
				{
					displayStatsOptions();
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
					if(c < 1 || c > 3)
						continue;
					
					if(c == 1)
					{
						option = "popular";
						break;
					}
					else if(c == 2)
					{
						option = "cost";
						break;
					}
					else if(c == 3)
					{
						option = "score";
						break;
					}
				}
				
				System.out.println("Select the amount of houses for each category you want to show (if wrong input it will be set to 5):");
				while ((choice = in.readLine()) == null && choice.length() == 0)
					;
				try 
				{
					c = Integer.parseInt(choice);
				} 
				catch (Exception e) 
				{
					c = 5;
				}
				if(option.equals("popular"))
				{
					List<String> categories = stats.getAllCategoriesInVisit(con.stmt);
					stats.statForMostVisited(categories, con.stmt, c);
				}
				else if(option.equals("cost"))
				{
					List<String> categories = stats.getAllCategoriesInVisit(con.stmt);
					stats.statMostExpensive(categories, con.stmt, c);
				}
				else if(option.equalsIgnoreCase("score"))
				{
					List<String> categories = stats.getAllCategoriesInFeedback(con.stmt);
					stats.statMostPopular(categories, con.stmt, c);
				}
			}
			else if(c == 12) // user awards
			{
				UserAwards ua = new UserAwards();
				boolean isAdmin = ua.AdminChecker(login, con.con, con.stmt);
				if(isAdmin == true)
				{
					userAwardsType(in, con, login, ua);
				}
			}
			else 
			{
				System.out.println("Thank you for using Uotel System. \n");
				con.stmt.close();
				break;
			}
		}
	}
	
	private static void userAwardsType(BufferedReader in, Connector con, String login, UserAwards ua) throws IOException, SQLException, ParseException
	{
		String choice;
		int c = 0;
		while (true) 
		{
			System.out.println("1. View the most trustworthy users");
			System.out.println("2. View the users with the most useful fedback");

			System.out.println("3. Go back to user options");
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
			
			if (c < 1 | c > 3)
				continue;
			
			String m;
			int n;
			
			if(c == 1)
			{
				System.out.println("How many users would you like to view:");
				while ((m = in.readLine()) == null && m.length() == 0)
					;
				try 
				{
					n = Integer.parseInt(m);
				} 
				catch (Exception e) 
				{
					continue;
				}
				
				ua.mostTrustedUsers(n, con.con, con.stmt);
			}
			else if(c == 2)
			{
				System.out.println("How many users would you like to view:");
				while ((m = in.readLine()) == null && m.length() == 0)
					;
				try 
				{
					n = Integer.parseInt(m);
				} 
				catch (Exception e) 
				{
					continue;
				}
				
				ua.mostUsefulUsers(n, con.con, con.stmt);
			}
			else
			{
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
			displayTHOptions();
			
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
			if (c < 1 | c > 7)
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
				System.out.println("please enter the yearBuilt(an integer):");
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
			else if(c == 3) // create new available
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
			else if(c == 4) // remove time of available
			{
				TH th = new TH();
				int THID = th.selectTH(con.stmt, login, "remove");
				
				Available available = new Available();
				int pid = available.displayAndSelectPidAvialable(THID, "remove", con.stmt);
				available.removeAvailabe(THID, pid, con.con);
			}
			else if(c == 5) // remove or add keywords
			{
				TH th = new TH();
				int THID = th.selectTH(con.stmt, login, "keyword");
				Keyword key = new Keyword();
				
				System.out.println("Select 1 to add more keywords for a selected TH and 2 to remove them (if not 1 or 2 will just return to temporary house menu");
				while ((choice = in.readLine()) == null && choice.length() == 0)
					;
				try 
				{
					c = Integer.parseInt(choice);
				} 
				catch (Exception e) 
				{
					c = 3;
				}
				if(c == 1) // add more keywords
				{
					List<Integer> keyIDS = key.userInput(con.con, con.stmt);
					for(int i = 0; i < keyIDS.size(); i++)
					{
						key.insertIntoHasKeys(THID, keyIDS.get(i), con.con);
					}
					System.out.println("keywords added");
				}
				else if(c == 2) // remove keywords
				{
					List<Integer> keyIDS = key.userInputRemove(THID, con.con, con.stmt);
					for(int i = 0; i < keyIDS.size(); i++)
					{
						key.removeFromHasKeys(THID, keyIDS.get(i), con.con);
					}
				}
			}
			else if(c == 6) // display all users TH
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

	private static void reserveTH(BufferedReader in, Connector con, String login) throws IOException, SQLException
	{
		List<Map.Entry<Integer, Integer>> dictionaryOfReserveIDs = new ArrayList<Map.Entry<Integer, Integer>>();
		RecommendedTH recommended = new RecommendedTH();
		while(true)
		{
			Available available = new Available();
			
			int THID = available.displayAndSelectTHAvialable(con.stmt);
			if(THID > 0)
			{
				int pid = available.displayAndSelectPidAvialable(THID, "reserve", con.stmt);
				if(pid > 0)
				{
					dictionaryOfReserveIDs.add(new AbstractMap.SimpleEntry(THID, pid));
					recommended.getRecommendedTH(THID, con.stmt);
				}
			}
			
			String choice;
			int c;

			System.out.println("\n1. Reserve another temorary housing.");
			System.out.println("2. Confirm reservations and return to previous screen.");
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
				if(dictionaryOfReserveIDs.size() > 0)
				{
					StringBuilder stringBuilder = new StringBuilder();
					for (int i = 0; i < dictionaryOfReserveIDs.size(); i++)
					{
						//get key is THID get value is pid
						available.displayReservations(dictionaryOfReserveIDs.get(i).getKey(), dictionaryOfReserveIDs.get(i).getValue(), con.stmt, stringBuilder);
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
						
						for (int i = 0; i < dictionaryOfReserveIDs.size(); i++)
						{
							// removes from available once you reserve them
							available.removeAvailabe(dictionaryOfReserveIDs.get(i).getKey(), dictionaryOfReserveIDs.get(i).getValue(), con.con);
						}
					}
					else
					{
						System.out.println("Reservations cancelled and cleared. \n");
					}
				}
				break;
			}
		}
		
		
	}

	private static void visitTH(BufferedReader in, Connector con, String login) throws IOException, SQLException
	{
		List<Map.Entry<Integer, Integer>> dictionaryOfVisitIDs = new ArrayList<Map.Entry<Integer, Integer>>();
		while(true)
		{
			Reserve reserve = new Reserve();
			
			int THID = reserve.displayAndSelectReservationTHID(login, con.stmt);
			if(THID > 0)
			{
				int pid = reserve.displayAndSelectPidReservation(THID, con.stmt);
				if(pid > 0)
				{
					dictionaryOfVisitIDs.add(new AbstractMap.SimpleEntry(THID, pid));
				}
			}
			
			String choice;
			int c;

			System.out.println("1. Record visit to another temorary housing.");
			System.out.println("2. Confirm visits and return to previous screen.");
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
				if(dictionaryOfVisitIDs.size() > 0)
				{
					for (int i = 0; i < dictionaryOfVisitIDs.size(); i++)
					{
						//get key is THID get value is pid
						reserve.displayVisits(dictionaryOfVisitIDs.get(i).getKey(), dictionaryOfVisitIDs.get(i).getValue(), con.stmt);
					}
					
					String yesOrNo = "";
					System.out.println("Would you like to confirm these visits?. \n");
					
					System.out.println("please enter yes or no (a choice that isn't yes will automatically be no):");
					while ((yesOrNo = in.readLine()) == null && yesOrNo.length() == 0)
						;
					
					Visit visit = new Visit();
					if(yesOrNo.equals("yes"))
					{
						for (int i = 0; i < dictionaryOfVisitIDs.size(); i++)
						{
							//get key is THID get value is pid
							visit.insertVisit(login, dictionaryOfVisitIDs.get(i).getKey(), dictionaryOfVisitIDs.get(i).getValue(), con.con, con.stmt);
						}
						System.out.println("Visits recorded thank you. \n");
						
						for (int i = 0; i < dictionaryOfVisitIDs.size(); i++)
						{
							// removes from available once you reserve them
							reserve.removeReservation(dictionaryOfVisitIDs.get(i).getKey(), dictionaryOfVisitIDs.get(i).getValue(), con.con);
						}
					}
					else
					{
						System.out.println("Visits cancelled and cleared. \n");
					}
				}
				break;
			}
		}
	}

	private static void assessFeedback(BufferedReader in, Connector con, String login) throws SQLException, IOException
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
				ratesC = Integer.parseInt(ratesInput);
			} 
			catch (Exception e) 
			{
				ratesC = 1;
			}
			Rates rates = new Rates();
			rates.insertRating(login, selectionFID, ratesC, con.con, con.stmt);
		}
	}

	private static void declareTrustworthy(BufferedReader in, Connector con, String login) throws IOException, SQLException
	{
		String trustChoice = "";
		int trustC = 0;
		while(true)
		{
			System.out.println("1. Declare user trustworthy.");
			System.out.println("2. Go back to previous menu.");
			System.out.println("please enter your choice:");
			
			while ((trustChoice = in.readLine()) == null && trustChoice.length() == 0)
				;
			try 
			{
				trustC = Integer.parseInt(trustChoice);
			} 
			catch (Exception e) 
			{
				continue;
			}
			if (trustC < 1 | trustC > 2)
				continue;
			if(trustC == 1)
			{
				Trust trust = new Trust();
				String selectedUser = trust.displayAndSelectUser(login, con.stmt, con.con);
				if(!selectedUser.equals(""))
				{
					String choice = "";
					int c = 0;
					System.out.println("If trustworthy give input of 1, if untrustworthy give input of 2 (if not 1 or 2 no trustworthy record will be made on this person). \n");
					System.out.println("please enter your choice:");
					
					while ((choice = in.readLine()) == null && choice.length() == 0)
						;
					try 
					{
						c = Integer.parseInt(choice);
					} 
					catch (Exception e) 
					{
					}
					if(c == 1)
					{
						trust.insertRating(login, selectedUser, true, con.con, con.stmt);
					}
					else if(c == 2)
					{
						trust.insertRating(login, selectedUser, false, con.con, con.stmt);
					}
				}
			}
			else if(trustC == 2)
			{
				break;
			}		
		}
	}

	private static void giveFeedback(BufferedReader in, Connector con, String login) throws SQLException, IOException
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
		}
		else
		{
			String input = selectionID + "," + login + "," + feedbackText + "," + scoreInt;
			feedback.insertFeedback(input, con.con, con.stmt);
		}
	}

	private static void browsing(BufferedReader in, Connector con, String login) throws IOException
	{
		String choice = "";
		int c = 0;
		Map<Integer, String> selectedBrowsing = new HashMap<Integer, String>(); // where string is 'and' or 'or'
		boolean search = true;
		while (true) 
		{
			displayBrowsingOptions();
			
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
			if (c < 1 | c > 6)
				continue;
			
			if(c == 1) // add browse by range in price
			{
				if(selectedBrowsing.containsKey(1))
				{
					System.out.println("Browsing by range in price per night has already been selected. \n");
				}
				else
				{
					displayAndOrOptions();
					while ((choice = in.readLine()) == null && choice.length() == 0)
						;
					try 
					{
						c = Integer.parseInt(choice);
					} 
					catch (Exception e) 
					{
						c = 1;
					}
					if(c == 2) //is or case
					{
						selectedBrowsing.put(1, "or");
					}
					else //is and case
					{
						selectedBrowsing.put(1, "and");
					}
				}
			}
			else if(c == 2) // add browse by city or state
			{
				if(selectedBrowsing.containsKey(2))
				{
					System.out.println("Browsing by city or state has already been selected. \n");
				}
				else
				{
					displayAndOrOptions();
					while ((choice = in.readLine()) == null && choice.length() == 0)
						;
					try 
					{
						c = Integer.parseInt(choice);
					} 
					catch (Exception e) 
					{
						c = 1;
					}
					if(c == 2) //is or case
					{
						selectedBrowsing.put(2, "or");
					}
					else //is and case
					{
						selectedBrowsing.put(2, "and");
					}
				}
			}
			else if(c == 3) // add browse by keywords
			{
				if(selectedBrowsing.containsKey(3))
				{
					System.out.println("Browsing by keywords has already been selected. \n");
				}
				else
				{
					displayAndOrOptions();
					while ((choice = in.readLine()) == null && choice.length() == 0)
						;
					try 
					{
						c = Integer.parseInt(choice);
					} 
					catch (Exception e) 
					{
						c = 1;
					}
					if(c == 2) //is or case
					{
						selectedBrowsing.put(3, "or");
					}
					else //is and case
					{
						selectedBrowsing.put(3, "and");
					}
				}
			}
			else if(c == 4) // add browse by category
			{
				if(selectedBrowsing.containsKey(4))
				{
					System.out.println("Browsing by category has already been selected. \n");
				}
				else
				{
					displayAndOrOptions();
					while ((choice = in.readLine()) == null && choice.length() == 0)
						;
					try 
					{
						c = Integer.parseInt(choice);
					} 
					catch (Exception e) 
					{
						c = 1;
					}
					if(c == 2) //is or case
					{
						selectedBrowsing.put(4, "or");
					}
					else //is and case
					{
						selectedBrowsing.put(4, "and");
					}
				}
			}
			
			else if(c == 5) // run selected things or tells them nothing has been selected
			{
				if(selectedBrowsing.size() == 0)
				{
					System.out.println("No selected browsing options made. Please make a selection or exit the browsing system. \n");
				}
				else
				{
					break;
				}
			}
			
			else if(c == 6)
			{
				search = false;
				break;
			}
		}
		if(search)
		{
			boolean browsePrice = false;
			boolean browseCityOrState = false;
			boolean browseKeywords = false;
			boolean browseCategory = false;
			
			boolean orAndPrice = true; //true means 'and' where false means 'or'
			boolean orAndCityState = true; //true means 'and' where false means 'or'
			boolean orAndCategory = true; //true means 'and' where false means 'or'
			
			String lowRange = "";
			String highRange = "";
			String cityOrState = "";
			List<Integer> keywordIDS = new ArrayList<Integer>();
			String category = "";
			Browsing browse = new Browsing();
			
			if (selectedBrowsing.containsKey(1)) // browse by price range
			{
				System.out.println("please enter a low price for the range(just the number):");
				while ((lowRange = in.readLine()) == null && lowRange.length() == 0)
					;
				System.out.println("please enter a high price for the range(just the number):");
				while ((highRange = in.readLine()) == null && highRange.length() == 0)
					;
				browsePrice = true;
				if(selectedBrowsing.get(1).equals("or"))
				{
					orAndPrice = false;
				}
			} 
			if (selectedBrowsing.containsKey(2)) // browse by city or state
			{
				cityOrState = browse.displayAndSelectCityOrState(con.stmt);
				if(!cityOrState.equals(""))
				{
					browseCityOrState = true;
					if(selectedBrowsing.get(2).equals("or"))
					{
						orAndCityState = false;
					}
				}
			}
			if(selectedBrowsing.containsKey(3)) // browse by keywords
			{
				keywordIDS = browse.displayAndSelectKeywords(con.stmt);
				if(keywordIDS.size() > 0)
				{
					browseKeywords = true;
				}
			}
			if(selectedBrowsing.containsKey(4)) // browse by category
			{
				category = browse.displayAndSelectCategory(con.stmt);
				if(!category.equals(""))
				{
					browseCategory = true;
					if(selectedBrowsing.get(4).equals("or"))
					{
						orAndCategory = false;
					}
				}
			}
			displaySortingOptions();
			String sortingMethod = "";
			while(true)
			{
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
				if(c < 1 || c > 3)  
				if(c == 1) //sort by price
				{
					sortingMethod = "price";
					break;
				}
				else if(c == 2) //sort by avg score of feedbacks
				{
					sortingMethod = "feedback";
					break;
				}
				else if(c == 3) //sort by avg trusted user feedbacks
				{
					sortingMethod = "trust";
					break;
				}
			}
			
			
			if(browseKeywords)
			{
				browse.displayHousesByKeywords(login, keywordIDS, Integer.parseInt(lowRange), Integer.parseInt(highRange), browsePrice, orAndPrice, cityOrState, browseCityOrState, orAndCityState, category, browseCategory, orAndCategory, sortingMethod, con.stmt);
			}
			else if(browsePrice || browseCityOrState || browseCategory)
			{
				browse.displayHouses(login, Integer.parseInt(lowRange), Integer.parseInt(highRange), browsePrice, cityOrState, browseCityOrState, orAndCityState, category, browseCategory, orAndCategory, sortingMethod, con.stmt);
			}
		}
	}
}
