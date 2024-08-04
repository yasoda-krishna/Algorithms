
import java.nio.file.*;
import java.util.*;

class Graph {
   // Variable declaration
   private int vertices;
   private LinkedList<Integer>[] adjacent_List;
   private boolean[] bfs_visited;
   private boolean[] dfs_visited;
   private String format = "           ";
   private Queue<Integer> queue;
   int current_vertex;

   /**
    * Name : Yasoda Krishna Reddy Annapureddy
    * Date : 02/25/2023
    * Time : 08:50 AM
    * Constructor for initializing the vertices variable
    * 
    * @param vertices
    */
   public Graph(int vertices) {
      this.vertices = vertices;
   }

   /**
    * Name : Yasoda Krishna Reddy Annapureddy
    * Date : 02/25/2023
    * Time : 08:55 AM
    * This method is for constructing the adjacenct list
    */
   @SuppressWarnings("unchecked")
   public void initialize_adjacencyList() {
      adjacent_List = new LinkedList[vertices];
      for (int number_of_vertices = 0; number_of_vertices < vertices; number_of_vertices++) {
         adjacent_List[number_of_vertices] = new LinkedList<>();
      }
   }

   /**
    * Name : Yasoda Krishna Reddy Annapureddy
    * Date : 02/25/2023
    * Time : 09:10 AM
    * This method will add the edges to the adjacent list
    * 
    * @param source      This is the source vertex of the edge
    * @param destination This is the destination vertex of the edge
    */
   public void addTheEdgeToGraph(int source, int destination) {
      // System.out.println(source + " " + destination);
      adjacent_List[source].add(destination);
      adjacent_List[destination].add(source);
   }

   /**
    * Name : Yasoda Krishna Reddy Annapureddy
    * Date : 02/26/2023
    * Time : 09:20 PM
    * This method will calculate the breadth first search using queue
    * 
    * @param vertx       The current vertex
    * @param bfs_visited The visited list is for storing the vertex is visted or
    *                    not
    */
   private void breadthFirstSearch(int vertx, boolean[] bfs_visited) {
      queue = new LinkedList<>();

      bfs_visited[vertx] = true;
      queue.offer(vertx);
      while (!queue.isEmpty()) {
         current_vertex = queue.poll();
         print(current_vertex + " ");

         for (int i = 0; i < adjacent_List[current_vertex].size(); i++) {
            if (!bfs_visited[adjacent_List[current_vertex].get(i)]) {
               bfs_visited[adjacent_List[current_vertex].get(i)] = true;
               queue.offer(adjacent_List[current_vertex].get(i));
            }
         }
      }
   }

   /**
    * Name : Yasoda Krishna Reddy Annapureddy
    * Date : 02/26/2023
    * Time : 10:02 PM
    * This method will calculate the depth first search recursively
    * 
    * @param vertx       The current vertex
    * @param dfs_visited The visited list is for storing the vertex is visited or
    *                    not
    */
   private void depthFirstSearch(int vertx, boolean[] dfs_visited) {
      dfs_visited[vertx] = true;
      print(vertx + " ");
      for (int i = 0; i < adjacent_List[vertx].size(); i++) {
         if (!dfs_visited[adjacent_List[vertx].get(i)]) {
            depthFirstSearch(adjacent_List[vertx].get(i), dfs_visited);
         }
      }

   }

   /**
    * Name : Yasoda Krishna Reddy Annapureddy
    * Date : 02/25/2023
    * Time : 02:20 PM
    * This method is for passing non visited vertex to breadth First Search method
    */
   private void bfs() {
      bfs_visited = new boolean[vertices];
      for (int vertx = 0; vertx < vertices; vertx++) {
         if (!bfs_visited[vertx]) {
            print(format);
            breadthFirstSearch(vertx, bfs_visited);
            println();
         }
      }

   }

   /**
    * Name : Yasoda Krishna Reddy Annapureddy
    * Date : 02/25/2023
    * Time : 04:20 PM
    * This method is for passing non visited vertex to depth first search
    */
   private void dfs() {
      dfs_visited = new boolean[vertices];
      for (int vertx = 0; vertx < vertices; vertx++) {
         if (!dfs_visited[vertx]) {
            print(format);
            depthFirstSearch(vertx, dfs_visited);
            println();
         }
      }
   }

   /**
    * Name : Yasoda Krishna Reddy Annapureddy
    * Date : 02/25/2023
    * Time : 12:20 PM
    * This method is for finding depth first search and breadth first search
    */
   public void findConnectedComponents() {
      String bfs_Label = "      Breadth First Search:";
      String dfs_Label = "      Depth First Search:";
      println(bfs_Label);
      bfs();
      println(dfs_Label);
      dfs();
   }

   /**
    * Name : Yasoda Krishna Reddy Annapureddy
    * Date : 03/01/2023
    * Time : 09:20 AM
    * The method prints the string in new line
    * 
    * @param s This variable is for passing a string
    */
   private void println(String s) {
      System.out.println(s);
   }

   /**
    * Name : Yasoda Krishna Reddy Annapureddy
    * Date : 03/01/2023
    * Time : 09:23 AM
    * This method prints a new line
    */
   private void println() {
      System.out.println();
   }

   /**
    * Name : Yasoda Krishna Reddy Annapureddy
    * Date : 03/01/2023
    * Time : 09:25 AM
    * This method prints a string
    * 
    * @param s This is for passing a string
    */
   private void print(String s) {
      System.out.print(s);
   }

}

/**
 * Name : Yasoda Krishna Reddy Annapureddy
 * Date : 02/24/2023
 * Time : 10:06 AM
 */
public class graphcc {
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
      System.out.println("Connected components of graphs in " + inputFile);
      for (int i = 2; i <= numberOfGraphs + 1; i++) {
         // System.out.println(fileInput[i]);
         String[] inp = fileInput[i].split("\n");
         String name = inp[0].split("=")[0].split(":")[0];
         int v = 0;
         String[] myVertices = inp[0].substring(inp[0].indexOf("|") + 1, inp[0].lastIndexOf(" ")).split("=");
         v = Integer.parseInt(myVertices[myVertices.length - 1].strip());
         Graph graph = new Graph(v);
         graph.initialize_adjacencyList();
         for (int j = 2; j < inp.length; j++) {
            if (inp[j].contains("---")) {
               break;
            }
            inp[j] = inp[j].replaceAll("\\s", "");
            inp[j] = inp[j].replace("(", "");
            inp[j] = inp[j].replace(")", "");
            inp[j] = inp[j].replace("}", "");
            String[] edge = inp[j].split(",");
            // System.out.println("added " + edge[0] + " " + edge[1]);
            graph.addTheEdgeToGraph(Integer.parseInt(edge[0]), Integer.parseInt(edge[1]));
         }
         System.out.println("**" + name + "'s connected components:  ");
         graph.findConnectedComponents();

         System.out.println();
      }
   }
}