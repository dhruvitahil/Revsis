/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *
 * @author sbmpc.student
 */
package javaapplication2;
 
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

public class JavaApplication2 
{
    public static void main(String[] args) 
    { 
        try
        {              
            Connection conn = DriverManager.getConnection("jdbc:sqlserver://192.168.43.71\\SQLEXPRESS;\" +\"databaseName=revsis;user=sa;password=pass@123;");            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            NLP.init(); 
            String array[]={"http://feeds.reuters.com/reuters/INBollywood","http://feeds.reuters.com/reuters/INEntertainmentNews","http://feeds.reuters.com/reuters/INsportsNews","http://feeds.reuters.com/reuters/INhealth","http://feeds.reuters.com/reuters/INtechnologyNews","http://feeds.reuters.com/reuters/INcricketNews","http://feeds.reuters.com/reuters/INtopNews","http://feeds.reuters.com/reuters/INbusinessNews"};
            String store[]={"Bollywood.rss","Entertainement.rss","Sports.rss","Health.rss","Technology.rss","Cricket.rss","TopNews.rss","Business.rss"};
            for(int i=0;i<8;i++)
            {
                RSSFeedParser parser = new RSSFeedParser(array[i]);
                //int i=1;
                //RSSFeedParser parser = new RSSFeedParser("http://feeds.reuters.com/reuters/INEntertainmentNews"); 
                Feed feed = parser.readFeed();
                for (FeedMessage message : feed.getMessages()) 
                {
                    //RSSFeedWriter writer = new RSSFeedWriter(feed, "Entertainement.rss");
                    RSSFeedWriter writer = new RSSFeedWriter(feed, store[i]);
                    Statement stmt = conn.createStatement();
                    System.out.println(message.getDescription() +"\r\n");
                    String gid = message.getGuid();
                    String title = message.getTitle();
                    String description = message.getDescription();
                    //description= String.replace(description, "'", "");
                    title = (title.replace("'", ""));
                    description = (description.replace("'", ""));
                    System.out.println(  NLP.computeSentiment(message.getDescription()));
                    int result=NLP.computeSentiment(message.getDescription());
                    String sql1 = "SELECT * FROM [revsis].[dbo].[TBLSentimentMaster]  WHERE gid = '"+gid +"'";
                    ResultSet rs = stmt.executeQuery(sql1) ;
                    if(rs.next()) 
                    {
                        System.out.println("not Adding") ;
                    }
                    else
                    { 
                        String sql = "INSERT INTO [revsis].[dbo].[TBLSentimentMaster] ([CategoryId],[SentimentScore],[gid],[Title],[Descrpition]) VALUES (" +i+","+result+",'"+ gid +"','"+ title +"','"+ description +"')";
                        stmt.executeUpdate(sql);
                    }
                    rs.close();
                    stmt.close();
                    
                }
            }
            conn.close();
       
        }
        catch(Exception ex)
        {   
            System.out.println(ex);
        }
    }
}
         /*     //JSON parser object to parse read file
        try{
        JSONParser jsonParser = new JSONParser();
         NLP.init(); 
          
        try (FileReader reader = new FileReader("employee.json"))
        {
            //Read JSON file
            Object obj = jsonParser.parse(reader);
 
            JSONArray employeeList = (JSONArray) obj;
            System.out.println(employeeList);
             
            //Iterate over employee array
            employeeList.forEach( emp -> parseEmployeeObject( (JSONObject) emp ) );
           
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
        catch(Exception e){}
    }
    private static void parseEmployeeObject(JSONObject employee)
    {
        try{
        //Get employee object within list
        JSONObject employeeObject = (JSONObject) employee.get("reviewer");
                    Connection conn = DriverManager.getConnection("jdbc:sqlserver://192.168.0.26\\SQLEXPRESS;\" +\"databaseName=revsis;user=sa;password=pass@123;");            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

        Statement stmt = conn.createStatement();
        //Get employee first name
        String firstName = (String) employeeObject.get("displayName");   
        System.out.println(firstName);
         String comment = (String) employeeObject.get("comment");   
        System.out.println(comment);
        comment = (comment.replace("'", ""));
        int result=NLP.computeSentiment(comment);
         System.out.println( result);
         String sql = "INSERT INTO [revsis].[dbo].[reviews] ([name],[comment],[SentimentScore]) VALUES ('"+ firstName +"','"+ comment +"',"+result+")";
           stmt.executeUpdate(sql);
    }
        catch(Exception e){}
    }

}
    */
