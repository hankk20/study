package hankk20.modern_java.parallel;

import java.util.function.Consumer;

public class WordCounter {
    private final int counter;
    private final boolean lastSpace;

    public WordCounter(int counter, boolean lastSpace) {
        this.counter = counter;
        this.lastSpace = lastSpace;
    }

    /**
     * Spliterator의 {@link WordCounterSpliterator#tryAdvance(Consumer)}에서 호출한다.
     * 분할된 값들을 누적시키는 작업을 한다.
     * 여기서는 단어갯수를 구해야 하기 때문에 문자 하나씩 받아서 공백이 나오면 counter를 1 증가시킨다.
     * @param c
     * @return
     */
    public WordCounter accumulate(Character c){
        if(Character.isWhitespace(c)){
            return lastSpace ? this : new WordCounter(counter, true);
        }
        return lastSpace ? new WordCounter(counter+1, false) : this;
    }

    public WordCounter combine(WordCounter wordCounter){
        int count = counter + wordCounter.counter;
        return new WordCounter(count, lastSpace);
    }

    public int getCounter() {
        return counter;
    }
}
