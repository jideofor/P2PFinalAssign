
/*
 * This class 
 */


public class RoutingInfoMessage {
   String type="ROUTING_INFO";
   int gateway_id;// a non-negative number of order 2'^32^', of the gateway node
   int node_id;// a non-negative number of order 2'^32^', indicating the target node (and also the id of the joining node).
  final String ipaddress="localhost";
   RoutingTable route_table;
   
   
   public RoutingInfoMessage(int gateway_id, int node_id, RoutingTable route_table){
	   this.gateway_id=gateway_id;
	   this.node_id=node_id;
	   this.route_table= route_table;
   }
   
   public int getGateway_id(){
	return gateway_id;
	   
   }
   public int getNodeId(){
	return node_id;
	   
   }
   public RoutingTable getRoutingTable(){
	return route_table;
	   
   }
}
