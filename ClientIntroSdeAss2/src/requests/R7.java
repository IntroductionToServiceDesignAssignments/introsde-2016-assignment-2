package requests;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

//request #7
public class R7 {
	// send the request
	// get a particular history
	static void sendR7(String measure, int personId, int History, String accept) throws Exception {
		try{
		// request settings
		String url = R1.SERVER_URL + "/person/" + personId + "/" + measure + "/" + History;

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		con.setRequestMethod("GET");
		if (accept.equals("application/json")) {
			con.setRequestProperty("Accept", "application/json");
			R1.wjson.write("\n\nRequest #7: GET " + url + " Accept: application/json");
			System.out.println("Request #7: GET " + url + " Accept: application/json");
		} else {
			con.setRequestProperty("Accept", "application/xml");
			R1.wxml.write("\n\nRequest #7: GET " + url + " Accept: application/xml");
			System.out.println("Request #7: GET " + url + " Accept: application/xml");
		}
		int responseCode = con.getResponseCode();
		// check the response status
		if (responseCode == 200 || responseCode == 202) {

			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			// print xml result
			if (accept.equals("application/json")) {
				R1.wjson.write("\nResult: OK");
				R1.wjson.write("\nHTTP Status: " + responseCode);
				R1.wjson.write("\n" + response.toString());

				System.out.println("Result: OK");
				System.out.println("HTTP Status: " + responseCode);
				System.out.println(response);
			} else {
				R1.wxml.write("\nResult: OK");
				R1.wxml.write("\nHTTP Status: " + responseCode);
				R1.wxml.write("\n" + response.toString());

				System.out.println("Result: OK");
				System.out.println("HTTP Status: " + responseCode);
				System.out.println(response);

			}

		} else {
			// print json result
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
		}}catch(Exception e){System.out.println(e);}
	}

}
