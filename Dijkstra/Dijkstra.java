import java.nio.file.*;
import java.util.*;

class Graph {
    // Declare an instance variable V that represents the number of vertices in the
    // graph
    private int V;
    // Declare an instance variable ind to keep track of the graph number
    private int ind;
    // Declare an instance variable adjacencyList to represent the list of lists of
    // nodes that represent the edges in the graph
    private List<List<Node>> adjacencyList;

    // Declare a nested class Node to represent the nodes in the graph
    static class Node {
        // Declare an instance variable vertex that represents the index of the vertex
        int vertex;
        // Declare an instance variable weight that represents the weight of the edge
        // between the vertices
        double weight;

        // Node constructor to initialize the values of vertex and weight
        Node(int vertex, double weight) {
            this.vertex = vertex;
            this.weight = weight;
        }
    }

    /**
     * Name : Yasoda Krishna Reddy Annapureddy
     * Date : 06/23/2023
     * Time : 02:30 PM
     * Graph constructor to initialize the number of vertices and the graph number
     *
     * @param V   Number of vertices in the graph.
     * @param ind This is for keep track of graph number.
     */
    Graph(int V, int ind) {
        this.V = V;
        this.ind = ind;
        // Initialize the adjacencyList with V empty lists of Nodes
        adjacencyList = new ArrayList<>(V);
        for (int i = 0; i < V; i++) {
            adjacencyList.add(new ArrayList<>());
        }
    }

    /**
     * Name : Yasoda Krishna Reddy Annapureddy
     * Date : 06/23/2023
     * Time : 02:42 PM
     * Method to add an edge between two vertices with a given weight
     *
     * @param source      The source of the edge
     * @param destination The destination of edge
     * @param weight      The weight of Edge
     */
    void addEdge(int source, int destination, double weight) {
        // Create a new node with the destination vertex and weight
        Node node = new Node(destination, weight);
        // Add the node to the adjacency list at the source vertex
        adjacencyList.get(source).add(node);
    }

    /**
     * Name : Yasoda Krishna Reddy Annapureddy
     * Date : 06/23/2023
     * Time : 02:47 PM
     * Method to find the shortest path from the source vertex using Dijkstra's
     * algorithm
     *
     * @param source The source of the shortest path
     */
    void dijkstra(int source) {
        // Create an array dist to store the shortest distance from the source vertex to
        // each vertex
        double[] dist = new double[V];
        // Create an array previous to store the previous vertex in the shortest path
        // from the source vertex to each vertex
        int[] previous = new int[V];
        // Fill the dist array with maximum values
        Arrays.fill(dist, Double.MAX_VALUE);
        // Set the source vertex distance to 0
        dist[source] = 0;
        // Create a priority queue to store the vertices based on their distances
        PriorityQueue<Node> pq = new PriorityQueue<>(V, new Comparator<Node>() {
            @Override
            public int compare(Node node1, Node node2) {
                // Compare the weights of the nodes
                if (node1.weight < node2.weight) {
                    return -1;
                } else if (node1.weight > node2.weight) {
                    return 1;
                }
                return 0;
            }
        });

        // offer the source node with distance 0 to the priority queue
        pq.offer(new Node(source, 0));

        // loop until the priority queue is not empty
        while (!pq.isEmpty()) {
            // get the node with the shortest distance from the priority queue
            int u = pq.poll().vertex;
            // iterate through the nodes connected to the current node
            for (Node node : adjacencyList.get(u)) {
                int v = node.vertex;
                double weight = node.weight;
                // if the new distance to v is shorter than the current distance
                if (dist[v] > dist[u] + weight) {
                    // update the distance to the new shorter distance
                    dist[v] = dist[u] + weight;
                    previous[v] = u;
                    // add the updated node to the priority queue
                    pq.offer(new Node(v, dist[v]));
                }
            }
        }
        // if end vertes is not reachable then there is no path
        if (dist[V - 1] == Double.MAX_VALUE) {
            System.out.println("\t*** There is no path.");
        } else {
            List<Integer> path = getPreviousVertex(previous, source, V - 1);
            double s = 0;
            for (int i = 1; i < path.size(); i++) {
                System.out.println(String.format("\t(%3d, %3d, %6.3f) --> %7.3f", path.get(i - 1), path.get(i),
                        dist[path.get(i)] - s, dist[path.get(i)]));
                s = dist[path.get(i)];

            }

        }
    }

    /**
     * Name : Yasoda Krishna Reddy Annapureddy
     * Date : 06/23/2023
     * Time : 04:40 PM
     *
     * helper method to get the path using the previous array
     *
     * @param previous The previous integer array that contains previous vertex
     * @param start    The start of the shortest path
     * @param end      The end of the shortest path
     * @return List of integers that contains shortest path
     */
    private List<Integer> getPreviousVertex(int[] previous, int start, int end) {
        List<Integer> path = new ArrayList<>();
        // iterate through the previous array from the end node to the start node
        for (int i = end; i != start; i = previous[i]) {
            path.add(i);
        }
        // add the start node to the path
        path.add(start);
        // reverse the path to get the order from start to end
        Collections.reverse(path);
        return path;
    }

}

class dijkstra {
    /**
     * Name : Yasoda Krishna Reddy Annapureddy
     * Date : 06/23/2023
     * Time : 02:06 PM
     * Main method
     *
     * @param args for accessing the command line arguments
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        Path inputFile = Path.of(args[0]);
        String input = Files.readString(inputFile);
        int noOfNodes = 0;

        String[] fileInput = input.split("\\*\\*");
        int numberOfGraphs = Integer.parseInt(fileInput[0].split(" ")[0]);
        System.out.println("Shortest Paths from vertex 0 to vertex n-1 in wdGraphs.txt, |V|=n");
        for (int i = 0; i < numberOfGraphs; i++) {
            String[] inp = fileInput[i + 1].split("\n");
            String name = inp[0].split("=")[0].split(":")[0];
            int v = 0;
            String[] myVertices = inp[0].substring(inp[0].indexOf("|") + 1, inp[0].indexOf(",")).split("=");
            v = Integer.parseInt(myVertices[myVertices.length - 1]);
            System.out.println(name + "'s  shortest path from 0 to " + (v - 1) + ":");
            Graph graph = new Graph(v, noOfNodes++);
            for (int j = 2; j < inp.length - 2; j++) {
                inp[j] = inp[j].replaceAll("\\s", "");
                inp[j] = inp[j].replace("(", "");
                inp[j] = inp[j].replace(")", "");
                inp[j] = inp[j].replace("}", "");
                String[] edge = inp[j].split(",");
                graph.addEdge(Integer.parseInt(edge[0]), Integer.parseInt(edge[1]), Double.parseDouble(edge[2]));

            }
            graph.dijkstra(0);
        }

    }
}