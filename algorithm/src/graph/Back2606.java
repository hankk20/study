package graph;

import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * https://www.acmicpc.net/problem/2606
 */
public class Back2606 {

    public static boolean[] visited;
    public static int count;
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int e = scanner.nextInt();
        scanner.nextLine();
        visited = new boolean[n+1];
        int[][] matrix = createMatrix(scanner, n, e);
        dfs(matrix, 1);
        System.out.println(count);

    }

    public static int[][] createMatrix(Scanner scanner, int n, int e){
        int[][] matrix = new int[n+1][n+1];
        for (int i = 0; i < e; i++) {
            StringTokenizer stringTokenizer = new StringTokenizer(scanner.nextLine(), " ");
            int n1 = Integer.parseInt(stringTokenizer.nextToken());
            int n2 = Integer.parseInt(stringTokenizer.nextToken());
            matrix[n1][n2] = matrix[n2][n1] = 1;
        }
        return matrix;
    }

    public static void dfs(int[][] matrix, int start) {
        if(matrix.length == 1){
            count++;
        }
        int[] edgeNode = matrix[start];
        visited[start] = true;
        for (int i = 1; i < edgeNode.length; i++) {
            if(edgeNode[i] == 1 && !visited[i]){
                count++;
                dfs(matrix, i);
            }
        }
    }
}
