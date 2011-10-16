package Java3Assignment3;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Observable;
import java.util.ArrayList;

public class InventorySystemModelJDBC extends Observable {

	// JDBC driver name and database URL                              
	static final String DATABASE_URL = "jdbc:mysql://localhost/inventorySystemModel";

	String username = null;
	String password = null;
	private Connection connection;

	private boolean connectedToDatabase = false;

	SKUGenerator skuGenerator;

	public InventorySystemModelJDBC(SKUGenerator SKUGenerator) {
		skuGenerator = SKUGenerator;
		skuGenerator.initialize();	
		skuGenerator.synchronizeSKUSystemWithPropsFileAndLoad();
	}

	String lastSKUModified;
	String m_currentSKU;

	Type m_type; 
	TypeOfChange m_change;

	//SETUP AND TYPE VARIABLES
	public void Enum(Type m_type) {
		this.m_type = m_type;
	}

	public void Enum(TypeOfChange m_change) {
		this.m_change = m_change;
	}

	public String getLastSKUModified() {
		return lastSKUModified;
	}

	public boolean setServerCredentials(String username, String password) {

		this.username = username;
		this.password = password;

		return true;
	}

	//JDBC CRUD
	public void createItem(Type type, String title, String price, String artist, String album, String year, String label, String studio, String director,
			String rating, String actors, String ISBN, String author, String publisher, String publishingYear, String publishingCity) {
		// connect to database books and query database

		this.m_type = type;

		if (m_type == Type.cd) {

			m_currentSKU = skuGenerator.generateSKUCD();

			CD cd = new CD(m_currentSKU, album, artist, year, label, price);

			String fromObject = cd.itemToString();

			String[] parsedItem = new String[6];

			parsedItem = fromObject.split("\t");

			if (connection == null) {
				connectToDatabase();
			}

			try {
				// Prepare a statement to insert a record
				String sql = "INSERT INTO cd (SKU, album, artist, year, label, price) VALUES ( ?, ?, ?, ?, ?, ? )";
				PreparedStatement pstmt = connection.prepareStatement(sql);

				//set the values
				pstmt.setNString(1,parsedItem[0]);
				pstmt.setNString(2,parsedItem[1]);
				pstmt.setNString(3,parsedItem[2]);
				pstmt.setNString(4,parsedItem[3]);
				pstmt.setNString(5,parsedItem[4]);
				pstmt.setNString(6,parsedItem[5]);

				// Insert the row
				pstmt.executeUpdate();
			}
			catch (SQLException sqlException) {
				sqlException.printStackTrace();
			}
			finally {
				disconnectFromDatabase();
			}
		}

		if (m_type == Type.dvd) {

			m_currentSKU = skuGenerator.generateSKUDVD();

			DVD dvd = new DVD(m_currentSKU, title, studio, director, rating, actors, price);

			String fromObject = dvd.itemToString();

			String[] parsedItem = new String[7];

			parsedItem = fromObject.split("\t");

			if (connection == null) {
				connectToDatabase();
			}

			try {
				// Prepare a statement to insert a record
				String sql = "INSERT INTO dvd (SKU, title, studio, director, rating, actors, price) VALUES ( ?, ?, ?, ?, ?, ?, ? )";
				PreparedStatement pstmt = connection.prepareStatement(sql);

				//set the values
				pstmt.setNString(1,parsedItem[0]);
				pstmt.setNString(2,parsedItem[1]);
				pstmt.setNString(3,parsedItem[2]);
				pstmt.setNString(4,parsedItem[3]);
				pstmt.setNString(5,parsedItem[4]);
				pstmt.setNString(6,parsedItem[5]);
				pstmt.setNString(7,parsedItem[6]);

				// Insert the row
				pstmt.executeUpdate();
			}
			catch (SQLException sqlException) {
				sqlException.printStackTrace();
			}
			finally {
				disconnectFromDatabase();
			}
		}

		if (m_type == Type.book) {

			m_currentSKU = skuGenerator.generateSKUBook();

			Book book = new Book(m_currentSKU, ISBN, title, author, publisher, publishingYear, publishingCity, price);

			String fromObject = book.itemToString();

			String[] parsedItem = new String[7];

			parsedItem = fromObject.split("\t");	

			if (connection == null) {
				connectToDatabase();
			}

			try {
				// Prepare a statement to insert a record
				String sql = "INSERT INTO book (SKU, ISBN, title, author, publisher, publishing_year, publishing_city, price) VALUES ( ?, ?, ?, ?, ?, ?, ?, ? )";
				PreparedStatement pstmt = connection.prepareStatement(sql);

				//set the values
				pstmt.setNString(1,parsedItem[0]);
				pstmt.setNString(2,parsedItem[1]);
				pstmt.setNString(3,parsedItem[2]);
				pstmt.setNString(4,parsedItem[3]);
				pstmt.setNString(5,parsedItem[4]);
				pstmt.setNString(6,parsedItem[5]);
				pstmt.setNString(7,parsedItem[6]);
				pstmt.setNString(8,parsedItem[7]);


				// Insert the row
				pstmt.executeUpdate();
			}
			catch (SQLException sqlException) {
				sqlException.printStackTrace();
			}
			finally {
				disconnectFromDatabase();
			}
		}

		skuGenerator.setSKUPropsFileAndSave();
		lastSKUModified = m_currentSKU;
		m_currentSKU = "";
		setChanged();
		m_change = TypeOfChange.modifyChange;
		notifyObservers(m_change);  
	}

