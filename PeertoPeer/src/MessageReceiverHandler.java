import java.io.EOFException;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.List;

import com.google.gson.Gson;


public class MessageReceiverHandler implements Runnable {

 	Node node;
	DatagramPacket request;
	final static int PORTNUMBER= 8767;
	  InetAddress IPAddress;
	private final static int  JOINING_NETWORK_SIMPLIFIED_MESSAGE =1;
	private final static int  JOINING_NETWORK_RELAY_SIMPLIFIED =2;
	private final static int  ACKNOWLEDGEMENT_MESSAGE =3;
	private final static int  INDEX_MESSAGE =4;
	private final static int  LEAVING_NETWORK_MESSAGE =5;
	private final static int  SEARCH_MESSAGE =6;
	private final static int  PING_MESSAGE =7;
	private final static int  ROUTINGINFO_MESSAGE =8;
	private final static int  SEARCH_RESPONSE_MESSAGE =9;
	private final static int  ROUTINGINFO_TO_JOINING_NODE =10;
	
	
	public MessageReceiverHandler(Node node, DatagramPacket request){
		this.node=node;
		this.request=request;
	
	}
	
	
	/*
	 * A helper function which takes a string from a udp packet and gets the type of meesage
	 * it returns the type as an int as this helps in the switch statement
	 */
	private int convertstringtoint(String s){
		
		 String[] temp=s.split(",");
		 String[] temp1=temp[0].split(":");
		 String type =temp1[1].substring(1, temp1[1].length()-1);
		 
		 if (type.equals("JOINING_NETWORK_SIMPLIFIED"))
			 return 1;
		 else if (type.equals("JOINING_NETWORK_RELAY"))
			 return 2;
		
		 else if (type.equals("ACK"))
			 return 3;
		 else if (type.equals("INDEX"))
			 return 4;
		 else if (type.equals("LEAVING_NETWORK"))
			 return 5;
		 else if (type.equals("SEARCH"))
			 return 6;
		 else if (type.equals("PING"))
			 return 7;
		 else if (type.equals("ROUTING_INFO"))
			 return 8;
		 else if (type.equals("SEARCH_RESPONSE"))
			 return 9;
		 else if(type.equals("ROUTINGINFO_TO_JOINING_NODE"))
		     return 10;
		 else
			 return 0;
		
	}
	
