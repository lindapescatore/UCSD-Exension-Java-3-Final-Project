package Java3Assignment3;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ModifyPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	//BUTTONS
	private JButton modifyItemButton;
	private JButton deleteItemButton;

	//PANELS
	private JPanel mainPanel;
	private JPanel currentSKUPanel;
	private JPanel modifyButtonPanel;
	private JPanel typeOfItemPanel;
	private JPanel mainTitlePanel;
	private JPanel dataEntry1Panel;
	private JPanel dataEntry2Panel;
	private JPanel dataEntry3Panel;
	private JPanel dataEntry4Panel;
	private JPanel dataEntry5Panel;
	private JPanel dataEntry6Panel;
	private JPanel dataEntry7Panel;

	//TITLES
	private JLabel skuToModifyInfoTitle;
	private JLabel mainTitle;
	private JLabel currentSKU;
	private JLabel data1;
	private JLabel data2;
	private JLabel data3;
	private JLabel data4;
	private JLabel data5;
	private JLabel data6;
	private JLabel data7;

	//FIELDS
	private JTextField skuToModifyTextField;
	private JTextField dataEntry1;
	private JTextField dataEntry2;
	private JTextField dataEntry3;
	private JTextField dataEntry4;
	private JTextField dataEntry5;
	private JTextField dataEntry6;
	private JTextField dataEntry7;

	//BOOLEAN VALUES TO FIX THE TYPE CHANGE
	boolean dataEntry1PanelExists = false;
	boolean dataEntry2PanelExists = false;
	boolean dataEntry3PanelExists = false;
	boolean dataEntry4PanelExists = false;
	boolean dataEntry5PanelExists = false;
	boolean dataEntry6PanelExists = false;
	boolean dataEntry7PanelExists = false;

	//static strings
	static String cdString = "CD";
	static String dvdString = "DVD";
	static String bookString = "Book";

	/* normal strings to create new items
	 *  
	 */

	String skuToModifyFromJTextField = null;
	String infoFromModelForCurrentItem = null;

	//common
	String title = "null";  
	String price = "null";  //  @jve:decl-index=0:
	//cd
	String album = "null";  //  @jve:decl-index=0:
	String artist = "null";  
	String year = "null"; 
	String label = "null";
	//dvd
	String studio = "null"; 
	String director = "null"; 
	String rating = "null";  
	String actors = "null";    
	//book
	String ISBN = "null";
	String author = "null";
	String publisher = "null";
	String publishingYear = "null";
	String publishingCity = "null";  

	//string arrays
	String[] cdOutput = { "Album", "Artist", "Year", "Label", "Price", "null", "null" };

	String[] dvdOutput = { "Title", "Studio", "Director", "Rating", "Actors", "Price", "null"  };

	String[] bookOutput = { "Title", "ISBN", "Author", "Publisher", "Publishing Year", "Publishing City", "Price" };

	String[] outputUsed = new String[7];  

	String[] prefetchDataFromModel = new String[7];

	String[] currentItemArray = new String[8];

	String[] dataEntryFromJTextField = new String[7];

	String[] dataFromModelToJTextFieldInitialize = new String[7];

	//ERROR CHECKING
	boolean checkForCorrectSKU = false;  

	//COMMON ITEMS FOR THE MODEL
	Type m_type;  
	InventorySystemModelJDBC m_InventoryModel;

	public void Enum(Type m_type) {
		this.m_type = m_type;
	}

	/**
	 * This is the default constructor
	 */
	public ModifyPanel(InventorySystemModelJDBC model) {
		super();

		m_InventoryModel = model;

		initialize();	
	}

	/**
	 * This method initializes this Create Panel
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(800, 600);
		outputUsed = cdOutput;
		m_type = Type.cd;

		//first initialize the array for the text fields to no values
		for (int i = 0; i < dataEntryFromJTextField.length; i++) {
			dataEntryFromJTextField[i] = "";
		}

		this.add(getMainPanel());

		//apply all the panels to the main panel
		mainPanel.add(getTypeOfItemPanel());
		mainPanel.add(getMainTitlePanel());
		mainPanel.add(getCurrentSKUPanel());
		mainPanel.add(getDataEntry1Panel());
		mainPanel.add(getDataEntry2Panel());
		mainPanel.add(getDataEntry3Panel());
		mainPanel.add(getDataEntry4Panel());
		mainPanel.add(getDataEntry5Panel());
		mainPanel.add(getModifyItemButtonPanel());
	}

	public JPanel getMainPanel() {
		mainPanel = new JPanel(new GridLayout(11, 0));
		mainPanel.setSize(800, 600);

		return mainPanel;
	}

	public JPanel getCurrentSKUPanel() {
		currentSKUPanel = new JPanel(new BorderLayout());

		if (skuToModifyFromJTextField != null) {
			if (m_InventoryModel.doesItemExist(skuToModifyFromJTextField)) {

				currentSKU = new JLabel("The current SKU selected is: " + skuToModifyFromJTextField);
			}
			else {
				currentSKU = new JLabel("No Valid SKU Selected Yet");
			}

			currentSKUPanel.add(currentSKU);
		}

		return currentSKUPanel;
	}

	private JPanel getTypeOfItemPanel() {
		typeOfItemPanel = new JPanel( new BorderLayout() );

		skuToModifyInfoTitle = new JLabel("Please enter the SKU you would like to modify:");

		skuToModifyTextField = new JTextField("", 10);
		skuToModifyTextField.addActionListener(
				new ActionListener()
				{
					public void actionPerformed( ActionEvent event )
					{
						try {
							skuToModifyFromJTextField = event.getActionCommand();
							if (!m_InventoryModel.doesItemExist(skuToModifyFromJTextField)) {
								throw new Exception();
							}
							else {
								checkForCorrectSKU = true;
							}
						}
						catch (Exception exception) {
							skuToModifyFromJTextField = null;
							JOptionPane.showMessageDialog( ModifyPanel.this, "That SKU does not exist in the inventory. Use the lookup features to find the SKU you need.", "Invalid Input", JOptionPane.ERROR_MESSAGE );
						}

						if (checkForCorrectSKU) {

							infoFromModelForCurrentItem = m_InventoryModel.retrieveItem(skuToModifyFromJTextField);

							prefetchDataFromModel = infoFromModelForCurrentItem.split("\t");

							if (skuToModifyFromJTextField.charAt(0) == 'C') {
								m_type = Type.cd;
								outputUsed = cdOutput;

								for (int i = 0; i <= 5 ; i++) {
									currentItemArray[i] = prefetchDataFromModel[i];
								}

								for (int i = 0; i < 5; i++) {
									dataFromModelToJTextFieldInitialize[i] = currentItemArray[i+1];
								}

								rebuildModifyPanels();

							}
							else if (skuToModifyFromJTextField.charAt(0) == 'D') {
								m_type = Type.dvd;
								outputUsed = dvdOutput;

								for (int i = 0; i <= 6 ; i++) {
									currentItemArray[i] = prefetchDataFromModel[i];
								}

								for (int i = 0; i < 6; i++) {
									dataFromModelToJTextFieldInitialize[i] = currentItemArray[i+1];
								}

								rebuildModifyPanels();

							}
							else if (skuToModifyFromJTextField.charAt(0) == 'B') {
								m_type = Type.book;
								outputUsed = bookOutput;

								for (int i = 0; i <= 7 ; i++) {
									currentItemArray[i] = prefetchDataFromModel[i];
								}

								for (int i = 0; i < 7; i++) {
									dataFromModelToJTextFieldInitialize[i] = currentItemArray[i+1];
								}

								rebuildModifyPanels();
							}

							checkForCorrectSKU = false;
						}
					}
				}
		); 


		typeOfItemPanel.add(skuToModifyInfoTitle, BorderLayout.NORTH);
		typeOfItemPanel.add(skuToModifyTextField, BorderLayout.SOUTH);


		return typeOfItemPanel;
	}

	public JPanel getMainTitlePanel() {
		//main title
		mainTitlePanel = new JPanel( new FlowLayout() );

		mainTitle = new JLabel("Please enter the new information for this item below:");
		mainTitlePanel.add(mainTitle);

		return mainTitlePanel;
	}

	/* Initialize the panels for the data entry points
	 * 
	 */
	public JPanel getDataEntry1Panel() {
		//panel 1
		dataEntry1Panel = new JPanel( new FlowLayout() );

		data1 = new JLabel(outputUsed[0] + ":");
		dataEntry1 = new JTextField(dataFromModelToJTextFieldInitialize[0], 30);
		dataEntry1.addActionListener(
				new ActionListener()
				{
					public void actionPerformed( ActionEvent event )
					{
						dataEntryFromJTextField[0] = event.getActionCommand();
					}
				}
		); 


		dataEntry1Panel.add(data1);
		dataEntry1Panel.add(dataEntry1);

		dataEntry1PanelExists = true;

		return dataEntry1Panel;
	}

	public JPanel getDataEntry2Panel() {

		//panel 2
		dataEntry2Panel = new JPanel( new FlowLayout() );

		data2 = new JLabel(outputUsed[1] + ":");
		dataEntry2 = new JTextField(dataFromModelToJTextFieldInitialize[1], 30);
		dataEntry2.addActionListener(
				new ActionListener()
				{
					public void actionPerformed( ActionEvent event )
					{
						dataEntryFromJTextField[1] = event.getActionCommand();
					}
				}
		); 

		dataEntry2Panel.add(data2);
		dataEntry2Panel.add(dataEntry2);

		dataEntry2PanelExists = true;

		return dataEntry2Panel;
	}


	public JPanel getDataEntry3Panel() {
		//panel 3
		dataEntry3Panel = new JPanel( new FlowLayout() );

		data3 = new JLabel(outputUsed[2] + ":");
		dataEntry3 = new JTextField(dataFromModelToJTextFieldInitialize[2], 30);
		dataEntry3.addActionListener(
				new ActionListener()
				{
					public void actionPerformed( ActionEvent event )
					{
						dataEntryFromJTextField[2] = event.getActionCommand();
					}
				}
		); 

		dataEntry3Panel.add(data3);
		dataEntry3Panel.add(dataEntry3);

		dataEntry3PanelExists = true;

		return dataEntry3Panel;
	}

	public JPanel getDataEntry4Panel() {
		//panel 4
		dataEntry4Panel = new JPanel( new FlowLayout() );

		data4 = new JLabel(outputUsed[3] + ":");
		dataEntry4 = new JTextField(dataFromModelToJTextFieldInitialize[3], 30);
		dataEntry4.addActionListener(
				new ActionListener()
				{
					public void actionPerformed( ActionEvent event )
					{
						dataEntryFromJTextField[3] = event.getActionCommand();
					}
				}
		); 

		dataEntry4Panel.add(data4);
		dataEntry4Panel.add(dataEntry4);

		dataEntry4PanelExists = true;

		return dataEntry4Panel;
	}


	public JPanel getDataEntry5Panel() {
		//panel 5
		dataEntry5Panel = new JPanel( new FlowLayout() );

		data5 = new JLabel(outputUsed[4] + ":");
		dataEntry5 = new JTextField(dataFromModelToJTextFieldInitialize[4], 30);
		dataEntry5.addActionListener(
				new ActionListener()
				{
					public void actionPerformed( ActionEvent event )
					{
						dataEntryFromJTextField[4] = event.getActionCommand();
					}
				}
		); 

		dataEntry5Panel.add(data5);
		dataEntry5Panel.add(dataEntry5);

		dataEntry5PanelExists = true;

		return dataEntry5Panel;
	}

	public JPanel getDataEntry6Panel() {
		//panel 6
		dataEntry6Panel = new JPanel( new FlowLayout() );

		data6 = new JLabel(outputUsed[5] + ":");
		dataEntry6 = new JTextField(dataFromModelToJTextFieldInitialize[5], 30);
		dataEntry6.addActionListener(
				new ActionListener()
				{
					public void actionPerformed( ActionEvent event )
					{
						dataEntryFromJTextField[5] = event.getActionCommand();
					}
				}
		); 

		dataEntry6Panel.add(data6);
		dataEntry6Panel.add(dataEntry6);

		dataEntry6PanelExists = true;

		return dataEntry6Panel;
	}

	public JPanel getDataEntry7Panel() {
		//panel 7
		dataEntry7Panel = new JPanel( new FlowLayout() );

		data7 = new JLabel(outputUsed[6] + ":");
		dataEntry7 = new JTextField(dataFromModelToJTextFieldInitialize[6], 30);
		dataEntry7.addActionListener(
				new ActionListener()
				{
					public void actionPerformed( ActionEvent event )
					{
						dataEntryFromJTextField[6] = event.getActionCommand();
					}
				}
		); 

		dataEntry7Panel.add(data7);
		dataEntry7Panel.add(dataEntry7);

		dataEntry7PanelExists = true;

		return dataEntry7Panel;
	}

	public JPanel getModifyItemButtonPanel() {

		modifyItemButton = new JButton( "Modify Item" );   
		modifyItemButton.addActionListener(
				new ActionListener()
				{
					public void actionPerformed( ActionEvent event )
					{
						if (m_type == Type.cd) {

							album = dataEntryFromJTextField[0];
							artist = dataEntryFromJTextField[1];
							year = dataEntryFromJTextField[2];
							label = dataEntryFromJTextField[3];
							price = dataEntryFromJTextField[4];

							m_InventoryModel.modifyItem(skuToModifyFromJTextField, m_type, title, price, artist, album, year, label, studio, director, rating, actors, ISBN, author, publisher, publishingYear, publishingCity);

							for (int i = 0; i < currentItemArray.length; i++) {
								currentItemArray[i] = "";
							}

							for (int i = 0; i < dataEntryFromJTextField.length; i++) {
								dataEntryFromJTextField[i] = "";
							}

							for (int i = 0; i < dataFromModelToJTextFieldInitialize.length; i++) {
								dataFromModelToJTextFieldInitialize[i] = "";
							}

							skuToModifyFromJTextField = null;

							m_type = Type.cd;

							rebuildModifyPanels();

						}
						else if (m_type == Type.dvd) {

							title = dataEntryFromJTextField[0];
							studio = dataEntryFromJTextField[1];
							director = dataEntryFromJTextField[2];
							rating = dataEntryFromJTextField[3];
							actors = dataEntryFromJTextField[4];
							price = dataEntryFromJTextField[5];

							m_InventoryModel.modifyItem(skuToModifyFromJTextField, m_type, title, price, artist, album, year, label, studio, director, rating, actors, ISBN, author, publisher, publishingYear, publishingCity);

							for (int i = 0; i < currentItemArray.length; i++) {
								currentItemArray[i] = "";
							}

							for (int i = 0; i < dataEntryFromJTextField.length; i++) {
								dataEntryFromJTextField[i] = "";
							}

							for (int i = 0; i < dataFromModelToJTextFieldInitialize.length; i++) {
								dataFromModelToJTextFieldInitialize[i] = "";
							}

							skuToModifyFromJTextField = null;

							m_type = Type.cd;

							rebuildModifyPanels();
						}
						else if (m_type == Type.book) {

							title = dataEntryFromJTextField[0];
							ISBN = dataEntryFromJTextField[1];
							author = dataEntryFromJTextField[2];
							publisher = dataEntryFromJTextField[3];
							publishingYear = dataEntryFromJTextField[4];
							publishingCity = dataEntryFromJTextField[5];
							price = dataEntryFromJTextField[6];

							m_InventoryModel.modifyItem(skuToModifyFromJTextField, m_type, title, price, artist, album, year, label, studio, director, rating, actors, ISBN, author, publisher, publishingYear, publishingCity);

							for (int i = 0; i < currentItemArray.length; i++) {
								currentItemArray[i] = "";
							}

							for (int i = 0; i < dataEntryFromJTextField.length; i++) {
								dataEntryFromJTextField[i] = "";
							}

							for (int i = 0; i < dataFromModelToJTextFieldInitialize.length; i++) {
								dataFromModelToJTextFieldInitialize[i] = "";
							}


							skuToModifyFromJTextField = null;

							m_type = Type.cd;

							rebuildModifyPanels();
						} 
					}
				}
		);

		deleteItemButton = new JButton( "Delete Item" );   
		deleteItemButton.addActionListener(
				new ActionListener()
				{
					public void actionPerformed( ActionEvent event )
					{
						m_InventoryModel.deleteItem(skuToModifyFromJTextField);

						for (int i = 0; i < currentItemArray.length; i++) {
							currentItemArray[i] = "";
						}

						for (int i = 0; i < dataEntryFromJTextField.length; i++) {
							dataEntryFromJTextField[i] = "";
						}

						for (int i = 0; i < dataFromModelToJTextFieldInitialize.length; i++) {
							dataFromModelToJTextFieldInitialize[i] = "";
						}

						skuToModifyFromJTextField = null;

						m_type = Type.cd;

						rebuildModifyPanels();
					}
				}
		);

		modifyButtonPanel = new JPanel( new FlowLayout() );

		modifyButtonPanel.add(modifyItemButton, BorderLayout.WEST);
		modifyButtonPanel.add(deleteItemButton, BorderLayout.EAST);

		return modifyButtonPanel;

	}

	public void rebuildModifyPanels() {
		//remove panels
		mainPanel.remove(typeOfItemPanel);
		mainPanel.remove(mainTitlePanel);
		mainPanel.remove(currentSKUPanel);

		if (dataEntry1PanelExists) {
			mainPanel.remove(dataEntry1Panel);
			dataEntry1PanelExists = false;
		}
		if (dataEntry2PanelExists) {
			mainPanel.remove(dataEntry2Panel);
			dataEntry2PanelExists = false;
		}
		if (dataEntry3PanelExists) {
			mainPanel.remove(dataEntry3Panel);
			dataEntry3PanelExists = false;
		}
		if (dataEntry4PanelExists) {
			mainPanel.remove(dataEntry4Panel);
			dataEntry4PanelExists = false;
		}
		if (dataEntry5PanelExists) {
			mainPanel.remove(dataEntry5Panel);
			dataEntry5PanelExists = false;
		}
		if (dataEntry6PanelExists) { 
			mainPanel.remove(dataEntry6Panel);
			dataEntry6PanelExists = false;
		}
		if (dataEntry7PanelExists) {
			mainPanel.remove(dataEntry7Panel);
			dataEntry7PanelExists = false;
		}

		mainPanel.remove(modifyButtonPanel);

		if (m_type == Type.cd) {
			mainPanel.add(getTypeOfItemPanel());
			mainPanel.add(getMainTitlePanel());
			mainPanel.add(getCurrentSKUPanel());
			mainPanel.add(getDataEntry1Panel());
			mainPanel.add(getDataEntry2Panel());
			mainPanel.add(getDataEntry3Panel());
			mainPanel.add(getDataEntry4Panel());
			mainPanel.add(getDataEntry5Panel());
			mainPanel.add(getModifyItemButtonPanel());
			validate();
			repaint();
		}
		else if (m_type == Type.dvd) {
			mainPanel.add(getTypeOfItemPanel());
			mainPanel.add(getMainTitlePanel());
			mainPanel.add(getCurrentSKUPanel());
			mainPanel.add(getDataEntry1Panel());
			mainPanel.add(getDataEntry2Panel());
			mainPanel.add(getDataEntry3Panel());
			mainPanel.add(getDataEntry4Panel());
			mainPanel.add(getDataEntry5Panel());
			mainPanel.add(getDataEntry6Panel());				
			mainPanel.add(getModifyItemButtonPanel());
			validate();
			repaint();
		}
		else if (m_type == Type.book) {
			mainPanel.add(getTypeOfItemPanel());
			mainPanel.add(getMainTitlePanel());
			mainPanel.add(getCurrentSKUPanel());
			mainPanel.add(getDataEntry1Panel());
			mainPanel.add(getDataEntry2Panel());
			mainPanel.add(getDataEntry3Panel());
			mainPanel.add(getDataEntry4Panel());
			mainPanel.add(getDataEntry5Panel());
			mainPanel.add(getDataEntry6Panel());
			mainPanel.add(getDataEntry7Panel());
			mainPanel.add(getModifyItemButtonPanel());
			validate();
			repaint();	
		}
	}
}
