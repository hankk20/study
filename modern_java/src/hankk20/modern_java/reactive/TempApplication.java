package hankk20.modern_java.reactive;

import java.util.concurrent.Flow;

public class TempApplication {

    public static void main(String[] args) {
        System.out.println("start");
        getTemperatures("Seoul")
                .subscribe(new TempSubscriber());
        System.out.println("end");
    }
    private static Flow.Publisher<TempInfo> getTemperatures(String town){
        return subscriber -> subscriber.onSubscribe(
                new TempSubscription(subscriber, town)
        );
    }

    public static class TestPublisher implements Flow.Publisher<TempInfo> {

        @Override
        public void subscribe(Flow.Subscriber<? super TempInfo> subscriber) {
            subscriber.onSubscribe(new TempSubscription(subscriber, "Masan"));
        }
    }
}
