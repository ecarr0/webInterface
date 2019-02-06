package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import data.Database;

//import view.Interface;

public class User {
		
	private long userNumber;		// account number (a 9-digit number)
	private String username;			// account balance (restricted to two places after the decimal)
	private String password;				// account holder (see User class)
		
	/**
	 * Constructs an instance (or object) of the BankAccount class.
	 * 
	 * @param userNumber
	 * @param username
	 * @param password
	 */
	
	public User(long userNumber, String username, String password) {
		this.userNumber = userNumber;
		this.username = username;
		this.password = password;
	}
	
	public User(String account) {
		System.out.println(account);
		this.userNumber = Long.valueOf(account.substring(0, 4));
		String testUser = account.substring(4,35);
		int index = 0;
		while(testUser.charAt(index) != ' ') {
			index++;
		}
		testUser = account.substring(4, index - 1);
		String testPass = account.substring(35, 65);
		index = 0;
		while(testPass.charAt(index) != ' ') {
			index++;
		}
		testPass = account.substring(35, index - 1);
		this.username = testUser;
		this.password = testPass;
	}
	
	@Override
	public String toString() {
		if(username.length() < 30) {
			while(username.length() < 30) {
				username += " ";
			}
		}
		if(password.length() < 30) {
			while(password.length() < 30) {
				password += " ";
			}
		}
		String userNumberString = Long.toString(userNumber);
		String userInfo = userNumberString + username + password;
		return userInfo;
	}
	
	/**
	 * Constructs an instance (or object) of the BankAccount class from a ResultSet.
	 * 
	 * @param rs
	 * @throws SQLException 
	 */
	
	public User(ResultSet rs) throws SQLException {	
		this(rs.getLong(Database.USER_NUMBER), rs.getString(Database.USERNAME), rs.getString(Database.PASSWORD));
	}
	
	///////////////////// GETTERS & SETTERS ///////////////////////////////////////////
	/**
	 * Retrieves the user number.
	 * 
	 * @return userNumber
	 */
	
	public long getUserNumber() {
		return userNumber;
	}
	
	/**
	 * Retrieves the username.
	 * 
	 * @return username
	 */
	
	public String getUsername() {
		return username;
	}
	
	/**
	 * Retrieves the password.
	 * 
	 * @return password
	 */
	
	public String getPassword() {
		return password;
	}
	
	///////////////////// INSTANCE METHODS ////////////////////////////////////////////
}
