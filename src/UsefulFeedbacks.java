import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class UsefulFeedbacks {
	
	public UsefulFeedbacks(){}
	
	public static void getUsefulFeedbacks(int THID, int numFeedbcks, Connection conn, Statement stmt) throws SQLException, IOException
	{
		String avgFeedbackScores = "select avg(score) from Feedback where THID = " + THID;
		String rankedFeedbacks = 
	}
	
}
