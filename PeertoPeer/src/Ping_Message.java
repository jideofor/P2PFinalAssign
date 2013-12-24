

public class Ping_Message {
 final String type="PING";
 int target_id; //identifying the suspected dead node.
 int sender_id; //identifying the originator    of the ping (does not change)
 final String ip_address="localhost";
 
 public Ping_Message(int target_id, int sender_id){
	 this.target_id= target_id;
	 this.sender_id=sender_id;
 }
 
}
