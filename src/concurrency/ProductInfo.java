package concurrency;

public class ProductInfo {
	String name, description;
	double price, rating;
	public ProductInfo() {}
	public ProductInfo(String name, double price,
			String description, double rating) {
		this.name = name;
		this.price = price;
		this.description = description;
		this.rating = rating;
	}
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("ProductInfo{name='");
		stringBuilder.append(this.name);
		stringBuilder.append("', price=");
		stringBuilder.append(this.price);
		stringBuilder.append(", description='");
		stringBuilder.append(this.description);
		stringBuilder.append("', rating=");
		stringBuilder.append(this.rating);
		stringBuilder.append("}");
		return stringBuilder.toString();
	}
}