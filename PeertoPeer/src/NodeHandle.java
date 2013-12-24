/*
 * This is the smalles bit of information that can be stored in a routing table
 * It contains the vital information that needs to be stored
 * 
 */

public class NodeHandle {
 String ipaddress;
 int userID;
 final static String IPADDRESS="localhost"; 
 
 public NodeHandle(int userID){
	this.ipaddress=IPADDRESS;
	this.userID=userID;
	 
 }
 

@Override
 public boolean equals(Object obj) {
     if (obj == null) {
         return false;
     }
     if (getClass() != obj.getClass()) {
         return false;
     }
     final NodeHandle other = (NodeHandle) obj;
     if (this.userID != other.userID) {
         return false;
     }
    
     return true;
 }



public int getuserID(){
	return userID;
}


}