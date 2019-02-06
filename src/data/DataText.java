package data;

/**
 * This class will serve as the intermediary between our ATM program and
 * the database of BankAccounts. It'll be responsible for fetching accounts
 * when users try to login, as well as updating those accounts after any
 * changes are made.
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
//import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
//import java.io.InputStreamReader;
import java.util.Arrays;

import model.User;

public class DataText {
	
	private String path;
	private String[] accounts;
	
	public DataText(String path) throws FileNotFoundException, IOException {
		this.path = path;
		this.accounts = getAllAccounts();
	}
	
	
	public String[] getAllAccounts() throws FileNotFoundException, IOException {
		int count = 0;
		String[] accounts = new String[10];
		
		FileReader altered = null;
		InputStreamReader original = null;
		try {
			altered = new FileReader(System.getProperty("user.dir") + File.separator + path);			
		} catch (FileNotFoundException e) {
			original = new InputStreamReader(getClass().getResourceAsStream(path));
		}
		
		try (BufferedReader br = new BufferedReader(original != null ? original : altered)) {
			String line;
			
			while ((line = br.readLine()) != null) {
				if (count >= accounts.length) {
					accounts = Arrays.copyOf(accounts, accounts.length + 10);
				}
				accounts[count++] = line;
			}
		}
		
		return Arrays.copyOf(accounts, count);
	}
	
	public String getAccount(String username, String password) {
		for (String account : accounts) {
			System.out.println(Integer.toString(account.length()));
			String testUser = account.substring(4,35);
			int index = 4;
			while(testUser.charAt(index) != ' ') {
				index++;
			}
			System.out.println(Integer.toString(index));
			testUser = account.substring(4, 4 + index);
			System.out.println(testUser);
			String testPass = account.substring(34);
			index = 0;
			while(testPass.charAt(index) != ' ' && index < 30) {
				index++;
			}
			System.out.println(Integer.toString(index));
			testPass = account.substring(34, 64-index);
			System.out.println(testPass);
			if (testUser == username && testPass == password) {
				return account;
			}
		}
		
		return null;
	}
	
	
	public long getMaxAccountNumber() {
		long max = -1L;
		
		for (String account : accounts) {
			long accountNumber = Long.parseLong(account.substring(0, 4));
			
			if (accountNumber > max) {
				max = accountNumber;
			}
		}
		
		return max;
	}
	
	public void updateAccount(User user) throws IOException {
		for (int i = 0; i < accounts.length; i++) {			
			if (accounts[i].startsWith(String.valueOf(user.getUserNumber()))) {
				accounts[i] = user.toString();
			}
		}
		
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(System.getProperty("user.dir") + File.separator + path))) {
			for (String acct : accounts) {
				bw.write(acct);
				bw.newLine();
			}
		}
	}
	
//	public void insertDefaultAccount() throws IOException {
//		String username = "user";
//		while(username.length() < 30) {
//			username += " ";
//		}
//		String password = "password";
//		while(password.length() < 30) {
//			password += " ";
//		}
//		accounts[0] = "0001" + username + password;
//		try (BufferedWriter bw = new BufferedWriter(new FileWriter(System.getProperty("user.dir") + File.separator + path))) {
//			for (String acct : accounts) {
//				bw.write(acct);
//				bw.newLine();
//			}
//		}
//	}
}