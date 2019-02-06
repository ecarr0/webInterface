package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.swing.JButton;
import javax.swing.JPanel;

import controller.ViewManager;

@SuppressWarnings("serial")
public class HomeView extends JPanel implements ActionListener {
	
	private ViewManager manager;		// manages interactions between the views, model, and database
	private JButton logoffButton;

	/**
	 * Constructs an instance (or objects) of the HomeView class.
	 * 
	 * @param manager
	 */
	
	public HomeView(ViewManager manager) {
		super();
		
		this.manager = manager;
		initialize();
	}
	
	///////////////////// PRIVATE METHODS /////////////////////////////////////////////
	
	/*
	 * Initializes the HomeView components.
	 */
	
	private void initialize() {
		
		// TODO
		//
		// this is a placeholder for this view and should be removed once you start
		// building the HomeView.
		
		//this.add(new javax.swing.JLabel("HomeView", javax.swing.SwingConstants.CENTER));
		this.setLayout(null);
		
		initLogOffButton();

	}
	private void initLogOffButton() {	
		logoffButton = new JButton("Log Off");
		logoffButton.setBounds(150, 100, 100, 35);
		logoffButton.addActionListener(this);
		
		this.add(logoffButton);
	}

	/*
	 * HomeView is not designed to be serialized, and attempts to serialize will throw an IOException.
	 * 
	 * @param oos
	 * @throws IOException
	 */
	
	private void writeObject(ObjectOutputStream oos) throws IOException {
		throw new IOException("ERROR: The HomeView class is not serializable.");
	}
	
	///////////////////// OVERRIDDEN METHODS //////////////////////////////////////////
	
	/*
	 * Responds to button clicks and other actions performed in the HomeView.
	 * 
	 * @param e
	 */
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		
		if(source.equals(logoffButton)) {
			manager.setUser(null);
			manager.setUser(null);
			manager.switchTo(Interface.LOGIN_VIEW);
		}
		
		else {
			System.err.println("ERROR: Action command not found (" + e.getActionCommand() + ")");
		}

	}
}