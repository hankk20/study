package graph;

import java.util.*;

public class BasicListDFS {

    public static boolean[] visited;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
        String[] s1 = s.split(" ");
        int n = Integer.parseInt(s1[0]); //Node 갯수
        int m = Integer.parseInt(s1[1]); //간선
        int v = Integer.parseInt(s1[2]); //시작위치
        ArrayList<Integer>[] list = createList(scanner, n, m);
        visited = new boolean[n+1];
        dfsRecursive(list, v);

    }


    public static ArrayList<Integer>[] createList(Scanner scanner, int node, int edge){
        ArrayList<Integer>[] list = new ArrayList[node+1];

        for (int i = 1; i < node+1; i++) {
            list[i] = new ArrayList<>();
        }

        for (int i = 0; i < edge; i++) {
            StringTokenizer stringTokenizer = new StringTokenizer(scanner.nextLine(), " ");
            int n = Integer.parseInt(stringTokenizer.nextToken());
            int m = Integer.parseInt(stringTokenizer.nextToken());
            list[n].add(m);
            list[m].add(n);
        }
        Arrays.stream(list)
                .filter(Objects::nonNull)
                .forEach(Collections::sort);
        return list;
    }

    public static LinkedHashSet<Node<Integer>> createNode(Scanner scanner, int node, int edge){
        LinkedHashSet<Node<Integer>> list = new LinkedHashSet<>();
        for (int i = 0; i < edge; i++) {
            StringTokenizer stringTokenizer = new StringTokenizer(scanner.nextLine(), " ");
            int n = Integer.parseInt(stringTokenizer.nextToken());
            int m = Integer.parseInt(stringTokenizer.nextToken());
            Node<Integer> node1 = new Node<>(n);
            Node<Integer> node2 = new Node<>(m);
        }
        return list;
    }

    public static void dfsRecursive(ArrayList<Integer>[] list, int start){
        visited[start] = true;
        ArrayList<Integer> integers = list[start];
        System.out.print(start+" ");
        for (int node : integers) {
            if(!visited[node]){
                dfsRecursive(list, node);
            }
        }
    }

    public static class Node<T> {
        private T value;
        private LinkedHashSet<Node<T>> link = new LinkedHashSet<>();

        public Node(T value){
            this(value, null);
        }
        public Node(T value, Node<T> node) {
            this.value = value;
            this.link.add(node);
        }

        public T getValue() {
            return value;
        }

        public LinkedHashSet<Node<T>> getNodes() {
            return link;
        }

        public void setNode(Node<T> node) {
            if(!link.contains(node)){
                link.add(node);
                node.setNode(this);
            }

        }

    }

}
