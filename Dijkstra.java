import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;

public class Dijkstra 
{
  public static void main(String[] args) 
  {
    //import all the input files
    String[] inputFiles = {
      "Algs_fifth_assignment_Spring_2023_test_cases/input1.txt",
      "Algs_fifth_assignment_Spring_2023_test_cases/input2.txt",
      "Algs_fifth_assignment_Spring_2023_test_cases/input3.txt",
      "Algs_fifth_assignment_Spring_2023_test_cases/input4.txt"
    };
    for (String inputFile : inputFiles) {
      File file = new File(inputFile);
      try (Scanner input = new Scanner(file)) 
      {
        int V = input.nextInt(); // these are the number of vertices
        int E = input.nextInt(); // these are the number of edges
        
        //Create an adjacency list for the graph G i.e here adjList is G
        List<List<Edge>> adjList = new ArrayList<>();
        for (int i = 0; i < V; i++) {
          adjList.add(new ArrayList<>());
        }
        
        //Add all the edges in the given graph to the adjacency list
        for (int i = 0; i < E; i++) {
          int src = input.nextInt();
          int dest = input.nextInt();
          int weight = input.nextInt();
          adjList.get(src).add(new Edge(dest, weight));
        }
        
        //calculate sum of weights to get the infinity value
        int sumOfWeights = 0;
        for (List<Edge> edges : adjList) {
          for (Edge edge : edges) {
            sumOfWeights += edge.weight;
          }
        }
        
        //create an array to store distances of v vertices
        int[] vertexDistance = new int[V];
        //initially assign all the vertices with infinity distance i.e sumOfWeights+1
        Arrays.fill(vertexDistance, sumOfWeights+1);
        //start at vertex 0 and initialize its distance to 0
        vertexDistance[0] = 0;
        //create a priority queue
        PriorityQueueNew pq = new PriorityQueueNew();
        //add root node to the priority queue
        pq.insert(0);
        while (pq.size>=0) {
          //extract vertex with the minimum distance from the priority queue
          int u = pq.extractMin();
          int ud = vertexDistance[u];
          for (Edge edge : adjList.get(u)) {
            int v = edge.dest;
            if (ud+edge.weight < vertexDistance[v]) {
              vertexDistance[v] = ud+edge.weight;
              pq.insert(v);
            }                                   
          }                         
        }
        // Print output
        System.out.print("\n Vertex");
        for (int i = 0; i < V; i++) {
          System.out.printf("\t%d", i);
        }
        System.out.println();
        System.out.print("Distance");
        for (int i = 0; i < V; i++) {               
          if((int)vertexDistance[i]==sumOfWeights+1){
            System.out.print("\t infinity");
          }
          else{
            System.out.printf("\t%d", vertexDistance[i]);
          }
        }
      } 
      catch (FileNotFoundException e) {
        e.printStackTrace();
      }
    }
  } 
  
  static class Edge {
    int dest;
    int weight;
    public Edge(int dest, int weight) {
      this.dest = dest;
      this.weight = weight;
    }
  }
  
  static class PriorityQueueNew{
    static int []Heap = new int[50];
    public int size = -1;
    
    // method to return the index of the parent node of a given node i
    static int parent(int i)
    {
      return (i - 1) / 2;
    }       
    
    // method to return the index of the left child of the given node i
    static int leftChild(int i)
    {
      return ((2 * i) + 1);
    }
    
    // method to return the index of the right child of the given node i
    static int rightChild(int i)
    {
      return ((2 * i) + 2);
    }
    
    // method to shift up if parent node of current node is greater than current node to maintain the heap property
    static void shiftUp(int i)
    {
      while (i > 0 &&
      Heap[parent(i)] > Heap[i])
      {
        swap(parent(i), i);
        i = parent(i);
      }
    }
    
    // method to shift down if the current node is greater than parent node to maintain the heap property
    public void shiftDown(int i)
    {
      int maxIndex = i;       
      // for left child
      int l = leftChild(i);       
      if (l <= size &&
      Heap[l] < Heap[maxIndex])
      {
        maxIndex = l;
      }       
      // for right child
      int r = rightChild(i);       
      if (r <= size &&
      Heap[r] < Heap[maxIndex])
      {
        maxIndex = r;
      }       
      // If i is not same as maxIndex
      if (i != maxIndex)
      {
        swap(i, maxIndex);
        shiftDown(maxIndex);
      }
    }
    
    // method to insert new node in the Heap
    public void insert(int p)
    {
      size = size + 1;
      Heap[size] = p;
      //call shift up to check the parent nodes and maitain heap property
      shiftUp(size);
    }
    
    // method to extract the element with maximum priority
    public int extractMin()
    {
      //always extract root of heap
      int result = Heap[0];       
      //Replace the value at the root with the last leaf
      Heap[0] = Heap[size];
      size = size - 1;
      // call Shift down for the replaced element to maintain the heap property
      shiftDown(0);
      return result;
    }
    
    static void swap(int i, int j)
    {
      int temp= Heap[i];
      Heap[i] = Heap[j];
      Heap[j] = temp;
    }
  }
}

