package requests;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.DocumentBuilder;

import org.json.JSONObject;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

public class R1 {

	static String first_person_id = "";
	static String last_person_id = "";
	// writers used to write in the log files
	static Writer wxml;
	static Writer wjson;
	// url of the web application
	// url of my server
	//static final String SERVER_URL = "https://lissonidavideapp.herokuapp.com/assignment2";
	
	//url of Daniele Dellagiacoma's server
	static final String SERVER_URL = "https://dellagiacomaintrosde2.herokuapp.com/sdelab";
	public static void main(String[] args) throws Exception {
		try {
			// create log files
			File filexml = new File("client-server-xml.log");
			FileOutputStream is = new FileOutputStream(filexml);
			OutputStreamWriter osw = new OutputStreamWriter(is);
			wxml = new BufferedWriter(osw);
			File filejson = new File("client-server-json.log");
			FileOutputStream is2 = new FileOutputStream(filejson);
			OutputStreamWriter osw2 = new OutputStreamWriter(is2);
			wjson = new BufferedWriter(osw2);

		} catch (IOException e) {
			System.err.println("Problem writing to the file statsTest.txt");
		}
		// send the requests
		Integer nPerson = sendCount();
		sendR1(nPerson, "application/xml");
		sendR1(nPerson, "application/json");
		R2.sendR2(first_person_id, 0, "application/xml");
		R2.sendR2(first_person_id, 0, "application/json");
		R3.sendR3JSON(first_person_id);
		R3.sendR3XML(first_person_id);
		R4.sendR4XML();
		R4.sendR4JSON();
		R5.sendR5(R4.idPersonJson);
		R5.sendR5(R4.idPersonXML);
		R9.sendR9("application/xml");
		R9.sendR9("application/json");
		R6.manageR6requests(first_person_id, last_person_id, R9.measureTypes);
		R7.sendR7(R6.measureType, 1, R6.idHistory, "application/xml");
		R7.sendR7(R6.measureType, 1, R6.idHistory, "application/json");
		R8.sendR8(first_person_id, "weight");
		wxml.close();
		wjson.close();
	}

	// request sended to person/count, return the number of person present in
	// the db
	static Integer sendCount() throws Exception {
		// request settings
		try{String url = SERVER_URL + "/person/count";

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		con.setRequestMethod("GET");

		int responseCode = con.getResponseCode();

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		// print result
		return Integer.parseInt(response.toString());}catch(Exception e){
			System.out.println(e);
			return 0;
			
		}

	}

	// sending the #1 requests, return all th people contained in the db
	// respons return xml and json
	static void sendR1(Integer nPerson, String accept) throws Exception {

		try{String url = SERVER_URL + "/person";

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		// add request header
		// con.setRequestProperty("User-Agent", USER_AGENT);
		if (accept.equals("application/json")) {
			con.setRequestProperty("Accept", "application/json");
			wjson.write("Request #1: GET " + url + " Accept: application/json");
			System.out.println("Request #1: GET " + url + " Accept: application/json");
		} else {
			// con.setRequestProperty("Accept","application/json");
			wxml.write("Request #1: GET " + url + " Accept: application/xml");
			System.out.println("Request #1: GET " + url + " Accept: application/xml");
		}
		int responseCode = con.getResponseCode();
		// check if the db contains more than 2 persons
		if (nPerson >= 2) {

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
			if (accept.equals("application/xml")) {
				wxml.write("\nResult: OK");
				wxml.write("\nHTTP Status: " + responseCode);
				readXML(response.toString());
				System.out.println(response);
				wxml.write("\n" + response.toString());

			} else {
				wjson.write("\nResult: OK");
				wjson.write("\nHTTP Status: " + responseCode);
				wjson.write("\n" + response.toString());
				System.out.println(response);
			}

		} else {
			wxml.write("\n Result: ERROR");
			wxml.write("\n HTTP Status: " + responseCode);
			wjson.write("\n Result: ERROR");
			wjson.write("\n HTTP Status: " + responseCode);
			System.out.println("Result: ERROR");
			System.out.println("HTTP Status: " + responseCode);
		}}catch(Exception e){System.out.println(e);}

	}

	static void readXML(String response) throws ParserConfigurationException, SAXException, IOException {
		// parse the cml response to get and save the first and the last person
		// id
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(new InputSource(new StringReader(response)));
		Element rootElement = document.getDocumentElement();
		Node first = rootElement.getFirstChild();
		Node last = rootElement.getLastChild();
		Element eElement = (Element) first;
		first_person_id = eElement.getElementsByTagName("idPerson").item(0).getTextContent().toString();
		Element eElement2 = (Element) last;
		last_person_id = eElement2.getElementsByTagName("idPerson").item(0).getTextContent().toString();

	}

}