	public void modifyItem(String SKU, Type type, String title, String price, String artist, String album, String year, String label, String studio, String director,
			String rating, String actors, String ISBN, String author, String publisher, String publishingYear, String publishingCity) {

		this.m_type = type;
		m_currentSKU = SKU;

		if (m_type == Type.cd) {

			CD cd = new CD(m_currentSKU, album, artist, year, label, price);

			String fromObject = cd.itemToString();

			String[] parsedItem = new String[6];

			parsedItem = fromObject.split("\t");

			if (connection == null) {
				connectToDatabase();
			}

			try {
				// Prepare a statement to insert a record
				String sql = "UPDATE cd SET SKU = ?, album = ?, artist = ?,  year = ?, label = ?, price = ? WHERE sku = ?";
				PreparedStatement pstmt = connection.prepareStatement(sql);

				//set the values
				pstmt.setNString(1,parsedItem[0]);
				pstmt.setNString(2,parsedItem[1]);
				pstmt.setNString(3,parsedItem[2]);
				pstmt.setNString(4,parsedItem[3]);
				pstmt.setNString(5,parsedItem[4]);
				pstmt.setNString(6,parsedItem[5]);
				pstmt.setNString(7,parsedItem[0]);
				
				// Update the row
				pstmt.executeUpdate();
			}
			catch (SQLException sqlException) {
				sqlException.printStackTrace();
			}
			finally {
				disconnectFromDatabase();
			}
		}

		if (m_type == Type.dvd) {

			DVD dvd = new DVD(m_currentSKU, title, studio, director, rating, actors, price);

			String fromObject = dvd.itemToString();

			String[] parsedItem = new String[7];

			parsedItem = fromObject.split("\t");

			if (connection == null) {
				connectToDatabase();
			}

			try {
				// Prepare a statement to insert a record
				String sql = "UPDATE dvd SET SKU = ?, title = ?, studio = ?,  director = ?, rating = ?, actors = ?, price = ? WHERE SKU = ?";
				PreparedStatement pstmt = connection.prepareStatement(sql);

				//set the values
				pstmt.setNString(1,parsedItem[0]);
				pstmt.setNString(2,parsedItem[1]);
				pstmt.setNString(3,parsedItem[2]);
				pstmt.setNString(4,parsedItem[3]);
				pstmt.setNString(5,parsedItem[4]);
				pstmt.setNString(6,parsedItem[5]);
				pstmt.setNString(7,parsedItem[6]);
				pstmt.setNString(8,parsedItem[0]);
				
				// Update the row
				pstmt.executeUpdate();
			}
			catch (SQLException sqlException) {
				sqlException.printStackTrace();
			}
			finally {
				disconnectFromDatabase();
			}
		}

		if (m_type == Type.book) {

			Book book = new Book(m_currentSKU, ISBN, title, author, publisher, publishingYear, publishingCity, price);

			String fromObject = book.itemToString();

			String[] parsedItem = new String[7];

			parsedItem = fromObject.split("\t");	
			
			if (connection == null) {
				connectToDatabase();
			}

			try {
				// Prepare a statement to insert a record
				String sql = "UPDATE book SET SKU = ?, ISBN = ?, title = ?,  author = ?, publisher = ?, publishing_year = ?, publishing_city = ?, price = ? WHERE SKU = ?";
				PreparedStatement pstmt = connection.prepareStatement(sql);

				//set the values
				pstmt.setNString(1,parsedItem[0]);
				pstmt.setNString(2,parsedItem[1]);
				pstmt.setNString(3,parsedItem[2]);
				pstmt.setNString(4,parsedItem[3]);
				pstmt.setNString(5,parsedItem[4]);
				pstmt.setNString(6,parsedItem[5]);
				pstmt.setNString(7,parsedItem[6]);
				pstmt.setNString(8,parsedItem[7]);
				pstmt.setNString(9,parsedItem[0]);
				
				// Update the row
				pstmt.executeUpdate();
			}
			catch (SQLException sqlException) {
				sqlException.printStackTrace();
			}
			finally {
				disconnectFromDatabase();
			}
		} 

		skuGenerator.setSKUPropsFileAndSave();
		lastSKUModified = m_currentSKU;
		m_currentSKU = "";
		setChanged();
		m_change = TypeOfChange.modifyChange;
		notifyObservers(m_change);  
	}

