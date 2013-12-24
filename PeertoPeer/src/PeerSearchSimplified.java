import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.util.List;


public interface PeerSearchSimplified {
	public  void init(DatagramSocket udp_socket); // initialise with a udp socket
	  public  void joinNetwork( String identifier, String target_identifier ); //returns network_id, a locally 
	                                       // generated number to identify peer network
	    public boolean leaveNetwork(long network_id); // parameter is previously returned peer network identifier
	    void indexPage(String word,List <String> urls,String targertID); //slightly modified this interface
	   void search(List<String> words,String targetID); // the result return would be displayed on the window
}
