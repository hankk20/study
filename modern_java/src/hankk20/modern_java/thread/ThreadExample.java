package hankk20.modern_java.thread;

import java.util.concurrent.*;

public class ThreadExample {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        threadTest();
        executorTest();
        completableFutureTest();
    }

    /**
     * Palin Thread
     * 날것 그대로의 스레드
     *
     * @throws InterruptedException
     */
    public static void threadTest() throws InterruptedException {
        Sum sum = new Sum();
        int i = 100;
        Thread one = new Thread(() -> sum.left = f(i));
        Thread two = new Thread(() -> sum.right = g(i));

        one.start();
        two.start();

        //one, two Thread가 끝날때까지 기다린다.
        one.join();
        two.join();

        //one, tow Thread가 끝나면 실행
        print("thread", sum.left, sum.right);
    }

    /**
     * ExecutorService를 사용한 스레드
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public static void executorTest() throws ExecutionException, InterruptedException {
        int i = 100;
        ExecutorService executorService = Executors.newCachedThreadPool();
        Future<Integer> f1 = executorService.submit(() -> f(i));
        Future<Integer> f2 = executorService.submit(() -> g(i));
        print("executor", f1.get(), f2.get());
        executorService.shutdown();
    }

    public static void completableFutureTest() throws ExecutionException, InterruptedException {
        int i = 100;
        ExecutorService executorService = Executors.newCachedThreadPool();
        CompletableFuture<Integer> future = new CompletableFuture<>();
        executorService.submit(() -> future.complete(g(i)));
        print("completable", f(i), future.get());
        executorService.shutdown();
    }

    public static void print(String s, int i, int j) {
        System.out.println(s + " :: " + i + " + " + j + " = " + (i + j));
    }

    public static int f(int i) {
        return i + 100;
    }

    public static int g(int i) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return i + 200;
    }

    private static class Sum {
        private int left;
        private int right;

    }
}
