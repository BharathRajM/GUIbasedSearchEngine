import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Vector;

public interface CrawlInterface 
{
	//to check whether the user exists in the database (LOGIN Table)
	public boolean checkUser(String username,String password) throws SQLException;
	
	//to search the entire INDEX(TITLE=searchKey) table and get the search results to the user
	public Vector<Vector> searchResult(String searchKey) throws SQLException;
	
	//to initiate a crawl on a URL given by the admin
	public Vector<Vector> crawlStart(String startURL)throws MalformedURLException;

	public Vector<String> getColNames();
	
	public void insertLink(String title,String link) throws SQLException;

	public String signUp(String username,String password)throws SQLException;
	
	public void giveFeedback(String feedback);
	
	public Vector<Vector> viewFeedback();
	
	public Vector<Vector> viewSearchHistory();
	
	public Vector<String> getSearchHistoryColNames();
	
//	public void userClickUpdate(String url);
}
