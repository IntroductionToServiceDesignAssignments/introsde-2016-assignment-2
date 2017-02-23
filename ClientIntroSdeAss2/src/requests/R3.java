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

import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class R3 {
	// send the request #3 in using a json body
	// update a person in th db
	static void sendR3JSON(String first_person_id) throws Exception {
		// request settings
		try{
		String url = R1.SERVER_URL + "/person/" + first_person_id;

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setDoOutput(true);

		con.setRequestMethod("PUT");
		con.setRequestProperty("Accept", "application/json");
		con.setRequestProperty("Content-Type", "application/json");
		// request boody
		String str = "{\"firstname\":\"Angolo\",\"lastname\":\"Pallio\",\"birthdate\":\"1978-9-2\"}";
		byte[] outputInBytes = str.getBytes("UTF-8");
		OutputStream os = con.getOutputStream();
		os.write(outputInBytes);
		os.close();

		int responseCode = con.getResponseCode();
		R1.wjson.write("\n\nRequest #3: PUT " + url + "  Accept:application/json Content-Type application/json ");

		System.out.println("Request #3: PUT " + url + "  Accept:application/json Content-Type application/json ");

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		JSONObject jobj = new JSONObject(response.toString());
		String name = jobj.getString("firstname");

		System.out.println(name);
		// check if the request is correct(if the name has been updated)
		if (name.equals("Angolo")) {
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

	// send the request #3 in using a xml body
	static void sendR3XML(String first_person_id) throws Exception {
		// request settings
		try{
		String url = R1.SERVER_URL + "/person/" + first_person_id;

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setDoOutput(true);

		con.setRequestMethod("PUT");
		con.setRequestProperty("Accept", "application/xml");
		con.setRequestProperty("Content-Type", "application/xml");
		// request body
		String str = "<person><lastname>Maregiot</lastname><firstname>Davide</firstname><birthdate>1978-09-02</birthdate></person>";
		byte[] outputInBytes = str.getBytes("UTF-8");
		OutputStream os = con.getOutputStream();
		os.write(outputInBytes);
		os.close();

		int responseCode = con.getResponseCode();
		R1.wxml.write("\n\nRequest #3: PUT " + url + "  Accept:application/xml Content-Type application/xml ");
		System.out.println("Request #3: PUT " + url + "  Accept:application/xml Content-Type application/xml ");

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		String resp = readXML(response.toString());
		// print result
		if (resp.equals("Davide")) {
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

	// parse xml in order to check if the request is correct(if the name has
	// been updated)
	static String readXML(String response) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(new InputSource(new StringReader(response)));
		Element rootElement = document.getDocumentElement();
		return rootElement.getElementsByTagName("firstname").item(0).getTextContent().toString();
	}

}
