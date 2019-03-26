import java.util.PriorityQueue;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class Dijkstra{

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

  Node n1 = new Node("R1");
  Node n2 = new Node("R2");
  Node n3 = new Node("R3");
  Node n4 = new Node("R4");
  Node n5 = new Node("R5");

  //initialize the edges
  n1.adjacencies = new Edge[]{
   new Edge(n2,75),
   new Edge(n3,1400)
  };

  n2.adjacencies = new Edge[]{
   new Edge(n1,75),
   new Edge(n3,71),
   new Edge(n4, 20)
  };

  n3.adjacencies = new Edge[]{
   new Edge(n1,1400),
   new Edge(n2,71),
   new Edge(n5,99)
  };

  n4.adjacencies = new Edge[]{
   new Edge(n2,20),
   new Edge(n5,55)
  };

  n5.adjacencies = new Edge[]{
   new Edge(n4,55),
   new Edge(n3,99)
  };


  Node[] nodes = {n1,n2,n3,n4,n5};

  //******compute paths******
  computePaths(n1);

  //print shortest paths
  /*
  for(Node n: nodes){
   System.out.println("Distance to " + 
    n + ": " + n.shortestDistance);
      List<Node> path = getShortestPathTo(n);
      System.out.println("Path: " + path);
  }*/

  List<Node> path = getShortestPathTo(n5);
  System.out.println("Path: " + path);
  
  List<Node> path3 = getShortestPathTo(n3);
  System.out.println("Path: " + path3);
  
  List<Node> path4 = getShortestPathTo(n2);
  System.out.println("Path: " + path4);
  
  List<Node> path2 = getShortestPathTo(n4);
  System.out.println("Path: " + path2);
  
 
  //It only works successfully from one router/node
  //computePaths(n5);
  List<Node> path5 = getShortestPathTo(n1);
  System.out.println("Path: " + path5);

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