	public void deleteItem(String itemSKUFromView) {
		// connect to database books and query database
		if (!connectedToDatabase) {

			if (connection == null) {
				connectToDatabase();
			}

			String sql = "null";

			try {

				// Prepare a statement to insert a record
				if (itemSKUFromView.charAt(0) == 'C') {
					sql = "DELETE FROM cd WHERE SKU = ?";
				}
				else if (itemSKUFromView.charAt(0) == 'D') {
					sql = "DELETE FROM dvd WHERE SKU = ?";
				}
				else if (itemSKUFromView.charAt(0) == 'B') {
					sql = "DELETE FROM book WHERE SKU = ?";
				}

				PreparedStatement pstmt = connection.prepareStatement(sql);

				//set the values
				pstmt.setNString(1,itemSKUFromView);

				// Insert the row
				pstmt.executeUpdate();


			}
			catch (SQLException sqlException) {
				sqlException.printStackTrace();
			}
			finally {
				disconnectFromDatabase();
			}

			lastSKUModified = itemSKUFromView;
			setChanged();
			m_change = TypeOfChange.delete;
			notifyObservers(m_change);
		}
	}

	public void connectToDatabase() {
		if (connection == null) {
			try {
				connection = DriverManager.getConnection( DATABASE_URL, username, password );	
			}
			catch (SQLException sqlException) {
				sqlException.printStackTrace();
			}

			connectedToDatabase = true;
		}
	}

	public void disconnectFromDatabase() {
		if (connectedToDatabase) {
			try {
				connection.close();
			}
			catch (SQLException sqlException) {
				sqlException.printStackTrace();
			}

			connectedToDatabase = false;
			connection = null;
		}
	}

