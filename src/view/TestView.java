package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
//import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import controller.ViewManager;
import model.User;

@SuppressWarnings("serial")
public class TestView extends JPanel implements ActionListener {
	private JButton submitButton;
	private JButton returnButton;
	private JFrame frame = new JFrame();
	private JLabel errorMessageLabel;		// label for potential error messages
	private JComboBox Question1;
	private JComboBox Question2;
	private JComboBox Question3;
	private JComboBox Question4;
	private ViewManager manager;		// manages interactions between the views, model, and database
	
	public TestView(ViewManager manager) {
		super();
		
		this.manager = manager;
		initialize();
	}
	
private void initialize() {
		
		// TODO
		//
		// this is a placeholder for this view and should be removed once you start
		// building the CreateView.
		this.setLayout(null);
		this.setVisible(false);

		initScreen();
		initQuestion1();
		initQuestion2();
		initQuestion3();
		initQuestion4();
		
		initSubmitButton();
		initReturnButton();
		// TODO
		//
		// this is where you should build the CreateView (i.e., all the components that
		// allow the user to enter his or her information and create a new account).
		//
		// feel free to use my layout in LoginView as an example for laying out and
		// positioning your components.
	}
	
	//Username and Password fields
	private void initScreen() {
//		JPanel panel = new JPanel();
//		
//		JScrollPane scrollPane = new JScrollPane(panel);
//		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
//		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
//		scrollPane.setBounds(50, 30, 300, 50);
//		
//		this.add(scrollPane);
//		
//		this.setLayout(new BorderLayout());
//		
//		JScrollBar hbar = new JScrollBar(JScrollBar.HORIZONTAL, 30, 20, 0, 500);
//		JScrollBar vbar = new JScrollBar(JScrollBar.VERTICAL, 30, 40, 0, 500);
//		
//		class MyAdjustmentListener implements AdjustmentListener {
//			public void adjustmentValueChanged(AdjustmentEvent e) {
//				this.repaint();
//			}
//		}
//		
//		hbar.addAdjustmentListener(new MyAdjustmentListener());
//		vbar.addAdjustmentListener(new MyAdjustmentListener());
//		
//		
//		this.getRootPane().add(hbar, BorderLayout.SOUTH);
//		this.getRootPane().add(vbar, BorderLayout.EAST);
//		this.setVisible(true);
//		
//		
//		JLabel label = new JLabel("The following questions pertain to you computer use.", SwingConstants.RIGHT);
//		label.setBounds(10,50,400,100);
//		label.setFont(new Font("DialogInput", Font.BOLD, 12));
		
//		this.add(label);
	}

	private void initQuestion1() {
		JLabel label = new JLabel("How many hours do you use devices every day?", SwingConstants.LEFT);
		label.setBounds(10,0,400,100);
		
		String[] options = new String[] {"", "Under 1 hour", "1 - 3 hours", "3 - 5 hours", "More than 5 hours"};
		
		label.setFont(new Font("DialogInput", Font.BOLD, 14));
		
		Question1 = new JComboBox<Object>(options);
		Question1.setBounds(10, 60, 200, 25);

		
		this.add(label);	
		this.add(Question1);
	}
	
	private void initQuestion2() {
		JLabel label = new JLabel("How many hours do you use social media every day?", SwingConstants.LEFT);
		label.setBounds(10,70,400,100);
		
		String[] options = new String[] {"", "Under 1 hour", "1 - 3 hours", "3 - 5 hours", "More than 5 hours"};
		
		label.setFont(new Font("DialogInput", Font.BOLD, 14));
		
		Question2 = new JComboBox<Object>(options);
		Question2.setBounds(10, 130, 200, 25);

		
		this.add(label);	
		this.add(Question2);
	}
	
	private void initQuestion3() {
		JLabel label = new JLabel("What do you mainly use your device(s) for?", SwingConstants.LEFT);
		label.setBounds(10,140,400,100);
		
		String[] options = new String[] {"", "Education", "Work", "Entertainment", "Other"};
		
		label.setFont(new Font("DialogInput", Font.BOLD, 14));
		
		Question3 = new JComboBox<Object>(options);
		Question3.setBounds(10, 200, 200, 25);

		
		this.add(label);	
		this.add(Question3);
	}
	
	private void initQuestion4() {
		JLabel label = new JLabel("How often do you buy new technology?", SwingConstants.LEFT);
		label.setBounds(10,210,400,100);
		
		String[] options = new String[] {"", "Biweekly", "Monthly", "Every 6 months", "Every year", "Every couple of years"};
		
		label.setFont(new Font("DialogInput", Font.BOLD, 14));
		
		Question4 = new JComboBox<Object>(options);
		Question4.setBounds(10, 270, 200, 25);

		
		this.add(label);	
		this.add(Question4);
	}
	
	//Buttons
	private void initSubmitButton() {
		submitButton = new JButton("Submit Answers");
		submitButton.setBounds(150, 310, 200, 35);
		submitButton.addActionListener(this);

		this.add(submitButton);		
	}

	private void initReturnButton() {	
		returnButton = new JButton("Cancel");
		returnButton.setBounds(200, 360, 100, 25);
		returnButton.addActionListener(this);
		
		this.add(returnButton);
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
			if(Question1.getSelectedIndex() == 0 || Question2.getSelectedIndex() == 0 || Question3.getSelectedIndex() == 0 || Question4.getSelectedIndex() == 0) {
				JOptionPane.showMessageDialog(null, "Make sure every question is answered.");
//				manager.switchBack();
			}
			else {
				JOptionPane.showMessageDialog(null, "Thank you for your response. You will now be taken back to the login screen.\nPlease notify the test administrator that you are done.");
				manager.switchTo(Interface.LOGIN_VIEW);
			}
		}
		else if(source.equals(returnButton)) {
			JOptionPane.showMessageDialog(null, "You have not submitted your answers. Your response will not be saved.");
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