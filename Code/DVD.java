package Java3Assignment3;

public class DVD extends Item {
	
	private String title;
	private String studio;
	private String director;
	private String rating;
	private String actors;
	private String price;
	
	public DVD(String SKU, String Name) {
		super(SKU);
		this.title = Name;
	}
	
	public DVD(String SKU, String title, String studio, String director, String rating,
			String actors, String price) {
		super(SKU);
		this.title = title;
		this.studio = studio;
		this.director = director;
		this.rating = rating;
		this.actors = actors;
		this.price = price;
	}
	
	public String itemToString() {
		String itemToString = (super.getSKU() + "\t" + title + "\t" + studio + "\t" + director + "\t" + rating + "\t" + actors + "\t" + price);
		
		return itemToString;
	}
}
