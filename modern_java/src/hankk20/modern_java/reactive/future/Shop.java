package hankk20.modern_java.reactive.future;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class Shop {
    private final Random random = new Random();

    public double getPrice(String product){
        return calculatePrice(product);
    }

    public Future<Double> getPriceAsyncSupply(String product){
        return CompletableFuture.supplyAsync(() -> calculatePrice(product));
    }
    public Future<Double> getPriceAsync(String product){
        CompletableFuture<Double> futurePrice = new CompletableFuture<>();
        new Thread(() -> {
            try {
                double v = calculatePrice(product);
                futurePrice.complete(v);
            }catch (Exception e){
                futurePrice.obtrudeException(e);
               // futurePrice.completeExceptionally(e);
            }
        }).start();
        return futurePrice;
    }

    public double calculatePrice(String product){
        delay();
        return random.nextDouble() * product.charAt(0) + product.charAt(1);
    }

    public static void delay(){
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 블록방식이랑 똑같은데
     * @param args
     */
    public static void main(String[] args) {
        Shop shop = new Shop();
        long start = System.nanoTime();

        //Future<Double> future = shop.getPriceAsync("abc");
        Future<Double> future = shop.getPriceAsyncSupply("abc");

        long invocationTime = (System.nanoTime() - start) / 1_000_000;
        System.out.println("return "+invocationTime+" mesecs");

        other();

        try {
            double price = future.get();
            System.out.printf("Price is %.2f%n", price);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        long ret = (System.nanoTime() - start) / 1_000_000;
        System.out.println("return after "+ret+" mesecs");

    }

    public static void other(){
        System.out.println("other process");
    }
}
