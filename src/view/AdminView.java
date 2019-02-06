package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import controller.ViewManager;

@SuppressWarnings("serial")
public class AdminView extends JPanel implements ActionListener {
		
	private ViewManager manager;			// manages interactions between the views, model, and database
	private JButton logOffButton;			// button that redirects users to the HomeView (if credentials match)
	private JTextField averageLengthField;		// textfield where the user enters his or her account number
	private JTextField averageNumCharactersField;	// textfield where the user enters his or her PIN
	private JTextField averageSpecialCharactersField;
	private JTextField averageAlphaCharactersField;
	private JTextField averageUpperField;
	private JTextField averageLowerField;
	private JLabel errorMessageLabel;		// label for potential error messages
	
	private double averageLength;
	private double averageAlpha;
	private double averageUpper;
	private double averageLower;
	private double averageNumber;
	private double averageSpecial;
	private double averageOther;
	private JTextField averageStrengthField;
	private double averageStrength;
	
	
	/**
	 * Constructs an instance (or objects) of the LoginView class.
	 * 
	 * @param manager
	 */
	
	public AdminView(ViewManager manager) {
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

		initLogOffButton();
		initErrorMessageLabel();
	}
	
	public void initSwitch() {
		setData();
		initAverageLength();		// textfield where the user enters his or her account number
		initAverageNumCharacters();	// textfield where the user enters his or her PIN
		initAverageSpecialCharacters();
		initAverageAlphaCharacters();
		initAverageUpper();
		initAverageLower();
		initBitStrength();
	}
	
	/*
	 * Initializes the components needed for the account number textfield.
	 */
	
	private void setData() {
		//Average Length
		double lengthPass = 0;
		int index = 0;
		double totalStrength = 0;
		String password;
		double average = 0;
		for(int i = 0002; i <= manager.db.highestUserNum(); i++) {
			password = manager.db.getUser(i).getPassword();
			System.out.println(password);
			totalStrength += bitstrength(password);
			while(password.charAt(index) != ' ') {
				index++;
			}
			lengthPass = index;
			System.out.println(lengthPass);
			average += lengthPass;
		}
		averageLength =  (average / (int)(manager.db.highestUserNum() - 0001));
		//Average number of alphabetical characters
		//Average number of upper case and lower case characters
		double numAlpha = 0;
		double numUpper = 0;
		double numLower = 0;
		double numNum = 0;
		double numSpecial = 0;
		double numOther = 0;
		char character;
		for(int i = 0002; i <= manager.db.highestUserNum(); i++) {
			password = manager.db.getUser(i).getPassword();
			while(password.charAt(index) != ' ') {
				index++;
			}
			lengthPass = index + 1;
			for(int j = 0; j < lengthPass; j++) {
				character = password.charAt(j);
				if((character > 64 && character < 91)) {
					numAlpha++;
					numUpper++;
				}
				else if(character > 96 && character < 123) {
					numAlpha++;
					numLower++;
				}
				else if(character > 47 && character < 58) {
					numNum++;
				}
				else if((character > 32 && character < 48) || (character > 57 && character < 65) || (character > 90 && character < 97) || (character > 122 && character < 127)) {
					numSpecial++;
				}
				else {
					numOther++;
				}
			}
		}
		System.out.println("Length: " + average + "\nNumAlpha: " + numAlpha + "\nNumNumber: " + numNum + "\nnumUpper: " + numUpper + "\nNumLower: " + numLower + "\nNumSpecial: " + numSpecial);
		System.out.println("Average alpha: " + averageAlpha + "\nAverage upper: " + averageUpper + "\nAverage lower: " + averageLower + "\nAverage number: " + averageNumber + "\nAverage Special: " + averageSpecial);
		System.out.println("Highest number: " + manager.db.highestUserNum());
		averageAlpha =  (numAlpha / (manager.db.highestUserNum() - 0001));
		averageUpper = numUpper / (manager.db.highestUserNum() - 0001);
		averageLower = numLower / (manager.db.highestUserNum() - 0001);
		averageNumber = numNum / (manager.db.highestUserNum()- 0001);
		averageSpecial = numSpecial / (manager.db.highestUserNum() - 0001);
		averageOther = numOther / (manager.db.highestUserNum() - 0001);
		averageStrength = totalStrength / (manager.db.highestUserNum() - 0001);
	}
	
	private void initAverageLength() {
		JLabel label = new JLabel("Average Length: ", SwingConstants.RIGHT);
		label.setBounds(50, 60, 150, 35);
		label.setLabelFor(averageLengthField);
		label.setFont(new Font("DialogInput", Font.BOLD, 14));
		
		averageLengthField = new JTextField(20);
		averageLengthField.setText(Double.toString(averageLength));
		averageLengthField.setBounds(205, 60, 200, 35);
		averageLengthField.setEditable(false);
		
		this.add(label);
		this.add(averageLengthField);
	}
	
	/*
	 * Initializes the components needed for the PIN textfield.
	 */
	
	private void initAverageSpecialCharacters() {
		JLabel label = new JLabel("# of Special: ", SwingConstants.RIGHT);
		label.setBounds(50, 260, 150, 35);
		label.setLabelFor(averageSpecialCharactersField);
		label.setFont(new Font("DialogInput", Font.BOLD, 14));
		
		averageSpecialCharactersField = new JTextField(20);
		averageSpecialCharactersField.setBounds(205, 260, 200, 35);
		averageSpecialCharactersField.setText(Double.toString(averageSpecial));
		averageSpecialCharactersField.setEditable(false);

	
		this.add(label);
		this.add(averageSpecialCharactersField);		
	}

