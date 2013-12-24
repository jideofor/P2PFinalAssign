import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;


public class RecevingThreadServer  implements Runnable{
	DatagramSocket socket;
    Node node;
    DatagramPacket receivePacket;
    byte buffer[];
    final static int PORTNUMBER= 8767;
    InetAddress IPAddress;
    
	public RecevingThreadServer(DatagramSocket socket,Node node){
		this.socket = socket;
		this.node=node;
		
		
		 try {
				IPAddress = InetAddress.getByName("localhost");
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	@Override
	public void run() {
		while(true){
			try {
				System.out.println("This should never endddddd");
				buffer= new byte[1400];
				receivePacket = new DatagramPacket(buffer, buffer.length,IPAddress,PORTNUMBER);
				socket.receive(receivePacket);
				System.out.println( node.identifier + "Just snifffed stuff on the network" );
			//	System.out.println(identifier + " has just received a message");
				new Thread( new MessageReceiverHandler(node,receivePacket)).start(); //spins out a new thread to deal with the request
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			}
		
	}

}
