package group22.utils;
import java.io.BufferedReader;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.eclipse.jetty.server.Request;
import javax.servlet.http.HttpServletRequest;



public class Helpers {
    // create JSON object of the http post request body from the webhook
    // @param buff http Post request payload
    // "return JSON object containing the data from the webhook
    // @throws Exception
    public static JSONObject convertBody(HttpServletRequest request) throws Exception{
        //read the payload line by line
        StringBuilder st = new StringBuilder();
        System.out.println("st done");
        BufferedReader buff = request.getReader();
        System.out.println("buff done");
        String temp=buff.readLine();
        System.out.println("temp done");
        String payload="";
        while(temp!=null){
                st.append(temp);
                temp=buff.readLine();
        }
        System.out.println("while done");
        buff.close();
        
        payload = st.toString();
        System.out.println("payload: " + payload);
        JSONParser parser = new JSONParser();
        System.out.println("parser done");
        JSONObject json = (JSONObject) parser.parse(payload);
        System.out.println("json doone");
        return json;
        
    }

    public static String getCloneUrl(JSONObject payload){
        return ((JSONObject) payload.get("repository")).get("clone_url").toString();
    }
    
}
