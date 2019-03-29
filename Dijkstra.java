import java.util.PriorityQueue;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class Dijkstra{
  /*ConcurrentHashMap<String , LSA> LSDB;
  public Dijkstra(ConcurrentHashMap<String , LSA> LSDB){
    this.LSDB = LSDB;
  }*/
  
  public static void computePaths(Node source){
    source.shortestDistance=0;

  //implement a priority queue
  PriorityQueue<Node> queue = new PriorityQueue<Node>();
  queue.add(source);

  while(!queue.isEmpty()){
   Node u = queue.poll();

   /*visit the adjacencies, starting from 
   the nearest node(smallest shortestDistance)*/
   
   for(Edge e: u.adjacencies){

    Node v = e.target;
    double weight = e.weight;

    //relax(u,v,weight)
    double distanceFromU = u.shortestDistance+weight;
    if(distanceFromU<v.shortestDistance){

     /*remove v from queue for updating 
     the shortestDistance value*/
     queue.remove(v);
     v.shortestDistance = distanceFromU;
     v.parent = u;
     queue.add(v);
    }
   }
  }
 }

 public static List<Node> getShortestPathTo(Node target){

  //trace path from target to source
  List<Node> path = new ArrayList<Node>();
  for(Node node = target; node!=null; node = node.parent){
   path.add(node);
  }
  
  //reverse the order such that it will be from source to target
  Collections.reverse(path);

  return path;
 }

 public static void main(String[] args){

  Node R1 = new Node("R1");
  Node R2 = new Node("R2");
  Node R3 = new Node("R3");
  Node R4 = new Node("R4");
  Node R5 = new Node("R5");
  
  String [][] LSA = new String [][] {{"R1", "R2", "75"}, {"R1", "R3", "75"}, {"R2", "R1", "75"}, {"R2", "R3", "71"}, {"R2", "R4", "20"}, {"R3", "R1", "75"},
    {"R3", "R2", "71"}, {"R3", "R5", "99"}, {"R4", "R2", "20"}, {"R4", "R5", "55"}, {"R5", "R4", "55"}, {"R5", "R3", "99"}};
  /*int j = 0;
  for (int i = 0; i < LSA.length; i++){
    if(LSA[i+1][0].equals(LSA[i][0])){
      j = i + 1;
      continue;
    }
    
    n.adjacencies = new Edge[]{
      new Edge(LSA[i][1], LSA[i][2]),
      
      /*while (LSA[i+1][0].equals(LSA[i][0])){
        new Edge(LSA[i+1][1], Integer.parseInt(LSA[i+1][2])),
        i++;
      }
    };
  }*/
  Node[] nodes = {R1,R2,R3,R4,R5};
  int i = 0;
  int j = 0;
  while(i < LSA.length){
    j = i;
    if(j+1 == LSA.length){
      Node n = nodes[Character.getNumericValue(LSA[j][0].charAt(1)) - 1];
      Node x = nodes[Character.getNumericValue(LSA[j][1].charAt(1)) - 1];
      n.adjacencies = new Edge[]{
        new Edge(x, Integer.parseInt(LSA[j][2]))
      };
      break;
    }
    while(LSA[j+1][0].equals(LSA[j][0])){
      j++;
    }
    Node n1 = nodes[Character.getNumericValue(LSA[i][0].charAt(1)) - 1];
    n1.adjacencies = new Edge[j-i+1];
    int k = 0;
    int m = i;
       while(k < j-i+1){
         Node x1 = nodes[Character.getNumericValue(LSA[m][1].charAt(1)) - 1];
         n1.adjacencies[k] = new Edge(x1,Integer.parseInt(LSA[m][2]));
          k++;
          m++;
       }    
    i = j + 1;
  }
  
  //initialize the edges
   /*R1.adjacencies = new Edge[]{
   new Edge(R2,75),
   new Edge(R3,75)
  };

  R2.adjacencies = new Edge[]{
   new Edge(R1,75),
   new Edge(R3,71),
   new Edge(R4, 20)
  };

  R3.adjacencies = new Edge[]{
   new Edge(R1,75),
   new Edge(R2,71),
   new Edge(R5,99)
  };

  R4.adjacencies = new Edge[]{
   new Edge(R2,20),
   new Edge(R5,55)
  };

  R5.adjacencies = new Edge[]{
   new Edge(R4,55),
   new Edge(R3,99)
  };*/

  

  //******compute paths******
  computePaths(R1);

  //print shortest paths
  
  for(Node n: nodes){
   System.out.println("Distance to " + 
    n + ": " + n.shortestDistance);
    List<Node> path = getShortestPathTo(n);
    System.out.println("Path: " + path);
  }

  /*List<Node> path = getShortestPathTo(R5);
  System.out.println("Path: " + path);
  
  List<Node> path5 = getShortestPathTo(R1);
  System.out.println("Path: " + path5);*/
 }
}

//define Node
class Node implements Comparable<Node>{
 
 public final String value;
 public Edge[] adjacencies;
 public double shortestDistance = Double.POSITIVE_INFINITY;
 public Node parent;

 public Node(String val){
  value = val;
 }

 public String toString(){
   return value;
 }

 public int compareTo(Node other){
  return Double.compare(shortestDistance, other.shortestDistance);
 }
}

//define Edge
class Edge{
 public final Node target;
 public final double weight;
 public Edge(Node targetNode, double weightVal){
  target = targetNode;
  weight = weightVal;
 }
}