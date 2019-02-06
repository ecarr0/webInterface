package data;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import model.User;


public class Database {
	
	/*
	 * Field names for database table: accounts.
	 */
	public static final String USER_NUMBER = "user_number";
	public static final String USERNAME = "username";
	public static final String PASSWORD = "password";

	private Connection conn;			// a connection to the database
	private Statement stmt;				// the statement used to build inserts, updates and selects
	private ResultSet rs;				// result set used for selects
	private DatabaseMetaData meta;		// metadata about the database
	
	/**
	 * Constructs an instance (or object) of the Database class.
	 */
	
	public Database() {
		try {
			this.connect();
			this.setup();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	///////////////////// INSTANCE METHODS ////////////////////////////////////////////
	/**
	 * Retrieves an existing account by user number.
	 * 
	 * @param accountNumber
	 * @return
	 */
	
	public User getUser(long user_number) {
		try {
			stmt = conn.createStatement();
			
			PreparedStatement selectStmt = conn.prepareStatement("SELECT * FROM loginInfo WHERE user_number = ?");
			selectStmt.setLong(1, user_number);
			
			rs = selectStmt.executeQuery();
			if (rs.next()) {
				return new User(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public void deleteTable() {
		try {
			stmt = conn.createStatement();
			
			PreparedStatement deleteStmt = conn.prepareStatement("DELETE FROM loginInfo WHERE 1 = 1 ");
			deleteStmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public User getUser(String username, String password) {
		try {
			stmt = conn.createStatement();
			
			PreparedStatement selectStmt = conn.prepareStatement("SELECT * FROM loginInfo WHERE username = ? AND password = ?");
			selectStmt.setString(1, username);
			selectStmt.setString(2, password);
			
			rs = selectStmt.executeQuery();
			if (rs.next()) {
				return new User(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	/**
	 * Inserts an user info into the database.
	 * 
	 * @param user
	 * @return true if the insert is successful; false otherwise.
	 */
	
	public boolean insertAccount(User user) {
		try {
			stmt = conn.createStatement();
			
			PreparedStatement insertStmt = conn.prepareStatement("INSERT INTO loginInfo VALUES (?, ?, ?)");		
			insertStmt.setLong(1, user.getUserNumber());
			insertStmt.setString(2, user.getUsername());
			insertStmt.setString(3, user.getPassword());

			insertStmt.executeUpdate();
			insertStmt.close();
			
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * Shuts down the database, releasing all allocated resources.
	 * 
	 * @throws SQLException
	 */
	
	public long highestUserNum() {
		long userNum = 0;
		try {
			stmt = conn.createStatement();


			PreparedStatement selectStmt = conn.prepareStatement("SELECT MAX(user_number) FROM loginInfo");
//			selectStmt.setLong(1, user_number);
			
			rs = selectStmt.executeQuery();
			System.out.println(rs);

			if (rs.next()) {
				return rs.getLong(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return userNum;
	}
	
	public void shutdown() throws SQLException {
		if (rs != null) rs.close();
		if (stmt != null) stmt.close();
		if (conn != null) conn.close();
	}
	
	///////////////////// PRIVATE METHODS /////////////////////////////////////////////
	
	/*
	 * Establishes a connection to the database.
	 * 
	 * @throws SQLException
	 */
	
	private void connect() throws SQLException {
		Properties props = new Properties();
        props.put("user", "user1");
        props.put("password", "user1");

        conn = DriverManager.getConnection("jdbc:derby:atm;create=true", props);
	}
	
	/*
	 * Performs initial database setup.
	 * 
	 * @throws SQLException
	 */
	
	private void setup() throws SQLException {
		createAccountsTable();
		insertDefaultAccount();
	}
	
	/*
	 * Creates the initial accounts table. This will only be done once during initial setup.
	 * 
	 * @throws SQLException
	 */
	
	private void createAccountsTable() throws SQLException {
		meta = conn.getMetaData();
		rs = meta.getTables(null, "USER1", "LOGININFO", null);
		
		if (!rs.next()) {
			stmt = conn.createStatement();
			
			stmt.execute(
				"CREATE TABLE loginInfo (" +
					"user_number BIGINT PRIMARY KEY, " +
					"username VARCHAR(50), " +
					"password VARCHAR(50) " +
					"bitstrength FLOAT" +
				")"
			);
		}
	}
	
	/*
	 * Inserts a default account into the database. This will only be done once during initial setup.
	 * 
	 * @throws SQLException
	 */
	
	private void insertDefaultAccount() throws SQLException {
		stmt = conn.createStatement();
		rs = stmt.executeQuery("SELECT COUNT(*) FROM loginInfo");
		if (rs.next() && rs.getInt(1) == 0) {
			PreparedStatement insertStmt = conn.prepareStatement("INSERT INTO loginInfo VALUES (?, ?, ?)");
			
			insertStmt.setLong(1, 0001);
			insertStmt.setString(2, "User");
			insertStmt.setString(3, "Password");
			insertStmt.executeUpdate();
			insertStmt.close();
		}
	}
}
