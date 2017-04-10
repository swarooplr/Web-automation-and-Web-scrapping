package Data_managment;

import java.util.HashMap;

import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import Webscrapper.Maindriver;

public class Merger {
	
	public static void mergeJSONonbjectsSISdata(JSONObject branchdata)
	{
		HashMap<String,String> map1=new HashMap<String,String>();
		HashMap<String,String> map2=new HashMap<String,String>();
		
		GsonBuilder builder=new GsonBuilder();
		builder.setPrettyPrinting();
		
		Gson gson=builder.create();
		
		map1=gson.fromJson(Maindriver.alldata.toString(),HashMap.class );
		map2=gson.fromJson(branchdata.toString(),HashMap.class );
		
		map1.putAll(map2);
		
		Maindriver.alldata =new JSONObject(map1);
		System.out.println(Maindriver.alldata.toString()+"---------");
		
	}
	
	public static void mergeJSONonbjectsRequestsprocessed(JSONObject obj2)
	{
		HashMap<String,String> map1=new HashMap<String,String>();
		HashMap<String,String> map2=new HashMap<String,String>();
		
		GsonBuilder builder=new GsonBuilder();
		builder.setPrettyPrinting();
		
		Gson gson=builder.create();
		
		if(!(Maindriver.requestsprocesed==null))
		map1=gson.fromJson(Maindriver.requestsprocesed.toString(),HashMap.class );
		
		map2=gson.fromJson(obj2.toString(),HashMap.class );
		
		map1.putAll(map2);
		
	    Maindriver.requestsprocesed =new JSONObject(map1);
	    
	    System.out.println(Maindriver.requestsprocesed.toString()+"----");
		
	}
	
	public static HashMap<String,String> mergeHashMaps(HashMap<String,String> obj1,HashMap<String,String> obj2)
	{
		
		
		obj1.putAll(obj2);
		
		
		return obj1;
	}
	
	public static  JSONObject mergeJSONObjects(JSONObject obj1,JSONObject obj2)
	{
		
		
		HashMap<String,String> map1=new HashMap<String,String>();
		HashMap<String,String> map2=new HashMap<String,String>();
		
		GsonBuilder builder=new GsonBuilder();
		builder.setPrettyPrinting();
		
		Gson gson=builder.create();
		
		if(!(obj1==null))
		map1=gson.fromJson(obj1.toString(),HashMap.class );
		
		if(!(obj2==null))
		map2=gson.fromJson(obj2.toString(),HashMap.class );
		
		map1.putAll(map2);
		
	   return new JSONObject(map1);
		
		
		
		
		
		
		
	}
}
