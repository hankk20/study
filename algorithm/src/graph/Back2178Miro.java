package graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * https://www.acmicpc.net/problem/2178
 */
public class Back2178Miro {


    public static void main(String[] args) throws IOException {

        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf.readLine(), " ");
        int arriX = Integer.parseInt(st.nextToken());
        int arriY = Integer.parseInt(st.nextToken());

        int[][] matrix = new int[arriX][arriY];
        //visited를 사용하면 메모리 초과 오류 남
        //boolean[][] visited = new boolean[arriX][arriY];
        for (int i = 0; i < arriX; i++) {
            matrix[i] = Arrays.stream(bf.readLine().split("")).mapToInt(Integer::parseInt)
                    .toArray();
        }
        System.out.println(bfs(matrix, arriX, arriY));

    }

    public static int bfs(int[][] matrix, int arriX, int arriY) {
        //현재 위치에서 상하좌우 검색할 좌표정보
        int[][] direction = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        Queue<int[]> queue = new LinkedList<>();
        //시작 위치는 0,0으로 고정이고 시작위치도 값을 가진다고해서 1로 셋팅
        queue.offer(new int[]{0, 0, 1});

        while (!queue.isEmpty()) {

            int[] poll = queue.poll();
            int qx = poll[0];
            int qy = poll[1];
            int count = poll[2];
            //길이 없으면 continue;
            if (matrix[qx][qy] == 0) {
                continue;
            }
            //왔던길은 0으로 해서 다시 가지 않도록 함 원래 visited로 확인할려고 했으나 메모리 때문에 대체 함
            matrix[qx][qy] = 0;
            //현재 위치가 종료 위치인지 확인 하고 종료위치이면 count를 리턴
            if (qx == (arriX-1) && qy == (arriY-1)) {
                return count;
            }
            //상하좌우 검색
            for (int i = 0; i < direction.length; i++) {
                int nextX = qx+direction[i][0];
                int nextY = qy+direction[i][1];
                //올바른 위치값인지 다음 위치가 갈수있는 위치인지 확인
                if (nextX < 0 || nextY < 0 || nextY >= arriY || nextX >= arriX || matrix[nextX][nextY] == 0) {
                    continue;
                }
                //count 증가하고 다음위치를 queue에 담음
                queue.offer(new int[]{nextX, nextY, count + 1});
            }
        }
        return -1;
    }
}
