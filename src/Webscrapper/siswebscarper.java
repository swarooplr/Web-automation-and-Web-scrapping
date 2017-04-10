package Webscrapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Queue;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import Data_managment.Merger;
import Information_and_links_provider.Info;

public class siswebscarper {
     
	HashMap<String,String> requests =new HashMap<String,String>();
	WebDriver driver=new ChromeDriver();
	
	JSONObject detailsofbranchrequest=new JSONObject();
	JSONObject branchrequestsprocessed=new JSONObject();
	
	
	
	siswebscarper(HashMap<String,String> requests)
	{
		this.requests=requests;
		System.out.println(requests.size());
		
	    
	    
	    
	}
	
	
	JSONObject webscarper()
	{
		driver.get(Info.Msritsislink);
		
		boolean status=true;
		
		for(HashMap.Entry<String,String> req :requests.entrySet() )
		{
			JSONObject student=new JSONObject();
			status=true;
			
			driver.findElement(By.id("username")).sendKeys(req.getKey());
			driver.findElement(By.id("password")).sendKeys(req.getValue());
			driver.findElement(By.name("submit")).click();
			
			long st=System.currentTimeMillis();
			
			try{
				
				int i;
				Integer j = null;
				String eachsubject;
				String value;
				JSONArray all_subject=new JSONArray();
				
				Queue<String> subjectlinks=new PriorityQueue<String>();
				
				String links;
				for(i=1;i<=10;i++)
				{
					try{
					eachsubject="//*[@id=\"sub-container\"]/div/div[3]/table["+j.valueOf(i).toString()+"]/tbody/tr/td[2]/div/a[1]";
					links=driver.findElement(By.xpath(eachsubject)).getAttribute("href");
					subjectlinks.add(links);
					}
					catch(Exception e){break;}
				}
				
				
				while(!subjectlinks.isEmpty())
				{
					try{
						
					
				    driver.get(subjectlinks.poll());		
					
					driver.getPageSource();
					}
					catch(Exception e)
					{
						break;
					}
					
					JSONObject subject =new JSONObject();
					JSONObject attendance =new JSONObject();
					JSONArray  attendancearr =new JSONArray();
					
					
				    
					value=driver.findElement(By.cssSelector("#sub-container > div > div:nth-child(3) > table.dash_even_row > tbody > tr > td:nth-child(1) > table > tbody > tr:nth-child(1) > td:nth-child(2) > div")).getText();          
					subject.put("name", value);
					
					value=driver.findElement(By.cssSelector("#sub-container > div > div:nth-child(3) > table.dash_even_row > tbody > tr > td:nth-child(1) > table > tbody > tr:nth-child(1) > td:nth-child(1) > div")).getText();          
					subject.put("code", value);
					
					value=driver.findElement(By.cssSelector("#sub-container > div > div:nth-child(3) > table.dash_even_row > tbody > tr > td:nth-child(4) > div > a:nth-child(1)")).getText();          
					subject.put("cie", value);
					
					
					value=driver.findElement(By.cssSelector("#sub-container > div > div:nth-child(3) > table.dash_even_row > tbody > tr > td:nth-child(2) > div > a:nth-child(1)")).getText();          
					attendance.put("percentage", value);
					
					
					value=driver.findElement(By.cssSelector("#sub-container > div > div:nth-child(3) > table:nth-child(5) > tbody > tr:nth-child(1) > td > table > tbody > tr:nth-child(1) > td > table > tbody > tr > td > div > div.progress-bar.progress-bar-success")).getText();          
					attendance.put("attended", value);
					
					
					value=driver.findElement(By.cssSelector("#sub-container > div > div:nth-child(3) > table:nth-child(5) > tbody > tr:nth-child(1) > td > table > tbody > tr:nth-child(1) > td > table > tbody > tr > td > div > div.progress-bar.progress-bar-danger")).getText();          
					attendance.put("absent", value);
					
					value=driver.findElement(By.cssSelector("#sub-container > div > div:nth-child(3) > table:nth-child(5) > tbody > tr:nth-child(1) > td > table > tbody > tr:nth-child(1) > td > table > tbody > tr > td > div > div.emptydiv > h4")).getText();          
					attendance.put("remaining", value);
					
					
					
					
			       
			       attendancearr.put(attendance);
			       subject.put("attendance",attendancearr);
			       
			       all_subject.put(subject);
					
				}
				
				String name=driver.findElement(By.cssSelector("#sub-container > div > div:nth-child(2) > div:nth-child(2)")).getText();
				
				student.put("name",name );
				student.put("dob",req.getValue());
				student.put("usn", req.getKey());
				student.put("courses", all_subject);
				
				
				
				
			}
			catch(Exception e)
			{
				status=false;
			}
			
			if(status)
			{    
			try {
				
				detailsofbranchrequest.put(req.getKey(),student.toString());
				branchrequestsprocessed.put(req.getKey(), req.getValue());
				System.out.println(branchrequestsprocessed.toString()+"--yo");
			} catch (JSONException e) {
				
			}
			driver.get(Info.Msritsislink);
			driver.findElement(By.cssSelector("#cssmenu > ul > li:nth-child(8) > a")).click();
			}
			
			long en=System.currentTimeMillis();
			System.out.println(en-st);
		}
		
		Merger.mergeJSONonbjectsSISdata(detailsofbranchrequest);
		Merger.mergeJSONonbjectsRequestsprocessed( branchrequestsprocessed);
		
		
		return detailsofbranchrequest;
	}
	
	
	
	
	
	
}
