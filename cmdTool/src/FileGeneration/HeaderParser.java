package FileGeneration;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HeaderParser {
	private FileWriter fw = null;
	private PrintWriter pw = null;
	public HeaderParser() {
		try {
			fw = new FileWriter("HeaderOutput.txt");
			pw = new PrintWriter(fw);	
		}catch(FileNotFoundException fnfe) {
			System.out.println(fnfe.getMessage());
		}catch(IOException ioe) {
			System.out.println(ioe.getMessage());
		}

	}
	public void writeToFile(String inputString, String objectName) {
		try {
			JSONObject obj = new JSONObject(inputString);
			JSONObject result = obj.getJSONObject("results");
			JSONArray arr = result.getJSONArray("bindings");
			for(int i=0; i<arr.length(); i++) {	
				String value = arr.getJSONObject(i).getJSONObject("resource").getString("value");
				JSONObject outputObject = new JSONObject();
				outputObject.put("name: ",objectName);
				outputObject.put("relationship: ", "sameAs");
				outputObject.put("data entity: ", value);
				pw.println(outputObject);
			}
		}catch(JSONException je) {
			System.out.println(je.getMessage());
		}
	}
	
	public void flush() {
		pw.flush();
	}

}
