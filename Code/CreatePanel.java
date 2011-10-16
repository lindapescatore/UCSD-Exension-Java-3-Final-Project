package Java3Assignment3;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreatePanel extends JPanel {

	private static final long serialVersionUID = 1L;

	//BUTTONS
	private JRadioButton jrbuttonTypeCD;
	private JRadioButton jrbuttonTypeDVD;
	private JRadioButton jrbuttonTypeBook;
	private JButton createItemButton;

	//PANELS
	private JPanel mainPanel;
	private JPanel createButtonPanel;
	private JPanel typeOfItemTitlePanel;
	private JPanel typeOfItemButtonsPanel;
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
	private JLabel typeOfItemTitle;
	private JLabel mainTitle;
	private JLabel data1;
	private JLabel data2;
	private JLabel data3;
	private JLabel data4;
	private JLabel data5;
	private JLabel data6;
	private JLabel data7;

	//FIELDS
	private JTextField dataEntry1;
	private JTextField dataEntry2;
	private JTextField dataEntry3;
	private JTextField dataEntry4;
	private JTextField dataEntry5;
	private JTextField dataEntry6;
	private JTextField dataEntry7;

	//BUTTON GROUPS
	ButtonGroup group = new ButtonGroup();  //  @jve:decl-index=0:

	//BOOLEAN VALUES TO FIX THE TYPE CHANGE
	boolean dataEntry1PanelExists = false;
	boolean dataEntry2PanelExists = false;
	boolean dataEntry3PanelExists = false;
	boolean dataEntry4PanelExists = false;
	boolean dataEntry5PanelExists = false;
	boolean dataEntry6PanelExists = false;
	boolean dataEntry7PanelExists = false;

	/* normal strings to create new items
	 *  
	 */

	String dataEntry1JTextField = "null";  //  @jve:decl-index=0:
	String dataEntry2JTextField = "null";  //  @jve:decl-index=0:
	String dataEntry3JTextField = "null";  //  @jve:decl-index=0:
	String dataEntry4JTextField = "null";  //  @jve:decl-index=0:
	String dataEntry5JTextField = "null";  //  @jve:decl-index=0:
	String dataEntry6JTextField = "null";  //  @jve:decl-index=0:
	String dataEntry7JTextField = "null";  //  @jve:decl-index=0:

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


	//COMMON ITEMS FOR THE MODEL
	Type m_type;  //  @jve:decl-index=0:
	InventorySystemModelJDBC m_InventoryModel;

	public void Enum(Type m_type) {
		this.m_type = m_type;
	}

	/**
	 * This is the default constructor
	 */
	public CreatePanel(InventorySystemModelJDBC model) {
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

		this.add(getMainPanel());

		//apply all the panels to the main panel
		mainPanel.add(getTypeOfItemPanel());
		mainPanel.add(getMainTitlePanel());
		mainPanel.add(getDataEntry1Panel());
		mainPanel.add(getDataEntry2Panel());
		mainPanel.add(getDataEntry3Panel());
		mainPanel.add(getDataEntry4Panel());
		mainPanel.add(getDataEntry5Panel());
		mainPanel.add(getCreateItemButtonPanel());
	}

	public JPanel getMainPanel() {
		mainPanel = new JPanel(new GridLayout(10, 0));
		mainPanel.setSize(800, 600);

		return mainPanel;
	}

	public JPanel getTypeOfItemPanel() {
		//select type radio button
		typeOfItemPanel = new JPanel( new BorderLayout() );
		typeOfItemTitlePanel = new JPanel( new FlowLayout() );
		typeOfItemButtonsPanel = new JPanel( new FlowLayout() );

		typeOfItemTitle = new JLabel("Please select the type of item you would like to add:");

		//cd jrbutton
		jrbuttonTypeCD = new JRadioButton("CD", true);   
		jrbuttonTypeCD.addActionListener(
				new ActionListener()
				{
					public void actionPerformed( ActionEvent event ) {
						m_type = Type.cd;
						outputUsed = cdOutput;
						
						rebuildCreatePanels();
					}    
				}
		);

		//dvd jrbutton
		jrbuttonTypeDVD = new JRadioButton("DVD", false);   
		jrbuttonTypeDVD.addActionListener(
				new ActionListener()
				{
					public void actionPerformed( ActionEvent event ) {
						m_type = Type.dvd;
						outputUsed = dvdOutput;
						
						rebuildCreatePanels();
					}    
				}
		);

		//book jrbutton
		jrbuttonTypeBook = new JRadioButton("Book", false);   
		jrbuttonTypeBook.addActionListener(
				new ActionListener()
				{
					public void actionPerformed( ActionEvent event ) {
						m_type = Type.book;
						outputUsed = bookOutput;
						
						rebuildCreatePanels();
					}    
				}
		);

		//Group the radio buttons
		group.add(jrbuttonTypeCD);
		group.add(jrbuttonTypeDVD);
		group.add(jrbuttonTypeBook);

		//Put the radio buttons in a row in a panel.
		typeOfItemTitlePanel.add(typeOfItemTitle);
		typeOfItemButtonsPanel.add(jrbuttonTypeCD);
		typeOfItemButtonsPanel.add(jrbuttonTypeDVD);
		typeOfItemButtonsPanel.add(jrbuttonTypeBook);

		typeOfItemPanel.add(typeOfItemTitlePanel, BorderLayout.NORTH);
		typeOfItemPanel.add(typeOfItemButtonsPanel, BorderLayout.SOUTH);

		return typeOfItemPanel;
	}

	public JPanel getMainTitlePanel() {
		//main title
		mainTitlePanel = new JPanel( new FlowLayout() );

		mainTitle = new JLabel("Please enter the information for the new item below:");
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
		dataEntry1 = new JTextField("", 30);
		dataEntry1.addActionListener(
				new ActionListener()
				{
					public void actionPerformed( ActionEvent event )
					{
						dataEntry1JTextField = event.getActionCommand();
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
		dataEntry2 = new JTextField("", 30);
		dataEntry2.addActionListener(
				new ActionListener()
				{
					public void actionPerformed( ActionEvent event )
					{
						dataEntry2JTextField = event.getActionCommand();
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
		dataEntry3 = new JTextField("", 30);
		dataEntry3.addActionListener(
				new ActionListener()
				{
					public void actionPerformed( ActionEvent event )
					{
						dataEntry3JTextField = event.getActionCommand();
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
		dataEntry4 = new JTextField("", 30);
		dataEntry4.addActionListener(
				new ActionListener()
				{
					public void actionPerformed( ActionEvent event )
					{
						dataEntry4JTextField = event.getActionCommand();
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
		dataEntry5 = new JTextField("", 30);
		dataEntry5.addActionListener(
				new ActionListener()
				{
					public void actionPerformed( ActionEvent event )
					{
						dataEntry5JTextField = event.getActionCommand();
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
		dataEntry6 = new JTextField("", 30);
		dataEntry6.addActionListener(
				new ActionListener()
				{
					public void actionPerformed( ActionEvent event )
					{
						dataEntry6JTextField = event.getActionCommand();
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
		dataEntry7 = new JTextField("", 30);
		dataEntry7.addActionListener(
				new ActionListener()
				{
					public void actionPerformed( ActionEvent event )
					{
						dataEntry7JTextField = event.getActionCommand();
					}
				}
		); 

		dataEntry7Panel.add(data7);
		dataEntry7Panel.add(dataEntry7);
		
		dataEntry7PanelExists = true;

		return dataEntry7Panel;
	}

	public JPanel getCreateItemButtonPanel() {

		createItemButton = new JButton( "Create Item" );   
		createItemButton.addActionListener(
				new ActionListener()
				{
					public void actionPerformed( ActionEvent event )
					{
						if (m_type == Type.cd) {

							album = dataEntry1JTextField;
							artist = dataEntry2JTextField;
							year = dataEntry3JTextField;
							label = dataEntry4JTextField;
							price = dataEntry5JTextField;

							m_InventoryModel.createItem(m_type, title, price, artist, album, year, label, studio, director, rating, actors, ISBN, author, publisher, publishingYear, publishingCity);
						}
						else if (m_type == Type.dvd) {

							title = dataEntry1JTextField;
							studio = dataEntry2JTextField;
							director = dataEntry3JTextField;
							rating = dataEntry4JTextField;
							actors = dataEntry5JTextField;
							price = dataEntry6JTextField;

							m_InventoryModel.createItem( m_type, title, price, artist, album, year, label, studio, director, rating, actors, ISBN, author, publisher, publishingYear, publishingCity);
						}
						else if (m_type == Type.book) {

							title = dataEntry1JTextField;
							ISBN = dataEntry2JTextField;
							author = dataEntry3JTextField;
							publisher = dataEntry4JTextField;
							publishingYear = dataEntry5JTextField;
							publishingCity = dataEntry6JTextField;
							price = dataEntry7JTextField;

							m_InventoryModel.createItem(m_type, title, price, artist, album, year, label, studio, director, rating, actors, ISBN, author, publisher, publishingYear, publishingCity);
						} 
					}
				}
		);

		createButtonPanel = new JPanel( new FlowLayout() );
		createButtonPanel.add(createItemButton);

		return createButtonPanel;
	}
	
	public void rebuildCreatePanels() {
		//remove panels
		mainPanel.remove(mainTitlePanel);

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

		mainPanel.remove(createButtonPanel);

		if (m_type == Type.cd) {
			mainPanel.add(getMainTitlePanel());
			mainPanel.add(getDataEntry1Panel());
			mainPanel.add(getDataEntry2Panel());
			mainPanel.add(getDataEntry3Panel());
			mainPanel.add(getDataEntry4Panel());
			mainPanel.add(getDataEntry5Panel());
			mainPanel.add(getCreateItemButtonPanel());
			validate();
			repaint();
		}
		else if (m_type == Type.dvd) {
			mainPanel.add(getMainTitlePanel());
			mainPanel.add(getDataEntry1Panel());
			mainPanel.add(getDataEntry2Panel());
			mainPanel.add(getDataEntry3Panel());
			mainPanel.add(getDataEntry4Panel());
			mainPanel.add(getDataEntry5Panel());
			mainPanel.add(getDataEntry6Panel());				
			mainPanel.add(getCreateItemButtonPanel());
			validate();
			repaint();
		}
		else if (m_type == Type.book) {
			mainPanel.add(getMainTitlePanel());
			mainPanel.add(getDataEntry1Panel());
			mainPanel.add(getDataEntry2Panel());
			mainPanel.add(getDataEntry3Panel());
			mainPanel.add(getDataEntry4Panel());
			mainPanel.add(getDataEntry5Panel());
			mainPanel.add(getDataEntry6Panel());
			mainPanel.add(getDataEntry7Panel());
			mainPanel.add(getCreateItemButtonPanel());
			validate();
			repaint();	
		}
	}
}
