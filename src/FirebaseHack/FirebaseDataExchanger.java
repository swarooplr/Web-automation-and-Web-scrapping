/**
 * 
 */
package FirebaseHack;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import Information_and_links_provider.Info;
import Webscrapper.Maindriver;

/**
 * @author swaroop
 *
 */
public class FirebaseDataExchanger {

	WebDriver driver=new ChromeDriver();
	
	public FirebaseDataExchanger()
	{
		gamillogin();
	}
	
	private boolean gamillogin()
	{ 
	   try
	   {	
	   driver.get(Info.Firebasedatabase);
	   driver.findElement(By.xpath("//*[@id=\"Email\"]")).sendKeys(Info.Gmailid);
	   driver.findElement(By.xpath("//*[@id=\"next\"]")).click();
	   Thread.sleep(1000);
	   driver.findElement(By.xpath("//*[@id=\"Passwd\"]")).sendKeys(Info.Gmailpassword);
	   driver.findElement(By.xpath("//*[@id=\"signIn\"]")).click();
	   }
	   catch(Exception e)
	   {
		   return false;
	   }
	   
	   return true;
		
	}
	
	
	
	
	public HashMap<String,String> getallusers()
	{
		driver.get(Info.Allusers);
		
		/*driver.get(driver
				.findElement(By.cssSelector("#menu_container_17 > md-menu-content > md-menu-item:nth-child(1) > a"))
				.getAttribute("href"));*/
		
		HashMap<String, String> allusers=null;
		
		allusers = readfile(Info.AllusersFilePath);
		
		return allusers;
	}
	
	public HashMap<String,String> getcurrentrequests()
	{
		
        driver.get(Info.CurrentRequests);
		
		/*driver.get(driver
				.findElement(By.cssSelector("#menu_container_17 > md-menu-content > md-menu-item:nth-child(1) > a"))
				.getAttribute("href"));*/
		
		HashMap<String, String> allusers=null;
		
		allusers = readfile(Info.CurrentRequestsFilePath);
		
		return allusers;
		
		
		
	}
	
	private HashMap<String,String> readfile(String filename) 
	{   
		GsonBuilder builder =new GsonBuilder();
		builder.setPrettyPrinting();
		
		BufferedReader bufferedReader = null;
		while(true){
		
		
			try {
				bufferedReader = new BufferedReader(
				         new FileReader(filename));
				break;
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				
			}
		
		
		}
		
		Gson gson =builder.create();
		HashMap<String,String> hmap =new HashMap<String,String>();
		
		hmap =gson.fromJson(bufferedReader, HashMap.class);
		
		return hmap;
	}
	
	
	public boolean writefile(String filename,JSONObject alldata,String firebaselink) 
	{   
		driver.get(firebaselink);
		
		
		try{
		    PrintWriter writer = new PrintWriter(filename, "UTF-8");
		    writer.println(alldata.toString());
		    
		    writer.close();
		} catch (IOException e) {
		   // do something
		}
		
		
		
		return true;
	}
	
	public boolean intialize_alldata_and_allusers()
	{  
		driver.get(Info.UpateAttendance);
		HashMap<String,String> sisinfo =new HashMap<String,String>();
		sisinfo=readfile(Info.CurrentAttendanceinfoFilePath);
		
		
		Maindriver.alldata= new JSONObject(sisinfo);		
		
		driver.get(Info.CurrentUsersLink);
        HashMap<String,String> currentusers=new HashMap<String,String>();
        currentusers=readfile(Info.CurrentUsersFilePath);

		
		
		
		
		return true;
	}
	
	
	
}
