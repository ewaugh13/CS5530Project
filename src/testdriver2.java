
import java.lang.*;
import java.sql.*;
import java.io.*;

public class testdriver2 {

	/**
	 * @param args
	 */
	public static void displayMenu() {
		// add more options I think based on the 14 sections but there could be
		// more?
		System.out.println("        Welcome to the Uotel System     ");
		System.out.println("1. Register into Uotel System:");
		System.out.println("2. ");
		System.out.println("3. exit Uotel System:");
		System.out.println("please enter your choice:");
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Connector con = null;
		String choice;
		String sql = null;
		int c = 0;
		try {
			// remember to replace the password
			con = new Connector();
			System.out.println("Database connection established");

			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

			while (true) {
				displayMenu();
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
					String login;
					String password;
					String fullName;
					String age; //going to need to parse this one when inserting
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
					String input = login + "," + password + "," + fullName + "," + age + "," + email + "," + phoneNumber + "," + address + "," + userType;
					users.insertUser(input, con.con);
				} else if (c == 2) {
					System.out.println("please enter your query below:");
					while ((sql = in.readLine()) == null && sql.length() == 0)
						System.out.println(sql);
					ResultSet rs = con.stmt.executeQuery(sql);
					ResultSetMetaData rsmd = rs.getMetaData();
					int numCols = rsmd.getColumnCount();
					while (rs.next()) {
						// System.out.print("cname:");
						for (int i = 1; i <= numCols; i++)
							System.out.print(rs.getString(i) + "  ");
						System.out.println("");
					}
					System.out.println(" ");
					rs.close();
				} else {
					System.out.println("EoM");
					con.stmt.close();

					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Either connection error or query execution error!");
		} finally {
			if (con != null) {
				try {
					con.closeConnection();
					System.out.println("Database connection terminated");
				}

				catch (Exception e) {
					/* ignore close errors */ }
			}
		}
	}
}
