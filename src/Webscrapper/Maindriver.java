package Webscrapper;

import java.util.HashMap;

import org.json.JSONObject;

import Data_managment.Merger;
import FirebaseHack.FirebaseDataExchanger;
import Information_and_links_provider.Info;

public class Maindriver {

	
	public static JSONObject alldata=new JSONObject();
	public static JSONObject requestsprocesed=new JSONObject();
	public static JSONObject current_users=new JSONObject();
	
	public static void main(String[] args)
	{   
		
		requestsprocesed=null;
		FirebaseDataExchanger f=new FirebaseDataExchanger();
		f.intialize_alldata_and_allusers();
		
		HashMap<String, String>h1=f.getallusers();
		HashMap<String,String>h2=f.getcurrentrequests();
		h1=Merger.mergeHashMaps(h1, h2);
		siswebscarper s=new siswebscarper(h1);
		
		JSONObject j=s.webscarper();
		System.out.println(j.toString());
		
		f.writefile(Info.Uploadingdatafile, alldata,Info.UpateAttendance);
		
		current_users = Merger.mergeJSONObjects(current_users,requestsprocesed);
		f.writefile(Info.RequestsProcessedFilePath, current_users,Info.CurrentUsersLink);
		
		
	}
	
}
