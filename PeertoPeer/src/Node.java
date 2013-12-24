import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.List;

import com.google.gson.Gson;

/*
 * This class represents a pastry node
 */

public class Node implements  PeerSearchSimplified {
  int userid;                 // int identifier
  String identifier;         //string identifier
 public RoutingTable routes;   //used to store routes
  Word_Url_Rank_Storage wordstorage;  //stores words to urls to ranks
  final int ROUTINGTABLEMAXSIZE=15;  //represents the size of the routing table
  DatagramSocket socket;
  DatagramPacket receivePacket;
  final static int PORTNUMBER= 8767;
  InetAddress IPAddress;
  boolean isbootstrapnode;

  byte buffer[];
  
  /*
   * constructor for bootstrapnode
   */
  public Node(String username){
	    
	    this.identifier=username;
	    this.userid= hashCode(username);
	    this.isbootstrapnode=true;
	    createRoutingTable();
	    wordstorage = new   Word_Url_Rank_Storage();
	    try {
			IPAddress = InetAddress.getByName("localhost");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 
	  //request = new DatagramPacket(new byte[1024], 0, 1024);
  }
  /*
   * Constructor for non bootstrap node
   */
  public Node(){
	  isbootstrapnode=false;
	  createRoutingTable();
	 wordstorage= new Word_Url_Rank_Storage();
	    try {
			IPAddress = InetAddress.getByName("localhost");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
  }
  
 
  /*
   * This function is used to get the id of a node
   */
  
  public int getUserId(){
	  return userid;
  }
  public String getIdentifier(){
	  return identifier;
  }
  /*
   * This function returns the routing table of a node
   */
  public void createRoutingTable(){
	  routes= new RoutingTable(ROUTINGTABLEMAXSIZE);
  }
  /*
   * Returns the routing table of the node
   */
  public  RoutingTable getRoutingTable(){
	  return routes;
  }

/*
 * This function is used to merger routing tables
 */
   public void mergerRoutingTable(RoutingTable table){
	   routes.mergeRoutingTable(table);
	
  }
/*
 * Used to turn string identifiers to user ids
 */

public int hashCode(String str) {
	  int hash = 0;
	  for (int i = 0; i < str.length(); i++) {
	    hash = hash * 31 + str.charAt(i);
	  }
	  return Math.abs(hash);
	}



@Override
/*
 * This function initialises a node's socket by accepting a socket and binding it to the port number
 */
public void init(DatagramSocket udp_socket) {
	// TODO Auto-generated method stub
	
	 this.socket= udp_socket;
	 
}
@Override
/*
 * This function is used by a node to Send a joining message to a network
 * A bootstrap node does cannonot need to join a network
 */
public void joinNetwork(String identifier,
		String target_identifier) {
	System.out.println( identifier + " is about to join network");
	// TODO Auto-generated method stub
	 this.userid= hashCode(identifier); //this creates my user id through the string identifier
	 this.identifier= identifier;
	 
	 if(isbootstrapnode==false){ //checks if the node is a bootstrapnode and if so, it does not join
	
		 int targetid= hashCode(target_identifier);

	 Joining_Network_Simplified_Message  joinmessage = new Joining_Network_Simplified_Message(this.userid, targetid );
	 
	
	 byte[] sendData = new byte[1400];
	 Gson gson = new Gson();
	 String json = gson.toJson(joinmessage);
	 try {
		
		 sendData = json.getBytes();
		 DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, PORTNUMBER);
		 this.socket.send(sendPacket);

		
	} catch (UnknownHostException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
     
	}
	 
}
@Override
public boolean leaveNetwork(long network_id) {
	// TODO Auto-generated method stub
	return false;
}
/*
 * (non-Javadoc)
 * @see PeerSearchSimplified#indexPage(java.lang.String, java.util.List, java.lang.String)
 * This function is used to index a page
 * 
 */
@Override
public void indexPage(String word, List <String> urls, String targetID) {
	// TODO Auto-generated method stub
	int targetIdentifier= hashCode(targetID);
	
	Index_Message indexmessage = new Index_Message(targetIdentifier,this.userid,word,urls); //creating an index message to be sent
	
	 byte[] sendData = new byte[1400];
	 Gson gson = new Gson();
	 String json = gson.toJson(indexmessage);
	 try {
		
		 sendData = json.getBytes();
		 System.out.println("About to send an Index message to " + targetID);
		 DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, PORTNUMBER);
		 this.socket.send(sendPacket);

		
	} catch (UnknownHostException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    
}
@Override
public void search( List<String> words , String targetID) {
	// TODO Auto-generated method stub
int targetIdentifier= hashCode(targetID);
	
	Search_Message searchmessage = new Search_Message(words,targetIdentifier,this.userid); //creating an index message to be sent
	
	 byte[] sendData = new byte[1400];
	 Gson gson = new Gson();
	 String json = gson.toJson(searchmessage);
	 try {
		
		 sendData = json.getBytes();
		 System.out.println("About to send a search message to " + targetID);
		 DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, PORTNUMBER);
		 this.socket.send(sendPacket);

		
	} catch (UnknownHostException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    
}
/*
 * This class is used to create the receiving thread to receive messages
 * It spins out a new thread whenever a message is received
 */
public class receivingThread implements Runnable {
	DatagramSocket socket;
    Node node;

  
		public receivingThread(DatagramSocket socket,Node node){
				this.socket = socket;
				this.node=node;
			}
		@Override
		public void run() {
			
			while(true){
				try {
				//	System.out.println("This should never endddddd");
					
					buffer= new byte[1400];
					
					receivePacket = new DatagramPacket(buffer, buffer.length,IPAddress,PORTNUMBER);
					socket.receive(receivePacket);
			   
					new Thread( new MessageReceiverHandler(node,receivePacket)).start(); //spins out a new thread to deal with the request
					
				
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				}

		}

}

/*
* This function is used to receive messages
*/
public   void receivemessage(){

 new Thread(new receivingThread(this.socket,this)).start();
	
}

}



