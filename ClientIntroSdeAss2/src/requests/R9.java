package requests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.json.JSONArray;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

// request #9
public class R9 {
	static ArrayList<String> measureTypes = new ArrayList();

	// get all the measure Types present in the db
	static void sendR9(String accept) throws Exception {
		try{
		// request settings
		ArrayList<String> measureTypesJSON = new ArrayList();
		String url = R1.SERVER_URL + "/measureTypes";

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setDoOutput(true);
		con.setRequestMethod("GET");

		if (accept.equals("application/json")) {
			con.setRequestProperty("Accept", "application/json");
			R1.wjson.write("\n\nRequest #9: GET " + url + " Accept: application/json");
			System.out.println("Request #9: GET " + url + " Accept: application/json");
		} else {
			con.setRequestProperty("Accept", "application/xml");
			R1.wxml.write("\n\nRequest #9: GET " + url + " Accept: application/xml");
			System.out.println("Request #9: GET " + url + " Accept: application/xml");
		}
		int responseCode = con.getResponseCode();

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		if (accept.equals("application/xml")) {
			measureTypes = readXML(response.toString());
		} else {// con json
			measureTypesJSON = readJSON(response.toString());
		}
		// check if the response contains more than 2 measure types
		// print result
		if (measureTypes.size() > 2) {

			R1.wxml.write("\nResult: OK");
			R1.wxml.write("\nHTTP Status: " + responseCode);
			R1.wxml.write("\n" + response.toString());
		} else {
			R1.wxml.write("\nResult: ERROR");
			R1.wxml.write("\nHTTP Status: " + responseCode);
			System.out.println("Result: ERROR");
			System.out.println("HTTP Status: " + responseCode);
		}
		if (measureTypesJSON.size() > 2) {

			R1.wjson.write("\nResult: OK");
			R1.wjson.write("\nHTTP Status: " + responseCode);
			R1.wjson.write("\n" + response.toString());
		} else {
			R1.wjson.write("\nResult: ERROR");
			R1.wjson.write("\nHTTP Status: " + responseCode);
			System.out.println("Result: ERROR");
			System.out.println("HTTP Status: " + responseCode);
		}}catch(Exception e){System.out.println(e);}

	}

	// parse xml response in order to check the number of measure types present
	// in the response
	static ArrayList<String> readXML(String response) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(new InputSource(new StringReader(response)));
		Element rootElement = document.getDocumentElement();
		NodeList list = rootElement.getElementsByTagName("measureName");
		ArrayList<String> temp = new ArrayList();
		for (int i = 0; i < list.getLength(); i++) {
			temp.add(list.item(i).getTextContent().toString());
		}

		return temp;

	}

	// parse json response in order to check the number of measure types present
	// in the response
	static ArrayList<String> readJSON(String response) {
		ArrayList<String> temp = new ArrayList();
		JSONArray jarr = new JSONArray(response);
		for (int i = 0; i < jarr.length(); i++) {
			temp.add(jarr.getJSONObject(0).getString("measureName"));
		}

		return temp;

	}

}
