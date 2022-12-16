package hankk20.modern_java.parallel;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

public class ForkJoinSumCalculator extends RecursiveTask<Long>{
    private final long[] numbers;
    private final int start; //이 서브태스크에서 처리할 배열의 초기 위치와 최종 위치
    private final int end;
    public static final long THRESHOLD = 10_000; //이 값 잏의 서브태스크는 더 이상 분할할 수 없다.

    public ForkJoinSumCalculator(long[] numbers) {
        this(numbers, 0, numbers.length);
    }

    public ForkJoinSumCalculator(long[] numbers, int start, int end) {
        this.numbers = numbers;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        int length = end - start; //이 태스크에서 더할 배열의 길이
        if(length <= THRESHOLD){ //최소분할 크기 보다 적으면
            return computeSequentially();
        }
        //배열의 첫 번째 절반을 더하도록 서브태스크 생성
        ForkJoinSumCalculator leftTask = new ForkJoinSumCalculator(numbers, start, start + length / 2);
        //fork 쓰레드 분기
        leftTask.fork();
        //배열의 나머지 절반을 더하도록 서브태스크 생성
        //2번째 Task를 fork 하지 않는 이유는 메인 쓰레드를 사용 하기 위함
        ForkJoinSumCalculator rightTask = new ForkJoinSumCalculator(numbers, start+length/2, end);
        Long rightResult = rightTask.compute();
        Long leftResult = leftTask.join();
        return leftResult+rightResult;
    }

    /**
     * 더 이상 분할 할 수 없을때 계산
     * @return
     */
    private long computeSequentially(){
        long sum = 0;
        for (int i = start ; i < end ; i++) {
            sum += numbers[i];
        }
        return sum;
    }

    public static void main(String[] args) {
        System.out.println("availableProcessors => " +Runtime.getRuntime().availableProcessors());
        long[] longs = LongStream.rangeClosed(1, 10_000_000).toArray();
        ForkJoinSumCalculator forkJoinSumCalculator = new ForkJoinSumCalculator(longs);
        Long invoke = new ForkJoinPool().invoke(forkJoinSumCalculator);
        System.out.println(invoke);
    }
}
