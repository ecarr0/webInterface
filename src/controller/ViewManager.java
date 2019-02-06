package controller;

import java.awt.CardLayout;
import java.awt.Container;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JOptionPane;

//import data.DataText;
import data.Database;
import model.User;
import view.AdminView;
import view.Interface;
import view.LoginView;
import view.TestView;


public class ViewManager {
	
	private Container views;				// the collection of all views in the application
	public Database db;					// a reference to the database
	private User user;
	
	/**
	 * Constructs an instance (or object) of the ViewManager class.
	 * 
	 * @param layout
	 * @param container
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	
	public ViewManager(Container views) throws FileNotFoundException, IOException {
		this.views = views;
		this.db = new Database();
	}
	
	///////////////////// INSTANCE METHODS ////////////////////////////////////////////
	
	/**
	 * Routes a login request from the LoginView to the Database.
	 * 
	 * @param accountNumber
	 * @param pin
	 */
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public void login(String username, String password) {
		try {
			user = db.getUser(username, password);
			if (user == null) {
				LoginView lv = ((LoginView) views.getComponents()[Interface.LOGIN_VIEW_INDEX]);
				lv.updateErrorMessage("Invalid username or password.");
			} else {
				if(user.getUserNumber() == 0001) {
					switchTo(Interface.ADMIN_VIEW);
					AdminView av = ((AdminView) views.getComponents()[Interface.ADMIN_VIEW_INDEX]);
					av.initSwitch();
					LoginView lv = ((LoginView) views.getComponents()[Interface.LOGIN_VIEW_INDEX]);
					lv.updateErrorMessage("");
				}
				else {
					switchTo(Interface.TEST_VIEW);
					LoginView lv = ((LoginView) views.getComponents()[Interface.LOGIN_VIEW_INDEX]);
					lv.updateErrorMessage("");
				}
			}
		} catch (NumberFormatException e) {
			// ignore
		}
	}
	
	/**
	 * Switches the active (or visible) view upon request.
	 * 
	 * @param view
	 */
	
	public void switchTo(String view) {
		((CardLayout) views.getLayout()).show(views, view);
		
	}
	
	
	/**
	 * Routes a shutdown request to the database before exiting the application. This
	 * allows the database to clean up any open resources it used.
	 */
	
	public void shutdown() {
		try {			
			int choice = JOptionPane.showConfirmDialog(
				views,
				"Are you sure?",
				"Shutdown Survey Interface",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE
			);
			
			if (choice == 0) {
//				db.shutdown();
				System.exit(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
