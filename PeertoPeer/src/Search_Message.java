import java.util.List;



public class Search_Message {
  final String type="SEARCH";
  List<String> word;// The words to search for
  int node_id;// target node id
  int sender_id;// a non-negative number of order 2'^32^', of this message originator
  
  public Search_Message( List<String> word, int node_id, int sender_id){
	  this.word=word;
	  this.node_id=node_id;
	  this.sender_id=sender_id;
	  
  }
  
  public int getNodeId(){
	  return node_id;
  }
  public int getSenderId(){
	return sender_id;
	  
  }
  /*
   * Returns 
   */
  public List<String> getWord(){
	  return word;
  }
}
