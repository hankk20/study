## 정리중

### java.util.concurrent

## 7. 병렬 스트림

- 병렬 스트림에서 사용 하는 스레드 풀 설정
  - ForkJoinPool 사용

### 7.2 Fork/Join 프레임워크

- Fork/Join은 서브태스크를 스레드 풀(ForkJoinPool)의 작업자 스레드에 분산 할당하는 ExecutorService를 구현한다.
- 리턴값 여부에 따라 리턴 값이 있으면 **RecursiveTask**, 없으면 **RecursiveAction**
- compute 메소드 구현

  ```java
          if(태스크가 충분히 작거나 더이상 분할 할 수 없으면){
              순차적으로 태스크 계산
          }else{
              태스크를 두 서브 태스크로 분할
              태스크가 다시 서브태스크로 분할되도록 이 메서드를 재귀적으로 호출
              모든 서브태스크의 연산이 완료될 때까지 기다림
              각 서브태스크의 결과를 합침
          }
  ```
- [RecursiveTask 예제](/modern_java/src/hankk20/modern_java/parallel/ForkJoinSumCalculator.java) CPU Core 수만큼 ForJoinPool에 쓰레드 생성

  ![CPU Core 수만큼 ForJoinPool에 쓰레드 생성](https://user-images.githubusercontent.com/60081600/208114763-4e5fd1c5-f7f1-4dc4-8f8c-afc30f2cc714.png)

### 7.3 Spliterator

- [WordCounterSpliterator](/modern_java/src/hankk20/modern_java/parallel/WordCounterSpliterator.java), [WordCounter](/modern_java/src/hankk20/modern_java/parallel/WordCounter.java)
- 동작순서
  - trySplit 메소드를 호출 하여 분할(Fork)
    - 분할과정은 characteristics영향을 받는다.
  - 더이상 분할 할게 없으면 tryAdvance를 호출한다.
    - tryAdvance에서는 reduce의 두번째 인자값인 BIFunction (accumulator)를 호출한다.
  - reduce의 세번째 인자값인 BinaryOperator를 호출하여 값을 합친다.(Join)

---

## 16. CompletableFuture와 리액티브

`스레드는 잠들어도 여전히 시스템 자원을 점유한다.`

### 16.1.1 Future 형식 API

- Future 제한
  - [SimpleFuture](/modern_java/src/hankk20/modern_java/reactive/future/SimpleFuture.java) 
  - Future.get()으로 결과를 기다려야 한다.
    ![2022-12-17 23 57 38](https://user-images.githubusercontent.com/60081600/208248073-6759a6e7-94b4-41e6-9a08-1ab82254b3c2.png)
