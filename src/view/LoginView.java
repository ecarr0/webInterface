package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import controller.ViewManager;
import model.User;

@SuppressWarnings("serial")
public class LoginView extends JPanel implements ActionListener {
		
	private ViewManager manager;			// manages interactions between the views, model, and database
	private JButton loginButton;			// button that redirects users to the HomeView (if credentials match)
	private JButton createButton;			// button that directs users to the CreateView
	private JButton powerButton;			// button that powers off the ATM
	private JTextField usernameField;		// textfield where the user enters his or her account number
	private JPasswordField passwordField;	// textfield where the user enters his or her PIN
	private JLabel errorMessageLabel;		// label for potential error messages
	private JTextField corporationField;
	/**
	 * Constructs an instance (or objects) of the LoginView class.
	 * 
	 * @param manager
	 */
	
	public LoginView(ViewManager manager) {
		super();
		
		this.manager = manager;
		this.errorMessageLabel = new JLabel("", SwingConstants.CENTER);
		initialize();
	}
	
	///////////////////// INSTANCE METHODS ////////////////////////////////////////////
	
	/**
	 * Updates the error message label.
	 * 
	 * @param errorMessage
	 */
	
	public void updateErrorMessage(String errorMessage) {
		errorMessageLabel.setText(errorMessage);
	}
	
	///////////////////// PRIVATE METHODS /////////////////////////////////////////////
	
	/*
	 * Initializes the LoginView components.
	 */
	
	private void initialize() {
		this.setLayout(null);
		
		initCorporateField();
		initUsernameField();
		initPasswordField();
		initLoginButton();
		initCreateButton();
		initErrorMessageLabel();
		initPowerButton();
	}
	
	/*
	 * Initializes the components needed for the account number textfield.
	 */
	
	private void initCorporateField() {
		JLabel label = new JLabel("TestGear", SwingConstants.LEFT);
		label.setBounds(165, 10, 300, 50);
		label.setFont(new Font("DialogInput", Font.BOLD, 36));
		
		this.add(label);
	}
	
	private void initUsernameField() {
		JLabel label = new JLabel("Email: ", SwingConstants.RIGHT);
		label.setBounds(60, 100, 95, 35);
		label.setLabelFor(usernameField);
		label.setFont(new Font("DialogInput", Font.BOLD, 14));
		
		usernameField = new JTextField(20);
		usernameField.setBounds(155, 100, 200, 35);
		
		this.add(label);
		this.add(usernameField);
	}
	
	/*
	 * Initializes the components needed for the PIN textfield.
	 */
	
	private void initPasswordField() {
		JLabel label = new JLabel("Password: ", SwingConstants.RIGHT);
		label.setBounds(60, 140, 95, 35);
		label.setLabelFor(passwordField);
		label.setFont(new Font("DialogInput", Font.BOLD, 14));
		
		passwordField = new JPasswordField(20);
		passwordField.setBounds(155, 140, 200, 35);
		
		this.add(label);
		this.add(passwordField);
	}
	
	/*
	 * Initializes the components needed for the login button.
	 */
	
	private void initLoginButton() {	
		loginButton = new JButton("Login");
		loginButton.setBounds(155, 180, 200, 35);
		loginButton.addActionListener(this);
		
		this.add(loginButton);
	}
	
	private void initErrorMessageLabel() {
		errorMessageLabel.setBounds(0, 240, 500, 35);
		errorMessageLabel.setFont(new Font("DialogInput", Font.ITALIC, 14));
		errorMessageLabel.setForeground(Color.RED);
		
		this.add(errorMessageLabel);
	}
	
	/*
	 * Initializes the components needed for the create button.
	 */
	
	private void initCreateButton() {
		JLabel label = new JLabel("Need an account? Create one today!", SwingConstants.CENTER);
		label.setBounds(10, 320, 500, 35);
		label.setLabelFor(createButton);
		label.setFont(new Font("DialogInput", Font.BOLD, 14));
		
		createButton = new JButton("Create an Account");
		createButton.setBounds(135, 360, 248, 35);
		createButton.addActionListener(this);
		
		this.add(label);
		this.add(createButton);		
	}
	
	/*
	 * Initializes the components needed for the power button.
	 */
	
	private void initPowerButton() {
		powerButton = new JButton();
		powerButton.setBounds(5, 5, 50, 50);
		powerButton.addActionListener(this);
		
		try {
			Image image = ImageIO.read(new File("images/power-off.png"));
			powerButton.setIcon(new ImageIcon(image));
		} catch (Exception e) {
			powerButton.setText("OFF");
		}
		
		this.add(powerButton);
	}
	
	/*
	 * LoginView is not designed to be serialized, and attempts to serialize will throw an IOException.
	 * 
	 * @param oos
	 * @throws IOException
	 */
	
	private void writeObject(ObjectOutputStream oos) throws IOException {
		throw new IOException("ERROR: The LoginView class is not serializable.");
	}

	///////////////////// OVERRIDDEN METHODS //////////////////////////////////////////
	
	/*
	 * Responds to button clicks performed in the LoginView.
	 * 
	 * @param e
	 */
	
	@SuppressWarnings("deprecation")
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source.equals(loginButton)) {
			manager.login(usernameField.getText(), passwordField.getText());
			usernameField.setText("");
			passwordField.setText("");
		} else if (source.equals(createButton)) {
			manager.switchTo(Interface.CREATE_VIEW);
		} else if (source.equals(powerButton)) {
			manager.shutdown();
		} else {
			System.err.println("ERROR: Action command not found (" + e.getActionCommand() + ")");
		}
	}
}
