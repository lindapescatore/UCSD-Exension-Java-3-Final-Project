package Java3Assignment3;

public class Book extends Item {
	
	private String ISBN;
	private String title;
	private String author;
	private String publisher;
	private String publishingYear;
	private String publishingCity;
	private String price;
	
	public Book(String SKU, String name) {
		super(SKU);
		this.title = name;
	}
	
	public Book(String SKU, String iSBN, String title, String author, String publisher,
			String publishingYear, String publishingCity, String price) {
		super(SKU);
		this.ISBN = iSBN;
		this.title = title;
		this.author = author;
		this.publisher = publisher;
		this.publishingYear = publishingYear;
		this.publishingCity = publishingCity;
		this.price = price;
	}
	
	
	public String itemToString() {
		String itemToString = (super.getSKU() + "\t" + title + "\t" + ISBN + "\t" + author + "\t" + publisher + "\t" + publishingYear + "\t" + publishingCity + "\t" + price);
		
		return itemToString;
	}
	
}
