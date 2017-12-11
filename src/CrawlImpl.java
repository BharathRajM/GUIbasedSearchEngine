import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;
//for crawling-
import java.util.regex.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class CrawlImpl implements CrawlInterface
{
	private static Connection connection = null;
	private static String jdbcurl="jdbc:h2:tcp://localhost/~/test";
	private static String jdbcusername="sa";
	private static String jdbcpassword="";
	String htmlCode;
	static String classusername="";
	static boolean  flag=false;
	static 
	{
		try 
		{
			Class.forName("org.h2.Driver");
			connection = DriverManager.getConnection(jdbcurl,jdbcusername,jdbcpassword);

		} 
		catch (ClassNotFoundException | SQLException e) 
		{
			e.printStackTrace();
		}
	}
	public boolean checkUser(String username,String password) throws SQLException
	{	//check the username and password on the database(LOGIN TABLE)
		//if matches then
		
		String query="select * from login where username='"+username+"' and password='"+password+"'";
		System.out.println(query);
		Statement stmt = connection.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		if(rs.next())
		{
			System.out.println("rs.next is executed:pointing to the first row now!");
			String role=rs.getString(4);
			System.out.println(role+"This is the role");
			if(role.equals("admin"))
			{
				AdminFront af = new AdminFront();
				af.main(null);
				
				return true;
			}
			else if(role.equals("user"))
			{
				classusername=username;
				System.out.println("Username = "+username+",Classusername ="+classusername);
				UserFront uf = new UserFront();
				uf.main(null);
				return true;
			}
		}
		
		//else return false
		return false;
	}
	public  Vector<Vector> searchResult(String searchKey) throws SQLException
	{
		System.out.println("Classusername = " + classusername);
		//System.out.println("username="+username);
		String query="SELECT I.TITLE,I.LINK FROM INDEX I,LINKCOUNT LC WHERE I.LINK=LC.LINK AND I.TITLE LIKE '%"+searchKey.toUpperCase()+"%' ORDER BY COUNT DESC";
		System.out.println(query);
		Statement stmt = connection.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		
		Vector<Vector> rowdata = new Vector<>();
	    while (rs.next())
	    {
	        Vector<String> rows = new Vector<>();
	       	rows.add(rs.getString(1));
	       	rows.add(rs.getString(2));
	       	rowdata.add(rows);
	    }
	    //time to insert into SEARCHHISTORY
	    String resultFound="No";
	    int noOfResults=0;
	    if(!rowdata.isEmpty())
	    {
	    	noOfResults = rowdata.size();
	    	System.out.println("No of Results = " +noOfResults);
	    	resultFound="Yes";
	    }	    
	    insertHistory(searchKey,resultFound,noOfResults,classusername);   //String searchkey,String resultFound,int noOfResults,String username
	    return rowdata;
	    
	}
	
	
	public String getTitle(String input) {
		String title="";
		
		System.out.println("get title called");
		String theTitleRegex=Pattern.quote("<title>")+ "(.*?)" + Pattern.quote("</title>");
		Pattern checkTitleRegex = Pattern.compile(theTitleRegex);
		Matcher matcher = checkTitleRegex.matcher(input);
		while(matcher.find())
		{
			 title=matcher.group(1);
		}
		return title;
	}
	
	
	
	
	public Vector<Vector> titleFetcher(ArrayList<String> cleanedUrls) 
	{
		
		System.out.println("title fetcher called");
		String subHtmlCode="";
		Vector<Vector> rowData=new Vector<>();
		
		//for(int j=0;j<10;j++) 
		for(String subH:cleanedUrls)
		{
			System.out.println(subH);
			//Check if the extension is *.aspx , *.js, *.css
			if(subH.contains(".css"))
				System.out.println("CSS FOUND!");
			else if(subH.contains(".js"))
				System.out.println("JS FOUND!");
			else if(subH.contains(".aspx"))
				System.out.println("ASPX FOUND!");
			else
			{
				//String subH=cleanedUrls.get(j);
				try 
				{	
					//url = new URL("https://www.google.com");   /////check
					URL url =new URL(subH);
					URLConnection urlcon = url.openConnection();
					urlcon.addRequestProperty("User-Agent","Chrome/62.0.3202.94");
					InputStream stream=urlcon.getInputStream();
					int i;
					while((i=stream.read())!=-1)
					{
						subHtmlCode=subHtmlCode+(char)i;
					}	
					//System.out.println(htmlCode);   //check2
				} 
				catch (IOException e) 
				{
					System.out.println("IOxception caught");
				//	e.printStackTrace();
				}		
				Vector<String> row=new Vector<>();
				String subTitle=getTitle(subHtmlCode).toUpperCase();
				row.add(subTitle);
				row.add(subH);
				if(subTitle!="")
				{
					System.out.println(row);
					rowData.add(row);
					try 
					{
//<<<<<<<<<<<<<<<<<<<<<<<<<call to jdbc to insert the results>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
						insertLink(subTitle,subH);
					}
					catch (SQLException e) 
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				subTitle="";    //because it would store the same title to the next link if the next link didn't have title	
				subHtmlCode="";
				subH="";
			}
		}
		
		return rowData;
	}
	public Vector<Vector> crawlStart(String startURL) throws MalformedURLException
	{ 	//CRAWL METHODS HERE

		//____________TO GET THE HTML STRING_____________________________________
		try 
		{
			//url = new URL("https://www.google.com");   /////check
			URL url =new URL(startURL);
			
			htmlCode="";
			URLConnection urlcon = url.openConnection();
			
			//added user agent
			System.out.println("start");
			urlcon.addRequestProperty("User-Agent","Chrome/62.0.3202.94"); //>>>>check if reequired or not

			InputStream stream=urlcon.getInputStream();
			int i;
			while((i=stream.read())!=-1)
			{
				htmlCode=htmlCode+(char)i;
			}
			//System.out.println(htmlCode);   //check2
		} 
		catch (IOException e) 
		{ 	return null;
		//	e.printStackTrace();
		}	
		
		System.out.println("crawl start called");
		String title=getTitle(htmlCode);
		//SHOULD ADD <<  matcher.group(1)    >>  TO THE INDEX TABLE(TITLE)
		ArrayList<String> listOfCleanUrl = regexChecker(htmlCode);
		
		return titleFetcher(listOfCleanUrl);
				
	}
		
	public static ArrayList<String> regexChecker(String st2checkin)
	{
		System.out.println("regex checker called");
		ArrayList<String> urlList = new ArrayList<String>();
		String theHrefRegex="href=\"([^\"]*)\"";
		Pattern checkHrefRegex= Pattern.compile(theHrefRegex);
		Matcher regexMatcher = checkHrefRegex.matcher(st2checkin);
		int count = 1;
		while(regexMatcher.find())	
		{
			System.out.println(count);
			
			if(regexMatcher.group().length()!=0)
			{
				count++;
				System.out.println(regexMatcher.group().trim());
				
				if(cleanedURL(regexMatcher.group().trim())==null)
				{
					System.out.println("just go");
				}
				else
				{
					urlList.add(cleanedURL(regexMatcher.group().trim()));
				}
				
			}
			//	System.out.println("Starting Index:"+regexMatcher.start());
			//	System.out.println("End Index" + regexMatcher.end());
		}
		
		
		
		return urlList;	
	}
	
	public static String cleanedURL(String rawurl)
	{
		System.out.println("cleaned url");
		int last;
		String url = rawurl;
		//	System.out.println(url);
			if(url.contains("http")||url.contains("https"))
			{
				if(url.contains("href=\""))
				{
					url=url.replace("href=\"", "");
					if(url.contains("/url?q="))
					{	
						url=url.replace("/url?q=", "");
					}
					last = url.lastIndexOf("\"");
					//System.out.println(last);
					url=url.substring(0,last);
					System.out.println(url);
					return url;
				}
			}
			
			return null;
	}

	@Override
	public Vector<String> getColNames() {
		// TODO Auto-generated method stub
		Vector<String> colNames=new Vector<>();
		colNames.add("Title");
		colNames.add("URL");
		return colNames;
	}
	
	public void insertLink(String title,String link) throws SQLException
	{
		try
		{
			String queryIndex = "INSERT INTO INDEX VALUES (NULL,'"+title+"','"+link+"')";
			Statement statement = connection.createStatement();
			String queryLinkCount = "INSERT INTO LINKCOUNT VALUES ('"+link+"',0)";
			int i = statement.executeUpdate(queryIndex);
			int j = statement.executeUpdate(queryLinkCount);
			
		}
		catch(org.h2.jdbc.JdbcSQLException e)
		{
			System.out.println("PRIMARY KEY VIOLATION!");
			String callstoredprocedure = "SELECT MYFUNCTION('"+link+"')";
			Statement statement = connection.createStatement();
			statement.execute(callstoredprocedure);
		}
	}
	
	public String signUp(String username,String password) throws SQLException
	{
		try 
		{
			System.out.println("Entering Signup()");
			String querySignUp = "INSERT INTO LOGIN VALUES (NULL,'"+username+"','"+password+"','user')";
			Statement statement;
			statement = connection.createStatement();
			statement.executeUpdate(querySignUp);
			return "User Created";
		} 
		catch (org.h2.jdbc.JdbcSQLException e) 
		{
			// TODO Auto-generated catch block
			System.out.println("User already exists!");
			e.printStackTrace();
			
			return "User already exists";
		}
		
	}
	
	public void giveFeedback(String feedback)
	{
		try 
		{
			String queryFeedback = "INSERT INTO FEEDBACK VALUES (NULL,'"+feedback+"')";
			Statement statement;
			statement = connection.createStatement();
			statement.executeUpdate(queryFeedback);
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	public Vector<Vector> viewFeedback()
	{
		Vector<Vector> rowAllFeedbacks = new Vector<>();
		System.out.println("Entering Signup()");
		try 
		{
			String queryFeedback = "SELECT * FROM FEEDBACK";
			Statement statement;
			statement = connection.createStatement();
			ResultSet rs=statement.executeQuery(queryFeedback);
			while (rs.next())
		    {
				Vector<String> feedbacks = new Vector<>();
		        feedbacks.add(rs.getString(1));
		        feedbacks.add(rs.getString(2));
		        rowAllFeedbacks.add(feedbacks);
		    }
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		return rowAllFeedbacks;
	}
	//to insert in history table to help the admin
	public void insertHistory(String searchkey,String resultFound,int noOfResults,String username)
	{
		try
		{
			String datetime;
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date d =new Date();
			datetime=dateFormat.format(d);
			System.out.println(datetime);
			String queryinsertHistory="INSERT INTO SEARCHHISTORY VALUES (NULL,'"+searchkey+"','"+resultFound+"',"+noOfResults+",'"+username+"','"+datetime+"')";
			Statement statement=connection.createStatement();
			statement.executeUpdate(queryinsertHistory);
			System.out.println("Inserted into SEARCHHISTORY");
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public Vector<Vector> viewSearchHistory()
	{
		Vector<Vector> rowAllSearchHistory = new Vector<>();
		System.out.println("Entering Signup()");
		try 
		{
			String queryFeedback = "SELECT * FROM SEARCHHISTORY";
			Statement statement;
			statement = connection.createStatement();
			ResultSet rs=statement.executeQuery(queryFeedback);
			while (rs.next())
		    {
				Vector<String> historyrow = new Vector<>();
		        historyrow.add(rs.getString(1));
		        historyrow.add(rs.getString(2));
		        historyrow.add(rs.getString(3));
		        historyrow.add(rs.getString(4));
		        historyrow.add(rs.getString(5));
		        historyrow.add(rs.getString(6));
		        rowAllSearchHistory.add(historyrow);
		    }
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		return rowAllSearchHistory;
	}
	
	public Vector<String> getSearchHistoryColNames() {
		// TODO Auto-generated method stub
		Vector<String> colNames=new Vector<>();
		colNames.add("Sl no.");
		colNames.add("Search Key");
		colNames.add("Result Found");
		colNames.add("No. of Results");
		colNames.add("Searched by");
		colNames.add("TimeStamp");
		
		return colNames;
	}
	
/*	public void userClickUpdate(String url)
	{
		String queryClickUpdate="UPDATE LINKCOUNT SET CLICKS=CLICKS+1 WHERE LINK='"+url+"'";
		Statement statement;
		try 
		{
			statement = connection.createStatement();
			statement.executeUpdate(queryClickUpdate);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}*/
	
}
