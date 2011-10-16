package Java3Assignment3;

public class CD extends Item{
	
	private String album;
	private String artist;
	private String year;
	private String label;
	private String price;
	
	public CD(String SKU, String Name) {
		super(SKU);
		this.album = Name;
	}
	
	public CD(String SKU, String album, String artist, String year, String label, String price) {
		super(SKU);
		this.album = album;
		this.artist = artist;
		this.year = year;
		this.label = label;
		this.price = price;
	}
	
	public String itemToString() {
		String itemToString = (super.getSKU() + "\t" + album + "\t" + artist + "\t" + year + "\t" + label + "\t" + price);
		
		return itemToString;
	}
	
}
