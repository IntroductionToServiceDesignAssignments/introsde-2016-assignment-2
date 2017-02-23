package requests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

//request #4
public class R4 {
	static int idPersonJson = 0;
	static int idPersonXML = 0;

	// send the request using a json body
	// insert a new person in the db
	static void sendR4JSON() throws Exception {
		// request settings
		try{
		String url = R1.SERVER_URL + "/person";

		URL obj = new URL(url);
		HttpURLConnection con;

		con = (HttpURLConnection) obj.openConnection();

		con.setDoOutput(true);
		con.setRequestMethod("POST");
		con.setRequestProperty("Accept", "application/json");
		con.setRequestProperty("Content-Type", "application/json");
		// request body
		//string used for my server
		/*String str = " {" + "\"lastname\": \"Norris\"," + "\"firstname\": \"Chuck\"," + "\"birthdate\": \"1945-01-01\","
				+ "\"measure\": [" + " {" + " \"value\": \"78.9\"," + "\"measureDefinition\": {"
				+ "\"idMeasureDef\": 1," + "\"measureName\": \"weight\"," + "\"measureType\": \"double\"" + " }" + "},"
				+ " {" + "  \"value\": \"172\"," + " \"measureDefinition\": {" + "  \"idMeasureDef\": 2,"
				+ " \"measureName\": \"height\"," + "\"measureType\": \"double\"" + "}" + "}" + "]}";
		*/
		//string used for Daniele Dellagiacoma's server
		String str=" {\"lastname\":\"Norris\",\"firstname\":\"Chuck\",\"birthdate\":\"1945-01-01\",\"measureType\": [{\"value\": \"78.9\",\"measureDefinition\": {\"idMeasureDef\": 1,\"measureName\": \"weight\",\"measureType\": \"double\"}},{\"value\": \"172\",\"measureDefinition\": {\"idMeasureDef\": 2,\"measureName\": \"height\",\"measureType\":\"double\"}}]}";
		byte[] outputInBytes = str.getBytes("UTF-8");
		OutputStream os = con.getOutputStream();
		os.write(outputInBytes);
		os.close();

		int responseCode = con.getResponseCode();
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		JSONObject jobj = new JSONObject(response.toString());

		idPersonJson = jobj.getInt("idPerson");
		R1.wjson.write("\n\nRequest #4: POST " + url + "  Accept:application/json Content-Type application/json");

		System.out.println("Request #4: POST " + url + "  Accept:application/json Content-Type application/json");
		// check if the request succeed(the person has been inserted)
		// check the response status
		if ((responseCode == 200 || responseCode == 202) && (idPersonJson != 0)) {
			R1.wjson.write("\nResult: OK");
			R1.wjson.write("\nHTTP Status: " + responseCode);
			R1.wjson.write("\n" + response.toString());
			System.out.println("Result: OK");
			System.out.println("HTTP Status: " + responseCode);

		} else {
			R1.wjson.write("\nResult: ERROR");
			R1.wjson.write("\nHTTP Status: " + responseCode);
			System.out.println("Result: ERROR");
			System.out.println("HTTP Status: " + responseCode);
		}}catch(Exception e){System.out.println(e);}
	}

	// send the request using a xml body
	// insert a new person in the db
	static void sendR4XML() throws Exception {
try{
		String url = R1.SERVER_URL + "/person/";

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setDoOutput(true);
		con.setRequestMethod("POST");
		con.setRequestProperty("Accept", "application/xml");
		con.setRequestProperty("Content-Type", "application/xml");
		// request body
	
		String str = "<person><birthdate>1945-01-012</birthdate>"
				+ "<firstname>Chuck</firstname><lastname>Norris</lastname><HealthProfile>" + "<measure>"
				+ "<measureDefinition>" + "      <idMeasureDef>1</idMeasureDef>"
				+ "    <measureName>weight</measureName>" + "  <measureType>double</measureType>"
				+ " </measureDefinition>" + " <value>78.9</value>" + "</measure>" + "<measure>" + "<measureDefinition>"
				+ "      <idMeasureDef>2</idMeasureDef>" + "    <measureName>height</measureName>"
				+ "  <measureType>double</measureType>" + " </measureDefinition>" + " <value>172</value>" + "</measure>"
				+ "</HealthProfile>" + "</person>";
		
		
		byte[] outputInBytes = str.getBytes("UTF-8");
		OutputStream os = con.getOutputStream();
		os.write(outputInBytes);
		os.close();

		int responseCode = con.getResponseCode();
		R1.wxml.write("\n\nRequest #4: POST " + url + " Accept:application/xml Content-Type application/xml");
		System.out.println("Request #4: POST " + url + " Accept:application/xml Content-Type application/xml");

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		String resp = "";
		resp = readXML(response.toString());
		idPersonXML = Integer.parseInt(resp);
		// check if the request succeed(the person has been inserted)
		if (!resp.equals("") && (responseCode == 200 || responseCode == 202 || responseCode == 201)) {
			R1.wxml.write("\nResult: OK");
			R1.wxml.write("\nHTTP Status: " + responseCode);
			R1.wxml.write("\n" + response.toString());
			System.out.println("Result: OK");
			System.out.println("HTTP Status: " + responseCode);

		} else {
			R1.wxml.write("\nResult: ERROR");
			R1.wxml.write("\nHTTP Status: " + responseCode);
			System.out.println("Result: ERROR");
			System.out.println("HTTP Status: " + responseCode);
		}}catch(Exception e){System.out.println(e);}

	}

	// parse xml to get the id of the person inserted
	static String readXML(String response) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(new InputSource(new StringReader(response)));
		Element rootElement = document.getDocumentElement();
		return rootElement.getElementsByTagName("idPerson").item(0).getTextContent().toString();
	}

}
