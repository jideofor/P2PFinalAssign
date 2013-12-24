

public class Joining_Network_Simplified_Message {
	final String type="JOINING_NETWORK_SIMPLIFIED";
	int node_id; // id of the joining node
	int target_id; //id of the target node 
	final String ipaddress="localhost";
	//String JsonRepresentaion
	//JSONObject jnm= new JSONObject();
	
	Joining_Network_Simplified_Message(int node_id,int target_id){
		this.node_id= node_id;
		this.target_id=target_id;
	//	convertToJson();
	}
	
public int getNode_ID(){
	return node_id;
}

public int getTarget_id(){
	return target_id;
}

}
