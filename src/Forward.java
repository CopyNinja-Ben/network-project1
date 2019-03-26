public class Forward{

  public static void main (String [] args){
    forwardTable("R1", "R2");
    System.out.println (nextHop[0][1]);
    accessRoutingTable("R1");
    forwardTable("R3", "R2");
    forwardTable("R4", "R2");
    forwardTable("R1", "R6");
    System.out.println (nextHop[0][0]);
    System.out.println (nextHop[0][1]);
    System.out.println (nextHop[1][0]);
    System.out.println (nextHop[1][1]);
    System.out.println (nextHop[2][0]);
    System.out.println (nextHop[2][1]);
    System.out.println (nextHop[3][0]);
    System.out.println (nextHop[3][1]);
    System.out.println (nextHop[4][0]);
    System.out.println (nextHop[4][1]);
  }
  
  static String [][] nextHop = new String [5][2];
  
  static void forwardTable(String destinationRouter, String nextRouter){
    for (int i = 0; i < nextHop.length; i++){
      if (nextHop[i][0] != null && nextHop[i][0].equals(destinationRouter)){
        nextHop[i][1] = nextRouter;
        break;
      }
      else if (nextHop[i][0] == null){
        nextHop[i][0] = destinationRouter;
        nextHop[i][1] = nextRouter;
        break;
      }
    }
  }

  static String accessRoutingTable(String destinationRouter){
    for (int i = 0; i < nextHop.length; i++){
      if (nextHop[i][0].equals(destinationRouter)){
        System.out.println(nextHop[i][1]);
        return nextHop[i][1];
      }
    }
  return "";
  }
}
