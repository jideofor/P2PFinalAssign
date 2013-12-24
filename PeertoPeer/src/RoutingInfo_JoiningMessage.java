/*
 * This class represents routing info to a joining message from a gateway node
 */
public class RoutingInfo_JoiningMessage {
	String type="ROUTINGINFO_TO_JOINING_NODE";
	   int joiningnode;// a non-negative number of order 2'^32^', of the gateway node
	   int sender_id;// a non-negative number of order 2'^32^', indicating the target node (and also the id of the joining node).
	  final String ipaddress="localhost";
	   RoutingTable route_table;
	   
	   
	   public RoutingInfo_JoiningMessage(int joiningnode, int sender_id, RoutingTable route_table){
		   this.joiningnode=joiningnode;
		   this.sender_id=sender_id;
		   this.route_table= new RoutingTable(7);
	   }
	   
	   public int getJoiningNode_id(){
		return joiningnode;
		   
	   }
	   public int getSenderId(){
		return sender_id;
		   
	   }
	   public RoutingTable getRoutingTable(){
		return route_table;
		   
	   }
}