	/*
	 * This method is used to receive different kinds of message
	 * It's the key piece in my receive handler
	 */
	public   void sovleMessage(){
		
		String socketDataAsString= new String(request.getData());
		String[] temp=socketDataAsString.split("}");
		socketDataAsString= temp[0] + '}';
		
		int messagetype= convertstringtoint(socketDataAsString);
		byte[] sendData = new byte[1400];
		switch (messagetype)
		{
		case JOINING_NETWORK_SIMPLIFIED_MESSAGE:
			System.out.println(socketDataAsString);
			Gson gson = new Gson();
			Joining_Network_Simplified_Message joinmessage = gson.fromJson(socketDataAsString, Joining_Network_Simplified_Message.class);
		
			if(this.node.getUserId()==joinmessage.getTarget_id()) //checks if the message is meant for me
			{
				 System.out.println("User " + joinmessage.getTarget_id() +" just received a join message from  " + joinmessage.getNode_ID() );
				//this.node.setGateway();//set gateway  to be true. you can be only be a gateway when nodes join to you
				
				/*
				 * If my routing table is not empty, i send out a routing info message back to the sender
				 */
				if(this.node.routes.isEmpty() ==false)//if my routing table is not empty
				{
					
					//create a routing info message
					RoutingInfo_JoiningMessage messagejoiningnode = new RoutingInfo_JoiningMessage(joinmessage.getNode_ID(),this.node.getUserId(),this.node.getRoutingTable());
					 
					 Gson gson1 = new Gson();
						String json1 = gson1.toJson(messagejoiningnode);
						sendData = json1.getBytes();
						
						 try {
							 InetAddress IPAddress = InetAddress.getByName("localhost");
							 DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress,PORTNUMBER);
							DatagramSocket socket = this.node.socket;
							 socket.send(sendPacket);
							
						} catch (SocketException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (UnknownHostException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					
					
					
				}
				
			
				/*
				 * There is another node that I can relay this message to so I would send a 
				 * Joining Network Relay message
				 */
				if(new Routing().isnodenearesttojoiningid(this.node,this.node.getUserId(),joinmessage.getNode_ID())==false){
					NodeHandle ndhndle=	new Routing().nextHop(this.node, joinmessage.getNode_ID());
					Joining_Network_Relay_Simplified joinmessagerelay = new Joining_Network_Relay_Simplified(joinmessage.getNode_ID(),this.node.getUserId(), ndhndle.getuserID());
					Gson gson2 = new Gson();
					String json2 = gson2.toJson(joinmessagerelay);
					sendData = json2.getBytes();
					
					 System.out.println("User " + this.node.getIdentifier() +" is sending out a join network relay message " + ndhndle.getuserID()) ;
					try {
						 InetAddress IPAddress;
						 IPAddress = InetAddress.getByName("localhost");
						 DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, PORTNUMBER);
						 DatagramSocket socket = this.node.socket;
						 socket.send(sendPacket);
						
					} catch (UnknownHostException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SocketException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				
				
				NodeHandle ndh= new NodeHandle(joinmessage.getNode_ID());
				System.out.println("inserting node handle inside gateway  node  " + this.node.identifier);
				this.node.routes.insertSingleNodehandle(ndh); //insert my the new joining node into my routing table
				System.out.println("The size of the routing table of  " +this.node.identifier + " is "  + this.node.routes.getroutesets().size());
				System.out.println("Finished joining");
				//this.node.clearGateway(); //set gateway to fasle
			}
			
			
			
			break;
		case  JOINING_NETWORK_RELAY_SIMPLIFIED:
			 gson = new Gson();
			
			 Joining_Network_Relay_Simplified  jnrelaymessage = gson.fromJson(socketDataAsString,Joining_Network_Relay_Simplified.class ); 
			
			 if(jnrelaymessage.getTargetId()==this.node.getUserId()) //checks if the message is meant for me
				{
				 System.out.println("User " + this.node.getIdentifier() +" just received a join relay message from  " + jnrelaymessage.getTargetId() );
					
				 //send routing info to joining node
					
					//create a routing info message TO BE SENT TO THE GATEWAY NODE
					RoutingInfoMessage routingmessage = new RoutingInfoMessage(jnrelaymessage.getGatewayid(),jnrelaymessage.getNodeId(),this.node.getRoutingTable());
					Gson gson1 = new Gson();
					String json1 = gson1.toJson(routingmessage);
					sendData = json1.getBytes();
					
					 try {
						 InetAddress IPAddress = InetAddress.getByName("localhost");
						 DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length,IPAddress,PORTNUMBER);
						DatagramSocket socket = this.node.socket;
						 socket.send(sendPacket);
						
					} catch (SocketException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (UnknownHostException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
					
					/*
					 * There is another node that I can relay this message to so I would send a 
					 * Joining Network Relay message
					 */
					if(new Routing().isnodenearesttojoiningid(this.node,this.node.getUserId(),jnrelaymessage.getNodeId())==false){
						NodeHandle ndhndle=	new Routing().nextHop(this.node, jnrelaymessage.getNodeId());
						Joining_Network_Relay_Simplified joinmessagerelay = new Joining_Network_Relay_Simplified(jnrelaymessage.getNodeId(),jnrelaymessage.getGatewayid(), ndhndle.getuserID());
						Gson gson2 = new Gson();
						String json2 = gson2.toJson(joinmessagerelay);
						sendData = json2.getBytes();
						
						 System.out.println("User " + this.node.getIdentifier() +" is sending out a join network relay message " + ndhndle.getuserID()) ;
						try {
							 InetAddress IPAddress;
							 IPAddress = InetAddress.getByName("localhost");
							 DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, PORTNUMBER);
							 DatagramSocket socket = this.node.socket;
							 socket.send(sendPacket);
							
						} catch (UnknownHostException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (SocketException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
					
					NodeHandle ndh= new NodeHandle(jnrelaymessage.node_id);
					System.out.println("inserting node handle of joining node inside table during a join network relay");
					this.node.routes.insertSingleNodehandle(ndh); //insert my the new joining node into my routing table
					
					
				}
			System.out.println("Finished JNR");
			break;
		case ACKNOWLEDGEMENT_MESSAGE:
			break;
		case INDEX_MESSAGE :
			gson = new Gson();
			Index_Message indexmessage = gson.fromJson(socketDataAsString, Index_Message.class);
			
			 if(indexmessage.target_id== this.node.getUserId()) //checks if the message is meant for me
			 {
				 System.out.println("User " + this.node.getIdentifier() +" just received an index  message from  " + indexmessage.getSenderId() );
				 this.node.wordstorage.indexword(indexmessage.getKeyword(),indexmessage.getLink());// indexes the word onto the storage
				 
			 }
			break;
		case LEAVING_NETWORK_MESSAGE:
			break;
		case SEARCH_MESSAGE:
			gson = new Gson();
			Search_Message searchmessage = gson.fromJson(socketDataAsString,	Search_Message .class);
			 if( searchmessage.getNodeId()== this.node.getUserId()) //checks if the message is meant for me
			 {
				 System.out.println(this.node.identifier +" just received a request to search word " + searchmessage.getWord());
				 List<Url2Rank>  response= this.node.wordstorage.searchResponse(searchmessage.getWord());
				 gson = new Gson();
				 Search_Response_Message searchresponse = new Search_Response_Message(this.node.getUserId(),searchmessage.getSenderId(), response);
				 String json2 = gson.toJson(searchresponse);
				 sendData = json2.getBytes();
					
				 System.out.println("User " + this.node.getIdentifier() +" is sending out a search response  " ) ;
				try {
					 InetAddress IPAddress;
					 IPAddress = InetAddress.getByName("localhost");
					 DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, PORTNUMBER);
					 DatagramSocket socket = this.node.socket;
					 socket.send(sendPacket);
					
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SocketException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			 }
			
			break;
		case PING_MESSAGE:
			break;
			/*
			 * This is used to receive routing info at the gateway node from nodes which received
			 * Join network relay messages
			 */
		case ROUTINGINFO_MESSAGE: 
			 gson = new Gson();
			 RoutingInfoMessage routingmessage= gson.fromJson(socketDataAsString,  RoutingInfoMessage.class);
			
				
			 if(routingmessage.getNodeId()== this.node.getUserId()) //checks if the message is meant for me
				{
				 System.out.println("User " + this.node.getIdentifier() +" just received a routing info message from  " + routingmessage.getNodeId() + "at the gateway node");
				 this.node.mergerRoutingTable(routingmessage.getRoutingTable());//  merges the return routing table 
				 if(routingmessage.getGateway_id()==routingmessage.getNodeId()){
					 //sending routing info to joining node from gateway
					 RoutingInfo_JoiningMessage messagejoiningnode = new RoutingInfo_JoiningMessage(routingmessage.getNodeId(),this.node.getUserId(),routingmessage.getRoutingTable());
					 
					 Gson gson1 = new Gson();
						String json1 = gson1.toJson(messagejoiningnode);
						sendData = json1.getBytes();
						
						 try {
							 InetAddress IPAddress = InetAddress.getByName("localhost");
							 DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress,PORTNUMBER);
							DatagramSocket socket = this.node.socket;
							 socket.send(sendPacket);
							
						} catch (SocketException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (UnknownHostException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					 
				 }
				 
				 
					System.out.println("Finished routing message "); 
				}
		
			break;
			/*
			 * This is used to receive routing info to a joining node
			 */
		case ROUTINGINFO_TO_JOINING_NODE:
			Gson gson1 = new Gson();
			
			 RoutingInfo_JoiningMessage messagejoiningnode = gson1.fromJson(socketDataAsString,RoutingInfo_JoiningMessage.class);
			 if(messagejoiningnode.getJoiningNode_id()==this.node.getUserId()){
				 this.node.mergerRoutingTable(messagejoiningnode.getRoutingTable());//  merges the return routing table 
			 }
			 break;
			 /*
			  * This is used to receive a search response message
			  */

		default:
		}
	}
	@Override
	public  void run() {
		// TODO Auto-generated method stub
	
		sovleMessage();
		
	}

}
