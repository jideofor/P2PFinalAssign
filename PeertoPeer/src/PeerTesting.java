import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import com.google.gson.Gson;

public class PeerTesting {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		final int PORTNUMBER=8767;
		DatagramSocket socket = null;
		
		try {
			 socket = new DatagramSocket(PORTNUMBER);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
	
	
	Node node1 = new Node("bootstrap");
	node1.init(socket);
	node1.receivemessage(); //used to set socket to receive messages
	
	Node node2 = new Node();
	node2.init(socket);
	node2.receivemessage();
	node2.joinNetwork( "john","bootstrap");
	
	Node node3 = new Node();
	node3.init(socket);
	node3.receivemessage();
	node3.joinNetwork( "johnson","john");
	
	Node node4 = new Node();
	node4.init(socket);
	node4.receivemessage();
	node4.joinNetwork( "steven","johnson");
	
	
	
	List <String> urls = new ArrayList<String>();
	urls.add("Hello");
	urls.add("Distributed systems");
	//indexing a page
        node2.indexPage("boy", urls, "stephen");
        
        
      List <String> words= new ArrayList<String>();
        words.add("boy");
	
        //searching for a word boy
	node3.search(words "stephen");
	
	
	}

}
