package requests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

//request #6
public class R6 {

	static int checker = 0;
	static int idHistory;
	static String measureType;

	// method used to send all th @6 request for each reault returned from the
	// request #9
	static void manageR6requests(String idFirst, String idLast, ArrayList<String> measures) throws Exception {
		// for each result send the request #6 return in xml
		try{
		for (String measure : measures) {
			sendR6XML(idFirst, measure);
			sendR6XML(idLast, measure);
		}
		// print result
		if (checker >= 1) {
			R1.wxml.write("\nRequest #6: Correct results: " + checker + "            OK");
			System.out.println("\nRequest #6: Correct results: " + checker + "            OK");

		} else {
			R1.wxml.write("\nRequest #6: ERROR Correct results: " + checker);
			System.out.println("\nRequest #6: ERROR Correct results: " + checker);

		}

		checker = 0;
		for (String measure : measures) {
			sendR6JSON(idFirst, measure);
			sendR6JSON(idLast, measure);
		}
		// for each result send the request #6 return in json

		if (checker >= 1) {
			R1.wjson.write("\nRequest #6: Correct results: " + checker + "            OK");
			System.out.println("\nRequest #6: Correct results: " + checker + "            OK");

		} else {
			R1.wjson.write("\nRequest #6: ERROR Correct results: " + checker);
			System.out.println("\nRequest #6: ERROR Correct results: " + checker);

		}}catch(Exception e){System.out.println(e);}

	}

	// xml request
	// get the health history of a particular person and measure
	static void sendR6XML(String idPerson, String measure) throws Exception {
		try{
		// request settings
		String url = R1.SERVER_URL + "/person/" + idPerson + "/" + measure;

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		con.setRequestMethod("GET");

		int responseCode = con.getResponseCode();
		R1.wxml.write("\n\nRequest #6: GET " + url + " Accept: application/xml");
		System.out.println("\nRequest #6: GET " + url + " Accept: application/xml");

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(new InputSource(new StringReader(response.toString())));
		Element rootElement = document.getDocumentElement();
		// print result
		if ((rootElement.getFirstChild() != null) && (responseCode == 200 || responseCode == 202)) {
			R1.wxml.write("\nResult: OK");
			R1.wxml.write("\nHTTP Status: " + responseCode);
			System.out.println("Result: OK");
			System.out.println("HTTP Status: " + responseCode);
			checker++;
			if (checker == 1) {
				// parse the xml of the first correct result
				readXML(response.toString());
			}
			R1.wxml.write("\n" + response.toString());
			System.out.println(response);
		} else {
			R1.wxml.write("\nResult: ERROR");
			R1.wxml.write("\nHTTP Status: " + responseCode);
			System.out.println("Result: ERROR");
			System.out.println("HTTP Status: " + responseCode);
		}}catch(Exception e){System.out.println(e);}

	}

	// parse xml response in order to get the idMeasureHistory and measureName
	static void readXML(String response) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(new InputSource(new StringReader(response)));
		Element rootElement = document.getDocumentElement();
		idHistory = Integer
				.parseInt(rootElement.getElementsByTagName("idMeasureHistory").item(0).getTextContent().toString());
		measureType = rootElement.getElementsByTagName("measureName").item(0).getTextContent().toString();
	}

	// json request
	// get the health history of a particular person and measure

	static void sendR6JSON(String idPerson, String measure) throws Exception {
		try{
		// request settings
		String url = R1.SERVER_URL + "/person/" + idPerson + "/" + measure;

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		con.setRequestMethod("GET");
		con.setRequestProperty("Accept", "application/json");
		int responseCode = con.getResponseCode();
		R1.wjson.write("\n\nRequest #6: GET " + url + " Accept: application/json");
		System.out.println("\nRequest #6: GET " + url + " Accept: application/json");

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		JSONArray jarr = new JSONArray(response.toString());
		int idMeasureHist = 0;

		// get idMeasureHistoryin order to get the correctness of the request
		if (jarr.length() != 0) {
			idMeasureHist = jarr.getJSONObject(0).getInt("idMeasureHistory");
		}
		// print result
		if ((idMeasureHist != 0) && (responseCode == 200 || responseCode == 202)) {
			R1.wjson.write("\nResult: OK");
			R1.wjson.write("\nHTTP Status: " + responseCode);
			System.out.println("Result: OK");
			System.out.println("HTTP Status: " + responseCode);
			checker++;

			R1.wjson.write("\n" + response.toString());
			System.out.println(response);
		} else {
			R1.wjson.write("\nResult: ERROR");
			R1.wjson.write("\nHTTP Status: " + responseCode);
			System.out.println("Result: ERROR");
			System.out.println("HTTP Status: " + responseCode);
		}}catch(Exception e){System.out.println(e);}

	}

}
