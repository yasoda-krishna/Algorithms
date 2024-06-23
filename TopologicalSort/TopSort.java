import java.nio.file.*;
import java.util.*;

class Graph {
    private Map<Integer, List<Integer>> graph;
    private Set<Integer> vert;
    private int vertices;
    private Map<Integer, Integer> indegrees;
    private List<Integer> visited = new ArrayList<>();
    private LinkedList<Integer> queue;
    private List<Integer> adjacenyList;

    /**
     * Name : Yasoda Krishna Reddy Annapureddy
     * Date : 06/22/2023
     * Time : 04:20 PM
     * Constructor for intializing the data and assigning values
     *
     * @param v number of vertices in the graph
     */
    public Graph(int v) {
        graph = new HashMap<>();
        vert = new HashSet<>();
        vertices = v;
    }

    /**
     * Name : Yasoda Krishna Reddy Annapureddy
     * Date : 06/22/2023
     * Time : 10:30 PM
     * This method will check the adjacency list of the visited node and decrement
     * the indegrees based on the visited node adjacenct list and add nodes to queue
     * if any node has indegree 0
     *
     * @param key vertex removed from the queue
     *
     */
    public void sortTheVertices(int key) {
        // get the adjacent vertices of the visited vertex
        adjacenyList = graph.get(key);
        // This condition verifies adjacency list is null or not
        if (adjacenyList != null) {
            // This loop passes to each adjcent vertex in the adjacency list and decrement
            // the adjacency vertex indegree with 1.
            // if any of the vertex have indegree 0 add the vertex to queue.
            for (int adjacent : adjacenyList) {
                indegrees.put(adjacent, indegrees.get(adjacent) - 1);
                if (indegrees.get(adjacent) == 0) {
                    queue.add(adjacent);
                }
            }
        }
    }

    /**
     * Name : Yasoda Krishna Reddy Annapureddy
     * Date : 06/22/2023
     * Time : 04:30 PM
     * The addTheEdgeToGraph method will add edges to the graph.
     *
     * @param m the source node
     * @param n the destination node
     */
    public void addTheEdgeToGraph(int m, int n) {
        if (graph.containsKey(m)) {
            graph.get(m).add(n);
        } else {
            graph.put(m, new ArrayList<>(Arrays.asList(n)));
        }
    }

    /**
     * Name : Yasoda Krishna Reddy Annapureddy
     * Date : 06/22/2023
     * Time : 07:30 PM
     * topologicalSort is the method to perform topological sort to the given graph.
     *
     * @return visited is The list contains of visited nodes.
     */
    public List<Integer> topologicalSort() {
        indegrees = new HashMap<>();
        // This loop inserts vertices into the indegress and initializes indegree value
        // to zero
        for (int vertex = 0; vertex < vertices; vertex++) {
            vert.add(vertex);
            indegrees.put(vertex, 0);
        }
        // System.out.println("graphsize " + graph.size());
        // This loop will insert the indegrees of each vertex.
        for (int source = 0; source < vertices; source++) {
            List<Integer> destinations = graph.get(source);
            if (destinations != null) {
                for (int destination = 0; destination < destinations.size(); destination++) {
                    int dest = destinations.get(destination);
                    indegrees.put(dest, indegrees.get(dest) + 1);
                }

            }

        }

        // for (int i = 0; i < graph.size(); i++) {
        // System.out.println("Vertex : " + i + " indegree" + indegrees.get(i));
        // }
        queue = new LinkedList<>();
        // This loop verifies any of the vertex have indegrees 0 and if vertex have zero
        // indegree to the queue.
        for (int loop = 0; loop < vertices; loop++) {
            if (indegrees.get(loop) == 0) {
                queue.add(loop);
            }
        }
        // This loop verifies queue is empty or not.
        // If the queue is not empty then remove the vertex from queue and add it to
        // visited list after that pass the vertex to sortTheVertices.
        while (!queue.isEmpty()) {
            int key = queue.remove();
            visited.add(key);
            sortTheVertices(key);
        }
        // return the visited list
        return visited;
    }
}

/**
 * Name : Yasoda Krishna Reddy Annapureddy
 * Date : 06/22/2023
 * Time : 02:06 PM
 */
public class topSort {
    /**
     * Main method that takes input from user and parses it to the required format
     * and
     * Create graph and inserts edges and calls topological sort method after that
     * stores the output in the list
     * and prints the output in the expected format.
     *
     * @param args[] command line arguments
     */
    public static void main(String args[]) throws Exception {

        Path inputFile = Path.of(args[0]);
        String input = Files.readString(inputFile);

        String[] fileInput = input.split("\\*\\*");
        int numberOfGraphs = Integer.parseInt(fileInput[0].split(" ")[0]);
        System.out.println("Topological Orders:");
        for (int i = 0; i < numberOfGraphs; i++) {
            String[] inp = fileInput[i + 1].split("\n");
            String name = inp[0].split("=")[0].split(":")[0];
            int v = 0;
            String[] myVertices = inp[0].substring(inp[0].indexOf("{") + 1, inp[0].indexOf("}")).split("\\s+");
            v = myVertices.length - 1;
            Graph graph = new Graph(v);
            for (int j = 2; j < inp.length - 2; j++) {
                inp[j] = inp[j].replaceAll("\\s", "");
                inp[j] = inp[j].replace("(", "");
                inp[j] = inp[j].replace(")", "");
                inp[j] = inp[j].replace("}", "");
                String[] edge = inp[j].split(",");
                graph.addTheEdgeToGraph(Integer.parseInt(edge[0]), Integer.parseInt(edge[1]));
            }
            List<Integer> output = graph.topologicalSort();
            System.out.print(name + ":  ");
            // this conditional statement check the size of the output list.
            // if size is 0 then the graph is cyclic.
            if (output.size() == 0) {
                System.out.print("No in-degree 0 vertex; not an acyclic graph.");
            } else {
                // This conditional statement check the size equals to the number of vertices
                // then
                // It prints all the vertices in the output list
                if (output.size() == v) {
                    for (int vertex : output) {
                        System.out.print(vertex + " ");
                    }
                } else {
                    // This prints the vertices in the list and prints there are no more in-degree 0
                    // vertex.
                    for (int j = 0; j < output.size(); j++) {
                        System.out.print(output.get(j) + " ");
                    }
                    System.out.print(" -> no more in-degree 0 vertex; not an acyclic graph.");
                }
            }
            System.out.println();
        }
    }
}