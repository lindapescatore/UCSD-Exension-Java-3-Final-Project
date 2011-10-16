package Java3Assignment3;

import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.SwingConstants;
import javax.swing.KeyStroke;
import java.awt.Point;
import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JMenuItem;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JFrame;
import javax.swing.JDialog;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class InventorySystemViewGUI implements Observer {

	private final static String newline = "\n";  //  @jve:decl-index=0:

	//MAIN MENU ITEMS
	private JFrame jFrame = null;
	private JPanel jContentPane = null;
	private JMenuBar jJMenuBar = null;
	private JMenu fileMenu = null;
	private JMenu editInventoryMenu = null;
	private JMenu listMenu = null;
	private JMenu helpMenu = null;
	private JMenuItem exitMenuItem = null;
	private JMenuItem logonMenuItem = null;
	private JMenuItem aboutMenuItem = null;
	private JMenuItem createMenuItem = null;
	private JMenuItem modifyMenuItem = null;
	private JMenuItem listMenuItem = null;

	//DIALOGS
	private JDialog aboutDialog = null;
	private JDialog loginDialog = null;

	//PANELS
	private JPanel loginContentPane = null;
	private JPanel aboutContentPane = null;
	private JLabel aboutVersionLabel = null;
	private JPanel createPanel = null;
	private JPanel modifyPanel = null;
	private JPanel listPanel = null;
	private JPanel outputAreaPanel;
	private JPanel outputPanel;
	private JPanel listingOutputTextArea;
	private JPanel loginPanelTitle;
	private JPanel loginPanelUsername;
	private JPanel loginPanelPassword;
	private JPanel loginPanelButtons;
	private JPanel loginButtonPanel;

	//TEXT AREAS
	protected JTextArea outputArea;

	//LABELS AND TEXT FIELDS
	private JLabel loginTitle;
	private JLabel usernameJLabel;
	private JLabel passwordJLabel;
	private JTextField usernameJTextField;
	private JPasswordField passwordField;

	InventorySystemModelJDBC m_InventoryModel;
	Type m_type;

	String username = null;
	String password = null;
	boolean isLoggedOn = false;

	//default constructor
	public InventorySystemViewGUI(InventorySystemModelJDBC model) {
		m_InventoryModel = model;
		m_InventoryModel.addObserver(this);
		run();
	}

	//main entry point for the view class
	private void run() {
		this.getJFrame();
	}

	public void update(Observable obs, Object typeOfChange) {
		String modifiedSKU = "null'";
		modifiedSKU =  m_InventoryModel.getLastSKUModified();

		if (typeOfChange.equals(TypeOfChange.modifyChange)) {

			outputArea.append("The inventory has changed, the last modified SKU was: " + modifiedSKU + "." + newline + newline);
			outputArea.append("Listing the new inventory item... " + newline + newline);

			outputArea.append("New Item: " + newline + newline);

			checkOnUpadate(modifiedSKU);
		}
		if (typeOfChange.equals(TypeOfChange.delete)) {

			outputArea.append("The inventory has changed, the last deleted SKU was: " + modifiedSKU + "." + newline + newline);
		}
	}

	private void checkOnUpadate(String currentSKU) {
		listSingleItemDetail(currentSKU);
	}

	private void listSingleItemDetail(String currentSKU) {
		if (m_InventoryModel.doesItemExist(currentSKU)) {

			String infoFromDB = m_InventoryModel.retrieveItem(currentSKU);

			String[] fromDBIntoArray = new String[7];

			fromDBIntoArray = infoFromDB.split("\t");

			String[] cdOutput = { "SKU:", "Album:", "Artist:", "Year:", "Label:", "Price:", "null", "null" };

			String[] dvdOutput = { "SKU:", "Title:", "Studio:", "Director:", "Rating:", "Actors:", "Price:", "null"  };

			String[] bookOutput = { "SKU:", "Title:", "ISBN:", "Author:", "Publisher:", "Publishing Year:", "Publishing City:", "Price:" };

			String[] outputUsed = new String[8];

			if (currentSKU.charAt(0) == 'C') {
				outputUsed = cdOutput;
			}
			else if (currentSKU.charAt(0) == 'D') {
				outputUsed = dvdOutput;
			}
			else if (currentSKU.charAt(0) == 'B') {
				outputUsed = bookOutput;
			}

			for (int i = 0; i < fromDBIntoArray.length; i++) {
				outputArea.append(outputUsed[i] +  "\t" + fromDBIntoArray[i] + newline);
			}

			outputArea.append("" + newline);
		}
		else {
			outputArea.append("Error retrieving the item." + newline);
		}
	}

	/**
	 * This method initializes jFrame
	 * 
	 * @return javax.swing.JFrame
	 */
	private JFrame getJFrame() {		
		if (jFrame == null) {
			jFrame = new JFrame();
			jFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			jFrame.setJMenuBar(getJJMenuBar());
			jFrame.setSize(1024, 768);
			jFrame.setContentPane(getJContentPane());
			jFrame.setTitle("Inventory Control System");
			jFrame.setVisible(true);

			if (!isLoggedOn) {
				JDialog loginDialog = getLoginDialog();
				loginDialog.setSize(new Dimension(300, 200));
				Point loc = getJFrame().getLocation();
				loc.translate(100, 100);
				loginDialog.setLocation(loc);
				loginDialog.setVisible(true);
			}
		}
		return jFrame;
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());

			jContentPane.add(getOutputAreaPanel(), BorderLayout.SOUTH);

			JScrollPane scrollPane = new JScrollPane(outputAreaPanel);

			scrollPane.setVerticalScrollBarPolicy(
					JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

			jContentPane.add(scrollPane);
		}
		return jContentPane;
	}

	private JDialog getLoginDialog() {

		if (loginDialog == null) {

			loginDialog = new JDialog(getJFrame(), true);
			loginDialog.setTitle("Credentials Logon");
			loginDialog.setContentPane(getLoginContentPane());
		}


		return loginDialog;
	}

	private JPanel getLoginContentPane() {

		if (loginContentPane == null) {
			loginContentPane = new JPanel(new GridLayout(5, 0));

			loginPanelTitle = new JPanel(new FlowLayout());
			loginPanelUsername = new JPanel(new FlowLayout());
			loginPanelPassword = new JPanel(new FlowLayout());
			loginPanelButtons = new JPanel(new FlowLayout());

			loginTitle = new JLabel("Please login to access the database:");

			usernameJLabel = new JLabel("Username:");
			passwordJLabel = new JLabel("Password:");

			usernameJTextField = new JTextField("", 15);
			usernameJTextField.addActionListener(
					new ActionListener()
					{
						public void actionPerformed( ActionEvent event )
						{
							username = event.getActionCommand();
						}
					}
			);

			passwordField = new JPasswordField(10);

			passwordField.addActionListener(new ActionListener()
			{
				public void actionPerformed( ActionEvent event )
				{
					password = event.getActionCommand();
				}
			}
			);

			loginPanelTitle.add(loginTitle);
			loginPanelUsername.add(usernameJLabel);
			loginPanelUsername.add(usernameJTextField);
			loginPanelPassword.add(passwordJLabel);
			loginPanelPassword.add(passwordField);
			loginPanelButtons.add(createButtonPanel());

			loginContentPane.add(loginPanelTitle);
			loginContentPane.add(loginPanelUsername);
			loginContentPane.add(loginPanelPassword);
			loginContentPane.add(loginPanelButtons);	
		}


		return loginContentPane;
	}

	protected JComponent createButtonPanel() {
		loginButtonPanel = new JPanel(new GridBagLayout());

		GridBagConstraints c = new GridBagConstraints();

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 2;
		c.gridy = 0;

		JButton okButton = new JButton("OK");
		JButton helpButton = new JButton("Help");

		okButton.addActionListener(
				new ActionListener()
				{
					public void actionPerformed( ActionEvent event )
					{		
						char[] input = new char[password.length()];
						
						for (int i = 0; i < input.length; i++) {
							input[i] = password.charAt(i);
						}

						if (isPasswordCorrect(input)) {
							JOptionPane.showMessageDialog(getJFrame(),
							"You are now logged into the database.");
							isLoggedOn = m_InventoryModel.setServerCredentials(username, password);


							if ((loginDialog != null) && (isLoggedOn)) {
								loginDialog.setVisible(false);
							}

							if (isLoggedOn) {
								if (createPanel != null) {
									jContentPane.remove(createPanel);
								}
								if (modifyPanel != null) {
									jContentPane.remove(modifyPanel);
								}
								jContentPane.add(getListPanel(), BorderLayout.NORTH);
								jContentPane.validate();	
								jContentPane.updateUI();
							}

						} else {
							JOptionPane.showMessageDialog(getJFrame(),
									"Invalid password. Try again.",
									"Error Message",
									JOptionPane.ERROR_MESSAGE);
						}

						//Zero out the possible password, for security.
						Arrays.fill(input, '0');

						passwordField.selectAll();
						resetFocus();
					}
				}
		);
		helpButton.addActionListener(
				new ActionListener()
				{
					public void actionPerformed( ActionEvent event )
					{
						JOptionPane.showMessageDialog(getJFrame(),
								"The username and password is your employee ID," +
						"Please ask your manager for more help.");
					}
				}
		);

		loginButtonPanel.add(okButton, c);
		c.gridx = 3;
		loginButtonPanel.add(helpButton, c);

		return loginButtonPanel;
	}

	/**
	 * Checks the passed-in array against the correct password.
	 * After this method returns, you should invoke eraseArray
	 * on the passed-in array.
	 */
	private static boolean isPasswordCorrect(char[] input) {
		boolean isCorrect = true;

		char[] correctPassword = { 'p', 'a', 's', 's', 'w', 'o', 'r', 'd' };

		if (input.length != correctPassword.length) {
			isCorrect = false;
		} else {
			isCorrect = Arrays.equals (input, correctPassword);
		}

		//Zero out the password.
		Arrays.fill(correctPassword,'0');

		return isCorrect;
	}

	//Must be called from the event dispatch thread.
	protected void resetFocus() {
		passwordField.requestFocusInWindow();
	}


	/**
	 * This method initializes createPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getCreatePanel() {
		if (createPanel == null) {
			createPanel = new CreatePanel(m_InventoryModel);
		}
		return createPanel;
	}

	/**
	 * This method initializes modifyPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getModifyPanel() {
		if (modifyPanel == null) {
			modifyPanel = new ModifyPanel(m_InventoryModel);
		}
		return modifyPanel;
	}

	/**
	 * This method initializes listPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getListPanel() {
		if (listPanel == null) {
			listPanel = new ListPanel(m_InventoryModel);
		}
		return listPanel;
	}

	/**
	 * This method initializes jJMenuBar	
	 * 	
	 * @return javax.swing.JMenuBar	
	 */
	private JMenuBar getJJMenuBar() {
		if (jJMenuBar == null) {
			jJMenuBar = new JMenuBar();
			jJMenuBar.add(getFileMenu());
			jJMenuBar.add(getEditInventoryMenu());
			jJMenuBar.add(getListMenu());
			jJMenuBar.add(getHelpMenu());
		}
		return jJMenuBar;
	}

	/**
	 * This method initializes jMenu	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getFileMenu() {
		if (fileMenu == null) {
			fileMenu = new JMenu();
			fileMenu.setText("File");
			fileMenu.add(getLogonMenuItem());
			fileMenu.add(getExitMenuItem());
		}
		return fileMenu;
	}

	/**
	 * This method initializes jMenu	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getEditInventoryMenu() {
		if (editInventoryMenu == null) {
			editInventoryMenu = new JMenu();
			editInventoryMenu.setText("Edit Inventory");
			editInventoryMenu.add(getCreateMenuItem());
			editInventoryMenu.add(getModifyMenuItem());
		}
		return editInventoryMenu;
	}

	/**
	 * This method initializes ListMenu	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getListMenu() {
		if (listMenu == null) {
			listMenu = new JMenu();
			listMenu.setText("List Inventory");
			listMenu.add(getListMenuItem());
		}
		return listMenu;
	}

	/**
	 * This method initializes HelpMenu	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getHelpMenu() {
		if (helpMenu == null) {
			helpMenu = new JMenu();
			helpMenu.setText("Help");
			helpMenu.add(getAboutMenuItem());
		}
		return helpMenu;
	}

	/**
	 * This method initializes ExitMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getExitMenuItem() {
		if (exitMenuItem == null) {
			exitMenuItem = new JMenuItem();
			exitMenuItem.setText("Exit");
			exitMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.exit(0);
				}
			});
		}
		return exitMenuItem;
	}


	/**
	 * This method initializes ExitMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getLogonMenuItem() {
		if (logonMenuItem == null) {
			logonMenuItem = new JMenuItem();
			logonMenuItem.setText("Login");
			logonMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (!isLoggedOn) {
						JDialog loginDialog = getLoginDialog();
						loginDialog.setSize(new Dimension(300, 200));
						Point loc = getJFrame().getLocation();
						loc.translate(100, 100);
						loginDialog.setLocation(loc);
						loginDialog.setVisible(true);
					}
					else {
						JOptionPane.showMessageDialog(getJFrame(),
								"You are already logged on to the database.",
								"Error Message",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			});
		}
		return logonMenuItem;
	}

	/**
	 * This method initializes AboutMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getAboutMenuItem() {
		if (aboutMenuItem == null) {
			aboutMenuItem = new JMenuItem();
			aboutMenuItem.setText("About");
			aboutMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JDialog aboutDialog = getAboutDialog();
					aboutDialog.setSize(new Dimension(300, 200));
					Point loc = getJFrame().getLocation();
					loc.translate(100, 100);
					aboutDialog.setLocation(loc);
					aboutDialog.setVisible(true);
				}
			});
		}
		return aboutMenuItem;
	}

	/**
	 * This method initializes aboutDialog	
	 * 	
	 * @return javax.swing.JDialog
	 */
	private JDialog getAboutDialog() {
		if (aboutDialog == null) {
			aboutDialog = new JDialog(getJFrame(), true);
			aboutDialog.setTitle("About");
			aboutDialog.setContentPane(getAboutContentPane());
		}
		return aboutDialog;
	}

	/**
	 * This method initializes aboutContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getAboutContentPane() {
		if (aboutContentPane == null) {
			aboutContentPane = new JPanel();

			aboutContentPane.setLayout(new BorderLayout());
			aboutContentPane.add(getAboutVersionLabel(), BorderLayout.CENTER);
		}
		return aboutContentPane;
	}

	/**
	 * This method initializes aboutVersionLabel	
	 * 	
	 * @return javax.swing.JLabel	
	 */
	private JLabel getAboutVersionLabel() {
		if (aboutVersionLabel == null) {
			aboutVersionLabel = new JLabel();
			aboutVersionLabel.setText("Inventory System Version 2.0, now with MYSQL!");
			aboutVersionLabel.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return aboutVersionLabel;
	}

	/**
	 * This method initializes CreateMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getCreateMenuItem() {
		if (createMenuItem == null) {
			createMenuItem = new JMenuItem();
			createMenuItem.setText("Create Listing");
			createMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,
					Event.CTRL_MASK, true));
			createMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					if (isLoggedOn) {
						if (listPanel != null) {
							jContentPane.remove(listPanel);
						}
						if (modifyPanel != null) {
							jContentPane.remove(modifyPanel);
						}
						jContentPane.add(getCreatePanel(), BorderLayout.NORTH);
						jContentPane.updateUI();
						jContentPane.repaint();
						jContentPane.validate();	
					}
					else {
						JOptionPane.showMessageDialog(getJFrame(),
								"You are not logged on to the database.",
								"Error Message",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			});

		}
		return createMenuItem;
	}

	/**
	 * This method initializes ModifyMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getModifyMenuItem() {
		if (modifyMenuItem == null) {
			modifyMenuItem = new JMenuItem();
			modifyMenuItem.setText("Modify Listing");
			modifyMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M,
					Event.CTRL_MASK, true));
			modifyMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (isLoggedOn) {
						if (listPanel != null) {
							jContentPane.remove(listPanel);
						}
						if (createPanel != null) {
							jContentPane.remove(createPanel);
						}
						jContentPane.add(getModifyPanel(), BorderLayout.NORTH);
						jContentPane.repaint();
						jContentPane.validate();	
					}
					else {
						JOptionPane.showMessageDialog(getJFrame(),
								"You are not logged on to the database.",
								"Error Message",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			});
		}
		return modifyMenuItem;
	}

	/**
	 * This method initializes ListSingleMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getListMenuItem() {
		if (listMenuItem == null) {
			listMenuItem = new JMenuItem();
			listMenuItem.setText("Listing Options");
			listMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L,
					Event.CTRL_MASK, true));
			listMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					if (isLoggedOn) {
						if (createPanel != null) {
							jContentPane.remove(createPanel);
						}
						if (modifyPanel != null) {
							jContentPane.remove(modifyPanel);
						}
						jContentPane.add(getListPanel(), BorderLayout.NORTH);
						jContentPane.repaint();
						jContentPane.validate();	
						jContentPane.updateUI();
					}
					else {
						JOptionPane.showMessageDialog(getJFrame(),
								"You are not logged on to the database.",
								"Error Message",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			});
		}
		return listMenuItem;
	}

	public JPanel getOutputAreaPanel() {
		outputAreaPanel = new JPanel(new BorderLayout());

		outputAreaPanel.setSize(800, 400);

		outputAreaPanel.add(getOutputPanel(), BorderLayout.SOUTH);

		return outputAreaPanel;
	}

	public JPanel getOutputPanel() {

		outputPanel = new JPanel(new FlowLayout());

		listingOutputTextArea = new JPanel(new BorderLayout());

		outputArea = new JTextArea(10, 80);	

		listingOutputTextArea.add(outputArea, BorderLayout.CENTER);		

		outputPanel.add(listingOutputTextArea);

		return outputPanel;
	}
}
