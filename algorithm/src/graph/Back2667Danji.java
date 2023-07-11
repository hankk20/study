package graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Stream;

/**
 * https://www.acmicpc.net/problem/2667
 */
public class Back2667Danji {
    public static int[] x = {0, 0, -1, 1};
    public static int[] y = {-1, 1, 0, 0};
    public static int size;

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));

        size = Integer.parseInt(bf.readLine());

        int[][] matrix = new int[size][size];
        boolean[][] visited = new boolean[size][size];

        for (int i = 0; i < size; i++) {
            matrix[i] = Stream.of(bf.readLine().split(""))
                    .mapToInt(Integer::parseInt)
                    .toArray();
        }
        //단지 갯수
        int dangi = 0;
        //단지 별 세대수 갯수
        ArrayList<Integer> counts = new ArrayList<>();

        //시작위치를 정한다. 0110101 이면 2번째 1부터 시작이 되고 dfs 탐색후 끝나면 탐색이 안된 위치를 찿아서 시작한다.
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if(!visited[i][j] && matrix[i][j] == 1){
                    dangi++;
                    int count = dfs(matrix, i, j, visited, 0);
                    counts.add(count);
                }
            }
        }
        Collections.sort(counts);
        System.out.println(dangi);
        counts.forEach(System.out::println);
    }

    public static int dfs(int[][] matrix, int currX, int currY, boolean[][] visited, int count) {
        visited[currX][currY] = true;
        //현재 위치에 세대가 있으면
        if(matrix[currX][currY] == 1){
            //count 증가
            count++;
            //좌우상하 위치로
            for (int i = 0; i < x.length; i++) {
                int nextX = currX+x[i];
                int nextY = currY+y[i];

                //방문하지 않은곳이면
                if(nextX < 0 || nextY < 0 || nextX >= size || nextY >= size || visited[nextX][nextY]){
                    continue;
                }
                //재귀 호출
                count = dfs(matrix, nextX, nextY, visited, count);
            }
        }
        return count;
    }
}
