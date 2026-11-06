package concurrency;
import java.util.Random;
import java.util.concurrent
.ExecutionException;
import java.util.concurrent
.CompletableFuture;
//дополнительное задание 1
public class DataAggregator {
	static ProductInfo productInfo = new ProductInfo("ноутбук", 899.99,
			"производитель: lenovo", 4.7);
	static void stop(int time) {
		try { Thread.sleep(time); }
		catch (InterruptedException interruptedException) {}
		Random random = new Random();
		if (random.nextInt(5) == 0) throw new RuntimeException();
	}
	static double fetchPrice() {
		stop(1000);
		return productInfo.price;
		}
	static String fetchDescription() {
		stop(2000);
		return productInfo.description;
	}
	static double fetchRating() {
		stop(3000);
		return productInfo.rating;
	}
	public static ProductInfo aggregateProductInfo(String productName) throws ExecutionException, InterruptedException {
		if (productInfo.name.equals(productName)) {
			CompletableFuture<Double> price = CompletableFuture.supplyAsync(() -> fetchPrice()).exceptionally(ex -> 0.0);
			CompletableFuture<String> description = CompletableFuture.supplyAsync(() -> fetchDescription()).exceptionally(ex -> "нет данных");
			CompletableFuture<Double> rating = CompletableFuture.supplyAsync(() -> fetchRating()).exceptionally(ex -> 0.0);
			CompletableFuture.allOf(price, description,
					rating);
			return new ProductInfo(productName, price.get(),
					description.get(), rating.get());
		}
		else return new ProductInfo();
	}
}