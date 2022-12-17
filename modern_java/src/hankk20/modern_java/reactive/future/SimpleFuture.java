package hankk20.modern_java.reactive.future;

import java.util.concurrent.*;

/**
 * Future 기능을 이해하기 위해 테스트하는 클래스
 * 1.5 이전까지는 직접 Thread를 실행했었는데 Executor가 나옴으로 사용하기 편리해짐
 */
public class SimpleFuture {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //쓰레드가 있으면 사용 없으면 쓰레드 생성 말그대로 캐쉬 쓰레드
        ExecutorService executorService = Executors.newCachedThreadPool();

        //5초뒤 결과를 반환하는 Callable 작업을 제출 하고 쓰레드가 생성되어 실행된다.
        Future<Double> callableSleep = executorService.submit(() -> {
            System.out.println("Callable Sleep");
            Thread.sleep(5000);
            return 5d;
        });

        System.out.println("Main Thread Running");
        //완료여부 리턴
        System.out.println(callableSleep.isDone());
        //쓰레드로 실행 시킨 작업의 결과를 기다린다.
        Double aDouble = callableSleep.get();

        //5초까지 기다렸다가 응답이 없으면 TimeoutException 발생
        //Double aDouble = callableSleep.get(5, TimeUnit.SECONDS);

        System.out.println("Result ::"+aDouble);
        executorService.shutdown();
    }

}
