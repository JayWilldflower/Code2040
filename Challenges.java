/**
  * CODE2040 API Challenge
  * @author Janai Williams
  */

import java.io.*;
import java.net.*;
import java.text.*;
import java.util.*;
import org.json.*;

public class Challenges {

	//My authentication token. 
	public static final String myToken = "7390d5eff1c7bbbc2d95a44dc7a5fbb9";
	
	/**
	   * This is the main method which calls each challenge method.
	   * @param args Unused.
	   * @return Nothing.
	   */
	public static void main(String[] args){
		
		System.out.println("\n-------Registering-------");
		register();
		
		System.out.println("\n-------Reversing String-------");
		reverseString();
		
		System.out.println("\n---------Needle in a Haystack---------");
		needleHaystack();
		
		System.out.println("\n-------------------Prefix-------------------");
		prefix();
		
		System.out.println("\n---------------------Dating Game-----------------------");
		datingGame();
	}
	
	  /**
	 	* This method creates and sends a post request to a specified url.
	 	* @param Payload, is a JSON dictionary.
	 	* @param Endpoint, This is the API url.
	 	* @return Reader, the input stream of the buffered reader to the calling method.
	 	*/
	public static BufferedReader postRequest(JSONObject payload, URL endpoint){

		BufferedReader reader = null;
		
		//Formating and implementing the POST request
		try {
			HttpURLConnection connect = (HttpURLConnection)endpoint.openConnection();
			connect.setDoOutput(true);
			connect.setDoInput(true);
			connect.setRequestProperty("Content-Type", "application/json");
			connect.setRequestProperty("Accept", "applicaton/json");
			connect.setRequestMethod("POST");
			
			OutputStreamWriter writer = new OutputStreamWriter(connect.getOutputStream(), "UTF-8");
			writer.write(payload.toString());
			writer.flush();
			writer.close();
		
			reader = new BufferedReader(new InputStreamReader(connect.getInputStream(), "UTF-8"));
			
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return reader;	 //returning input stream to calling method
	}
	
	/**
	  * This method handles registration.
	  * @param Nothing.
	  * @return Nothing.
	  */
	public static void register(){
		
		try {
			//Creating JSON dictionary and initializing the url object
			JSONObject dictionary = new JSONObject();
			dictionary.put("token", myToken);
			dictionary.put("github","https://github.com/OhHoney34/Code2040.git");
			URL url = new URL("http://challenge.code2040.org/api/register");
			
			//Connecting to API and retrieving response via POST
			BufferedReader stream = postRequest(dictionary, url);
			System.out.println(stream.readLine());
			
			stream.close();
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	  * This method performs the reversal of a string by and 
	  * send the reversed string back to the API endpoint for validation.
	  * @param Nothing.
	  * @return Nothing.
	  * @exception MalformedURLException
	  * @exception JSONException  
	  * @exception IOException
	  */
	public static void reverseString(){
		
		try {
			//Creating JSON dictionary and initializing the url object
			JSONObject dictionary = new JSONObject();
			dictionary.put("token", myToken);
			URL url = new URL("http://challenge.code2040.org/api/reverse");
			
			//Connecting to API and retrieving string to reverse
			BufferedReader stream = postRequest(dictionary, url);
			String forward = stream.readLine();
			
			System.out.println("The string: " + forward);
			
			StringBuffer buffer = new StringBuffer(forward);
			String reverse = buffer.reverse().toString();
			
			
			dictionary.put("token", myToken);
			dictionary.put("string", reverse);
			url = new URL("http://challenge.code2040.org/api/reverse/validate");
			
			//Sending reversed string back to API endpoint to validate.
			stream = postRequest(dictionary, url);
			
			System.out.println("The reversed string: " + reverse);
			System.out.println("\n" + stream.readLine());	
			
			stream.close();
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	  * This searches for a string inside of an arrayList and 
	  * sends the index of the string to the API endpoint for validation.
	  * @param Nothing.
	  * @return Nothing.
	  * @exception MalformedURLException
	  * @exception JSONException  
	  * @exception IOException
	 ***/
	public static void needleHaystack(){
	
		try {
			//Creating JSON dictionary and initializing the url object
			JSONObject dictionary = new JSONObject();
			dictionary.put("token", myToken);
			URL url = new URL("http://challenge.code2040.org/api/haystack");
				
			//Connecting to API and retrieving string and array of strings
			BufferedReader stream = postRequest(dictionary, url);
			
			JSONObject obj = new JSONObject(stream.readLine());
			String needle = obj.getString("needle");
			JSONArray arr = obj.getJSONArray("haystack");
		
			System.out.println("The needle is: " + needle);
			
			ArrayList<String> list = new ArrayList<String>();
			
			/*storing the JSON Array in an arrayList 
			 while searching for the string within the list.*/
			int index = 0;
			for(int j=0; j< arr.length(); j++){
				
				list.add(arr.get(j).toString());
				Boolean flag = list.contains(needle);
				
				if(flag == true){	
					index = j;
					break;
				}
			}
			 
			System.out.println("The needle loaction is at index: " + index);
			
			//Creating JSON dictionary and initializing the url object
			dictionary.put("token", myToken);
			dictionary.put("needle", index);
			url = new URL("http://challenge.code2040.org/api/haystack/validate");
			
			//Sending the index of the string back to API endpoint to validate.
			stream = postRequest(dictionary, url);
			System.out.println("\n" + stream.readLine());
			
			stream.close();
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * This method searches through an arraylist for strings that do not begin
	 * with the prefix string. The strings that do not begin with the prefix are
	 * stored in a sublist and sent back to the API endpoint for validation. 
	 * @param Nothing.
	 * @return Nothing.
     * @exception MalformedURLException
     * @exception JSONException  
     * @exception IOException
	 */
	public static void prefix(){
		
		try {
			//Creating JSON dictionary and initializing the url object
			JSONObject dictionary = new JSONObject();
			dictionary.put("token", myToken);
			URL url = new URL("http://challenge.code2040.org/api/prefix");
			
			BufferedReader stream = postRequest(dictionary, url);
			
			JSONObject obj = new JSONObject(stream.readLine());
			String prefix = obj.getString("prefix");
			JSONArray arr = obj.getJSONArray("array");
		
			System.out.println("The string prefix is: " + prefix);
			
			ArrayList<String> list = new ArrayList<String>();
			ArrayList<String> subList = new ArrayList<String>();
			
			/*storing the JSON Array in an arrayList 
			while searching for the strings within 
			the list that don't begin with the prefix.*/
			for(int j=0; j< arr.length(); j++){
				
				list.add(arr.get(j).toString());
				
				Boolean flag = list.get(j).startsWith(prefix);
				
				if(flag == false){	
					subList.add(list.get(j));
				}	
			}
			
			System.out.println("\nThe non prefixed strings are...");
			
			for(String e : subList){
				System.out.println(e);
			}
			
			//Creating JSON dictionary and initializing the url object
			dictionary.put("token", myToken);
			dictionary.put("array", subList);
			url = new URL("http://challenge.code2040.org/api/prefix/validate");
			
			//Sending the arrayliast of non prefix strings back to the api for validation
			stream = postRequest(dictionary, url);
			System.out.println("\n" + stream.readLine());
			
			stream.close();
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	  * This method adds a specific interval of seconds to datestamp
	  * in ISO 8601 format. The new datestamp is returned to the API 
	  * endpoint in the ISO 8601 format for validation.
	  * *@param Nothing.
	  * @return Nothing.
      * @exception MalformedURLException
      * @exception JSONException  
      * @exception IOException
      * @exception ParseException
      */
	public static void datingGame(){
	
		try {
			//Creating JSON dictionary and initializing the url object
			JSONObject dictionary = new JSONObject();
			dictionary.put("token", myToken);
			URL url = new URL("http://challenge.code2040.org/api/dating");
			
			BufferedReader stream = postRequest(dictionary, url);
			
			JSONObject obj = new JSONObject(stream.readLine());
			String datestamp = obj.getString("datestamp");
			int interval = obj.getInt("interval");
			
			System.out.println("The original ISO 8601 datestamp: " + datestamp);
			System.out.println("The seconds interval: " + interval);
			
			/*I had a difficult time using certain libraries to add the interval of seconds
			 * to the datestamp. So I did the calculations manually and used the SimpleDateFormat
			 * method to convert the datestamp to seconds and vice-versa.
			 */
			SimpleDateFormat obj1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
			Date date = obj1.parse(datestamp);	
			
			//Performing calculations for the new datestamp
			long date_seconds = (long)(date.getTime()*(.001));
			long total_seconds = (date_seconds + interval)*(long)(1e3);
			Date newDate = new Date(total_seconds);
			
			//Reverting the date back into ISO 8601 format
			String formattedDate = obj1.format(newDate);		
			System.out.println("\nThe resulting ISO 8601 datestamp: " + formattedDate); 

			//Creating JSON dictionary and initializing the url object
			dictionary.put("token", myToken);
			dictionary.put("datestamp", formattedDate);
			url = new URL("http://challenge.code2040.org/api/dating/validate");
			
			//Sending the datestamp back to the API for validation
			stream = postRequest(dictionary, url);
			System.out.println("\n" + stream.readLine());
			
			stream.close();
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
	}
	
}
