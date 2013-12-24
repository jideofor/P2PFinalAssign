
import java.util.List;


public class Index_Message {
  final String type="INDEX";
  int target_id;
  int sender_id;
  String keyword;
  List <String> link;
  
  public Index_Message(int target_id,int sender_id,String keyword,List<String> link){
  	
	  this.target_id=target_id;
  	  this.sender_id=sender_id;
  	  this.keyword=keyword;
  	  this.link=link;
  }
  
  public int getTargetId(){
	return target_id;
	  
  }
  public int getSenderId(){
	  return sender_id;
  }
  public String getKeyword(){
	return keyword;
	  
  }
  public List<String> getLink(){
	return link;
	  
  }
       
}
