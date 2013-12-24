import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
/*
 * This class is used to store words to its url plus its ranks
 * 
 */

public class Word_Url_Rank_Storage {
	public Map<String,List<Url2Rank>> map;
	 
	 
	 public Word_Url_Rank_Storage(){
		 
		map= new HashMap<String, List<Url2Rank>>();
		 
	 }
	
/*
 * Check my data structure to see if this word has been indexed
 */
	 
public  boolean  containsWord(String word){
	return  map.containsKey(word);
  }


/*
 * Overloaded function 
 * Takes a word to index and a list of String url
 */
public void indexword(String word,List<String> url){
	
	
	for(int i=0; i<url.size();i++){
		indexWordHelper(word,url.get(i));
	}
		
}
/*
 * Takes a word and a url and creates a index in the data structure
 */
private void  indexWordHelper(String word, String url){
	//Url2Rank
	List<Url2Rank> value = new Vector<Url2Rank>();
	
	value.add(new Url2Rank(url));
	if (!containsWord(word)){
		map.put(word,value);
	//	System.out.println("Hello");
	}
	/*
	 * This word already exists in the data sttucture
	 * Its time to find out what url has it & if it matches the imput url
	 * h
	 */
	else
	{
		
	value=	map.get(word);
    Url2Rank a = new Url2Rank(url);
	//System.out.println("Size of list inside " + value.size());
	if(value.contains(a)==false)
	{
	//	System.out.println("Yes I have that word but differnt url");
		value.add(new Url2Rank(url)); // adds a new Url2Rank object to the vector for a particular word
	//   map.put(word, value); //not sure if  i need this 
	}
	/*
	 * This word already has a url for it, lets just increment the rank for that url
	 */
	    else{
	    	System.out.println("hi");
	    	int indexnum=value.indexOf(a);
	    	Url2Rank obj=value.get(indexnum);
	    	obj.updateRanksObject();
	    }
		
	}
	
   }

/*
 * Returns a url to rank based on a list of words
 */
public List<Url2Rank> searchResponse(List<String>word){
	List <Url2Rank> response = new Vector<Url2Rank>();
	
	List<Url2Rank> value;

	for(int j=0;j<word.size();j++){
	if(containsWord(word.get(j))){
		value=map.get(word);
		for(int i=0; i<value.size();i++){
			response.add(value.get(i));
			System.out.println("Response  " + value.get(i).getUrl() + "ranks " + value.get(i).getRank());
		}
		
		
	}
	}
	return response;
}
}