	//LISTING METHODS
	public String[] listInventoryFromDisc() {

		ArrayList<String> toOutput = new ArrayList<String>(10);
		ResultSet rs = null;
		String sql = null;
		String output = null;

		//common
		String SKU = null;
		String title = null;  
		String price = null;  
		//cd
		String album = null;  
		String artist = null;  
		String year = null; 
		String label = null;
		//dvd
		String studio = null; 
		String director = null; 
		String rating = null;  
		String actors = null;    
		//book
		String ISBN = null;
		String author = null;
		String publisher = null;
		String publishingYear = null;
		String publishingCity = null;

		//FOR CD'S
		if (!connectedToDatabase) {

			if (connection == null) {
				connectToDatabase();
			}

			try {

				sql = "SELECT * FROM cd";

				PreparedStatement pstmt = connection.prepareStatement(sql,ResultSet.CONCUR_READ_ONLY); 

				// execute the query
				rs = pstmt.executeQuery();

				toOutput.add("");
				toOutput.add("SKU\tAlbum\tArtist\tYear\tLabel\tPrice");
				toOutput.add("");
				
				if (rs.next()) {  			
					do {  
						SKU = rs.getString("SKU");
						album = rs.getString("album");
						artist = rs.getString("artist");
						year = rs.getString("year");
						label = rs.getString("label");
						price = rs.getString("price");

						output = String.format("%s\t%s\t%s\t%s\t%s\t%s", SKU, album, artist, year, label, price);	

						toOutput.add(output);

					} while (rs.next());  
				} 
				else {  
					toOutput.add("There are no CD records.");
				} 
			}
			catch (SQLException sqlException) {
				sqlException.printStackTrace();
			}

			finally {

				if (rs != null) {
					try {
						rs.close();
					} catch (SQLException sqlException) {
						// Do nothing with exception.
					}
					rs = null;
				}
				if (connection != null) {
					disconnectFromDatabase();
				}
			}
		}

		//FOR DVD'S
		if (!connectedToDatabase) {

			if (connection == null) {
				connectToDatabase();
			}

			try {

				sql = "SELECT * FROM dvd";

				PreparedStatement pstmt = connection.prepareStatement(sql,ResultSet.CONCUR_READ_ONLY); 

				// execute the query
				rs = pstmt.executeQuery();

				toOutput.add("");
				toOutput.add("SKU\tTitle\tStudio\tDirector\tRating\tActors\tPrice");
				toOutput.add("");
				
				if (rs.next()) {  			
					do {  
						SKU = rs.getString("SKU");
						title = rs.getString("title");
						studio = rs.getString("studio");
						director = rs.getString("director");
						rating = rs.getString("rating");
						actors = rs.getString("actors");
						price = rs.getString("price");

						output = String.format("%s\t%s\t%s\t%s\t%s\t%s\t%s", SKU, title, studio, director, rating, actors, price);

						toOutput.add(output);

					} while (rs.next());  
				} 
				else {  
					toOutput.add("There are no DVD records.");
				}
			}

			catch (SQLException sqlException) {
				sqlException.printStackTrace();
			}

			finally {

				if (rs != null) {
					try {
						rs.close();
					} catch (SQLException sqlException) {
						// Do nothing with exception.
					}
					rs = null;
				}
				if (connection != null) {
					disconnectFromDatabase();
				}
			}
		}

		//FOR BOOKS
		if (!connectedToDatabase) {

			if (connection == null) {
				connectToDatabase();
			}

			try {


				sql = "SELECT * FROM book";

				PreparedStatement pstmt = connection.prepareStatement(sql,ResultSet.CONCUR_READ_ONLY); 

				// execute the query
				rs = pstmt.executeQuery();

				toOutput.add("");
				toOutput.add("SKU\tISBN\tTitle\tAuthor\tPublisher\tPublishing Year Publishing City Price");
				toOutput.add("");
				
				if (rs.next()) {  			
					do {  
						SKU = rs.getString("SKU");
						ISBN = rs.getString("ISBN");
						title = rs.getString("title");
						author = rs.getString("author");
						publisher = rs.getString("publisher");
						publishingYear = rs.getString("publishing_year");
						publishingCity = rs.getString("publishing_city");
						price = rs.getString("price");

						output = String.format("%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s", SKU, ISBN, title, author, publisher, publishingYear, publishingCity, price);	

						toOutput.add(output);

					} while (rs.next());  
				} 
				else {  
					toOutput.add("There are no Book records.");
				} 
			}

			catch (SQLException sqlException) {
				sqlException.printStackTrace();
			}

			finally {

				if (rs != null) {
					try {
						rs.close();
					} catch (SQLException sqlException) {
						// Do nothing with exception.
					}
					rs = null;
				}
				if (connection != null) {
					disconnectFromDatabase();
				}
			}
		}

		String[] listAsStringArray = new String[toOutput.size()];

		listAsStringArray = toOutput.toArray(listAsStringArray);

		return listAsStringArray;
	}

