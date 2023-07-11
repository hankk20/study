package graph;


import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * https://www.acmicpc.net/problem/1260
 */
public class Back1260MatrixDFS {

    public static boolean[] visited;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
        String[] s1 = s.split(" ");
        int n = Integer.parseInt(s1[0]); //Node 갯수
        int m = Integer.parseInt(s1[1]); //간선
        int v = Integer.parseInt(s1[2]); //시작위치
        int[][] matrix = createMatrix(scanner, n, m);
        visited = new boolean[n+1];
        dfsMatrixRecursive(matrix, v);
        System.out.println();
        visited = new boolean[n+1];
        bfsMatrix(matrix, v);
    }

    /**
     * 인접행렬 생성
     * index를 1부터 시작하기 위해 node+1 갯수만큼 2차원 배열 생성
     * 간선갯수만큼 입력 받음 => 1 2, 1 3, 1 4, 2 4, 3 4
     * 양방향이기때문에
     * 1 2 입력시
     * int[1][2] = 1 이되고
     * int[2][1] = 1 이된다
     * [0, 0, 1, 0, 0]
     * [0, 1, 0, 0, 0]
     *
     * 1 3 입력시
     * [1][3] = 1, [3][1] = 1
     * [0, 0, 1, 1, 0]
     * [0, 1, 0, 0, 0]
     * [0, 1, 0, 0, 0]
     * @return
     */
    public static int[][] createMatrix(Scanner scanner, int node, int edge){
        int[][] matrix = new int[node+1][node+1];
        for (int i = 0; i < edge; i++) {
            StringTokenizer stringTokenizer = new StringTokenizer(scanner.nextLine(), " ");
            int n = Integer.parseInt(stringTokenizer.nextToken());
            int m = Integer.parseInt(stringTokenizer.nextToken());
            matrix[n][m] = 1;
            matrix[m][n] = 1;
        }
        return matrix;
    }

    /**
     * DFS 시작
     * 1 2, 1 3, 1 4, 2 4, 3 4일때 배열의 값은(0번째 인덱스는 생략)
     * [0, 1, 1, 1]
     * [1, 0, 0, 1]
     * [1, 0, 0, 1]
     * [1, 1, 1, 0]
     * 시작 node가 1이면
     * [0, 1, 1, 1] 선택되고
     * 시작 node 1은 방문 체크
     * 시작노드 출력 1
     * 2번째가 연결되어 있으므로 2번째 배열을 다시 재귀함수에 넣고 node 번호를 넣어줌
     *
     * 2번째 재귀에서는 => 2
     * 2번째 열 [1, 0, 0, 1]이 선택되고
     * 시작노드는 2
     * 출력 2
     * 방문 체크
     * 다시 첫번째 1을 선택했을때 visited[1]는 true이므로 패스
     * 4번째 값이 1이므로 다시 재귀 => 4
     *
     * 3번째 재귀에서
     * 4번째 열 [1, 1, 1, 0] 선택
     * 시작노드 4
     * 출력 4
     * 방문 체크
     * 1, 2는 방문 체크 되어있으므로 스킵
     * 3번째 열을 재귀 => 3
     *
     * 4번째 재귀에서
     * 3번째 열 [1, 0, 0, 1] 선택
     * 시작노드 3
     * 출력 3
     * 방문 체크
     * 전부 방문되어있으므로 끝
     *
     * @param matrix
     * @param start
     */
    public static void dfsMatrixRecursive(int[][] matrix, int start) {

        int[] matrix1 = matrix[start];

        visited[start] = true;

        System.out.print(start+" ");
        for (int i = 1; i < matrix1.length; i++) {
            if(matrix1[i] == 1 && !visited[i]){
                dfsMatrixRecursive(matrix, i);
            }
        }
    }

    public static void bfsMatrix(int[][] matrix, int start) {
        visited[start] = true;
        Queue<Integer> queue = new LinkedList<>();
        queue.add(start);
        while (!queue.isEmpty()){
            int poll = queue.poll();
            System.out.print(poll +" ");
            int[] matrix1 = matrix[poll];
            for (int i = 1; i < matrix1.length; i++) {
                if(matrix1[i] == 1 && !visited[i]){
                    visited[i] = true;
                    queue.add(i);
                }
            }
        }
    }

}
