

public class Url2Rank{
 
  String url;
  int rank;
  
  public Url2Rank(String url){
	  rank=0;
	 this.url=url;
	  updateRanksObject();
  }

  public void  updateRanksObject(){
	 rank= rank +1;
  }


public int getRank(){

	return rank;
	
}


public String getUrl(){
  return url;	
}

@Override
public boolean equals(Object obj) {
    if (obj == null) {
        return false;
    }
    if (getClass() != obj.getClass()) {
        return false;
    }
    final Url2Rank other = (Url2Rank) obj;
    if (this.url != other.url) {
        return false;
    }
   
    return true;
}



}