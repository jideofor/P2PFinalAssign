

public class Joining_Network_Relay_Simplified {
	final String type="JOINING_NETWORK_RELAY";
	int node_id; // a non-negative number of order 2'^32^', indicating the id of the joining node).
	int gateway_id;// a non-negative number of order 2'^32^', of the gateway node
	int target_id;// a non-negative number of order 2'^32^', indicating the target node for this message.
	
	public Joining_Network_Relay_Simplified(int node_id, int gateway_id, int target_id){
		this.gateway_id=gateway_id;
		this.node_id= node_id;
		this.target_id= target_id;
	}


public int getNodeId(){
	return node_id;
	
}
public int getTargetId(){
	return target_id;
	
}
public int getGatewayid(){
	return gateway_id;
	
}
}