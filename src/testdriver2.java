
import java.lang.*;
import java.sql.*;
import java.io.*;

public class testdriver2 {

	/**
	 * @param args
	 */
	public static void loginOrCreateAccount() {
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
		System.out.println("2. List or update a temporary housing you own");
		System.out.println("3. exit Uotel System");
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
					System.out.println("Database connection terminated");
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
				System.out.println("are you a temorary house owner(yes or no):");
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
				System.out.println("EoM");
				con.stmt.close();
				return "";
			}
		}
	}


	public static void userOptions(BufferedReader in, Connector con, String login) throws Exception 
	{
		String choice;
		int c = 0;
		while (true) {
			displayUserOptions();
			//String login;
			//String password;
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
			if (c == 1) // reserving a place
			{
				//get the periods available for a TH
				String TemporaryHousingID;


				System.out.println("please enter a TH id for the house you are searching availablity:");
				while ((TemporaryHousingID = in.readLine()) == null && TemporaryHousingID.length() == 0)
					;
/*
				Available available = new Available();
				available.getDatesAvailable(TemporaryHousingID, con.stmt);
				*/
			} 
			else if (c == 2) // user creates or updates a TH
			{
				insertOrUpdateTH(in, con, login);
			} 
			else 
			{
				System.out.println("EoM");
				con.stmt.close();
				break;
			}
		}
	}
	
	private static void insertOrUpdateTH(BufferedReader in, Connector con, String login) throws IOException, SQLException
	{
		String choice;
		int c = 0;
		while (true) 
		{
			System.out.println("1. Create a temporary housing");
			System.out.println("2. Update a temporary housing");
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
		
			String address; 
			String THName;
			String yearBuilt;
			String category;

			System.out.println("please enter a address:");
			while ((address = in.readLine()) == null && address.length() == 0)
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

			TH Thdata = new TH();
			if(c == 1)
			{
				String input = address + "," + THName + "," + yearBuilt + "," + category + "," + login;
				Thdata.insertTH(input, con.con, con.stmt);
			}
			else if(c == 2)
			{
				
			}
			else
			{
				break;
			}
		}
	}
}