	public String[] listInventoryFromDiscByType(String SKURangeFromView) {
		ArrayList<String> toOutput = new ArrayList<String>(10);
		ResultSet rs = null;
		String sql = null;
		String output = null;

		//common
		String SKU = null;
		String title = null;  
		String price = null;  
		//cd
		String album = null;  
		String artist = null;  
		String year = null; 
		String label = null;
		//dvd
		String studio = null; 
		String director = null; 
		String rating = null;  
		String actors = null;    
		//book
		String ISBN = null;
		String author = null;
		String publisher = null;
		String publishingYear = null;
		String publishingCity = null;

		if (SKURangeFromView.charAt(0) == 'C') {
			if (!connectedToDatabase) {

				if (connection == null) {
					connectToDatabase();
				}

				try {

					sql = "SELECT * FROM cd";

					PreparedStatement pstmt = connection.prepareStatement(sql,ResultSet.CONCUR_READ_ONLY); 

					// execute the query
					rs = pstmt.executeQuery();

					toOutput.add("");
					toOutput.add("SKU\tAlbum\tArtist\tYear\tLabel\tPrice");
					toOutput.add("");
					
					if (rs.next()) {  			
						do {  
							SKU = rs.getString("SKU");
							album = rs.getString("album");
							artist = rs.getString("artist");
							year = rs.getString("year");
							label = rs.getString("label");
							price = rs.getString("price");

							output = String.format("%s\t%s\t%s\t%s\t%s\t%s", SKU, album, artist, year, label, price);	

							toOutput.add(output);

						} while (rs.next());  
					} 
					else {  
						toOutput.add("There are no CD records.");
					} 
				}
				catch (SQLException sqlException) {
					sqlException.printStackTrace();
				}

				finally {

					if (rs != null) {
						try {
							rs.close();
						} catch (SQLException sqlException) {
							// Do nothing with exception.
						}
						rs = null;
					}
					if (connection != null) {
						disconnectFromDatabase();
					}
				}
			}
		}

		else if (SKURangeFromView.charAt(0) == 'D') {
			if (!connectedToDatabase) {

				if (connection == null) {
					connectToDatabase();
				}

				try {

					sql = "SELECT * FROM dvd";

					PreparedStatement pstmt = connection.prepareStatement(sql,ResultSet.CONCUR_READ_ONLY); 

					// execute the query
					rs = pstmt.executeQuery();

					toOutput.add("");
					toOutput.add("SKU\tTitle\tStudio\tDirector\tRating\tActors\tPrice");
					toOutput.add("");
					
					if (rs.next()) {  			
						do {  
							SKU = rs.getString("SKU");
							title = rs.getString("title");
							studio = rs.getString("studio");
							director = rs.getString("director");
							rating = rs.getString("rating");
							actors = rs.getString("actors");
							price = rs.getString("price");

							output = String.format("%s\t%s\t%s\t%s\t%s\t%s\t%s", SKU, title, studio, director, rating, actors, price);

							toOutput.add(output);

						} while (rs.next());  
					} 
					else {  
						toOutput.add("There are no DVD records.");
					}
				}

				catch (SQLException sqlException) {
					sqlException.printStackTrace();
				}

				finally {

					if (rs != null) {
						try {
							rs.close();
						} catch (SQLException sqlException) {
							// Do nothing with exception.
						}
						rs = null;
					}
					if (connection != null) {
						disconnectFromDatabase();
					}
				}
			}
		}


		else if (SKURangeFromView.charAt(0) == 'B') {
			if (!connectedToDatabase) {

				if (connection == null) {
					connectToDatabase();
				}

				try {


					sql = "SELECT * FROM book";

					PreparedStatement pstmt = connection.prepareStatement(sql,ResultSet.CONCUR_READ_ONLY); 

					// execute the query
					rs = pstmt.executeQuery();

					toOutput.add("");
					toOutput.add("SKU\tISBN\tTitle\tAuthor\tPublisher\tPublishing Year Publishing City Price");
					toOutput.add("");
					
					if (rs.next()) {  			
						do {  
							SKU = rs.getString("SKU");
							ISBN = rs.getString("ISBN");
							title = rs.getString("title");
							author = rs.getString("author");
							publisher = rs.getString("publisher");
							publishingYear = rs.getString("publishing_year");
							publishingCity = rs.getString("publishing_city");
							price = rs.getString("price");

							output = String.format("%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s", SKU, ISBN, title, author, publisher, publishingYear, publishingCity, price);	

							toOutput.add(output);

						} while (rs.next());  
					} 
					else {  
						toOutput.add("There are no Book records.");
					} 
				}

				catch (SQLException sqlException) {
					sqlException.printStackTrace();
				}

				finally {

					if (rs != null) {
						try {
							rs.close();
						} catch (SQLException sqlException) {
							// Do nothing with exception.
						}
						rs = null;
					}
					if (connection != null) {
						disconnectFromDatabase();
					}
				}
			}
		}

		String[] listAsStringArray = new String[toOutput.size()];

		listAsStringArray = toOutput.toArray(listAsStringArray);

		return listAsStringArray;
	}

