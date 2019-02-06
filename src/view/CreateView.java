package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.swing.JButton;
//import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import controller.ViewManager;
import model.User;

@SuppressWarnings("serial")
public class CreateView extends JPanel implements ActionListener {
	
	private ViewManager manager;		// manages interactions between the views, model, and database
	private JPasswordField passwordField;
	private JTextField usernameField;
	private JButton submitButton;
	private JButton returnButton;
	private User newUser;
	private long newUserNumber;
	private JLabel errorMessageLabel;		// label for potential error messages

	
	/**
	 * Constructs an instance (or object) of the CreateView class.
	 * 
	 * @param manager
	 */
	
	public CreateView(ViewManager manager) {
		super();
		
		this.manager = manager;
		this.errorMessageLabel = new JLabel("", SwingConstants.CENTER);

		initialize();
	}
	
	///////////////////// PRIVATE METHODS /////////////////////////////////////////////
	
	/*
	 * Initializes the CreateView components.
	 */
	
	private void initialize() {
		
		// TODO
		//
		// this is a placeholder for this view and should be removed once you start
		// building the CreateView.
		this.setLayout(null);

		initCorporateField();
		
		initPasswordField();
		initUsernameField();
		
		initSubmitButton();
		initReturnButton();
		
		initErrorMessageLabel();

		// TODO
		//
		// this is where you should build the CreateView (i.e., all the components that
		// allow the user to enter his or her information and create a new account).
		//
		// feel free to use my layout in LoginView as an example for laying out and
		// positioning your components.
	}
	
	private void initCorporateField() {
		JLabel label = new JLabel("TestGear", SwingConstants.RIGHT);
		label.setBounds(30, 10, 300, 50);
		label.setFont(new Font("DialogInput", Font.BOLD, 36));
		
		JLabel label2 = new JLabel("Registration", SwingConstants.RIGHT);
		label2.setBounds(80, 60, 300, 50);
		label2.setFont(new Font("DialogInput", Font.BOLD, 36));
		
		this.add(label);
		this.add(label2);
	}
	
	//Username and Password fields
	private void initPasswordField() {
		JLabel label = new JLabel("Password: ", SwingConstants.RIGHT);
		label.setBounds(0,200,130,25);
		label.setLabelFor(passwordField);
		label.setFont(new Font("DialogInput", Font.BOLD, 14));
		
		passwordField = new JPasswordField(20);
		passwordField.setBounds(155, 200, 200, 25);
		
		this.add(label);
		this.add(passwordField);	
	}

	private void initUsernameField() {
		JLabel label = new JLabel("Email: ", SwingConstants.RIGHT);
		label.setBounds(0,150,130,25);
		label.setLabelFor(usernameField);
		label.setFont(new Font("DialogInput", Font.BOLD, 14));
		
		usernameField = new JTextField(20);
		usernameField.setBounds(155, 150, 200, 25);
		
		this.add(label);
		this.add(usernameField);	
	}
	
	//Buttons
	private void initSubmitButton() {
		submitButton = new JButton("Create Account");
		submitButton.setBounds(155, 300, 200, 35);
		submitButton.addActionListener(this);

		this.add(submitButton);		
	}

	private void initReturnButton() {	
		returnButton = new JButton("Cancel");
		returnButton.setBounds(205, 340, 100, 25);
		returnButton.addActionListener(this);
		
		this.add(returnButton);
	}
	
	private void initErrorMessageLabel() {
		errorMessageLabel.setBounds(0, 240, 500, 35);
		errorMessageLabel.setFont(new Font("DialogInput", Font.ITALIC, 14));
		errorMessageLabel.setForeground(Color.RED);
		errorMessageLabel.setText("");
		
		this.add(errorMessageLabel);
	}
	
	public void updateErrorMessage(String errorMessage) {
		errorMessageLabel.setText(errorMessage);
	}
	
	/*
	 * CreateView is not designed to be serialized, and attempts to serialize will throw an IOException.
	 * 
	 * @param oos
	 * @throws IOException
	 */
	
	private void writeObject(ObjectOutputStream oos) throws IOException {
		throw new IOException("ERROR: The CreateView class is not serializable.");
	}
	
	///////////////////// OVERRIDDEN METHODS //////////////////////////////////////////
	
	/*
	 * Responds to button clicks and other actions performed in the CreateView.
	 * 
	 * @param e
	 */
	
	@SuppressWarnings("deprecation")
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		
		if(source.equals(submitButton)) { 
			boolean hasAt = false;
			boolean hasPeriod = false;
			for(int i = 0; i < usernameField.getText().length(); i++) {
				if(usernameField.getText().charAt(i) == '@') {
					hasAt = true;
				}
				if(usernameField.getText().charAt(i) == '.') {
					hasPeriod = true;
				}
			}
			if(hasAt && hasPeriod) {
				newUserNumber = manager.db.highestUserNum() + 1;
				newUser = new User(newUserNumber, usernameField.getText(), passwordField.getText());
				System.out.println(newUser.toString());
				manager.db.insertAccount(newUser);
				passwordField.setText("");
				usernameField.setText("");
				manager.switchTo(Interface.LOGIN_VIEW);
			}
			else {
				updateErrorMessage("Invalid email.");
			}
		}
		else if(source.equals(returnButton)) {
			passwordField.setText("");
			usernameField.setText("");
			manager.switchTo(Interface.LOGIN_VIEW);
		}
		else {
			System.out.println("Error");
		}
		// TODO
		//
		// this is where you'll setup your action listener, which is responsible for
		// responding to actions the user might take in this view (an action can be a
		// user clicking a button, typing in a textfield, etc.).
		//
		// feel free to use my action listener in LoginView.java as an example.
	}
}