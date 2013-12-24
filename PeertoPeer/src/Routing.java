import java.util.Vector;

/*
 * This class is used to route information
 */
public class Routing {

	
	
	public Routing(){
		
	}
	/*
	 * This function calculates the next hop for a message
	 */
	public NodeHandle nextHop(Node node, int joiningid){
		RoutingTable a = node.getRoutingTable();
		Vector<NodeHandle> routesets =a.getroutesets();
	//	System.err.println("Size of routing table " + routesets.size());
		int [] userids= getsortedIds(node);
		int nodehandleusrid=nearestproimityjoiningid(userids,joiningid);
		//System.err.println("The node handle user id is " + nodehandleusrid);
		int index=routesets.indexOf( new NodeHandle(nodehandleusrid));
		
	//	System.err.println("The index is " + index);
		return routesets.get(index);
		
	}
	// sort the ids
	private int[] getsortedIds(Node node){
		RoutingTable a = node.getRoutingTable();
		Vector<NodeHandle> routesets =a.getroutesets();
		
		int num= routesets.size();
		int userids[]= new int[num +1];
		
		for(int i=0;i<num;i++){
			userids[i]=routesets.get(i).getuserID();
		}
		userids[num]=node.getUserId(); // i am adding the id of the node to the userids
		userids=selectionSort(userids); // sort out the number
		
		return userids;
	}
	private int[] selectionSort(int[] data){
		  int lenD = data.length;
		  int j = 0;
		  int tmp = 0;
		  for(int i=0;i<lenD;i++){
		    j = i;
		    for(int k = i;k<lenD;k++){
		      if(data[j]>data[k]){
		        j = k;
		      }
		    }
		    tmp = data[i];
		    data[i] = data[j];
		    data[j] = tmp;
		  }
		  return data;
		}
	/*
	 * This function tells me which of my IDs in my routingtable is my joining id  numberically closest to
	 * 
	 */
	
	private int nearestproimityjoiningid(int [] sortedIDS,int joiningid){
		//System.out.println("The joining id in the search is " + joiningid);
		int nexthopid=0;
		int i=0;
		while(i<sortedIDS.length && joiningid< sortedIDS[i])
			i++;
		
		
		if (i>= sortedIDS.length)
			nexthopid=sortedIDS[sortedIDS.length -1];
		else
			nexthopid=sortedIDS[i];
		
		return nexthopid;
		
	}
	
	/*
	 * This function is used to check is the target id is the same of the picked id for next hop
	 */
	public boolean isnodenearesttojoiningid(Node node, int targetid, int joiningid){
	
	int []userids=	getsortedIds(node);
	int pm= nearestproimityjoiningid(userids,joiningid);
	
	//System.err.println("target id " + targetid + " joiningid " + joiningid);
	return  targetid == pm;
	}
}