	//METHODS TO CHECK IF CHANGES COMMITED OR IF ITEMS HAVE BEEN MODIFIED
	public boolean doesItemExist(String itemSKUFromView) {

		boolean doesItemExist = false;

		if (!connectedToDatabase) {

			if (connection == null) {
				connectToDatabase();
			}

			ResultSet rs = null;
			String sql = null;

			try {

				// Prepare a statement to insert a record
				if (itemSKUFromView.charAt(0) == 'C') {
					sql = "SELECT SKU FROM cd WHERE SKU = ?";
				}
				else if (itemSKUFromView.charAt(0) == 'D') {
					sql = "SELECT SKU FROM dvd WHERE SKU = ?";
				}
				else if (itemSKUFromView.charAt(0) == 'B') {
					sql = "SELECT SKU FROM book WHERE SKU = ?";
				}

				PreparedStatement pstmt = connection.prepareStatement(sql,ResultSet.CONCUR_READ_ONLY); 

				//set the values
				pstmt.setNString(1,itemSKUFromView);

				// execute the query
				rs = pstmt.executeQuery();

				if (rs.next()) {  
					doesItemExist = true;
				} 
				else {  
					doesItemExist = false;
				}


			}
			catch (SQLException sqlException) {
				sqlException.printStackTrace();
			}

			finally {

				if (rs != null) {
					try {
						rs.close();
					} catch (SQLException sqlException) {
						// Do nothing with exception.
					}
					rs = null;
				}
				if (connection != null) {
					disconnectFromDatabase();
				}
			}
		}

		if (doesItemExist == false) {
			return false;
		}
		else {
			return true;
		}
	}

	public String retrieveItem(String itemSKUFromView) {

		ResultSet rs = null;
		String output = null;

		//common
		String SKU = null;
		String title = null;  
		String price = null;  
		//cd
		String album = null;  
		String artist = null;  
		String year = null; 
		String label = null;
		//dvd
		String studio = null; 
		String director = null; 
		String rating = null;  
		String actors = null;    
		//book
		String ISBN = null;
		String author = null;
		String publisher = null;
		String publishingYear = null;
		String publishingCity = null;  

		try {	
			if (connection == null) {
				connectToDatabase();
			}

			output = null;

			String sql = "null";

			if (itemSKUFromView.charAt(0) == 'C') {
				sql = ("SELECT * FROM cd WHERE sku = ?");
			}
			else if (itemSKUFromView.charAt(0) == 'D') {
				sql = ("SELECT * FROM dvd WHERE sku = ?");
			}
			else if (itemSKUFromView.charAt(0) == 'B') {
				sql = ("SELECT * FROM book WHERE sku = ?");
			}

			PreparedStatement pstmt = connection.prepareStatement(sql, ResultSet.CONCUR_READ_ONLY);

			//set the values
			pstmt.setNString(1,itemSKUFromView);

			rs = pstmt.executeQuery();

			if (itemSKUFromView.charAt(0) == 'C') {
				while (rs.next()) {
					SKU = rs.getString("SKU");
					album = rs.getString("album");
					artist = rs.getString("artist");
					year = rs.getString("year");
					label = rs.getString("label");
					price = rs.getString("price");

					output = String.format("%s\t%s\t%s\t%s\t%s\t%s", SKU, album, artist, year, label, price);	
				}
			}
			else if (itemSKUFromView.charAt(0) == 'D') {	
				while (rs.next()) {
					SKU = rs.getString("SKU");
					title = rs.getString("title");
					studio = rs.getString("studio");
					director = rs.getString("director");
					rating = rs.getString("rating");
					actors = rs.getString("actors");
					price = rs.getString("price");

					output = String.format("%s\t%s\t%s\t%s\t%s\t%s\t%s", SKU, title, studio, director, rating, actors, price);
				}

			}
			else if (itemSKUFromView.charAt(0) == 'B') {
				while (rs.next()) {
					SKU = rs.getString("SKU");
					ISBN = rs.getString("ISBN");
					title = rs.getString("title");
					author = rs.getString("author");
					publisher = rs.getString("publisher");
					publishingYear = rs.getString("publishing_year");
					publishingCity = rs.getString("publishing_city");
					price = rs.getString("price");

					output = String.format("%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s", SKU, ISBN, title, author, publisher, publishingYear, publishingCity, price);	
				}
			}
		}

		// Catch exceptions.
		catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
		finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlException) {
					// Do nothing with exception.
				}
				rs = null;
			}
			if (connection != null) {
				disconnectFromDatabase();
			}
		}
		return output;
	}
}
