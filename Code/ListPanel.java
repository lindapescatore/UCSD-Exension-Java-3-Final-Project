package Java3Assignment3;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ListPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private final static String newline = "\n";  //  @jve:decl-index=0:

	//PANELS
	private JPanel mainPanel;
	private JPanel selectionPanel;
	private JPanel outputAreaPanel;
	private JPanel listTypePanel;
	private JPanel listByButtonPanel;
	private JPanel additionalInfoPanel;
	private JPanel outputPanel;
	private JPanel listingOutputTextArea;
	private JPanel listByTypeSelectionPanel;


	//BUTTONS
	private JButton listBySKUButton;
	private JButton listByTypeButton;
	private JButton listEntireInventoryButton;
	private JButton copySKUJbutton;
	private JButton pasteSKUJbutton;

	private JButton listByCD;
	private JButton listByDVD;
	private JButton listByBook;

	//LABELS
	private JLabel skuListInfoTitle;

	//TEXT FIELDS
	JTextField skuToListJTextField;

	//TEXT AREAS
	protected JTextArea outputArea;

	//NON SWING VARIABLES
	boolean checkForCorrectSKU = false;
	boolean pastePressed = false;

	String skuToListFromJTextField = null;
	String skuFromOutputArea = null;

	TypeOfList m_typeOfList;  //  @jve:decl-index=0:

	public void Enum(TypeOfList m_typeOfList) {
		this.m_typeOfList = m_typeOfList;
	}

	InventorySystemModelJDBC m_InventoryModel;

	/**
	 * This is the default constructor
	 */
	public ListPanel(InventorySystemModelJDBC model) {
		super();
		m_InventoryModel = model;

		initialize();
	}

	/**
	 * This method initializes the list panel
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(800, 600);

		m_typeOfList = TypeOfList.sku;

		this.add(getMainPanel());

		mainPanel.add(getSelectionPanel(), BorderLayout.NORTH);
		mainPanel.add(getOutputAreaPanel(), BorderLayout.SOUTH);
	}


	public JPanel getMainPanel() {
		mainPanel = new JPanel(new BorderLayout());
		mainPanel.setSize(800, 600);

		return mainPanel;
	}

	public JPanel getSelectionPanel() {
		selectionPanel = new JPanel(new BorderLayout());

		selectionPanel.setSize(800, 200);

		selectionPanel.add(getListTypePanel(), BorderLayout.NORTH);

		return selectionPanel;
	}

	public JPanel getOutputAreaPanel() {
		outputAreaPanel = new JPanel(new BorderLayout());

		outputAreaPanel.setSize(800, 400);

		outputAreaPanel.add(getOutputPanel(), BorderLayout.SOUTH);
		
		JScrollPane scrollPane = new JScrollPane(outputArea);

		scrollPane.setVerticalScrollBarPolicy(
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		outputAreaPanel.add(scrollPane, BorderLayout.EAST);

		return outputAreaPanel;
	}

	public JPanel getListTypePanel() {
		listTypePanel = new JPanel( new BorderLayout() );

		listByButtonPanel = new JPanel(new FlowLayout());

		listBySKUButton = new JButton( "List By SKU" );   
		listBySKUButton.addActionListener(
				new ActionListener()
				{
					public void actionPerformed( ActionEvent event )
					{
						outputArea.setText("");

						m_typeOfList = TypeOfList.sku;

						selectionPanel.removeAll();
						selectionPanel.add(getListTypePanel(), BorderLayout.NORTH);
						selectionPanel.add(getAdditionalInfoPanel(), BorderLayout.SOUTH);

						selectionPanel.revalidate();
						selectionPanel.repaint();
					}
				}
		);

		listByTypeButton = new JButton( "List By Type" );   
		listByTypeButton.addActionListener(
				new ActionListener()
				{
					public void actionPerformed( ActionEvent event )
					{	
						outputArea.setText("");

						m_typeOfList = TypeOfList.type;

						selectionPanel.removeAll();
						selectionPanel.add(getListTypePanel(), BorderLayout.NORTH);
						selectionPanel.add(getAdditionalInfoPanel(), BorderLayout.SOUTH);

						selectionPanel.revalidate();
						selectionPanel.repaint();
					}
				}
		);

		listEntireInventoryButton = new JButton( "List Entire Inventory" );   
		listEntireInventoryButton.addActionListener(
				new ActionListener()
				{
					public void actionPerformed( ActionEvent event )
					{
						outputArea.setText("");

						m_typeOfList = TypeOfList.all;

						selectionPanel.removeAll();
						selectionPanel.add(getListTypePanel(), BorderLayout.NORTH);
						selectionPanel.add(getAdditionalInfoPanel(), BorderLayout.SOUTH);

						selectionPanel.revalidate();
						selectionPanel.repaint();

						String[] listAsStringArray;

						listAsStringArray = m_InventoryModel.listInventoryFromDisc();

						outputArea.setText("");

						for (int i = 0; i < listAsStringArray.length; i++) {

							outputArea.append(listAsStringArray[i] + newline);
						}
					}
				}
		);

		listByButtonPanel.add(listBySKUButton);
		listByButtonPanel.add(listByTypeButton);
		listByButtonPanel.add(listEntireInventoryButton);

		listTypePanel.add(listByButtonPanel, BorderLayout.NORTH);

		return listTypePanel;
	}

	public JPanel getAdditionalInfoPanel() {

		additionalInfoPanel = new JPanel( new BorderLayout() );

		skuListInfoTitle = new JLabel("Please enter the SKU you would like to List:");

		if (pastePressed) {
			skuToListJTextField = new JTextField(skuFromOutputArea, 20);

			//reset paste
			pastePressed = false;
		}
		else if (!pastePressed) {
			skuToListJTextField = new JTextField("", 20);
		}
		skuToListJTextField.addActionListener(
				new ActionListener()
				{
					public void actionPerformed( ActionEvent event )
					{
						try {
							skuToListFromJTextField = event.getActionCommand();
							if (!m_InventoryModel.doesItemExist(skuToListFromJTextField)) {
								throw new Exception();
							}
							else {
								checkForCorrectSKU = true;
							}
						}
						catch (Exception exception) {
							JOptionPane.showMessageDialog( ListPanel.this, "That SKU does not exist in the inventory. Use the lookup features to find the SKU you need.", "Invalid Input", JOptionPane.ERROR_MESSAGE );
						}

						if (checkForCorrectSKU) {
							outputArea.setText("");

							String infoFromPropsFile = m_InventoryModel.retrieveItem(skuToListFromJTextField);

							String[] fromPropsFileIntoArray = new String[7];

							fromPropsFileIntoArray = infoFromPropsFile.split("\t");

							String[] cdOutput = { "SKU:", "Album:", "Artist:", "Year:", "Label:", "Price:", "null", "null" };

							String[] dvdOutput = { "SKU:", "Title:", "Studio:", "Director:", "Rating:", "Actors:", "Price:", "null"  };

							String[] bookOutput = { "SKU:", "Title:", "ISBN:", "Author:", "Publisher:", "Publishing Year:", "Publishing City:", "Price:" };

							String[] outputUsed = new String[8];

							if (skuToListFromJTextField.charAt(0) == 'C') {
								outputUsed = cdOutput;
							}
							else if (skuToListFromJTextField.charAt(0) == 'D') {
								outputUsed = dvdOutput;
							}
							else if (skuToListFromJTextField.charAt(0) == 'B') {
								outputUsed = bookOutput;
							}

							outputArea.append("Item Requested: " + newline + newline);

							for (int i = 0; i < fromPropsFileIntoArray.length; i++) {
								outputArea.append(outputUsed[i] +  "\t\t\t\t" + fromPropsFileIntoArray[i] + newline);
							}

							outputArea.append("");
						}								
					}
				}
		); 

		listByTypeSelectionPanel = new JPanel(new GridBagLayout());

		GridBagConstraints c = new GridBagConstraints();

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 2;
		c.gridy = 0;

		listByCD = new JButton( "CD's" );   
		listByCD.addActionListener(
				new ActionListener()
				{
					public void actionPerformed( ActionEvent event )
					{
						String[] listAsStringArray;

						listAsStringArray = m_InventoryModel.listInventoryFromDiscByType("C");

						outputArea.setText("");

						for (int i = 0; i < listAsStringArray.length; i++) {
							outputArea.append(listAsStringArray[i] + newline);
						}
					}
				}
		);

		listByDVD = new JButton( "DVD's" );   
		listByDVD.addActionListener(
				new ActionListener()
				{
					public void actionPerformed( ActionEvent event )
					{
						String[] listAsStringArray;

						listAsStringArray = m_InventoryModel.listInventoryFromDiscByType("D");

						outputArea.setText("");

						for (int i = 0; i < listAsStringArray.length; i++) {
							outputArea.append(listAsStringArray[i] + newline);
						}
					}
				}
		);

		listByBook = new JButton( "Book's" );   
		listByBook.addActionListener(
				new ActionListener()
				{
					public void actionPerformed( ActionEvent event )
					{
						String[] listAsStringArray;

						listAsStringArray = m_InventoryModel.listInventoryFromDiscByType("B");

						outputArea.setText("");

						for (int i = 0; i < listAsStringArray.length; i++) {
							outputArea.append(listAsStringArray[i] + newline);
						}
					}
				}
		);


		if (m_typeOfList == TypeOfList.sku) {
			listByTypeSelectionPanel.removeAll();

			listByTypeSelectionPanel.add(skuListInfoTitle, c);
			c.gridx = 6;
			listByTypeSelectionPanel.add(skuToListJTextField, c);
			c.gridx = 8;
			listByTypeSelectionPanel.add(getPasteButton());

			listByTypeSelectionPanel.revalidate();
			listByTypeSelectionPanel.repaint();
		}
		else if (m_typeOfList == TypeOfList.type) {
			listByTypeSelectionPanel.removeAll();

			listByTypeSelectionPanel.add(listByCD, c);
			c.gridx = 4;
			listByTypeSelectionPanel.add(listByDVD, c);
			c.gridx = 6;
			listByTypeSelectionPanel.add(listByBook, c);

			listByTypeSelectionPanel.revalidate();
			listByTypeSelectionPanel.repaint();
		}

		additionalInfoPanel.add(listByTypeSelectionPanel);

		return additionalInfoPanel;
	}

	public JPanel getOutputPanel() {

		outputPanel = new JPanel(new FlowLayout());

		listingOutputTextArea = new JPanel(new BorderLayout());

		outputArea = new JTextArea(30, 80);

		outputArea.setEditable(false);

		listingOutputTextArea.add(outputArea, BorderLayout.CENTER);	
		listingOutputTextArea.add(getCopyButton(), BorderLayout.EAST);
		outputPanel.add(listingOutputTextArea);

		return outputPanel;
	}

	public JButton getCopyButton() {

		copySKUJbutton = new JButton( "Copy SKU" );
		copySKUJbutton.addActionListener(
				new ActionListener() {
					public void actionPerformed( ActionEvent e )
					{					
						skuFromOutputArea = outputArea.getSelectedText();
					}
				}
		);

		return copySKUJbutton;	
	}


	public JButton getPasteButton() {

		pasteSKUJbutton = new JButton( "Paste SKU" );
		pasteSKUJbutton.addActionListener(
				new ActionListener() {
					public void actionPerformed( ActionEvent e )
					{
						pastePressed = true;

						selectionPanel.removeAll();
						selectionPanel.add(getListTypePanel(), BorderLayout.NORTH);
						selectionPanel.add(getAdditionalInfoPanel(), BorderLayout.SOUTH);

						selectionPanel.revalidate();
						selectionPanel.repaint();
					}
				}
		);

		return pasteSKUJbutton;	
	}

}
