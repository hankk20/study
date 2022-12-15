package hankk20.modern_java.reactive;

import java.util.concurrent.Flow;

public class TempApplication {

    public static void main(String[] args) {
        getTemperatures("Seoul")
                .subscribe(new TempSubscriber());
    }
    private static Flow.Publisher<TempInfo> getTemperatures(String town){
        return subscriber -> subscriber.onSubscribe(
                new TempSubscription(subscriber, town)
        );
    }
}
