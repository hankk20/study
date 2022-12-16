package hankk20.modern_java.parallel;

import java.util.Spliterator;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class WordCounterSpliterator implements Spliterator<Character> {

    private final String string;
    private int currentChar = 0;

    public WordCounterSpliterator(String string) {
        this.string = string;
    }

    /**
     * 분할된 WordCountSpliterator의 string에는 trySplit에서 분할된 문자열을 가지고 있다.<br/>
     * 분할된 데이터를 하나씩 소모시키며 소모에 대한 처리는 {@link Stream#reduce(Object, BiFunction, BinaryOperator)}에서 전달 받은 {@link BiFunction}에서 처리한다.
     * @param action The action
     * @return
     */
    @Override
    public boolean tryAdvance(Consumer<? super Character> action) {
        action.accept(string.charAt(currentChar++)); //한문자씩 읽어 드림 //stream reduce의 두번째 인자 값인 BiFunction accumulate가 호출된다.
        return currentChar < string.length(); //마지막 문자까지 읽어드리면 false;
    }

    /**
     * 분할 작업을 한다 (Fork)
     * @return
     */
    @Override
    public Spliterator<Character> trySplit() {
        int currentSize = string.length() - currentChar; //남은 문자 갯수를 구한다.
        if(currentSize < 10){ //남은 문자 갯수가 10개 이하면 분할을 멈춘다.
            return null;
        }

        //문장의 중간으로 이동시킨다.
        for (int splitPos = currentSize / 2 + currentChar; splitPos < string.length(); splitPos++) {
            if(Character.isWhitespace(string.charAt(splitPos))){ //공백이 나올때 까지 루프를 돌린다.(단어 기준으로 분할하기 위함)
                WordCounterSpliterator spliter = new WordCounterSpliterator(string.substring(currentChar, splitPos));
                currentChar = splitPos;
                return spliter;
            }
        }
        return null;
    }

    @Override
    public long estimateSize() {
        return string.length() - currentChar;
    }

    @Override
    public int characteristics() {
        return ORDERED;
    }



    public static void main(String[] args) {
        String test = """
                썬 마이크로시스템즈에서 1995년에 개발한 객체 지향 프로그래밍 언어.
                창시자는 제임스 고슬링이다. 2010년에 오라클이 썬 마이크로시스템즈를 인수하면서 Java의 저작권을 소유하였다.
                현재는 OpenJDK는 GPL2이나 오라클이 배포하는 Oracle JDK는 상업라이선스로 2019년 1월부터 유료화정책을 강화하고 있다.
                Java EE는 이클립스 재단의 소유이다. Java 언어는 J2SE 1.4부터는 Java Community Process (JCP)에서 개발을 주도하고 있다.
                """;
        WordCounterSpliterator wordCounterSpliterator = new WordCounterSpliterator(test);
        Stream<Character> stream = StreamSupport.stream(wordCounterSpliterator, true);

        //첫번째 인자값은 초기값
        WordCounter reduce = stream.reduce(new WordCounter(0, true), WordCounter::accumulate, WordCounter::combine);
        System.out.println(reduce.getCounter());
    }
}
