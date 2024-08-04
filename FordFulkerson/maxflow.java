
import java.nio.file.*;
import java.util.*;

class Graph {
    // Variable declaration
    private int V;
    private List<List<Node>> adjacencyList;
    double[][] matrix;

    static class Node {
        int vertex;
        double weight;

        Node(int vertex, double weight) {
            this.vertex = vertex;
            this.weight = weight;
        }
    }

    Graph(int V) {
        this.V = V;
        adjacencyList = new ArrayList<>(V);
        for (int i = 0; i < V; i++) {
            adjacencyList.add(new ArrayList<>());
        }
        matrix = new double[V][V];
        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                matrix[i][j] = 0;
            }
        }
    }

    void printMatrix() {

        for (int i = 0; i < V; i++) {
            if (i == 0) {
                for (int k = 0; k < V; k++)
                    System.out.print("\t" + k + ":");
                System.out.println();
            }
            System.out.print(i + ":\t");
            for (int j = 0; j < V; j++) {

                if (matrix[i][j] <= 0) {
                    System.out.print("-\t");
                } else {
                    System.out.printf("%.3f\t", matrix[i][j]);
                }
            }
            System.out.println();
        }
    }

    void addEdge(int source, int destination, double weight) {
        Node to = new Node(destination, weight);
        Node from = new Node(source, weight);
        adjacencyList.get(source).add(to);
        adjacencyList.get(destination).add(from);
        matrix[source][destination] = weight;
    }

    public double fordFulkerson(int source, int sink) {
        double[][] residualCapacity = new double[V][V];

        for (int u = 0; u < V; u++) {
            for (Node edge : adjacencyList.get(u)) {
                int v = edge.vertex;
                residualCapacity[u][v] = edge.weight;
            }
        }

        double maxFlow = 0;
        int[] parent = new int[V];

        while (bfs(residualCapacity, source, sink, parent)) {
            double pathFlow = Double.MAX_VALUE;

            int v = sink;
            while (v != source) {
                int u = parent[v];
                pathFlow = Math.min(pathFlow, residualCapacity[u][v]);
                v = u;
            }

            v = sink;
            while (v != source) {
                int u = parent[v];
                residualCapacity[u][v] -= pathFlow;
                residualCapacity[v][u] += pathFlow;
                v = u;
            }

            maxFlow += pathFlow;
        }
        System.out.printf("\tFord-Fulkerson => %.3f\n", maxFlow);
        if (V < 11) {
            printFlow(residualCapacity);
        }

        return maxFlow;
    }

    private boolean bfs(double[][] residualCapacity, int source, int sink, int[] parent) {
        boolean[] visited = new boolean[V];
        for (int i = 0; i < V; ++i)
            visited[i] = false;
        Queue<Integer> queue = new LinkedList<>();
        queue.add(source);
        visited[source] = true;
        parent[source] = -1;

        while (!queue.isEmpty()) {
            int u = queue.poll();

            for (int v = 0; v < V; v++) {
                if (!visited[v] && residualCapacity[u][v] > 0) {
                    queue.add(v);
                    parent[v] = u;
                    visited[v] = true;

                }
            }
        }

        return (visited[sink] == true);
    }

    public double preflowPush(int source, int sink) {
        double[][] flow = new double[V][V];
        int[] height = new int[V];
        double[] excess = new double[V];

        height[source] = V;
        for (Node neighbor : adjacencyList.get(source)) {
            int next = neighbor.vertex;
            flow[source][next] = neighbor.weight;
            excess[next] = neighbor.weight;
            excess[source] -= neighbor.weight;
            flow[next][source] = -neighbor.weight;
        }

        Queue<Integer> activeNodes = new LinkedList<>();
        for (int i = 1; i < V - 1; i++) {
            if (excess[i] > 0) {
                activeNodes.add(i);
            }
        }

        while (!activeNodes.isEmpty()) {
            int currentNode = activeNodes.poll();

            boolean relabeled = false;
            for (Node neighbor : adjacencyList.get(currentNode)) {
                int next = neighbor.vertex;
                double residualCapacity = neighbor.weight - flow[currentNode][next];

                if (residualCapacity > 0 && height[currentNode] > height[next]) {
                    double pushedFlow = Math.min(excess[currentNode], residualCapacity);
                    flow[currentNode][next] += pushedFlow;
                    flow[next][currentNode] -= pushedFlow;

                    excess[currentNode] -= pushedFlow;
                    excess[next] += pushedFlow;

                    if (next != source && next != sink && !activeNodes.contains(next)) {
                        activeNodes.add(next);
                    }

                    if (excess[currentNode] <= 0) {
                        break;
                    }
                } else {
                    relabeled = true;
                }
            }

            if (excess[currentNode] > 0 && relabeled) {
                relabel(currentNode, flow, height);
                activeNodes.add(currentNode);
            }
        }

        double maxFlow = 0;
        for (int i = 0; i < V; i++) {
            maxFlow += flow[source][i];
        }
        System.out.printf("\tPreflow-push => %.3f\n", maxFlow);
        if (V < 11) {
            printFlow(flow);
        }
        return maxFlow;
    }

    private void relabel(int currentNode, double[][] flow, int[] height) {
        int minHeight = Integer.MAX_VALUE;
        for (Node neighbor : adjacencyList.get(currentNode)) {
            int next = neighbor.vertex;
            double residualCapacity = neighbor.weight - flow[currentNode][next];
            if (residualCapacity > 0) {
                minHeight = Math.min(minHeight, height[next]);
            }
        }

        height[currentNode] = minHeight + 1;
    }

    public void printFlow(double[][] flow) {
        for (int i = 0; i < V; i++) {
            if (i == 0) {
                for (int k = 0; k < V; k++)
                    System.out.print("\t" + k + ":");
                System.out.println();
            }
            System.out.print(i + ":\t");
            for (int j = 0; j < V; j++) {
                if (flow[i][j] <= 0) {
                    System.out.print("-\t");
                } else {
                    System.out.printf("%.3f\t", flow[i][j]);
                }
            }
            System.out.println();
        }
    }
}

public class maxflow {
    public static void main(String[] args) throws Exception {
        Path inputFile = Path.of(args[0]);
        String input = Files.readString(inputFile);
        String[] fileInput = input.split("\\*\\*");
        int numberOfGraphs = Integer.parseInt(fileInput[0].split(" ")[0]);
        System.out.println("Maximum flow from vertex 0 to vertex n-1 in flowNetworks.txt, |V|=n\n");
        for (int i = 0; i < numberOfGraphs; i++) {
            String[] inp = fileInput[i + 1].split("\n");
            String name = inp[0].split("=")[0].split(":")[0];
            int v = 0;
            String[] myVertices = inp[0].substring(inp[0].indexOf("|") + 1, inp[0].indexOf(",")).split("=");
            v = Integer.parseInt(myVertices[myVertices.length - 1]);
            System.out.println(name + "'s  Maximum Flow:");
            Graph graph = new Graph(v);
            for (int j = 2; j < inp.length - 2; j++) {
                inp[j] = inp[j].replaceAll("\\s", "");
                inp[j] = inp[j].replace("(", "");
                inp[j] = inp[j].replace(")", "");
                inp[j] = inp[j].replace("}", "");
                String[] edge = inp[j].split(",");
                graph.addEdge(Integer.parseInt(edge[0]), Integer.parseInt(edge[1]), Double.parseDouble(edge[2]));

            }
            if (v < 11) {
                graph.printMatrix();
            }
            double fordFulkerson = graph.fordFulkerson(0, v - 1);

            double preflowPush = graph.preflowPush(0, v - 1);

        }
    }

}
