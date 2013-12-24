
import java.util.Vector;

/*
 * This class represents a routing table for a node
 */
public class RoutingTable {
 
	
	//private int myNodeId;
	private Vector<NodeHandle> routesets;
	
	public RoutingTable(int size){
		routesets= new Vector<NodeHandle>(size,0);
	}
	
	/*
	 * This function merges a two routing tables together
	 */
	
	public void mergeRoutingTable(RoutingTable newtable){
		
		if(newtable!=null){
			Vector<NodeHandle> routes = newtable.getroutesets();
			for(NodeHandle element:routes){
				if(!routesets.contains(element))
					routesets.add(element);
				
			}
			
			
			
		}
	}
	/*
	 * This function inserts a single node into the routing table
	 */
	
	public void insertSingleNodehandle(NodeHandle nodehandle){
		if(routesets.contains(nodehandle)==false){
			routesets.add(nodehandle);
		}
		
	}
	/*
	 * This function is used to remove a node from the routing table
	 */
	public void deleteNodeHandle(NodeHandle nodehandle){
	
	 routesets.remove(nodehandle);
	}
	/*
	 * This returns the routesets which represent the routing table
	 */
	public Vector<NodeHandle> getroutesets(){
		return routesets;
		
	}
	
	/*
	 * This function is used to get a node handle
	 */
	public NodeHandle getNodeHandle(NodeHandle nhandle){
		
		int index=routesets.indexOf(nhandle);
		return  routesets.elementAt(index);
		
	}
	/*
	 * This function is used to check if a routing table already has this nodehandle in the routing table
	 */
	public boolean doesthisRoutingTableHaveThisNodeHandle(NodeHandle nhandle){
		return routesets.contains(nhandle);
	}
	
	/*
	 * This function is used to check if a routing table is empty
	 */
	public boolean isEmpty(){
		
		if (routesets.size()==0)
			return true;
		else
			return false;
	}
}
