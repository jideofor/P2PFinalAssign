/*
 * This class represents Acknowledgment messages
 */

public class Acknowledgement_Message {
 final String type="ACK";
 int node_id;
 final String ip_address="localhost";
 
 
 public Acknowledgement_Message(int node_id){
	 this.node_id=node_id;
 }
 
 public int getNodeId(){
	return node_id;
	 
 }
}
