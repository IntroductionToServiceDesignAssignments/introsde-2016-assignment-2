package requests;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

//request #5
public class R5 {
	// send the request, deleting the person created using json
	// send the request, deleting the person created using xml
	static void sendR5(int idPerson) throws Exception {
		// request settings
		try{
		String url = R1.SERVER_URL + "/person/" + idPerson;

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("DELETE");

		int responseCode = con.getResponseCode();
		R1.wxml.write("\n\nRequest #5: DELETE " + url);
		R1.wjson.write("\n\nRequest #5: DELETE " + url);
		System.out.println("\nRequest #5: DELETE " + url);
		// send request #2 in order to check if the person has been deleted
		// if the request return errore the request #5 succeeded
		R2.sendR2(String.valueOf(idPerson), 1, "application/xml");
		System.out.println(R2.responseCodeCheck);
		// print result
		// check the response status of the request #R2 just called
		// if the response status contains an error code means that the person has been deleted correctly
		if (R2.responseCodeCheck == 404 || R2.responseCodeCheck == 500) {
			R1.wxml.write("\nResult: OK");
			R1.wxml.write("\nHTTP Status: " + responseCode);
			R1.wjson.write("\nResult: OK");
			R1.wjson.write("\nHTTP Status: " + responseCode);

			System.out.println("Result: OK");
			System.out.println("HTTP Status: " + responseCode);
		} else {
			R1.wxml.write("\nResult: ERROR");
			R1.wxml.write("\nHTTP Status: " + responseCode);
			R1.wjson.write("\nResult: ERROR");
			R1.wjson.write("\nHTTP Status: " + responseCode);
			System.out.println("Result: ERROR");
			System.out.println("HTTP Status: " + responseCode);
		}}catch(Exception e){System.out.println(e);}
	}

}
