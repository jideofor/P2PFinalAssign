

public class Leaving_Network_Message {
 final String type= "LEAVING_NETWORK";
 int node_id; // a non-negative number of order 2'^32^' identifying the leaving node.
 
   public Leaving_Network_Message(int node_id){
	   this.node_id= node_id;
   }
}
