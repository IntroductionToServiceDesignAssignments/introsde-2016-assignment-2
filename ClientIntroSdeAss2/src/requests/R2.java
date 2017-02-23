package requests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class R2 {
	//used to retrive the response status when the method is called from the class R5
	static int responseCodeCheck;

	// send the second request return th person with the first id
	// response xml and json
	static void sendR2(String first_person_id, int check, String accept) throws Exception {
		// request settings
try{
		String url = R1.SERVER_URL + "/person/" + first_person_id;

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		con.setRequestMethod("GET");
		if (accept.equals("application/json")) {
			con.setRequestProperty("Accept", "application/json");
			R1.wjson.write("\n\nRequest #2: GET " + url + " Accept: application/json");
			System.out.println("Request #2: GET " + url + " Accept: application/json");
		} else {
			con.setRequestProperty("Accept", "application/xml");
			R1.wxml.write("\n\nRequest #2: GET " + url + " Accept: application/xml");
			System.out.println("Request #2: GET " + url + " Accept: application/xml");
		}
		int responseCode = con.getResponseCode();
		
		// if the method is called from R1. class the value is 0
		// if the method is called from R5 class the value is 1
		if (check == 0) {
			// check the response status
			if (responseCode == 200 || responseCode == 202) {

				System.out.println("Result: OK");
				System.out.println("HTTP Status: " + responseCode);
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();

				// print result
				if (accept.equals("application/json")) {
					R1.wjson.write("\nResult: OK");
					R1.wjson.write("\nHTTP Status: " + responseCode);
					System.out.println(response);
					R1.wjson.write("\n" + response.toString());
				} else {
					R1.wxml.write("\nResult: OK");
					R1.wxml.write("\nHTTP Status: " + responseCode);
					System.out.println(response);
					R1.wxml.write("\n" + response.toString());

				}

			} else {
				if (accept.equals("application/json")) {
					R1.wjson.write("\nResult: ERROR");
					R1.wjson.write("\nHTTP Status: " + responseCode);
					System.out.println("Result: ERROR");
					System.out.println("HTTP Status: " + responseCode);
				} else {
					R1.wxml.write("\nResult: ERROR");
					R1.wxml.write("\nHTTP Status: " + responseCode);
					System.out.println("Result: ERROR");
					System.out.println("HTTP Status: " + responseCode);

				}
			}
		} else {//if has been called from R5
			responseCodeCheck = responseCode;
		}}catch(Exception e){System.out.println(e);}
	}

}
