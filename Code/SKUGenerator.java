package Java3Assignment3;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class SKUGenerator {

	private String CDSKUPrefix = "C";
	private String DVDSKUPrefix = "D";
	private String BookSKUPrefix = "B";
	private int CDStartSeed = 10000;
	private int DVDStartSeed = 20000;
	private int BookStartSeed = 30000;
	private int cdCount = 0;
	private int dvdCount = 0;
	private int bookCount = 0;



	Properties skuSystem = new Properties();

	//initialize
	public void initialize() {
		skuSystem.setProperty( "CD Count", Integer.toString(cdCount));
		skuSystem.setProperty( "DVD Count", Integer.toString(dvdCount));
		skuSystem.setProperty( "Book Count", Integer.toString(bookCount));
		System.out.println("SKU System Started.\n");
	}

	public String generateSKUCD() {
		String CDSKU = "null";
		int CDSKUNumberPart = 0;

		CDSKUNumberPart = CDStartSeed + cdCount;
		CDSKU = CDSKUPrefix + Integer.toString(CDSKUNumberPart);;

		cdCount++;
		return CDSKU;	
	}

	public String generateSKUDVD() {
		String DVDSKU = "null";
		int DVDSKUNumberPart = 0;

		DVDSKUNumberPart = DVDStartSeed + dvdCount;
		DVDSKU = DVDSKUPrefix + Integer.toString(DVDSKUNumberPart);;

		dvdCount++;
		return DVDSKU;
	}

	public String generateSKUBook() {
		String BookSKU = "null";
		int BookSKUNumberPart = 0;

		BookSKUNumberPart = BookStartSeed + bookCount;
		BookSKU = BookSKUPrefix + Integer.toString(BookSKUNumberPart);;

		bookCount++;
		return BookSKU;
	}

	public void setSKUPropsFileAndSave() {
		skuSystem.setProperty( "CD Count", Integer.toString(cdCount));
		skuSystem.setProperty( "DVD Count", Integer.toString(dvdCount));
		skuSystem.setProperty( "Book Count", Integer.toString(bookCount));
		System.out.println("You are logged out of the SKU System. \n");
		saveSKUSystem(skuSystem);
	}

	public void synchronizeSKUSystemWithPropsFileAndLoad() {
		loadSKUSystem(skuSystem);
		int setCdCountFromPropsFile = Integer.parseInt(skuSystem.getProperty("CD Count"));
		int setDVDCountFromPropsFile = Integer.parseInt(skuSystem.getProperty("DVD Count"));
		int setBookCountFromPropsFile = Integer.parseInt(skuSystem.getProperty("Book Count"));

		cdCount = setCdCountFromPropsFile;
		dvdCount = setDVDCountFromPropsFile;
		bookCount = setBookCountFromPropsFile;
	}



	private static void saveSKUSystem( Properties props ) {
		try
		{
			FileOutputStream output = new FileOutputStream( "skuSystem.dat" );
			props.store(output, "SKU Properties");
			output.close();
			System.out.println("The SKU system has been saved.\n");
		}
		catch (IOException ioException) {
			System.out.println("Error Writing the inventory to the disk.");
			ioException.printStackTrace();		
		}		
	}

	private static void loadSKUSystem( Properties props ) {
		try 
		{
			FileInputStream input = new FileInputStream( "skuSystem.dat" );
			props.load(input);
			System.out.println("Current SKU system successfully loaded. \n");
		}
		catch (IOException ioException) {
			System.out.println("Failed to load the current SKU system.\n");
			System.out.println("Likely the first time the system has started.\n");
			//ioException.printStackTrace();
		}
	}
}

