package requests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

//request #8
public class R8 {
	// method used to manage the sending of the request #8
	static void sendR8(String idPerson, String measure) throws Exception {
		try{
		// send the request #6 in order to count the historyes
		int firstCount = sendR6(idPerson, measure, "xml");
		// send xml request
		sendR8XML(idPerson, measure);
		// send the request #6 in order to count the historyes

		int secondCount = sendR6(idPerson, measure, "xml");
		// chek if the new history with the old health profile value has been
		// inserted
		if (firstCount + 1 == secondCount) {
			// print final xml result
			R1.wxml.write("\nContent type:application/xml Result: OK");
			R1.wxml.write("\nHTTP Status: " + "New Health Profile inserted, History updated");

			System.out.println("Content type:application/xml Result: OK");
			System.out.println("HTTP Status: " + "New Health Profile inserted, History updated");
		} else {
			R1.wxml.write("\nResult: ERROR");
			R1.wxml.write("\nHTTP Status: " + "404");
			System.out.println("Result: ERROR");
			System.out.println("HTTP Status: " + "404");
		}
		// send the request #6 in order to count the historyes

		firstCount = sendR6(idPerson, measure, "json");
		// send json request
		sendR8JSON(idPerson, measure);
		// send the request #6 in order to count the historyes
		secondCount = sendR6(idPerson, measure, "json");
		// chek if the new history with the old health profile value has been
		// inserted
		if (firstCount + 1 == secondCount) {
			// print final json result
			R1.wjson.write("\nContent type:application/json Result: OK");
			R1.wjson.write("\nHTTP Status: " + "New Health Profile inserted, History updated");
			System.out.println("Content type:application/json Result: OK");
			System.out.println("HTTP Status: " + "New Health Profile inserted, History updated");
		} else {
			R1.wjson.write("Content type:application/json Result: ERROR");
			R1.wjson.write("HTTP Status: " + "404");
			System.out.println("Content type:application/json Result: ERROR");
			System.out.println("HTTP Status: " + "404");
		}}catch(Exception e){System.out.println(e);}

	}

	// send the request using a xml body
	// create a new health profile for a person and a particular measure
	// save in the history the old health profile
	static void sendR8XML(String idPerson, String measure) throws Exception {
		try{
		// request settings
		String url = R1.SERVER_URL + "/person/" + idPerson + "/" + measure;

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setDoOutput(true);
		con.setRequestMethod("POST");
		con.setRequestProperty("Accept", "application/xml");
		con.setRequestProperty("Content-Type", "application/xml");
		// request body
		String str = "<healthMeasureHistory><timestamp>2011-12-09</timestamp><value>72</value></healthMeasureHistory>";
		byte[] outputInBytes = str.getBytes("UTF-8");
		OutputStream os = con.getOutputStream();
		os.write(outputInBytes);
		os.close();

		int responseCode = con.getResponseCode();
		// print result
		R1.wxml.write("\n\nRequest #8: POST " + url + " Accept:application/xml Content-Type application/xml");
		System.out.println("Request #8: POST " + url + " Accept:application/xml Content-Type application/xml");

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		R1.wxml.write("\n" + response.toString());
		System.out.println(response);}catch(Exception e){System.out.println(e);}

	}

	// send the request using a json body
	// create a new health profile for a person and a particular measure
	// save in the history the old health profile
	static void sendR8JSON(String idPerson, String measure) throws Exception {
		try{
		// request settings
		String url = R1.SERVER_URL + "/person/" + idPerson + "/" + measure;

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setDoOutput(true);
		con.setRequestMethod("POST");
		con.setRequestProperty("Accept", "application/json");
		con.setRequestProperty("Content-Type", "application/json");
		// request body
		String str = " {\"value\": \"72\", \"timestamp\":\"2011-12-09 00:00:00\"}";
		byte[] outputInBytes = str.getBytes("UTF-8");
		OutputStream os = con.getOutputStream();
		os.write(outputInBytes);
		os.close();

		// print result
		int responseCode = con.getResponseCode();
		R1.wjson.write("\n\nRequest #8: POST " + url + " Accept:application/json Content-Type application/json");
		System.out.println("Request #8: POST " + url + " Accept:application/json Content-Type application/json");

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		System.out.println(response);}catch(Exception e){System.out.println(e);}
	}

	// request r6 modified in order to count the number of histories of a given
	// person and a given measure
	static int sendR6(String idPerson, String measure, String accept) throws Exception {
		try{
		String url = R1.SERVER_URL + "/person/" + idPerson + "/" + measure;

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		con.setRequestMethod("GET");

		int responseCode = con.getResponseCode();
		if (accept.equals("json")) {
			R1.wjson.write("\nRequest #6: GET " + url + " Accept: application/json");
			System.out.println("\nRequest #6: GET " + url + " Accept: application/json");

		} else {
			R1.wxml.write("\nRequest #6: GET " + url + " Accept: application/xml");
			System.out.println("\nRequest #6: GET " + url + " Accept: application/xml");
		}

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
		if ((rootElement.getFirstChild() != null) && (responseCode == 200 || responseCode == 202)) {
			if (accept.equals("json")) {
				R1.wjson.write("\nResult: OK");
				R1.wjson.write("\nHTTP Status: " + responseCode);
				System.out.println("Result: OK");
				System.out.println("HTTP Status: " + responseCode);
			} else {
				R1.wxml.write("\nResult: OK");
				R1.wxml.write("\nHTTP Status: " + responseCode);
				System.out.println("Result: OK");
				System.out.println("HTTP Status: " + responseCode);
			}
			return readXML(response.toString());

		} else {
			if (accept.equals("json")) {
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
			return 0;
		}}catch(Exception e){ System.out.println(e); return 0;}

	}

	static int readXML(String response) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(new InputSource(new StringReader(response)));
		Element rootElement = document.getDocumentElement();
		NodeList list = rootElement.getElementsByTagName("idMeasureHistory");
		ArrayList<String> temp = new ArrayList();
		return list.getLength();

	}

}
