import java.util.List;



public class Search_Response_Message {
 final String type="SEARCH_RESPONSE";
// String word; //target word to search for
 int node_id; //target node id
 int sender_id; // a non negative number of the message originator
 List<Url2Rank> response;
 
 public Search_Response_Message(int sender_id, int node_id, List<Url2Rank> response){
	// this.word=word;
	 this.node_id=node_id;
	 this.sender_id=sender_id;
	 this.response=response;
 }
 
 //public String getWord(){
//	 return word;
 //}
 public int getNodeId(){
	 return node_id;
 }
 
 public int getSenderId(){
	 return sender_id;
 }
 public List<Url2Rank> getResponse(){
	return response;
	 
 }
}