	private void initAverageNumCharacters() {
		JLabel label = new JLabel("# of Numbers: ", SwingConstants.RIGHT);
		label.setBounds(50, 220, 150, 35);
		label.setLabelFor(averageNumCharactersField);
		label.setFont(new Font("DialogInput", Font.BOLD, 14));
		
		averageNumCharactersField = new JTextField(20);
		averageNumCharactersField.setBounds(205, 220, 200, 35);
		averageNumCharactersField.setText(Double.toString(averageNumber));
		averageNumCharactersField.setEditable(false);
	
		this.add(label);
		this.add(averageNumCharactersField);		
	}
	
	private void initAverageAlphaCharacters() {
		JLabel label = new JLabel("# of Alpha Characters: ", SwingConstants.RIGHT);
		label.setBounds(50, 100, 150, 35);
		label.setLabelFor(averageAlphaCharactersField);
		label.setFont(new Font("DialogInput", Font.BOLD, 14));
		
		averageAlphaCharactersField = new JTextField(20);
		averageAlphaCharactersField.setBounds(205, 100, 200, 35);
		averageAlphaCharactersField.setText(Double.toString(averageAlpha));
		averageAlphaCharactersField.setEditable(false);
	
		this.add(label);
		this.add(averageAlphaCharactersField);
	}
	
	private void initAverageUpper() {
		JLabel label = new JLabel("# of Uppercase: ", SwingConstants.RIGHT);
		label.setBounds(50, 140, 150, 35);
		label.setLabelFor(averageUpperField);
		label.setFont(new Font("DialogInput", Font.BOLD, 14));
		
		averageUpperField = new JTextField(20);
		averageUpperField.setBounds(205, 140, 200, 35);
		averageUpperField.setText(Double.toString(averageUpper));
		averageUpperField.setEditable(false);
	
		this.add(label);
		this.add(averageUpperField);
	}
	
	private void initAverageLower() {
		JLabel label = new JLabel("# of Lowercase: ", SwingConstants.RIGHT);
		label.setBounds(50, 180, 150, 35);
		label.setLabelFor(averageLowerField);
		label.setFont(new Font("DialogInput", Font.BOLD, 14));
		
		averageLowerField = new JTextField(20);
		averageLowerField.setBounds(205, 180, 200, 35);
		averageLowerField.setText(Double.toString(averageLower));
		averageLowerField.setEditable(false);
	
		this.add(label);
		this.add(averageLowerField);
	}
	
	private void initBitStrength() {
		JLabel label = new JLabel("Bitstrength: ", SwingConstants.RIGHT);
		label.setBounds(50, 300, 150, 35);
		label.setLabelFor(averageStrengthField);
		label.setFont(new Font("DialogInput", Font.BOLD, 14));
		
		averageStrengthField = new JTextField(20);
		averageStrengthField.setBounds(205, 300, 200, 35);
		averageStrengthField.setText(Double.toString(averageStrength));
		averageStrengthField.setEditable(false);
	
		this.add(label);
		this.add(averageStrengthField);
	}
	
	/*
	 * Initializes the components needed for the login button.
	 */
	
	private void initLogOffButton() {	
		logOffButton = new JButton("Log Off");
		logOffButton.setBounds(205, 360, 200, 35);
		logOffButton.addActionListener(this);
		
		this.add(logOffButton);
	}
	
	private void initErrorMessageLabel() {
		errorMessageLabel.setBounds(0, 240, 500, 35);
		errorMessageLabel.setFont(new Font("DialogInput", Font.ITALIC, 14));
		errorMessageLabel.setForeground(Color.RED);
		
		this.add(errorMessageLabel);
	}
	
	public double bitstrength(String password) {
		int passLength = 0;
		while(passLength < 30 && password.charAt(passLength) != ' ') {
			passLength++;
		}
		double bitStrength = 0;
		int type = 0;
		System.out.println(password);
		boolean checkLower = false;
		boolean checkUpper = false;
		boolean checkNum = false;
		boolean checkSpecial = false;
		for(int j = 0; j < passLength; j++) {
			char character = password.charAt(j);
			if(character > 96 && character < 123 && !checkLower) {
				type += 26;
				checkLower = true;
			}
			else if(character > 64 && character < 91 && !checkUpper) {
				type += 26;
				checkUpper = true;
			}
			else if(character > 47 && character < 58 && !checkNum) {
				type += 10;
				checkNum = true;
			}
			else if(!checkSpecial && (character > 32 && character < 48) || (character > 57 && character < 65) || (character > 90 && character < 97) || (character > 122 && character < 127)) {
				type += 33;
				checkSpecial = true;
			}
		}	
		bitStrength = log2(Math.pow(type, passLength));
		System.out.println("Password : " + password);
		System.out.println("Length: " + passLength);
		System.out.println("Bitstrength: " + bitStrength +"\n");
		return bitStrength;
	}
	
	public static double logb(double a, double b) {
		return Math.log(a) / Math.log(b);
	}
	
	public static double log2(double a) {
		return logb(a, 2);
	}
	
	/*
	 * Initializes the components needed for the create button.
	 */

	
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
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		
		if (source.equals(logOffButton)) {
			manager.switchTo(Interface.LOGIN_VIEW);
		} 
		else {
			System.err.println("ERROR: Action command not found (" + e.getActionCommand() + ")");
		}
	}
}
